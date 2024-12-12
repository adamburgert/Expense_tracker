
# Expense Tracker Application

## Overview

The Expense Tracker Application is a comprehensive full-stack solution for managing personal expenses. It offers features like expense tracking, category management, user roles, reports, and notifications. The application is built with modern neuromorphic UI design for a sleek and interactive user experience.

---

## Features

- **Frontend**: Angular-based responsive interface using modern neuromorphic UI principles.
- **Backend**: Spring Boot RESTful APIs with comprehensive endpoint mappings.
- **Database**: PostgreSQL for robust and structured data management.
- **Containerization**: Fully Dockerized architecture for deployment convenience.


---

## Prerequisites

- Docker and Docker Compose or Podman installed.
- IntelliJ IDEA for building and running the backend.
- SQL initialization scripts: `schema.sql` and `expense_tracker.sql`.

---

## Quick Start

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd <repository-name>
   ```

2. **Prepare Environment**:
   - Ensure `docker-compose.yml`, `schema.sql`, and `expense_tracker.sql` are in the root directory.

3. **Start Docker Services**:
   ```bash
   docker-compose up --build
   ```

4. **Build and Run Backend**:
   - Open the project in **IntelliJ IDEA**.
   - Ensure the PostgreSQL database `expense_tracker` is started in IntelliJ.
   - Build the project using:
     ```bash
     mvn clean package
     mvn clean install
     ```

5. **Access the Application**:
   - **Frontend**: `http://localhost:4200`
   - **Backend**: `http://localhost:8080`
   - **Database**: PostgreSQL on `localhost:5432`

---

## Database Configuration

- Database Name: `expense_tracker`
- Username: `user`
- Password: `password`

---

## Backend API Endpoints

### Categories (`/api/categories`)
- `POST /api/categories`: Create a new category.
- `GET /api/categories`: Retrieve all categories.
- `GET /api/categories/{id}`: Retrieve a category by ID.
- `GET /api/categories/all`: Retrieve all categories with detailed information.
- `GET /api/categories/total`: Get the total count of categories.
- `GET /api/categories/username/category`: Retrieve categories associated with a username.
- `GET /api/categories/category/username`: Retrieve usernames associated with a category.
- `PUT /api/categories/{id}`: Update a category by ID.
- `DELETE /api/categories/{id}`: Delete a category by ID.
- `GET /api/categories/search`: Search categories by criteria.

### Expenses (`/api/expenses`)
- `POST /api/expenses`: Create a new expense.
- `GET /api/expenses`: Retrieve all expenses.
- `GET /api/expenses/{id}`: Retrieve an expense by ID.
- `GET /api/expenses/all`: Retrieve all expenses with detailed information.
- `GET /api/expenses/total`: Get the total expense amount.
- `GET /api/expenses/user/{userId}`: Retrieve expenses by user ID.
- `GET /api/expenses/category/{categoryId}`: Retrieve expenses by category ID.
- `PUT /api/expenses/{id}`: Update an expense by ID.
- `DELETE /api/expenses/{id}`: Delete an expense by ID.

### Users (`/api/users`)
- `POST /api/users/register`: Register a new user.
- `POST /api/users/login`: Log in a user.
- `POST /api/users/logout`: Log out a user.
- `GET /api/users`: Retrieve all users.
- `GET /api/users/{id}`: Retrieve a user by ID.
- `GET /api/users/username/{username}`: Retrieve a user by username.
- `GET /api/users/email/{email}`: Retrieve a user by email.
- `PUT /api/users/profile`: Update user profile.
- `PUT /api/users/{id}`: Update a user by ID.
- `DELETE /api/users/{id}`: Delete a user by ID.

### Roles (`/api/roles`)
- `GET /api/roles`: Retrieve all roles.
- `GET /api/roles/{name}`: Retrieve a role by name.
- `POST /api/roles/assignRole/{id}/roles/{name}`: Assign a role to a user.
- `PUT /api/roles/{role_id}`: Update a role by ID.
- `DELETE /api/roles/{role_id}`: Delete a role by ID.

### Reports (`/api/reports`)
- `POST /api/reports/generate`: Generate a new report.
- `GET /api/reports/all`: Retrieve all reports.
- `GET /api/reports/latest`: Retrieve the latest report.
- `GET /api/reports/expense-report`: Retrieve the expense report.
- `PUT /api/reports/{report_id}`: Update a report by ID.

### Notifications (`/api/notifications`)
- `GET /api/notifications/getLatestNotifications`: Retrieve the latest notifications.
- Polls every 5 seconds for updates.

---

## Frontend Components

The Angular frontend includes several components for managing different functionalities:

- **`HomeComponent`**: The landing page for the application.
- **`DashboardComponent`**: Overview of expenses in a table format with search by description, amount, or date.
- **`ExpenseListComponent`**: Manage expenses with add, update, delete, and search functionalities.
- **`CategoryListComponent`**: Manage categories with add, update, delete, and search functionalities.
- **`UserProfileComponent`**: Manage and update user details.
- **`LoginComponent`**: Handles user login with JWT token authentication.
- **`RegistrationComponent`**: Handles user registration.
- **`ReportComponent`**: Generate and view detailed expense reports.
- **`NotificationComponent`**: View and manage notifications.
- **`NotificationDialogComponent`**: Dialog for displaying notification details.

---

## Routing

The application uses Angular routing to navigate between components:

```typescript
export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'expenses', component: ExpenseListComponent },
  { path: 'report', component: ReportComponent },
  { path: 'user-profile', component: UserProfileComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegistrationComponent },
  { path: 'notification', component: NotificationComponent },
  { path: 'category', component: CategoryListComponent },
  { path: 'notification-dialog', component: NotificationDialogComponent },
  { path: '**', redirectTo: '/home' }
];
```

---

## Contribution Guidelines

1. Fork the repository.
2. Create a feature branch: `git checkout -b feature/your-feature-name`.
3. Commit your changes: `git commit -m 'Add feature'`.
4. Push the branch: `git push origin feature/your-feature-name`.
5. Submit a pull request.

---

## Contact

For issues or inquiries, contact the project maintainer at [burgertadam@gmail.com].

---
