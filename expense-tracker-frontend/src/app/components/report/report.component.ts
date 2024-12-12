import { Component, OnInit } from '@angular/core';
import { ExpenseService } from '../services/expense.service';
import html2canvas from 'html2canvas';
import * as jsPDF from 'jspdf';
import {CurrencyPipe, DatePipe, NgForOf} from '@angular/common';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  standalone: true,
  imports: [
    CurrencyPipe,
    DatePipe,
    NgForOf
  ],
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {

  reports: any[] = [];
  expenses: any[] = [];
  expenseCategoryBreakdown: any[] = [];
  startDate: string = '';
  endDate: string = '';
  totalPrice: number = 0;
  totalExpenses: number = 0;
  userId: number = 1;

  constructor(private expenseService: ExpenseService) {}

  ngOnInit(): void {
    this.loadData();
  }

  generateDashboardPDF(): void {
    const element = document.getElementById('dashboard');

    if (element) {
      html2canvas(element).then((canvas) => {
        const pdf = new jsPDF.jsPDF();

        const imgData = canvas.toDataURL('image/png');
        pdf.addImage(imgData, 'PNG', 10, 10, 190, 150);

        pdf.save('dashboard_report.pdf');
      });
    }
  }


  loadData(): void {
    this.expenseService.getTotalExpenses(this.userId).subscribe(total => {
      this.totalExpenses = total;
    });

    this.expenseService.getExpenseCategoryBreakdown(this.userId).subscribe(
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

    this.expenseService.getAllExpenses(this.userId).subscribe(expenses => {
      this.expenses = expenses;
      this.calculateTotalPrice();
    });
  }


  calculateTotalPrice(): void {
    this.totalPrice = this.expenses.reduce((sum, expense) => sum + expense.totalPrice, 0);
  }
}
