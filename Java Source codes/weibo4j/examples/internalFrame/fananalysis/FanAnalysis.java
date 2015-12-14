package internalFrame.fananalysis;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import statisticsModel.ChartCreate;
import statisticsModel.DataAccess;
import statisticsModel.SimpleStatistics;
import weibo4j.Weibo;

public class FanAnalysis extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<String> arrayList = new ArrayList<String>();
	public String[] col1 = { "项目","数据"};
	public DefaultTableModel model1 = new DefaultTableModel();
	public JTable table1 = new JTable();
	public String zipname=System.getProperty("user.dir")+"\\temp\\getfanlist.xls";
	public ChartCreate cc=new ChartCreate();
	public final DefaultPieDataset dataset1 = new DefaultPieDataset();
	public final DefaultPieDataset dataset2 = new DefaultPieDataset();
	public final DefaultPieDataset dataset3 = new DefaultPieDataset();
	public final DefaultCategoryDataset dataset4 = new DefaultCategoryDataset();
	public final DefaultPieDataset dataset5 = new DefaultPieDataset();
	public String[] gender={"女","男"};
	public String[] verified={"认证","非认证"};
	public int[] dtsh={50,100,200,500,1000,5000,10000,50000,100000};
	public String[] dtsht={"小于50","50-100","100-200","200-500","500-1000","1000-5000","5000-10000","10000-50000","50000-100000","大于100000"};
	public int[] dtshy={30,90,180,270,360,450,540,630,720};
	public String[] dtshty={"小于1月","1月-3月","3月-6月","6月-9月","9月-12月","12月-15月","15月-18月","18月-21月","21月-24月","大于24月"};
	public int returnno=15; 
	
	public FanAnalysis() {
		super();
		setBounds(0, 0, 280, 236);
		setLayout(new GridBagLayout());  
		
		
		int width=Toolkit.getDefaultToolkit().getScreenSize().width; 
		int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		SimpleStatistics ss=new SimpleStatistics();
		int cw=ss.chartwidthset(width);
		int ch=ss.chartheightset(height);
		int tw=ss.tablewidthset(width);
		
		//填加图片
		final JFreeChart jfreechart1 = ChartCreate.createChart(null,"粉丝分析图表");
	    final ChartPanel chartPanel1 = new ChartPanel(jfreechart1);
		final JFreeChart jfreechart4 = ChartCreate.createBarChart(null,"粉丝地区分布", "省份", "粉丝数");
		//final JFreeChart jfreechart5 = ChartCreate.createBarChart(null,"转发用户粉丝分布", "用户名称", "被@次数");
	    
		final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
		//gridBagConstraints_21.weighty = 0;
		gridBagConstraints_12.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints_12.anchor=GridBagConstraints.NORTHWEST;
		gridBagConstraints_12.fill = GridBagConstraints.BOTH;
		gridBagConstraints_12.gridwidth = 3;
		gridBagConstraints_12.gridheight= 8;
		gridBagConstraints_12.gridy = 0;
		gridBagConstraints_12.gridx = 1;
		gridBagConstraints_12.ipadx =cw;
		gridBagConstraints_12.ipady =ch;
		add(chartPanel1, gridBagConstraints_12);
		
		//添加表格
		model1=CreateModel(col1);
		table1 = new JTable(model1);
		final TableRowSorter<DefaultTableModel> sorter1 =new TableRowSorter<DefaultTableModel>(model1);  
        table1.setRowSorter(sorter1);
        JScrollPane scrollPane1=new JScrollPane(table1);
        
        final GridBagConstraints gridBagConstraints_13 = new GridBagConstraints();
		//gridBagConstraints_21.weighty = 0;
		gridBagConstraints_13.insets = new Insets(20, 20, 0, 0);
		gridBagConstraints_13.anchor=GridBagConstraints.NORTHWEST;
		gridBagConstraints_13.fill = GridBagConstraints.BOTH;
		gridBagConstraints_13.gridwidth = 1;
		gridBagConstraints_13.gridheight= 8;
		gridBagConstraints_13.gridy = 0;
		gridBagConstraints_13.gridx = 4;
		gridBagConstraints_13.ipadx =tw;
		gridBagConstraints_13.ipady =100;
		add(scrollPane1, gridBagConstraints_13);
        
        //添加按钮
	    JButton ChartCreate1=CreateChartButton("粉丝性别分布情况",11,gender,"f",jfreechart1,dataset1,chartPanel1);		
	    final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
		//gridBagConstraints_21.weighty = 0;
		gridBagConstraints_11.insets = new Insets(20, 0, 0, 10);
		gridBagConstraints_11.anchor=GridBagConstraints.NORTHWEST;
		gridBagConstraints_11.fill = GridBagConstraints.BOTH;
		gridBagConstraints_11.gridwidth = 1;
		gridBagConstraints_11.gridheight= 1;
		gridBagConstraints_11.gridy = 0;
		gridBagConstraints_11.gridx = 0;
		gridBagConstraints_11.ipadx = 10;
		gridBagConstraints_11.ipady = 10;
		add(ChartCreate1, gridBagConstraints_11);
	    
	    JButton ChartCreate2=CreateChartButton("粉丝认证情况分析",12,verified,"true",jfreechart1,dataset2,chartPanel1);	
	    final GridBagConstraints gridBagConstraints_21 = new GridBagConstraints();
		//gridBagConstraints_21.weighty = 0;
		gridBagConstraints_21.insets = new Insets(10, 0, 0, 10);
		gridBagConstraints_21.anchor=GridBagConstraints.NORTHWEST;
		gridBagConstraints_21.fill = GridBagConstraints.BOTH;
		gridBagConstraints_21.gridwidth = 1;
		gridBagConstraints_21.gridheight= 1;
		gridBagConstraints_21.gridy = 1;
		gridBagConstraints_21.gridx = 0;
		gridBagConstraints_21.ipadx = 10;
		gridBagConstraints_21.ipady = 10;
		add(ChartCreate2, gridBagConstraints_21);
		
		JButton ChartCreate3=CreateChartButtonFan("粉丝被关注分析",1,dtsht,jfreechart1,dataset3,chartPanel1);
		final GridBagConstraints gridBagConstraints_31 = new GridBagConstraints();
		//gridBagConstraints_21.weighty = 0;
		gridBagConstraints_31.insets = new Insets(10, 0, 0, 10);
		gridBagConstraints_31.anchor=GridBagConstraints.NORTHWEST;
		gridBagConstraints_31.fill = GridBagConstraints.BOTH;
		gridBagConstraints_31.gridwidth = 1;
		gridBagConstraints_31.gridheight= 1;
		gridBagConstraints_31.gridy = 2;
		gridBagConstraints_31.gridx = 0;
		gridBagConstraints_31.ipadx = 10;
		gridBagConstraints_31.ipady = 10;
		add(ChartCreate3, gridBagConstraints_31);
		
		
		JButton ChartCreate4=CreateBarChartButtonFan("粉丝地区分布情况",6,jfreechart4,dataset4,chartPanel1);	
		final GridBagConstraints gridBagConstraints_41 = new GridBagConstraints();
		//gridBagConstraints_21.weighty = 0;
		gridBagConstraints_41.insets = new Insets(10, 0, 0, 10);
		gridBagConstraints_41.anchor=GridBagConstraints.NORTHWEST;
		gridBagConstraints_41.fill = GridBagConstraints.BOTH;
		gridBagConstraints_41.gridwidth = 1;
		gridBagConstraints_41.gridheight= 1;
		gridBagConstraints_41.gridy = 3;
		gridBagConstraints_41.gridx = 0;
		gridBagConstraints_41.ipadx =10;
		gridBagConstraints_41.ipady =10;
		add(ChartCreate4, gridBagConstraints_41);
		
		
		JButton ChartCreate5= CreateChartButtonFanyear("粉丝微博年龄分布",13,dtshty,jfreechart1,dataset5,chartPanel1);	
		final GridBagConstraints gridBagConstraints_51 = new GridBagConstraints();
		//gridBagConstraints_21.weighty = 0;
		gridBagConstraints_51.insets = new Insets(10, 0, 0, 10);
		gridBagConstraints_51.anchor=GridBagConstraints.NORTHWEST;
		gridBagConstraints_51.fill = GridBagConstraints.BOTH;
		gridBagConstraints_51.gridwidth = 1;
		gridBagConstraints_51.gridheight= 1;
		gridBagConstraints_51.gridy = 4;
		gridBagConstraints_51.gridx = 0;
		gridBagConstraints_51.ipadx = 10;
		gridBagConstraints_51.ipady = 10;
		add(ChartCreate5, gridBagConstraints_51);
		
		JLabel tt=new JLabel("当前登录用户:"+Weibo.getclient()+"     ————————欢迎关注达巴赖马 O(∩_∩)O");
		final GridBagConstraints gridBagConstraints_xx = new GridBagConstraints();
		//gridBagConstraints_21.weighty = 0;
		gridBagConstraints_xx.insets = new Insets(15, 5, 5, 0);
		gridBagConstraints_xx.anchor=GridBagConstraints.EAST;
		gridBagConstraints_xx.fill = GridBagConstraints.EAST;
		gridBagConstraints_xx.gridwidth = 6;
		gridBagConstraints_xx.gridheight= 1;
		gridBagConstraints_xx.gridy = 20;
		gridBagConstraints_xx.gridx = 0;
		add(tt, gridBagConstraints_xx);
	}
	
	//粉丝性别(认证)分析按钮
	public JButton CreateChartButton(final String name,final int column,final String[] classify,final String first,final JFreeChart jfc,final DefaultPieDataset dataset,final ChartPanel chartPanel1){
		JButton draw=new JButton(name);		
		draw.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				cleartable(model1,table1);
				List<String> arrayListtemp = new ArrayList<String>();
				DataAccess da=new DataAccess();
				arrayListtemp=da.GetGenderVerified(zipname, 0, column);
				
				SimpleStatistics  ss=new SimpleStatistics();
				String[] sb=ss.DuplicateDataDelete(arrayListtemp);
				for(int i=0;i<sb.length;i++){
					if(sb[i].equals(first)){
						int no=ss.ComputeNo(sb[i],arrayListtemp);
						dataset.setValue(classify[0],no);
						Object[] str={classify[0],no};
						model1.addRow(str);
					}else{
						int no=ss.ComputeNo(sb[i],arrayListtemp);
						dataset.setValue(classify[1],no);
						Object[] str={classify[1],no};
						model1.addRow(str);
					}
				}	      
				cc.resetChart(jfc,dataset);
				jfc.setTitle(name);
				chartPanel1.setChart(jfc);
				}
		});
		return draw;
		}	
		
		//粉丝粉丝数粉丝分析按钮
		public JButton CreateChartButtonFan(final String name,final int column,final String[] classify,final JFreeChart jfc,final DefaultPieDataset dataset,final ChartPanel chartPanel1){
			JButton draw=new JButton(name);		
			draw.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					cleartable(model1,table1);
					DataAccess da=new DataAccess(); 
					SimpleStatistics ss=new SimpleStatistics();
					Long[] asd=da.GetNumber(zipname, 0, column);
					int dd[]=ss.FanNumberClassification(asd,dtsh);
					  
					for(int i=0;i<classify.length;i++){
						dataset.setValue(classify[i],dd[i]);
						Object[] str={classify[i],dd[i]};
						model1.addRow(str);
					}	      
					cc.resetChart(jfc,dataset);
					jfc.setTitle(name);
					chartPanel1.setChart(jfc);
					}
			});
			return draw;
		}
		
		//粉丝年龄分析按钮
		public JButton CreateChartButtonFanyear(final String name,final int column,final String[] classify,final JFreeChart jfc,final DefaultPieDataset dataset,final ChartPanel chartPanel1){
			JButton draw=new JButton(name);		
			draw.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					cleartable(model1,table1);
					DataAccess da=new DataAccess(); 
					SimpleStatistics ss=new SimpleStatistics();
					Long[] asd=da.GetFanYear(zipname, 0, column);
					int dd[]=ss.FanNumberClassification(asd,dtshy);
					  
					for(int i=0;i<classify.length;i++){
						dataset.setValue(classify[i],dd[i]);
						Object[] str={classify[i],dd[i]};
						model1.addRow(str);
					}	      
					cc.resetChart(jfc,dataset);
					jfc.setTitle(name);
					chartPanel1.setChart(jfc);
					}
			});
			return draw;
		}
		
		//转发用户地区分析
		public JButton CreateBarChartButtonFan(final String name,final int column,final JFreeChart jfc,final DefaultCategoryDataset dataset,final ChartPanel chartPanel1){
			JButton draw=new JButton(name);		
			draw.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					cleartable(model1,table1);
					DataAccess da=new DataAccess(); 
					SimpleStatistics ss=new SimpleStatistics();
					List<String> asd=da.GetProvince(zipname, 0, column);
					String dd[]=ss.DuplicateDataDelete(asd); 
					  
					for(int i=0;i<dd.length;i++){
						int data=ss.ComputeNo(dd[i], asd);
						dataset.setValue(data,"",(Comparable<String>)dd[i]);
						Object[] str={dd[i],data};
						model1.addRow(str);
					}	      
					cc.resetBarChart(jfc,dataset);
					jfc.setTitle(name);
					chartPanel1.setChart(jfc);
				} 
			});
			return draw;
		}
		
		//转发用户@分析
		public JButton CreateBarChartButtonMention(final String name,final int column,final JFreeChart jfc,final DefaultCategoryDataset dataset,final ChartPanel chartPanel1){
			JButton draw=new JButton(name);		
			draw.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					cleartable(model1,table1);
					List<Integer> mentionno = new ArrayList<Integer>();
					DataAccess da=new DataAccess();
					SimpleStatistics ss=new SimpleStatistics();
					List<String> arrayListtemp = da.GetMention(zipname, 0, column);
					String[] temp=ss.DuplicateDataDelete(arrayListtemp);
										  
					for(int i=0;i<temp.length;i++)    {   
					    int no=ss.ComputeNo(temp[i],arrayListtemp); 
					    mentionno.add(no);
					    
						Object[] str={temp[i],no};
						model1.addRow(str);
					}
					
					Collections.sort(mentionno);
					int xx=0;
					if(mentionno.size()<returnno){
						xx=mentionno.size()-1;
					}
					else{
						xx=mentionno.size()-returnno;
					}
					int tenth=mentionno.get(xx);	
					
					for(int i=0;i<model1.getRowCount();i++){
						int no=Integer.parseInt(table1.getValueAt(i, 1)+"");
						if(no>=tenth){
							String str=table1.getValueAt(i, 0)+"";
							dataset.setValue(no,"",(Comparable<String>)str);
						}
					}
										
					cc.resetBarChart(jfc,dataset);
					jfc.setTitle(name);
					chartPanel1.setChart(jfc);
				} 
			});
			return draw;
		}
		
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
		
		public void cleartable(DefaultTableModel model,JTable table){
			if(table.getModel().getRowCount()>0)
			  {
				  for (int index = model.getRowCount() - 1; index >= 0; index--) {
			            model.removeRow(index);
			        }
			  }
		}
}
