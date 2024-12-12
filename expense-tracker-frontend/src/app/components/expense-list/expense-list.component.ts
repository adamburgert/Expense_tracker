import { Component, OnInit } from '@angular/core';
import { ExpenseService, Expense } from '../services/expense.service';
import { FormsModule } from '@angular/forms';
import { CurrencyPipe, NgForOf, NgIf } from '@angular/common';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-expense-list',
  templateUrl: './expense-list.component.html',
  standalone: true,
  imports: [FormsModule, CurrencyPipe, NgIf, NgForOf],
  styleUrls: ['./expense-list.component.css']
})
export class ExpenseListComponent implements OnInit {
  expense: Expense = { id: 0, name: '', amount: 0, date: '', description: '' , price: 0, totalPrice: 0, created_at: '' };
  expenses: Expense[] = [];
  isEditing = false;
  userId: number = 1;
  protected totalPrice: any;


  constructor(private expenseService: ExpenseService) {}

  ngOnInit(): void {
    this.loadExpenses();
  }

  loadExpenses(): void {
    this.expenseService.getAllExpenses(this.userId).subscribe(
      (expenses: Expense[]) => {
        this.expenses = expenses;
      },
      (error: any) => {
        console.error('Error fetching expenses:', error);
      }
    );
  }

  saveExpense(): void {
    if (this.isEditing) {
      this.updateExpense();
    } else {
      this.addExpense();
    }
  }

  addExpense(): void {
    this.expenseService.createExpense(this.expense).subscribe(
      (newExpense: Expense) => {
        console.log('Expense added:', newExpense);
        this.expenses.push(newExpense);
        this.resetForm();
      },
      (error: any) => {
        console.error('Error adding expense:', error);
      }
    );
  }

  updateExpense(): void {
    this.expenseService.updateExpense(this.expense.id, this.expense).subscribe(
      (updatedExpense: { id: any; }) => {
        console.log('Expense updated:', updatedExpense);
        const index = this.expenses.findIndex((e) => e.id === updatedExpense.id);
        if (index !== -1) {
          this.expenses[index] = <Expense>updatedExpense;
        }
        this.resetForm();
      },
      (error: any) => {
        console.error('Error updating expense:', error);
      }
    );
  }

  // Handle Delete Expense (DELETE request)
  deleteExpense(): void {
    this.expenseService.deleteExpense(this.expense.id).subscribe(
      () => {
        console.log('Expense deleted');
        this.expenses = this.expenses.filter((expense) => expense.id !== this.expense.id);
        this.resetForm();
      },
      (error: any) => {
        console.error('Error deleting expense:', error);
      }
    );
  }

  editExpense(expense: Expense): void {
    this.expense = { ...expense };
    this.isEditing = true;
  }

  resetForm(): void {
    this.expense = { id: 0, name: '', amount: 0, date: '', description: '', price: 0, totalPrice: 0, created_at: ''};
    this.isEditing = false;
  }

  getTotalPrice(): void {
    this.expenseService.getTotalPrice().subscribe(
      (totalPrice) => {
        this.totalPrice = totalPrice;
      },
      (error: HttpErrorResponse) => {
        console.error('Error fetching total price:', error);
      }
    );
  }

}
