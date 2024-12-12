import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient, private router: Router) {}

  getUserProfile(): Observable<any> {
    return this.http.get(`${this.apiUrl}/profile/{id}`).pipe(
      catchError(this.handleError)
    );
  }

  login(credentials: { username: string; name: string; email: string; password: string }): Observable<any> {
    const url = `${this.apiUrl}/login/{id}`;
    return this.http.post(url, credentials).pipe(
      tap((response: any) => {
        if (response) {
          console.log('Login successful:', response);
        }
      }),
      catchError(this.handleError)
    );
  }

  onLogin(credentials: { username: string; name: string; email: string; password: string }): Observable<any> {
    return this.login(credentials).pipe(
      catchError(this.handleError)
    );
  }

  deleteUser(id: number): Observable<string> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete(url, { responseType: 'text' }).pipe(
      catchError(this.handleError)
    );
  }

  updateUserProfile(userProfile: any): Observable<any> {
<<<<<<< HEAD
    const token = localStorage.getItem('your256bitSecretKeyYour256bitSecretKeyvz9ibgzp9bgzoiboivzivgizvzi8vfg8tzft976fctouvtgouvc579cdti8'); 
=======
    return this.http.put(`${this.apiUrl}/profile/{id}`, userProfile).pipe(
      catchError(this.handleError)
    );
  }
>>>>>>> 58bbcd1 (GET, PUT, DELETE, POST, fixed)

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error);
    if (error.status === 401) {
      alert('Invalid credentials!');
    }
    return throwError(() => new Error(error.message || 'An unexpected error occurred.'));
  }
}
