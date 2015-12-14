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
//Excel获得文件 
Workbook wb=Workbook.getWorkbook(new File("test.xls")); 

//打开一个文件的副本，并且指定数据写回到原文件 
WritableWorkbook book=Workbook.createWorkbook(new File("test.xls"),wb); 
WritableSheet sheet = book.getSheet(0); 
jxl.write.Number number = new jxl.write.Number(2,3,24); 
sheet.addCell(number); 
jxl.write.Label label= new jxl.write.Label(2,11,"fsdfsf可以读取Excel 95, 97, 2000文件可以读或写Excel 97及其以后版本的的公式（不过我发现好像有bug）生成Excel 97格式的电子表格 "); 
sheet.addCell(label); 
jxl.write.Label label4= new jxl.write.Label(5,4,"sdfsd可以读取Excel 95, 97, 2000文件可以读或写Excel 97及其以后版本的的公式（不过我发现好像有bug）生成Excel 97格式的电子表格 "); 
sheet.addCell(label4); 
jxl.write.Label label2= new jxl.write.Label(1,11,"sfsd2005.11.5-2005.11.25"); 
sheet.addCell(label2); 
jxl.write.Label label3= new jxl.write.Label(1,12,"sdfsdf2005.11.15-2005.11.25"); 
sheet.addCell(label3);
jxl.write.Label label5= new jxl.write.Label(3,1,"fsdfsf suck 2000文件可以读或写Excel 97及其以后版本的的公式（不过我发现好像有bug）生成Excel 97格式的电子表格 "); 
sheet.addCell(label5); 
//添加一个工作表 
// WritableSheet sheet=book.createSheet("第二页",1); 

//sheet.addCell(new Label(0,0,"第二页的测试数据")); 

book.write(); 
book.close(); 
}catch(Exception e) 
{ 
System.out.println(e); 
} 
} 
}