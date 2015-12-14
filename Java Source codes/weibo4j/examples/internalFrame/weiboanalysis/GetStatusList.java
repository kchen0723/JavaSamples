package internalFrame.weiboanalysis;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.DateFormatter;

import statisticsModel.DataAccess;
import statisticsModel.MyTableModel;
import statisticsModel.SimpleStatistics;
import statisticsModel.TextComponentPopupMenu;
import weibo4j.Paging;
import weibo4j.Status;
import weibo4j.Weibo;
import weibo4j.WeiboException;

public class GetStatusList extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 20110923;
	String[] col1 = { "用户名","微博ID","发布时间","微博内容","来源","用户ID","昵称", "地区","性别","粉丝数","关注数","微博数","认证","注册时间" };
	String[] col2 = { "微博ID","发布时间","微博内容","来源","用户名","用户ID","昵称", "地区","性别","粉丝数","关注数","微博数","认证","注册时间","原ID"};
	public int[] columns1={100,100,120,500,120,100,80, 80,50,80,80,80,50,150};
	public int[] columns2={100,120,500,120,100,100,80, 80,50,80,80,80,50,150,100};
	public DefaultTableModel model1 = new DefaultTableModel();
	public JTable table1 = new JTable();
	public DefaultTableModel model2 = new DefaultTableModel();
	public JTable table2 = new JTable();
	public JLabel logo=new JLabel("新浪微博数据抓取");
	public int count=0;
	public Timer timer = new Timer();
	//private boolean goon=false;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static DateFormat format2 =   new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat formatdate2 = new SimpleDateFormat("yyyy-MM-dd");
	static DateFormatter df = new DateFormatter(format2);	
	
	final static JTextField useridt = new JTextField(); 	
	final static JTextField wbidt = new JTextField(); 
	final static JFormattedTextField startpaget = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
	final static JFormattedTextField endpaget = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
	final static JFormattedTextField startpaget2 = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
	final static JFormattedTextField endpaget2 = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
	final static JFormattedTextField days1t = new JFormattedTextField(df);
	final static JFormattedTextField maxnot = new JFormattedTextField();
	final static JFormattedTextField itemst = new JFormattedTextField();
	final static JFormattedTextField days2t = new JFormattedTextField(df);
	
	public String  zipname=null;
	public String temp=System.getProperty("user.dir")+"\\temp\\getstatuslist.xls";
	final static JTextField pathfile = new JTextField(); 
	final static JFormattedTextField tp1 = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
	final static JFormattedTextField tp2 = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
	public MyTableModel tm=new MyTableModel();
	public TextComponentPopupMenu tpm=new TextComponentPopupMenu();
	
	public GetStatusList() {
		super();
		setLayout(new GridBagLayout());
		setBounds(0, 0, 280, 236);
		
		//定义数据表
		model1=tm.CreateModel(col1);
		table1 = tm.buildTable(model1);
		final TableRowSorter<DefaultTableModel> sorter1 =new TableRowSorter<DefaultTableModel>(model1);  
        table1.setRowSorter(sorter1);
        JScrollPane scrollPane1=new JScrollPane(table1);
        table1.addMouseListener(new TableListeners1());
        
        model2=tm.CreateModel(col2);
        table2 = tm.buildTable(model2);
		final TableRowSorter<DefaultTableModel> sorter2 =new TableRowSorter<DefaultTableModel>(model2);  
        table2.setRowSorter(sorter2);
        JScrollPane scrollPane2=new JScrollPane(table2);
		//固定表头
        table1.getTableHeader().setReorderingAllowed(false); 
        table2.getTableHeader().setReorderingAllowed(false); 
        //添加水平滚动条
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table1.setColumnModel(tm.getColumn(table1, columns1)); 
        table2.setColumnModel(tm.getColumn(table2, columns2)); 
		//第一行布局
		JLabel userid=new JLabel("输入用户ID（名称）");
		final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
		//gridBagConstraints_11.weighty = 0;
		gridBagConstraints_11.insets = new Insets(5, 10, 0, 0);
		//gridBagConstraints_11.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_11.gridwidth = 2;
		gridBagConstraints_11.gridy = 0;
		gridBagConstraints_11.gridx = 0;
		add(userid, gridBagConstraints_11);
		
		final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
		gridBagConstraints_12.weightx = 0;
		gridBagConstraints_12.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_12.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_12.anchor = GridBagConstraints.WEST;
		gridBagConstraints_12.gridwidth = 3;
		gridBagConstraints_12.gridy = 0;
		gridBagConstraints_12.gridx = 1;
		gridBagConstraints_12.ipadx= 150;
		add(useridt, gridBagConstraints_12);
		useridt.addMouseListener(tpm.getSharedInstance());  
				
		final JButton useridb=new JButton("查询微博");
		final GridBagConstraints gridBagConstraints_13 = new GridBagConstraints();
		//gridBagConstraints_13.weighty = 0;
		gridBagConstraints_13.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_13.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_13.gridwidth = 1;
		gridBagConstraints_13.gridy = 0;
		gridBagConstraints_13.gridx = 4;
		add(useridb, gridBagConstraints_13);
		useridb.addActionListener(new GetUserTimeline());
		
		JLabel startpage=new JLabel("微博页码:");
		final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
		//gridBagConstraints_14.weighty = 0;
		gridBagConstraints_14.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_14.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_14.gridwidth = 1;
		gridBagConstraints_14.gridy = 0;
		gridBagConstraints_14.gridx = 5;
		add(startpage, gridBagConstraints_14);
		
		final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
		//gridBagConstraints_15.weighty = 0;
		gridBagConstraints_15.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_15.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_15.gridwidth = 1;
		gridBagConstraints_15.gridy = 0;
		gridBagConstraints_15.gridx = 6;
		gridBagConstraints_15.ipadx = 10;
		startpaget.setText("1");
		add(startpaget, gridBagConstraints_15);
		
		
		JLabel endpage=new JLabel("至:");
		final GridBagConstraints gridBagConstraints_16 = new GridBagConstraints();
		//gridBagConstraints_16.weighty = 0;
		gridBagConstraints_16.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_16.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_16.gridwidth = 1;
		gridBagConstraints_16.gridy = 0;
		gridBagConstraints_16.gridx = 7;
		add(endpage, gridBagConstraints_16);
		
		final GridBagConstraints gridBagConstraints_17 = new GridBagConstraints();
		//gridBagConstraints_17.weighty = 0;
		gridBagConstraints_17.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_17.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_17.gridwidth = 1;
		gridBagConstraints_17.gridy = 0;
		gridBagConstraints_17.gridx = 8;
		gridBagConstraints_17.ipadx = 20;
		endpaget.setText("10");
		add(endpaget, gridBagConstraints_17);
		
		final JButton delwb=tm.CreateButtonS("删除微博",model1,table1);
		final GridBagConstraints gridBagConstraints_18 = new GridBagConstraints();
		gridBagConstraints_18.weightx = 0;
		gridBagConstraints_18.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_18.fill = GridBagConstraints.EAST;
		gridBagConstraints_18.anchor = GridBagConstraints.EAST;
		gridBagConstraints_18.gridwidth = 1;
		gridBagConstraints_18.gridy = 2;
		gridBagConstraints_18.gridx = 10;
		add(delwb, gridBagConstraints_18);
		
		//第二行布局
		JLabel wbid=new JLabel("输入微博ID（数字）");
		final GridBagConstraints gridBagConstraints_21 = new GridBagConstraints();
		//gridBagConstraints_21.weighty = 0;
		gridBagConstraints_21.insets = new Insets(5, 10, 0, 0);
		//gridBagConstraints_21.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_21.gridwidth = 2;
		gridBagConstraints_21.gridy = 1;
		gridBagConstraints_21.gridx = 0;
		add(wbid, gridBagConstraints_21);
		
		final GridBagConstraints gridBagConstraints_22 = new GridBagConstraints();
		gridBagConstraints_22.weighty = 0;
		gridBagConstraints_22.insets = new Insets(5, 0, 0, 0);
		gridBagConstraints_22.anchor = GridBagConstraints.WEST;
		gridBagConstraints_22.gridwidth = 3;
		gridBagConstraints_22.gridy = 1;
		gridBagConstraints_22.gridx = 1;
		gridBagConstraints_22.ipadx= 150;
		add(wbidt, gridBagConstraints_22);
		wbidt.addMouseListener(tpm.getSharedInstance());  
		
		final JButton wbidb=new JButton("查询转发");
		final GridBagConstraints gridBagConstraints_23 = new GridBagConstraints();
		//gridBagConstraints_23.weighty = 0;
		gridBagConstraints_23.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_23.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_23.gridwidth = 1;
		gridBagConstraints_23.gridy = 1;
		gridBagConstraints_23.gridx = 4;
		add(wbidb, gridBagConstraints_23);
		wbidb.addActionListener(new GetRepostTimeline());
		
		JLabel startpage2=new JLabel("转发页码:");
		final GridBagConstraints gridBagConstraints_24 = new GridBagConstraints();
		//gridBagConstraints_24.weighty = 0;
		gridBagConstraints_24.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_24.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_24.gridwidth = 1;
		gridBagConstraints_24.gridy = 1;
		gridBagConstraints_24.gridx = 5;
		add(startpage2, gridBagConstraints_24);
		
		final GridBagConstraints gridBagConstraints_25 = new GridBagConstraints();
		//gridBagConstraints_25.weighty = 0;
		gridBagConstraints_25.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_25.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_25.gridwidth = 1;
		gridBagConstraints_25.gridy = 1;
		gridBagConstraints_25.gridx = 6;
		gridBagConstraints_25.ipadx = 10;
		startpaget2.setText("1");
		add(startpaget2, gridBagConstraints_25);
				
		JLabel endpage2=new JLabel("至:");
		final GridBagConstraints gridBagConstraints_26 = new GridBagConstraints();
		//gridBagConstraints_26.weighty = 0;
		gridBagConstraints_26.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_26.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_26.gridwidth = 1;
		gridBagConstraints_26.gridy = 1;
		gridBagConstraints_26.gridx = 7;
		add(endpage2, gridBagConstraints_26);
		
		final GridBagConstraints gridBagConstraints_27 = new GridBagConstraints();
		//gridBagConstraints_27.weighty = 0;
		gridBagConstraints_27.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_27.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_27.gridwidth = 1;
		gridBagConstraints_27.gridy = 1;
		gridBagConstraints_27.gridx = 8;
		gridBagConstraints_27.ipadx = 20;
		endpaget2.setText("10");
		add(endpaget2, gridBagConstraints_27);
		
		final JButton delall1=tm.CreateButtonA("全部删除",model1,table1);
		final GridBagConstraints gridBagConstraints_28 = new GridBagConstraints();
		gridBagConstraints_28.weightx = 0;
		gridBagConstraints_28.insets = new Insets(5, 0, 0, 0);
		gridBagConstraints_28.anchor = GridBagConstraints.EAST;
		gridBagConstraints_28.gridwidth = 1;
		gridBagConstraints_28.gridy = 2;
		gridBagConstraints_28.gridx = 12;
		add(delall1, gridBagConstraints_28);
		
		//第三行布局
		JLabel days1=new JLabel("时间范围:");
		final GridBagConstraints gridBagConstraints_31 = new GridBagConstraints();
		//gridBagConstraints_31.weighty = 0;
		gridBagConstraints_31.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_31.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_31.gridwidth = 2;
		gridBagConstraints_31.gridy = 0;
		gridBagConstraints_31.gridx = 9;
		add(days1, gridBagConstraints_31);
		
		//设定搜索时间范围
		days1t.setText("1984-01-01");
		final GridBagConstraints gridBagConstraints_32 = new GridBagConstraints();
		gridBagConstraints_32.weightx = 0;
		gridBagConstraints_32.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_32.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_32.anchor = GridBagConstraints.WEST;
		gridBagConstraints_32.gridwidth = 1;
		gridBagConstraints_32.gridy = 0;
		gridBagConstraints_32.gridx = 10;
		gridBagConstraints_32.ipadx= 60;
		add(days1t, gridBagConstraints_32);
		
		JLabel days2=new JLabel("至");
		final GridBagConstraints gridBagConstraints_33 = new GridBagConstraints();
		//gridBagConstraints_33.weighty = 0;
		gridBagConstraints_33.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_33.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_33.gridwidth = 2;
		gridBagConstraints_33.gridy = 0;
		gridBagConstraints_33.gridx = 11;
		add(days2, gridBagConstraints_33);
		
		//设定搜索时间范围
		SimpleStatistics ss=new SimpleStatistics();
		String today=formatdate2.format(ss.DaysAdd(new Date(), 1));
		days2t.setText(today);
		final GridBagConstraints gridBagConstraints_34 = new GridBagConstraints();
		gridBagConstraints_34.weightx = 0;
		gridBagConstraints_34.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_34.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_34.anchor = GridBagConstraints.WEST;
		gridBagConstraints_34.gridwidth = 1;
		gridBagConstraints_34.gridy = 0;
		gridBagConstraints_34.gridx = 12;
		gridBagConstraints_34.ipadx= 60;
		add(days2t, gridBagConstraints_34);
		
		//第四行布局
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.weightx = 0.8;
		gridBagConstraints_4.weighty = 1.0;
		gridBagConstraints_4.insets = new Insets(10, 10, 0, 10);
		gridBagConstraints_4.fill = GridBagConstraints.BOTH;
		gridBagConstraints_4.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_4.gridwidth = 14;
		gridBagConstraints_4.gridheight = 2;
		gridBagConstraints_4.gridy = 3;
		gridBagConstraints_4.gridx = 0;
		gridBagConstraints_4.ipadx = 35;
		gridBagConstraints_4.ipady = -195;
		add(scrollPane1, gridBagConstraints_4);
		
		//第五行布局
		
		//微博条数
		JLabel statusno=new JLabel("当前微博条数:");
		final GridBagConstraints gridBagConstraints_31x = new GridBagConstraints();
		//gridBagConstraints_31x.weighty = 0;
		gridBagConstraints_31x.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_31x.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_31x.gridwidth = 1;
		gridBagConstraints_31x.gridy = 2;
		gridBagConstraints_31x.gridx = 0;
		add(statusno, gridBagConstraints_31x);
		
		final GridBagConstraints gridBagConstraints_41xx = new GridBagConstraints();
		//gridBagConstraints_41.weighty = 0;
		gridBagConstraints_41xx.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_41.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_41xx.gridwidth = 1;
		//gridBagConstraints_41.gridheight = 1;
		gridBagConstraints_41xx.gridy = 2;
		gridBagConstraints_41xx.gridx = 1;
		gridBagConstraints_41xx.ipadx = 30;
		add(tp1, gridBagConstraints_41xx);
		
		//转发条数
		JLabel repostno=new JLabel("当前转发条数:");
		final GridBagConstraints gridBagConstraints_51x = new GridBagConstraints();
		//gridBagConstraints_51x.weighty = 0;
		gridBagConstraints_51x.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_51x.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_51x.gridwidth = 1;
		gridBagConstraints_51x.gridy = 5;
		gridBagConstraints_51x.gridx = 0;
		add(repostno, gridBagConstraints_51x);
		
		final GridBagConstraints gridBagConstraints_41xs = new GridBagConstraints();
		//gridBagConstraints_41.weighty = 0;
		gridBagConstraints_41xs.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_41.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_41.gridwidth = 1;
		//gridBagConstraints_41.gridheight = 1;
		gridBagConstraints_41xs.gridy = 5;
		gridBagConstraints_41xs.gridx = 1;
		gridBagConstraints_41xs.ipadx = 30;
		add(tp2, gridBagConstraints_41xs);
		
		final JButton output=CreateSearchButton("数据导出");
		final GridBagConstraints gridBagConstraints_41 = new GridBagConstraints();
		//gridBagConstraints_41.weighty = 0;
		gridBagConstraints_41.insets = new Insets(10, 0, 0, 0);
		//gridBagConstraints_41.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_41.gridwidth = 1;
		//gridBagConstraints_41.gridheight = 1;
		gridBagConstraints_41.gridy = 5;
		gridBagConstraints_41.gridx = 4;
		//gridBagConstraints_41.ipadx = 30;
		add(output, gridBagConstraints_41);
		
		final JButton delrp=tm.CreateButtonS("删除转发",model2,table2);
		final GridBagConstraints gridBagConstraints_42 = new GridBagConstraints();
		//gridBagConstraints_41.weighty = 0;
		gridBagConstraints_42.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_42.anchor = GridBagConstraints.WEST;
		//gridBagConstraints_41.gridwidth = 1;
		//gridBagConstraints_41.gridheight = 1;
		gridBagConstraints_42.gridy = 5;
		gridBagConstraints_42.gridx = 10;
		add(delrp, gridBagConstraints_42);
		
		final JButton delall2=tm.CreateButtonA("全部删除",model2,table2);
		final GridBagConstraints gridBagConstraints_43 = new GridBagConstraints();
		//gridBagConstraints_41.weighty = 0;
		gridBagConstraints_43.insets = new Insets(10, 00, 0, 00);
		gridBagConstraints_43.anchor = GridBagConstraints.EAST;
		//gridBagConstraints_41.gridwidth = 1;
		//gridBagConstraints_41.gridheight = 1;
		gridBagConstraints_43.gridy = 5;
		gridBagConstraints_43.gridx = 12;
		add(delall2, gridBagConstraints_43);
		
		//第六行布局
		final GridBagConstraints gridBagConstraints_51 = new GridBagConstraints();
		gridBagConstraints_51.weightx = 0.8;
		gridBagConstraints_51.weighty = 1.0;
		gridBagConstraints_51.insets = new Insets(10, 10, 10, 10);
		gridBagConstraints_51.fill = GridBagConstraints.BOTH;
		gridBagConstraints_51.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_51.gridwidth = 14;
		gridBagConstraints_51.gridheight = 2;
		gridBagConstraints_51.gridy = 6;
		gridBagConstraints_51.gridx = 0;
		gridBagConstraints_51.ipadx = 35;
		gridBagConstraints_51.ipady = -195;
		add(scrollPane2, gridBagConstraints_51);
		
		JLabel tt=new JLabel("当前登录用户:"+Weibo.getclient()+"     ————————欢迎关注达巴赖马 O(∩_∩)O");
		final GridBagConstraints gridBagConstraints_xx = new GridBagConstraints();
		//gridBagConstraints_21.weighty = 0;
		gridBagConstraints_xx.insets = new Insets(15, 5, 5, 0);
		gridBagConstraints_xx.anchor=GridBagConstraints.EAST;
		gridBagConstraints_xx.fill = GridBagConstraints.EAST;
		gridBagConstraints_xx.gridwidth = 7;
		gridBagConstraints_xx.gridheight= 1;
		gridBagConstraints_xx.gridy = 20;
		gridBagConstraints_xx.gridx = 0;
		add(tt, gridBagConstraints_xx);
	}
	
	//获取用户微博列表（页数时间）
	public class GetUserTimeline implements ActionListener {
		public void actionPerformed(ActionEvent el){
			SimpleStatistics ss=new SimpleStatistics();
			System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
	    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
	    	int start2=Integer.parseInt(startpaget.getText());
    		int end2=Integer.parseInt(endpaget.getText());
    		
	    	if(useridt.getText().isEmpty()||start2>=end2||days1t.getText().isEmpty()||days2t.getText().isEmpty())  
			  {	  
				  JOptionPane.showMessageDialog(null, "出错了，忘输入微博数字ID，查询时间，或者页数不对啊！", "提示", JOptionPane.ERROR_MESSAGE);
			  }
			  else{
				  try {
					  Weibo weibo = new Weibo();
					  weibo.setToken(Weibo.getat(),Weibo.getats());
					  for(int i=start2;i<end2;i++)
					  {				
						  List<Status> statuses = weibo.getUserTimeline(useridt.getText(),new Paging(i,20));
						  for (Status status : statuses) {
							  String strtime=format2.format(status.getCreatedAt());
							  if(ss.DateBetweenCheck(strtime,days1t.getText(),days2t.getText())){
								  String s1=status.getUser().getName();
								  long s2=status.getId();
								  String s3=format.format(status.getCreatedAt());
								  String s4=status.getText();
								  String s5=status.getSource();
								  long s6=status.getUser().getId();
								  String s7=status.getUser().getScreenName();
								  String s8=status.getUser().getLocation();
								  String s10=status.getUser().getGender();
								  long s11=status.getUser().getFollowersCount();
								  long s12=status.getUser().getFriendsCount();
								  long s13=status.getUser().getStatusesCount();
								  String s14=status.getUser().isVerified()+"";
								  String s15=format.format(status.getUser().getCreatedAt());
								  Object[] str_row = {s1, s2,s3,s4, s5,s6,s7, s8,s10, s11,s12,s13,s14,s15};  
								  model1.addRow(str_row);
							  }
						  }
					}
				} catch (WeiboException e) {
					e.printStackTrace();
				}
			  }
	    	tp1.setText(model1.getRowCount()+""); 
		}		
	}
	
	public class GetRepostTimeline implements ActionListener {
		public void actionPerformed(ActionEvent el){
			System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
	    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
	    	int start=Integer.parseInt(startpaget2.getText());
    		int end=Integer.parseInt(endpaget2.getText());
	    	
	    	if(wbidt.getText().isEmpty()||start>=end){	  
				  JOptionPane.showMessageDialog(null, "出错了，忘输入微博数字ID，或者页数不对啊！", "提示", JOptionPane.ERROR_MESSAGE);
	    	}
	    	else{
	    		try {
	    			Weibo weibo = new Weibo();
	    			weibo.setToken(Weibo.getat(),Weibo.getats());
	    			for(int i=start;i<end+1;i++){
	    				List <Status> list =  weibo.getreposttimeline(wbidt.getText(),new Paging(i,200));
	    				for(Status status:list){
				  				long s1=status.getId();
				  				String s2=format.format(status.getCreatedAt());
				  				String s3=status.getText();
				  				String s4=status.getSource();
				  				String s5=status.getUser().getName();
				  				long s6=status.getUser().getId();
				  				String s7=status.getUser().getScreenName();
				  				String s8=status.getUser().getLocation();
				  				String s10=status.getUser().getGender();
				  				long s11=status.getUser().getFollowersCount();
				  				long s12=status.getUser().getFriendsCount();
				  				long s13=status.getUser().getStatusesCount();
				  				String s14=status.getUser().isVerified()+"";
				  				String s16=format.format(status.getUser().getCreatedAt());
				  				String s17=wbidt.getText();
				  				Object[] str_row = {s1, s2,s3,s4, s5,s6,s7, s8,s10, s11,s12,s13,s14,s16,s17};  
				  				model2.addRow(str_row);
				  			}
				  		}
	    			} catch (Exception e) {
	    				e.printStackTrace();
	    			}
	    	DataAccess da=new DataAccess();
	    	da.ExcelCreate(temp, model2, col2);
		}
	    	tp1.setText(model1.getRowCount()+"");
	    	tp2.setText(model2.getRowCount()+"");
		}
	}
	
	public class TableListeners1 implements MouseListener {
		public void mouseClicked(MouseEvent e){
		}

		public void mouseEntered(MouseEvent arg0) {
		}

		public void mouseExited(MouseEvent arg0) {
		}

		public void mousePressed(MouseEvent arg0) {
			int indexs =table1.getSelectedRow();
			wbidt.setText(table1.getValueAt(indexs, 1)+"");
		}

		public void mouseReleased(MouseEvent arg0) {
		}
	}
	
	public JButton CreateSearchButton(String name){
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
			          	da.ExcelCreate(zipname,model2,col2);
			          }else if (JOptionPane.showConfirmDialog(new JFrame(),
			                  "确认覆盖文件？", "保存",
			                  JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
			        	  da.ExcelCreate(zipname,model2,col2);	    
			              }
			          }
			}
		});
	return button;
	}
	
	public Object returnTable(int i,int j){
		Object content=null;
		if(model2.getRowCount()>0){
			content=table2.getValueAt(i, j);
		}
		return content;
	}
	
}