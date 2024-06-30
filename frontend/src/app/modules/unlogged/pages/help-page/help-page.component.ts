// help-page.component.ts
import {Component} from '@angular/core';
import {
  InquiryMessageDialogComponent
} from "../../../../core/components/inquiry-message-dialog/inquiry-message-dialog.component";
import {SendMail} from "../../../../core/models/Mail";
import {MatDialog} from "@angular/material/dialog";
import {EmailingService} from "../../../../core/services/emailing/emailing.service";
import {NotificationService} from "../../../../core/services/notification/notification.service";

@Component({
  selector: 'app-help-page',
  templateUrl: './help-page.component.html',
  styleUrls: ['./help-page.component.css']
})
export class HelpPageComponent {
  faqs = [
    { question: 'How to reset your password?', answer: 'To reset your password, go to settings and click on "Reset Password".' },
    { question: 'How to update your profile?', answer: 'You can update your profile information from your account settings page.' }
  ];

  constructor(public dialog: MatDialog,
              private emailService: EmailingService,
              private notificationService: NotificationService) {
  }

  contactSupport() {
    const dialogRef = this.dialog.open(InquiryMessageDialogComponent, {
      width: '40%',
      height: '80vh',
      data: {staticInquiry: 'Ask a question' }
    });


    dialogRef.componentInstance.dialogClosed.subscribe(result => {
      const mailToBeSent: SendMail = {
        to: ['SYSTEM'],
        subject: `Question dt FAQ`,
        content: result.message
      }

      if (result) {
        this.emailService.sendEmail(mailToBeSent)
          .subscribe(() => this.notificationService.success('Mail', 'Question has been sent'));
      }
    });
  }
}
