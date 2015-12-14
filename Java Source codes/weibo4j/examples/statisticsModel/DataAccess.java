package statisticsModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class DataAccess {
	public SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public List<String> GetGenderVerified (String filepath,int sheetno,int column){
		List<String> arrayListtemp = new ArrayList<String>();
		try  
		{  
			Workbook book= Workbook.getWorkbook(new File(filepath));  
			Sheet sheet=book.getSheet(sheetno);
			int rows=sheet.getRows();
			for(int j=1;j<rows;j++)
			{
				String result=sheet.getCell(column,j).getContents();  
				if(result!=""){
					arrayListtemp.add(result);
				}
			}
			book.close();
		}catch(Exception e1)  
		{  
			System.out.println(e1);  
		} 
			return arrayListtemp;
	}

	public void ExcelWrite(int excelcolumns,int[] nocolumns,int sheetno,String path,DefaultTableModel model ){
		try  
		{  
		Object[] rt=new Object[excelcolumns];	
		Workbook book= Workbook.getWorkbook(new File(path));  
		//获得第一个工作表对象  
		Sheet sheet=book.getSheet(sheetno);
		int rows=sheet.getRows();
		int collumns=sheet.getColumns();
		//得到第一列第一行的单元格
		for(int j=1;j<rows;j++)
		{
			for(int i=0;i<collumns;i++)
				{	
				if (check(i,nocolumns)) 
					{ 
					int result=Integer.valueOf(sheet.getCell(i,j).getContents());  
					rt[i]=result;
					}
				else{
					Object result=sheet.getCell(i,j).getContents();  
					rt[i]=result;
					}
				}
			model.addRow(rt);
		}
		book.close();
		}catch(Exception e1)  
		{  
			System.out.println(e1);  
		}
	}
	
	public void ExcelRowWrite(String check,int excelcolumns,int[] nocolumns,int sheetno,String path,DefaultTableModel model ){
		try  
		{  
		Object[] rt=new Object[excelcolumns];	
		Workbook book= Workbook.getWorkbook(new File(path));  
		//获得第一个工作表对象  
		Sheet sheet=book.getSheet(sheetno);
		int rows=sheet.getRows();
		int collumns=sheet.getColumns();
		//得到第一列第一行的单元格
		for(int j=1;j<rows;j++)
		{
			if(sheet.getCell(1,j).getContents().equals(check)){
				for(int i=0;i<collumns;i++)
				{	
				if (check(i,nocolumns)) 
					{ 
					int result=Integer.valueOf(sheet.getCell(i,j).getContents());  
					rt[i]=result;
					}
				else{
					Object result=sheet.getCell(i,j).getContents();  
					rt[i]=result;
					}
				}
				rt[excelcolumns-1]="no";
				model.addRow(rt);
			}
		}
		book.close();
		}catch(Exception e1)  
		{  
			System.out.println(e1);  
		}
	}
	
	//失败！excel不可以代替数据库
	public void ExcelAddRows(int sheetno,String path,DefaultTableModel model,JTable table ){
		try  
		{  
		//Excel获得文件 
		Workbook wb=Workbook.getWorkbook(new File(path)); 

		//打开一个文件的副本，并且指定数据写回到原文件 
		WritableWorkbook book=Workbook.createWorkbook(new File(path),wb); 
		//获得第一个工作表对象  
		WritableSheet sheet = book.getSheet(0); 	
		
		int tablerows=model.getRowCount();
		int tablecolumns=model.getColumnCount();
		int excelrows=sheet.getRows();
		
		//得到第一列第一行的单元格
		for(int i=0;i<tablerows;i++){
			for(int j=0;j<excelrows;j++){
				int items = sheet.getRows()+1;
				if(sheet.getCell(1, j).equals(table.getValueAt(i, 1))){
					System.out.println("数据已存在");
				}else{
					for(int c=0;c<tablecolumns;c++){
						jxl.write.Label label= new jxl.write.Label(c,items,table.getValueAt(i, c)+"");				
						sheet.addCell(label); 
					}
				}
			}
		}
		book.write(); 
		book.close();
		}catch(Exception e1)  
		{  
			System.out.println(e1);  
		}
	}
	
		public void ExcelCreate(String pathec,DefaultTableModel model,String[] str){
			File file = new File(pathec);
			try {
				WritableWorkbook book = Workbook.createWorkbook(file);
				WritableSheet sheet = book.createSheet("temp",1);
 	           	for(int j=0;j<model.getColumnCount();j++)
 	           	{
 	        	   Label lab=new Label(j,0,str[j]);
 	        	   try {
 		                  sheet.addCell(lab);
 		              } catch (RowsExceededException e) {
 		                  e.printStackTrace();
 		              } catch (WriteException e) {
 		                  e.printStackTrace();
 		              }
 	           	}
 	           	for(int i=0;i<model.getRowCount();i++)
 	           	{ 
 					for(int j=0;j<model.getColumnCount();j++)
 					{
 						Label lab=new Label(j,i+1,model.getValueAt(i, j)+"");
 						try {
 							sheet.addCell(lab);
 						} catch (RowsExceededException e) {
 							// TODO Auto-generated catch block
 							e.printStackTrace();
 						} catch (WriteException e) {
 							// TODO Auto-generated catch block
 							e.printStackTrace();
 						}
 					}
 				}
 				
 				book.write();
 	            try {
 					book.close();
 				} catch (WriteException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}  
 				
 			} catch (IOException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
		}
		
		public void ExcelDataGet(String filepath,int sheetno,DefaultTableModel model,JTable table){
			try  
			{  
				Workbook book= Workbook.getWorkbook(new File(filepath));  
				Sheet sheet=book.getSheet(sheetno);
				int rows=sheet.getRows();
				int columns=sheet.getColumns();
				if(rows>1){
					for(int j=1;j<rows;j++)
					{
						List<String> array = new ArrayList<String>();
						for(int i=0;i<columns;i++){
							String result=sheet.getCell(i,j).getContents(); 
							array.add(result);
						}
						String[] s = array.toArray(new String[array.size()]);
						model.addRow(s);
					}
				}
				book.close();
			}catch(Exception e1)  
			{  
				System.out.println(e1);  
			}
		}
		
		public Long[] GetNumber(String filepath,int sheetno,int column){
			List<Long> arrayListtemp = new ArrayList<Long>();
			
			try  
			{  
				Workbook book= Workbook.getWorkbook(new File(filepath));  
				Sheet sheet=book.getSheet(sheetno);
				int rows=sheet.getRows();
				for(int j=1;j<rows;j++)
				{
					Long result=Long.valueOf(sheet.getCell(column,j).getContents()); 
					arrayListtemp.add(result);
				}
				book.close();
			}catch(Exception e1)  
			{  
				System.out.println(e1);  
			}
			
			Long[] back = arrayListtemp.toArray(new Long[arrayListtemp.size()]);
			return back; 
		}
		
		public List<String> GetProvince (String filepath,int sheetno,int column){
			List<String> arrayListtemp = new ArrayList<String>();
			try  
			{  
				Workbook book= Workbook.getWorkbook(new File(filepath));  
				Sheet sheet=book.getSheet(sheetno);
				int rows=sheet.getRows();
				for(int j=1;j<rows;j++)
				{
					String result=sheet.getCell(column,j).getContents();  
					if(result!=""){
						String[] ss1 =result.split(" ");
						arrayListtemp.add(ss1[0]);
					}
				}
				book.close();
			}catch(Exception e1)  
			{  
				System.out.println(e1);  
			} 
				return arrayListtemp;
		}
		
		public List<String> GetSource(String filepath,int sheetno,int column){
			List<String> arrayListtemp = new ArrayList<String>();
			try  
			{  
				Workbook book= Workbook.getWorkbook(new File(filepath));  
				Sheet sheet=book.getSheet(sheetno);
				int rows=sheet.getRows();
				for(int j=1;j<rows;j++)
				{
					String result=sheet.getCell(column,j).getContents();  
					if(result!=""){
						String[] s1 =result.split(">");
						String[] s2 = s1[1].split("<"); 
						arrayListtemp.add(s2[0]);
					}
				}
				book.close();
			}catch(Exception e1){  
				System.out.println(e1);  
			} 
				return arrayListtemp;
		}
		
		public List<String> GetMention (String filepath,int sheetno,int column){
			List<String> arrayListtemp = new ArrayList<String>();
			try  
			{  
				Workbook book= Workbook.getWorkbook(new File(filepath));  
				Sheet sheet=book.getSheet(sheetno);
				int rows=sheet.getRows();
				for(int j=1;j<rows;j++)
				{
					String result=sheet.getCell(column,j).getContents();  
					if(result!=""){
						String[] s1 =result.split("@");
						for(int i=1;i<s1.length;i++){
							String[] s2 = s1[i].split(":");
							String[] s3 = s2[0].split("：");
							String[] s4 = s3[0].split(" ");
							arrayListtemp.add(s4[0]);
						}
					}
				}
				book.close();
			}catch(Exception e1){  
				System.out.println(e1);  
			} 
				return arrayListtemp;
		}
		
		public Long[] GetFanYear (String filepath,int sheetno,int column){
			List<Long> arrayListtemp = new ArrayList<Long>();
			try  
			{  
				Workbook book= Workbook.getWorkbook(new File(filepath));  
				Sheet sheet=book.getSheet(sheetno);
				int rows=sheet.getRows();
				for(int j=1;j<rows;j++)
				{
					String result=sheet.getCell(column,j).getContents();  
					Date date = format.parse(result);
					Date today =new Date();
					Long years=((today.getTime() - date.getTime())/(1000*3600*24));
					arrayListtemp.add(years);
				}
				book.close();
			}catch(Exception e1){  
				System.out.println(e1);  
			} 
			Long[] back = arrayListtemp.toArray(new Long[arrayListtemp.size()]);
			return back;
		}
		
		public Long[] GetFanStatusFrequency (String filepath,int sheetno,int newstatus,int createat,int statusno){
			List<Long> arrayListtemp = new ArrayList<Long>();
			try  
			{  
				Workbook book= Workbook.getWorkbook(new File(filepath));  
				Sheet sheet=book.getSheet(sheetno);
				int rows=sheet.getRows();
				for(int j=1;j<rows;j++)
				{
					Long frequency= null;
					String ns=sheet.getCell(newstatus,j).getContents();  
					int no=Integer.parseInt(sheet.getCell(statusno,j).getContents());  
					String ca=sheet.getCell(createat,j).getContents();
					//if(ns.equals("null")||no==0){
					if(no==0){
						frequency=Long.parseLong("0");
					}else{
						//1.0使用下列语句，2.0不支持获取ns
						//Date datens = format.parse(ns);
						
						Date dateca = format.parse(ca);
						Date datens =new Date();
						long year=(datens.getTime() - dateca.getTime())/(1000*3600*24);
						if(year==0){
							frequency=Long.parseLong("0");
						}else{
							frequency=no/((datens.getTime() - dateca.getTime())/(1000*3600*24));
						}
					}
					arrayListtemp.add(frequency);
				}
				book.close();
			}catch(Exception e1){  
				System.out.println(e1);  
			} 
			Long[] back = arrayListtemp.toArray(new Long[arrayListtemp.size()]);
			return back;
		}
		
		public List<String> GetCommonInfo(String filepath,int sheetno,int column){
			List<String> arrayListtemp = new ArrayList<String>();
			try  
			{  
				Workbook book= Workbook.getWorkbook(new File(filepath));  
				Sheet sheet=book.getSheet(sheetno);
				int rows=sheet.getRows();
				for(int j=1;j<rows;j++)
				{
					String result=sheet.getCell(column,j).getContents();  
					if(result!=""){
						String[] s=result.split(" ");
						arrayListtemp.add(s[0]);
					}
				}
				book.close();
			}catch(Exception e1){  
				System.out.println(e1);  
			} 
				return arrayListtemp;
		}
		
		public List<Integer> GetCommonInfono(String filepath,int sheetno,int column){
			List<Integer> arrayListtemp = new ArrayList<Integer>();
			try  
			{  
				Workbook book= Workbook.getWorkbook(new File(filepath));  
				Sheet sheet=book.getSheet(sheetno);
				int rows=sheet.getRows();
				for(int j=1;j<rows;j++)
				{
					String result=sheet.getCell(column,j).getContents();  
					if(result!=""){
						arrayListtemp.add(Integer.parseInt(result));
					}
				}
				book.close();
			}catch(Exception e1){  
				System.out.println(e1);  
			} 
				return arrayListtemp;
		}
		
		public int[] GetTimeDistribution(String filepath,int sheetno,int column){
			int[] arrayListtemp = new int[24];
			try  
			{  
				Workbook book= Workbook.getWorkbook(new File(filepath));  
				Sheet sheet=book.getSheet(sheetno);
				int rows=sheet.getRows();
				SimpleStatistics ss=new SimpleStatistics();
				for(int j=1;j<rows;j++)
				{
					String result=sheet.getCell(column,j).getContents();  
					if(result!=""){
						ss.TimeClassification(result, arrayListtemp);
					}
				}
				book.close();
			}catch(Exception e1){  
				System.out.println(e1);  
			} 
			int[] back=new int[24]; 
			back[0]=arrayListtemp[0];
			for(int i=1;i<24;i++){
				back[i]=arrayListtemp[i]-arrayListtemp[i-1];
			}
				return back;
		}
		
		public void GetDataToTable(DefaultTableModel model,String filepath,int sheetno,int[] columns){
			try  
			{  
				Workbook book= Workbook.getWorkbook(new File(filepath));  
				Sheet sheet=book.getSheet(sheetno);
				int rows=sheet.getRows();
				for(int j=1;j<rows;j++)
				{
					String str1=sheet.getCell(columns[0],j).getContents();  
					int str2=Integer.parseInt(sheet.getCell(columns[1],j).getContents());
					Object[] s2 ={str1,str2};
					model.addRow(s2);
				}
				book.close();
			}catch(Exception e1){  
				System.out.println(e1);  
			} 
		}
		
		public boolean check(int column,int[] columns){
			boolean back=false;
			for(int i=0;i<columns.length;i++){
				if(column==columns[i]){
					back=true;
					break;
				}
			}
			return back;
		}

		public int[] GetOriginalRatio(String filepath,int sheetno,int column){
			int[] a = new int[2];
			a[0]=0;
			try  
			{  
				Workbook book= Workbook.getWorkbook(new File(filepath));  
				Sheet sheet=book.getSheet(sheetno);
				int row=sheet.getRows();
				
				for(int j=1;j<row;j++)
				{
					String result=sheet.getCell(column,j).getContents();  
					if(result!=""){
						a[0]=a[0]+Integer.parseInt(result);
					}
				}
				a[1]=row-a[0]-1;
				book.close();
			}catch(Exception e1){  
				System.out.println(e1);  
			} 
			return a;
		}
		
		
}
