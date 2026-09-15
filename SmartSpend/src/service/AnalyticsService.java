package service;
import model.Transaction; import java.util.*;
public class AnalyticsService {
    public double totalIncome(List<Transaction> t){return t.stream().filter(x->x.getType().equalsIgnoreCase("INCOME")).mapToDouble(Transaction::getAmount).sum();}
    public double totalExpense(List<Transaction> t){return t.stream().filter(x->x.getType().equalsIgnoreCase("EXPENSE")).mapToDouble(Transaction::getAmount).sum();}
    public double balance(List<Transaction> t){return totalIncome(t)-totalExpense(t);}
    public Map<String,Double> categoryExpenses(List<Transaction> ts){Map<String,Double> m=new TreeMap<>();for(Transaction t:ts)if(t.getType().equalsIgnoreCase("EXPENSE"))m.merge(t.getCategory(),t.getAmount(),Double::sum);return m;}
    public Map<String,Double> monthlyExpenses(List<Transaction> ts){Map<String,Double> m=new TreeMap<>();for(Transaction t:ts)if(t.getType().equalsIgnoreCase("EXPENSE")&&t.getDate().length()>=7)m.merge(t.getDate().substring(0,7),t.getAmount(),Double::sum);return m;}
    public String dashboard(List<Transaction> t){return String.format("Income: ₹%.2f%nExpenses: ₹%.2f%nBalance: ₹%.2f",totalIncome(t),totalExpense(t),balance(t));}
}
