package statisticsModel;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

//http://topic.csdn.net/u/20070822/19/e0eec440-0524-4bb5-90d1-e5ff9fe19645.html
//http://www.diybl.com/course/3_program/java/javashl/2008222/100426.html
//http://www.blogjava.net/supercrsky/articles/170119.html

public class SendEmail {
		public void EmailSend(String[] receiver,String title,String content) {
			try {
				
				Properties p = new Properties();
				p.put("mail.transport.protocol","smtp");
				p.put("mail.smtp.auth","true");
				p.put("mail.transport.protocol","smtp");
				p.put("mail.smtp.host","smtp.163.com");
				p.put("mail.smtp.port","25");
				
				//建立会话
				Session session = Session.getInstance(p);
				Message msg = new MimeMessage(session); //建立信息
				msg.setFrom(new InternetAddress("hyb19852004@163.com")); //发件人
				//实现邮件中加入超链接
				Multipart mp1 = new MimeMultipart();
				BodyPart bp1 = new MimeBodyPart();

				int num=receiver.length;
				Address[] addresses=new InternetAddress[num];
				for(int i=0;i<num;i++){
					addresses[i]=new InternetAddress(receiver[i]);
				}
				msg.setRecipients(Message.RecipientType.TO,addresses); //收件人
				msg.setHeader("content-type","text/html");
				msg.setSentDate(new Date()); // 发送日期
				msg.setSubject(title); // 主题
				bp1.setContent(content, "text/html; charset=utf-8");//设置邮件内容及编码
				mp1.addBodyPart(bp1);
				msg.setContent(mp1);

				
				//msg.setText(content); //内容
				// 邮件服务器进行验证
				Transport tran = session.getTransport("smtp");
				tran.connect("smtp.163.com", "hyb19852004", "87895841");
				// bluebit_cn是用户名，xiaohao是密码
				tran.sendMessage(msg, msg.getAllRecipients()); // 发送
				System.out.println("邮件发送成功");

			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
