package internalFrame.weibomonitor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.swing.text.DateFormatter;

import statisticsModel.ChangeCode;
import statisticsModel.DataAccess;
import statisticsModel.MyTableModel;
import statisticsModel.SimpleStatistics;
import statisticsModel.TextComponentPopupMenu;
import weibo4j.Weibo;
import dao.dao;
import dao.daoFunction;

public class StatusQuery  extends JPanel {
	/**
	 * 
	 */
	//���ݿ��ֶ�31��
	String[] col1 = {"΢��ID","����ʱ��","΢������","��Դ","�û���","�û�ID","����","����ʡ","������","ע��ʱ��","�Ա�","��˿��","��ע��","΢����","��֤","ԭ��","΢��ID","����ʱ��","΢������","��Դ","�û���","�û�ID","����","����ʡ","������","ע��ʱ��","�Ա�","��˿��","��ע��","΢����","��֤","������","����ʱ��","�澯"};
	public DefaultTableModel model1 = new DefaultTableModel();
	public JTable table1 = new JTable();
	JScrollPane scrollPane1=null;
	
	private static final long serialVersionUID = -3251502005148394518L;
	public ChangeCode cc=new ChangeCode();
	static DateFormat format =   new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat formatdate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat formatdate2 = new SimpleDateFormat("yyyy-MM-dd");
	
	static DateFormatter df = new DateFormatter(format);
	final static JTextField keyword1t = new JTextField(); 
	final static JTextField keyword1ft = new JTextField(); 
	final static JTextField keyword2ft = new JTextField(); 
	final static JFormattedTextField days1t = new JFormattedTextField(df);
	final static JFormattedTextField maxnot = new JFormattedTextField();
	final static JFormattedTextField itemst = new JFormattedTextField();
	final static JFormattedTextField days2t = new JFormattedTextField(df);
	public int[] norow={9,10,13,14,15,25,26,29,30,31};
	public int[] columns={80,160,500,80,100,80,80,50,50,160,50,50,50,50,30,50,80,160,500,80,100,80,80,50,50,80,30,50,50,50,30,80};
	public String searchlist=System.getProperty("user.dir")+"\\temp\\getsearchlist.xls";
	public MyTableModel tm=new MyTableModel();
	public TextComponentPopupMenu tpm=new TextComponentPopupMenu();
		
