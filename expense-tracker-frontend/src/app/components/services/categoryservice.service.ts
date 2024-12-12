import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, throwError, BehaviorSubject, map} from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

export interface Category {
  id: number;
  name: string;
  date: string;
  price: number;
  amount: number;
  description?: string;
  totalPrice?: number;
  created_at: string;

}



@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private apiUrl = 'http://localhost:8080/api/categories';

  private categoriesSubject = new BehaviorSubject<Category[]>([]);
  categories$ = this.categoriesSubject.asObservable();

  constructor(private http: HttpClient) {}

  getAllCategories(id: number): Observable<Category[]> {
    return this.http.get<{ data: Category[] }>(`${this.apiUrl}`).pipe(
      tap((response) => {
        const categories = response.data || [];
        categories.forEach((category) => {
          if (!category.description) {
            category.description = 'No description available';
          }
        });
        this.categoriesSubject.next(categories);
      }),
      map((response) => response.data || [])
    );
  }

  getTotalPrice(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/total`).pipe(
      catchError(this.handleError)
    );
  }

  searchCategories(
    description: string,
  ): Observable<Category[]> {
    let params = new HttpParams();

    if (description) {
      params = params.set('description', description);
    }

    return this.http.get<Category[]>(`${this.apiUrl}/search`, { params });
  }



  createCategory(category: Category): Observable<Category> {
    return this.http.post<Category>(`${this.apiUrl}`, category).pipe(
      catchError(this.handleError)
    );
  }

  updateCategory(id: number, category: Category): Observable<Category> {
    return this.http.put<Category>(`${this.apiUrl}/${id}`, category).pipe(
      catchError(this.handleError)
    );
  }

  deleteCategory(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: any) {
    console.error('An error occurred:', error);
    let errorMessage = 'Unknown error occurred!';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      errorMessage = `Server returned code: ${error.status}, error message is: ${error.message}`;
    }
    return throwError(() => new Error(errorMessage));
  }

  getTotalCategories(userId: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/total`).pipe(
      catchError(this.handleError)
    );
  }

  getCategoryBreakdown(userId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/category-breakdown`).pipe(
      catchError(this.handleError)
    );
  }
}
