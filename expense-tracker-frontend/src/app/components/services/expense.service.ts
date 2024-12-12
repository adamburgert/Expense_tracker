import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Observable, throwError, BehaviorSubject } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

export interface Expense {
  id: number;
  name: string;
  amount: number;
  date: string;
  price: number;
  description?: string;
  totalPrice?: number;
  created_at: string;

}



@Injectable({
  providedIn: 'root',
})
export class ExpenseService {
  private apiUrl = 'http://localhost:8080/api/expenses';

  private expensesSubject = new BehaviorSubject<Expense[]>([]);
  expenses$ = this.expensesSubject.asObservable();

  constructor(private http: HttpClient) {}

  getAllExpenses(id: number): Observable<Expense[]> {
    return this.http.get<Expense[]>(`${this.apiUrl}`).pipe(
      tap((expenses) => {
        expenses.forEach((expense) => {
          if (!expense.description) {
            expense.description = 'No description available';
          }
        });
        this.expensesSubject.next(expenses);
      })
    );
  }

  getTotalPrice(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/total`).pipe(
      catchError(this.handleError)
    );
  }

  searchExpenses(
    description: string,
    amount: number | null,
    price: number | null
  ): Observable<Expense[]> {
    let params = new HttpParams();

    if (description) {
      params = params.set('description', description);
    }
    if (amount != null) {
      params = params.set('amount', amount.toString());
    }
    if (price != null) {
      params = params.set('price', price.toString());
    }

    return this.http.get<Expense[]>(`${this.apiUrl}/search`, { params });
  }



  createExpense(expense: Expense): Observable<Expense> {
    return this.http.post<Expense>(`${this.apiUrl}`, expense).pipe(
      catchError(this.handleError)
    );
  }

  updateExpense(id: number, expense: Expense): Observable<Expense> {
    return this.http.put<Expense>(`${this.apiUrl}/${id}`, expense).pipe(
      catchError(this.handleError)
    );
  }

  deleteExpense(id: number): Observable<void> {
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

  getTotalExpenses(id: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/total`).pipe(
      catchError(this.handleError)
    );
  }

  getExpenseCategoryBreakdown(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/category-breakdown`).pipe(
      catchError(this.handleError)
    );
  }
}
