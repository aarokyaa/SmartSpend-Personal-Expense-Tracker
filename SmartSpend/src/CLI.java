import model.Budget;
import model.Transaction;
import report.ReportGenerator;
import repository.BudgetRepository;
import repository.TransactionRepository;
import service.AnalyticsService;
import service.AnomalyService;
import service.BudgetService;
import service.PredictionService;
import service.RecommendationService;
import service.TransactionService;
import java.util.*;

/** Command-line interface for SmartSpend. Shares the same services and CSV storage as the GUI. */
public class CLI {
    private final Scanner sc = new Scanner(System.in);
    private final TransactionService transactions = new TransactionService(new TransactionRepository());
    private final BudgetService budgets = new BudgetService(new BudgetRepository());
    private final AnalyticsService analytics = new AnalyticsService();
    private final PredictionService prediction = new PredictionService();
    private final AnomalyService anomaly = new AnomalyService();
    private final RecommendationService recommendations = new RecommendationService();
    private final ReportGenerator reports = new ReportGenerator();

    public void start() {
        System.out.println("\n===============================================");
        System.out.println("             SMARTSPEND CLI MODE");
        System.out.println("   Personal Expense Tracker & Analyzer");
        System.out.println("===============================================");
        boolean running = true;
        while (running) {
            printMenu();
            String choice = prompt("Enter your choice");
            try {
                switch (choice) {
                    case "1" -> addTransaction();
                    case "2" -> listTransactions();
                    case "3" -> searchTransactions();
                    case "4" -> editTransaction();
                    case "5" -> deleteTransaction();
                    case "6" -> manageBudgets();
                    case "7" -> showSummary();
                    case "8" -> showInsights();
                    case "9" -> generateReport();
                    case "10" -> dataManagement();
                    case "0" -> running = false;
                    default -> System.out.println("Invalid choice. Please enter a number from 0 to 10.");
                }
            } catch (Exception e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
            if (running) pause();
        }
        System.out.println("\nThank you for using SmartSpend. Goodbye!");
    }

    private void printMenu() {
        System.out.println("\n--------------- MAIN MENU ----------------");
        System.out.println("1. Add Transaction");
        System.out.println("2. View Transactions");
        System.out.println("3. Search Transactions");
        System.out.println("4. Edit Transaction");
        System.out.println("5. Delete Transaction");
        System.out.println("6. Manage Budgets");
        System.out.println("7. Financial Summary");
        System.out.println("8. Smart Insights");
        System.out.println("9. Generate Report");
        System.out.println("10. Data Management");
        System.out.println("0. Exit");
        System.out.println("--------------------------------------------");
    }

    private void addTransaction() {
        System.out.println("\n--- Add Transaction ---");
        String type = prompt("Type (INCOME/EXPENSE)");
        double amount = promptDouble("Amount");
        String category = prompt("Category");
        String date = prompt("Date (YYYY-MM-DD)");
        String method = prompt("Payment method");
        String description = prompt("Description (optional)");
        Transaction t = transactions.add(amount, type, category, date, method, description);
        System.out.println("✓ Transaction added successfully. ID: " + t.getId());
    }

    private void listTransactions() {
        System.out.println("\n--- Transactions ---");
        List<Transaction> list = transactions.getAll();
        if (list.isEmpty()) { System.out.println("No transactions found."); return; }
        System.out.printf("%-4s %-9s %-12s %-16s %-12s %-12s %s%n", "ID", "Type", "Amount", "Category", "Date", "Method", "Description");
        System.out.println("-".repeat(100));
        for (Transaction t : list) {
            System.out.printf("%-4d %-9s ₹%-11.2f %-16s %-12s %-12s %s%n", t.getId(), t.getType(), t.getAmount(), t.getCategory(), t.getDate(), t.getPaymentMethod(), t.getDescription());
        }
        System.out.println("Total records: " + list.size());
    }

    private void searchTransactions() {
        System.out.println("\n--- Search Transactions ---");
        String q = prompt("Search by category, type, date, payment method, or description").toLowerCase(Locale.ROOT);
        List<Transaction> matches = new ArrayList<>();
        for (Transaction t : transactions.getAll()) {
            String text = (t.getCategory()+" "+t.getType()+" "+t.getDate()+" "+t.getPaymentMethod()+" "+t.getDescription()).toLowerCase(Locale.ROOT);
            if (text.contains(q)) matches.add(t);
        }
        if (matches.isEmpty()) { System.out.println("No matching transactions found."); return; }
        for (Transaction t : matches) System.out.println(t);
        System.out.println("Matches: " + matches.size());
    }

    private void editTransaction() {
        System.out.println("\n--- Edit Transaction ---");
        int id = promptInt("Transaction ID");
        Transaction old = transactions.findById(id);
        if (old == null) { System.out.println("Transaction not found."); return; }
        System.out.println("Current: " + old);
        double amount = promptDouble("New amount");
        String type = prompt("New type (INCOME/EXPENSE)");
        String category = prompt("New category");
        String date = prompt("New date (YYYY-MM-DD)");
        String method = prompt("New payment method");
        String description = prompt("New description");
        if (transactions.update(id, amount, type, category, date, method, description)) System.out.println("✓ Transaction updated successfully.");
    }

    private void deleteTransaction() {
        System.out.println("\n--- Delete Transaction ---");
        int id = promptInt("Transaction ID");
        Transaction t = transactions.findById(id);
        if (t == null) { System.out.println("Transaction not found."); return; }
        System.out.println(t);
        if (confirm("Delete this transaction?")) System.out.println(transactions.delete(id) ? "✓ Transaction deleted." : "Transaction could not be deleted.");
        else System.out.println("Delete cancelled.");
    }

    private void manageBudgets() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Budget Management ---");
            System.out.println("1. View Budgets");
            System.out.println("2. Add / Update Budget");
            System.out.println("0. Back");
            String choice = prompt("Choice");
            switch (choice) {
                case "1" -> viewBudgets();
                case "2" -> setBudget();
                case "0" -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void viewBudgets() {
        List<Budget> bs = budgets.getBudgets();
        if (bs.isEmpty()) { System.out.println("No budgets configured."); return; }
        System.out.println("\nCurrent-month budget status:");
        for (Budget b : bs) System.out.println("• " + budgets.budgetStatus(b, transactions.getAll()));
    }

    private void setBudget() {
        String category = prompt("Category");
        double limit = promptDouble("Monthly limit");
        budgets.setBudget(category, limit);
        System.out.println("✓ Budget saved successfully.");
    }

    private void showSummary() {
        System.out.println("\n--- Financial Summary ---");
        System.out.println(analytics.dashboard(transactions.getAll()));
        System.out.println("\nCategory-wise expenses:");
        analytics.categoryExpenses(transactions.getAll()).forEach((k,v) -> System.out.printf("%-20s ₹%.2f%n", k, v));
        System.out.println("\nMonthly expenses:");
        analytics.monthlyExpenses(transactions.getAll()).forEach((k,v) -> System.out.printf("%-10s ₹%.2f%n", k, v));
    }

    private void showInsights() {
        System.out.println("\n--- Smart Insights ---");
        List<Transaction> expenses = transactions.getAll().stream().filter(t -> t.getType().equalsIgnoreCase("EXPENSE")).toList();
        List<Transaction> unusual = anomaly.findUnusual(expenses);
        System.out.println("Unusual transactions: " + unusual.size());
        for (Transaction t : unusual) System.out.println("  ⚠ " + t);
        System.out.println("\nRecommendations:");
        for (String tip : recommendations.generate(transactions.getAll(), budgets.getBudgets())) System.out.println("• " + tip);
        Map<String, Double> monthly = analytics.monthlyExpenses(transactions.getAll());
        if (monthly.size() >= 2) System.out.printf("\nPredicted next-month expense: ₹%.2f%n", prediction.predictFromMonthlyMap(monthly));
        else System.out.println("\nExpense prediction requires at least two months of expense data.");
    }

    private void generateReport() {
        System.out.println("\n--- Report Generation ---");
        String path = reports.saveReport(transactions.getAll(), budgets.getBudgets(), budgets);
        System.out.println("✓ Report generated successfully: " + path);
    }

    private void dataManagement() {
        System.out.println("\n--- Data Management ---");
        System.out.println("1. Clear Transactions");
        System.out.println("2. Reset Transactions + Budgets");
        System.out.println("0. Back");
        String choice = prompt("Choice");
        switch (choice) {
            case "1" -> { if (confirm("Clear all transaction records?")) { transactions.reset(); System.out.println("✓ Transactions cleared. Categories and reports are preserved."); } }
            case "2" -> { if (confirm("Reset ALL transactions and budgets?")) { transactions.reset(); budgets.reset(); System.out.println("✓ Transactions and budgets reset. Categories and reports are preserved."); } }
            case "0" -> { }
            default -> System.out.println("Invalid choice.");
        }
    }

    private String prompt(String label) { System.out.print(label + ": "); return sc.nextLine().trim(); }
    private double promptDouble(String label) {
        while (true) { try { return Double.parseDouble(prompt(label)); } catch (NumberFormatException e) { System.out.println("Please enter a valid number."); } }
    }
    private int promptInt(String label) {
        while (true) { try { return Integer.parseInt(prompt(label)); } catch (NumberFormatException e) { System.out.println("Please enter a valid integer."); } }
    }
    private boolean confirm(String label) {
        while (true) { String x = prompt(label + " (Y/N)"); if (x.equalsIgnoreCase("y")) return true; if (x.equalsIgnoreCase("n")) return false; System.out.println("Please enter Y or N."); }
    }
    private void pause() { System.out.println("\nPress Enter to continue..."); sc.nextLine(); }
}
