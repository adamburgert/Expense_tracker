import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ExpenseListComponent } from './components/expense-list/expense-list.component';
import { ReportComponent } from './components/report/report.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import {NotificationComponent} from './components/notification/notification.component';
import {CategoryListComponent} from './components/category/category-list.component';
import {NotificationDialogComponent} from './components/notification-dialog/notification-dialog.component';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'expenses', component: ExpenseListComponent },
  { path: 'report', component: ReportComponent },
  { path: 'user-profile', component: UserProfileComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegistrationComponent },
  { path: 'notification', component: NotificationComponent },
  { path: 'category', component: CategoryListComponent },
  {path: 'notification-dialog', component: NotificationDialogComponent },
  { path: '**', redirectTo: '/home' }
];
