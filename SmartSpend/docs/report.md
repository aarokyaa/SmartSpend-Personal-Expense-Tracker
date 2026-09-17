# SmartSpend Project Report Content

## 1. Introduction
SmartSpend is an interactive Java desktop application designed to help users record income and expenses, manage monthly budgets, understand spending patterns, detect unusually large expenses, estimate future expenses and generate a readable report.

## 2. Problem Statement
Manual expense tracking makes it difficult to maintain accurate records and understand where money is being spent. SmartSpend provides a single local application for recording transactions and turning those records into useful financial insights.

## 3. Objectives
- Provide simple transaction CRUD.
- Categorize income sources and expenses.
- Persist data using CSV files.
- Provide financial summaries and visual charts.
- Monitor monthly budgets.
- Detect unusual spending using descriptive statistics.
- Predict next-month expenses using simple linear regression.
- Give plain-English recommendations.
- Generate a text report.

## 4. Functional Requirements
FR1 Transaction Management — add, view, search, edit and delete transactions.
FR2 Budget Management — set/update category limits and monitor current-month usage.
FR3 Analytics — calculate income, expenses, balance, category totals and monthly totals.
FR4 Smart Analysis — recommendations and unusual-spending detection.
FR5 Prediction — estimate the next month's expense total when at least two months are available.
FR6 Reporting — generate and save a consolidated text report.

## 5. Non-Functional Requirements
- Usability: dropdowns, tabs, search and clear validation dialogs.
- Performance: calculations operate locally on small/medium CSV datasets.
- Reliability: invalid input is rejected and CSV writes are isolated in repository classes.
- Maintainability: model, service, repository, utility and presentation layers are separated.
- Portability: uses standard Java 21/Swing APIs and does not require a database.
- Privacy: financial records remain local.

## 6. Architecture
User → Swing GUI → Service Layer → Repository Layer → CSV Storage.

Services include TransactionService, BudgetService, AnalyticsService, RecommendationService, AnomalyService and PredictionService.

## 7. Smart Analysis
### Anomaly Detection
For expense records, SmartSpend calculates the mean and standard deviation. An expense above `mean + standard deviation` is flagged as potentially unusual. This is an educational statistical heuristic, not a financial fraud detector.

### Prediction
Monthly expense totals are treated as y values and sequential month indices as x values. Simple linear regression produces an estimate for the following month. At least two different months are required.

### Recommendations
The application explains the balance/savings situation, identifies the largest expense category, warns about high or exceeded budgets, and encourages consistent record keeping.

## 8. Testing
The existing assertion-based tests were compiled and executed after the upgrade. TransactionTest, BudgetTest and AnalyticsTest passed successfully. The application source also compiles successfully with Java 21.

## 9. Screenshots to add before submission
Capture actual screenshots from the running application:
1. Dashboard
2. Add Transaction dialog
3. Transactions/search
4. Budget page
5. Smart Insights
6. Reports

## 10. Future Enhancements
- JavaFX visual theme and richer charts.
- SQLite database for larger datasets.
- User accounts and encrypted storage.
- Export to PDF/Excel.
- More advanced forecasting models.
- Custom categories and recurring transactions.
