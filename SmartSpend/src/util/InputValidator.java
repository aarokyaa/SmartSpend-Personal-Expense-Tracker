package util;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class InputValidator {
    public static boolean validAmount(double a){return a>0&&!Double.isNaN(a)&&!Double.isInfinite(a);}
    public static boolean validType(String t){return t!=null&&(t.equalsIgnoreCase("INCOME")||t.equalsIgnoreCase("EXPENSE"));}
    public static boolean validText(String s){return s!=null&&!s.trim().isEmpty();}
    public static boolean validDate(String s){
        if(s==null||!s.matches("\\d{4}-\\d{2}-\\d{2}")) return false;
        try { LocalDate.parse(s); return true; } catch(DateTimeParseException e){ return false; }
    }
}
