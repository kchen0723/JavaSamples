package weibo4j.examples.WindowsWeibo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import weibo4j.User;
import weibo4j.Weibo;
import weibo4j.WeiboException;

public class BatchDestroyFriendship extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 20111006;
	
	String[] col10 = { "用户名","用户ID","粉丝??","微博数","关注数","地区","认证","注册时间","更新时间"};
	public DefaultTableModel model10 = new DefaultTableModel();
	public JTable table10 = new JTable();
	String[] col11 = { "用户名","用户ID","粉丝数","微博数","关注数","地区","认证","注册时间","更新时间"};
	public DefaultTableModel model11 = new DefaultTableModel();
	public JTable table11 = new JTable();
	String[] col8 = { "用户名称","次数","粉丝数","关注数","微博数","认证"};
	public DefaultTableModel model8 = new DefaultTableModel();
	public JTable table12 = new JTable();
	String[] col12 = { "用户名称","次数","粉丝数","关注数","微博数","认证"};
	public DefaultTableModel model9 = new DefaultTableModel();
	public JTable table9 = new JTable();
	public JComboBox jcb = new JComboBox();
	final static JTextField fannot = new JTextField(); 
	final static JTextField trendnot = new JTextField(); 
	final static JFormattedTextField endpaget2 = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
	final static Format shortDate = DateFormat.getDateInstance(DateFormat.SHORT);
	final static JFormattedTextField dateset1 = new JFormattedTextField(shortDate);
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public BatchDestroyFriendship(){
		this.setLayout(null);
		this.setBounds(5,40,880,620);
		
		JLabel fanno=new JLabel("设置粉丝数:");
		fanno.setBounds(350, 20, 120, 20);
		this.add(fanno);

		fannot.setText("1000");
		fannot.setBounds(420, 20, 40, 20);
		this.add(fannot);
		
		JLabel trendno=new JLabel("输入话题:");
		trendno.setBounds(240, 300, 120, 20);
		this.add(trendno);

		trendnot.setBounds(300, 300, 80, 20);
		this.add(trendnot);
		
		final JButton friends=new JButton("查询关注");
		friends.setBounds(20, 20, 90, 20);
		this.add(friends);
		
		final JButton del10=new JButton("全部删除");
		del10.setBounds(130, 20, 90, 20);
		this.add(del10);
		
		JLabel monthset=new JLabel("设置月份");
		monthset.setBounds(240, 20, 90, 20);
		this.add(monthset);
		
		for(int i=0;i<12;i++){
			jcb.addItem(i+4);
		}
				
		jcb.setBounds(300, 20, 40, 20);
		this.add(jcb);
		
		final JButton comments=new JButton("查询评论");
		comments.setBounds(20, 20, 90, 20);
		this.add(comments);
		
		final JButton del6=new JButton("全部删除");
		del6.setBounds(130, 20, 90, 20);
		//this.add(del6);
		
		//final JButton up=new JButton("开始");
		//up.setBounds(670,500, 80, 20);
		//this.add(up);
		
		//final JButton ub=new JButton("停止");
		//ub.setBounds(770,500, 80, 20);
		//this.add(ub);
		
		final JButton mentions=new JButton("查询话题");
		mentions.setBounds(20, 300, 90, 20);
		this.add(mentions);
		
		final JButton del7=new JButton("全部删除");
		del7.setBounds(130, 300, 90, 20);
		this.add(del7);
		
		final JButton batchcancel=new JButton("批量取消");
		batchcancel.setBounds(520, 20, 90, 20);
		this.add(batchcancel);
		
		//final JButton recancel=new JButton("移出取消");
		//recancel.setBounds(630, 20, 90, 20);
		//this.add(recancel);
		
		//final JButton del11=new JButton("全部删除");
		//del11.setBounds(740, 20, 90, 20);
		//this.add(del11);
		
		//final JButton mentionsa=new JButton("分析@我");
		//mentionsa.setBounds(520, 300, 90, 20);
		//this.add(mentionsa);
		
		//final JButton del9=new JButton("全部删除");
		//del9.setBounds(630, 300, 90, 20);
		//this.add(del9);
		
		model10 = new DefaultTableModel(col10, 0)
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
		
		table10 = new JTable(model10);
		final TableRowSorter<DefaultTableModel> sorter10 =new TableRowSorter<DefaultTableModel>(model10);  
        table10.setRowSorter(sorter10);
        
		JScrollPane scrollPane10=new JScrollPane(table10);
		scrollPane10.setBounds(20, 50, 440, 240);
		this.add(scrollPane10);
		
		model11 = new DefaultTableModel(col11, 0)
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
		
		table11 = new JTable(model11);
		final TableRowSorter<DefaultTableModel> sorter11 =new TableRowSorter<DefaultTableModel>(model11);  
        table11.setRowSorter(sorter11);
        
		JScrollPane scrollPane11=new JScrollPane(table11);
		scrollPane11.setBounds(480, 50, 380, 240);
		this.add(scrollPane11);
		
		del10.addActionListener(new delete10());
		friends.addActionListener(new GetFriends());
		batchcancel.addActionListener(new BatchCancel());
		table10.addMouseListener(new TableListeners10());
		table11.addMouseListener(new TableListeners11());
	}
	
	public class delete10 implements ActionListener{
		public void actionPerformed(ActionEvent el) {
			cleartable(model10 , table10);
		}	
	}
	
	public class TableListeners10 implements MouseListener {
		public void mouseClicked(MouseEvent e){
		}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		public void mousePressed(MouseEvent arg0) {
			cancel(model10,table10,model11,table11);
		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}
	}
	
	public class TableListeners11 implements MouseListener {
		public void mouseClicked(MouseEvent e){
		}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		public void mousePressed(MouseEvent arg0) {
			int row=table11.getSelectedRow();
			model11.removeRow(row);
		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}
	}
	
	
	public class BatchCancel implements ActionListener{
		public void actionPerformed(ActionEvent el) {
			System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
	    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
			int number=0;
	    	try {
				Weibo weibo = new Weibo();
				weibo.setToken(Weibo.getat(),Weibo.getats());
				for(int i=0;i<model11.getRowCount();i++){
					User user = weibo.destroyFriendship(model11.getValueAt(i,1)+"");//args[2]:关注用户的id
					System.out.println(user.toString());
					number++;
					model11.removeRow(i);
				}
				JOptionPane.showMessageDialog(null, "取消"+number+"个关注！", "提示", JOptionPane.ERROR_MESSAGE);
			} catch (WeiboException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public class GetFriends implements ActionListener {
		public void actionPerformed(ActionEvent el){
			Date dateset=datecompute(jcb.getSelectedItem());
			System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
	    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
	      	try {
				Weibo weibo = new Weibo();
				weibo.setToken(Weibo.getat(),Weibo.getats());
			  	int end=weibo.getUserTimeline().get(1).getUser().getFollowersCount()+20;
				for(int cursor = 1;cursor<=end;cursor=cursor+20)
				{	
				//采用cursor参数处理翻页
				List<User> list = weibo.getFriendsStatuses(cursor);
				for(User user : list) {
					 	String s1=user.getName();
						String s2=user.getId()+"";
						String s3=user.getFollowersCount()+"";
						String s4=user.getFriendsCount()+"";
						String s5=user.getStatusesCount()+"";
						String s6=user.getLocation(); 
						String s7=user.isVerified()+"";
						String s8=user.getCreatedAt()+"";
						String s9=null;
						if(user.getStatus()==null)
						{
							s9=null;	
						}
						else if(checkdate(dateset,user.toString3())==false&&checkfanno(fannot.getText(),user.getFollowersCount())==true){
							s9=format.format(user.toString3());
							String[] str_row = {s1,s2,s3,s4,s5,s6,s7,s8,s9};  
							model10.addRow(str_row);
						}
					}
				}
			} catch (WeiboException e) {
				e.printStackTrace();
			}
			System.out.println(format.format(dateset));
		}
	}
	
	public void cleartable(DefaultTableModel model , JTable table){
		if(table.getModel().getRowCount()>0)
		  {
			  for (int index = model.getRowCount() - 1; index >= 0; index--) {
		            model.removeRow(index);
		        }
		  }
	}
	
	public void cancel(DefaultTableModel modelout,JTable tableout,DefaultTableModel modelin,JTable tablein){
		int row=tableout.getSelectedRow();
			String[] str=new String[9];
			for(int j=0;j<modelout.getColumnCount();j++){
				str[j]=tableout.getValueAt(row,j)+"";
			}
			
			if(tablein.getRowCount()==0){
				modelin.addRow(str);
			}
			else{
				Object ss=tableout.getValueAt(row,0);
				if(check(ss,tablein)){
					JOptionPane.showMessageDialog(null, "重复填加！", "提示", JOptionPane.ERROR_MESSAGE);
				}
				else{
					modelin.addRow(str);
					}
		   }
	}
	
	public Date datecompute(Object month){
		Date now=new Date();
		Calendar  g = Calendar.getInstance();
		g.setTime(now);
		int monthex=Integer.parseInt(month+"");  
		int monthnow=g.get(Calendar.MONTH); 
		if(monthex>monthnow){
			g.add(2,12-monthex);
			g.add(1,-1);
		}
		else{
			g.add(2, -monthex);
		}
		return g.getTime(); 
	}
	
	public boolean checkdate(Date dateset,Date dateget) {
		if(dateget.after(dateset)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean checkfanno(String textset,int textget) {
		int a=Integer.parseInt(textset);
		if(textget<a){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean check(Object ss,JTable table)
	{
		for(int i=0;i<table.getRowCount();i++)
			if(ss.equals(table.getValueAt(i, 0)))
			return true;
			return false;
	}
}
