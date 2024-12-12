import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient, private router: Router) {}

  login(credentials: { username: string; name: string; email: string; password: string }): Observable<any> {
    const url = `${this.apiUrl}/login`;
    return this.http.post(url, credentials).pipe(
      catchError(this.handleError)
    );
  }

  register(user: { username: string; name: string; email: string; password: string }): Observable<any> {
    const url = `${this.apiUrl}/register`;
    return this.http.post(url, user).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error);
    if (error.status === 400) {
      alert('Registration failed: ' + error.error.message);
    }
    return throwError(() => new Error(error.message || 'An unexpected error occurred.'));
  }
}
