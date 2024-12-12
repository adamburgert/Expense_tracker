import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import {Router, RouterLink} from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NgForOf } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  standalone: true,
  imports: [
    FormsModule,
    RouterLink,
    NgForOf
  ],
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  user = {
    username: '',
    name: '',
    email: '',
    password: ''
  };

  roles: string[] = ['admin', 'USER'];
  selectedRole: string = '';


  constructor(
    private authService: AuthService,
    private router: Router,
    private http: HttpClient
  ) {
  }

  ngOnInit(): void {
  }

  onRegister(): void {
    this.authService.register(this.user).subscribe(
      (response) => {
        console.log('Registration successful:', response);

        if (response && response.token) {
          // Store the token securely in localStorage
          localStorage.setItem('authToken', response.token);
          console.log('JWT token stored in localStorage');
        }


        this.router.navigate(['/login']);
      },
      (error) => {
        console.error('Registration error:', error);
      }
    );
  }
}
