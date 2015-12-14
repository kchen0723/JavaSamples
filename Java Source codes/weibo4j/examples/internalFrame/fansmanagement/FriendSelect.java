package internalFrame.fansmanagement;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import statisticsModel.ChangeCode;
import statisticsModel.DataAccess;
import statisticsModel.MyTableModel;
import statisticsModel.SimpleStatistics;
import statisticsModel.TextComponentPopupMenu;
import weibo4j.User;
import weibo4j.Weibo;
import weibo4j.WeiboException;
import dao.dao;
import dao.daoFunction;

public class FriendSelect extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5970069439721779819L;
	String[] col1 = { "用户特征","用户名","用户ID","所在地区","认证","性别","粉丝数","关注数","微博数","更新时间","注册时间","微博ID" };
	String[] col2 = { "用户名","用户ID","所在地区","认证","性别","粉丝数","关注数","微博数","更新时间","关注对象" };
	public DefaultTableModel model1 = new DefaultTableModel();
	public JTable table1 = new JTable();
	public DefaultTableModel model2 = new DefaultTableModel();
	public JTable table2 = new JTable();
	JScrollPane scrollPane1=null;
	
	final static JTextField namet = new JTextField();//待搜索对象输入框 
	final static JTextField keywordst = new JTextField();//待搜索关键词输入框 	
	final static JFormattedTextField fannot = new JFormattedTextField(); //粉丝下限输入框
	final static JFormattedTextField statusnot = new JFormattedTextField(); //微博下限输入框
	final static JFormattedTextField friendsnot = new JFormattedTextField(); //关注上限输入框
	
	public JComboBox locationbox = new JComboBox();//地区选择下拉框
	final static JFormattedTextField refreshtimet = new JFormattedTextField(); //最近一次更新时间（xx天以内有更新）
	final static JFormattedTextField itemsonet = new JFormattedTextField(); //初选用户数目
	final static JFormattedTextField itemstwot = new JFormattedTextField(); //精选用户数目
	final static JFormattedTextField startpaget = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
	final static JFormattedTextField endpaget = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
	final static JFormattedTextField startpaget2 = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
	final static JFormattedTextField endpaget2 = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
	public String  zipname=null;
	public DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public String candidatelist=System.getProperty("user.dir")+"\\temp\\getcandidatelist.xls";
	public ChangeCode cc=new ChangeCode();
	public int[] norow={8,9,10};
	public int[] columns={500,120,100,80,80,50,50,50,50,150,150,80};
	public MyTableModel tm=new MyTableModel();
	public TextComponentPopupMenu tpm=new TextComponentPopupMenu();
	
	public FriendSelect(){
		super();
		setLayout(new GridBagLayout());
		setBounds(0, 0, 280, 236);
		SimpleStatistics ss=new SimpleStatistics();
		ss.ComboboxInitial(locationbox);
		
		//定义数据表1，初选表
		model1=tm.CreateModel(col1);
		table1 = tm.buildTable(model1);
		final TableRowSorter<DefaultTableModel> sorter1 =new TableRowSorter<DefaultTableModel>(model1);  
        table1.setRowSorter(sorter1);
        scrollPane1=new JScrollPane(table1);
        scrollPane1.setVisible(true);
        //固定表头
        table1.getTableHeader().setReorderingAllowed(false); 
        //添加水平滚动条
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table1.setColumnModel(tm.getColumn(table1, columns)); 
         
        
      //定义数据表2，精选表（无功能）
		model2=tm.CreateModel(col2);
		table2 = new JTable(model2);
		final TableRowSorter<DefaultTableModel> sorter2 =new TableRowSorter<DefaultTableModel>(model2);  
        table2.setRowSorter(sorter2);
        JScrollPane scrollPane2=new JScrollPane(table2);
        
        //第一行布局（搜索条件选项）
		JLabel name=new JLabel("输入用户名称：");//获取相关用户粉丝列表
		final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
		//gridBagConstraints_11.weighty = 0;
		gridBagConstraints_11.insets = new Insets(5, 10, 0, 0);
		//gridBagConstraints_11.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_11.gridwidth = 2;
		gridBagConstraints_11.gridy = 0;
		gridBagConstraints_11.gridx = 0;
		add(name, gridBagConstraints_11);
		
		final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
		gridBagConstraints_12.weightx = 0;
		gridBagConstraints_12.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_12.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_12.anchor = GridBagConstraints.WEST;
		gridBagConstraints_12.gridwidth = 1;
		gridBagConstraints_12.gridy = 0;
		gridBagConstraints_12.gridx = 1;
		gridBagConstraints_12.ipadx= 100;
		add(namet, gridBagConstraints_12);
		namet.addMouseListener(tpm.getSharedInstance()); 
		
		JLabel keywords=new JLabel("输入关键词：");//获取相关用户粉丝列表
		final GridBagConstraints gridBagConstraints_21 = new GridBagConstraints();
		//gridBagConstraints_21.weighty = 0;
		gridBagConstraints_21.insets = new Insets(5, 10, 0, 0);
		//gridBagConstraints_21.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_21.gridwidth = 2;
		gridBagConstraints_21.gridy = 1;
		gridBagConstraints_21.gridx = 0;
		add(keywords, gridBagConstraints_21);
		
		final GridBagConstraints gridBagConstraints_22 = new GridBagConstraints();
		gridBagConstraints_22.weightx = 0;
		gridBagConstraints_22.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_22.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_22.anchor = GridBagConstraints.WEST;
		gridBagConstraints_22.gridwidth = 1;
		gridBagConstraints_22.gridy = 1;
		gridBagConstraints_22.gridx = 1;
		gridBagConstraints_22.ipadx= 100;
		add(keywordst, gridBagConstraints_22);
		keywordst.addMouseListener(tpm.getSharedInstance()); 
		
		JLabel fanno=new JLabel("粉丝数目下限:");//设置目标粉丝粉丝数下限
		final GridBagConstraints gridBagConstraints_13 = new GridBagConstraints();
		//gridBagConstraints_14.weighty = 0;
		gridBagConstraints_13.insets = new Insets(5, 30, 0, 0);
		//gridBagConstraints_14.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_13.gridwidth = 1;
		gridBagConstraints_13.gridy = 0;
		gridBagConstraints_13.gridx = 2;
		add(fanno, gridBagConstraints_13);
		
		final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
		//gridBagConstraints_15.weighty = 0;
		gridBagConstraints_14.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_14.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_14.gridwidth = 1;
		gridBagConstraints_14.gridy = 0;
		gridBagConstraints_14.gridx = 3;
		gridBagConstraints_14.ipadx = 30;
		fannot.setText("400");
		add(fannot, gridBagConstraints_14);
		
		JLabel friendsno=new JLabel("关注数目上限:");//设置目标粉丝关注数上限
		final GridBagConstraints gridBagConstraints_23 = new GridBagConstraints();
		//gridBagConstraints_14.weighty = 0;
		gridBagConstraints_23.insets = new Insets(5, 30, 0, 0);
		//gridBagConstraints_14.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_23.gridwidth = 1;
		gridBagConstraints_23.gridy = 1;
		gridBagConstraints_23.gridx = 2;
		add(friendsno, gridBagConstraints_23);
		
		final GridBagConstraints gridBagConstraints_24 = new GridBagConstraints();
		//gridBagConstraints_15.weighty = 0;
		gridBagConstraints_24.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_24.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_24.gridwidth = 1;
		gridBagConstraints_24.gridy = 1;
		gridBagConstraints_24.gridx = 3;
		gridBagConstraints_24.ipadx = 30;
		friendsnot.setText("2000");
		add(friendsnot, gridBagConstraints_24);
		
		JLabel location=new JLabel("所在地区:");//设置目标粉丝所在地区
		final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
		//gridBagConstraints_14.weighty = 0;
		gridBagConstraints_15.insets = new Insets(5, 30, 0, 0);
		//gridBagConstraints_14.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_15.gridwidth = 1;
		gridBagConstraints_15.gridy = 0;
		gridBagConstraints_15.gridx = 6;
		add(location, gridBagConstraints_15);
		
		final GridBagConstraints gridBagConstraints_16 = new GridBagConstraints();
		//gridBagConstraints_15.weighty = 0;
		gridBagConstraints_16.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_16.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_16.gridwidth = 1;
		gridBagConstraints_16.gridy = 0;
		gridBagConstraints_16.gridx = 7;
		gridBagConstraints_16.ipadx = 20;
		add(locationbox, gridBagConstraints_16);
		
		JLabel statusno=new JLabel("微博数目下限:");//设置目标粉丝微博数下限
		final GridBagConstraints gridBagConstraints_25 = new GridBagConstraints();
		//gridBagConstraints_14.weighty = 0;
		gridBagConstraints_25.insets = new Insets(5, 30, 0, 0);
		//gridBagConstraints_14.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_25.gridwidth = 1;
		gridBagConstraints_25.gridy = 1;
		gridBagConstraints_25.gridx = 4;
		add(statusno, gridBagConstraints_25);
		
		final GridBagConstraints gridBagConstraints_26 = new GridBagConstraints();
		//gridBagConstraints_15.weighty = 0;
		gridBagConstraints_26.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_26.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_26.gridwidth = 1;
		gridBagConstraints_26.gridy = 1;
		gridBagConstraints_26.gridx = 5;
		gridBagConstraints_26.ipadx = 30;
		statusnot.setText("200");
		add(statusnot, gridBagConstraints_26);		
		
		JLabel refreshtime=new JLabel("更新时间(天):");//设置更新时间
		final GridBagConstraints gridBagConstraints_17 = new GridBagConstraints();
		//gridBagConstraints_14.weighty = 0;
		gridBagConstraints_17.insets = new Insets(5, 30, 0, 0);
		//gridBagConstraints_14.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_17.gridwidth = 1;
		gridBagConstraints_17.gridy = 0;
		gridBagConstraints_17.gridx = 4;
		add(refreshtime, gridBagConstraints_17);
		
		final GridBagConstraints gridBagConstraints_18 = new GridBagConstraints();
		//gridBagConstraints_15.weighty = 0;
		gridBagConstraints_18.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_18.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_18.gridwidth = 1;
		gridBagConstraints_18.gridy = 0;
		gridBagConstraints_18.gridx = 5;
		gridBagConstraints_18.ipadx = 30;
		refreshtimet.setText("7");
		add(refreshtimet, gridBagConstraints_18);
		
		final JButton fanget=new JButton("查询指定用户粉丝");
		final GridBagConstraints gridBagConstraints_19 = new GridBagConstraints();
		//gridBagConstraints_19.weighty = 0;
		gridBagConstraints_19.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_19.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_19.gridwidth = 1;
		gridBagConstraints_19.gridy = 0;
		gridBagConstraints_19.gridx = 8;
		add(fanget, gridBagConstraints_19);
		fanget.addActionListener(new GetFollowers());
		
		final JButton keywordsget=new JButton("查询关键词组用户");
		final GridBagConstraints gridBagConstraints_29 = new GridBagConstraints();
		//gridBagConstraints_29.weighty = 0;
		gridBagConstraints_29.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_29.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_29.gridwidth = 1;
		gridBagConstraints_29.gridy = 2;
		gridBagConstraints_29.gridx = 8;
		add(keywordsget, gridBagConstraints_29);
		keywordsget.addActionListener(new ButtonQuerryListener());
		
		final JButton prefriends=new JButton("保存预关注");
		final GridBagConstraints gridBagConstraints_pf = new GridBagConstraints();
		//gridBagConstraints_pf.weighty = 0;
		gridBagConstraints_pf.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_pf.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_pf.gridwidth = 2;
		gridBagConstraints_pf.gridy = 2;
		gridBagConstraints_pf.gridx = 6;
		gridBagConstraints_pf.ipadx = 20;
		add(prefriends, gridBagConstraints_pf);
		prefriends.addActionListener(new ButtonPrefriendsListener());
		
		//第二行布局，计算初步筛选用户数目，清空初选表按钮
		JLabel itemsone=new JLabel("筛选用户数目：");
		final GridBagConstraints gridBagConstraints_31 = new GridBagConstraints();
		//gridBagConstraints_31.weighty = 0;
		gridBagConstraints_31.insets = new Insets(5, 10, 0, 0);
		//gridBagConstraints_31.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_31.gridwidth = 2;
		gridBagConstraints_31.gridy = 2;
		gridBagConstraints_31.gridx = 0;
		add(itemsone, gridBagConstraints_31);
		
		final GridBagConstraints gridBagConstraints_32 = new GridBagConstraints();
		gridBagConstraints_32.weighty = 0;
		gridBagConstraints_32.insets = new Insets(5, 0, 0, 0);
		gridBagConstraints_32.anchor = GridBagConstraints.WEST;
		gridBagConstraints_32.gridwidth = 3;
		gridBagConstraints_32.gridy = 2;
		gridBagConstraints_32.gridx = 1;
		gridBagConstraints_32.ipadx= 50;
		add(itemsonet, gridBagConstraints_32);
		
		final JButton delall1=tm.CreateButtonA("列表全部清空",model1,table1);
		final GridBagConstraints gridBagConstraints_27 = new GridBagConstraints();
		gridBagConstraints_27.weightx = 0;
		gridBagConstraints_27.insets = new Insets(5, 20, 0, 0);
		gridBagConstraints_27.anchor = GridBagConstraints.EAST;
		gridBagConstraints_27.gridwidth = 1;
		gridBagConstraints_27.gridy = 0;
		gridBagConstraints_27.gridx = 9;
		add(delall1, gridBagConstraints_27);
		
		final JButton delall2=tm.CreateButtonS("列表单条删除",model1,table1);
		final GridBagConstraints gridBagConstraints_37 = new GridBagConstraints();
		gridBagConstraints_37.weightx = 0;
		gridBagConstraints_37.insets = new Insets(5, 20, 0, 0);
		gridBagConstraints_37.anchor = GridBagConstraints.EAST;
		gridBagConstraints_37.gridwidth = 1;
		gridBagConstraints_37.gridy = 2;
		gridBagConstraints_37.gridx = 9;
		add(delall2, gridBagConstraints_37);
				
		//第三行布局
		final GridBagConstraints gridBagConstraints_4t = new GridBagConstraints();
		gridBagConstraints_4t.weightx = 0.8;
		gridBagConstraints_4t.weighty = 1.0;
		gridBagConstraints_4t.insets = new Insets(10, 10, 0, 10);
		gridBagConstraints_4t.fill = GridBagConstraints.BOTH;
		gridBagConstraints_4t.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_4t.gridwidth = 12;
		gridBagConstraints_4t.gridheight = 1;
		gridBagConstraints_4t.gridy = 3;
		gridBagConstraints_4t.gridx = 0;
		gridBagConstraints_4t.ipadx = 35;
		gridBagConstraints_4t.ipady = -195;
		add(scrollPane1, gridBagConstraints_4t);
		
		//第四行布局，计算二次筛选用户数目，用户精选按钮，清空精选表按钮（不设功能）
		JLabel itemstwo=new JLabel("精选用户数目：");
		final GridBagConstraints gridBagConstraints_41 = new GridBagConstraints();
		//gridBagConstraints_41.weighty = 0;
		gridBagConstraints_41.insets = new Insets(10, 0, 0, 0);
		//gridBagConstraints_41.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_41.gridwidth = 1;
		//gridBagConstraints_41.gridheight = 1;
		gridBagConstraints_41.gridy = 3;
		gridBagConstraints_41.gridx = 0;
		//add(itemstwo, gridBagConstraints_41);
		
		final GridBagConstraints gridBagConstraints_42 = new GridBagConstraints();
		gridBagConstraints_42.weighty = 0;
		gridBagConstraints_42.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_42.anchor = GridBagConstraints.WEST;
		gridBagConstraints_42.gridwidth = 1;
		gridBagConstraints_42.gridy = 3;
		gridBagConstraints_42.gridx = 1;
		gridBagConstraints_42.ipadx= 50;
		//add(itemstwot, gridBagConstraints_42);
		
		final JButton fanselect=new JButton("目标用户精选");
		final GridBagConstraints gridBagConstraints_43 = new GridBagConstraints();
		//gridBagConstraints_43.weighty = 0;
		gridBagConstraints_43.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_43.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_43.gridwidth = 1;
		gridBagConstraints_43.gridy = 3;
		gridBagConstraints_43.gridx = 6;
		//add(fanselect, gridBagConstraints_43);
		
		final JButton delall3=tm.CreateButtonA("精选列表清空",model2,table2);
		final GridBagConstraints gridBagConstraints_44 = new GridBagConstraints();
		gridBagConstraints_44.weightx = 0;
		gridBagConstraints_44.insets = new Insets(5, 20, 0, 0);
		gridBagConstraints_44.anchor = GridBagConstraints.EAST;
		gridBagConstraints_44.gridwidth = 1;
		gridBagConstraints_44.gridy = 3;
		gridBagConstraints_44.gridx = 8;
		//add(delall2, gridBagConstraints_44);
		
		//第五行布局
		final GridBagConstraints gridBagConstraints_51 = new GridBagConstraints();
		gridBagConstraints_51.weightx = 0.8;
		gridBagConstraints_51.weighty = 1.0;
		gridBagConstraints_51.insets = new Insets(10, 10, 0, 10);
		gridBagConstraints_51.fill = GridBagConstraints.BOTH;
		gridBagConstraints_51.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_51.gridwidth = 12;
		gridBagConstraints_51.gridheight = 1;
		gridBagConstraints_51.gridy = 4;
		gridBagConstraints_51.gridx = 0;
		gridBagConstraints_51.ipadx = 35;
		gridBagConstraints_51.ipady = -195;
		//add(scrollPane2, gridBagConstraints_51);
		
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
	
	//获取初选用户列表
	public class GetFollowers implements ActionListener {
		public void actionPerformed(ActionEvent el){
			if(namet.getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "忘输入用户名称啦！", "提示", JOptionPane.ERROR_MESSAGE);
			}else{
				System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		    	try {
					Weibo weibo = new Weibo();
					weibo.setToken(Weibo.getat(),Weibo.getats());
					int end=weibo.showUser(namet.getText()).getFollowersCount()+300;
					if(end>4000){
						end=4000;
					}
					
					SimpleStatistics ss=new SimpleStatistics();
					
					for(int cursor = 1;cursor<=end;cursor=cursor+20)
					{	
					//采用cursor参数处理翻页
					List<User> list = weibo.getFollowersStatuses(namet.getText(),cursor);
						for(User user : list) {
							if(user.getStatus()!=null){
								Date rtime = user.getStatus().getCreatedAt();
								int province = user.getProvince();
								int fansno = user.getFollowersCount();
								int statusno = user.getStatusesCount();
								int friendsno = user.getFriendsCount();
								if(friendsno<Integer.parseInt(friendsnot.getText())&&statusno>Integer.parseInt(statusnot.getText())&&rtime.after(ss.DateConvert(refreshtimet.getText()))&&ss.LocationConvert(province,locationbox.getSelectedItem()+"")&&fansno>Integer.parseInt(fannot.getText())){
									String s1=user.getName();
									String s2=user.getId()+"";
									String s3=user.getLocation();
									String s4=user.isVerified()+""; 
									String s5=user.getGender();
									int s6=user.getFollowersCount();
									int s7=user.getFriendsCount();
									int s8=user.getStatusesCount();
									String s9=format.format(user.getStatus().getCreatedAt());
									String s10=format.format(user.getCreatedAt());
									String s0=namet.getText();	
									
									Object[] str_row = {s0,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10};  
									model1.addRow(str_row);
								}
							}
						}
					}
					itemsonet.setText(table1.getModel().getRowCount()+"");
				} catch (WeiboException e) {
					e.printStackTrace();
				}
				DataAccess da=new DataAccess();
			    da.ExcelCreate(candidatelist, model1, col1);
			}
		}
	}
	
	//关键字微博用户检索按钮
	public class ButtonQuerryListener implements ActionListener {
		  public void actionPerformed(ActionEvent evt) {
			  if(keywordst.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "忘输入关键词啦！", "提示", JOptionPane.ERROR_MESSAGE);
			  }else{
				  QueryFunction1(table1,model1);
			  }
		  }
	}
	
	//数据存预关注表，若用户已在黑名单，则不添加
	public class ButtonPrefriendsListener implements ActionListener {
		  public void actionPerformed(ActionEvent evt) {
			  PrefriendsFunction(table1,model1);
		  }
	}
	
	public void PrefriendsFunction(JTable table,DefaultTableModel model){
		int rows = table.getModel().getRowCount();
		int columns = table.getModel().getColumnCount();
		int friends = 0;
		int bitches =0;
		for(int i=0;i<rows;i++){
			int point=0;
			String temp = table.getValueAt(i, 2)+"";
			ResultSet rt = dao.SqlExcute("SELECT UserID FROM tb_BitchList where UserID = '"+temp+"'" ); 
			try {
				while(rt.next()){ 
					point++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(point==0){
				String strinsert="";
				for(int j=0;j<columns-1;j++){
					String tb=table.getValueAt(i, j)+"";
					strinsert=strinsert+tb+"','";
				}
				strinsert="'"+strinsert+table.getValueAt(i, columns-1)+"'";
				dao.insert("insert into tb_PreFriendsList (UserSource,UserName," +
						"UserID,UserLocation,UserVerify,UserGender,UserFans," +
						"UserFriends,UserStatusNo,UserUpdateTime,UserCreateTime," +
						"StatusId) values ("+ strinsert +" )");
				friends++;
			}else{
				bitches++;
			}
			try {
				rt.close();
	  			rt=null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		JOptionPane.showMessageDialog(null, "成功存入"+friends+"条数据进入预关注表，发现"+bitches+"条黑名单数据", "提示", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	public void QueryFunction1(JTable table,DefaultTableModel model){
		SimpleStatistics ss=new SimpleStatistics();
		ResultSet rt=null;
		daoFunction df=new daoFunction();
		//获取搜索关键字
		List<String> arrayListtemp = new ArrayList<String>();
		//简体字符转换繁体
		List<String> arrayListtempf = new ArrayList<String>();
		String temp[]=keywordst.getText().split(" ");
		for(int i=0;i<temp.length;i++){
			if(temp[i]!=""){
				arrayListtemp.add(temp[i]);
				arrayListtempf.add((cc.toShort(temp[i])));
			}
		}
				
		int fansno = Integer.parseInt(fannot.getText());
		int statusno = Integer.parseInt(statusnot.getText());
		int friendsno = Integer.parseInt(friendsnot.getText());
		
		rt = dao.SqlExcute("SELECT StatusId,StatusCreateTime,StatusText,UserProvince,UserFans,UserFriends,UserStatusNo FROM tb_StatusSearch where UserFans > "+fansno+" and  UserFriends < "+friendsno+" and UserStatusNo > "+statusno); 
	
		try {
			while(rt.next()){ 
				String strtime=rt.getString("StatusCreateTime");
				Date time = null;
				try {
					time = format.parse(strtime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int province=Integer.parseInt( rt.getString("UserProvince"));
				if(ss.LocationConvert(province,locationbox.getSelectedItem()+"")&&time.after(ss.DateConvert(refreshtimet.getText()))&&ss.KeyWordsSearch(arrayListtemp, arrayListtempf, rt.getString("StatusText"))){
					String sid=rt.getString("StatusId");
					String sql1="SELECT ID,StatusText,UserName,UserID,UserLocation,UserVerify,UserGender,UserFans,UserFriends,UserStatusNo,StatusCreateTime,UserCreateTime,StatusId FROM tb_StatusSearch where StatusId='"+sid+"'";
					df.RowSingleWrite(model1, sql1,norow,12,2);
				}
				
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
  		int rows=table.getModel().getRowCount();
  		itemsonet.setText(rows+"");
  		DataAccess da=new DataAccess();
	    da.ExcelCreate(candidatelist, model1, col1);
	}
	
}
