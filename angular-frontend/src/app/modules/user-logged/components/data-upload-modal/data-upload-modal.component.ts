import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ImageService} from "../../../../core/services/image/image.service";
import {ProductsService} from "../../../../core/services/products/products.service";
import {Product} from "../../../../core/models/Product";

@Component({
  selector: 'app-data-upload-modal',
  templateUrl: './data-upload-modal.component.html',
  styleUrls: ['./data-upload-modal.component.scss']
})
export class DataUploadModalComponent {
  @Output() close = new EventEmitter<void>();
  dragover = false;
  files: File[] = [];
  products: Product[] = [];
  selectedProduct: Product = this.products[0];
  @Input()
  userProducts: Product[] = [];

  constructor(
    private imageService: ImageService,
    private productService: ProductsService
    ) {
    productService.getProducts().subscribe(ret => {
      this.products = ret;
      this.selectedProduct = this.products[0];
    })
  }

  onClose() {
    this.close.emit();
  }

  onFileDrop(files: FileList) {
    for (let i = 0; i < files.length; i++) {
      this.files.push(files[i]);
    }
  }

  onSubmit() {
    this.imageService.uploadImage(this.files, this.products, this.selectedProduct)
    this.close.emit();
  }

  triggerFileInput() {
    document.getElementById('fileInput')!!.click();
  }

  fileBrowseHandler(event: any) {
    const selectedFiles = event.target.files;
    for (let i = 0; i < selectedFiles.length; i++) {
      this.files.push(selectedFiles[i]);
    }
  }

  setSelectedProduct($event: any) {
    this.selectedProduct = this.products.find(p => p.name === $event.target.value)!!
    console.log(this.selectedProduct)
  }

  removeFile(file: File) {
    this.files = this.files.filter(f => f !== file);
  }

  isDataValid() {
    return this.files.length > 0;
  }
}
