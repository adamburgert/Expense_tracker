import { Component, OnInit } from '@angular/core';
import { NotificationService } from '../services/notification.service';
import { MatDialog } from '@angular/material/dialog';
import { NotificationDialogComponent } from '../notification-dialog/notification-dialog.component';
import {NgForOf} from '@angular/common'; // Ensure correct path

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  standalone: true,
  imports: [
    NgForOf
  ],
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit {
  notifications: string[] = [];

  constructor(
    private notificationService: NotificationService,
    private dialog: MatDialog // Inject MatDialog here
  ) {
  }

  ngOnInit(): void {
    const userId = 1;
    this.notificationService.startPolling(userId);

    this.notificationService.notification$.subscribe(notification => {
      this.notifications.push(notification);

      // Open the dialog when a new notification is added
      this.openDialog(notification);

      // Remove the notification after 5 seconds
      setTimeout(() => {
        this.notifications.shift();
      }, 5000);
    });
  }

  // Open the dialog with the notification message
  openDialog(message: string): void {
    const dialogRef = this.dialog.open(NotificationDialogComponent, {
      data: message
    });

    dialogRef.afterClosed().subscribe(() => {
      console.log('Notification dialog was closed');
    });
  }
}
