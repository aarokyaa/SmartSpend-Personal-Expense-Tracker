# 💰 SmartSpend

> **Java-Based Personal Expense Tracker & Spending Analyzer**  
> VITyarthi — Build Your Own Project | AAROKYA KUMAR | 25BAI10405 | CSE AI & ML

---

## 📋 Project Description

SmartSpend is a fully offline desktop personal-finance application built in **Java 21** using the **Java Swing** GUI framework. It enables users to record, categorize, and intelligently analyze income and expenses — with budget tracking, anomaly detection, predictive modelling, and automated report generation.

All data is persisted locally in lightweight **CSV files**. No internet connection, cloud account, or database installation is required.

---

## ✨ Features

| Feature | Description |
|---|---|
| 📊 Dashboard | Real-time financial summary: income, expenses, balance |
| 💳 Transaction Management | Full CRUD — Add, Edit, Delete, View, Search transactions |
| 🎯 Budget Tracking | Set per-category monthly limits; real-time vs-budget comparison |
| 📈 Financial Analytics | Category breakdown, monthly trends, totals |
| 🧠 Anomaly Detection | Flags unusual spending using mean ± 2σ statistical rule |
| 💡 Smart Recommendations | Actionable recommendations based on budget utilization |
| 🔮 Expense Prediction | Next-month forecast using simple linear regression |
| 📄 Report Generation | Timestamped financial reports saved to data/reports/ |
| 🗑️ Data Management | Safe reset operations with confirmation dialogs |

---

## 🛠️ Technology Stack

- **Language:** Java 21
- **GUI:** Java Swing (JFrame, JTabbedPane, JTable, JPanel)
- **Persistence:** CSV flat files via custom CSVUtil
- **Analytics:** Descriptive statistics (mean, std. deviation)
- **Prediction:** Simple linear regression (least-squares, from scratch)
- **Architecture:** 4-layer — GUI / Service / Repository / Storage
- **Testing:** Java unit tests in tests/ directory
- **Build:** PowerShell scripts (run.ps1, test.ps1)

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────┐
│          SmartSpend GUI (Java Swing)         │
│   Dashboard · Transactions · Budgets ·      │
│   Smart Insights · Reports · Data Mgmt      │
└──────────────────┬──────────────────────────┘
                   │
┌──────────────────▼──────────────────────────┐
│           Service / Business Layer           │
│  TransactionService  │  BudgetService        │
│  AnalyticsService    │  PredictionService    │
│  AnomalyService      │  RecommendationSvc    │
│  ReportGenerator                             │
└──────────────────┬──────────────────────────┘
                   │
┌──────────────────▼──────────────────────────┐
│            Repository Layer                  │
│  TransactionRepository  │  BudgetRepository  │
│       CSVUtil  │  InputValidator              │
└──────────────────┬──────────────────────────┘
                   │
┌──────────────────▼──────────────────────────┐
│              CSV Storage                     │
│  transactions.csv  │  budgets.csv            │
│  categories.csv    │  data/reports/          │
└─────────────────────────────────────────────┘
```

---

## 📁 Project Structure

```
SmartSpend/
├── README.md              ← This file
├── statement.md           ← Problem statement
├── run.ps1                ← Compile & run script
├── test.ps1               ← Compile & test script
├── src/
│   ├── Main.java          ← Entry point + GUI
│   ├── model/             ← Transaction, Budget, Category
│   ├── repository/        ← TransactionRepository, BudgetRepository
│   ├── service/           ← All business logic services
│   ├── util/              ← CSVUtil, InputValidator
│   └── report/            ← ReportGenerator
├── tests/                 ← Unit test files
├── data/
│   ├── transactions.csv
│   ├── budgets.csv
│   ├── categories.csv
│   └── reports/           ← Generated reports saved here
└── docs/
    ├── architecture.md
    ├── report.md
    ├── uml.md
    ├── workflow.md
    ├── screenshots/       ← Application screenshots
    └── diagrams/          ← Generated UML/architecture diagrams
```

---

## 🚀 How to Run

### Prerequisites
- Java 21 JDK installed (Oracle JDK or OpenJDK)
- PowerShell (Windows) or Bash equivalent

### Run the Application

```powershell
.\run.ps1
```

This script compiles all Java source files and launches SmartSpend.

### Manual Compile & Run

```bash
# Compile
javac -d out src/*.java src/model/*.java src/repository/*.java src/service/*.java src/util/*.java src/report/*.java

# Run
java -cp out Main
```

---

## 🧪 How to Test

```powershell
.\test.ps1
```

This script compiles and runs all tests in the `tests/` directory.

---

## 💾 Data Storage

| File | Purpose |
|---|---|
| `data/transactions.csv` | All income and expense records |
| `data/budgets.csv` | Per-category monthly budget limits |
| `data/categories.csv` | Predefined spending categories |
| `data/reports/` | Auto-generated financial report text files |

**transactions.csv format:** `id,amount,type,category,date,paymentMethod,description`

---

## 🧠 Smart Analysis

### Anomaly Detection
Uses the **empirical rule (mean ± 2σ)**. Transactions with amount > μ + 2σ within their category are flagged as unusual.

### Expense Prediction
**Simple linear regression** fitted on historical monthly expense totals. Extrapolates to next month using least-squares formula.

### Recommendations
Analyses budget utilization per category and generates natural-language spending recommendations.

---

## 📸 Screenshots

See `docs/screenshots/` for all application screenshots:

- `01_Dashboard.png` — Financial summary dashboard
- `02_Transactions.png` — Transaction management panel
- `03_Add_Budget.png` — Budget entry dialog
- `04_Budgets.png` — Budget monitoring panel
- `05_Smart_Insights.png` — Anomaly detection & predictions
- `06_Reports.png` — Report generation
- `07_Data_Management.png` — Data reset controls
- `08_Testing.png` — Test suite execution
- `09_Project_Structure1.png` — Project structure (part 1)
- `09_Project_Structure2.png` — Project structure (part 2)

---

## 🔭 Future Scope

- SQLite/MySQL database migration
- User authentication & encryption
- JavaFX charts integration
- Cloud backup support
- Mobile/web version
- PDF export

---

## 👤 Student Information

| Field | Value |
|---|---|
| Name | AAROKYA KUMAR |
| Registration No. | 25BAI10405 |
| Branch | CSE — Artificial Intelligence & Machine Learning |
| Institution | VIT Bhopal University |
| Project | VITyarthi Build Your Own Project |

---

## 📦 Version Control (GitHub)

This project follows standard Git version control practices:

```bash
# Initialize repository
git init
git add .
git commit -m "Initial commit: SmartSpend v1.0"

# Push to GitHub
git remote add origin https://github.com/aarokyaa/SmartSpend.git
git push -u origin main
```

Recommended `.gitignore`:
```
out/
*.class
data/reports/
```

---

*SmartSpend — Built with Java 21 · Swing · CSV · Statistical Analysis*
