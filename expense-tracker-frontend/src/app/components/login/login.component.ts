import { Component } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import { UserService } from '../services/user.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import {AuthService} from '../services/auth.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink]
})
export class LoginComponent {
  credentials = {
    username: '',
    name: '',
    email: '',
    password: ''
  };


  constructor(private userService: UserService, private router: Router, private authService: AuthService,  private http: HttpClient) {
  }

  onSubmit(): void {
    this.userService.onLogin(this.credentials).subscribe(
      (response: { token: string; }) => {

        console.log('Login successful', response);

        localStorage.setItem('***REMOVED***', response.token);

      },
      (error: any) => {

        console.error('Login failed', error);
        alert('Login failed: Invalid credentials or server error');
      }
    );
  }


  onLogout(): void {
    const token = localStorage.getItem('***REMOVED***');

    if (!token) {
      console.error('No JWT token found. User is not authenticated.');
      this.router.navigate(['/login']);
      return;
    }

    this.http.post('/api/users/logout', {}, {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${token}`
      })
    }).subscribe({
      next: () => {

        sessionStorage.clear();
        localStorage.clear();
        document.cookie = 'JSESSIONID=; Max-Age=0';


        this.router.navigate(['/login']);
      },
      error: (err: any) => {
        console.error('Logout failed', err);
      }
    });
  }
}
