package weibo4j.examples.WindowsWeibo;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import weibo4j.Count;
import weibo4j.Paging;
import weibo4j.Status;
import weibo4j.User;
import weibo4j.Weibo;
import weibo4j.WeiboException;

public class FanInfoAccess extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 20110923;
	String[] col3 = { "用户名","粉丝数","微博数","关注数","用户ID","昵称","地区","介绍","所在省","所在市","领域","性别","认证","注册时间","更新时间"};
	String[] col4 = { "用户名","微博ID","微博内容","评论数","转发数","发布时间"};
	String[] col5 = { "粉丝名称","认证与否","关注数","粉丝数","微博数","平均评论","平均转发","微博年龄"};
	public static JLabel fanno=new JLabel("目前粉丝数目:");
	public DefaultTableModel model3 = new DefaultTableModel();
	public JTable table3 = new JTable();
	public DefaultTableModel model4 = new DefaultTableModel(col4, 0);
	public JTable table4 = new JTable(model4);
	public DefaultTableModel model5 = new DefaultTableModel();
	public JTable table5 = new JTable();
	public int fancount=0;
	public int count=0;
	public Timer timer = new Timer();
	public double commentsav=0;
	public double rtsav=0;
	private boolean goon=false;
	public DecimalFormat fnum = new DecimalFormat("##0.000");   
	public Date date = new Date();   
	final static JTextField useridt3 = new JTextField(); 
	final static JTextField wbidt3 = new JTextField(); 
	final static JTextField fannamet = new JTextField(); 
	final static JTextField verifyt = new JTextField(); 
	final static JTextField fannost = new JTextField(); 
	final static JTextField followst = new JTextField(); 
	final static JTextField statusst = new JTextField();
	final static JTextField dayst = new JTextField(); 
	final static JTextField cavst = new JTextField(); 
	final static JTextField rtavst = new JTextField();
	final static JTextField pathfile = new JTextField(); 
	public String  zipname=null;
	public long timecontrol=120000;
	final static JFormattedTextField endpaget3 = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
	final static JFormattedTextField fanno3 = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
	final static JFormattedTextField tmesett = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
		
	public FanInfoAccess(){
		this.setLayout(null);
		this.setBounds(5,40,880,620);
		JLabel userid3=new JLabel("输入用户id（昵称）");
		userid3.setBounds(30, 20, 120, 20);
		this.add(userid3);
				
		useridt3.setBounds(150, 20, 130, 20);
		this.add(useridt3);
				
		JLabel wbid3=new JLabel("待查用户id（昵称）");
		wbid3.setBounds(30, 60, 120, 20);
		this.add(wbid3);
				
		JLabel endpage3=new JLabel("粉丝终止页码:");
		endpage3.setBounds(410, 20, 120, 20);
		this.add(endpage3);
		
		JLabel timeset=new JLabel("间隔时间:");
		timeset.setBounds(650, 20, 90, 20);
		this.add(timeset);
		
		tmesett.setText("120");
		tmesett.setBounds(710, 20, 30, 20);
		this.add(tmesett);
		
		fanno3.setText("0");
		fanno3.setBounds(620, 20, 20, 20);
		this.add(fanno3);
		
		endpaget3.setText("10");
		endpaget3.setBounds(500, 20, 20, 20);
		this.add(endpaget3);
		
		fanno.setBounds(530, 20, 120, 20);
		this.add(fanno);
		
		wbidt3.setBounds(150, 60, 130, 20);
		this.add(wbidt3);
		
		final JButton useridb3=new JButton("查询粉丝");
		useridb3.setBounds(300, 20, 90, 20);
		this.add(useridb3);
		
		final JButton wbidb3=new JButton("粉丝分析");
		wbidb3.setBounds(300, 60, 90, 20);
		this.add(wbidb3);
		
		final JButton getpath=new JButton("查找文件");
		getpath.setBounds(660,60, 90, 20);
		this.add(getpath);
		
		final JButton upload=new JButton("上载文件");
		upload.setBounds(760,60, 90, 20);
		this.add(upload);
		
		pathfile.setBounds(410, 60, 230, 20);
		this.add(pathfile);
		
		final JButton delall3=new JButton("全部删除");
		delall3.setBounds(760,20, 90, 20);
		this.add(delall3);
		
		final JButton delall4=new JButton("全部删除");
		delall4.setBounds(300,390, 90, 20);
		this.add(delall4);
			
		JLabel fanname=new JLabel("粉丝名称");
		fanname.setBounds(30, 300, 100, 20);
		this.add(fanname);
				
		fannamet.setBounds(90, 300, 50, 20);
		this.add(fannamet);
				
		JLabel verify=new JLabel("认证与否");
		verify.setBounds(160, 300, 100, 20);
		this.add(verify);
				
		verifyt.setBounds(220, 300, 50, 20);
		this.add(verifyt);
		
		JLabel fannos=new JLabel("关注数量");
		fannos.setBounds(290, 300, 100, 20);
		this.add(fannos);
				
		fannost.setBounds(350, 300, 50, 20);
		this.add(fannost);
				
		JLabel follows=new JLabel("粉丝数量");
		follows.setBounds(30, 330, 100, 20);
		this.add(follows);
				
		followst.setBounds(90, 330, 50, 20);
		this.add(followst);
		
		JLabel statuss=new JLabel("微博数量");
		statuss.setBounds(160, 330, 100, 20);
		this.add(statuss);
				
		statusst.setBounds(220, 330, 50, 20);
		this.add(statusst);
		
		JLabel days=new JLabel("微博年龄");
		days.setBounds(290, 330, 100, 20);
		this.add(days);
				
		dayst.setBounds(350, 330, 50, 20);
		this.add(dayst);
		
		JLabel cavs=new JLabel("平均评论");
		cavs.setBounds(30, 360, 100, 20);
		this.add(cavs);
				
		cavst.setBounds(90, 360, 50, 20);
		this.add(cavst);
		
		JLabel rtavs=new JLabel("平均转发");
		rtavs.setBounds(160, 360, 100, 20);
		this.add(rtavs);
				
		rtavst.setBounds(220, 360, 50, 20);
		this.add(rtavst);
		
		model3 = new DefaultTableModel(col3, 0)
		{
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
		
		table3 = new JTable(model3);
		final TableRowSorter<DefaultTableModel> sorter =new TableRowSorter<DefaultTableModel>(model3);  
        table3.setRowSorter(sorter);
		
        model5 = new DefaultTableModel(col5, 0)
		{
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
		
		table5 = new JTable(model5);
		final TableRowSorter<DefaultTableModel> sorter5 =new TableRowSorter<DefaultTableModel>(model5);  
        table5.setRowSorter(sorter5);
        
        JScrollPane scrollPane3=new JScrollPane(table3);
		scrollPane3.setBounds(20, 100, 830, 180);
		this.add(scrollPane3);	
		
		JScrollPane scrollPane4=new JScrollPane(table4);
		scrollPane4.setBounds(20, 420, 400, 160);
		this.add(scrollPane4);
		
		JScrollPane scrollPane5=new JScrollPane(table5);
		scrollPane5.setBounds(430, 320, 420, 260);
		this.add(scrollPane5);
		
		final JButton up=new JButton("开始");
		up.setBounds(470,290, 80, 20);
		this.add(up);
		
		final JButton ub=new JButton("停止");
		ub.setBounds(570,290, 80, 20);
		this.add(ub);
		
		final JButton export=new JButton("导出");
		export.setBounds(670,290, 80, 20);
		this.add(export);
				
		final JButton delall5=new JButton("删除");
		delall5.setBounds(770,290, 80, 20);
		this.add(delall5);
		
		if(!isGoon())
		{
			timecontrol=Integer.parseInt(tmesett.getText())*1000;
			timer.schedule(new MyTask(), 0, timecontrol);
		}
		
		ub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean is=false;
				count=0;
				setGoon(is);
				up.setForeground(Color.black);
			}	
		});
			
		up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(model3.getRowCount()==0){
					JOptionPane.showMessageDialog(null, "粉丝列表为空，请上载文件或查询粉丝！", "提示", JOptionPane.ERROR_MESSAGE);
				}
				else{
					boolean is=true;
					count=0;
					setGoon(is);
					up.setForeground(Color.red);
				}
			}	
		});
		useridb3.addActionListener(new GetFollowers());
		//useridb3.addActionListener(new xx());
		wbidb3.addActionListener(new GetCounts());
		delall3.addActionListener(new deleteall3());
		delall4.addActionListener(new deleteall4());
		table3.addMouseListener(new TableListeners3());
		getpath.addActionListener(new GetFilepath());
		upload.addActionListener(new UploadFile());
		delall5.addActionListener(new deleteall5());
		export.addActionListener(new ExcelExport());
	}

	public class deleteall3 implements ActionListener{
		public void actionPerformed(ActionEvent el) {
			clearNo(table3);
		}	
	}
	
	public class deleteall4 implements ActionListener{
		public void actionPerformed(ActionEvent el) {
			clearNo(table4);
		}	
	}
	
	public class deleteall5 implements ActionListener{
		public void actionPerformed(ActionEvent el) {
			clearNo(table5);
		}	
	}
	
	public class xx implements ActionListener{
		public void actionPerformed(ActionEvent el) {
			System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
	    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
	        try {
	        	Weibo weibo = new Weibo();
	        	weibo.setToken(Weibo.getat(),Weibo.getats());
	        	List<Status> list = weibo.getMentions(new Paging(1,100));
	        	for(Status status : list) {
	        		System.out.println( status.getId() + "  : "+status.getText());
	        	}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}
	
	public class UploadFile implements ActionListener{
		public void actionPerformed(ActionEvent el) {
			if(pathfile.getText().length()==0){
				JOptionPane.showMessageDialog(null, "文件名为空，请选择文件！", "提示", JOptionPane.ERROR_MESSAGE);
			}
			else{
			try  
			{  
			Object[] rt=new Object[15];	
			Workbook book= Workbook.getWorkbook(new File(zipname));  
			//获得第一个工作表对象  
			Sheet sheet=book.getSheet(0);
			int rows=sheet.getRows();
			int collumns=sheet.getColumns();
			//得到第一列第一行的单元格
			for(int j=0;j<rows;j++)
			{
				for(int i=0;i<collumns;i++)
					{	
					if (sheet.getCell(i,j).getType() == CellType.NUMBER) 
						{ 
						Long result=Long.valueOf(sheet.getCell(i,j).getContents());  
						rt[i]=result;
						}
					else{
						Object result=sheet.getCell(i,j).getContents();  
						rt[i]=result;
						}
					}
				model3.addRow(rt);
			}
			book.close();
			}catch(Exception e)  
			{  
			System.out.println(e);  
			} 
			fanno3.setText(table3.getModel().getRowCount()+"");
			}
		}	
	}

	public class GetFilepath implements ActionListener{
		public void actionPerformed(ActionEvent el) {
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
		      int r = chooser.showOpenDialog(null);
		      if (r == JFileChooser.APPROVE_OPTION) {
		        zipname = chooser.getSelectedFile().getPath();
		        pathfile.setText(zipname); 
		      }
		}	
	}
		
	public class GetFollowers implements ActionListener {
		public void actionPerformed(ActionEvent el){
			if(useridt3.getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "忘输入用户ID啦！", "提示", JOptionPane.ERROR_MESSAGE);
			}
			else{
			System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
	    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
	    	int end=Integer.parseInt(endpaget3.getText())*20;
	    	try {
				Weibo weibo = new Weibo();
				weibo.setToken(Weibo.getat(),Weibo.getats());
				for(int cursor = 1;cursor<=end;cursor=cursor+20)
				{	
				//采用cursor参数处理翻页
				List<User> list = weibo.getFollowersStatuses(useridt3.getText(),cursor);
				for(User user : list) {
					 	String s1=user.getName();
						long s5=user.getId();
						String s6=user.getScreenName(); 
						String s7=user.getLocation(); 
						String s8=user.getDescription();
						int s9=user.getProvince();
						int s10=user.getCity();
						String s11=user.getUserDomain();
						String s12=user.getGender();
						long s2=user.getFollowersCount();
						long s4=user.getFriendsCount();
						long s3=user.getStatusesCount();
						String s13=user.isVerified()+"";
						String s14=user.getCreatedAt()+"";
						String s15=null;
						if(user.getStatus()==null)
						{
							s15=null;	
						}
						else{
							s15=user.toString3()+"";
						}
						Object[] str_row = {s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15};  
						model3.addRow(str_row);
						}
					}
				fancount=table3.getModel().getRowCount();
				fanno3.setText(fancount+"");
				System.out.println(fancount);
			} catch (WeiboException e) {
				e.printStackTrace();
			}
			}
		}
	}
	
	public class GetCounts implements ActionListener{
		public void actionPerformed(ActionEvent el) {
			if(wbidt3.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "忘选择待查询粉丝啦！", "提示", JOptionPane.ERROR_MESSAGE);
			}
			else{
				getcounts(wbidt3.getText());
			}
		}	
	}
	
	public class MyTask extends TimerTask
	{
		public void run()
		{
				if(!isGoon())
				{
					
				}
				else
				{
					String str=table3.getValueAt(count,4)+"";
					System.out.println(str);
					getcounts(str);
					count++;
				}
		}
	}
	
	 public class ExcelExport implements ActionListener{
	       public void actionPerformed(ActionEvent el) {
    	   		Date date = new Date();
    	   		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
    	   		String jj=sdf.format(date);
    	   		//String jj=date.getMonth()+date.getDay()+date.getHours()+date.getMinutes()+date.getSeconds()+"";
	    	   	String name="分析数据"+jj+".xls";
	              File file = new File(name);
	              try {
	                  WritableWorkbook book1 = Workbook.createWorkbook(file);
	                  writeexcel(book1,model5,col5);
	                 
	              } catch (IOException e) {
	                  // TODO Auto-generated catch block
	                  e.printStackTrace();
	              }
	       }  
	    }

	public class TableListeners3 implements MouseListener {
		public void mouseClicked(MouseEvent e){
		}
		public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		}
		public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		}
		public void mousePressed(MouseEvent arg0) {
			int indexs =table3.getSelectedRow();
			wbidt3.setText(table3.getValueAt(indexs, 0)+"");
		}
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}
	}
	
	public void clearNo(JTable table){
		if(table.getModel().getRowCount()>0)
		  {
			  for (int index = table.getModel().getRowCount() - 1; index >= 0; index--) {
				  ((DefaultTableModel) table.getModel()).removeRow(index);
		        }
		  }
	}
	
	public void getcounts(String item){
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
        try {
        	long commentsno=0;
        	long rtno=0;
        	Weibo weibo = new Weibo();
			weibo.setToken(Weibo.getat(),Weibo.getats());
        	List<Status> statuses = weibo.getUserTimeline(item,new Paging(1,100));
        	StringBuilder ids = new StringBuilder();
        	for(Status status : statuses) {
        		ids.append(status.getId()).append(',');
        	}
        	ids.deleteCharAt(ids.length() - 1);
        	List<Count> counts = weibo.getCounts(ids.toString());
    		for(Count count : counts) {
    			System.out.println(count.toString());
    			long s1=count.getIdx();
        		String s2=weibo.showStatus(s1).getText();
        		long s3=count.getComments();
        		long s4=count.getRt();
				Date s5=weibo.showStatus(s1).getCreatedAt();
				Object[] str_row = {item,s1,s2,s3,s4, s5};  
				model4.addRow(str_row);
				commentsno=commentsno+count.getComments();	
				rtno=rtno+count.getRt();
    		}
    		int rows=table4.getModel().getRowCount();
    		commentsav=(float)commentsno/rows;
    		rtsav=(float)rtno/rows;
    		cavst.setText(fnum.format(commentsav));
    		rtavst.setText(fnum.format(rtsav));
    		String t1=weibo.showUser(item).getName();
    		String t2=weibo.showUser(item).isVerified()+"";
    		long t3=weibo.showUser(item).getFriendsCount();
    		long t4=weibo.showUser(item).getFollowersCount();
    		long t5=weibo.showUser(item).getStatusesCount();	
    		long t6=(date.getTime() - weibo.showUser(item).getCreatedAt().getTime())/(1000*3600*24);
    		fannamet.setText(t1);
    		verifyt.setText(t2);
    		fannost.setText(t3+"");
    		followst.setText(t4+"");
    		statusst.setText(t5+"");
    		dayst.setText(t6+"");
    		Object[] str={t1,t2,t3,t4,t5,commentsav,rtsav,t6};
    		model5.addRow(str);
    		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setGoon(boolean goon) {
		this.goon = goon;
	}
		
	public boolean isGoon() {
		return goon;
	}
	
	public void writeexcel(WritableWorkbook book,DefaultTableModel model,String[] str){
	       try {
	    	   WritableSheet sheet = book.createSheet("统计数据",1);
	           for(int j=0;j<model.getColumnCount();j++)
	           {
	        	   Label lab=new Label(j,0,str[j]);
	        	   try {
		                  sheet.addCell(lab);
		              } catch (RowsExceededException e) {
		                  // TODO Auto-generated catch block
		                  e.printStackTrace();
		              } catch (WriteException e) {
		                  // TODO Auto-generated catch block
		                  e.printStackTrace();
		              }
	           }
	           for(int i=0;i<model.getRowCount();i++)
	           { 
					for(int j=0;j<model.getColumnCount();j++)
					{
						Label lab=new Label(j,i+1,model.getValueAt(i, j)+"");
						try {
							sheet.addCell(lab);
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
