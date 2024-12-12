import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import {HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {NgIf} from '@angular/common';


@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  standalone: true,
  imports: [
    FormsModule,
    NgIf
  ],
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  loading = false;
  userProfile: any = {};
  error: string | undefined;
  users:  any = {};


  constructor(private userService: UserService, protected router: Router) {}

  ngOnInit(): void {
    this.getUserProfile();
  }

  getUserProfile(): void {
    this.loading = true;
    this.userService.getUserProfile().subscribe(
      (data: any) => {
        this.userProfile = data;
        this.loading = false;
      },
      (error: HttpErrorResponse) => {
        console.error('Error fetching profile:', error);
        this.error = error.error.message || error.message;
        this.loading = false;
      }
    );
  }

  onUpdateUserProfile(): void {
    this.loading = true;

    this.userService.updateUserProfile(this.userProfile).subscribe({
      next: (response: any) => {
        console.log('Profile updated successfully', response);
        this.loading = false;
        this.router.navigate(['/dashboard']);
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error updating profile:', error);

        if (error.error && typeof error.error === 'object' && error.error.message) {
          this.error = error.error.message;
        } else if (typeof error.error === 'string') {
          this.error = error.error;
        } else {
          this.error = 'An unknown error occurred.';
        }

        this.loading = false;
      },
      complete: () => {
        console.log('Profile update request completed');
      }
    });
  }



  deleteUser(userId: number) {
    this.userService.deleteUser(userId).subscribe({
      next: (message: any) => {
        console.log(message);
      },
      error: (error) => {
        console.error('Error deleting user:', error.message);
        alert(error.message);
      }
    });
  }





  logout(): void {
    sessionStorage.clear();
    localStorage.clear();
    document.cookie = 'JSESSIONID=; Max-Age=0';
    this.router.navigate(['/login']);
  }
}
