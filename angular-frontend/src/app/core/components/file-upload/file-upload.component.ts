import {Component, OnInit} from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatDialog} from "@angular/material/dialog";
import {DialogConfirmComponent} from "../dialog-confirm/dialog-confirm.component";
import {ImageStoreService} from "../../services/image-store/image-store.service";

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrl: './file-upload.component.scss'
})
export class FileUploadComponent implements OnInit {
  hoverIndex: number | null = null;
  imagesToBeStored: File[] = [];

  constructor(private _snackBar: MatSnackBar,
              public dialog: MatDialog,
              private imageStoreService: ImageStoreService){
  }

  ngOnInit() {
  }

  onFileChange(pFileList: File[]){
    this.imagesToBeStored = Object.keys(pFileList).map(key => pFileList[parseInt(key)]);
    this.imageStoreService.changeImages(this.imagesToBeStored);
  }

  onFileDelete(index: any) {
    this.imagesToBeStored.splice(index, 1);
  }

  getFiles() {
    return this.imagesToBeStored;
  }

  onFileDetailsChange(event: Event){
    // @ts-ignore
    this.onFileChange((event.target as HTMLInputElement).files)
  }

  openConfirmDialog(pIndex: any): void {
    const dialogRef = this.dialog.open(DialogConfirmComponent, {
      panelClass: 'modal-xs'
    });
    dialogRef.componentInstance.fName = this.getFiles()[pIndex].name;
    dialogRef.componentInstance.fIndex = pIndex;


    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.onFileDelete(result);
      }
    });
  }
}
