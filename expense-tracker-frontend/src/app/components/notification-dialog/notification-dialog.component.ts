import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {MatButtonModule} from '@angular/material/button';

@Component({
  selector: 'app-notification-dialog',
  templateUrl: './notification-dialog.component.html',
  standalone: true,
  imports: [
    MatButtonModule
  ],
  styleUrls: ['./notification-dialog.component.css']
})
export class NotificationDialogComponent {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: string,
    public dialogRef: MatDialogRef<NotificationDialogComponent>
  ) {}

  closeDialog(): void {
    this.dialogRef.close();
  }
}
