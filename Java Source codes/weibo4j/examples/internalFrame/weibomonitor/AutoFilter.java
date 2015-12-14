package internalFrame.weibomonitor;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import statisticsModel.MyTableModel;
import statisticsModel.SendEmail;
import statisticsModel.SimpleStatistics;
import statisticsModel.TextComponentPopupMenu;
import weibo4j.Paging;
import weibo4j.Status;
import weibo4j.Weibo;
import weibo4j.WeiboException;
import dao.dao;
import dao.daoFunction;

public class AutoFilter extends JPanel{
	private static final long serialVersionUID = -3251502005148394518L;
	//数据库字段33个，加入入库时间，预警字段
	String[] col = {"微博ID","发布时间","微博内容","来源","用户名",
			"用户ID","地区","所在省","所在市","注册时间","性别",
			"粉丝数","关注数","微博数","认证","是否原创","微博ID",
			"发布时间","微博内容","来源","用户名","用户ID","地区",
			"所在省","所在市","注册时间","性别","粉丝数","关注数",
			"微博数","认证","搜索词","检索时间","告警"};
	public int[] columns={80,160,500,80,100,80,80,50,50,160,50,50,50,50,30,50,80,160,500,80,100,80,80,50,50,80,30,50,50,50,30,80};
	public SimpleDateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public SimpleDateFormat eformatdate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public JComboBox keywordsbox1 = new JComboBox();//关键词选择下拉框
	public JComboBox keywordsbox2 = new JComboBox();//敏感词选择下拉框
	public JComboBox noticerbox = new JComboBox();//通知用户选择下拉框
	public JComboBox kwnoticerbox = new JComboBox();//关键词用户选择下拉框
	final static JTextField keywords1 = new JTextField(); 
	final static JTextField keywords2 = new JTextField(); 
	final static JTextField noticert= new JTextField(); 
	final static JTextField kwnoticert= new JTextField(); 
	final static JFormattedTextField timet = new JFormattedTextField();
	final static JFormattedTextField maxpagest = new JFormattedTextField();
	final static JFormattedTextField itemst1 = new JFormattedTextField();
	final static JFormattedTextField itemst2 = new JFormattedTextField();
	final static JFormattedTextField runtimet = new JFormattedTextField();
	public DefaultTableModel model1 = new DefaultTableModel();
	public JTable table1 = new JTable();
	public DefaultTableModel model2 = new DefaultTableModel();
	public JTable table2 = new JTable();
	public MyTableModel tm=new MyTableModel();
	//定时器
	public int count=0;
	public int runtime=0;
	private boolean goon=false;
	public Timer timer = new Timer();
	public Timer timeralert = new Timer();
	public int timecontrol=-1;
	public String temp=System.getProperty("user.dir")+"\\temp\\temp.xls";
	public SimpleStatistics ss=new SimpleStatistics();
	public TextComponentPopupMenu tpm=new TextComponentPopupMenu();
	//保存Email，通知用户
	public String readStr ="";
	public String path=System.getProperty("user.dir");
	public String txtname="contactor.txt";
	
