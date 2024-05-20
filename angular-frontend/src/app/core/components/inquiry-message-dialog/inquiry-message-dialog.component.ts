import {Component, Inject, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Observable} from "rxjs";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

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

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {
    this.staticInquiry = data.staticInquiry;
  }

  ngOnInit() {
    this.messageForm = new FormGroup({
      message: new FormControl('')
    });
  }
}
