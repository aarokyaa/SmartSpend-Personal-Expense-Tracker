# SmartSpend Architecture

```text
User
  ↓
Java Swing GUI (Main.java)
  ↓
Service Layer
  ├─ TransactionService
  ├─ BudgetService
  ├─ AnalyticsService
  ├─ RecommendationService
  ├─ AnomalyService
  └─ PredictionService
  ↓
Repository Layer
  ├─ TransactionRepository
  └─ BudgetRepository
  ↓
CSV files in data/
```

The GUI is responsible for presentation and user interaction. Services contain business rules and calculations. Repositories isolate CSV persistence. This separation keeps the project modular and easier to test and maintain.
