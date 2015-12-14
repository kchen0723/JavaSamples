package weibo4j.examples.WindowsWeibo;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import weibo4j.Weibo;
import weibo4j.WeiboException;
import weibo4j.http.AccessToken;
import weibo4j.http.RequestToken;
import weibo4j.util.BareBonesBrowserLaunch;

public class WindowsWeibo extends JFrame {
/**
	 * 
	 */
private static final long serialVersionUID = 20110923;
public static String CONSUMER_KEY ;
public static String CONSUMER_SECRET ;
public JTabbedPane tp1 = new JTabbedPane(JTabbedPane.TOP);
public JPanel jf1=new JPanel(); 
public StatusDataAccess jf2=new StatusDataAccess();
public FanInfoAccess jf3=new FanInfoAccess(); 
public RelationshipAnalysis jf4=new RelationshipAnalysis(); 
public BatchDestroyFriendship jf5=new BatchDestroyFriendship(); 
public JLabel logo=new JLabel("新浪微博数据抓取");
final static JTextField keyt = new JTextField("608406723"); 
final static JTextField set = new JTextField("fc036f076303f732be8e9cead6823ac8"); 
final static JTextField useridt = new JTextField(); 	
final static JTextField wbidt = new JTextField(); 
public String at=null;
public String ats=null;
final static JTextField usernamet = new JTextField();        
final static JTextField accesskeyt = new JTextField();
final static JTextField accesskeyset = new JTextField();
public String path=System.getProperty("user.dir");
public String txtname="userinfo.txt";
public String readStr ="";
public int marks=0;
public RequestToken requestToken =null;
String[] col = {"用户名","接入账号","接入密码"};
public DefaultTableModel model = new DefaultTableModel(col, 0);
public JTable table = new JTable(model);

public WindowsWeibo(){
	this.setTitle("苍山有井独自空");
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	this.setSize(new Dimension(900,700));
	this.setLayout(null);
	this.setResizable(false);
	jf1.setLayout(null);
	
	logo.setBounds(10, 5, 120, 30);
	tp1.setBounds(5,40,880,620);
	this.add(logo);
	this.add(tp1);
	
	this.add(tp1);
	tp1.addTab("OAUTH认证页面", jf1);
	tp1.addTab("数据获取页面", jf2);
	tp1.addTab("粉丝信息页面", jf3);
	tp1.addTab("互动分析页面", jf4);
	tp1.addTab("关注筛选页面", jf5);
	JLabel key=new JLabel("输入KEY");
	key.setBounds(40, 40, 80, 20);
	jf1.add(key);

	keyt.setBounds(120, 40, 200, 20);
	jf1.add(keyt);
	JLabel se=new JLabel("输入SECRET");
	se.setBounds(40, 70, 80, 20);
	jf1.add(se);
	set.setBounds(120, 70, 200, 20);
	jf1.add(set);
	
	JLabel pin=new JLabel("输入PIN");
	pin.setBounds(40, 100, 80, 20);
	jf1.add(pin);
		
	final JTextField pint = new JTextField(); 	
	pint.setBounds(120, 100, 200, 20);
	jf1.add(pint);
			
	final JButton app=new JButton("调用SDK");
	app.setBounds(360, 90, 110, 20);
	jf1.add(app);
			
	final JButton api=new JButton("获取PIN码");
	api.setBounds(360, 40, 110, 20);
	jf1.add(api);
		
	//final JButton tabx=new JButton("切换");
	//tabx.setBounds(60,200, 100, 20);
	
	JLabel username=new JLabel("账号名称");
    username.setBounds(40, 130, 80, 20);
    jf1.add(username);

    usernamet.setBounds(120, 130, 200, 20);
    jf1.add(usernamet);

    JLabel accesskey=new JLabel("接入账号");
    accesskey.setBounds(40, 160, 80, 20);
    jf1.add(accesskey);

    accesskeyt.setBounds(120, 160, 200, 20);
    jf1.add(accesskeyt);

    JLabel accesskeyse=new JLabel("接入密码");
    accesskeyse.setBounds(40, 190, 80, 20);
    jf1.add(accesskeyse);
    
    accesskeyset.setBounds(120, 190, 200, 20);
    jf1.add(accesskeyset);

    final JButton filecreat=new JButton("写入账号");
    filecreat.setBounds(360, 140, 110, 20);
    jf1.add(filecreat);

    JScrollPane scrollPane=new JScrollPane(table);
    scrollPane.setBounds(520, 40, 330, 170);
    jf1.add(scrollPane);

	
	//tabx.addActionListener(new ActionListener() {
	//	public void actionPerformed(ActionEvent e) {
			
	//	}	
	//});
	
	api.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			 try {
				 	CONSUMER_KEY=keyt.getText();
				 	CONSUMER_SECRET=set.getText();
					System.setProperty("weibo4j.oauth.consumerKey", Weibo.setkey(CONSUMER_KEY));
			    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.setsecret(CONSUMER_SECRET));
				 	Weibo weibo = new Weibo();
		            // set callback url, desktop app please set to null
		            // http://callback_url?oauth_token=xxx&oauth_verifier=xxx
		            requestToken = weibo.getOAuthRequestToken();

		            requestToken.getToken();
		            requestToken.getTokenSecret();
		            requestToken.getAuthorizationURL();
		            BareBonesBrowserLaunch.openURL(requestToken.getAuthorizationURL());
		              
		           /* weibo.setToken(accessToken.getToken(), accessToken.getTokenSecret());

					Status status = weibo.updateStatus("test message6 ");
					System.out.println("Successfully updated the status to ["
							+ status.getText() + "].");

		            try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
		            
		        } catch (WeiboException te) {
		            System.out.println("Failed to get timeline: " + te.getMessage());
		            System.exit( -1);
		        }
			}
	});
		
	app.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			AccessToken accessToken = null;
			Weibo weibo = new Weibo();
			if(pint.getText().isEmpty())  
			  {	  
				  JOptionPane.showMessageDialog(null, "忘输入pin啦！", "提示", JOptionPane.ERROR_MESSAGE);
			  }
			  else{
				  //tp1.setSelectedComponent(jf2);
				  while (null == accessToken) {
					  String pin = pint.getText();
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
				  accesskeyset.setText(ats);
				  }
		  }
	});
	filecreat.addActionListener(new FileCreat());
    this.addWindowListener(new WindowsActive());
    table.addMouseListener(new TableListeners());
    tp1.addChangeListener(new TabbedPaneFrame());
	}	
	
	public class FileCreat implements ActionListener{
		public void actionPerformed(ActionEvent el) {
             if((usernamet.getText().length()==0)||(accesskeyt.getText().length()==0)||(accesskeyset.getText().length()==0))
             {
            	 JOptionPane.showMessageDialog(null, "请输入账号名称、接入账号和接入密码！", "提示", JOptionPane.ERROR_MESSAGE);
             }
             else{
            	 try {
             		writeTxtFile(usernamet.getText()+","+accesskeyt.getText()+","+accesskeyset.getText());
 				} catch (IOException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
 				String[] str={usernamet.getText(),accesskeyt.getText(),accesskeyset.getText()};
 				model.addRow(str); 
             }
		}       
	}

	public class FileInput implements ActionListener{
		public void actionPerformed(ActionEvent el) {
             readTxtFile(path, txtname);
             try {
                      writeTxtFile("A"+marks);
                      marks++;
             } catch (IOException e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
             }
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
                  String[] str_row = {s1, s2,s3}; 
                  model.addRow(str_row);
                  length=length+3;
         }
    	}       
	}

	public class TableListeners implements MouseListener {
		public void mouseClicked(MouseEvent e){
			
		}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mousePressed(MouseEvent arg0) {
			int indexs =table.getSelectedRow();
			usernamet.setText(table.getValueAt(indexs, 0)+"");
			accesskeyt.setText(table.getValueAt(indexs, 1)+"");
			accesskeyset.setText(table.getValueAt(indexs, 2)+"");
			Weibo.setat(accesskeyt.getText());
			Weibo.setats(accesskeyset.getText());
		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}
	}
	
	public class TabbedPaneFrame implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			if(accesskeyt.getText().isEmpty()||accesskeyt.getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "请输入接入账号和接入密码！", "提示", JOptionPane.ERROR_MESSAGE);
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

	public void writeTxtFile(String newStr) throws IOException{
			//先读取原有文件内容，然后进行写入操作
		String filein = newStr+ "," +readStr;
		RandomAccessFile mm = null;
			try {
				mm = new RandomAccessFile(path +"/"+txtname, "rw");
				mm.writeBytes(filein);
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
	
	public static void main(String[] args){
		WindowsWeibo b=new WindowsWeibo();
		b.setVisible(true);
		System.out.println(System.getProperty("user.dir"));
   }
}   



