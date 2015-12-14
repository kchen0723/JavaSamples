package internalFrame.weibomonitor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import statisticsModel.DataAccess;
import statisticsModel.MyTableModel;
import statisticsModel.SimpleStatistics;
import statisticsModel.TextComponentPopupMenu;
import weibo4j.Paging;
import weibo4j.Status;
import weibo4j.Weibo;
import weibo4j.WeiboException;
import dao.dao;

public class StatusSearch extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3251502005148394518L;
	//数据库字段31个
	String[] col = {"微博ID","发布时间","微博内容","来源","用户名","用户ID","地区","所在省","所在市","注册时间","性别","粉丝数","关注数","微博数","认证","是否原创","微博ID","发布时间","微博内容","来源","用户名","用户ID","地区","所在省","所在市","注册时间","性别","粉丝数","关注数","微博数","认证","搜索词"};
	public int[] columns={80,160,500,80,100,80,80,50,50,160,50,50,50,50,30,50,80,160,500,80,100,80,80,50,50,80,30,50,50,50,30,80};
	
	//数据表字段
	String[] col1 = { "微博ID","发布时间","微博内容","来源","用户名","用户ID","地区","性别","粉丝数","关注数","微博数","认证","原创","原作者","认证*","发布时间*"};
	public DefaultTableModel model1 = new DefaultTableModel();
	public JTable table1 = new JTable();
	public DefaultTableModel model2 = new DefaultTableModel();
	public JTable table2 = new JTable();
	
	final static JTextField trendnamet = new JTextField(); 
	final static JFormattedTextField dayst = new JFormattedTextField();
	final static JFormattedTextField minnot = new JFormattedTextField();
	final static JFormattedTextField maxnot = new JFormattedTextField();
	final static JFormattedTextField itemst = new JFormattedTextField();
	final static JFormattedTextField days2t = new JFormattedTextField();
	final static JFormattedTextField maxno2t = new JFormattedTextField();
	final static JFormattedTextField minno2t = new JFormattedTextField();
	final static JFormattedTextField items2t= new JFormattedTextField();
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public MyTableModel tm=new MyTableModel();
	public TextComponentPopupMenu tpm=new TextComponentPopupMenu();
	
	public StatusSearch() {
		super();
		setLayout(new GridBagLayout());
		setBounds(0, 0, 280, 236);
		
		String zipname=System.getProperty("user.dir")+"\\db\\db_weispace.MDB";
		System.out.println(zipname);
		
		//获取当前登陆用户
		//String d=Weibo.getclient();
		//System.out.println(d);
		
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
                
        //第一行布局
        JLabel trendname=new JLabel("微博搜索关键词设定:");
		final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
		//gridBagConstraints_11.weighty = 0;
		gridBagConstraints_11.insets = new Insets(5, 10, 0, 0);
		//gridBagConstraints_11.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_11.gridwidth = 2;
		gridBagConstraints_11.gridy = 0;
		gridBagConstraints_11.gridx = 0;
		add(trendname, gridBagConstraints_11);
		
		//输入微博（话题）关键字
		final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
		gridBagConstraints_12.weightx = 0;
		gridBagConstraints_12.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_12.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_12.anchor = GridBagConstraints.WEST;
		gridBagConstraints_12.gridwidth = 1;
		gridBagConstraints_12.gridy = 0;
		gridBagConstraints_12.gridx = 1;
		gridBagConstraints_12.ipadx= 80;
		add(trendnamet, gridBagConstraints_12);
		trendnamet.addMouseListener(tpm.getSharedInstance());   
				
		JLabel days=new JLabel("时间范围设定（天）:");
		final GridBagConstraints gridBagConstraints_13 = new GridBagConstraints();
		//gridBagConstraints_13.weighty = 0;
		gridBagConstraints_13.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_13.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_13.gridwidth = 2;
		gridBagConstraints_13.gridy = 0;
		gridBagConstraints_13.gridx = 2;
		add(days, gridBagConstraints_13);
		
		//设定搜索时间范围
		final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
		gridBagConstraints_14.weightx = 0;
		gridBagConstraints_14.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_14.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_14.anchor = GridBagConstraints.WEST;
		gridBagConstraints_14.gridwidth = 1;
		gridBagConstraints_14.gridy = 0;
		gridBagConstraints_14.gridx = 3;
		gridBagConstraints_14.ipadx= 30;
		add(dayst, gridBagConstraints_14);
		
		JLabel maxno=new JLabel("搜索页数:");
		final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
		//gridBagConstraints_15.weighty = 0;
		gridBagConstraints_15.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_15.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_15.gridwidth = 2;
		gridBagConstraints_15.gridy = 0;
		gridBagConstraints_15.gridx = 4;
		add(maxno, gridBagConstraints_15);
		
		//设定搜索最小页数
		minnot.setText("1");
		final GridBagConstraints gridBagConstraints_16n = new GridBagConstraints();
		gridBagConstraints_16n.weightx = 0;
		gridBagConstraints_16n.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_16n.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_16n.anchor = GridBagConstraints.WEST;
		gridBagConstraints_16n.gridwidth = 1;
		gridBagConstraints_16n.gridy = 0;
		gridBagConstraints_16n.gridx = 5;
		gridBagConstraints_16n.ipadx= 20;
		add(minnot, gridBagConstraints_16n);
		
		//保存关键词微博搜索表位excel
		JButton save1=CreateSearchButton("保存EXCEL",model1,col);
		final GridBagConstraints gridBagConstraints_27 = new GridBagConstraints();
		//gridBagConstraints_27.weighty = 0;
		gridBagConstraints_27.insets = new Insets(10, 0, 0, 0);
		//gridBagConstraints_27.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_27.gridwidth = 3;
		gridBagConstraints_27.gridy = 1;
		gridBagConstraints_27.gridx = 5;
		add(save1, gridBagConstraints_27);
		
		JLabel minno=new JLabel("至:");
		final GridBagConstraints gridBagConstraints_15z = new GridBagConstraints();
		//gridBagConstraints_15z.weighty = 0;
		gridBagConstraints_15z.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_15z.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_15z.gridwidth = 2;
		gridBagConstraints_15z.gridy = 0;
		gridBagConstraints_15z.gridx = 6;
		add(minno, gridBagConstraints_15z);
		
		//设定搜索最大页数
		maxnot.setText("30");
		final GridBagConstraints gridBagConstraints_16 = new GridBagConstraints();
		gridBagConstraints_16.weightx = 0;
		gridBagConstraints_16.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_16.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_16.anchor = GridBagConstraints.WEST;
		gridBagConstraints_16.gridwidth = 1;
		gridBagConstraints_16.gridy = 0;
		gridBagConstraints_16.gridx = 7;
		gridBagConstraints_16.ipadx= 20;
		add(maxnot, gridBagConstraints_16);
		
		
		//微博（话题）检索按钮
		final JButton statussearch=new JButton("搜索查询");
		final GridBagConstraints gridBagConstraints_1b = new GridBagConstraints();
		//gridBagConstraints_1b.weighty = 0;
		gridBagConstraints_1b.insets = new Insets(5, 50, 0, 0);
		//gridBagConstraints_1b.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_1b.gridwidth = 1;
		gridBagConstraints_1b.gridy = 0;
		gridBagConstraints_1b.gridx = 8;
		add(statussearch, gridBagConstraints_1b);
		statussearch.addActionListener(new GetTrendStatus());
		
		//第二行布局
		JLabel items=new JLabel("当前搜索微博总数目:");
		final GridBagConstraints gridBagConstraints_25 = new GridBagConstraints();
		//gridBagConstraints_25.weighty = 0;
		gridBagConstraints_25.insets = new Insets(5, 10, 0, 0);
		//gridBagConstraints_25.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_25.gridwidth = 2;
		gridBagConstraints_25.gridy = 1;
		gridBagConstraints_25.gridx = 0;
		add(items, gridBagConstraints_25);
		
		final GridBagConstraints gridBagConstraints_26 = new GridBagConstraints();
		gridBagConstraints_26.weightx = 0;
		gridBagConstraints_26.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_26.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_26.anchor = GridBagConstraints.WEST;
		gridBagConstraints_26.gridwidth = 1;
		gridBagConstraints_26.gridy = 1;
		gridBagConstraints_26.gridx = 1;
		gridBagConstraints_26.ipadx= 30;
		add(itemst, gridBagConstraints_26);
		
		
		//数据库存储按钮
		final JButton dbaccessw=new JButton("存数据库");
		final GridBagConstraints gridBagConstraints_3bx = new GridBagConstraints();
		//gridBagConstraints_3bx.weighty = 0;
		gridBagConstraints_3bx.insets = new Insets(10, 50, 0, 0);
		//gridBagConstraints_3bx.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_3bx.gridwidth = 1;
		gridBagConstraints_3bx.gridy = 1;
		gridBagConstraints_3bx.gridx = 8;
		add(dbaccessw, gridBagConstraints_3bx);
		dbaccessw.addActionListener(new ButtonInsertListener1());
		
		
		final JButton delwb=tm.CreateButtonS("选定删除",model1,table1);
		final GridBagConstraints gridBagConstraints_3b2 = new GridBagConstraints();
		gridBagConstraints_3b2.weightx = 0;
		gridBagConstraints_3b2.insets = new Insets(5, 50, 0, 0);
		//gridBagConstraints_3b2.fill = GridBagConstraints.EAST;
		gridBagConstraints_3b2.anchor = GridBagConstraints.EAST;
		gridBagConstraints_3b2.gridwidth = 1;
		gridBagConstraints_3b2.gridy = 0;
		gridBagConstraints_3b2.gridx = 9;
		add(delwb, gridBagConstraints_3b2);
		
		final JButton delall1=tm.CreateButtonA("全部删除",model1,table1);
		final GridBagConstraints gridBagConstraints_3b3 = new GridBagConstraints();
		gridBagConstraints_3b3.weightx = 0;
		gridBagConstraints_3b3.insets = new Insets(10, 50, 0, 0);
		gridBagConstraints_3b3.anchor = GridBagConstraints.EAST;
		gridBagConstraints_3b3.gridwidth = 1;
		gridBagConstraints_3b3.gridy = 1;
		gridBagConstraints_3b3.gridx = 9;
		add(delall1, gridBagConstraints_3b3);
		
		//第四行布局（微博搜索列表）
		final GridBagConstraints gridBagConstraints_41 = new GridBagConstraints();
		gridBagConstraints_41.weightx = 0.8;
		gridBagConstraints_41.weighty = 1.0;
		gridBagConstraints_41.insets = new Insets(10, 10, 0, 10);
		gridBagConstraints_41.fill = GridBagConstraints.BOTH;
		gridBagConstraints_41.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_41.gridwidth = 12;
		gridBagConstraints_41.gridheight = 1;
		gridBagConstraints_41.gridy = 3;
		gridBagConstraints_41.gridx = 0;
		gridBagConstraints_41.ipadx = 35;
		gridBagConstraints_41.ipady = -195;
		add(scrollPane1, gridBagConstraints_41);
		
		//第五行布局（关注用户微博检索）
		JLabel days2=new JLabel("时间范围设定（天）:");
		final GridBagConstraints gridBagConstraints_55 = new GridBagConstraints();
		//gridBagConstraints_55.weighty = 0;
		gridBagConstraints_55.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_55.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_55.gridwidth = 2;
		gridBagConstraints_55.gridy = 4;
		gridBagConstraints_55.gridx = 0;
		add(days2, gridBagConstraints_55);
		
		//设定搜索时间范围
		final GridBagConstraints gridBagConstraints_56 = new GridBagConstraints();
		gridBagConstraints_56.weightx = 0;
		gridBagConstraints_56.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_56.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_56.anchor = GridBagConstraints.WEST;
		gridBagConstraints_56.gridwidth = 1;
		gridBagConstraints_56.gridy = 4;
		gridBagConstraints_56.gridx = 1;
		gridBagConstraints_56.ipadx= 30;
		add(days2t, gridBagConstraints_56);
		
		JLabel maxno2=new JLabel("搜索页数:");
		final GridBagConstraints gridBagConstraints_57 = new GridBagConstraints();
		//gridBagConstraints_57.weighty = 0;
		gridBagConstraints_57.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_57.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_57.gridwidth = 2;
		gridBagConstraints_57.gridy = 4;
		gridBagConstraints_57.gridx = 4;
		add(maxno2, gridBagConstraints_57);
		
		//设定搜索最小页数
		minno2t.setText("1");
		final GridBagConstraints gridBagConstraints_58n = new GridBagConstraints();
		gridBagConstraints_58n.weightx = 0;
		gridBagConstraints_58n.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_58n.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_58n.anchor = GridBagConstraints.WEST;
		gridBagConstraints_58n.gridwidth = 1;
		gridBagConstraints_58n.gridy = 4;
		gridBagConstraints_58n.gridx = 5;
		gridBagConstraints_58n.ipadx= 20;
		add(minno2t, gridBagConstraints_58n);
		
		//保存关注用户微博搜索表位excel
		JButton save2=CreateSearchButton("保存EXCEL",model2,col);
		final GridBagConstraints gridBagConstraints_67 = new GridBagConstraints();
		//gridBagConstraints_67.weighty = 0;
		gridBagConstraints_67.insets = new Insets(10, 0, 0, 0);
		//gridBagConstraints_67.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_67.gridwidth = 3;
		gridBagConstraints_67.gridy = 5;
		gridBagConstraints_67.gridx = 5;
		add(save2, gridBagConstraints_67);
		
		JLabel minno2=new JLabel("至");
		final GridBagConstraints gridBagConstraints_57n = new GridBagConstraints();
		//gridBagConstraints_57n.weighty = 0;
		gridBagConstraints_57n.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_57n.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_57n.gridwidth = 2;
		gridBagConstraints_57n.gridy = 4;
		gridBagConstraints_57n.gridx = 6;
		add(minno2, gridBagConstraints_57n);
		//设定搜索最大条数
		maxno2t.setText("30");
		final GridBagConstraints gridBagConstraints_58 = new GridBagConstraints();
		gridBagConstraints_58.weightx = 0;
		gridBagConstraints_58.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_58.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_58.anchor = GridBagConstraints.WEST;
		gridBagConstraints_58.gridwidth = 1;
		gridBagConstraints_58.gridy = 4;
		gridBagConstraints_58.gridx = 7;
		gridBagConstraints_58.ipadx= 20;
		add(maxno2t, gridBagConstraints_58);
		
		
		//关注用户微博检索按钮
		final JButton frdsearch=new JButton("关注查询");
		final GridBagConstraints gridBagConstraints_5b = new GridBagConstraints();
		//gridBagConstraints_5b.weighty = 0;
		gridBagConstraints_5b.insets = new Insets(5, 50, 0, 0);
		//gridBagConstraints_5b.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_5b.gridwidth = 1;
		gridBagConstraints_5b.gridy = 4;
		gridBagConstraints_5b.gridx = 8;
		add(frdsearch, gridBagConstraints_5b);
		frdsearch.addActionListener(new GetFriendsTimeline());
		
		//第六行布局
        JLabel items2=new JLabel("当前搜索微博总数目:");
		final GridBagConstraints gridBagConstraints_61 = new GridBagConstraints();
		//gridBagConstraints_61.weighty = 0;
		gridBagConstraints_61.insets = new Insets(10, 10, 0, 0);
		//gridBagConstraints_61.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_61.gridwidth = 2;
		gridBagConstraints_61.gridy = 5;
		gridBagConstraints_61.gridx = 0;
		add(items2, gridBagConstraints_61);
		
		final GridBagConstraints gridBagConstraints_62 = new GridBagConstraints();
		gridBagConstraints_62.weightx = 0;
		gridBagConstraints_62.insets = new Insets(10, 0, 0, 0);
		//gridBagConstraints_62.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_62.anchor = GridBagConstraints.WEST;
		gridBagConstraints_62.gridwidth = 1;
		gridBagConstraints_62.gridy = 5;
		gridBagConstraints_62.gridx = 1;
		gridBagConstraints_62.ipadx= 30;
		add(items2t, gridBagConstraints_62);
		
		//数据库读取按钮
		final JButton dbaccessw2=new JButton("存数据库");
		final GridBagConstraints gridBagConstraints_5b1 = new GridBagConstraints();
		//gridBagConstraints_5b1.weighty = 0;
		gridBagConstraints_5b1.insets = new Insets(10, 50, 0, 0);
		//gridBagConstraints_5b1.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_5b1.gridwidth = 1;
		gridBagConstraints_5b1.gridy = 5;
		gridBagConstraints_5b1.gridx = 8;
		add(dbaccessw2, gridBagConstraints_5b1);
		dbaccessw2.addActionListener(new ButtonInsertListener2());
		
		final JButton delwb2=tm.CreateButtonS("单条删除",model1,table1);
		final GridBagConstraints gridBagConstraints_5b2 = new GridBagConstraints();
		gridBagConstraints_5b2.weightx = 0;
		gridBagConstraints_5b2.insets = new Insets(10, 50, 0, 0);
		//gridBagConstraints_5b2.fill = GridBagConstraints.EAST;
		gridBagConstraints_5b2.anchor = GridBagConstraints.EAST;
		gridBagConstraints_5b2.gridwidth = 1;
		gridBagConstraints_5b2.gridy = 4;
		gridBagConstraints_5b2.gridx = 9;
		add(delwb2, gridBagConstraints_5b2);
		
		final JButton delall2=tm.CreateButtonA("全部删除",model1,table1);
		final GridBagConstraints gridBagConstraints_5b3 = new GridBagConstraints();
		gridBagConstraints_5b3.weightx = 0;
		gridBagConstraints_5b3.insets = new Insets(10, 50, 0, 0);
		gridBagConstraints_5b3.anchor = GridBagConstraints.EAST;
		gridBagConstraints_5b3.gridwidth = 1;
		gridBagConstraints_5b3.gridy = 5;
		gridBagConstraints_5b3.gridx = 9;
		add(delall2, gridBagConstraints_5b3);		
		
		//第七行布局
		final GridBagConstraints gridBagConstraints_71 = new GridBagConstraints();
		gridBagConstraints_71.weightx = 0.8;
		gridBagConstraints_71.weighty = 1.0;
		gridBagConstraints_71.insets = new Insets(10, 10, 10, 10);
		gridBagConstraints_71.fill = GridBagConstraints.BOTH;
		gridBagConstraints_71.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_71.gridwidth = 12;
		gridBagConstraints_71.gridheight = 2;
		gridBagConstraints_71.gridy = 6;
		gridBagConstraints_71.gridx = 0;
		gridBagConstraints_71.ipadx = 35;
		gridBagConstraints_71.ipady = -195;
		add(scrollPane2, gridBagConstraints_71);
		
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
	
	public class GetTrendStatus implements ActionListener {
		public void actionPerformed(ActionEvent el){
			if(trendnamet.getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "未输入微博关键字！", "提示", JOptionPane.ERROR_MESSAGE);
			}else{
				System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		    	try {
					Weibo weibo = new Weibo();
					weibo.setToken(Weibo.getat(),Weibo.getats());
					SimpleStatistics ss=new SimpleStatistics();
					int end=Integer.parseInt(maxnot.getText());
					int start=Integer.parseInt(minnot.getText());
					for(int i=start;i<=end;i++)
					{	
					//采用cursor参数处理翻页
						List<Status> status = weibo.getTrendStatus(trendnamet.getText(),new Paging(i,20));
						for(Status statuses:status){
							Date ctime = statuses.getCreatedAt();
							if(ctime.after(ss.DateConvert(dayst.getText()))){
									String sid=statuses.getId()+"";
									String stm=format.format(statuses.getCreatedAt());
									String stx=statuses.getText().replace("'","“");
									String ssc=ss.SourceExtract(statuses.getSource()); 
									String sune=statuses.getUser().getName();
									String suid=statuses.getUser().getId()+"";
									String sulc=statuses.getUser().getLocation();
									int supc=statuses.getUser().getProvince();
									int suct=statuses.getUser().getCity();
									String suca=format.format(statuses.getUser().getCreatedAt());
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
										sotm=format.format(statuses.getRetweeted_status().getCreatedAt());
										sotx=statuses.getRetweeted_status().getText().replace("'","“");;
										sosc=ss.SourceExtract(statuses.getRetweeted_status().getSource());; 
										soune=statuses.getRetweeted_status().getUser().getName();
										souid=statuses.getRetweeted_status().getUser().getId()+"";
										soulc=statuses.getRetweeted_status().getUser().getLocation();
										soupc=statuses.getRetweeted_status().getUser().getProvince();
										souct=statuses.getRetweeted_status().getUser().getCity();
										souca=format.format(statuses.getRetweeted_status().getUser().getCreatedAt());
										sougd=statuses.getRetweeted_status().getUser().getGender();
										soufl=statuses.getRetweeted_status().getUser().getFollowersCount();
										soufd=statuses.getRetweeted_status().getUser().getFriendsCount();
										sousn=statuses.getRetweeted_status().getUser().getStatusesCount();
										souvd=statuses.getRetweeted_status().getUser().isVerified()+"";
									}
									Object[] str_row2 = {sid,stm,stx,ssc,sune,suid,sulc,supc,suct,suca,sugd,sufl,sufd,susn,suvd,sorg,soid,sotm,sotx,sosc,soune,souid,soulc,soupc,souct,souca,sougd,soufl,soufd,sousn,souvd,trendnamet.getText()};  
									model1.addRow(str_row2);
									//model2.addRow(str_row2);
								}
							}
						}
					itemst.setText(table1.getModel().getRowCount()+"");
				} catch (WeiboException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public class GetFriendsTimeline implements ActionListener {
		public void actionPerformed(ActionEvent el){
				System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		    	try {
					Weibo weibo = new Weibo();
					weibo.setToken(Weibo.getat(),Weibo.getats());
					SimpleStatistics ss=new SimpleStatistics();
					int end=Integer.parseInt(maxno2t.getText());
					int start=Integer.parseInt(minno2t.getText());
					for(int i=start;i<=end;i++)
					{	
					//采用cursor参数处理翻页
						List<Status> status = weibo.getFriendsTimeline(new Paging(i,20));
						for(Status statuses:status){
							Date ctime = statuses.getCreatedAt();
							if(ctime.after(ss.DateConvert(days2t.getText()))){
									String sid=statuses.getId()+"";
									String stm=format.format(statuses.getCreatedAt());
									String stx=statuses.getText().replace("'","“");
									String ssc=ss.SourceExtract(statuses.getSource()); 
									String sune=statuses.getUser().getName();
									String suid=statuses.getUser().getId()+"";
									String sulc=statuses.getUser().getLocation();
									int supc=statuses.getUser().getProvince();
									int suct=statuses.getUser().getCity();
									String suca=format.format(statuses.getUser().getCreatedAt());
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
										sotm=format.format(statuses.getRetweeted_status().getCreatedAt());
										sotx=statuses.getRetweeted_status().getText().replace("'","“");;
										sosc=ss.SourceExtract(statuses.getRetweeted_status().getSource());; 
										soune=statuses.getRetweeted_status().getUser().getName();
										souid=statuses.getRetweeted_status().getUser().getId()+"";
										soulc=statuses.getRetweeted_status().getUser().getLocation();
										soupc=statuses.getRetweeted_status().getUser().getProvince();
										souct=statuses.getRetweeted_status().getUser().getCity();
										souca=format.format(statuses.getRetweeted_status().getUser().getCreatedAt());
										sougd=statuses.getRetweeted_status().getUser().getGender();
										soufl=statuses.getRetweeted_status().getUser().getFollowersCount();
										soufd=statuses.getRetweeted_status().getUser().getFriendsCount();
										sousn=statuses.getRetweeted_status().getUser().getStatusesCount();
										souvd=statuses.getRetweeted_status().getUser().isVerified()+"";
									}
									Object[] str_row2 = {sid,stm,stx,ssc,sune,suid,sulc,supc,suct,suca,sugd,sufl,sufd,susn,suvd,sorg,soid,sotm,sotx,sosc,soune,souid,soulc,soupc,souct,souca,sougd,soufl,soufd,sousn,souvd,Weibo.getclient()};  
									model2.addRow(str_row2);
									//model2.addRow(str_row2);
								}
							}
						}
					items2t.setText(table2.getModel().getRowCount()+"");
				} catch (WeiboException e) {
					e.printStackTrace();
				}
			}
		
	}	  
	
	//插入按钮search
	public class ButtonInsertListener1 implements ActionListener {
		  public void actionPerformed(ActionEvent evt) {
			  InsertFunction1(table1,model1);
			  JOptionPane.showMessageDialog(null, "数据库保存完毕", "提示", JOptionPane.INFORMATION_MESSAGE);
				
		  }
	}	  
	
	//插入按钮friends
	public class ButtonInsertListener2 implements ActionListener {
		  public void actionPerformed(ActionEvent evt) {
			  InsertFunction2(table2,model2);
			  JOptionPane.showMessageDialog(null, "数据库保存完毕", "提示", JOptionPane.INFORMATION_MESSAGE);
				
		  }
	}
	
	//数据库插入方法status
	public void InsertFunction1(JTable table,DefaultTableModel model){
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
				  dao.insert("insert into tb_StatusSearch (StatusId,StatusCreateTime,StatusText,StatusSource,UserName,UserID,UserLocation,UserProvince,UserCity,UserCreateTime,UserGender,UserFans,UserFriends,UserStatusNo,UserVerify,StatusOriginal ,RetweetID,RetweetCreateTime,RetweetText,RetweetSource,RetweetUserName,RetweetUserID,RetweetUserLocation,RetweetUserProvince,RetweetUserCity,RetweetUserCreateTime,RetweetUserGender,RetweetUserFans,RetweetUserFriends,RetweetUserStatusNo,RetweetUserVerify,"+
						  "SearchKey) values ("+ strinsert +" )");
				  }
		  }
	}
	
	//数据库插入方法friends
	public void InsertFunction2(JTable table,DefaultTableModel model){
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
				  dao.insert("insert into tb_FriendsSearch (StatusId,StatusCreateTime,StatusText,StatusSource,UserName,UserID,UserLocation,UserProvince,UserCity,UserCreateTime,UserGender,UserFans,UserFriends,UserStatusNo,UserVerify,StatusOriginal ,RetweetID,RetweetCreateTime,RetweetText,RetweetSource,RetweetUserName,RetweetUserID,RetweetUserLocation,RetweetUserProvince,RetweetUserCity,RetweetUserCreateTime,RetweetUserGender,RetweetUserFans,RetweetUserFriends,RetweetUserStatusNo,RetweetUserVerify,"+
						  "SearchKey) values ("+ strinsert +" )");
				  }
		  }
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


