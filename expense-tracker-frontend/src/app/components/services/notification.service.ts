import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatDialog } from '@angular/material/dialog';
import { NotificationDialogComponent } from '../notification-dialog/notification-dialog.component';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private notificationSubject = new Subject<string>();
  public notification$ = this.notificationSubject.asObservable();

  private apiUrl = '/api/notifications/getLatestNotifications';
  private pollingIntervalId: any;

  constructor(private http: HttpClient, private dialog: MatDialog) {}

  startPolling(userId: number) {
    if (this.pollingIntervalId) {
      clearInterval(this.pollingIntervalId);
    }

    this.pollingIntervalId = setInterval(() => {
      this.http.get<string[]>(`${this.apiUrl}?userId=${userId}`)
        .subscribe((notifications) => {
          if (notifications.length > 0) {
            notifications.forEach(notification => {
              this.showDialog(notification);
              this.notificationSubject.next(notification);
            });
          }
        }, error => {
          console.error('Error fetching notifications', error);
        });
    }, 5000);
  }

  stopPolling() {
    if (this.pollingIntervalId) {
      clearInterval(this.pollingIntervalId);
    }
  }

  private showDialog(notification: string) {
    this.dialog.open(NotificationDialogComponent, {
      data: notification,
      width: '400px',
      height: '200px',
    }).afterClosed().subscribe(() => {
      console.log('Dialog was closed');
    });
  }
}
