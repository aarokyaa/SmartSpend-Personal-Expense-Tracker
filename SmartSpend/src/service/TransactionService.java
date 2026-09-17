package service;
import model.Transaction; import repository.TransactionRepository; import util.InputValidator; import java.util.*;
public class TransactionService {
    private final TransactionRepository repo; private final List<Transaction> ts;
    public TransactionService(TransactionRepository r){repo=r;ts=r.findAll();}
    public Transaction add(double a,String type,String cat,String date,String method,String desc){
        if(!InputValidator.validAmount(a))throw new IllegalArgumentException("Amount must be positive.");
        if(!InputValidator.validType(type))throw new IllegalArgumentException("Type must be INCOME or EXPENSE.");
        if(!InputValidator.validText(cat))throw new IllegalArgumentException("Category required.");
        if(!InputValidator.validDate(date))throw new IllegalArgumentException("Date must be YYYY-MM-DD.");
        Transaction t=new Transaction(repo.nextId(),a,type.toUpperCase(),cat,date,method,desc);ts.add(t);repo.saveAll(ts);return t;
    }
    public List<Transaction> getAll(){return new ArrayList<>(ts);}
    public Transaction findById(int id){return ts.stream().filter(t->t.getId()==id).findFirst().orElse(null);}
    public boolean update(int id,double a,String type,String cat,String date,String method,String desc){
        Transaction t=findById(id);if(t==null)return false;
        if(!InputValidator.validAmount(a)||!InputValidator.validType(type)||!InputValidator.validText(cat)||!InputValidator.validDate(date))throw new IllegalArgumentException("Invalid transaction data.");
        t.setAmount(a);t.setType(type.toUpperCase());t.setCategory(cat);t.setDate(date);t.setPaymentMethod(method);t.setDescription(desc);repo.saveAll(ts);return true;
    }
    public boolean delete(int id){boolean x=ts.removeIf(t->t.getId()==id);if(x)repo.saveAll(ts);return x;}
    public void reset(){ts.clear();repo.reset();}
}
