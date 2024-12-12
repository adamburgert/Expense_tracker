import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Router} from '@angular/router';
import {catchError, tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users'

  constructor(private http: HttpClient, private router: Router) {}

  getUserProfile(): Observable<any> {
    return this.http.get(`${this.apiUrl}/profile/`, { withCredentials: true });
  }

  login(credentials: { username: string; name: string; email: string; password: string }): Observable<any> {
    const url = `${this.apiUrl}/login`;
    return this.http.post(url, credentials).pipe(
      tap((response: any) => {
        if (response && response.token) {
          localStorage.setItem('***REMOVED***', response.token);
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

  private handleError(error: any): Observable<never> {
    console.error('An error occurred', error);
    if (error.status === 401) {
      alert('Invalid credentials!');
    }
    throw new Error(error.message || error);
  }





  deleteUser(id: number): Observable<string> {
    const url = `${this.apiUrl}/${id}`;

    return this.http.delete(url, { responseType: 'text' }).pipe(
      catchError(this.handleError)
    );
  }



  updateUserProfile(userProfile: any): Observable<any> {
    const token = localStorage.getItem('***REMOVED***'); // Replace 'accessToken' with your actual key name

    const headers = new HttpHeaders({
      'Authorization': token ? `Bearer ${token}` : ''
    });

    return this.http.put(`${this.apiUrl}/{id}`, userProfile, { headers });
  }
}