	public AutoFilter() {
		super();
		setLayout(new GridBagLayout());
		setBounds(0, 0, 280, 236);
				
		//定义数据表
		model1=tm.CreateModel(col);
		table1 = tm.buildTable(model1);
		final TableRowSorter<DefaultTableModel> sorter1 =new TableRowSorter<DefaultTableModel>(model1);  
        table1.setRowSorter(sorter1);
        JScrollPane scrollPane1=new JScrollPane(table1);
                
        model2=tm.CreateModel(col);
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
        table1.setColumnModel(tm.getColumn(table1, columns)); 
        table2.setColumnModel(tm.getColumn(table1, columns)); 
        
        //关键词下拉框
        JLabel trendname1=new JLabel("微博关键词:");
		final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
		//gridBagConstraints_11.weighty = 0;
		gridBagConstraints_11.insets = new Insets(5, 10, 0, 0);
		//gridBagConstraints_11.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_11.gridwidth = 2;
		gridBagConstraints_11.gridy = 0;
		gridBagConstraints_11.gridx = 0;
		add(trendname1, gridBagConstraints_11);
		
		final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
		gridBagConstraints_12.weightx = 0;
		gridBagConstraints_12.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_12.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_12.anchor = GridBagConstraints.WEST;
		gridBagConstraints_12.gridwidth = 1;
		gridBagConstraints_12.gridy = 0;
		gridBagConstraints_12.gridx = 1;
		gridBagConstraints_12.ipadx= 60;
		add(keywordsbox1, gridBagConstraints_12);
		keywordsbox1.addActionListener(new ComboboxListen1());
		//初始化
		ComboboxInitial(keywordsbox1);
		
		//敏感词下拉框
        JLabel trendname2=new JLabel("微博敏感词:");
		final GridBagConstraints gridBagConstraints_21 = new GridBagConstraints();
		//gridBagConstraints_21.weighty = 0;
		gridBagConstraints_21.insets = new Insets(10, 10, 0, 0);
		//gridBagConstraints_21.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_21.gridwidth = 2;
		gridBagConstraints_21.gridy = 1;
		gridBagConstraints_21.gridx = 0;
		add(trendname2, gridBagConstraints_21);
		
		final GridBagConstraints gridBagConstraints_22 = new GridBagConstraints();
		gridBagConstraints_22.weightx = 0;
		gridBagConstraints_22.insets = new Insets(10, 0, 0, 0);
		//gridBagConstraints_22.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_22.anchor = GridBagConstraints.WEST;
		gridBagConstraints_22.gridwidth = 1;
		gridBagConstraints_22.gridy = 1;
		gridBagConstraints_22.gridx = 1;
		gridBagConstraints_22.ipadx= 60;
		add(keywordsbox2, gridBagConstraints_22);
		keywordsbox2.addActionListener(new ComboboxListen2());
		
		//敏感词下拉框
        JLabel trendname3=new JLabel("业务管理员:");
		final GridBagConstraints gridBagConstraints_31 = new GridBagConstraints();
		//gridBagConstraints_31.weighty = 0;
		gridBagConstraints_31.insets = new Insets(5, 10, 0, 0);
		//gridBagConstraints_31.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_31.gridwidth = 2;
		gridBagConstraints_31.gridy = 2;
		gridBagConstraints_31.gridx = 0;
		add(trendname3, gridBagConstraints_31);
		
		final GridBagConstraints gridBagConstraints_32 = new GridBagConstraints();
		gridBagConstraints_32.weightx = 0;
		gridBagConstraints_32.insets = new Insets(10, 0, 0, 0);
		//gridBagConstraints_32.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_32.anchor = GridBagConstraints.WEST;
		gridBagConstraints_32.gridwidth = 1;
		gridBagConstraints_32.gridy = 2;
		gridBagConstraints_32.gridx = 1;
		gridBagConstraints_32.ipadx= 60;
		add(kwnoticerbox, gridBagConstraints_32);
		kwnoticerbox.addActionListener(new ComboboxListen4());
		
		 //关键词增减
 		final GridBagConstraints gridBagConstraints_13 = new GridBagConstraints();
		gridBagConstraints_13.weightx = 0;
		gridBagConstraints_13.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_13.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_13.anchor = GridBagConstraints.WEST;
		gridBagConstraints_13.gridwidth = 1;
		gridBagConstraints_13.gridy = 0;
		gridBagConstraints_13.gridx = 2;
		gridBagConstraints_13.ipadx= 100;
		add(keywords1, gridBagConstraints_13);
		keywords1.addMouseListener(tpm.getSharedInstance());  
		
		//关键词增
		final JButton kw1add=new JButton("添加");
		final GridBagConstraints gridBagConstraints_1ba = new GridBagConstraints();
		//gridBagConstraints_1ba.weighty = 0;
		gridBagConstraints_1ba.insets = new Insets(5, 5, 0, 0);
		//gridBagConstraints_1ba.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_1ba.gridwidth = 1;
		gridBagConstraints_1ba.gridy = 0;
		gridBagConstraints_1ba.gridx = 3;
		add(kw1add, gridBagConstraints_1ba);
		kw1add.addActionListener(new Keywords1Add());
		
		//关键词减
		final JButton kw1remove=new JButton("删除");
		final GridBagConstraints gridBagConstraints_1br = new GridBagConstraints();
		//gridBagConstraints_1br.weighty = 0;
		gridBagConstraints_1br.insets = new Insets(5, 5, 0, 0);
		//gridBagConstraints_1br.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_1br.gridwidth = 1;
		gridBagConstraints_1br.gridy = 0;
		gridBagConstraints_1br.gridx = 4;
		add(kw1remove, gridBagConstraints_1br);
		kw1remove.addActionListener(new Keywords1Remove());
		
		 //敏感词增减
 		final GridBagConstraints gridBagConstraints_23 = new GridBagConstraints();
		gridBagConstraints_23.weightx = 0;
		gridBagConstraints_23.insets = new Insets(10, 20, 0, 0);
		//gridBagConstraints_23.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_23.anchor = GridBagConstraints.WEST;
		gridBagConstraints_23.gridwidth = 1;
		gridBagConstraints_23.gridy = 1;
		gridBagConstraints_23.gridx = 2;
		gridBagConstraints_23.ipadx= 100;
		add(keywords2, gridBagConstraints_23);
		keywords2.addMouseListener(tpm.getSharedInstance());  
		
		//敏感词增
		final JButton kw2add=new JButton("添加");
		final GridBagConstraints gridBagConstraints_2ba = new GridBagConstraints();
		//gridBagConstraints_2ba.weighty = 0;
		gridBagConstraints_2ba.insets = new Insets(10, 5, 0, 0);
		//gridBagConstraints_2ba.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_2ba.gridwidth = 1;
		gridBagConstraints_2ba.gridy = 1;
		gridBagConstraints_2ba.gridx = 3;
		add(kw2add, gridBagConstraints_2ba);
		kw2add.addActionListener(new Keywords2Add());
		
		//敏感词减
		final JButton kw2remove=new JButton("删除");
		final GridBagConstraints gridBagConstraints_2br = new GridBagConstraints();
		//gridBagConstraints_2br.weighty = 0;
		gridBagConstraints_2br.insets = new Insets(10, 5, 0, 0);
		//gridBagConstraints_2br.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_2br.gridwidth = 1;
		gridBagConstraints_2br.gridy = 1;
		gridBagConstraints_2br.gridx = 4;
		add(kw2remove, gridBagConstraints_2br);
		kw2remove.addActionListener(new Keywords2Remove());
		
		 //业务管理员增减
 		final GridBagConstraints gridBagConstraints_33 = new GridBagConstraints();
		gridBagConstraints_33.weightx = 0;
		gridBagConstraints_33.insets = new Insets(10, 20, 0, 0);
		//gridBagConstraints_33.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_33.anchor = GridBagConstraints.WEST;
		gridBagConstraints_33.gridwidth = 1;
		gridBagConstraints_33.gridy = 2;
		gridBagConstraints_33.gridx = 2;
		gridBagConstraints_33.ipadx= 100;
		add(kwnoticert, gridBagConstraints_33);
		kwnoticert.addMouseListener(tpm.getSharedInstance());  
		
		//业务管理员增
		final JButton kw3add=new JButton("添加");
		final GridBagConstraints gridBagConstraints_3ba = new GridBagConstraints();
		//gridBagConstraints_3ba.weighty = 0;
		gridBagConstraints_3ba.insets = new Insets(10, 5, 0, 0);
		//gridBagConstraints_3ba.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_3ba.gridwidth = 1;
		gridBagConstraints_3ba.gridy = 2;
		gridBagConstraints_3ba.gridx = 3;
		add(kw3add, gridBagConstraints_3ba);
		kw3add.addActionListener(new Keywords4Add());
		
		//业务管理员减
		final JButton kw3remove=new JButton("删除");
		final GridBagConstraints gridBagConstraints_3br = new GridBagConstraints();
		//gridBagConstraints_3br.weighty = 0;
		gridBagConstraints_3br.insets = new Insets(10, 5, 0, 0);
		//gridBagConstraints_3br.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_3br.gridwidth = 1;
		gridBagConstraints_3br.gridy = 2;
		gridBagConstraints_3br.gridx = 4;
		add(kw3remove, gridBagConstraints_3br);
		kw3remove.addActionListener(new Keywords4Remove());
		
		//间隔时间设定
        JLabel time=new JLabel("时间设定（分）:");
		final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
		//gridBagConstraints_15.weighty = 0;
		gridBagConstraints_15.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_15.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_15.gridwidth = 2;
		gridBagConstraints_15.gridy = 0;
		gridBagConstraints_15.gridx = 5;
		add(time, gridBagConstraints_15);
		
		final GridBagConstraints gridBagConstraints_16 = new GridBagConstraints();
		gridBagConstraints_16.weightx = 0;
		gridBagConstraints_16.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_16.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_16.anchor = GridBagConstraints.WEST;
		gridBagConstraints_16.gridwidth = 1;
		gridBagConstraints_16.gridy = 0;
		gridBagConstraints_16.gridx = 6;
		gridBagConstraints_16.ipadx= 30;
		add(timet, gridBagConstraints_16);
		timet.setText("10");
		
		
		//最大页数设定
        JLabel maxpages=new JLabel("最大页数（20）:");
		final GridBagConstraints gridBagConstraints_25 = new GridBagConstraints();
		//gridBagConstraints_25.weighty = 0;
		gridBagConstraints_25.insets = new Insets(10, 20, 0, 0);
		//gridBagConstraints_25.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_25.gridwidth = 2;
		gridBagConstraints_25.gridy = 1;
		gridBagConstraints_25.gridx = 5;
		add(maxpages, gridBagConstraints_25);
		
		final GridBagConstraints gridBagConstraints_26 = new GridBagConstraints();
		gridBagConstraints_26.weightx = 0;
		gridBagConstraints_26.insets = new Insets(10, 0, 0, 0);
		//gridBagConstraints_26.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_26.anchor = GridBagConstraints.WEST;
		gridBagConstraints_26.gridwidth = 1;
		gridBagConstraints_26.gridy = 1;
		gridBagConstraints_26.gridx = 6;
		gridBagConstraints_26.ipadx= 30;
		add(maxpagest, gridBagConstraints_26);
		
		//定时开始
		final JButton timeup=new JButton("开始监控");
		final GridBagConstraints gridBagConstraints_1bt = new GridBagConstraints();
		//gridBagConstraints_1bt.weighty = 0;
		gridBagConstraints_1bt.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_1bt.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_1bt.gridwidth = 1;
		gridBagConstraints_1bt.gridy = 0;
		gridBagConstraints_1bt.gridx = 7;
		add(timeup, gridBagConstraints_1bt);
		timeup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean is=true;
				setGoon(is);
				//生产环境时间设定
				timecontrol=Integer.parseInt(timet.getText())*1000*60;
				//测试环境时间设定
				//timecontrol=Integer.parseInt(timet.getText())*1000;
				timer.schedule(new MyTask(), 1000, timecontrol);
				timeralert.schedule(new MyTaskAlert(), 120000, timecontrol);
				timeralert.schedule(new MyTaskOperation(), 200000, timecontrol*3);
				System.out.println(timecontrol); 
				count=0;
				runtime=0;
				timeup.setForeground(Color.red);
				
			}	
		});
		
		//定时结束
		final JButton timedown=new JButton("停止监控");
		final GridBagConstraints gridBagConstraints_2bt = new GridBagConstraints();
		//gridBagConstraints_2bt.weighty = 0;
		gridBagConstraints_2bt.insets = new Insets(10, 20, 0, 0);
		//gridBagConstraints_2bt.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_2bt.gridwidth = 1;
		gridBagConstraints_2bt.gridy = 1;
		gridBagConstraints_2bt.gridx = 7;
		add(timedown, gridBagConstraints_2bt);
		timedown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(timecontrol); 
				boolean is=false;
				count=0;
				runtime=0;
				setGoon(is);
				timeup.setForeground(Color.black);
				timecontrol=-1;
				System.out.println(timecontrol); 
			}	
		});
		
		//记录检索微博条数（暂取消）
        JLabel items1=new JLabel("微博数目:");
		//final GridBagConstraints gridBagConstraints_31 = new GridBagConstraints();
		//gridBagConstraints_31.weighty = 0;
		gridBagConstraints_31.insets = new Insets(10, 5, 0, 0);
		//gridBagConstraints_31.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_31.gridwidth = 2;
		gridBagConstraints_31.gridy = 2;
		gridBagConstraints_31.gridx = 0;
		//add(items1, gridBagConstraints_31);
		
		//final GridBagConstraints gridBagConstraints_32 = new GridBagConstraints();
		gridBagConstraints_32.weightx = 0;
		gridBagConstraints_32.insets = new Insets(10, 0, 0, 0);
		//gridBagConstraints_32.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_32.anchor = GridBagConstraints.WEST;
		gridBagConstraints_32.gridwidth = 1;
		gridBagConstraints_32.gridy = 2;
		gridBagConstraints_32.gridx = 1;
		gridBagConstraints_32.ipadx= 60;
		//add(itemst1, gridBagConstraints_32);
		
		//记录跑批次数
        JLabel runtime=new JLabel("接口调用次数:");
		final GridBagConstraints gridBagConstraints_35 = new GridBagConstraints();
		//gridBagConstraints_35.weighty = 0;
		gridBagConstraints_35.insets = new Insets(10, 5, 0, 0);
		//gridBagConstraints_35.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_35.gridwidth = 2;
		gridBagConstraints_35.gridy = 2;
		gridBagConstraints_35.gridx = 5;
		add(runtime, gridBagConstraints_35);
		
		final GridBagConstraints gridBagConstraints_36 = new GridBagConstraints();
		gridBagConstraints_36.weightx = 0;
		gridBagConstraints_36.insets = new Insets(10, 0, 0, 0);
		//gridBagConstraints_36.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_36.anchor = GridBagConstraints.WEST;
		gridBagConstraints_36.gridwidth = 1;
		gridBagConstraints_36.gridy = 2;
		gridBagConstraints_36.gridx = 6;
		gridBagConstraints_36.ipadx= 30;
		add(runtimet, gridBagConstraints_36);
		
		final JButton deletea1=tm.CreateButtonA("删除全部",model1,table1);
		final GridBagConstraints gridBagConstraints_19 = new GridBagConstraints();
		//gridBagConstraints_19.weighty = 0;
		gridBagConstraints_19.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_19.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_19.gridwidth = 1;
		gridBagConstraints_19.gridy = 0;
		gridBagConstraints_19.gridx = 9;
		add(deletea1, gridBagConstraints_19);
		
		final JButton deletea2=tm.CreateButtonS("删除单条",model1,table1);
		final GridBagConstraints gridBagConstraints_29 = new GridBagConstraints();
		//gridBagConstraints_29.weighty = 0;
		gridBagConstraints_29.insets = new Insets(10, 20, 0, 0);
		//gridBagConstraints_29.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_29.gridwidth = 1;
		gridBagConstraints_29.gridy = 1;
		gridBagConstraints_29.gridx = 9;
		add(deletea2, gridBagConstraints_29);
		
		//预选数据表
		final GridBagConstraints gridBagConstraints_t1 = new GridBagConstraints();
		gridBagConstraints_t1.weightx = 0.8;
		gridBagConstraints_t1.weighty = 1.0;
		gridBagConstraints_t1.insets = new Insets(5, 10, 10, 10);
		gridBagConstraints_t1.fill = GridBagConstraints.BOTH;
		gridBagConstraints_t1.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_t1.gridwidth = 12;
		gridBagConstraints_t1.gridheight = 2;
		gridBagConstraints_t1.gridy = 4;
		gridBagConstraints_t1.gridx = 0;
		gridBagConstraints_t1.ipadx = 35;
		gridBagConstraints_t1.ipady = -195;
		add(scrollPane1, gridBagConstraints_t1);
		
		//记录敏感微博条数
        JLabel items2=new JLabel("微博数目:");
		final GridBagConstraints gridBagConstraints_51 = new GridBagConstraints();
		//gridBagConstraints_51.weighty = 0;
		gridBagConstraints_51.insets = new Insets(10, 5, 0, 0);
		//gridBagConstraints_51.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_51.gridwidth = 2;
		gridBagConstraints_51.gridy = 6;
		gridBagConstraints_51.gridx = 5;
		add(items2, gridBagConstraints_51);
		
		final GridBagConstraints gridBagConstraints_52 = new GridBagConstraints();
		gridBagConstraints_52.weightx = 0;
		gridBagConstraints_52.insets = new Insets(10, 0, 0, 0);
		//gridBagConstraints_52.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_52.anchor = GridBagConstraints.WEST;
		gridBagConstraints_52.gridwidth = 1;
		gridBagConstraints_52.gridy = 6;
		gridBagConstraints_52.gridx = 6;
		gridBagConstraints_52.ipadx= 60;
		add(itemst2, gridBagConstraints_52);
		
		 //通知用户增减
 		final GridBagConstraints gridBagConstraints_63 = new GridBagConstraints();
		gridBagConstraints_63.weightx = 0;
		gridBagConstraints_63.insets = new Insets(10, 20, 0, 0);
		//gridBagConstraints_63.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_63.anchor = GridBagConstraints.WEST;
		gridBagConstraints_63.gridwidth = 1;
		gridBagConstraints_63.gridy = 6;
		gridBagConstraints_63.gridx = 2;
		gridBagConstraints_63.ipadx= 100;
		add(noticert, gridBagConstraints_63);
		noticert.addMouseListener(tpm.getSharedInstance());  
		
		//通知用户增
		final JButton nt2add=new JButton("添加");
		final GridBagConstraints gridBagConstraints_6ba = new GridBagConstraints();
		//gridBagConstraints_6ba.weighty = 0;
		gridBagConstraints_6ba.insets = new Insets(10, 5, 0, 0);
		//gridBagConstraints_6ba.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_6ba.gridwidth = 1;
		gridBagConstraints_6ba.gridy = 6;
		gridBagConstraints_6ba.gridx = 3;
		add(nt2add, gridBagConstraints_6ba);
		nt2add.addActionListener(new Keywords3Add());
		
		//通知用户减
		final JButton nt2remove=new JButton("删除");
		final GridBagConstraints gridBagConstraints_6br = new GridBagConstraints();
		//gridBagConstraints_6br.weighty = 0;
		gridBagConstraints_6br.insets = new Insets(10, 5, 0, 0);
		//gridBagConstraints_6br.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_6br.gridwidth = 1;
		gridBagConstraints_6br.gridy = 6;
		gridBagConstraints_6br.gridx = 4;
		add(nt2remove, gridBagConstraints_6br);
		nt2remove.addActionListener(new Keywords3Remove());
		
	      //关键词下拉框
        JLabel naticername=new JLabel("系统管理员:");
		final GridBagConstraints gridBagConstraints_6n = new GridBagConstraints();
		//gridBagConstraints_6n.weighty = 0;
		gridBagConstraints_6n.insets = new Insets(10, 10, 0, 0);
		//gridBagConstraints_6n.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_6n.gridwidth = 2;
		gridBagConstraints_6n.gridy = 6;
		gridBagConstraints_6n.gridx = 0;
		add(naticername, gridBagConstraints_6n);
		
		final GridBagConstraints gridBagConstraints_6b = new GridBagConstraints();
		gridBagConstraints_6b.weightx = 0;
		gridBagConstraints_6b.insets = new Insets(10, 0, 0, 0);
		//gridBagConstraints_6b.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_6b.anchor = GridBagConstraints.WEST;
		gridBagConstraints_6b.gridwidth = 1;
		gridBagConstraints_6b.gridy = 6;
		gridBagConstraints_6b.gridx = 1;
		gridBagConstraints_6b.ipadx= 60;
		add(noticerbox, gridBagConstraints_6b);
		noticerbox.addActionListener(new ComboboxListen3());
		//初始化
		ComboboxInitialCC(noticerbox);
		
		final JButton deletea3=tm.CreateButtonA("删除全部",model2,table2);
		final GridBagConstraints gridBagConstraints_59 = new GridBagConstraints();
		//gridBagConstraints_59.weighty = 0;
		gridBagConstraints_59.insets = new Insets(10, 0, 0, 0);
		//gridBagConstraints_59.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_59.gridwidth = 1;
		gridBagConstraints_59.gridy = 6;
		gridBagConstraints_59.gridx = 7;
		add(deletea3, gridBagConstraints_59);
		
		final JButton deletea4=tm.CreateButtonS("删除单条",model2,table2);
		final GridBagConstraints gridBagConstraints_58 = new GridBagConstraints();
		//gridBagConstraints_58.weighty = 0;
		gridBagConstraints_58.insets = new Insets(10, 20, 0, 0);
		//gridBagConstraints_58.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_58.gridwidth = 1;
		gridBagConstraints_58.gridy = 6;
		gridBagConstraints_58.gridx = 9;
		add(deletea4, gridBagConstraints_58);
		
		//预选数据表
		final GridBagConstraints gridBagConstraints_t2 = new GridBagConstraints();
		gridBagConstraints_t2.weightx = 0.8;
		gridBagConstraints_t2.weighty = 1.0;
		gridBagConstraints_t2.insets = new Insets(5, 10, 10, 10);
		gridBagConstraints_t2.fill = GridBagConstraints.BOTH;
		gridBagConstraints_t2.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_t2.gridwidth = 12;
		gridBagConstraints_t2.gridheight = 2;
		gridBagConstraints_t2.gridy = 7;
		gridBagConstraints_t2.gridx = 0;
		gridBagConstraints_t2.ipadx = 35;
		gridBagConstraints_t2.ipady = -195;
		add(scrollPane2, gridBagConstraints_t2);
	}
		
	public void ComboboxInitial(JComboBox keywordsbox){
		ResultSet rt = dao.SqlExcute("SELECT KeyWords FROM tb_FilterKeywords"); 
		try {
			while(rt.next()){ 
				String kws=rt.getString("KeyWords").trim();
				if(kws.equals("")==false){
					keywordsbox.addItem(kws);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<keywordsbox.getItemCount();i++){
			System.out.println(keywordsbox.getItemAt(i)+"  "+i);
		}
	}
	
	//联系人combobox初始化,txt
	public void ComboboxInitialCC(JComboBox keywordsbox){
		String str = readTxtFile(path,txtname);
		if(str.equals("")==false){
			String temp[]=str.split(",");
			for(int i=0;i<temp.length;i++){
				if(temp[i]!=""){
					noticerbox.addItem(temp[i]+"");
				}
			}
		}
	}
	
	//Keywords1侦听，初始化Keywords2，keywords3
	public class ComboboxListen1 implements ActionListener {
		public void actionPerformed(ActionEvent el){
			String newItem = keywordsbox1.getSelectedItem()+"";
			keywordsbox2.removeAllItems();
			kwnoticerbox.removeAllItems();
			keywords1.setText(newItem);
			ResultSet rt = dao.SqlExcute("SELECT KeyWords,SensitiveWords,AlertContactor FROM tb_FilterKeywords where KeyWords= '"+ newItem +"'"); 
			try {
				while(rt.next()){ 
					String kws = rt.getString("SensitiveWords");
					String nts = rt.getString("AlertContactor");
					
					if(kws!=null){
						String stws[]=kws.split(",");
						for(int i=0;i<stws.length;i++){
							if(stws[i].equals("")==false){
								keywordsbox2.addItem(stws[i]);
							}
						}
					}	
					
					if(nts!=null){
						String snts[]=nts.split(",");
						for(int i=0;i<snts.length;i++){
							if(snts[i].equals("")==false){
								kwnoticerbox.addItem(snts[i]);
							}
						}
					}	
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//Keywords2侦听
	public class ComboboxListen2 implements ActionListener {
		public void actionPerformed(ActionEvent el){
			String newItem = keywordsbox2.getSelectedItem()+"";
			keywords2.setText(newItem);
		}
	}
	
	//contactor侦听
	public class ComboboxListen3 implements ActionListener {
		public void actionPerformed(ActionEvent el){
			String newItem = noticerbox.getSelectedItem()+"";
			noticert.setText(newItem);
		}
	}
	
	//kwcontactor侦听
	public class ComboboxListen4 implements ActionListener {
		public void actionPerformed(ActionEvent el){
			String newItem = kwnoticerbox.getSelectedItem()+"";
			kwnoticert.setText(newItem);
		}
	}
	
	
	/** *********************combobox增减方法************************* */
	
	
	//Keywords1增，写数据库
	public class Keywords1Add implements ActionListener {
		public void actionPerformed(ActionEvent el){
			int check=0;
			if(keywords1.getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "请在文本框输入词组！", "提示", JOptionPane.ERROR_MESSAGE);
			}else{
				int items=keywordsbox1.getItemCount();
				for(int i=0;i<items;i++){
					if(keywordsbox1.getItemAt(i).equals(keywords1.getText())){
						check++;
					}
				}
				if(check==0){
					keywordsbox1.addItem(keywords1.getText());
					dao.insert("insert into tb_FilterKeywords (KeyWords) values ('"+ keywords1.getText() +"' )");
				}
			}
		}
	}
	
	//Keywords1减，删数据库
	public class Keywords1Remove implements ActionListener {
		public void actionPerformed(ActionEvent el){
			if(keywords1.getText().isEmpty()==false){
				String str=keywords1.getText();
				int check= JOptionPane.showConfirmDialog(null,   "与 “"+str+"”相关记录将全部删除，您确定删除？",   "系统提示 ",   JOptionPane.YES_NO_OPTION,   JOptionPane.QUESTION_MESSAGE); 
				if(check==JOptionPane.YES_OPTION){
					keywordsbox1.removeItem(str);
					dao.update("Delete FROM tb_FilterKeywords where KeyWords = '"+ str +"'");
				}
			}
		}
	}
	
	//kwnoticert减，改数据库
	public class Keywords4Remove implements ActionListener {
		public void actionPerformed(ActionEvent el){
			SensitiveNoticerRemove(kwnoticerbox,kwnoticert,"AlertContactor");
		}
	}
	
	//敏感词，告知用户减
	public void SensitiveNoticerRemove(JComboBox keywordsbox2,JTextField keywords2,String Words){
		if(keywords2.getText().isEmpty()==false){
			String str=keywords2.getText();
			keywordsbox2.removeItem(str);
			String strnew="";
			int itemsnew = keywordsbox2.getItemCount();
			for(int i=0;i<itemsnew-1;i++){
				strnew=strnew+keywordsbox2.getItemAt(i)+",";
				strnew=strnew.trim();
			}
			strnew=strnew+keywordsbox2.getItemAt(itemsnew-1);
			strnew=strnew.trim();
			dao.update("update tb_FilterKeywords set "+Words+"='" + 
				strnew	+ "' where KeyWords= '" + keywords1.getText()+"'");
		}
	}
	
	//敏感词，告知用户增
	public void SensitiveNoticerAdd(JComboBox keywordsbox2,JTextField keywords2,String words){
		int check=0;
		if(keywords2.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "请在文本框输入词组！", "提示", JOptionPane.ERROR_MESSAGE);
		}else{
			int items=keywordsbox2.getItemCount();
			for(int i=0;i<items;i++){
				if(keywordsbox2.getItemAt(i).equals(keywords2.getText())){
					check++;
				}
			}
			if(check==0){
				keywordsbox2.addItem(keywords2.getText().trim());
				String str="";
				int itemsnew = keywordsbox2.getItemCount();
				for(int i=0;i<itemsnew-1;i++){
					str=str+keywordsbox2.getItemAt(i)+",";
					str=str.trim();
				}
				str=str+keywordsbox2.getItemAt(itemsnew-1);
				str=str.trim();
				dao.update("update tb_FilterKeywords set "+ words +"=' "+ 
					str	+ "' where KeyWords= '" + keywords1.getText()+"'");
			}
		}
	}
	
	//Keywords2增，改数据库
	public class Keywords2Add implements ActionListener {
		public void actionPerformed(ActionEvent el){
			SensitiveNoticerAdd(keywordsbox2,keywords2,"SensitiveWords");
		}
	}
	//kwnoticer增，改数据库
	public class Keywords4Add implements ActionListener {
		public void actionPerformed(ActionEvent el){
			SensitiveNoticerAdd(kwnoticerbox,kwnoticert,"AlertContactor");
		}
	}
	
	
	//Keywords2减，改数据库
	public class Keywords2Remove implements ActionListener {
		public void actionPerformed(ActionEvent el){
			SensitiveNoticerRemove(keywordsbox2,keywords2,"SensitiveWords");
		}
	}
	
	//联系人增，改txt
	public class Keywords3Add implements ActionListener {
		public void actionPerformed(ActionEvent el){
			int check=0;
			if(noticert.getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "请在文本框输入词组！", "提示", JOptionPane.ERROR_MESSAGE);
			}else{
				int items=noticerbox.getItemCount();
				for(int i=0;i<items;i++){
					if(noticerbox.getItemAt(i).equals(noticert.getText())){
						check++;
					}
				}
				if(check==0){
					noticerbox.addItem(noticert.getText());
					String str="";
					int itemsnew = noticerbox.getItemCount();
					for(int i=0;i<itemsnew;i++){
						str=str+noticerbox.getItemAt(i)+",";
					}
					
					CreatTxt(path, txtname);
					try {
						writeTxtFile(str);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	//联系人减，改txt
	public class Keywords3Remove implements ActionListener {
		public void actionPerformed(ActionEvent el){
			if(noticert.getText().isEmpty()==false){
				String str=noticert.getText();
				noticerbox.removeItem(str);
				String strnew="";
				int itemsnew = noticerbox.getItemCount();
				for(int i=0;i<itemsnew;i++){
					strnew=strnew+noticerbox.getItemAt(i)+",";
				}
				CreatTxt(path, txtname);
				try {
					writeTxtFile(strnew);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/** *********************业务线程功能设定************************* */
	
	
	//定时器线程设置，数据抓取过滤线程
	public class MyTask extends TimerTask
	{
		public void run(){
			if(!isGoon()){
			}else{
				//清空上次监控数据
				TableCLear();
				System.out.println(count);
				int items=keywordsbox1.getItemCount();
				//获取本次监控关键词，count关键词计数
				String kws=keywordsbox1.getItemAt(count)+"";
				System.out.println(kws);
				//调用api，获取原始数据
				GetTrendStatus(kws);
				//UploadFile();
				//原始数据过滤，敏感词微博检索
				List<String> SensitiveWords = SensitiveWordsReturn(kws);
				SensitiveWordsFilter(SensitiveWords);
				//关键词计数器加1
				count++;
				//接口调用次数加1
				runtime++;
				//关键词循环
				if(count>=items){
					count=0;
				}
				System.out.println(count);
				int rows1=table1.getModel().getRowCount();
				//显示关键词微博数
				itemst1.setText(rows1+"");
				//显示接口调用次数
				runtimet.setText(runtime+"");
				int rows2=table2.getModel().getRowCount();
				//显示敏感词微博数
				itemst2.setText(rows2+"");
				SensitiveWords = null;
				//发布告警微博
				//String content="@乌岚布和,@达巴赖马  "+count+","+rows2+","+formatdate.format(new Date());
				//UpdateStatus(content);
				//写监控日志
				LogAdd();
				//写敏感词监控表
				InsertFunctionSA(table2,model2);
				//写关键词监控表
				InsertFunctionSS(table1,model1);
				
			}
		}
	}
	//定时器线程设置，告警邮件线程
	public class MyTaskAlert extends TimerTask
	{
		public void run(){
			if(!isGoon()){
				
			}else{
				int max=keywordsbox1.getItemCount();
				int kwcount= count-1;
				if(count==0){
					kwcount=max-1;
				}
				String kws=keywordsbox1.getItemAt(kwcount)+"";
				AlertEmail(kws);
			}
		}
	}
	//定时器线程设置，系统运行邮件线程
	public class MyTaskOperation extends TimerTask
	{
		public void run(){
			if(!isGoon()){
				
			}else{
				try {
					OperationEmail();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public void setGoon(boolean goon) {
		this.goon = goon;
	}
		
	public boolean isGoon() {
		return goon;
	}
	
	//获取关键词微博
	public void GetTrendStatus(String keywords){
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		try {
			Weibo weibo = new Weibo();
			weibo.setToken(Weibo.getat(),Weibo.getats());
			SimpleStatistics ss=new SimpleStatistics();
			int end=Integer.parseInt(maxpagest.getText());
			String intime=formatdate.format(new Date());
			for(int i=1;i<=end;i++)
			{	
				//采用cursor参数处理翻页
				List<Status> status = weibo.getTrendStatus(keywords,new Paging(i,20));
				for(Status statuses:status){
						String sid=statuses.getId()+"";
						String stm=formatdate.format(statuses.getCreatedAt());
						String stx=statuses.getText().replace("'","“");
						String ssc=ss.SourceExtract(statuses.getSource()); 
						String sune=statuses.getUser().getName();
						String suid=statuses.getUser().getId()+"";
						String sulc=statuses.getUser().getLocation();
						int supc=statuses.getUser().getProvince();
						int suct=statuses.getUser().getCity();
						String suca=formatdate.format(statuses.getUser().getCreatedAt());
						String sugd=statuses.getUser().getGender();
						int sufl=statuses.getUser().getFollowersCount();
						int sufd=statuses.getUser().getFriendsCount();
						int susn=statuses.getUser().getStatusesCount();
						String suvd=statuses.getUser().isVerified()+"";
						//原创微博信息，若无显示为空
						String sorg="Y";
						String soid="";
						String sotm="";
						String sotx="";
						String sosc=""; 
						String soune="";
						String souid="";
						String soulc="";
						int soupc=-1;
						int souct=-1;
						String souca="";
						String sougd="";
						int soufl=-1;
						int soufd=-1;
						int sousn=-1;
						String souvd="";
									
						if(statuses.getRetweeted_status()!=null){
							sorg="N";
							soid=statuses.getRetweeted_status().getId()+"";
							sotm=formatdate.format(statuses.getRetweeted_status().getCreatedAt());
							sotx=statuses.getRetweeted_status().getText().replace("'","“");;
							sosc=ss.SourceExtract(statuses.getRetweeted_status().getSource());; 
							soune=statuses.getRetweeted_status().getUser().getName();
							souid=statuses.getRetweeted_status().getUser().getId()+"";
							soulc=statuses.getRetweeted_status().getUser().getLocation();
							soupc=statuses.getRetweeted_status().getUser().getProvince();
							souct=statuses.getRetweeted_status().getUser().getCity();
							souca=formatdate.format(statuses.getRetweeted_status().getUser().getCreatedAt());
							sougd=statuses.getRetweeted_status().getUser().getGender();
							soufl=statuses.getRetweeted_status().getUser().getFollowersCount();
							soufd=statuses.getRetweeted_status().getUser().getFriendsCount();
							sousn=statuses.getRetweeted_status().getUser().getStatusesCount();
							souvd=statuses.getRetweeted_status().getUser().isVerified()+"";
						}
						
						Object[] str_row2 = {sid,stm,stx,ssc,sune,suid,sulc,supc,suct,suca,sugd,sufl,sufd,susn,suvd,sorg,soid,sotm,sotx,sosc,soune,souid,soulc,soupc,souct,souca,sougd,soufl,soufd,sousn,souvd,keywords,intime};  
						model1.addRow(str_row2);
						//model2.addRow(str_row2);
				}
			}
		} catch (WeiboException e) {
					e.printStackTrace();
		}
	}
	
	//根据keywords，返回sensitive
	public List<String> SensitiveWordsReturn(String keywords){
		List<String> SensitiveWords = new ArrayList<String>();
		ResultSet rt = dao.SqlExcute("SELECT KeyWords,SensitiveWords FROM tb_FilterKeywords where KeyWords= '"+ keywords +"'"); 
		try {
			while(rt.next()){ 
				String kws = rt.getString("SensitiveWords");
				if(kws!=null){
					String stws[]=kws.split(",");
					for(int i=0;i<stws.length;i++){
						if(stws[i].equals("")==false){
							SensitiveWords.add(stws[i]);
						}
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SensitiveWords;
	}
	
	//关键词微博，敏感词过滤
	public void SensitiveWordsFilter(List<String> SensitiveWords){
		int rows=table1.getModel().getRowCount();
		for(int i=0;i<rows;i++){
			String str=table1.getValueAt(i, 2)+"";
			String result = ss.WordsSearch(str, SensitiveWords);
			if(result!=null){
				Object temp[]=new Object[34];
				for(int j=0;j<32;j++){
					temp[j]=table1.getValueAt(i, j)+"";
				}
				String[] fuck=result.split(",");
				String bitch="";
				for(int k=1;k<fuck.length-1;k++){
					bitch=bitch+fuck[k]+",";
				}
				bitch=bitch+fuck[fuck.length-1];
				temp[33]=bitch;
				String intime=formatdate.format(new Date());
				temp[32]=intime;
				model2.addRow(temp);
			}
		}
	}
	
	//表清空
	public void TableCLear() {
		if(table1.getModel().getRowCount()>0)
		{
			for (int index = model1.getRowCount() - 1; index >= 0; index--) {
				model1.removeRow(index);
			}
		}
		if(table2.getModel().getRowCount()>0)
		{
			for (int index = model2.getRowCount() - 1; index >= 0; index--) {
				model2.removeRow(index);
			}
		}
	}
	
	//系统运行邮件发送(监控表最近10条数据)
	public void OperationEmail() throws UnsupportedEncodingException{
		ResultSetMetaData rsmd=null;
		String etimenowt=eformatdate.format(new Date());
		ResultSet rt=null;
		rt = dao.SqlExcute("SELECT top 10 * FROM " +
				"tb_MonitorLog WHERE ID>0 Order by ID desc");  
		try {
			rsmd = rt.getMetaData();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String content="<tr>";
		String title="<tr>" +
				"<th align=\"center\">关键词</th>" +
				"<th align=\"center\">运行时间</th>" +
				"<th align=\"center\">关键词微博</th>" +
				"<th align=\"center\">敏感词微博</th>" +
				"<th align=\"center\">敏感词统计</th>" + 
				"</tr>\r\n";
		try {
			while(rt.next()){ 
				for(int j=2;j<6;j++){
					String str=new String(rt.getString(rsmd.getColumnName(j)));
					content=content+"<td align=\"center\">"+str+"</td>";
				}
				
				String st=new String(rt.getString(rsmd.getColumnName(6)));
				content=content+"<td align=\"center\">"+ st +"</td></tr>"+"\r\n"+"<tr>";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  	}  
		try {
  			rt.close();
  		} catch (SQLException e) {
		// TODO Auto-generated catch block
  			e.printStackTrace();	
	  	}
  		rt=null;
  		String contents="<table border=\"1\">"+title+content+"</table>";
  		System.out.println(contents);
  		
  		SendEmail se=new SendEmail();
  		String titles= "系统运行监控"+etimenowt;
  		//获取系统管理员邮箱
  		int opno=noticerbox.getItemCount();
  		String[] opnot=new String[opno];
  		for(int i=0;i<opno;i++){
  			String name=noticerbox.getItemAt(i)+"";
  			if(name.length()>3){
  				opnot[i]=name;
  			}
  		}
  		se.EmailSend(opnot, titles, contents);
	}
	
	//警告邮件发送(2小时内敏感词微博)
	public void AlertEmail(String keywords){
		ResultSet rt=null;
		ResultSetMetaData rsmd=null;
		Date timecheck=ss.TimeAdd(new Date(),-2);
		String timenowt=formatdate.format(timecheck);
		String etimenowt=eformatdate.format(timecheck);
		rt = dao.SqlExcute("SELECT StatusCreateTime," +
				"UserName,SearchKey,AlertWords," +
				"StatusText,UserID,StatusId " +
				"FROM tb_StatusAlert " +
				"WHERE SearchKey ='"+ keywords +
				"' and StatusCreateTime>'"+ timenowt +"'");  
		try {
			rsmd = rt.getMetaData();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		
		if(rt!=null){
			String content="<p>";
			try {
				while(rt.next()){ 
					
					for(int j=1;j<5;j++){
						try {
							String str=new String(rt.getBytes(rsmd.getColumnName(j)),"gbk");
							content=content+str+", ";
							
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					String text=new String(rt.getString((rsmd.getColumnName(5))));
					String uid=new String(rt.getString((rsmd.getColumnName(6))));
					String sid=new String(rt.getString((rsmd.getColumnName(7))));
					String url ="<br><a href=\"http://api.t.sina.com.cn/"+ uid +"/statuses/"+ sid +"\" rel=\"nofollow\">"+ text +"</a></p>";
					content=content+ url +"   "+"\r\n<p>";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		  	}  
			try {
	  			rt.close();
	  		} catch (SQLException e) {
			// TODO Auto-generated catch block
	  			e.printStackTrace();	
		  	}
	  		rt=null;
	  		System.out.println(content);
	  		
	  		//判断内容是否为空，如为空，则不发邮件
	  		if(content.length()>5){
	  			SendEmail se=new SendEmail();
		  		String title= keywords+"报警"+etimenowt;
		  		
		  		//获取关键词告知用户邮件地址
		  		ResultSet rt1 = dao.SqlExcute("SELECT KeyWords," +
		  				"AlertContactor FROM tb_FilterKeywords " +
		  				"where KeyWords= '"+ keywords +"'"); 
		  		List<String> arrayList=new ArrayList<String>();
		  		try {
					while(rt1.next()){
						String kws = rt1.getString("AlertContactor");
						if(kws!=null){
							String stws[]=kws.split(",");
							for(int i=0;i<stws.length;i++){
								if(stws[i].equals("")==false){
									arrayList.add(stws[i]);
								}
							}
						}	
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		  		String[] receiver=arrayList.toArray(new String[arrayList.size()]);
		  		se.EmailSend(receiver, title, content);
	  		}
		}
	}
	//写查询日志
	public void LogAdd(){
		Date timenow=new Date();
		String keywords="";
		if(table1.getModel().getRowCount()>0){
			keywords=table1.getValueAt(0, 31)+"";
		}
		String timenowt=formatdate.format(timenow);
		//只存2小时记录
		Date timecheck=ss.TimeAdd(timenow,-2);
		//存储敏感词
		List<String> arrayListtemp = new ArrayList<String>();
		//记录关键词微博数目（2小时内）
		int KeyWordsNo=0;
		for(int i=0;i<table1.getModel().getRowCount();i++){
			String timetp=table1.getValueAt(i, 1)+"";
			try {
				Date timetpd=formatdate.parse(timetp);
				if(timetpd.after(timecheck)){
					KeyWordsNo++;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//记录敏感词微博数目，并分类统计（2小时内）
		int SensitiveWordsNo=0;
		for(int i=0;i<table2.getModel().getRowCount();i++){
			String timetp=table2.getValueAt(i, 1)+"";
			try {
				Date timetpd=formatdate.parse(timetp);
				if(timetpd.after(timecheck)){
					SensitiveWordsNo++;
					String str=table2.getValueAt(i, 33)+"";
					String[] temp=str.split(",");
					for(int j=0;j<temp.length;j++){
						if(temp[j]!="null" && temp[j]!=null){
							arrayListtemp.add(temp[j]);
						}
					}
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String WordsStatistics="";
		String[] result=ss.DuplicateDataDelete(arrayListtemp);
		for(int i=0;i<result.length;i++){
			int count = ss.ComputeNo(result[i], arrayListtemp);
			WordsStatistics=WordsStatistics+result[i]+","+count+",";
		}
		dao.insert("insert into tb_MonitorLog (KeyWords,RunTime," +
		  		"KeyWordsNo,SensitiveWordsNo,WordsStatistics) values ('"+ 
		  		keywords +"','"+ timenowt +"','"+ KeyWordsNo +"','"+ 
		  		SensitiveWordsNo+"','"+WordsStatistics+ "' )");
		  
		System.out.println(timenowt+" "+KeyWordsNo+" "+SensitiveWordsNo+" "+WordsStatistics);
	}
		
	//写关键词微博表（tb_StatusSearch）
	public void InsertFunctionSS(JTable table,DefaultTableModel model){
		int row = table.getModel().getRowCount();
		int column=table.getModel().getColumnCount();
		
		if(table.getModel().getRowCount()>0)
		  {
			  for (int i=0; i<row; i++) {
				  String strinsert="";
				  for(int j=0;j<column-1;j++){
					  String tb=table.getValueAt(i, j)+"";
					  strinsert=strinsert+tb+"','";
				  }
				  strinsert="'"+strinsert+table.getValueAt(i, column-1)+"'";
				  dao.insert("insert into tb_StatusSearch (StatusId,StatusCreateTime," +
				  		"StatusText,StatusSource,UserName,UserID,UserLocation,UserProvince," +
				  		"UserCity,UserCreateTime,UserGender,UserFans,UserFriends,UserStatusNo," +
				  		"UserVerify,StatusOriginal ,RetweetID,RetweetCreateTime,RetweetText," +
				  		"RetweetSource,RetweetUserName,RetweetUserID,RetweetUserLocation," +
				  		"RetweetUserProvince,RetweetUserCity,RetweetUserCreateTime,RetweetUserGender," +
				  		"RetweetUserFans,RetweetUserFriends,RetweetUserStatusNo,RetweetUserVerify,"+
						  "SearchKey,DbInsertTime,AlertWords) values ("+ strinsert +" )");
				  }
		  }
	}
	
	//写敏感词微博表（tb_StatusAlert）
	public void InsertFunctionSA(JTable table,DefaultTableModel model){
		int row = table.getModel().getRowCount();
		int column=table.getModel().getColumnCount();
		
		if(table.getModel().getRowCount()>0)
		  {
			  for (int i=0; i<row; i++) {
				  String strinsert="";
				  for(int j=0;j<column-1;j++){
					  String tb=table.getValueAt(i, j)+"";
					  strinsert=strinsert+tb+"','";
				  }
				  strinsert="'"+strinsert+table.getValueAt(i, column-1)+"'";
				  dao.insert("insert into tb_StatusAlert (StatusId,StatusCreateTime," +
				  		"StatusText,StatusSource,UserName,UserID,UserLocation,UserProvince," +
				  		"UserCity,UserCreateTime,UserGender,UserFans,UserFriends,UserStatusNo," +
				  		"UserVerify,StatusOriginal ,RetweetID,RetweetCreateTime,RetweetText," +
				  		"RetweetSource,RetweetUserName,RetweetUserID,RetweetUserLocation," +
				  		"RetweetUserProvince,RetweetUserCity,RetweetUserCreateTime,RetweetUserGender," +
				  		"RetweetUserFans,RetweetUserFriends,RetweetUserStatusNo,RetweetUserVerify,"+
						  "SearchKey,DbInsertTime,AlertWords) values ("+ strinsert +" )");
				  }
		  }
	}
	
	//发微博告警
	public void UpdateStatus(String content){
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		try {
			Weibo weibo = new Weibo();
			weibo.setToken(Weibo.getat(),Weibo.getats());
			Status status = weibo.updateStatus(content);
			System.out.println(status.getId() + " : "+ status.getText()+"  "+status.getCreatedAt());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//测试方法
	public void UploadFile(){
			try  
			{  
			Object[] rt=new Object[32];	
			Workbook book= Workbook.getWorkbook(new File(temp));  
			//获得第一个工作表对象  
			Sheet sheet=book.getSheet(0);
			int rows=sheet.getRows();
			int collumns=sheet.getColumns();
			//得到第一列第一行的单元格
			for(int j=1;j<rows;j++)
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
				model1.addRow(rt);
			}
			book.close();
			}catch(Exception e)  
			{  
			System.out.println(e);  
			} 
	}
	
	public void CreatTxt(String path, String txtname) {
		File txt = new File(path +"/"+txtname);
		txt.delete();
		try {
			txt.createNewFile();
			System.out.println(path);
			System.out.println(txtname+"文本文件创建成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String readTxtFile(String path, String txtname){
		readStr="";
		String read;
		FileReader fileread;
		File filename = new File(path +"/"+txtname);
			try {
				fileread = new FileReader(filename);
				BufferedReader bufread = new BufferedReader(fileread);
				try {
						while ((read = bufread.readLine()) != null) {
							readStr = read;
						}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("文件内容是:"+ "\r\n" + readStr);
				return readStr;
			}

	public void writeTxtFile(String readStr) throws IOException{
			//先读取原有文件内容，然后进行写入操作
		String filein = readStr;
		String nullstr="";
		RandomAccessFile mm = null;
			try {
				mm = new RandomAccessFile(path +"/"+txtname, "rw");
				mm.write(nullstr.getBytes());
				mm.write(filein.getBytes());
			} catch (IOException e1) {
				// TODO 自动生成 catch 块
				e1.printStackTrace();
			} finally {
				if (mm != null) {
					try {
						mm.close();
					} catch (IOException e2) {
						// TODO 自动生成 catch 块
						e2.printStackTrace();
					}
				}
			}
	}
}
