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

  constructor(private http: HttpClient, private dialog: MatDialog) {}

  // Fetch notifications every 5 seconds (polling)
  startPolling(userId: number) {
    setInterval(() => {
      this.http.get<string[]>(`${this.apiUrl}?userId=${userId}`)
        .subscribe((notifications) => {
          if (notifications.length > 0) {
            notifications.forEach(notification => {
              this.showDialog(notification);
              this.notificationSubject.next(notification);
            });
          }
        });
    }, 5000);
  }

  private showDialog(notification: string) {
    this.dialog.open(NotificationDialogComponent, {
      data: notification,
      width: '400px',
      height: '200px'
    });
  }
}
