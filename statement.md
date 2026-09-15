# SmartSpend — Problem Statement

## Project Title
SmartSpend: Java-Based Personal Expense Tracker & Spending Analyzer

## Student
AAROKYA KUMAR | 25BAI10405 | CSE AI & ML | VIT Bhopal University

---

## Problem Background

Personal financial management is a critical life skill, yet most individuals have limited visibility into their own spending patterns. The consequences of poor expense tracking include unplanned debt, budget overruns, inability to save, and general financial stress.

Existing solutions present a difficult trade-off:

- **Cloud-based finance apps** (Mint, YNAB, Walnut) require internet connectivity, expose sensitive financial data to third-party servers, often charge subscription fees, and may be unavailable in regions with limited connectivity.
- **Spreadsheets** (Excel, Google Sheets) require significant manual effort, offer no automated intelligence, provide no anomaly detection, and cannot predict future spending.
- **Banking apps** show only bank-linked transactions and do not support multi-account, multi-payment-method, or cash tracking.

---

## Problem Statement

> **Design and implement a self-contained desktop application that enables individuals to comprehensively track personal income and expenses, monitor budgets, detect unusual spending patterns, forecast future expenses, and generate financial reports — entirely offline, with zero external dependencies, using the Java ecosystem.**

---

## Core Problems Addressed

1. **No real-time balance awareness** — Users lack instant visibility into their net income vs. expense position.
2. **No budget control per category** — Without per-category limits, overspending in any category goes unnoticed.
3. **No anomaly alerting** — Unusual or fraudulent-pattern transactions are not automatically surfaced.
4. **No financial forecasting** — Users cannot estimate upcoming expenses to plan savings or reduce spending.
5. **No structured reporting** — Creating a financial summary requires manual effort without automated report generation.
6. **Privacy risk with cloud services** — Sensitive spending data is exposed when using internet-based finance tools.

---

## Proposed Solution

SmartSpend addresses all six problems with:

| Problem | SmartSpend Solution |
|---|---|
| No balance awareness | Real-time Dashboard with income/expense/balance totals |
| No budget control | Per-category budget management with live utilization display |
| No anomaly alerting | Statistical anomaly detection using mean ± 2σ rule |
| No forecasting | Simple linear regression predicting next-month total expense |
| No reporting | Automated timestamped financial report generation |
| Privacy concerns | 100% offline; data stored only in local CSV files |

---

## Project Scope

### In Scope
- Full CRUD transaction management (income & expense)
- Category-wise budget setting and monitoring
- Financial analytics and category breakdowns
- Statistical anomaly detection
- Natural-language spending recommendations
- Linear regression expense prediction
- Financial report generation and storage
- Safe data management with confirmation flows
- Comprehensive automated test suite

### Out of Scope
- Cloud synchronization
- User authentication / multi-user support
- Mobile or web interface
- Real-time bank API integration
- Advanced machine learning models

---

## Technical Approach

- **Language:** Java 21 — platform-independent, OOP-native, industry-standard
- **GUI:** Java Swing — bundled with JDK, zero additional dependencies
- **Storage:** CSV files — portable, human-readable, dependency-free
- **Architecture:** 4-layer (GUI → Service → Repository → Storage)
- **Intelligence:** Statistical methods (mean/σ) and simple linear regression, implemented from scratch

---

## Expected Outcomes

1. A fully functional offline personal expense tracking desktop application.
2. Demonstrated mastery of Java OOP, GUI development, file I/O, and software architecture.
3. Practical implementation of statistical analysis and predictive modelling without external libraries.
4. A professional, well-documented, tested, and organized Java project suitable for portfolio submission.

---

*VITyarthi Build Your Own Project | AAROKYA KUMAR | 25BAI10405*
