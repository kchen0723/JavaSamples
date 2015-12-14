package weibo4j.examples.WindowsWeibo;

import java.awt.*;      //Ϊ��ʹ�ò��ֹ�����   
import java.awt.event.*;//���������¼�   
import javax.swing.*;   //���µ�GUI���   
import javax.swing.filechooser.FileFilter;

import java.io.*;       //��д�ļ���   
  
public class filechooser   
{   
  
    private JFrame frm;   
    private JButton open;   
    private JButton read;   
    private JPanel p;   
    private File f;   
    private JFileChooser fc;   
    private int flag;   
  
   public filechooser()   
    {   
      frm=new JFrame("java");   
      open=new JButton("open");   
      read=new JButton("read");   
      p=new JPanel();   
      fc=new JFileChooser();   
  
  
      Container c=frm.getContentPane();   
      c.setLayout(new FlowLayout());   
  
      c.add(p);   
      p.add(open);   
      p.add(read);   
  
    //ע�ᰴť�¼�   
      open.addActionListener(new action());   
      read.addActionListener(new action());   
  
      frm.setSize(300,300);   
      frm.setVisible(true);   
    //����Ĭ�ϵĹرղ���   
      frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
    }   
  
  
  private void openFile()  //���ļ�   
           {   
              //���ô��ļ��Ի���ı���   
                fc.setDialogTitle("Open File");   
  
              //������ʾ���ļ��ĶԻ���   
           try{    
                       flag=fc.showOpenDialog(frm);    
                }   
           catch(HeadlessException head){    
  
                       System.out.println("Open File Dialog ERROR!");   
                }   
                 
              //�������ȷ����ť�����ø��ļ���   
              if(flag==JFileChooser.APPROVE_OPTION)   
                {   
                     //��ø��ļ�   
                       f=fc.getSelectedFile();   
                       System.out.println("open file----"+f.getName());   
                 }   
           }   
  
  private void readFile() //�����ļ�   
         {   
             String fileName;   
  
           //���ñ����ļ��Ի���ı���   
             fc.setDialogTitle("Save File");    
  
           //���ｫ��ʾ�����ļ��ĶԻ���   
        try{    
                  flag=fc.showSaveDialog(frm);   
             }   
        catch(HeadlessException he){   
                  System.out.println("Save File Dialog ERROR!");    
             }   
        fc.setFileFilter(new FileFilter() {
	        public boolean accept(File f) {
	          return f.getName().toLowerCase().endsWith(".xls")
	              || f.isDirectory();
	        }
	        public String getDescription() {
	          return "XLS Files";
	        }
	      });
           //�������ȷ����ť�����ø��ļ���   
           if(flag==JFileChooser.APPROVE_OPTION)   
             {   
                 //���������Ҫ������ļ�   
                   f=fc.getSelectedFile();   
                 //����ļ���   
                   fileName=fc.getName(f);   
                 //Ҳ����ʹ��fileName=f.getName();   
                   System.out.println(fileName);   
             }   
         }   
  
  //��ť���������ڲ���   
  class action implements ActionListener   
    {   
      public void actionPerformed(ActionEvent e)   
        {   
  
          //�ж����ĸ���Ŧ�������   
          if(e.getSource()==open)   
               openFile();   
          else  
          if(e.getSource()==read)   
               readFile();   
        }   
    }   
  
  public static void main(String[] args)   
    {   
    new filechooser();   
     }   
}