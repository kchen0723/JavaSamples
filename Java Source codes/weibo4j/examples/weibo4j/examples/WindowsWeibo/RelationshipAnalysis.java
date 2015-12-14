package weibo4j.examples.WindowsWeibo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
import weibo4j.Comment;
import weibo4j.Paging;
import weibo4j.Status;
import weibo4j.Weibo;

public class RelationshipAnalysis extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 20111006;
	
	String[] col6 = { "用户名称","用户ID","评论内容","评论时间","粉丝数","关注数","微博数","微博内容","发布时间"};
	public DefaultTableModel model6 = new DefaultTableModel();
	public JTable table6 = new JTable();
	String[] col7 = { "用户名称","用户ID","@内容","@时间","粉丝数","关注数","微博数"};
	public DefaultTableModel model7 = new DefaultTableModel();
	public JTable table7 = new JTable();
	String[] col8 = { "用户名称","用户ID","次数","粉丝数","关注数","微博数"};
	public DefaultTableModel model8 = new DefaultTableModel();
	public JTable table8 = new JTable();
	String[] col9 = { "用户名称","用户ID","次数","粉丝数","关注数","微博数"};
	public DefaultTableModel model9 = new DefaultTableModel();
	public JTable table9 = new JTable();
	final static JFormattedTextField startpaget = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
	final static JFormattedTextField endpaget = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
	final static JFormattedTextField startpaget2 = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
	final static JFormattedTextField endpaget2 = new JFormattedTextField(NumberFormat.getIntegerInstance()); 
	final static Format shortDate = DateFormat.getDateInstance(DateFormat.SHORT);
	final static JFormattedTextField dateset1 = new JFormattedTextField(shortDate);
	final static JFormattedTextField dateset2 = new JFormattedTextField(shortDate);
	
	List<Long> arrayList2 = new ArrayList<Long>();
	
	boolean judge=true;
	
	public RelationshipAnalysis(){
		this.setLayout(null);
		this.setBounds(5,40,880,620);

		JLabel startdate1=new JLabel("输入截止日期:");
		startdate1.setBounds(240, 20, 120, 20);
		this.add(startdate1);
		
		dateset1.setColumns(20);
		Date aa1 =java.sql.Date.valueOf("2011-10-01");
		dateset1.setValue(aa1);
		dateset1.setBounds(330, 20, 60, 20);
		this.add(dateset1);
		
		JLabel startdate2=new JLabel("输入截止日期:");
		startdate2.setBounds(240, 300, 120, 20);
		this.add(startdate2);
		
		dateset2.setColumns(20);
		Date aa2 =java.sql.Date.valueOf("2011-10-01");
		dateset2.setValue(aa2);
		dateset2.setBounds(330, 300, 60, 20);
		this.add(dateset2);
		
		startpaget.setText("1");
		startpaget.setBounds(330, 20, 30, 20);
		//this.add(startpaget);
		
		JLabel endpage=new JLabel("转发结束页码:");
		endpage.setBounds(370, 20, 120, 20);
		//this.add(endpage);
		
		endpaget.setText("10");
		endpaget.setBounds(460, 20, 30, 20);
		//this.add(endpaget);
		
		JLabel startpage2=new JLabel("用户起始页码:");
		startpage2.setBounds(540, 20, 120, 20);
		//this.add(startpage2);
		
		startpaget2.setText("1");
		startpaget2.setBounds(530, 20, 30, 20);
		//this.add(startpaget2);
		
		JLabel endpage2=new JLabel("用户结束页码:");
		endpage2.setBounds(570, 20, 120, 20);
		//this.add(endpage2);
		
		endpaget2.setText("10");
		endpaget2.setBounds(660, 20, 30, 20);
		//this.add(endpaget2);
		
		final JButton comments=new JButton("查询评论");
		comments.setBounds(20, 20, 90, 20);
		this.add(comments);
		
		final JButton del6=new JButton("全部删除");
		del6.setBounds(130, 20, 90, 20);
		this.add(del6);
		
		final JButton mentions=new JButton("查询@我");
		mentions.setBounds(20, 300, 90, 20);
		this.add(mentions);
		
		final JButton del7=new JButton("全部删除");
		del7.setBounds(130, 300, 90, 20);
		this.add(del7);
		
		final JButton commentsa=new JButton("分析评论");
		commentsa.setBounds(520, 20, 90, 20);
		this.add(commentsa);
		
		final JButton del8=new JButton("全部删除");
		del8.setBounds(630, 20, 90, 20);
		this.add(del8);
		
		final JButton mentionsa=new JButton("分析@我");
		mentionsa.setBounds(520, 300, 90, 20);
		this.add(mentionsa);
		
		final JButton del9=new JButton("全部删除");
		del9.setBounds(630, 300, 90, 20);
		this.add(del9);		
		

		
		model6 = new DefaultTableModel(col6, 0)
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
		
		table6 = new JTable(model6);
		final TableRowSorter<DefaultTableModel> sorter6 =new TableRowSorter<DefaultTableModel>(model6);  
        table6.setRowSorter(sorter6);
        
		JScrollPane scrollPane6=new JScrollPane(table6);
		scrollPane6.setBounds(20, 50, 480, 240);
		this.add(scrollPane6);
		
		model7 = new DefaultTableModel(col7, 0)
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
		
		table7 = new JTable(model7);
		final TableRowSorter<DefaultTableModel> sorter7 =new TableRowSorter<DefaultTableModel>(model7);  
        table7.setRowSorter(sorter7);
        
		JScrollPane scrollPane7=new JScrollPane(table7);
		scrollPane7.setBounds(20, 330, 480, 240);
		this.add(scrollPane7);
		
		model8 = new DefaultTableModel(col8, 0)
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
		
		table8 = new JTable(model8);
		final TableRowSorter<DefaultTableModel> sorter8 =new TableRowSorter<DefaultTableModel>(model8);  
        table8.setRowSorter(sorter8);
        
		JScrollPane scrollPane8=new JScrollPane(table8);
		scrollPane8.setBounds(520, 50, 340, 240);
		this.add(scrollPane8);
		
		model9 = new DefaultTableModel(col9, 0)
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
		
		table9 = new JTable(model9);
		final TableRowSorter<DefaultTableModel> sorter9 =new TableRowSorter<DefaultTableModel>(model9);  
        table9.setRowSorter(sorter9);
        
		JScrollPane scrollPane9=new JScrollPane(table9);
		scrollPane9.setBounds(520, 330, 340, 240);
		this.add(scrollPane9);
				
		comments.addActionListener(new GetCommentsToMe());
		commentsa.addActionListener(new StatComments());
		mentions.addActionListener(new GetMentions());
		mentionsa.addActionListener(new StatMetions());
		del6.addActionListener(new delete6());
		del7.addActionListener(new delete7());
		del8.addActionListener(new delete8());
		del9.addActionListener(new delete9());
	}
	
	public class delete6 implements ActionListener{
		public void actionPerformed(ActionEvent el) {
			cleartable(model6 , table6);
		}	
	}
	
	public class delete7 implements ActionListener{
		public void actionPerformed(ActionEvent el) {
			cleartable(model7 , table7);
		}	
	}
	
	public class delete8 implements ActionListener{
		public void actionPerformed(ActionEvent el) {
			cleartable(model8 , table8);
		}	
	}
	
	public class delete9 implements ActionListener{
		public void actionPerformed(ActionEvent el) {
			cleartable(model9 , table9);
		}	
	}
	
	public class StatComments implements ActionListener{
		public void actionPerformed(ActionEvent el) {
			if(model6.getRowCount()==0)  
			  {	  
				JOptionPane.showMessageDialog(null, "关注列表为空，点击“查询评论”获取关注列表！", "提示", JOptionPane.ERROR_MESSAGE);
			  }
			else{
				redundanc(model6,model8,4);	
			}
		}	
	}
	
	public class StatMetions implements ActionListener{
		public void actionPerformed(ActionEvent el) {
			if(model7.getRowCount()==0)  
			  {	  
				JOptionPane.showMessageDialog(null, "@列表为空，点击“查询@我”获取@列表！", "提示", JOptionPane.ERROR_MESSAGE);
			  }
			else{
				redundanc(model7,model9,4);	
			}
		}	
	}
	
	public class GetCommentsToMe implements ActionListener{
		public void actionPerformed(ActionEvent el) {
			System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
	    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
	    	//int start=Integer.parseInt(startpaget.getText());
    		//int end=Integer.parseInt(endpaget.getText());
    		a:	
	    	for(int i=1;i<200000;i++)
    			{	
        			try {
        	        	Weibo weibo = new Weibo();
        				weibo.setToken(Weibo.getat(),Weibo.getats());
        				Date ss=weibo.getUserTimeline().get(1).getUser().getCreatedAt();
        				List<Comment> comments = weibo.getCommentsToMe(new Paging(i,20));
        	        	for(Comment comment : comments) {
        	    			Date s4=comment.getCreatedAt();
        	    			
        	    			judge=checkout(((DateFormat) shortDate).parse(dateset1.getText()),s4);
        	    			boolean j2=checkout(((DateFormat) shortDate).parse(dateset1.getText()),ss);
        	    			if(judge==false)
        	    			{
        	    				break a;
        	    			}
        	    			else if(j2==true){
        	    				JOptionPane.showMessageDialog(null, "时间过早，用户还未创建，请重新输入时间", "提示", JOptionPane.ERROR_MESSAGE);
        	    				break a;
        	    			}
        	    			else{
        	    				String s1=comment.getUser().getName();
        	    				String s2=comment.getUser().getId()+"";
        	    				String s3=comment.getText();
        	    				int s5=comment.getUser().getFollowersCount();
        	    				int s8=comment.getUser().getFriendsCount();
        	    				int s9=comment.getUser().getStatusesCount();
        	    				String s6=comment.getStatus().getText();
                	    		Date s7=comment.getStatus().getCreatedAt();
                	    		Object[] str_row = {s1,s2,s3,s4,s5,s8,s9,s6,s7};  
                				model6.addRow(str_row);
                				}
        	    		}
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
    			}
    	}
	}
	
	public class GetMentions implements ActionListener{
		public void actionPerformed(ActionEvent el) {
			System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
	    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
	    	a:
	    	for(int i=1;i<20000;i++)
    		{	
    			try {
    	        	Weibo weibo = new Weibo();
    				weibo.setToken(Weibo.getat(),Weibo.getats());
    				Date ss=weibo.getUserTimeline().get(1).getUser().getCreatedAt();
    				List<Status> list = weibo.getMentions(new Paging(i,20));
    	    		for(Status status : list) {
    	    			Date s4=status.getCreatedAt();
    	    			judge=checkout(((DateFormat) shortDate).parse(dateset2.getText()),s4);
    	    			boolean j2=checkout(((DateFormat) shortDate).parse(dateset2.getText()),ss);
    	    			if(judge==false)
    	    			{
    	    				break a;
    	    			}
    	    			else if(j2==true){
    	    				JOptionPane.showMessageDialog(null, "时间过早，用户还未创建，请重新输入时间", "提示", JOptionPane.ERROR_MESSAGE);
    	    				break a;
    	    			}
    	    			else{
    	    				String s1=status.getUser().getName();
    	    				String s2=status.getUser().getId()+"";
            	    		String s3=status.getText();
            	    		int s5=status.getUser().getFollowersCount();
            	    		int s6=status.getUser().getFriendsCount();
            	    		int s7=status.getUser().getStatusesCount();
    	    				Object[] str_row = {s1,s2,s3,s4,s5,s6,s7};  
            				model7.addRow(str_row);
           	    			}
    	    		}
    			} catch (Exception e) {
    				e.printStackTrace();
    			}	
    		}
    	}
	}
	
	public void redundanc(DefaultTableModel modelraw,DefaultTableModel model,int data){
		List<String> arrayList = new ArrayList<String>();
		for(int i=0;i<modelraw.getRowCount();i++){
			arrayList.add((String) modelraw.getValueAt(i,1));
		}
		String[] s1 = arrayList.toArray(new String[arrayList.size()]); 
		HashSet<String> hashSet = new HashSet<String>(arrayList);
	    List<String> arrayListNext = new ArrayList<String>(hashSet);
	    String[] s2 = arrayListNext.toArray(new String[arrayListNext.size()]);
	    for(int i=0;i<s2.length;i++){
			for(int j=0;j<modelraw.getRowCount();j++){
				if(s2[i].equals(modelraw.getValueAt(j,1))){
					int str2=stat(s2[i],s1);
					String str1=(String) modelraw.getValueAt(j,0);
					String str3= modelraw.getValueAt(j,data)+"";
					String str4= modelraw.getValueAt(j,data+1)+"";
					String str5= modelraw.getValueAt(j,data+2)+"";
					String str7=s2[i];
					Object[] str_row = {str1,str7,str2,str3,str4,str5};  
					model.addRow(str_row);
					break;
				}
			}
		}
	}
	
	public boolean checkout(Date setdate,Date commentsdate){
		if(commentsdate.after(setdate))
		{
			return true;
		}
		else{
			return false;
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
	
	 public int stat(String s,String str[] ){
		int num=0;
		for(int i=0;i<str.length;i++){
			if(s.equals(str[i])){
				num++;
			}
		} 
		return num;
	}
}