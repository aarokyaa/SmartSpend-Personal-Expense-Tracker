package service;
import model.*; import repository.BudgetRepository; import java.util.*; import java.time.YearMonth;

public class BudgetService {
    private final BudgetRepository repo; private final List<Budget> bs;
    public BudgetService(BudgetRepository r){repo=r;bs=r.findAll();}
    public void setBudget(String cat,double limit){
        if(cat==null||cat.isBlank()||limit<=0)throw new IllegalArgumentException("Category and positive limit required.");
        for(int i=0;i<bs.size();i++)if(bs.get(i).getCategory().equalsIgnoreCase(cat)){bs.set(i,new Budget(cat.trim(),limit));repo.saveAll(bs);return;}
        bs.add(new Budget(cat.trim(),limit));repo.saveAll(bs);
    }
    public List<Budget> getBudgets(){return new ArrayList<>(bs);}
    public double spentForCategory(String cat,List<Transaction> ts){
        return ts.stream().filter(t->t.getType().equalsIgnoreCase("EXPENSE"))
            .filter(t->t.getCategory().equalsIgnoreCase(cat)).mapToDouble(Transaction::getAmount).sum();
    }
    public double spentForCurrentMonth(String cat,List<Transaction> ts){
        String month=YearMonth.now().toString();
        return ts.stream().filter(t->t.getType().equalsIgnoreCase("EXPENSE"))
            .filter(t->t.getCategory().equalsIgnoreCase(cat))
            .filter(t->t.getDate()!=null&&t.getDate().startsWith(month))
            .mapToDouble(Transaction::getAmount).sum();
    }
    public double spentForMonth(String cat,List<Transaction> ts,String month){
        return ts.stream().filter(t->t.getType().equalsIgnoreCase("EXPENSE"))
            .filter(t->t.getCategory().equalsIgnoreCase(cat))
            .filter(t->t.getDate()!=null&&t.getDate().startsWith(month))
            .mapToDouble(Transaction::getAmount).sum();
    }
    public void reset(){bs.clear();repo.reset();}
    public String budgetStatus(Budget b,List<Transaction> ts){
        double spent=spentForCurrentMonth(b.getCategory(),ts), rem=b.getLimit()-spent;
        return rem<0?b.getCategory()+": OVER BUDGET by ₹"+String.format("%.2f",-rem):
            b.getCategory()+": ₹"+String.format("%.2f",spent)+" / ₹"+String.format("%.2f",b.getLimit())+
            " | Remaining ₹"+String.format("%.2f",rem);
    }
}
