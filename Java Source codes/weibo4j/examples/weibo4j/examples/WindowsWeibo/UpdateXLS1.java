package weibo4j.examples.WindowsWeibo;
import java.io.*; 
import jxl.*; 
import jxl.write.*; 

public class UpdateXLS1 
{ 
public static void main(String args[]) 
{ 
try 
{ 
//Excel����ļ� 
Workbook wb=Workbook.getWorkbook(new File("test.xls")); 

//��һ���ļ��ĸ���������ָ������д�ص�ԭ�ļ� 
WritableWorkbook book=Workbook.createWorkbook(new File("test.xls"),wb); 
WritableSheet sheet = book.getSheet(0); 
jxl.write.Number number = new jxl.write.Number(2,3,24); 
sheet.addCell(number); 
jxl.write.Label label= new jxl.write.Label(2,11,"fsdfsf���Զ�ȡExcel 95, 97, 2000�ļ����Զ���дExcel 97�����Ժ�汾�ĵĹ�ʽ�������ҷ��ֺ�����bug������Excel 97��ʽ�ĵ��ӱ�� "); 
sheet.addCell(label); 
jxl.write.Label label4= new jxl.write.Label(5,4,"sdfsd���Զ�ȡExcel 95, 97, 2000�ļ����Զ���дExcel 97�����Ժ�汾�ĵĹ�ʽ�������ҷ��ֺ�����bug������Excel 97��ʽ�ĵ��ӱ�� "); 
sheet.addCell(label4); 
jxl.write.Label label2= new jxl.write.Label(1,11,"sfsd2005.11.5-2005.11.25"); 
sheet.addCell(label2); 
jxl.write.Label label3= new jxl.write.Label(1,12,"sdfsdf2005.11.15-2005.11.25"); 
sheet.addCell(label3);
jxl.write.Label label5= new jxl.write.Label(3,1,"fsdfsf suck 2000�ļ����Զ���дExcel 97�����Ժ�汾�ĵĹ�ʽ�������ҷ��ֺ�����bug������Excel 97��ʽ�ĵ��ӱ�� "); 
sheet.addCell(label5); 
//���һ�������� 
// WritableSheet sheet=book.createSheet("�ڶ�ҳ",1); 

//sheet.addCell(new Label(0,0,"�ڶ�ҳ�Ĳ�������")); 

book.write(); 
book.close(); 
}catch(Exception e) 
{ 
System.out.println(e); 
} 
} 
}