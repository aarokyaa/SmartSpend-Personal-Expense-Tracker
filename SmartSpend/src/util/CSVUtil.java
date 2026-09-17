package util;
import java.io.*; import java.nio.file.*; import java.util.*;

public class CSVUtil {
    public static List<String> readLines(String f){
        try{Path p=Paths.get(f);return Files.exists(p)?Files.readAllLines(p):new ArrayList<>();}
        catch(IOException e){return new ArrayList<>();}
    }
    public static void writeLines(String f,List<String> l){
        try{Path p=Paths.get(f);if(p.getParent()!=null)Files.createDirectories(p.getParent());Files.write(p,l);}
        catch(IOException e){throw new RuntimeException("File error: "+e.getMessage(),e);}
    }
    /** Splits a simple RFC-4180-style CSV row, including quoted commas. */
    public static List<String> parseLine(String line){
        List<String> out=new ArrayList<>(); StringBuilder cur=new StringBuilder(); boolean quoted=false;
        for(int i=0;i<line.length();i++){
            char c=line.charAt(i);
            if(c=='"'){
                if(quoted && i+1<line.length() && line.charAt(i+1)=='"'){cur.append('"');i++;}
                else quoted=!quoted;
            } else if(c==','&&!quoted){out.add(cur.toString());cur.setLength(0);}
            else cur.append(c);
        }
        out.add(cur.toString()); return out;
    }
}
