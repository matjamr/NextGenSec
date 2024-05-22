import {Component, EventEmitter, Inject, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Observable} from "rxjs";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-inquiry-message-dialog',
  templateUrl: './inquiry-message-dialog.component.html',
  styleUrl: './inquiry-message-dialog.component.css'
})
export class InquiryMessageDialogComponent<T> implements OnInit{
  messageForm!: FormGroup;

  @Input()
  searchData$?: Observable<T[] | null>

  staticInquiry: string | null;

  @Input()
  searchPredicate!: (data: T, search: string) => boolean;

  @Input()
  renderView!: (data: T) => string;

  @Input()
  formControl: FormControl<T | null> = new FormControl<T | null>(null);

  @Output()
  dialogClosed: EventEmitter<any> = new EventEmitter();

  closeDialog() {
    this.dialogClosed.emit(this.messageForm.value);
    this.dialogRef.close();
  }


  constructor(@Inject(MAT_DIALOG_DATA) public data: any,  private dialogRef: MatDialogRef<InquiryMessageDialogComponent<T>>) {
    this.staticInquiry = data.staticInquiry;
  }

  ngOnInit() {
    this.messageForm = new FormGroup({
      message: new FormControl('')
    });
  }

  protected readonly close = close;
}
