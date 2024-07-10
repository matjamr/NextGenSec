import {Component, Input} from '@angular/core';
import {Image} from "../../../../models/Image";
import {ImageDialogService} from "../../service/image-dialog.service";

@Component({
  selector: 'app-image-details',
  templateUrl: './image-details.component.html',
  styleUrl: './image-details.component.scss'
})
export class ImageDetailsComponent {
  @Input()
  image!: Image;

  @Input()
  isImageDeletionMode?: boolean | false;

  isSelected: boolean = false;

  constructor(
    private imageDialogService: ImageDialogService
  ) { }

  imageDeleteClick = () => {
    if(this.isSelected) {
      let imageIndex = this.imageDialogService.imagesToBeDeleted.indexOf(this.image);
      this.imageDialogService.imagesToBeDeleted.splice(imageIndex, 1);
    } else {
      this.imageDialogService.imagesToBeDeleted.push(this.image);
    }

    this.isSelected = !this.isSelected;
  }
}
