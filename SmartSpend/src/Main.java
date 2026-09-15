import model.*; import repository.*; import report.*; import service.*; import util.*;
import javax.swing.*; import javax.swing.border.*; import javax.swing.table.*;
import java.awt.*; import java.awt.event.*; import java.time.*; import java.util.*; import java.util.List;

public class Main {
    static final Color BG=new Color(245,247,250), CARD=Color.WHITE, TEXT=new Color(32,38,48), MUTED=new Color(100,110,125);
    static final Color ACCENT=new Color(70,92,190), GREEN=new Color(38,145,93), RED=new Color(205,70,70), AMBER=new Color(207,143,35);
    static final Font TITLE=new Font("Segoe UI",Font.BOLD,24), HEAD=new Font("Segoe UI",Font.BOLD,16), BODY=new Font("Segoe UI",Font.PLAIN,13);
    static TransactionService t=new TransactionService(new TransactionRepository());
    static BudgetService b=new BudgetService(new BudgetRepository());
    static AnalyticsService a=new AnalyticsService(); static PredictionService p=new PredictionService();
    static AnomalyService anomaly=new AnomalyService(); static RecommendationService rec=new RecommendationService();
    static ReportGenerator report=new ReportGenerator();
    static JFrame frame; static JPanel dashboard,transactions,budgets,insights,reports,dataManagement; static JTable table; static DefaultTableModel tableModel;
    static JLabel incomeVal,expenseVal,balanceVal,monthVal; static JTextArea insightArea,reportArea;

    public static void main(String[] args){ SwingUtilities.invokeLater(Main::createUI); }

