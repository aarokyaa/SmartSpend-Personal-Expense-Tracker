package service;
import model.Transaction; import java.util.*;
public class AnomalyService {
    public List<Transaction> findUnusual(List<Transaction> expenses){
        if(expenses==null||expenses.size()<3)return List.of();
        double mean=expenses.stream().mapToDouble(Transaction::getAmount).average().orElse(0);
        double sd=Math.sqrt(expenses.stream().mapToDouble(x->Math.pow(x.getAmount()-mean,2)).average().orElse(0));
        double threshold=mean+sd; List<Transaction> r=new ArrayList<>();
        for(Transaction x:expenses)if(x.getAmount()>threshold)r.add(x); return r;
    }
}
