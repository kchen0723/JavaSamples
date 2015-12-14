package internalFrame.fansmanagement;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import statisticsModel.DataAccess;
import statisticsModel.MyTableModel;
import statisticsModel.SimpleStatistics;
import statisticsModel.TextComponentPopupMenu;
import weibo4j.Weibo;
import weibo4j.WeiboException;
import dao.dao;

public class FriendCreate extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5970069439721779819L;
	String[] col1 = { "���","�û�����","�û���","�û�ID","���ڵ���","��֤","�Ա�","��˿��","��ע��","΢����","����ʱ��","ע��ʱ��","΢��ID" };
	String[] col2 = { "�û�����","�û���","�û�ID","���ڵ���","��֤","�Ա�","��˿��","��ע��","΢����","����ʱ��","ע��ʱ��","΢��ID","��עʱ��"};
	public int[] columns1={50,500,120,100,80,80,50,50,50,50,150,150,80};
	public int[] columns2={500,120,100,80,80,50,50,50,50,150,150,80,100};
	public int[] datacolumn={8,9,10}; 
	public DefaultTableModel model1 = new DefaultTableModel();
	public JTable table1 = new JTable();
	public DefaultTableModel model2 = new DefaultTableModel();
	public JTable table2 = new JTable();
	final static JFormattedTextField batchitemst = new JFormattedTextField();
	final static JFormattedTextField createitemst = new JFormattedTextField();
	public DataAccess da=new DataAccess();
	public SimpleStatistics ss=new SimpleStatistics();
	public MyTableModel tm=new MyTableModel();
	public SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public TextComponentPopupMenu tpm=new TextComponentPopupMenu();
	
	public FriendCreate(){
		super();
		setLayout(new GridBagLayout());
		setBounds(0, 0, 280, 236);
					
		//�������ݱ�1��Ԥ��ע��
		model1=tm.CreateModel(col1);
		table1 = tm.buildTable(model1);
		final TableRowSorter<DefaultTableModel> sorter1 =new TableRowSorter<DefaultTableModel>(model1);  
        table1.setRowSorter(sorter1);
        JScrollPane scrollPane1=new JScrollPane(table1);
               
        //�������ݱ�2����ע��
        model2=tm.CreateModel(col2);
		table2 = tm.buildTable(model2);
		final TableRowSorter<DefaultTableModel> sorter2 =new TableRowSorter<DefaultTableModel>(model2);  
        table2.setRowSorter(sorter2);
        JScrollPane scrollPane2=new JScrollPane(table2);
        //�̶���ͷ
        table1.getTableHeader().setReorderingAllowed(false); 
        table2.getTableHeader().setReorderingAllowed(false); 
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table1.setColumnModel(tm.getColumn(table1, columns1)); 
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table2.setColumnModel(tm.getColumn(table2, columns2)); 
        
        //��һ�в��֣�����ѡ����ܰ�ť��
        final JButton prefriendget=new JButton("��ȡԤ��ע��");//��ȡԤ��ע�б���ѡ�����أ�
		final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
		//gridBagConstraints_11.weighty = 0;
		gridBagConstraints_11.insets = new Insets(5, 10, 0, 0);
		//gridBagConstraints_11.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_11.gridwidth = 2;
		gridBagConstraints_11.gridy = 0;
		gridBagConstraints_11.gridx = 0;
		add(prefriendget, gridBagConstraints_11);
		prefriendget.addActionListener(new FileUpload());
		
		final JButton deletes1=tm.CreateButtonS("ɾ����������",model1,table1);//ɾ��Ԥ��ע�б�������
		final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
		//gridBagConstraints_12.weighty = 0;
		gridBagConstraints_12.insets = new Insets(5, 10, 0, 0);
		//gridBagConstraints_12.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_12.gridwidth = 2;
		gridBagConstraints_12.gridy = 0;
		gridBagConstraints_12.gridx = 1;
		add(deletes1, gridBagConstraints_12);
		
		final JButton deletea1=tm.CreateButtonA("ɾ��ȫ������",model1,table1);//ɾ��Ԥ��ע�б�ȫ������
		final GridBagConstraints gridBagConstraints_13 = new GridBagConstraints();
		//gridBagConstraints_13.weighty = 0;
		gridBagConstraints_13.insets = new Insets(5, 10, 0, 0);
		//gridBagConstraints_13.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_13.gridwidth = 2;
		gridBagConstraints_13.gridy = 0;
		gridBagConstraints_13.gridx = 2;
		add(deletea1, gridBagConstraints_13);
		
		JLabel batchitems=new JLabel("���õ����������������");
		final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
		//gridBagConstraints_14.weighty = 0;
		gridBagConstraints_14.insets = new Insets(5, 10, 0, 0);
		//gridBagConstraints_14.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_14.gridwidth = 2;
		gridBagConstraints_14.gridy = 0;
		gridBagConstraints_14.gridx = 3;
		add(batchitems, gridBagConstraints_14);
		
		final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
		gridBagConstraints_15.weightx = 0;
		gridBagConstraints_15.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_15.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_15.anchor = GridBagConstraints.WEST;
		gridBagConstraints_15.gridwidth = 1;
		gridBagConstraints_15.gridy = 0;
		gridBagConstraints_15.gridx = 4;
		gridBagConstraints_15.ipadx= 30;
		add(batchitemst, gridBagConstraints_15);
		batchitemst.setText("10");
		
		//�ڶ��в��֣�Ԥ��ע��
		final GridBagConstraints gridBagConstraints_21 = new GridBagConstraints();
		gridBagConstraints_21.weightx = 0.8;
		gridBagConstraints_21.weighty = 1.0;
		gridBagConstraints_21.insets = new Insets(10, 10, 0, 10);
		gridBagConstraints_21.fill = GridBagConstraints.BOTH;
		gridBagConstraints_21.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_21.gridwidth = 12;
		gridBagConstraints_21.gridheight = 1;
		gridBagConstraints_21.gridy = 1;
		gridBagConstraints_21.gridx = 0;
		gridBagConstraints_21.ipadx = 35;
		gridBagConstraints_21.ipady = -195;
		add(scrollPane1, gridBagConstraints_21);
		
		//�����в��֣���ע��ť��
		final JButton friendcreate=new JButton("��ע�����û�");//��עԤѡ���û�
		final GridBagConstraints gridBagConstraints_31 = new GridBagConstraints();
		//gridBagConstraints_31.weighty = 0;
		gridBagConstraints_31.insets = new Insets(5, 10, 0, 0);
		//gridBagConstraints_31.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_31.gridwidth = 2;
		gridBagConstraints_31.gridy = 2;
		gridBagConstraints_31.gridx = 0;
		add(friendcreate, gridBagConstraints_31);
		friendcreate.addActionListener(new CreateFriendship());
		
		JLabel createitems=new JLabel("�ѹ�ע�û�����");
		final GridBagConstraints gridBagConstraints_32 = new GridBagConstraints();
		//gridBagConstraints_32.weighty = 0;
		gridBagConstraints_32.insets = new Insets(5, 10, 0, 0);
		//gridBagConstraints_32.fill = GridBagConstraints.HORIZONTAL;
		//gridBagConstraints_32.gridwidth = 2;
		gridBagConstraints_32.gridy = 2;
		gridBagConstraints_32.gridx = 1;
		add(createitems, gridBagConstraints_32);
		
		final GridBagConstraints gridBagConstraints_33 = new GridBagConstraints();
		gridBagConstraints_33.weightx = 0;
		gridBagConstraints_33.insets = new Insets(5, 0, 0, 0);
		//gridBagConstraints_33.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_33.anchor = GridBagConstraints.WEST;
		gridBagConstraints_33.gridwidth = 1;
		gridBagConstraints_33.gridy = 2;
		gridBagConstraints_33.gridx = 2;
		gridBagConstraints_33.ipadx= 30;
		add(createitemst, gridBagConstraints_33);
		
		//�����в��֣���ע��
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
		add(scrollPane2, gridBagConstraints_41);
		
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
	
	//Ԥ��ע���ʼ��
	public class FileUpload implements ActionListener {
		public void actionPerformed(ActionEvent el){
			String uploadno = batchitemst.getText();
			Object temp[] = new Object[13];
			ResultSet rt = dao.SqlExcute("SELECT top "+ uploadno +" * from tb_PreFriendsList where UserFriends > 0 Order by ID desc ");
			//ResultSet rt = dao.SqlExcute("SELECT * from tb_PreFriendsList where UserFriends > 0 Order by ID desc ");
			ResultSetMetaData rsmd=null;
			try {
				rsmd = rt.getMetaData();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				while(rt.next()){ 
					for(int i=0;i<13;i++){
						if(ss.NoRowCheck(i+1, datacolumn)){
							int str=rt.getInt(rsmd.getColumnName(i+1));
							temp[i]=str;
						}else{
							String str=rt.getString(rsmd.getColumnName(i+1));
							temp[i]=str;
						}
					}
				model1.addRow(temp);
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
	    }
	}
	
	//�����ӹ�ע
	public class CreateFriendship implements ActionListener  {
		public void actionPerformed(ActionEvent el){
			System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
	    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
	    	int rows = table1.getModel().getRowCount();
			int columns = table1.getModel().getColumnCount();
			int createitems=0;
	    	int notcreat=0;
	    	String createtime=format.format(new Date());
	    	try {
				
				Weibo weibo = new Weibo();
				weibo.setToken(Weibo.getat(),Weibo.getats());
				String myid=weibo.getUserTimeline().get(1).getUser().getId()+"";
				
				for(int i=0;i<rows;i++){
					//tempid��ȡ����Access���Զ���ţ��Ա�ɾ������
					long tempid=Long.parseLong(table1.getValueAt(i, 0)+"");
					//userid��ȡ�û�ID
					String userid=table1.getValueAt(i, 3)+"";
					dao.update("Delete FROM tb_PreFriendsList where ID = "+ tempid);
					//�ж��û��Ƿ��Թ�ע����δ��ע���򴴽�
					boolean bool = weibo.existsFriendship(myid,userid);//myid:�Լ���id��ids.get(i):��ע�����id
					if(bool==false){
						weibo.createFriendship(userid);//args[2]:��ע�û���id
						//��¼��ע�û���
						createitems++;
						//��ǰ��ע�û���FriendsList����¼��עʱ��
						Object tempdata[]=new Object[13];
						String strinsert="";
						for(int j=1;j<columns;j++){
							String tb=table1.getValueAt(i, j)+"";
							tempdata[j-1]=tb;
							strinsert=strinsert+tb+"','";
						}
						
						tempdata[12]=createtime;
						model2.addRow(tempdata);
						strinsert="'"+strinsert+createtime+"'";
						dao.insert("insert into tb_FriendsList (UserSource,UserName," +
									"UserID,UserLocation,UserVerify,UserGender,UserFans," +
									"UserFriends,UserStatusNo,UserUpdateTime,UserCreateTime," +
									"StatusId,FriendsTime) values ("+ strinsert +" )");
						model1.removeRow(i);
						createitemst.setText(model2.getRowCount()+"");
					}else{
						//��¼֮ǰ�Թ�ע�û�
						notcreat++;
					}
				}	
			} catch (WeiboException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "API�ӿ��쳣�����ܷ��ʳ���Ƶ�����ޣ����Ժ�����", "��ʾ", JOptionPane.ERROR_MESSAGE);
			}
			
			JOptionPane.showMessageDialog(null, "�ɹ���ע"+createitems+"λ�û�������"+notcreat+"λ�û�ԭ���ѹ�ע��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						
		}
	}
}
