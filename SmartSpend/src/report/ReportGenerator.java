package report;
import model.*; import service.*; import java.nio.file.*; import java.time.*; import java.io.*; import java.util.*;
public class ReportGenerator {
    private final AnalyticsService a=new AnalyticsService(); private final PredictionService p=new PredictionService();
    public String generate(List<Transaction> ts,List<Budget> bs,BudgetService b){
        StringBuilder s=new StringBuilder("========== SMARTSPEND REPORT ==========\nGenerated: "+LocalDateTime.now()+"\n\nFINANCIAL SUMMARY\n");
        s.append(a.dashboard(ts)).append("\n\nCATEGORY-WISE EXPENSES\n");a.categoryExpenses(ts).forEach((k,v)->s.append(String.format("%-20s ₹%.2f%n",k,v)));
        s.append("\nMONTHLY EXPENSES\n");Map<String,Double> m=a.monthlyExpenses(ts);m.forEach((k,v)->s.append(String.format("%s : ₹%.2f%n",k,v)));
        s.append("\nBUDGET STATUS\n");for(Budget x:bs)s.append(b.budgetStatus(x,ts)).append("\n");
        if(m.size()>=2)s.append(String.format("\nPREDICTED NEXT-MONTH EXPENSE: ₹%.2f%n",p.predictFromMonthlyMap(m)));else s.append("\nPREDICTION: Need 2 months of data.\n");
        return s.toString();
    }
    public String saveReport(List<Transaction> ts,List<Budget> bs,BudgetService b){String c=generate(ts,bs,b);Path q=Paths.get("data/reports/smartspend_report.txt");try{Files.createDirectories(q.getParent());Files.writeString(q,c);return q.toString();}catch(IOException e){throw new RuntimeException(e);}}
}
