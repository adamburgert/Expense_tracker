import { Component, OnInit } from '@angular/core';
import { Category, CategoryService } from '../services/categoryservice.service';
import { HttpErrorResponse } from '@angular/common/http';
import {CurrencyPipe, NgForOf, NgIf} from '@angular/common';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  standalone: true,
  imports: [
    CurrencyPipe,
    FormsModule,
    NgForOf,
    NgIf
  ],
  styleUrls: ['./category-list.component.css']
})

export class CategoryListComponent implements OnInit {
  category: Category = {date: '', id: 0, amount: 0, name: '', description: '', price: 0, created_at: ''};
  isEditing: boolean = false;
  id: number = 1;
  searchTerm: string = '';
  amount: number | null = null;
  price: number | null = null;
  userId: number = 1;
  totalPrice: number | undefined;
  categories: any[] = [];

  constructor(private categoryService: CategoryService) {
  }

  ngOnInit(): void {

    this.loadCategories();
  }

  loadCategories(): void {
    this.categoryService.getAllCategories(this.id).subscribe(
      (categories: Category[]) => {
        this.categories = categories;
      },
      (error: HttpErrorResponse) => {
        console.error('Error fetching categories:', error);
      }
    );
  }

  onSearch(): void {
    this.categoryService.searchCategories(this.searchTerm).subscribe(
      (expenses: any[]) => {
        this.categories = expenses;
      },
      (error: HttpErrorResponse) => {
        console.error('Error searching expenses:', error);
      }
    );
  }

  saveCategory(): void {
    if (this.isEditing) {
      this.updateCategory();
    } else {
      this.addCategory();
    }
  }

  // Handle Add Category (POST request)
  addCategory(): void {
    this.categoryService.createCategory(this.category).subscribe(
      (newCategory: Category) => {
        console.log('Category added:', newCategory);
        this.categories.push(newCategory);
        this.resetForm();
      },
      (error: HttpErrorResponse) => {
        console.error('Error adding category:', error);
      }
    );
  }

  // Handle Update Category (PUT request)
  updateCategory(): void {
    this.categoryService.updateCategory(this.category.id, this.category).subscribe(
      (updatedCategory: Category) => {
        console.log('Category updated:', updatedCategory);
        const index = this.categories.findIndex((e) => e.id === updatedCategory.id);
        if (index !== -1) {
          this.categories[index] = updatedCategory;
        }
        this.resetForm();
      },
      (error: HttpErrorResponse) => {
        console.error('Error updating category:', error);
      }
    );
  }

  // Handle Delete Category (DELETE request)
  deleteCategory(): void {
    this.categoryService.deleteCategory(this.category.id).subscribe(
      () => {
        console.log('Category deleted');
        this.categories = this.categories.filter((category) => category.id !== this.category.id);
        this.resetForm();
      },
      (error: HttpErrorResponse) => {
        console.error('Error deleting category:', error);
      }
    );
  }

  editCategory(category: Category): void {
    this.category = {...category};
    this.isEditing = true;
  }

  resetForm(): void {
    this.category = {date: '', amount: 0, totalPrice: 0, id: 0, name: '', description: '', price: 0, created_at: ''};
    this.isEditing = false;
  }

}
