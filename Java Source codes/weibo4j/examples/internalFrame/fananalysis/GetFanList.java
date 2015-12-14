package internalFrame.fananalysis;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import statisticsModel.DataAccess;
import statisticsModel.MyTableModel;
import statisticsModel.TextComponentPopupMenu;

import weibo4j.User;
import weibo4j.Weibo;
import weibo4j.WeiboException;

public class GetFanList extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7585623886868371273L;
	String[] col1 = { "�û���","��˿��","΢����","��ע��","�û�ID","�ǳ�","����","����","??��ʡ","������","����","�Ա�","��֤","ע��ʱ��","����ʱ��"};
	String[] col2 = { "΢��ID","����ʱ��","΢������","��Դ","�û���","�û�ID","�ǳ�","����ʡ","������","�Ա�","��˿��","��ע��","΢����","��֤","����ʱ��"};
	public DefaultTableModel model1 = new DefaultTableModel();
	public JTable table1 = new JTable();
	public JLabel logo=new JLabel("����΢������ץȡ");
	public int count=0;
	public Timer timer = new Timer();
	//private boolean goon=false;
	final static JTextField useridt = new JTextField(); 	
	final static JTextField wbidt = new JTextField(); 
	final static JFormattedTextField fannot = new JFormattedTextField(); 
	final static JFormattedTextField endpaget = new JFormattedTextField(); 
	final static JFormattedTextField startpaget2 = new JFormattedTextField(); 
	final static JFormattedTextField endpaget2 = new JFormattedTextField(); 
	public String  zipname=null;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public String temp=System.getProperty("user.dir")+"\\temp\\getfanlist.xls";
	public MyTableModel tm=new MyTableModel();
	public TextComponentPopupMenu tpm=new TextComponentPopupMenu();
	
	public GetFanList() {
		super();
		setLayout(new GridBagLayout());
		setBounds(0, 0, 280, 236);
		
		//�������ݱ�
		model1=tm.CreateModel(col1);
		table1 = new JTable(model1);
		final TableRowSorter<DefaultTableModel> sorter1 =new TableRowSorter<DefaultTableModel>(model1);  
        table1.setRowSorter(sorter1);
        JScrollPane scrollPane1=new JScrollPane(table1);
        //�̶���ͷ
        table1.getTableHeader().setReorderingAllowed(false); 
           
        //��һ�в���
		JLabel userid=new JLabel("�����û�ID�����ƣ�");
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
		
		final JButton useridb=new JButton("��ѯ��˿");
		final GridBagConstraints gridBagConstraints_13 = new GridBagConstraints();
		//gridBagConstraints_13.weighty = 0;
		gridBagConstraints_13.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_13.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_13.gridwidth = 1;
		gridBagConstraints_13.gridy = 0;
		gridBagConstraints_13.gridx = 4;
		add(useridb, gridBagConstraints_13);
		useridb.addActionListener(new GetFollowers());
		
		JLabel startpage=new JLabel("΢��ҳ��:");
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
		gridBagConstraints_15.ipadx = 30;
		startpaget2.setText("1");
		add(startpaget2, gridBagConstraints_15);
		
		
		JLabel endpage=new JLabel("��:");
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
		gridBagConstraints_17.ipadx = 30;
		endpaget2.setText("2000");
		add(endpaget2, gridBagConstraints_17);
		
		
		final JButton Listexport=new JButton("�б���");
		final GridBagConstraints gridBagConstraints_1x = new GridBagConstraints();
		//gridBagConstraints_1x.weighty = 0;
		gridBagConstraints_1x.insets = new Insets(5, 20, 0, 0);
		//gridBagConstraints_1x.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_1x.gridwidth = 1;
		gridBagConstraints_1x.gridy = 0;
		gridBagConstraints_1x.gridx = 9;
		add(Listexport, gridBagConstraints_1x);
		
		final JButton delwb=tm.CreateButtonA("ȫ��ɾ��",model1,table1);
		final GridBagConstraints gridBagConstraints_18 = new GridBagConstraints();
		gridBagConstraints_18.weightx = 0;
		gridBagConstraints_18.insets = new Insets(5, 20, 0, 20);
		//gridBagConstraints_18.fill = GridBagConstraints.EAST;
		gridBagConstraints_18.anchor = GridBagConstraints.EAST;
		gridBagConstraints_18.gridwidth = 1;
		gridBagConstraints_18.gridy = 0;
		gridBagConstraints_18.gridx = 10;
		add(delwb, gridBagConstraints_18);
		
		//�ڶ��в���
		final GridBagConstraints gridBagConstraints_21 = new GridBagConstraints();
		gridBagConstraints_21.weightx = 0.8;
		gridBagConstraints_21.weighty = 1.0;
		gridBagConstraints_21.insets = new Insets(10, 10, 0, 10);
		gridBagConstraints_21.fill = GridBagConstraints.BOTH;
		gridBagConstraints_21.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_21.gridwidth = 12;
		gridBagConstraints_21.gridheight = 2;
		gridBagConstraints_21.gridy = 1;
		gridBagConstraints_21.gridx = 0;
		gridBagConstraints_21.ipadx = 35;
		gridBagConstraints_21.ipady = -180;
		add(scrollPane1, gridBagConstraints_21);
		
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
	
	public class GetFollowers implements ActionListener {
		public void actionPerformed(ActionEvent el){
			if(useridt.getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "�������û�ID����", "��ʾ", JOptionPane.ERROR_MESSAGE);
			}
			else{
				int start=Integer.parseInt(startpaget2.getText());
				int end=Integer.parseInt(endpaget2.getText());
				System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		    	try {
					Weibo weibo = new Weibo();
					weibo.setToken(Weibo.getat(),Weibo.getats());
					
					for(int cursor = start;cursor<=end;cursor=cursor+20)
					{	
					//����cursor��������ҳ
					List<User> list = weibo.getFollowersStatuses(useridt.getText(),cursor);
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
							String s14=format.format(user.getCreatedAt());
							String s15=null;
							if(user.getStatus()==null)
							{
								s15=null;	
							}
							else{
								s15=format.format(user.toString3());
							}
							Object[] str_row = {s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15};  
							model1.addRow(str_row);
							}
						}
					int fancount = table1.getModel().getRowCount();
					fannot.setText(fancount+"");
				} catch (WeiboException e) {
					e.printStackTrace();
				}
			
			DataAccess da=new DataAccess();
		    da.ExcelCreate(temp, model1, col1);
			}
		}
	}
	
}
