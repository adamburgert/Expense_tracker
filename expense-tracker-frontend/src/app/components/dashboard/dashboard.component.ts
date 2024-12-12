import { Component, OnInit } from '@angular/core';
import { ExpenseService } from '../services/expense.service';
import { CurrencyPipe, DatePipe, NgForOf, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    CurrencyPipe,
    DatePipe,
    FormsModule
  ],
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  totalExpenses: number | undefined;
  totalPrice: number | undefined;
  expenseCategoryBreakdown: { name: string; total: number }[] = [];
  expenses: any[] = [];
  searchTerm: string = '';
  amount: number | null = null;
  price: number | null = null;
  id: number = 0;

  constructor(private expenseService: ExpenseService) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.expenseService.getTotalExpenses(this.id).subscribe(total => {
      this.totalExpenses = total;
    });

    this.expenseService.getExpenseCategoryBreakdown(this.id).subscribe(
      (data: any) => {
        console.log(data);
        if (Array.isArray(data)) {
          this.expenseCategoryBreakdown = data;
        } else {
          this.expenseCategoryBreakdown = [];
        }
      },
      (error) => {
        console.error('Error fetching category breakdown:', error);
        this.expenseCategoryBreakdown = [];
      }
    );

    this.expenseService.getAllExpenses(this.id).subscribe(data => {
      this.expenses = data;
    });
  }

  onSearch(): void {
    this.expenseService.searchExpenses(this.searchTerm, this.amount, this.price).subscribe(
      (expenses: any[]) => {
        this.expenses = expenses;
      },
      (error: HttpErrorResponse) => {
        console.error('Error searching expenses:', error);
      }
    );
  }
}
