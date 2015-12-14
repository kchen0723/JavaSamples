package weibo4j.examples.WindowsWeibo;

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

//http://topic.csdn.net/u/20070822/19/e0eec440-0524-4bb5-90d1-e5ff9fe19645.html
//http://www.diybl.com/course/3_program/java/javashl/2008222/100426.html
//http://www.blogjava.net/supercrsky/articles/170119.html

public class TestEmail {
	public void EmailSend() {
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
			msg.setRecipient(Message.RecipientType.TO,
			new InternetAddress("357033112@qq.com")); //收件人
			
			msg.setSentDate(new Date()); // 发送日期
			msg.setSubject("答话稀有"); // 主题
			msg.setText("快点下在"); //内容
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



