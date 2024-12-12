import { Component, OnInit } from '@angular/core';
import { NotificationService } from '../services/notification.service';
import {NgForOf} from '@angular/common';

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

  constructor(private notificationService: NotificationService) {}

  ngOnInit(): void {
    const userId = 1;
    this.notificationService.startPolling(userId);


    this.notificationService.notification$.subscribe(notification => {
      this.notifications.push(notification);


      setTimeout(() => {
        this.notifications.shift();
      }, 5000);
    });
  }
}
