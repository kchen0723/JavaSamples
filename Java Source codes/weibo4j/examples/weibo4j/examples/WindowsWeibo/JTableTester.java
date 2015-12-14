package weibo4j.examples.WindowsWeibo;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.awt.*;


public class JTableTester {
      static Object data[][] = {
              {"China","Beijing","Chinese",1},
              {"America","Washington","English",2},
              {"Korea","Seoul","Korean",3},
              {"Japan","Tokyo","Japanese",4},
              {"France","Paris","French",25},
              {"England","London","English",64},
              {"Germany","Berlin","German",17},
      };
      static String titles[] = {"Country","Capital","Language","No"};
      public static void main(String[] args) {                
              DefaultTableModel m = new DefaultTableModel(data,titles){
            	 public Class getColumnClass(int column){
            		Class returnValue;
            	if ((column >= 0) && (column < getColumnCount()))
            		{
            		returnValue = getValueAt(0, column).getClass();
            		}
            		else
            		{
            		returnValue = Object.class;
            		}
            		return returnValue;
            		}
            	};
              JTable t = new JTable(m);
           
              final TableRowSorter sorter =new TableRowSorter(m);  
              t.setRowSorter(sorter);    //为JTable设置排序器
            
              JScrollPane sPane=new JScrollPane();
              sPane.setViewportView(t);
            
              JPanel    p    =    new    JPanel();
              p.setLayout(new    BoxLayout(p,BoxLayout.X_AXIS));
              JLabel    l    =    new    JLabel("Criteria:");
              final    JTextField    tf    =    new    JTextField();
              JButton    b    =    new    JButton("Do    Filter");
              p.add(l);
              p.add(tf);
              p.add(b);
              b.addActionListener(new    ActionListener()    {
                      public    void    actionPerformed(ActionEvent    e)    {
                              if(tf.getText().length()==0){
                                      sorter.setRowFilter(null);
                              }else{
                                      sorter.setRowFilter(RowFilter.regexFilter(tf.getText()));//为JTable设置基于正则表达式的过滤条件
                              }
                      }
              });
            
              JFrame    f    =    new    JFrame("JTable    Sorting    and    Filtering");
              f.getContentPane().add(sPane,BorderLayout.CENTER);                
              f.getContentPane().add(p,BorderLayout.SOUTH);
              f.setSize(400,300);
              f.setVisible(true);
      }
}
