package weibo4j.examples.WindowsWeibo;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
public class Test extends JFrame implements ListSelectionListener{
	private JTable table1;
	private JTable table2;
	private JPanel panel1;
	private JPanel panel2;
	private ListSelectionModel selectionMode ;
	private DefaultTableModel model1;
	private DefaultTableModel model2;
	private static int flag=0;
	public Test()
	{
		
		String[] header={"字段一","字段二","字段三"};
		String[] header2={"字段三","字段四","字段五"};
		Object[][] date={{"1","2","3"},{"4","5","6"},{"7","8","9"}};
		Container content=this.getContentPane();
		model1=new DefaultTableModel(date,header);
		model2=new DefaultTableModel(null,header2);
		table1=new JTable(model1);
		selectionMode=table1.getSelectionModel();
		selectionMode.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectionMode.addListSelectionListener(this);
		table2=new JTable(model2);
		panel1=new JPanel();
		panel1.setLayout(new GridLayout(2,1));
		panel1.add(table1.getTableHeader());
		panel1.add(table1);
		
		panel2=new JPanel();
		panel2.setLayout(new GridLayout(2,1));
		panel2.add(table2.getTableHeader());
		panel2.add(table2);
		
		content.add(panel1,BorderLayout.NORTH);
		content.add(panel2,BorderLayout.SOUTH);
		
		this.setSize(300,300);
		this.setLocation(300,300);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	public void valueChanged(ListSelectionEvent e) {
		int[] rows=table1.getSelectedRows();//获取所有选择的行
		//获取所有选择的列

		for(int i=0;i<rows.length;i++)
		{
			int index=rows[i];
			String[] str={(String) model1.getValueAt(index,0),(String) model1.getValueAt(index,1),(String) model1.getValueAt(index,2)};
		      //添加到table2中
		    if(check((String) model1.getValueAt(index,0),model2)){
			model2.addRow(str);
			model1.removeRow(index);
		    }
		}
	}

	public boolean check(String s,DefaultTableModel model)
	{
		for(int i=0;i<model.getRowCount();i++){
			if(s.equals(model.getValueAt(i, 0))){
				return false;
			} 	
		}
		return true;
	}

	public static void main(String[]args)
	{
		new Test();
	}
}