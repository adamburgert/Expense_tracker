import {Component, signal} from '@angular/core';
import {RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {HttpClient, HttpClientModule, HttpHeaders} from '@angular/common/http';
import { Router } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ReportComponent } from './components/report/report.component';
import {LoginComponent } from './components/login/login.component';
import {ExpenseListComponent } from './components/expense-list/expense-list.component';
import {UserProfileComponent} from './components/user-profile/user-profile.component';
import {HomeComponent} from './components/home/home.component';
import {RegistrationComponent} from './components/registration/registration.component';
import {UserService} from './components/services/user.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    RouterLinkActive,
    RouterLinkActive,
    DashboardComponent,
    ReportComponent,
    LoginComponent,
    ExpenseListComponent,
    UserProfileComponent,
    HomeComponent,
    RegistrationComponent,
    RouterLink,
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'expense-tracker-frontend';

  constructor(private userService: UserService, private router: Router, private http: HttpClient) {
  }
}
