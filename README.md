CoinTrack Android App

Use Case: Create an android app for the same. Use creative ideas as to how to make this app user friendly and helpful to client and his employees assuming the client has 5 employees.

5 Employees defined in the application:
{"Kaiwan", "Kai123"},
{"Anushree", "Anu456"},
{"Ramesh", "Ram789"},
{"Saswati", "Sasabc"},
{"Adi", "adi888"}

Admin:
{“Admin”,”Admin123”}

Preferrd Emulator: Pixel 7 Pro API 30

This application contains a login page in the beginning where employee and admin login takes place. If logged in by one of the employees, the employee dashboard activity is started and there are tabs created using CardView: Add Expense, View Expense, Logout, Contact Us.The name of the employee is visible on dashboard as "Welcome, employee_name!".

This application uses SQLite for storing data as a local database. The databse 'exp.db' contains a table 'EXPENSE' with columns: Employee name(Varchar(50)), Expense name(Varchar(50)), Expense(Float).
functions defined are insert data, remove data when clicked on 'X', showing the expense list using list view, displaying total expense. (In actual scenario, firebase can be used as a remote database)

Employees can add their expense that need to be reimbursed by the company.
They can view and edit the expenses in the view expenses tab.
The logout button takes the employee to the login page again.
In contact Us activity, there are email and phone number links which open into corresponding apps.

If logged in by the Admin, the Admin activity opens up which has a spinner and a listView. the spinner is for selecting the employees/all employees. 
The admin can remove entries if expense is reimbursed to the employee.

apk file attached in the repository as "Kaiwan_Expense_Tracker.apk".
pdf file with screenshots attached in this repository.
