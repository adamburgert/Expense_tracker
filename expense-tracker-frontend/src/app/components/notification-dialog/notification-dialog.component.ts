import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-notification-dialog',
  templateUrl: './notification-dialog.component.html',
  standalone: true,
  styleUrls: ['./notification-dialog.component.css']
})
export class NotificationDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: string) {}
}
