package weibo4j.examples.tags;

import java.io.File;
import java.util.List;

import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;

import weibo4j.Paging;
import weibo4j.Tag;
import weibo4j.Weibo;

public class GetTags{
	public static String zipname="d:\\My Documents\\java\\wspace-10-20\\temp\\getstatuslist.xls";
	/**
	 * 获取tags；
	 * @param args
	 * args[0]和args[1]为通过GetToken.java获取到的accesstoken和accesstoken secret
	 * args[2]为获取用户的Id
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		try  
		{  
		try {
			Weibo weibo = new Weibo();
			weibo.setToken("cb84083390367bfa5437469c438a6502","3fe919007f7528adef06e79e8f031341");
			Paging paging = new Paging();
			paging.setCount(20);
			paging.setPage(1);
			Workbook book= Workbook.getWorkbook(new File(zipname));  
			//获得第一个工作表对??  
			Sheet sheet=book.getSheet(0);
			int rows=sheet.getRows();
			int collumns=sheet.getColumns();
			//得到第一列第一行的单元格
			for(int j=1;j<rows;j++)
			{
				String result=sheet.getCell(5,j).getContents();
				List<Tag> gettags=weibo.getTags(result, paging);
				for(Tag status : gettags) {
					System.out.println( status.toString());
				}
			}
			book.close();
			}catch(Exception e)  
			{  
			System.out.println(e);  
			} 
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}