# SmartSpend — Interactive Java Personal Finance Assistant

SmartSpend is a Java 21 desktop application for recording income/expenses, managing monthly budgets, visualizing spending, detecting unusually large expenses, predicting next-month expenses, and generating reports.

## What changed
- Modern Java Swing GUI instead of a console-only menu.
- Dashboard with income, expenses, balance and current-month spending cards.
- Spending-by-category and monthly-spending charts with no external chart library.
- Searchable transaction table with add/edit/delete and double-click editing.
- Dropdown-based categories, income sources and payment methods.
- Input validation with clear error dialogs.
- Monthly budget progress bars and over-budget warnings.
- Smart Insights page with plain-English recommendations.
- Unusual-spending detection using mean + standard deviation.
- Next-month expense prediction using simple linear regression.
- CSV persistence retained, with safer quoted CSV fields.
- Correct calendar-date validation.
- Text report generation retained.
- Existing assertion-based tests remain compatible.

## Run in VS Code / PowerShell
```powershell
Remove-Item -Recurse -Force out -ErrorAction SilentlyContinue
New-Item -ItemType Directory out | Out-Null
javac -encoding UTF-8 -d out src\Main.java src\model\*.java src\repository\*.java src\service\*.java src\util\*.java src\report\*.java
java -cp out Main
```

Or run:
```powershell
.\run.ps1
```

## Test
```powershell
.\test.ps1
```

## Main modules
1. Transaction Management (CRUD)
2. Budget Management
3. Financial Analytics & Visualization
4. Smart Recommendations
5. Unusual Spending / Anomaly Detection
6. Expense Prediction
7. Report Generation
8. CSV Data Persistence

## Project structure
```text
SmartSpend/
├── src/
│   ├── Main.java                 # GUI / presentation layer
│   ├── model/                    # Transaction, Budget, Category
│   ├── repository/               # CSV persistence
│   ├── service/                  # Business logic + analytics
│   ├── util/                     # Validation and CSV utilities
│   └── report/                   # Report generation
├── tests/                        # Assertion-based tests
├── data/                         # CSV storage and generated reports
└── docs/                         # Architecture, workflow, UML and report material
```

## Data and privacy
The application stores data locally in `data/transactions.csv` and `data/budgets.csv`. No online account or network service is required.

## Academic concepts demonstrated
- Java OOP and modular design
- CRUD operations
- File handling and persistence
- Collections and streams
- Data aggregation
- Descriptive statistics
- Anomaly detection
- Simple linear regression
- Input validation and error handling
- Testing and Git-ready project organization

## Submission
Run the application yourself and capture screenshots of the Dashboard, Add Transaction form, Transactions table, Budget page, Smart Insights and Reports page. Add them to `docs/screenshots/` and the final report before submission.
