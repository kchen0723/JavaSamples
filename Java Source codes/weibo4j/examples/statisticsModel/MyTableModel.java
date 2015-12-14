package statisticsModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class MyTableModel {
	//表格排序属性设置
	public DefaultTableModel CreateModel(String[] name){
		DefaultTableModel model = new DefaultTableModel(name, 0){
	      	 /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Class getColumnClass(int column){
	     		Class returnValue;
	     	if ((column >= 0) && (column < getColumnCount())&&(getValueAt(0, column)!=null))
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
		return model;
	}	
	
	//清空列表设置
	public JButton CreateButtonA(String name,final DefaultTableModel model,final JTable table){
		JButton button = new JButton(name);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getModel().getRowCount()>0)
				  {
					  for (int index = model.getRowCount() - 1; index >= 0; index--) {
				            model.removeRow(index);
				        }
				  }
			}
		});
	return button;
	}	
	
	//列表单条删除按钮
	public JButton CreateButtonS(String name,final DefaultTableModel model,final JTable table){
		JButton button = new JButton(name);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selection = table.getSelectedRow();
				selection = table.convertRowIndexToModel(selection);
				model.removeRow(selection );
				}
			});
		return button;
	}	
	
	public JTable buildTable(DefaultTableModel model) {  
	    JTable table = new JTable(model);  
	    table.setShowGrid(true);  
	    table.setGridColor(Color.gray);  
	    table.setRowHeight(25);  
	    table.setFont(new Font("", Font.PLAIN, 14));  
	    return table;  
	}  
	
	public TableColumnModel getColumn(JTable table, int[] width) {  
	    TableColumnModel columns = table.getColumnModel();  
	    for (int i = 0; i < width.length; i++) {  
	        TableColumn column = columns.getColumn(i);  
	        column.setPreferredWidth(width[i]);  
	    }  
	    return columns;  
	}
	
	public JButton CreateSearchButton(String name,final DefaultTableModel model,final String[] col ){
		JButton button = new JButton(name);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataAccess da=new DataAccess();
				JFileChooser chooser = new JFileChooser();
			      chooser.setCurrentDirectory(new File("."));
			      chooser.setFileFilter(new FileFilter() {
			        public boolean accept(File f) {
			          return f.getName().toLowerCase().endsWith(".xls")
			              || f.isDirectory();
			        }
			        public String getDescription() {
			          return "XLS Files";
			        }
			      });
			      int r = chooser.showSaveDialog(null);
			      if (r == JFileChooser.APPROVE_OPTION) {
			          String zipname = chooser.getSelectedFile().getPath();
			          if(zipname.indexOf(".xls")<0){
			          	zipname=zipname+".xls";
			          	da.ExcelCreate(zipname,model,col);
			          }else if (JOptionPane.showConfirmDialog(new JFrame(),
			                  "确认覆盖文件？", "保存",
			                  JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
			        	  da.ExcelCreate(zipname,model,col);	    
			              }
			          }
			}
		});
	return button;
	}
	
	
}
