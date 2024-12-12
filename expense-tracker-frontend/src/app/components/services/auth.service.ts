import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import {catchError, tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient, private router: Router) {
  }

  //
  login(credentials: { username: string; name: string; email: string; password: string }): Observable<any> {
    const url = `${this.apiUrl}/login`;
    return this.http.post(url, credentials);
  }

  logout(): Observable<any> {
    const url = `${this.apiUrl}/logout`;
    return this.http.post(url, {}).pipe(
      tap(() => {
        this.router.navigate(['/home']);
      })
    );
  }

  register(user: { username: string; name: string; email: string; password: string }): Observable<any> {
    const url = `${this.apiUrl}/register`;
    return this.http.post(url, user).pipe(
      tap((response: any) => {
        if (response && response.token) {
          localStorage.setItem('***REMOVED***', response.token);
        }
      }),
      catchError(this.handleError)
    );
  }


  private handleError(error: any): Observable<never> {
    console.error('An error occurred', error);
    if (error.status === 400) {
      alert('Registration failed: ' + error.error.message);
    }
    throw new Error(error.message || error);
  }


}
