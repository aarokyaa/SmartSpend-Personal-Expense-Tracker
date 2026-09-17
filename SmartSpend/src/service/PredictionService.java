package service;
import java.util.*;
public class PredictionService {
    public double predictNextMonth(List<Double> y){int n=y.size();if(n<2)throw new IllegalArgumentException("At least 2 months required.");double sx=0,sy=0,sxy=0,sx2=0;for(int i=0;i<n;i++){double x=i+1,v=y.get(i);sx+=x;sy+=v;sxy+=x*v;sx2+=x*x;}double d=n*sx2-sx*sx,s=(n*sxy-sx*sy)/d,b=(sy-s*sx)/n;return Math.max(0,b+s*(n+1));}
    public double predictFromMonthlyMap(Map<String,Double> m){return predictNextMonth(new ArrayList<>(m.values()));}
}