    static void createUI(){
        frame=new JFrame("SmartSpend — Personal Finance Assistant");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); frame.setSize(1180,760); frame.setMinimumSize(new Dimension(1000,650));
        frame.setLocationRelativeTo(null);
        JPanel root=new JPanel(new BorderLayout()); root.setBackground(BG);
        root.add(header(),BorderLayout.NORTH);
        JTabbedPane tabs=new JTabbedPane(); tabs.setFont(new Font("Segoe UI",Font.BOLD,13)); tabs.setBackground(BG);
        dashboard=buildDashboard(); transactions=buildTransactions(); budgets=buildBudgets(); insights=buildInsights(); reports=buildReports(); dataManagement=buildDataManagement();
        tabs.addTab("Dashboard",dashboard); tabs.addTab("Transactions",transactions); tabs.addTab("Budgets",budgets); tabs.addTab("Smart Insights",insights); tabs.addTab("Reports",reports); tabs.addTab("Data Management",dataManagement);
        root.add(tabs,BorderLayout.CENTER); frame.setContentPane(root); refreshAll(); frame.setVisible(true);
    }

    static JPanel header(){
        JPanel p=new JPanel(new BorderLayout(15,0)); p.setBackground(new Color(28,35,49)); p.setBorder(new EmptyBorder(16,24,16,24));
        JLabel logo=new JLabel("SmartSpend"); logo.setForeground(Color.WHITE); logo.setFont(new Font("Segoe UI",Font.BOLD,25));
        JLabel sub=new JLabel("  Personal Finance Assistant"); sub.setForeground(new Color(190,200,215)); sub.setFont(BODY);
        JPanel left=new JPanel(new FlowLayout(FlowLayout.LEFT,0,0)); left.setOpaque(false); left.add(logo);left.add(sub);
        JLabel help=new JLabel("Track  •  Analyze  •  Improve");help.setForeground(new Color(205,212,225));help.setFont(BODY);p.add(left,BorderLayout.WEST);p.add(help,BorderLayout.EAST);return p;
    }

    static JPanel buildDashboard(){
        JPanel p=base(); JPanel top=new JPanel(new BorderLayout());top.setOpaque(false);
        top.add(section("Financial Overview","A quick picture of your recorded money in and money out."),BorderLayout.WEST);
        JButton add=button("+ Add Transaction",ACCENT);add.addActionListener(e->transactionDialog(null));top.add(add,BorderLayout.EAST);p.add(top,BorderLayout.NORTH);
        JPanel cards=new JPanel(new GridLayout(1,4,14,0));cards.setOpaque(false);
        incomeVal=metric(cards,"Total Income",GREEN); expenseVal=metric(cards,"Total Expenses",RED); balanceVal=metric(cards,"Current Balance",ACCENT); monthVal=metric(cards,"This Month",AMBER);p.add(cards,BorderLayout.CENTER);
        JPanel lower=new JPanel(new GridLayout(1,2,14,0));lower.setOpaque(false);
        JPanel cat=card();cat.add(section("Spending by Category","Where your expense money is going."),BorderLayout.NORTH);cat.add(new ChartPanel(ChartMode.CATEGORY),BorderLayout.CENTER);
        JPanel month=card();month.add(section("Monthly Spending","Your expense trend by month."),BorderLayout.NORTH);month.add(new ChartPanel(ChartMode.MONTH),BorderLayout.CENTER);
        lower.add(cat);lower.add(month);p.add(lower,BorderLayout.SOUTH); return p;
    }
    static JLabel metric(JPanel parent,String title,Color c){JPanel x=card();x.setBorder(new CompoundBorder(new LineBorder(new Color(225,228,235)),new EmptyBorder(15,16,15,16)));
        JLabel t1=new JLabel(title);t1.setForeground(MUTED);t1.setFont(BODY);JLabel v=new JLabel("₹0.00");v.setForeground(c);v.setFont(new Font("Segoe UI",Font.BOLD,23));x.add(t1,BorderLayout.NORTH);x.add(v,BorderLayout.CENTER);parent.add(x);return v;
    }

    static JPanel buildTransactions(){
        JPanel p=base(); JPanel top=new JPanel(new BorderLayout(12,0));top.setOpaque(false);
        top.add(section("Transactions","Add, edit, delete and search your income and expenses."),BorderLayout.WEST);
        JPanel actions=new JPanel(new FlowLayout(FlowLayout.RIGHT,8,0));actions.setOpaque(false);
        JTextField search=new JTextField(18);search.setToolTipText("Search description, category or payment method");
        JButton add=button("+ Add",ACCENT); JButton edit=button("Edit",new Color(80,105,130)); JButton del=button("Delete",RED);
        actions.add(new JLabel("Search:"));actions.add(search);actions.add(add);actions.add(edit);actions.add(del);top.add(actions,BorderLayout.EAST);p.add(top,BorderLayout.NORTH);
        String[] cols={"ID","Date","Type","Category / Source","Amount","Payment","Description"};
        tableModel=new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};
        table=new JTable(tableModel);table.setRowHeight(30);table.setFont(BODY);table.getTableHeader().setFont(HEAD);table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true); table.setFillsViewportHeight(true);
        table.getColumnModel().getColumn(0).setPreferredWidth(45);table.getColumnModel().getColumn(4).setPreferredWidth(110);
        JScrollPane sp=new JScrollPane(table);sp.setBorder(new LineBorder(new Color(220,224,230)));p.add(sp,BorderLayout.CENTER);
        JPanel tip=new JPanel(new FlowLayout(FlowLayout.LEFT));tip.setOpaque(false);JLabel l=new JLabel("Tip: double-click a row to edit it.");l.setForeground(MUTED);tip.add(l);p.add(tip,BorderLayout.SOUTH);
        add.addActionListener(e->transactionDialog(null)); edit.addActionListener(e->editSelected());del.addActionListener(e->deleteSelected());
        table.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){if(e.getClickCount()==2)editSelected();}});
        search.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){void f(){filter(search.getText());}public void insertUpdate(javax.swing.event.DocumentEvent e){f();}public void removeUpdate(javax.swing.event.DocumentEvent e){f();}public void changedUpdate(javax.swing.event.DocumentEvent e){f();}});
        return p;
    }
    static void filter(String q){q=q.toLowerCase();tableModel.setRowCount(0);for(Transaction x:t.getAll())if(q.isBlank()||((x.getCategory()+" "+x.getDescription()+" "+x.getPaymentMethod()).toLowerCase().contains(q)))addRow(x);}

    static JPanel buildBudgets(){
        JPanel p=base();JPanel top=new JPanel(new BorderLayout());top.setOpaque(false);top.add(section("Monthly Budgets","Set limits for categories and see how much of each limit is used this month."),BorderLayout.WEST);
        JButton set=button("+ Set / Update Budget",ACCENT);top.add(set,BorderLayout.EAST);p.add(top,BorderLayout.NORTH);
        JPanel list=new JPanel();list.setLayout(new BoxLayout(list,BoxLayout.Y_AXIS));list.setOpaque(false);JScrollPane scroll=new JScrollPane(list);scroll.setBorder(null);p.add(scroll,BorderLayout.CENTER);
        set.addActionListener(e->budgetDialog()); p.putClientProperty("budgetList",list); return p;
    }
    static JPanel buildInsights(){
        JPanel p=base();JPanel top=new JPanel(new BorderLayout());top.setOpaque(false);top.add(section("Smart Insights","Understand what your numbers mean without doing the calculations yourself."),BorderLayout.WEST);
        JButton refresh=button("Refresh Analysis",ACCENT);top.add(refresh,BorderLayout.EAST);p.add(top,BorderLayout.NORTH);
        insightArea=new JTextArea();insightArea.setEditable(false);insightArea.setFont(new Font("Segoe UI",Font.PLAIN,14));insightArea.setLineWrap(true);insightArea.setWrapStyleWord(true);insightArea.setMargin(new Insets(18,20,18,20));insightArea.setBackground(Color.WHITE);
        JScrollPane s=new JScrollPane(insightArea);s.setBorder(new LineBorder(new Color(225,228,235)));p.add(s,BorderLayout.CENTER);refresh.addActionListener(e->refreshInsights());return p;
    }
    static JPanel buildReports(){
        JPanel p=base();JPanel top=new JPanel(new BorderLayout());top.setOpaque(false);top.add(section("Reports","Generate a readable summary of transactions, budgets, trends and predictions."),BorderLayout.WEST);
        JButton gen=button("Generate Report",ACCENT);top.add(gen,BorderLayout.EAST);p.add(top,BorderLayout.NORTH);
        reportArea=new JTextArea();reportArea.setEditable(false);reportArea.setFont(new Font("Consolas",Font.PLAIN,13));reportArea.setMargin(new Insets(16,18,16,18));reportArea.setBackground(Color.WHITE);
        p.add(new JScrollPane(reportArea),BorderLayout.CENTER);gen.addActionListener(e->{String s=report.saveReport(t.getAll(),b.getBudgets(),b);reportArea.setText(report.generate(t.getAll(),b.getBudgets(),b));reportArea.setCaretPosition(0);JOptionPane.showMessageDialog(frame,"Report saved to:\n"+s,"Report Generated",JOptionPane.INFORMATION_MESSAGE);});return p;
    }

    static JPanel buildDataManagement(){
        JPanel p=base();
        JPanel top=new JPanel(new BorderLayout()); top.setOpaque(false);
        top.add(section("Data Management","Safely clear stored transactions or budgets while keeping SmartSpend usable."),BorderLayout.WEST);
        p.add(top,BorderLayout.NORTH);

        JPanel cards=new JPanel(new GridLayout(1,2,18,0)); cards.setOpaque(false);
        cards.add(manageCard("Clear Transactions",
                "Deletes all saved income and expense transactions. Categories, budgets and generated reports are preserved.",
                "Clear Transactions",()->{
                    int ok=JOptionPane.showConfirmDialog(frame,
                            "Clear ALL transactions?\nThis cannot be undone.",
                            "Confirm Clear",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                    if(ok==JOptionPane.YES_OPTION){
                        t.reset(); refreshAll();
                        JOptionPane.showMessageDialog(frame,"All transactions were cleared successfully.","SmartSpend",JOptionPane.INFORMATION_MESSAGE);
                    }
                }));
        cards.add(manageCard("Reset Transactions + Budgets",
                "Clears transaction history and all monthly budgets. Categories and generated report files are preserved.",
                "Reset Financial Data",()->{
                    int ok=JOptionPane.showConfirmDialog(frame,
                            "Reset ALL transactions and budgets?\nThis cannot be undone.",
                            "Confirm Reset",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                    if(ok==JOptionPane.YES_OPTION){
                        t.reset(); b.reset(); refreshAll();
                        JOptionPane.showMessageDialog(frame,"Transactions and budgets were reset successfully.","SmartSpend",JOptionPane.INFORMATION_MESSAGE);
                    }
                }));
        p.add(cards,BorderLayout.CENTER);
        JPanel note=new JPanel(new FlowLayout(FlowLayout.LEFT)); note.setOpaque(false);
        JLabel n=new JLabel("Note: categories.csv and files in data/reports are not deleted by these actions."); n.setForeground(MUTED); note.add(n); p.add(note,BorderLayout.SOUTH);
        return p;
    }
    static JPanel manageCard(String title,String desc,String action,Runnable task){
        JPanel c=card(); JPanel inner=new JPanel(); inner.setOpaque(false); inner.setLayout(new BoxLayout(inner,BoxLayout.Y_AXIS));
        JLabel h=new JLabel(title); h.setFont(TITLE); h.setForeground(TEXT);
        JTextArea d=new JTextArea(desc); d.setEditable(false); d.setLineWrap(true); d.setWrapStyleWord(true); d.setOpaque(false); d.setForeground(MUTED); d.setFont(BODY); d.setBorder(null);
        JButton btn=button(action, action.startsWith("Reset")?RED:ACCENT); btn.setAlignmentX(Component.LEFT_ALIGNMENT); btn.addActionListener(e->task.run());
        inner.add(h); inner.add(Box.createVerticalStrut(10)); inner.add(d); inner.add(Box.createVerticalGlue()); inner.add(btn); c.add(inner,BorderLayout.CENTER); return c;
    }

    static void refreshAll(){refreshTable();refreshDashboard();refreshBudgets();refreshInsights();if(reportArea!=null)reportArea.setText(report.generate(t.getAll(),b.getBudgets(),b));}
    static void refreshTable(){if(tableModel==null)return;tableModel.setRowCount(0);for(Transaction x:t.getAll())addRow(x);}
    static void addRow(Transaction x){tableModel.addRow(new Object[]{x.getId(),x.getDate(),x.getType(),x.getCategory(),String.format("₹%.2f",x.getAmount()),x.getPaymentMethod(),x.getDescription()});}
    static void refreshDashboard(){
        double in=a.totalIncome(t.getAll()),ex=a.totalExpense(t.getAll()),bal=in-ex;
        incomeVal.setText(money(in));expenseVal.setText(money(ex));balanceVal.setText(money(bal));
        String ym=YearMonth.now().toString();double me=t.getAll().stream().filter(x->x.getType().equalsIgnoreCase("EXPENSE")&&x.getDate().startsWith(ym)).mapToDouble(Transaction::getAmount).sum();
        monthVal.setText(money(me));
        dashboard.revalidate();dashboard.repaint();
    }
    static void refreshBudgets(){
        if(budgets==null)return;JPanel list=(JPanel)budgets.getClientProperty("budgetList");if(list==null)return;list.removeAll();
        for(Budget x:b.getBudgets()){double spent=b.spentForCurrentMonth(x.getCategory(),t.getAll()),pct=Math.min(100,spent/x.getLimit()*100);JPanel row=card();row.setMaximumSize(new Dimension(Integer.MAX_VALUE,95));
            JLabel name=new JLabel(x.getCategory()+"   •   "+money(spent)+" of "+money(x.getLimit()));name.setFont(HEAD);
            JProgressBar bar=new JProgressBar(0,100);bar.setValue((int)pct);bar.setStringPainted(true);bar.setString(String.format("%.0f%% used",spent/x.getLimit()*100));
            JLabel status=new JLabel(spent>x.getLimit()?"Over budget by "+money(spent-x.getLimit()):"Remaining "+money(x.getLimit()-spent));status.setForeground(spent>x.getLimit()?RED:(pct>=80?AMBER:GREEN));status.setFont(BODY);
            row.add(name,BorderLayout.NORTH);row.add(bar,BorderLayout.CENTER);row.add(status,BorderLayout.SOUTH);list.add(row);list.add(Box.createVerticalStrut(10));}
        if(b.getBudgets().isEmpty()){JLabel empty=new JLabel("No budgets yet. Set your first monthly category limit.");empty.setForeground(MUTED);list.add(empty);}
        list.revalidate();list.repaint();
    }
    static void refreshInsights(){
        if(insightArea==null)return;StringBuilder s=new StringBuilder();
        double in=a.totalIncome(t.getAll()),ex=a.totalExpense(t.getAll());s.append("SMART SUMMARY\n\n");
        s.append("Income: ").append(money(in)).append("\nExpenses: ").append(money(ex)).append("\nBalance: ").append(money(in-ex)).append("\n");
        if(in>0)s.append(String.format("Savings rate: %.1f%%%n",(in-ex)/in*100));
        s.append("\nRECOMMENDATIONS\n\n");int i=1;for(String x:rec.generate(t.getAll(),b.getBudgets()))s.append(i++).append(". ").append(x).append("\n\n");
        s.append("UNUSUAL SPENDING\n\n");List<Transaction> exps=t.getAll().stream().filter(x->x.getType().equalsIgnoreCase("EXPENSE")).toList();
        if(exps.size()<3)s.append("Add at least 3 expense transactions to activate anomaly detection.\n");
        else {double mean=exps.stream().mapToDouble(Transaction::getAmount).average().orElse(0),sd=Math.sqrt(exps.stream().mapToDouble(x->Math.pow(x.getAmount()-mean,2)).average().orElse(0));s.append(String.format("Typical expense: %s | Alert threshold: %s%n",money(mean),money(mean+sd)));boolean found=false;for(Transaction x:exps)if(x.getAmount()>mean+sd){s.append("• ").append(x.getDate()).append(" — ").append(x.getCategory()).append(" — ").append(money(x.getAmount())).append("\n");found=true;}if(!found)s.append("No unusually large expenses detected.\n");}
        s.append("\nNEXT-MONTH PREDICTION\n\n");Map<String,Double> m=a.monthlyExpenses(t.getAll());if(m.size()>=2)s.append("Estimated expenses: ").append(money(p.predictFromMonthlyMap(m))).append("\nBased on a simple linear regression over your monthly expense totals.");else s.append("Need at least 2 different months of expense data.");
        insightArea.setText(s.toString());insightArea.setCaretPosition(0);
    }

    static void transactionDialog(Transaction existing){
        boolean edit=existing!=null;JTextField amount=new JTextField(edit?String.valueOf(existing.getAmount()):"");
        JComboBox<String> type=new JComboBox<>(new String[]{"EXPENSE","INCOME"});if(edit)type.setSelectedItem(existing.getType());
        JComboBox<String> cat=new JComboBox<>();JTextField date=new JTextField(edit?existing.getDate():LocalDate.now().toString());
        JComboBox<String> method=new JComboBox<>(new String[]{"UPI","Bank Transfer","Cash","Card","Other"});if(edit)method.setSelectedItem(existing.getPaymentMethod());
        JTextField desc=new JTextField(edit?existing.getDescription():"");
        Runnable updateCats=()->{cat.removeAllItems();String[] arr=type.getSelectedItem().equals("INCOME")?new String[]{"Allowance","Salary","Scholarship","Freelance","Gift","Other"}:new String[]{"Food","Travel","Entertainment","Education","Shopping","Bills","Health","Other"};for(String x:arr)cat.addItem(x);if(edit&&cat.getItemCount()>0)cat.setSelectedItem(existing.getCategory());};
        type.addActionListener(e->updateCats.run());updateCats.run();
        JPanel form=formPanel();addField(form,"Amount (₹)",amount);addField(form,"Type",type);addField(form,"Category / Income Source",cat);addField(form,"Date (YYYY-MM-DD)",date);addField(form,"Payment Method",method);addField(form,"Description",desc);
        int r=JOptionPane.showConfirmDialog(frame,form,edit?"Edit Transaction":"Add Transaction",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if(r==JOptionPane.OK_OPTION)try{double val=Double.parseDouble(amount.getText().trim());String ty=(String)type.getSelectedItem(),ca=(String)cat.getSelectedItem(),dt=date.getText().trim(),pm=(String)method.getSelectedItem();if(edit)t.update(existing.getId(),val,ty,ca,dt,pm,desc.getText().trim());else t.add(val,ty,ca,dt,pm,desc.getText().trim());refreshAll();}catch(Exception e){JOptionPane.showMessageDialog(frame,e.getMessage(),"Invalid Input",JOptionPane.WARNING_MESSAGE);}
    }
    static void editSelected(){int r=table.getSelectedRow();if(r<0){JOptionPane.showMessageDialog(frame,"Select a transaction first.");return;}int model=table.convertRowIndexToModel(r);int id=(Integer)tableModel.getValueAt(model,0);transactionDialog(t.findById(id));}
    static void deleteSelected(){int r=table.getSelectedRow();if(r<0){JOptionPane.showMessageDialog(frame,"Select a transaction first.");return;}int model=table.convertRowIndexToModel(r);int id=(Integer)tableModel.getValueAt(model,0);int ok=JOptionPane.showConfirmDialog(frame,"Delete transaction #"+id+"?","Confirm Delete",JOptionPane.YES_NO_OPTION);if(ok==JOptionPane.YES_OPTION){t.delete(id);refreshAll();}}
    static void budgetDialog(){JComboBox<String> cat=new JComboBox<>(new String[]{"Food","Travel","Entertainment","Education","Shopping","Bills","Health","Other"});JTextField limit=new JTextField();JPanel f=formPanel();addField(f,"Category",cat);addField(f,"Monthly limit (₹)",limit);
        int r=JOptionPane.showConfirmDialog(frame,f,"Set / Update Budget",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);if(r==JOptionPane.OK_OPTION)try{b.setBudget((String)cat.getSelectedItem(),Double.parseDouble(limit.getText().trim()));refreshAll();}catch(Exception e){JOptionPane.showMessageDialog(frame,e.getMessage(),"Invalid Budget",JOptionPane.WARNING_MESSAGE);}
    }

    static JPanel formPanel(){JPanel p=new JPanel(new GridLayout(0,2,10,10));p.setBorder(new EmptyBorder(12,12,12,12));p.setPreferredSize(new Dimension(470,270));return p;}
    static void addField(JPanel p,String label,Component c){JLabel l=new JLabel(label);l.setFont(HEAD);p.add(l);p.add(c);}
    static JPanel base(){JPanel p=new JPanel(new BorderLayout(16,16));p.setBackground(BG);p.setBorder(new EmptyBorder(20,22,20,22));return p;}
    static JPanel card(){JPanel p=new JPanel(new BorderLayout(8,8));p.setBackground(CARD);p.setBorder(new CompoundBorder(new LineBorder(new Color(225,228,235)),new EmptyBorder(15,16,15,16)));return p;}
    static JPanel section(String title,String sub){JPanel p=new JPanel();p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));p.setOpaque(false);JLabel h=new JLabel(title);h.setFont(TITLE);h.setForeground(TEXT);JLabel d=new JLabel(sub);d.setFont(BODY);d.setForeground(MUTED);p.add(h);p.add(Box.createVerticalStrut(3));p.add(d);return p;}
    static JButton button(String s,Color c){JButton b=new JButton(s);b.setFont(new Font("Segoe UI",Font.BOLD,13));b.setForeground(Color.WHITE);b.setBackground(c);b.setFocusPainted(false);b.setBorder(new EmptyBorder(9,15,9,15));return b;}
    static String money(double x){return String.format("₹%,.2f",x);}

    enum ChartMode{CATEGORY,MONTH}
    static class ChartPanel extends JPanel{
        ChartMode mode;ChartPanel(ChartMode m){mode=m;setBackground(Color.WHITE);setPreferredSize(new Dimension(400,250));}
        protected void paintComponent(Graphics g){super.paintComponent(g);Graphics2D x=(Graphics2D)g;x.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);Map<String,Double> data=mode==ChartMode.CATEGORY?a.categoryExpenses(t.getAll()):a.monthlyExpenses(t.getAll());if(data.isEmpty()){x.setColor(MUTED);x.drawString("No expense data yet.",25,60);return;}List<Map.Entry<String,Double>> e=new ArrayList<>(data.entrySet());if(mode==ChartMode.CATEGORY)e.sort((u,v)->Double.compare(v.getValue(),u.getValue()));if(e.size()>6)e=e.subList(0,6);double max=e.stream().mapToDouble(Map.Entry::getValue).max().orElse(1);int w=getWidth(),h=getHeight(),left=55,bottom=42,top=20,gap=12;int bw=Math.max(20,(w-left-25-gap*(e.size()-1))/e.size());
            x.setColor(new Color(220,224,230));x.drawLine(left,top,left,h-bottom);x.drawLine(left,h-bottom,w-15,h-bottom);
            for(int i=0;i<e.size();i++){int bh=(int)((h-bottom-top-10)*(e.get(i).getValue()/max));int xx=left+10+i*(bw+gap),yy=h-bottom-bh;x.setColor(ACCENT);x.fillRoundRect(xx,yy,bw,bh,8,8);x.setColor(TEXT);String val=String.format("%.0f",e.get(i).getValue());x.drawString(val,xx+Math.max(0,(bw-x.getFontMetrics().stringWidth(val))/2),Math.max(14,yy-5));String label=e.get(i).getKey();if(label.length()>10)label=label.substring(0,10);x.drawString(label,xx,h-bottom+20);}
        }
    }
}
