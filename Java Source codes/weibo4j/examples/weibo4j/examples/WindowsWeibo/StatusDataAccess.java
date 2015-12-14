package weibo4j.examples.WindowsWeibo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import weibo4j.Paging;
import weibo4j.Status;
import weibo4j.Weibo;
import weibo4j.WeiboException;


public class StatusDataAccess extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 20110923;
	String[] col1 = { "微博ID","发布时间","微博内容","来源","用户名","用户ID","昵称","所在省","所在市","性别","粉丝数","关注数","微博数","认证","更新时间" };
	String[] col2 = { "用户名","微博ID","发布时间","微博内容","来源","用户ID","昵称","所在省","所在市","性别","粉丝数","关注数","微博数","认证","更新时间"};
	public DefaultTableModel model1 = new DefaultTableModel();
	public JTable table1 = new JTable();
	public DefaultTableModel model2 = new DefaultTableModel();
	public JTable table2 = new JTable();
	public JLabel logo=new JLabel("新浪微博数据抓取");
	public int count=0;
	public Timer timer = new Timer();
	private boolean goon=false;
	final static JTextField useridt = new JTextField(); 	
	final static JTextField wbidt = new JTextField(); 
	final static JFormattedTextField startpaget = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
	final static JFormattedTextField endpaget = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
	final static JFormattedTextField startpaget2 = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
	final static JFormattedTextField endpaget2 = new JFormattedTextField(NumberFormat.getIntegerInstance()); 

	
	public StatusDataAccess(){
		this.setLayout(null);
		this.setBounds(5,40,880,620);
		JLabel userid=new JLabel("输入用户id（数字）");
		userid.setBounds(30, 20, 120, 20);
		this.add(userid);
		
		useridt.setBounds(150, 20, 130, 20);
		this.add(useridt);
		
		JLabel wbid=new JLabel("输入微博id（数字）");
		wbid.setBounds(30, 60, 120, 20);
		this.add(wbid);
		
		JLabel startpage=new JLabel("转发起始页码:");
		startpage.setBounds(420, 60, 120, 20);
		this.add(startpage);
		
		startpaget.setText("1");
		startpaget.setBounds(510, 60, 30, 20);
		this.add(startpaget);
		
		JLabel endpage=new JLabel("转发结束页码:");
		endpage.setBounds(550, 60, 120, 20);
		this.add(endpage);
		
		endpaget.setText("10");
		endpaget.setBounds(640, 60, 30, 20);
		this.add(endpaget);
		
		JLabel startpage2=new JLabel("用户起始页码:");
		startpage2.setBounds(420, 20, 120, 20);
		this.add(startpage2);
		
		startpaget2.setText("1");
		startpaget2.setBounds(510, 20, 30, 20);
		this.add(startpaget2);
		
		JLabel endpage2=new JLabel("用户结束页码:");
		endpage2.setBounds(550, 20, 120, 20);
		this.add(endpage2);
		
		endpaget2.setText("10");
		endpaget2.setBounds(640, 20, 30, 20);
		this.add(endpaget2);
		
		wbidt.setBounds(150, 60, 130, 20);
		this.add(wbidt);
		
		final JButton useridb=new JButton("查询微博");
		useridb.setBounds(300, 20, 100, 20);
		this.add(useridb);
		
		final JButton wbidb=new JButton("查询转发");
		wbidb.setBounds(300, 60, 100, 20);
		this.add(wbidb);
		
		final JButton delall1=new JButton("全部删除");
		delall1.setBounds(740,20, 100, 20);
		this.add(delall1);
		
		final JButton delall2=new JButton("全部删除");
		delall2.setBounds(590,290, 100, 20);
		this.add(delall2);
		
		final JButton del=new JButton("删除转发");
		del.setBounds(740,290, 100, 20);
		this.add(del);
		
		final JButton up=new JButton("开始");
		up.setBounds(670,40, 80, 20);
		//this.add(up);
		
		final JButton ub=new JButton("停止");
		ub.setBounds(770,40, 80, 20);
		//this.add(ub);	
		
		final JButton output=new JButton("导出");
		output.setBounds(440,290, 100, 20);
		this.add(output);
		
		final JButton delid=new JButton("删除用户");
		delid.setBounds(740,60, 100, 20);
		this.add(delid);
		
		model1 = new DefaultTableModel(col1, 0){
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
		table1 = new JTable(model1);
		model2 = new DefaultTableModel(col2, 0){
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
		table2 = new JTable(model2);
		final TableRowSorter<DefaultTableModel> sorter1 =new TableRowSorter<DefaultTableModel>(model1);  
        table1.setRowSorter(sorter1);
        final TableRowSorter<DefaultTableModel> sorter2 =new TableRowSorter<DefaultTableModel>(model2);  
        table2.setRowSorter(sorter2);
		
		JScrollPane scrollPane1=new JScrollPane(table1);
		scrollPane1.setBounds(20, 320, 830, 260);
		this.add(scrollPane1);
		
		JScrollPane scrollPane2=new JScrollPane(table2);
		scrollPane2.setBounds(20, 100, 830, 180);
		this.add(scrollPane2);	
		
		del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int selectRows=table1.getSelectedRows().length;
				 for(int i=0;i<selectRows;i++){
					 model1.removeRow(table1.getSelectedRow());
				}
			}
		});
		
		delid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int selectRows=table2.getSelectedRows().length;
				 for(int i=0;i<selectRows;i++){
					 model2.removeRow(table2.getSelectedRow());
				}
			}
		});
		
		if(!isGoon())
		{
			timer.schedule(new MyTask(), 0, 1000);
		}
		
		ub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean is=false;
				setGoon(is);
			}	
		});
			
		up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean is=true;
				setGoon(is);
			}	
		});
		
		useridb.addActionListener(new GetUserTimeline());
		wbidb.addActionListener(new GetRepostTimeline());
		output.addActionListener(new updateexcel());	
		delall1.addActionListener(new deleteall1());
		delall2.addActionListener(new deleteall2());
		table2.addMouseListener(new TableListeners2());
		
	}
	class GetUserTimeline implements ActionListener {
		public void actionPerformed(ActionEvent el){
			System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
	    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
	    	int start2=Integer.parseInt(startpaget2.getText());
    		int end2=Integer.parseInt(endpaget2.getText());
    		
	    	if(useridt.getText().isEmpty()||start2>=end2)  
			  {	  
				  JOptionPane.showMessageDialog(null, "出错了，忘输入微博数字ID，或者页数不对啊！", "提示", 

JOptionPane.ERROR_MESSAGE);
			  }
			  else{
	    	
				  try {
				//获取24小时内前20条用户的微博信息;args[2]:用户ID
					Weibo weibo = new Weibo();
					weibo.setToken(Weibo.getat(),Weibo.getats());
				//获取24小时内前20条用户的微博信息;args[2]:用户ID
					for(int i=start2;i<end2+1;i++)
					{	
					List<Status> statuses = weibo.getUserTimeline(useridt.getText(),new Paging(i,20));
					for (Status status : statuses) {
					
		            String s1=status.getUser().getName();
					long s2=status.getId();
					String s3=status.getCreatedAt()+"";
					String s4=status.getText();
					String s5=status.getSource();
					long s6=status.getUser().getId();
					String s7=status.getUser().getScreenName();
					String s8=status.getUser().getLocation();
					String s9=status.getUser().getUserDomain();
					String s10=status.getUser().getGender();
					long s11=status.getUser().getFollowersCount();
					long s12=status.getUser().getFriendsCount();
					long s13=status.getUser().getStatusesCount();
					String s14=status.getUser().isVerified()+"";
					String s15=status.getUser().getCreatedAt()+"";
					Object[] str_row = {s1, s2,s3,s4, s5,s6,s7, s8,s9,s10, s11,s12,s13,s14,s15};  
					model2.addRow(str_row);
					}
		        	}
				} catch (WeiboException e) {
					e.printStackTrace();
				}
			  }
		}		
	}
	
	class GetRepostTimeline implements ActionListener {
		public void actionPerformed(ActionEvent el){
			System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
	    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
	    	int start=Integer.parseInt(startpaget.getText());
    		int end=Integer.parseInt(endpaget.getText());
	    	
	    	if(wbidt.getText().isEmpty()||start>=end)  
			  {	  
				  JOptionPane.showMessageDialog(null, "出错了，忘输入微博数字ID，或者页数不对啊！", "提示", JOptionPane.ERROR_MESSAGE);
			  }
			  else{
	    	
	        try {
	        	Weibo weibo = new Weibo();
				weibo.setToken(Weibo.getat(),Weibo.getats());
					    		
				for(int i=start;i<end+1;i++)
	        	{
				List <Status> list =  weibo.getreposttimeline(wbidt.getText(),new Paging(i,200));
	        	for(Status status:list){
	        		
	        		long s1=status.getId();
	        		String s2=status.getCreatedAt()+"";
					String s3=status.getText();
					String s4=status.getSource();
					String s5=status.getUser().getName();
					long s6=status.getUser().getId();
					String s7=status.getUser().getScreenName();
					String s8=status.getUser().getLocation();
					String s9=status.getUser().getUserDomain();
					String s10=status.getUser().getGender();
					long s11=status.getUser().getFollowersCount();
					long s12=status.getUser().getFriendsCount();
					long s13=status.getUser().getStatusesCount();
					String s14=status.getUser().isVerified()+"";
					String s15=status.getUser().getCreatedAt()+"";
					Object[] str_row = {s1, s2,s3,s4, s5,s6,s7, s8,s9,s10, s11,s12,s13,s14,s15};  
					model1.addRow(str_row);
	        		}
	        		}
				} catch (Exception e) {
				e.printStackTrace();
				}
			}
		}	
	}
	
	public class updateexcel implements ActionListener{
		public void actionPerformed(ActionEvent el) {
			try {
				String name="用户ID"+useridt.getText()+"微博ID"+wbidt.getText()+".xls"; 
				File file = new File(name);
				WritableWorkbook book = Workbook.createWorkbook(file);
				String page1="用户ID"+useridt.getText();
				WritableSheet sheet1 = book.createSheet(page1,0);
				String page2="微博ID"+wbidt.getText();
				WritableSheet sheet2 = book.createSheet(page2,1);
				for(int i=0;i<model2.getRowCount();i++)
				{ 
					for(int j=0;j<model2.getColumnCount();j++)
					{
						Label lab=new Label(j,i,model2.getValueAt(i, j)+"");
						try {
							sheet1.addCell(lab);
						} catch (RowsExceededException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (WriteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				for(int i=0;i<model1.getRowCount();i++)
				{ 
					for(int j=0;j<model1.getColumnCount();j++)
					{
						Label lab=new Label(j,i,model1.getValueAt(i, j)+"");
						try {
							sheet2.addCell(lab);
						} catch (RowsExceededException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (WriteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				book.write();
	            try {
					book.close();
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}	
	}
	
	public class deleteall1 implements ActionListener{
		public void actionPerformed(ActionEvent el) {
			if(table2.getModel().getRowCount()>0)
			  {
				  for (int index = model2.getRowCount() - 1; index >= 0; index--) {
			            model2.removeRow(index);
			        }
			  }
		}	
	}
	
	public class deleteall2 implements ActionListener{
		public void actionPerformed(ActionEvent el) {
			if(table1.getModel().getRowCount()>0)
			  {
				  for (int index = model1.getRowCount() - 1; index >= 0; index--) {
			            model1.removeRow(index);
			        }
			  }
		}	
	}
	
	class TableListeners2 implements MouseListener {
		public void mouseClicked(MouseEvent e){
			
		}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mousePressed(MouseEvent arg0) {
			int indexs =table2.getSelectedRow();
			wbidt.setText(table2.getValueAt(indexs, 1)+"");
		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public void setGoon(boolean goon) {
		this.goon = goon;
	}
		
	public boolean isGoon() {
		return goon;
	}

	class MyTask extends TimerTask
	{
		public void run()
		{
			int i=0;
			while(i<1)
			{   
				if(!isGoon())
				{
					break;
				}
				else
				{
					String s1="A"+count*1;
					String s2="B"+count*1;
					String s3="C"+count*1;
					String s4="D"+count*1;
					String[] str_row = { s1, s2,s3,s4};  
					String[] str_row2 = { s1, s2,s3};  
					model1.addRow(str_row);
					model2.addRow(str_row2);
					i++;
					count++;
				}
			}
		}
	}
	
}

