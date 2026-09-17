package repository;
import model.Budget; import util.CSVUtil; import java.util.*;
public class BudgetRepository {
    private final String file="data/budgets.csv";
    public BudgetRepository(){if(CSVUtil.readLines(file).isEmpty())CSVUtil.writeLines(file,List.of("category,limit"));}
    public List<Budget> findAll(){List<Budget> r=new ArrayList<>();for(String l:CSVUtil.readLines(file)){if(l.startsWith("category,")||l.isBlank())continue;String[] p=l.split(",",-1);if(p.length<2)continue;try{r.add(new Budget(p[0],Double.parseDouble(p[1])));}catch(Exception ignored){}}return r;}
    public void reset(){saveAll(Collections.emptyList());}
    public void saveAll(List<Budget> bs){List<String> l=new ArrayList<>();l.add("category,limit");for(Budget b:bs)l.add(b.getCategory().replace(","," ")+","+b.getLimit());CSVUtil.writeLines(file,l);}
}
