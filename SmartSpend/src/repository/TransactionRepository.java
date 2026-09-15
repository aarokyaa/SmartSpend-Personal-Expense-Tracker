package repository;
import model.Transaction; import util.CSVUtil; import java.util.*;
public class TransactionRepository {
    private final String file="data/transactions.csv";
    public TransactionRepository(){if(CSVUtil.readLines(file).isEmpty())CSVUtil.writeLines(file,List.of("id,amount,type,category,date,paymentMethod,description"));}
    public List<Transaction> findAll(){
        List<Transaction> r=new ArrayList<>();
        for(String l:CSVUtil.readLines(file)){if(l.startsWith("id,")||l.isBlank())continue;
            List<String> p=CSVUtil.parseLine(l); if(p.size()<7)continue;
            try{r.add(new Transaction(Integer.parseInt(p.get(0)),Double.parseDouble(p.get(1)),p.get(2),p.get(3),p.get(4),p.get(5),p.get(6)));}catch(Exception ignored){}
        } return r;
    }
    public void saveAll(List<Transaction> ts){List<String> l=new ArrayList<>();l.add("id,amount,type,category,date,paymentMethod,description");for(Transaction t:ts)l.add(t.toCsv());CSVUtil.writeLines(file,l);}
    public void reset(){saveAll(Collections.emptyList());}
    public int nextId(){return findAll().stream().mapToInt(Transaction::getId).max().orElse(0)+1;}
}
