package internalFrame.weibomonitor;

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

public class QueryAnalysis extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<String> arrayList = new ArrayList<String>();
	public String[] col1 = { "��Ŀ","����"};
	public DefaultTableModel model1 = new DefaultTableModel();
	public JTable table1 = new JTable();
	public String zipname=System.getProperty("user.dir")+"\\temp\\getsearchlist.xls";
	public ChartCreate cc=new ChartCreate();
	public final DefaultPieDataset dataset1 = new DefaultPieDataset();
	public final DefaultPieDataset dataset2 = new DefaultPieDataset();
	public final DefaultPieDataset dataset3 = new DefaultPieDataset();
	public final DefaultCategoryDataset dataset4 = new DefaultCategoryDataset();
	public final DefaultCategoryDataset dataset5 = new DefaultCategoryDataset();
	public final DefaultCategoryDataset dataset6 = new DefaultCategoryDataset();
	public final DefaultCategoryDataset dataset7 = new DefaultCategoryDataset();
	public final DefaultCategoryDataset dataset8 = new DefaultCategoryDataset();
	
	public String[] gender={"Ů","��"};
	public String[] verified={"��֤","����֤"};
	public int[] dtsh={50,100,200,500,1000,5000,10000,50000,100000};
	public String[] dtsht={"С��50","50-100","100-200","200-500","500-1000","1000-5000","5000-10000","10000-50000","50000-100000","����100000"};
	public String[] dtshtdt={"0-1ʱ","1-2ʱ","2-3ʱ","3-4ʱ","4-5ʱ","5-6ʱ","6-7ʱ","7-8ʱ","8-9ʱ","9-10ʱ","10-11ʱ","11-12ʱ","12-13ʱ","13-14ʱ","14-15ʱ","15-16ʱ","16-17ʱ","17-18ʱ","18-19ʱ","19-20ʱ","20-21ʱ","21-22ʱ","22-23ʱ","23-24ʱ"};
	public int returnno=15; 

	public QueryAnalysis() {
		super();
		setBounds(0, 0, 280, 236);
		setLayout(new GridBagLayout());  
		
		
		int width=Toolkit.getDefaultToolkit().getScreenSize().width; 
		int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		SimpleStatistics ss=new SimpleStatistics();
		int cw=ss.chartwidthset(width);
		int ch=ss.chartheightset(height);
		int tw=ss.tablewidthset(width);
		
		/**�����������ͼ��jfreechart1��Ӧpieͼ��jfreechart5��Ӧbarͼ
		 * ÿ����ť����ͼ���Ե�������database
		 */
		final JFreeChart jfreechart1 = ChartCreate.createChart(null,"΢������ͼ��");
	    final ChartPanel chartPanel1 = new ChartPanel(jfreechart1);
		final JFreeChart jfreechart4 = ChartCreate.createBarChart(null,"΢����˿�ֲ�", "ʡ��", "��˿��");
		final JFreeChart jfreechart5 = ChartCreate.createBarChart(null,"΢��@�û��ֲ�", "�û�����", "��@����");
		final JFreeChart jfreechart7 = ChartCreate.createLineChart(null,"΢������ʱ��η���", "ʱ��", "΢����");
		final JFreeChart jfreechart8 = ChartCreate.createLineChart(null,"΢��ÿ�շ�����������", "ʱ��", "΢����");
		
		final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
		//gridBagConstraints_21.weighty = 0;
		gridBagConstraints_12.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints_12.anchor=GridBagConstraints.NORTHWEST;
		gridBagConstraints_12.fill = GridBagConstraints.BOTH;
		gridBagConstraints_12.gridwidth = 3;
		gridBagConstraints_12.gridheight= 9;
		gridBagConstraints_12.gridy = 0;
		gridBagConstraints_12.gridx = 1;
		gridBagConstraints_12.ipadx =cw;
		gridBagConstraints_12.ipady =ch;
		add(chartPanel1, gridBagConstraints_12);
		
		//��ӱ��
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
		gridBagConstraints_13.gridheight= 9;
		gridBagConstraints_13.gridy = 0;
		gridBagConstraints_13.gridx = 4;
		gridBagConstraints_13.ipadx =tw;
		gridBagConstraints_13.ipady =100;
		add(scrollPane1, gridBagConstraints_13);
        
        //��Ӱ�ť
	    JButton ChartCreate1=CreateChartButton("΢���Ա����",10,gender,"f",jfreechart1,dataset1,chartPanel1);		
	    final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
		//gridBagConstraints_21.weighty = 0;
		gridBagConstraints_11.insets = new Insets(40, 0, 0, 10);
		gridBagConstraints_11.anchor=GridBagConstraints.NORTHWEST;
		gridBagConstraints_11.fill = GridBagConstraints.BOTH;
		gridBagConstraints_11.gridwidth = 1;
		gridBagConstraints_11.gridheight= 1;
		gridBagConstraints_11.gridy = 0;
		gridBagConstraints_11.gridx = 0;
		gridBagConstraints_11.ipadx = 10;
		gridBagConstraints_11.ipady = 10;
		add(ChartCreate1, gridBagConstraints_11);
	    
	    JButton ChartCreate2=CreateChartButton("΢����֤����",14,verified,"true",jfreechart1,dataset2,chartPanel1);	
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
		
		
		JButton ChartCreate3=CreateChartButtonFan("΢����˿����",11,dtsht,jfreechart1,dataset3,chartPanel1);
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
		
		
		JButton ChartCreate4=CreateBarChartButtonFan("΢����������",6,jfreechart4,dataset4,chartPanel1);	
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
		
		
		JButton ChartCreate5= CreateBarChartButtonMention("΢��@����",2,jfreechart5,dataset5,chartPanel1);	
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
		
		JButton ChartCreate6= CreateChartSourceButton("΢���ͻ��˷���",3,jfreechart5,dataset6,chartPanel1);	
		final GridBagConstraints gridBagConstraints_61 = new GridBagConstraints();
		//gridBagConstraints_21.weighty = 0;
		gridBagConstraints_61.insets = new Insets(10, 0, 0, 10);
		gridBagConstraints_61.anchor=GridBagConstraints.NORTHWEST;
		gridBagConstraints_61.fill = GridBagConstraints.BOTH;
		gridBagConstraints_61.gridwidth = 1;
		gridBagConstraints_61.gridheight= 1;
		gridBagConstraints_61.gridy = 5;
		gridBagConstraints_61.gridx = 0;
		gridBagConstraints_61.ipadx = 10;
		gridBagConstraints_61.ipady = 10;
		add(ChartCreate6, gridBagConstraints_61);
		
		JButton ChartCreate7=CreateBarChartButtonTimeDistribution("΢��ʱ�η���",dtshtdt,1,jfreechart7,dataset7,chartPanel1);	
	    final GridBagConstraints gridBagConstraints_71 = new GridBagConstraints();
		//gridBagConstraints_71.weighty = 0;
		gridBagConstraints_71.insets = new Insets(10, 0, 0, 10);
		gridBagConstraints_71.anchor=GridBagConstraints.NORTHWEST;
		gridBagConstraints_71.fill = GridBagConstraints.BOTH;
		gridBagConstraints_71.gridwidth = 1;
		gridBagConstraints_71.gridheight= 1;
		gridBagConstraints_71.gridy = 6;
		gridBagConstraints_71.gridx = 0;
		gridBagConstraints_71.ipadx = 10;
		gridBagConstraints_71.ipady = 10;
		add(ChartCreate7, gridBagConstraints_71);
				
		JButton ChartCreate8=CreateChartButtonDateNo("΢��ʱ������",1,jfreechart8,dataset8,chartPanel1);
		final GridBagConstraints gridBagConstraints_81 = new GridBagConstraints();
		//gridBagConstraints_21.weighty = 0;
		gridBagConstraints_81.insets = new Insets(10, 0, 0, 10);
		gridBagConstraints_81.anchor=GridBagConstraints.NORTHWEST;
		gridBagConstraints_81.fill = GridBagConstraints.BOTH;
		gridBagConstraints_81.gridwidth = 1;
		gridBagConstraints_81.gridheight= 1;
		gridBagConstraints_81.gridy = 7;
		gridBagConstraints_81.gridx = 0;
		gridBagConstraints_81.ipadx = 10;
		gridBagConstraints_81.ipady = 10;
		add(ChartCreate8, gridBagConstraints_81);
		
		
		JLabel tt=new JLabel("��ǰ��¼�û�:"+Weibo.getclient()+"     ������������������ӭ��ע������� O(��_��)O");
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
	
	//΢���Ա�(��֤)������ť
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
		
		//΢���ͻ��˷���
		//��ֵ���Ӵ�С���У�ѡȡ����ǰ15�����ݣ���Ԥѡ����δ��15����ȫ����ʾ������1��ͳ�� 
		public JButton CreateChartSourceButton(final String name,final int column,final JFreeChart jfc,final DefaultCategoryDataset dataset,final ChartPanel chartPanel1){
			JButton draw=new JButton(name);		
			draw.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					dataset.clear();
					cleartable(model1,table1);
					List<Integer> mentionno = new ArrayList<Integer>();
					DataAccess da=new DataAccess();
					SimpleStatistics ss=new SimpleStatistics();
					List<String> arrayListtemp = da.GetCommonInfo(zipname, 0, column);
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
						xx=0;
					}
					else{
						xx=mentionno.size()-returnno;
					}
					int tenth=mentionno.get(xx);	
					for(int i=0;i<model1.getRowCount();i++){
						int no=Integer.parseInt(table1.getValueAt(i, 1)+"");
						if(no>=tenth && no>1){
							String str=table1.getValueAt(i, 0)+"";
							dataset.setValue(no,"",(Comparable<String>)str);
						}
					}
										
					cc.resetBarChart(jfc,dataset);
					jfc.setTitle(name);
					chartPanel1.setChart(jfc);
					arrayListtemp=null;
					mentionno=null;
				} 
			});
			return draw;
		}
	
		//΢����˿������ť
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
		
		//΢����������
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
		
		//΢��@����
		//��ֵ���Ӵ�С���У�ѡȡ����ǰ15�����ݣ���Ԥѡ����δ��15����ȫ����ʾ������1��ͳ�� 
		public JButton CreateBarChartButtonMention(final String name,final int column,final JFreeChart jfc,final DefaultCategoryDataset dataset,final ChartPanel chartPanel1){
			JButton draw=new JButton(name);		
			draw.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					dataset.clear();
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
						if(no>=tenth && no>1){
							String str=table1.getValueAt(i, 0)+"";
							dataset.setValue(no,"",(Comparable<String>)str);
						}
					}
										
					cc.resetBarChart(jfc,dataset);
					jfc.setTitle(name);
					chartPanel1.setChart(jfc);
					arrayListtemp=null;
					mentionno=null;
				} 
			});
			return draw;
		}
		
		//�û�΢���������ڷ���
		public JButton CreateChartButtonDateNo(final String name,final int column,final JFreeChart jfc,final DefaultCategoryDataset dataset,final ChartPanel chartPanel1){
			JButton draw=new JButton(name);		
			draw.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					cleartable(model1,table1);
					dataset.clear();
					DataAccess da=new DataAccess();
					List<String> mentionno = da.GetCommonInfo(zipname, 0, column);
					SimpleStatistics ss=new SimpleStatistics();
					String[] temp=ss.DuplicateDataDelete(mentionno);
					String[] dateorder=ss.DateOrder(temp);
													  
					for(int i=0;i<dateorder.length;i++)    {   
						int no=ss.ComputeNo(dateorder[i],mentionno); 
						dataset.setValue(no,"",(Comparable<String>)ss.MonthDayExtract(dateorder[i]));
						Object[] str={dateorder[i],no};
						model1.addRow(str);
					}
					cc.resetLineChart(jfc,dataset);
					chartPanel1.setChart(jfc);
				} 
			});
			return draw;
		}
		
		//΢������ʱ�η���
		public JButton CreateBarChartButtonTimeDistribution(final String name,final String[] dtshtdt,final int column,final JFreeChart jfc,final DefaultCategoryDataset dataset,final ChartPanel chartPanel1){
			JButton draw=new JButton(name);		
			draw.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					cleartable(model1,table1);
					dataset.clear();
					DataAccess da=new DataAccess();
					int[] temp = da.GetTimeDistribution(zipname, 0, column);
					for(int i=0;i<dtshtdt.length;i++)    {   
						dataset.setValue(temp[i],"΢����",(Comparable<String>)dtshtdt[i]);
						Object[] str={dtshtdt[i],temp[i]};
						model1.addRow(str);
					}
					cc.resetLineChart(jfc,dataset);
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