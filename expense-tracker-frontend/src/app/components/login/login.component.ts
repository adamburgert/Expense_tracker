import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../services/user.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../services/auth.service';

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

  constructor(private userService: UserService, private router: Router, private authService: AuthService) {}

  Login(): void {
    this.userService.onLogin(this.credentials).subscribe(
      (response) => {
        console.log('Login successful', response);
        this.router.navigate(['/dashboard']);
      },
      (error: any) => {
        console.error('Login failed', error);
        alert('Login failed: Invalid credentials or server error');
      }
    );
  }
}
