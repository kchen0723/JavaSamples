package login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import statisticsModel.TextComponentPopupMenu;

import mainframe.WSFrame;
import weibo4j.Weibo;
import weibo4j.WeiboException;
import weibo4j.http.AccessToken;
import weibo4j.http.RequestToken;
import weibo4j.util.BareBonesBrowserLaunch;

public class OauthLogin extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 20111020;
	private JLabel selectnametd;
	private JLabel selectname;
	private JLabel getpin;
	private JLabel username;
	private JLabel accesskey;
	private JLabel accesstoken;
	private JTextField getpint= new JTextField();
	private JTextField usernamet= new JTextField();
	private JTextField accesskeyt= new JTextField();
	private JTextField accesstokent= new JTextField();
	private JButton exit;
	private JButton login;
	private JButton getpinb;
	private JButton gettoken;
	public JComboBox jcb = new JComboBox();
	public JComboBox jcbtd = new JComboBox();
	public String path=System.getProperty("user.dir");
	public String txtname="userinfo.txt";
	public String txtnametd="channelinfo.txt";
	public String readStr ="";
	public String at=null;
	public String ats=null;
	public int marks=0;
	public RequestToken requestToken =null;
	String[] col = {"用户名","接入账号","接入密码"};
	public DefaultTableModel model = new DefaultTableModel(col, 0);
	public JTable table = new JTable(model);
	String[] colt = {"通道名","账号名称","账号密码"};
	public DefaultTableModel modelt = new DefaultTableModel(colt, 0);
	public JTable tablet = new JTable(modelt);
	public TextComponentPopupMenu tpm=new TextComponentPopupMenu();
	public static String CONSUMER_KEY = "";
	public static String CONSUMER_SECRET = "";
	
	
	public OauthLogin() {
		setTitle("登录吧");
		final JPanel panel = new OauthPanel();
		panel.setLayout(null);
		getContentPane().add(panel);
		setBounds(300, 200, panel.getWidth(),panel.getHeight());
		int y=90;
		int x=60;
		
		selectnametd = new JLabel("选择通道：");
		selectnametd.setBounds(x, y, 200, 18);
		panel.add(selectnametd);
		jcbtd.setBounds(x+70, y, 150, 18);
		panel.add(jcbtd);
		
		selectname= new JLabel("选择用户：");
		selectname.setBounds(x, y+45, 200, 18);
		panel.add(selectname);
		jcb.setBounds(x+70, y+45, 150, 18);
		panel.add(jcb);
		
		getpin= new JLabel("输入 PIN：");
		getpin.setBounds(x,y+90, 200, 18);
		panel.add(getpin);
		getpint.setBounds(x+70,y+90, 150, 18);
		panel.add(getpint);
		getpint.addMouseListener(tpm.getSharedInstance());  
		
		accesskey= new JLabel("接入账号：");
		accesskey.setBounds(x,y+100,200,18);
		//panel.add(accesskey);
		accesskeyt.setBounds(x+70,y+100,150,18);
		//panel.add(accesskeyt);
		
		accesstoken= new JLabel("接入密码：");
		accesstoken.setBounds(x,y+120,200,18);
		//panel.add(accesstoken);
		accesstokent.setBounds(x+70,y+120,150,18);
		//panel.add(accesstokent);
		
		int yb=90;
		int xb=310;
		
		getpinb = new JButton();
		getpinb.setText("获取PIN");
		getpinb.setBounds(xb, yb,100, 18);
		panel.add(getpinb);
		
		gettoken = new JButton();
		gettoken.setText("PIN登录");
		gettoken.setBounds(xb, yb+30,100, 18);
		panel.add(gettoken);
		

		
		login = new JButton();
		login.setText("直接登录");
		login.setBounds(xb, yb+60, 100, 18);
		panel.add(login);
		
		JScrollPane scrollPane=new JScrollPane(table);
	    scrollPane.setBounds(20, 40, 200, 50);
	    
	    JScrollPane scrollPanet=new JScrollPane(tablet);
	    scrollPanet.setBounds(80, 40, 200, 50);
	    		
		exit = new JButton();
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				System.exit(0);
			}
		});
		exit.setText("退出");
		exit.setBounds(xb, yb+90, 100, 18);
		panel.add(exit);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		login.addActionListener(new GetFct());
		gettoken.addActionListener(new GetAccess());
		getpinb.addActionListener(new GetPin());
		this.addWindowListener(new WindowsActive());
		jcb.addActionListener(new ItemCallback());
		jcbtd.addActionListener(new ItemCallbacktd());
	}
	
	public class GetAccess implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			AccessToken accessToken = null;
			Weibo weibo = new Weibo();
			if(getpint.getText().isEmpty())  
			  {	  
				  JOptionPane.showMessageDialog(null, "请输入pin啦！", "提示", JOptionPane.ERROR_MESSAGE);
			  }
			  else {
				  
				  while (null == accessToken) {
					  String pin = getpint.getText();
					  try{
						  accessToken = requestToken.getAccessToken(pin);
					  } catch (WeiboException te) {
			        	  if(401 == te.getStatusCode()){
			        		  System.out.println("Unable to get the access token.");
			          }else{
			        	  	  te.printStackTrace();
			          }
			        }
				  }
				  System.out.println("Access token: "+ accessToken.getToken());
				  at=accessToken.getToken();
				  System.out.println("Access token secret: "+ accessToken.getTokenSecret());
				  ats=accessToken.getTokenSecret();
				  weibo.setToken(accessToken.getToken(), accessToken.getTokenSecret());
				  Weibo.setat(at);
				  Weibo.setats(ats);
				  accesskeyt.setText(at);
				  accesstokent.setText(ats);
				  setLoginclient();
				  String user=Weibo.getclient();
				  try {
	            		String str=readTxtFile(path, txtname);
	            		System.out.println(str);
	             		writeTxtFile(user+","+at+","+ats,str);
	 				} catch (IOException e1) {
	 					// TODO Auto-generated catch block
	 					e1.printStackTrace();
	 				}
	 				String[] str={user,at,ats};
	 				model.addRow(str); 
	 				setVisible(false);
	            	new WSFrame();
			  }
		}
	}
	
	public class GetFct implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if((accesskeyt.getText().length()==0)||(accesstokent.getText().length()==0))
            {
           	 	JOptionPane.showMessageDialog(null, "请输入接入账号和接入密码！", "提示", JOptionPane.ERROR_MESSAGE);
            }
            else{
            	Weibo.setat(accesskeyt.getText());
				Weibo.setats(accesstokent.getText());
				Weibo.setkey(CONSUMER_KEY);
				Weibo.setsecret(CONSUMER_SECRET);
				//存入当前登陆用户信息
				setLoginclient();
				setVisible(false);
            	new WSFrame();  
            }
		}
	}
	public class GetPin implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			 try {
				 	System.setProperty("weibo4j.oauth.consumerKey", Weibo.setkey(CONSUMER_KEY));
			    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.setsecret(CONSUMER_SECRET));
				 	Weibo weibo = new Weibo();
		            requestToken = weibo.getOAuthRequestToken();
		            requestToken.getToken();
		            requestToken.getTokenSecret();
		            requestToken.getAuthorizationURL();
		            BareBonesBrowserLaunch.openURL(requestToken.getAuthorizationURL());
		              
		        } catch (WeiboException te) {
		            System.out.println("Failed to get timeline: " + te.getMessage());
		            System.exit( -1);
		        }
			}
	}
	
	//获取当前登录用户
	public void setLoginclient(){
		String loginclient=null;
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
    	try {
			Weibo weibo = new Weibo();
			weibo.setToken(accesskeyt.getText(),accesstokent.getText());
			loginclient=weibo.getUserTimeline().get(1).getUser().getName();
			Weibo.setclient(loginclient);
    	} catch (WeiboException e1) {
			e1.printStackTrace();
		}
	}
	
	public class FileCreat implements ActionListener{
		public void actionPerformed(ActionEvent el) {
             if((usernamet.getText().length()==0)||(accesskeyt.getText().length()==0)||(accesstokent.getText().length()==0))
             {
            	 JOptionPane.showMessageDialog(null, "请输入账号名称、接入账号和接入密码！", "提示", JOptionPane.ERROR_MESSAGE);
             }
             else{
            	 try {
            		String str=readTxtFile(path, txtname);
            		System.out.println(str);
             		writeTxtFile(usernamet.getText()+","+accesskeyt.getText()+","+accesstokent.getText(),str);
 				} catch (IOException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
 				String[] str={usernamet.getText(),accesskeyt.getText(),accesstokent.getText()};
 				model.addRow(str); 
 				JOptionPane.showMessageDialog(null, "账号保存成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
             }
		}       
	}

	public class ItemCallback implements ActionListener {
		 public void actionPerformed(ActionEvent e){
			 int row=rowcheck(table,jcb);
			 System.out.println(jcb.getSelectedItem()+"   "+row);
			 accesskeyt.setText(table.getValueAt(row, 1)+"");
			 accesstokent.setText(table.getValueAt(row, 2)+"");
		 }
	}
	
	public class ItemCallbacktd implements ActionListener {
		 public void actionPerformed(ActionEvent e){
			 int row=rowcheck(tablet,jcbtd);
			 System.out.println(jcbtd.getSelectedItem()+"   "+row);
			 CONSUMER_KEY = tablet.getValueAt(row, 1)+"";
			 CONSUMER_SECRET = tablet.getValueAt(row, 2)+"";
			 System.out.println(CONSUMER_KEY+"   "+CONSUMER_SECRET);
		 }
	}
	
	public class WindowsActive implements WindowListener{
		public void windowActivated(WindowEvent arg0) {
             // TODO Auto-generated method stub
		}
		@Override
    	public void windowClosed(WindowEvent arg0) {
             // TODO Auto-generated method stub
    	}
   		@Override
    	public void windowClosing(WindowEvent arg0) {
             // TODO Auto-generated method stub
    	}
    	@Override
    	public void windowDeactivated(WindowEvent arg0) {
             // TODO Auto-generated method stub
    	}
    	@Override
    	public void windowDeiconified(WindowEvent arg0) {
             // TODO Auto-generated method stub
    	}
    	@Override
    	public void windowIconified(WindowEvent arg0) {
             // TODO Auto-generated method stub
    	}
    	@Override
    	public void windowOpened(WindowEvent arg0) {
             // TODO Auto-generated method stub
    		CreatTxt(path, txtname);
    		String[] str={};
			int length=0;
			str=readTxtFile(path, txtname).split(",");
			while(length<str.length){
                  String s1="";
                  String s2="";
                  String s3="";
                  if(str[length]!= null)
                  {
                       s1=str[length];
                  }
                  else
                  {
                       break;
                  }
                  if(length+1<str.length)
                  {
                       s2=str[length+1];
                  }
                  else
                  {
                       break;
                  }
                  if(length+2<str.length)
                  {
                       s3=str[length+2];
                  }
                  else
                  {
                       break;
                  }
                  String[] str_row = {s1,s2,s3}; 
                  model.addRow(str_row);
                  length=length+3;
			}
			for(int i=0;i<table.getRowCount();i++){
			jcb.addItem(table.getValueAt(i,0)); 
		}
			
			CreatTxt(path, txtnametd);
    		String[] strtd={};
			int lengthtd=0;
			strtd=readTxtFile(path, txtnametd).split(",");
			while(lengthtd<strtd.length){
                  String s1="";
                  String s2="";
                  String s3="";
                  if(strtd[lengthtd]!= null)
                  {
                       s1=strtd[lengthtd];
                  }
                  else
                  {
                       break;
                  }
                  if(lengthtd+1<strtd.length)
                  {
                       s2=strtd[lengthtd+1];
                  }
                  else
                  {
                       break;
                  }
                  if(lengthtd+2<strtd.length)
                  {
                       s3=strtd[lengthtd+2];
                  }
                  else
                  {
                       break;
                  }
                  String[] str_row = {s1,s2,s3}; 
                  modelt.addRow(str_row);
                  lengthtd=lengthtd+3;
			}
			for(int i=0;i<tablet.getRowCount();i++){
			jcbtd.addItem(tablet.getValueAt(i,0)); 
			}
		}       
	}

	public void CreatTxt(String path, String txtname) {
		File txt = new File(path +"/"+txtname);
		if (!txt.exists()) {
			try {
					txt.createNewFile();
					System.out.println(path);
					System.out.println(txtname+"文本文件创建成功");
			} catch (Exception e) {
				e.printStackTrace();
			}
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

	public void writeTxtFile(String newStr,String readStr) throws IOException{
			//先读取原有文件内容，然后进行写入操作
		String filein = newStr+ "," +readStr;
		RandomAccessFile mm = null;
			try {
				mm = new RandomAccessFile(path +"/"+txtname, "rw");
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
	
	public int rowcheck(JTable table,JComboBox jcb){
		int row=0;
		for(int i=0;i<table.getRowCount();i++){
			if(jcb.getSelectedItem().equals(table.getValueAt(i, 0)))
			{
				row=i;
				break;
			}
		}
		return row;
	}
	
}

