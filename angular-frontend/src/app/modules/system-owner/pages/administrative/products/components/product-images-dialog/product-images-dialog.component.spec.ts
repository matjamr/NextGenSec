import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ProductImagesDialogComponent} from './product-images-dialog.component';

describe('ProductImagesDialogComponent', () => {
  let component: ProductImagesDialogComponent;
  let fixture: ComponentFixture<ProductImagesDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProductImagesDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductImagesDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
