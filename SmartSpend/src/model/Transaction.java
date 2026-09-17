package model;

public class Transaction {
    private int id; private double amount; private String type, category, date, paymentMethod, description;
    public Transaction(int id,double amount,String type,String category,String date,String paymentMethod,String description){
        this.id=id; this.amount=amount; this.type=type; this.category=category; this.date=date;
        this.paymentMethod=paymentMethod; this.description=description==null?"":description;
    }
    public int getId(){return id;} public double getAmount(){return amount;} public String getType(){return type;}
    public String getCategory(){return category;} public String getDate(){return date;}
    public String getPaymentMethod(){return paymentMethod;} public String getDescription(){return description;}
    public void setAmount(double v){amount=v;} public void setType(String v){type=v;}
    public void setCategory(String v){category=v;} public void setDate(String v){date=v;}
    public void setPaymentMethod(String v){paymentMethod=v;} public void setDescription(String v){description=v;}
    private String csv(String s){return "\""+s.replace("\"","\"\"")+"\"";}
    public String toCsv(){return id+","+amount+","+type+","+csv(category)+","+date+","+paymentMethod+","+csv(description);}
    public String toString(){return "ID: "+id+" | "+type+" | ₹"+String.format("%.2f",amount)+" | "+category+" | "+date+" | "+paymentMethod+" | "+description;}
}
