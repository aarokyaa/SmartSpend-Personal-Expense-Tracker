package service;

import model.Budget;
import model.Transaction;
import java.util.*;

public class RecommendationService {
    private final AnalyticsService analytics = new AnalyticsService();

    public List<String> generate(List<Transaction> ts, List<Budget> budgets) {
        List<String> tips = new ArrayList<>();
        double income = analytics.totalIncome(ts);
        double expense = analytics.totalExpense(ts);
        if (income <= 0) tips.add("Add at least one income transaction so SmartSpend can measure your savings rate.");
        else {
            double rate = (income-expense)/income*100.0;
            if(rate < 0) tips.add("You are spending more than your recorded income. Review large expenses and budgets.");
            else if(rate < 20) tips.add("Your savings rate is below 20%. Try setting a weekly spending limit for non-essential categories.");
            else tips.add(String.format("Good job! Your recorded savings rate is %.1f%%. Keep monitoring your largest categories.", rate));
        }

        Map<String,Double> cats = analytics.categoryExpenses(ts);
        if(!cats.isEmpty()){
            String top = cats.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
            double val = cats.get(top);
            tips.add(String.format("%s is your largest expense category at ₹%.2f. Consider reviewing recurring purchases here.", top, val));
        }

        for(Budget b: budgets){
            double spent = new BudgetService(new repository.BudgetRepository()).spentForCurrentMonth(b.getCategory(), ts);
            double pct = spent / b.getLimit() * 100.0;
            if(pct > 100) tips.add(String.format("%s budget is over by ₹%.2f. Reduce spending in this category.", b.getCategory(), spent-b.getLimit()));
            else if(pct >= 80) tips.add(String.format("%s budget is %.0f%% used. You have ₹%.2f left.", b.getCategory(), pct, b.getLimit()-spent));
        }
        if(tips.size()<4) tips.add("Tip: Record transactions regularly for more reliable trends, anomaly detection and prediction.");
        return tips;
    }
}
