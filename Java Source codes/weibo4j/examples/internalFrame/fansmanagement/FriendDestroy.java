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
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import dao.dao;
import dao.daoFunction;

import statisticsModel.DataAccess;
import statisticsModel.MyTableModel;
import statisticsModel.SimpleStatistics;
import statisticsModel.TextComponentPopupMenu;

import weibo4j.User;
import weibo4j.Weibo;
import weibo4j.WeiboException;

public class FriendDestroy extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5970069439721779819L;
	String[] col1 = { "编号","用户特征","用户名","用户ID","所在地区","认证","性别","粉丝数","关注数","微博数","更新时间","注册时间","微博ID","关注时间","取消原因" };
	String[] col2 = { "用户特征","用户名","用户ID","所在地区","认证","性别","粉丝数","关注数","微博数","更新时间","注册时间","微博ID","关注时间","取消时间"};
	public int[] columns1={50,200,120,100,80,80,60,60,60,60,150,150,80,150,80};
	public int[] columns2={200,120,100,80,80,60,60,60,60,160,160,100,80,150};
	public int norow[] ={1,8,9,10};
	public DefaultTableModel model1 = new DefaultTableModel();
	public JTable table1 = new JTable();
	public DefaultTableModel model2 = new DefaultTableModel();
	public JTable table2 = new JTable();
	final static JFormattedTextField friendbackdayst = new JFormattedTextField();
	final static JFormattedTextField refreshtimet = new JFormattedTextField(); //最近一次更新时间（xx天以内有更新）
	final static JFormattedTextField fannot = new JFormattedTextField(); //粉丝下限输入框
	final static JFormattedTextField itemsonet = new JFormattedTextField(); //预删除用户数目
	final static JFormattedTextField startpaget = new JFormattedTextField(); 
	final static JFormattedTextField endpaget = new JFormattedTextField(); 
	public DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public String friendslist=System.getProperty("user.dir")+"\\temp\\friendslist.xls";
	public String bitchlist=System.getProperty("user.dir")+"\\temp\\bitchlist.xls";
	public DataAccess da=new DataAccess();
	public SimpleStatistics ss=new SimpleStatistics();
	public MyTableModel tm=new MyTableModel();
		
	public FriendDestroy(){
		super();
		setLayout(new GridBagLayout());
		setBounds(0, 0, 280, 236);
						
		//定义数据表1，关注预取消列表
		model1=tm.CreateModel(col1);
		table1 = tm.buildTable(model1);
		final TableRowSorter<DefaultTableModel> sorter1 =new TableRowSorter<DefaultTableModel>(model1);  
        table1.setRowSorter(sorter1);
        JScrollPane scrollPane1=new JScrollPane(table1);
        
      //定义数据表2，关注取消列表
		model2=tm.CreateModel(col2);
		table2 = tm.buildTable(model2);
		final TableRowSorter<DefaultTableModel> sorter2 =new TableRowSorter<DefaultTableModel>(model2);  
        table2.setRowSorter(sorter2);
        JScrollPane scrollPane2=new JScrollPane(table2);
        
        table1.getTableHeader().setReorderingAllowed(false); 
        table2.getTableHeader().setReorderingAllowed(false); 
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table1.setColumnModel(tm.getColumn(table1, columns1)); 
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table2.setColumnModel(tm.getColumn(table2, columns2)); 
        
        //第一行布局（搜索条件选项）
        JLabel fanno=new JLabel("粉丝数目下限：");//设置目标粉丝粉丝数下限
		final GridBagConstraints gridBagConstraints_13 = new GridBagConstraints();
		//gridBagConstraints_14.weighty = 0;
		gridBagConstraints_13.insets = new Insets(5, 10, 0, 0);
		//gridBagConstraints_14.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_13.gridwidth = 1;
		gridBagConstraints_13.gridy = 0;
		gridBagConstraints_13.gridx = 0;
		add(fanno, gridBagConstraints_13);
		
		final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
		//gridBagConstraints_15.weighty = 0;
		gridBagConstraints_14.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_14.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_14.gridwidth = 1;
		gridBagConstraints_14.gridy = 0;
		gridBagConstraints_14.gridx = 1;
		gridBagConstraints_14.ipadx = 50;
		fannot.setText("400");
		add(fannot, gridBagConstraints_14);
        
        
        JLabel refreshtime=new JLabel("更新时间(天):");//设置更新时间
		final GridBagConstraints gridBagConstraints_17 = new GridBagConstraints();
		//gridBagConstraints_14.weighty = 0;
		gridBagConstraints_17.insets = new Insets(5, 30, 0, 0);
		//gridBagConstraints_14.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_17.gridwidth = 1;
		gridBagConstraints_17.gridy = 0;
		gridBagConstraints_17.gridx = 2;
		add(refreshtime, gridBagConstraints_17);
		
		final GridBagConstraints gridBagConstraints_18 = new GridBagConstraints();
		//gridBagConstraints_15.weighty = 0;
		gridBagConstraints_18.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_18.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_18.gridwidth = 1;
		gridBagConstraints_18.gridy = 0;
		gridBagConstraints_18.gridx = 3;
		gridBagConstraints_18.ipadx = 30;
		refreshtimet.setText("7");
		add(refreshtimet, gridBagConstraints_18);
        
		JLabel startpage=new JLabel("起始关注数:");
		final GridBagConstraints gridBagConstraints_14y = new GridBagConstraints();
		//gridBagConstraints_14y.weighty = 0;
		gridBagConstraints_14y.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_14y.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_14y.gridwidth = 1;
		gridBagConstraints_14y.gridy = 0;
		gridBagConstraints_14y.gridx = 4;
		add(startpage, gridBagConstraints_14y);
		
		final GridBagConstraints gridBagConstraints_15y = new GridBagConstraints();
		//gridBagConstraints_15y.weighty = 0;
		gridBagConstraints_15y.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_15y.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_15y.gridwidth = 1;
		gridBagConstraints_15y.gridy = 0;
		gridBagConstraints_15y.gridx = 5;
		gridBagConstraints_15y.ipadx = 40;
		startpaget.setText("1");
		add(startpaget, gridBagConstraints_15y);
				
		JLabel endpage=new JLabel("至:");
		final GridBagConstraints gridBagConstraints_16y = new GridBagConstraints();
		//gridBagConstraints_16y.weighty = 0;
		gridBagConstraints_16y.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_16y.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_16y.gridwidth = 1;
		gridBagConstraints_16y.gridy = 0;
		gridBagConstraints_16y.gridx = 6;
		add(endpage, gridBagConstraints_16y);
		
		final GridBagConstraints gridBagConstraints_17y = new GridBagConstraints();
		//gridBagConstraints_17y.weighty = 0;
		gridBagConstraints_17y.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_17y.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_17y.gridwidth = 1;
		gridBagConstraints_17y.gridy = 0;
		gridBagConstraints_17y.gridx = 7;
		gridBagConstraints_17y.ipadx = 40;
		endpaget.setText("3000");
		add(endpaget, gridBagConstraints_17y);
		
		
		JLabel friendbackdays=new JLabel("设置回粉天数：");//设置搜索条件，获取xx天未关注我的列表
		final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
		//gridBagConstraints_11.weighty = 0;
		gridBagConstraints_11.insets = new Insets(10, 30, 0, 0);
		//gridBagConstraints_11.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_11.gridwidth = 2;
		gridBagConstraints_11.gridy = 1;
		gridBagConstraints_11.gridx = 2;
		add(friendbackdays, gridBagConstraints_11);
		
		final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
		gridBagConstraints_12.weightx = 0;
		gridBagConstraints_12.insets = new Insets(10, 0, 0, 0);
		//gridBagConstraints_12.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_12.anchor = GridBagConstraints.WEST;
		gridBagConstraints_12.gridwidth = 1;
		gridBagConstraints_12.gridy = 1;
		gridBagConstraints_12.gridx = 3;
		gridBagConstraints_12.ipadx= 30;
		friendbackdayst.setText("3");
		add(friendbackdayst, gridBagConstraints_12);
		
		JLabel itemsone=new JLabel("筛选用户数目：");
		final GridBagConstraints gridBagConstraints_21 = new GridBagConstraints();
		//gridBagConstraints_21.weighty = 0;
		gridBagConstraints_21.insets = new Insets(10, 10, 0, 0);
		//gridBagConstraints_21.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_21.gridwidth = 2;
		gridBagConstraints_21.gridy = 1;
		gridBagConstraints_21.gridx = 0;
		add(itemsone, gridBagConstraints_21);
		
		final GridBagConstraints gridBagConstraints_22 = new GridBagConstraints();
		gridBagConstraints_22.weighty = 0;
		gridBagConstraints_22.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_22.anchor = GridBagConstraints.WEST;
		gridBagConstraints_22.gridwidth = 1;
		gridBagConstraints_22.gridy = 1;
		gridBagConstraints_22.gridx = 1;
		gridBagConstraints_22.ipadx= 50;
		add(itemsonet, gridBagConstraints_22);
		
		final JButton friendsget=new JButton("定期未回粉筛选");
		final GridBagConstraints gridBagConstraints_1b1 = new GridBagConstraints();
		//gridBagConstraints_1b1.weighty = 0;
		gridBagConstraints_1b1.insets = new Insets(10, 20, 0, 0);
		//gridBagConstraints_1b1.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_1b1.gridwidth = 1;
		gridBagConstraints_1b1.gridy = 1;
		gridBagConstraints_1b1.gridx = 8;
		add(friendsget, gridBagConstraints_1b1);
		friendsget.addActionListener(new FileUpload());
		
		final JButton friendselect=new JButton("不活跃关注筛选");
		final GridBagConstraints gridBagConstraints_2b1 = new GridBagConstraints();
		//gridBagConstraints_2b1.weighty = 0;
		gridBagConstraints_2b1.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_2b1.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_2b1.gridwidth = 1;
		gridBagConstraints_2b1.gridy = 0;
		gridBagConstraints_2b1.gridx = 8;
		add(friendselect, gridBagConstraints_2b1);
		friendselect.addActionListener(new GetFriends());
		
		final JButton deletea1=tm.CreateButtonA("删除全部数据",model1,table1);
		final GridBagConstraints gridBagConstraints_19 = new GridBagConstraints();
		//gridBagConstraints_19.weighty = 0;
		gridBagConstraints_19.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_19.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_19.gridwidth = 1;
		gridBagConstraints_19.gridy = 0;
		gridBagConstraints_19.gridx = 9;
		add(deletea1, gridBagConstraints_19);
		
		final JButton deletea2=tm.CreateButtonS("删除单条数据",model1,table1);
		final GridBagConstraints gridBagConstraints_29 = new GridBagConstraints();
		//gridBagConstraints_29.weighty = 0;
		gridBagConstraints_29.insets = new Insets(10, 20, 0, 0);
		//gridBagConstraints_29.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_29.gridwidth = 1;
		gridBagConstraints_29.gridy = 1;
		gridBagConstraints_29.gridx = 9;
		add(deletea2, gridBagConstraints_29);
				
				
		//第二行布局（关注预取消列表）
		final GridBagConstraints gridBagConstraints_3t = new GridBagConstraints();
		gridBagConstraints_3t.weightx = 0.8;
		gridBagConstraints_3t.weighty = 1.0;
		gridBagConstraints_3t.insets = new Insets(10, 10, 0, 10);
		gridBagConstraints_3t.fill = GridBagConstraints.BOTH;
		gridBagConstraints_3t.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_3t.gridwidth = 12;
		gridBagConstraints_3t.gridheight = 1;
		gridBagConstraints_3t.gridy = 2;
		gridBagConstraints_3t.gridx = 0;
		gridBagConstraints_3t.ipadx = 35;
		gridBagConstraints_3t.ipady = -195;
		add(scrollPane1, gridBagConstraints_3t);
		
		//第三行布局（取消关注）
		final JButton friendsdestroy=new JButton("关注批量取消");
		final GridBagConstraints gridBagConstraints_31 = new GridBagConstraints();
		//gridBagConstraints_31.weighty = 0;
		gridBagConstraints_31.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_31.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_31.gridwidth = 1;
		gridBagConstraints_31.gridy = 3;
		gridBagConstraints_31.gridx = 8;
		add(friendsdestroy, gridBagConstraints_31);
		friendsdestroy.addActionListener(new BatchCancel());
		
		final JButton deletea3=tm.CreateButtonA("删除全部数据",model2,table2);
		final GridBagConstraints gridBagConstraints_32 = new GridBagConstraints();
		//gridBagConstraints_32.weighty = 0;
		gridBagConstraints_32.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_32.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_32.gridwidth = 1;
		gridBagConstraints_32.gridy = 3;
		gridBagConstraints_32.gridx = 9;
		add(deletea3, gridBagConstraints_32);
		
		//第四行布局（关注取消列表）
		final GridBagConstraints gridBagConstraints_41 = new GridBagConstraints();
		gridBagConstraints_41.weightx = 0.8;
		gridBagConstraints_41.weighty = 1.0;
		gridBagConstraints_41.insets = new Insets(10, 10, 0, 10);
		gridBagConstraints_41.fill = GridBagConstraints.BOTH;
		gridBagConstraints_41.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_41.gridwidth = 12;
		gridBagConstraints_41.gridheight = 1;
		gridBagConstraints_41.gridy = 4;
		gridBagConstraints_41.gridx = 0;
		gridBagConstraints_41.ipadx = 35;
		gridBagConstraints_41.ipady = -195;
		add(scrollPane2, gridBagConstraints_41);
		
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
	
	//粉丝批量删除
	public class BatchCancel implements ActionListener{
		public void actionPerformed(ActionEvent el) {
			System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
	    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
	    	String destroytime=format.format(new Date());
	    	int number=0;
	    	try {
				Weibo weibo = new Weibo();
				weibo.setToken(Weibo.getat(),Weibo.getats());
				for(int i=0;i<model1.getRowCount();i++){
					User user = weibo.destroyFriendship(table1.getValueAt(i,3)+"");//args[2]:关注用户的id
					System.out.println(user.toString());
					number++;
					Object tempdata[]=new Object[14];
					String strinsert="";
					for(int j=1;j<14;j++){
						tempdata[j-1]=table1.getValueAt(i,j)+"";
						String tb=table1.getValueAt(i, j)+"";
						strinsert=strinsert+tb+"','";
					}
					strinsert="'"+strinsert+destroytime+"'";
					dao.insert("insert into tb_BitchList (UserSource,UserName," +
							"UserID,UserLocation,UserVerify,UserGender,UserFans," +
							"UserFriends,UserStatusNo,UserUpdateTime,UserCreateTime," +
							"StatusId,FriendsTime,DestroyTime) values ("+ strinsert +" )");
					tempdata[13]=destroytime;
					model2.addRow(tempdata);
					
					String tempids=table1.getValueAt(i, 0)+"";
					long tempid=Long.parseLong(tempids);
					dao.update("Delete FROM tb_FriendsList where ID = "+ tempid);
							
					model1.removeRow(i);
				}
				JOptionPane.showMessageDialog(null, "取消"+number+"个关注！", "提示", JOptionPane.ERROR_MESSAGE);
			} catch (WeiboException e) {
				e.printStackTrace();
			}
		}	
	}
	
	//关注预取消列表上载
	public class FileUpload implements ActionListener {
		public void actionPerformed(ActionEvent el){
		    System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
	    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
	    	daoFunction df=new daoFunction();
	    	try {
				Weibo weibo = new Weibo();
				weibo.setToken(Weibo.getat(),Weibo.getats());
				String myid=weibo.getUserTimeline().get(1).getUser().getId()+"";
				ResultSet rt = dao.SqlExcute("SELECT top 20 ID,UserID,FriendsTime FROM tb_FriendsList where ID > 0 Order by ID asc" ); 
				try {
					while(rt.next()){ 
						String friends=rt.getString("FriendsTime");
						Date friendbacktime=ss.DateConvert(friendbackdayst.getText());
						Date friendstime = null;
						try {
							friendstime = format.parse(friends);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String tempid=rt.getString("UserID");
						if(friendstime.before(friendbacktime)){
							boolean bool = weibo.existsFriendship(tempid,myid);//myid:自己的id；tempid:关注对象的id
							if(bool==false){
								long sid=Long.parseLong(rt.getString("ID"));
								String sql1="SELECT * FROM tb_FriendsList where ID= "+sid;
								df.RowSingleWrite(model1, sql1,norow,14,1);	
							}
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
			} catch (WeiboException e) {
				e.printStackTrace();
			}
			
			for(int i=0;i<table1.getModel().getRowCount();i++){
				String temp=table1.getValueAt(i, 14)+"";
				if(temp.equals("negtive")==false){
					table1.setValueAt("none",i, 14);
				}
			}
			itemsonet.setText(model1.getRowCount()+"");
		}
	}
	
	//搜索无效关注
	public class GetFriends implements ActionListener {
		public void actionPerformed(ActionEvent el){
			Date dateset=ss.DateConvert(refreshtimet.getText());
			int fanset=Integer.parseInt(fannot.getText());
			System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
	    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
	    	int startp=Integer.parseInt(startpaget.getText());
    		int endp=Integer.parseInt(endpaget.getText());
    			    	
	    	try {
				Weibo weibo = new Weibo();
				weibo.setToken(Weibo.getat(),Weibo.getats());
			  	//int end=weibo.getUserTimeline().get(1).getUser().getFollowersCount()+20;
				//if(end>4000){
				//	end=4000;
				//}
			  	for(int cursor = startp;cursor<=endp;cursor=cursor+20)
				{	
					//采用cursor参数处理翻页
					List<User> list = weibo.getFriendsStatuses(cursor);
					for(User user : list) {
						int userfan=user.getFollowersCount();
						Date userupdate=null;
						if(user.getStatus()==null)
						{
							userupdate=ss.DateConvert("1000");	
						}else{
							userupdate=user.getStatus().getCreatedAt();
						}
						if(userfan<fanset && userupdate.before(dateset)){
							String s1="0";
							String s2="";
							String s3=user.getName();
							String s4=user.getId()+"";
							String s5=user.getLocation();
							String s6=user.isVerified()+""; 
							String s7=user.getGender();
							int s8=user.getFollowersCount();
							int s9=user.getFriendsCount();
							int s10=user.getStatusesCount();
							String s11=format.format(user.getStatus().getCreatedAt());
							String s11ex=format.format(user.getCreatedAt());
							String s12="";
							String s13="";
							String s14="negtive";
							Object[] str_row = {s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s11ex,s12,s13,s14};  
							model1.addRow(str_row);
						}
					}
				}
			} catch (WeiboException e) {
				e.printStackTrace();
			}
			System.out.println(format.format(dateset));
			itemsonet.setText(model1.getRowCount()+"");
		}
	}

}