	public StatusQuery() {
		super();
		setLayout(new GridBagLayout());
		setBounds(0, 0, 280, 236);
		
		//�������ݱ�
		model1=tm.CreateModel(col1);
		table1 = tm.buildTable(model1);
		final TableRowSorter<DefaultTableModel> sorter1 =new TableRowSorter<DefaultTableModel>(model1);  
        table1.setRowSorter(sorter1);
        scrollPane1=new JScrollPane(table1);
        scrollPane1.setVisible(true);
        //�̶���ͷ
        table1.getTableHeader().setReorderingAllowed(false); 
        //���ˮƽ������
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table1.setColumnModel(tm.getColumn(table1, columns)); 
         
        //��һ�в���
        JLabel keyword1=new JLabel("�ؼ���:");
		final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
		//gridBagConstraints_11.weighty = 0;
		gridBagConstraints_11.insets = new Insets(5, 10, 0, 0);
		//gridBagConstraints_11.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_11.gridwidth = 2;
		gridBagConstraints_11.gridy = 0;
		gridBagConstraints_11.gridx = 0;
		gridBagConstraints_11.ipadx= 10;
		add(keyword1, gridBagConstraints_11);
		
		//����΢�������⣩���˹ؼ���1
		final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
		gridBagConstraints_12.weightx = 0;
		gridBagConstraints_12.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_12.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_12.anchor = GridBagConstraints.WEST;
		gridBagConstraints_12.gridwidth = 1;
		gridBagConstraints_12.gridy = 0;
		gridBagConstraints_12.gridx = 1;
		gridBagConstraints_12.ipadx= 80;
		add(keyword1t, gridBagConstraints_12);
		keyword1t.addMouseListener(tpm.getSharedInstance());  
		
		JLabel days1=new JLabel("ʱ�䷶Χ:");
		final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
		//gridBagConstraints_15.weighty = 0;
		gridBagConstraints_15.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_15.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_15.gridwidth = 2;
		gridBagConstraints_15.gridy = 0;
		gridBagConstraints_15.gridx = 2;
		add(days1, gridBagConstraints_15);
		
		//�趨����ʱ�䷶Χ
		days1t.setText("1984-01-01");
		final GridBagConstraints gridBagConstraints_16 = new GridBagConstraints();
		gridBagConstraints_16.weightx = 0;
		gridBagConstraints_16.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_16.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_16.anchor = GridBagConstraints.WEST;
		gridBagConstraints_16.gridwidth = 1;
		gridBagConstraints_16.gridy = 0;
		gridBagConstraints_16.gridx = 3;
		gridBagConstraints_16.ipadx= 60;
		add(days1t, gridBagConstraints_16);
		
		JLabel days2=new JLabel("��");
		final GridBagConstraints gridBagConstraints_17 = new GridBagConstraints();
		//gridBagConstraints_17.weighty = 0;
		gridBagConstraints_17.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_17.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_17.gridwidth = 2;
		gridBagConstraints_17.gridy = 0;
		gridBagConstraints_17.gridx = 4;
		add(days2, gridBagConstraints_17);
		
		//�趨����ʱ�䷶Χ
		SimpleStatistics ss=new SimpleStatistics();
		String today=formatdate2.format(ss.DaysAdd(new Date(), 1));
		days2t.setText(today);
		final GridBagConstraints gridBagConstraints_18 = new GridBagConstraints();
		gridBagConstraints_18.weightx = 0;
		gridBagConstraints_18.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_18.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_18.anchor = GridBagConstraints.WEST;
		gridBagConstraints_18.gridwidth = 1;
		gridBagConstraints_18.gridy = 0;
		gridBagConstraints_18.gridx = 5;
		gridBagConstraints_18.ipadx= 60;
		add(days2t, gridBagConstraints_18);
		
		JLabel items=new JLabel("΢����Ŀ:");
		final GridBagConstraints gridBagConstraints_19 = new GridBagConstraints();
		//gridBagConstraints_19.weighty = 0;
		gridBagConstraints_19.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_19.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_19.gridwidth = 2;
		gridBagConstraints_19.gridy = 0;
		gridBagConstraints_19.gridx = 6;
		add(items, gridBagConstraints_19);
		
		//�趨����ʱ�䷶Χ
		final GridBagConstraints gridBagConstraints_110 = new GridBagConstraints();
		gridBagConstraints_110.weightx = 0;
		gridBagConstraints_110.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_110.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_110.anchor = GridBagConstraints.WEST;
		gridBagConstraints_110.gridwidth = 1;
		gridBagConstraints_110.gridy = 0;
		gridBagConstraints_110.gridx = 7;
		gridBagConstraints_110.ipadx= 30;
		add(itemst, gridBagConstraints_110);
		
		//���ݿ��ȡ��ťsearch
		final JButton dbaccessr1=new JButton("΢������");
		final GridBagConstraints gridBagConstraints_3b11 = new GridBagConstraints();
		//gridBagConstraints_3b11.weighty = 0;
		gridBagConstraints_3b11.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_3b11.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_3b11.gridwidth = 1;
		gridBagConstraints_3b11.gridy = 0;
		gridBagConstraints_3b11.gridx = 8;
		add(dbaccessr1, gridBagConstraints_3b11);
		dbaccessr1.addActionListener(new ButtonQuerryListener1());
		
		//���ݿ��ȡ��ťfriends
		final JButton dbaccessr2=new JButton("��ע����");
		final GridBagConstraints gridBagConstraints_3b12 = new GridBagConstraints();
		//gridBagConstraints_3b12.weighty = 0;
		gridBagConstraints_3b12.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_3b12.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_3b12.gridwidth = 1;
		gridBagConstraints_3b12.gridy = 0;
		gridBagConstraints_3b12.gridx = 9;
		add(dbaccessr2, gridBagConstraints_3b12);
		dbaccessr2.addActionListener(new ButtonQuerryListener2());
		
		final JButton output=CreateSearchButton("���ݵ���");
		final GridBagConstraints gridBagConstraints_41 = new GridBagConstraints();
		//gridBagConstraints_41.weighty = 0;
		gridBagConstraints_41.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_41.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_41.gridwidth = 1;
		//gridBagConstraints_41.gridheight = 1;
		gridBagConstraints_41.gridy = 0;
		gridBagConstraints_41.gridx = 10;
		//gridBagConstraints_41.ipadx = 30;
		add(output, gridBagConstraints_41);
		
		final JButton delwb=tm.CreateButtonS("ѡ��ɾ��",model1,table1);
		final GridBagConstraints gridBagConstraints_3b2 = new GridBagConstraints();
		gridBagConstraints_3b2.weightx = 0;
		gridBagConstraints_3b2.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_3b2.fill = GridBagConstraints.EAST;
		gridBagConstraints_3b2.anchor = GridBagConstraints.EAST;
		gridBagConstraints_3b2.gridwidth = 1;
		gridBagConstraints_3b2.gridy = 0;
		gridBagConstraints_3b2.gridx = 11;
		add(delwb, gridBagConstraints_3b2);
		
		final JButton delall1=tm.CreateButtonA("ȫ��ɾ��",model1,table1);
		final GridBagConstraints gridBagConstraints_3b3 = new GridBagConstraints();
		gridBagConstraints_3b3.weightx = 0;
		gridBagConstraints_3b3.insets = new Insets(5, 20, 0, 0);
		gridBagConstraints_3b3.anchor = GridBagConstraints.EAST;
		gridBagConstraints_3b3.gridwidth = 1;
		gridBagConstraints_3b3.gridy = 0;
		gridBagConstraints_3b3.gridx = 12;
		add(delall1, gridBagConstraints_3b3);
		
		//�����в��֣�΢�������б�
		final GridBagConstraints gridBagConstraints_t41 = new GridBagConstraints();
		gridBagConstraints_t41.weightx = 0.8;
		gridBagConstraints_t41.weighty = 1.0;
		gridBagConstraints_t41.insets = new Insets(10, 10, 0, 10);
		gridBagConstraints_t41.fill = GridBagConstraints.BOTH;
		gridBagConstraints_t41.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_t41.gridwidth = 16;
		gridBagConstraints_t41.gridheight = 1;
		gridBagConstraints_t41.gridy = 3;
		gridBagConstraints_t41.gridx = 0;
		gridBagConstraints_t41.ipadx = 35;
		gridBagConstraints_t41.ipady = -195;
		add(scrollPane1, gridBagConstraints_t41);
		
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
	
	//������ťsearch
	public class ButtonQuerryListener1 implements ActionListener {
		  public void actionPerformed(ActionEvent evt) {
			  QueryFunction1(table1,model1);
		  }
	}
	
	//������ťfriends
	public class ButtonQuerryListener2 implements ActionListener {
		  public void actionPerformed(ActionEvent evt) {
			  QueryFunction2(table1,model1);
		  }
	}

	//���ݿ��ȡ����search
	public void QueryFunction1(JTable table,DefaultTableModel model){
		if(table.getModel().getRowCount()>0)
		  {
			  for (int index = model.getRowCount() - 1; index >= 0; index--) {
		            model.removeRow(index);
		        }
		  }
		SimpleStatistics ss=new SimpleStatistics();
		ResultSet rt=null;
		daoFunction df=new daoFunction();
		//��ȡ�����ؼ���
		List<String> arrayListtemp = new ArrayList<String>();
		//�����ַ�ת������
		List<String> arrayListtempf = new ArrayList<String>();
		
		String temp[]=keyword1t.getText().split(" ");
		for(int i=0;i<temp.length;i++){
			if(temp[i]!=""){
				arrayListtemp.add(temp[i]);
				arrayListtempf.add((cc.toShort(temp[i])));
			}
		}
		
		
		rt = dao.SqlExcute("SELECT StatusId,StatusCreateTime,StatusText FROM tb_StatusSearch");  
				
		//Access���ݿ�like���bug
		//if(arrayListtemp.size()<2){
		//rt = dao.SqlExcute("SELECT StatusId,StatusCreateTime,StatusText FROM tb_StatusSearch where StatusText Like '%"+arrayListtemp.get(0)+"%' or StatusText Like '%"+arrayListtempf.get(0)+"%'");  
		//	rt = dao.SqlExcute("SELECT StatusId,StatusCreateTime,StatusText FROM tb_StatusSearch where SearchKey = '"+arrayListtemp.get(0)+"' or SearchKey = '"+arrayListtempf.get(0)+"'");  
		//}else{
		//	rt = dao.SqlExcute("SELECT StatusId,StatusCreateTime,StatusText FROM tb_StatusSearch where ( StatusText Like '%"+arrayListtemp.get(0)+"%' or StatusText Like '%"+arrayListtempf.get(0)+"%') and ( StatusText Like '%"+arrayListtemp.get(1)+"%' or StatusText Like '%"+arrayListtempf.get(1)+"%')");  
		//System.out.println("SELECT StatusId,StatusCreateTime,StatusText FROM tb_StatusSearch where ( StatusText Like '%"+arrayListtemp.get(0)+"%' or StatusText Like '%"+arrayListtempf.get(0)+"%') and ( StatusText Like '%"+arrayListtemp.get(1)+"%' or StatusText Like '%"+arrayListtempf.get(1)+"%')");
		//}
				
		try {
			while(rt.next()){ 
				String strtime=rt.getString("StatusCreateTime");
				if(ss.DateBetweenCheck(strtime,days1t.getText(),days2t.getText())&&ss.KeyWordsSearch(arrayListtemp, arrayListtempf, rt.getString("StatusText"))){
					String sid=rt.getString("StatusId");
					String sql1="SELECT * FROM tb_StatusSearch where StatusId='"+sid+"'";
					df.RowSingleWrite(model1, sql1,norow,34,2);
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
  		itemst.setText(rows+"");
  		DataAccess da=new DataAccess();
	    da.ExcelCreate(searchlist, model1, col1);
	}
	
	//���ݿ��ȡ����friends
	public void QueryFunction2(JTable table,DefaultTableModel model){
		if(table.getModel().getRowCount()>0)
		  {
			  for (int index = model.getRowCount() - 1; index >= 0; index--) {
		            model.removeRow(index);
		        }
		  }
		SimpleStatistics ss=new SimpleStatistics();
		ResultSet rt=null;
		daoFunction df=new daoFunction();
		//��ȡ�����ؼ���
		List<String> arrayListtemp = new ArrayList<String>();
		//�����ַ�ת������
		List<String> arrayListtempf = new ArrayList<String>();
		String temp[]=keyword1t.getText().split(" ");
		for(int i=0;i<temp.length;i++){
			if(temp[i]!=""){
				arrayListtemp.add(temp[i]);
				arrayListtempf.add((cc.toShort(temp[i])));
			}
		}
				
		rt = dao.SqlExcute("SELECT StatusId,StatusCreateTime,StatusText FROM tb_FriendsSearch");  
				
		//Access���ݿ�like���bug
		//if(arrayListtemp.size()<2){
		//rt = dao.SqlExcute("SELECT StatusId,StatusCreateTime,StatusText FROM tb_StatusSearch where StatusText Like '%"+arrayListtemp.get(0)+"%' or StatusText Like '%"+arrayListtempf.get(0)+"%'");  
		//	rt = dao.SqlExcute("SELECT StatusId,StatusCreateTime,StatusText FROM tb_StatusSearch where SearchKey = '"+arrayListtemp.get(0)+"' or SearchKey = '"+arrayListtempf.get(0)+"'");  
		//}else{
		//	rt = dao.SqlExcute("SELECT StatusId,StatusCreateTime,StatusText FROM tb_StatusSearch where ( StatusText Like '%"+arrayListtemp.get(0)+"%' or StatusText Like '%"+arrayListtempf.get(0)+"%') and ( StatusText Like '%"+arrayListtemp.get(1)+"%' or StatusText Like '%"+arrayListtempf.get(1)+"%')");  
		//System.out.println("SELECT StatusId,StatusCreateTime,StatusText FROM tb_StatusSearch where ( StatusText Like '%"+arrayListtemp.get(0)+"%' or StatusText Like '%"+arrayListtempf.get(0)+"%') and ( StatusText Like '%"+arrayListtemp.get(1)+"%' or StatusText Like '%"+arrayListtempf.get(1)+"%')");
		//}
				
		try {
			while(rt.next()){ 
				String strtime=rt.getString("StatusCreateTime");
				if(ss.DateBetweenCheck(strtime,days1t.getText(),days2t.getText())&&ss.KeyWordsSearch(arrayListtemp, arrayListtempf, rt.getString("StatusText"))){
					String sid=rt.getString("StatusId");
					String sql1="SELECT * FROM tb_FriendsSearch where StatusId='"+sid+"'";
					df.RowSingleWrite(model1, sql1,norow,32,2);
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
  		itemst.setText(rows+"");
  		DataAccess da=new DataAccess();
	    da.ExcelCreate(searchlist, model1, col1);
	}
	//���ݵ�����ť
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
			          	da.ExcelCreate(zipname,model1,col1);
			          }else if (JOptionPane.showConfirmDialog(new JFrame(),
			                  "ȷ�ϸ����ļ���", "����",
			                  JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
			        	  da.ExcelCreate(zipname,model1,col1);	    
			              }
			          }
			}
		});
	return button;
	}
	
}
