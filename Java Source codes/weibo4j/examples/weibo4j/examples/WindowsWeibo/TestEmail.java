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
			
			//�����Ự
			Session session = Session.getInstance(p);
			Message msg = new MimeMessage(session); //������Ϣ
			msg.setFrom(new InternetAddress("hyb19852004@163.com")); //������
			msg.setRecipient(Message.RecipientType.TO,
			new InternetAddress("357033112@qq.com")); //�ռ���
			
			msg.setSentDate(new Date()); // ��������
			msg.setSubject("��ϡ��"); // ����
			msg.setText("�������"); //����
			// �ʼ�������������֤
			Transport tran = session.getTransport("smtp");
			tran.connect("smtp.163.com", "hyb19852004", "87895841");
			// bluebit_cn���û�����xiaohao������
			tran.sendMessage(msg, msg.getAllRecipients()); // ����
			System.out.println("�ʼ����ͳɹ�");

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}



