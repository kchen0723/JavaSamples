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
	 * ��ȡtags��
	 * @param args
	 * args[0]��args[1]Ϊͨ��GetToken.java��ȡ����accesstoken��accesstoken secret
	 * args[2]Ϊ��ȡ�û���Id
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
			//��õ�һ���������??  
			Sheet sheet=book.getSheet(0);
			int rows=sheet.getRows();
			int collumns=sheet.getColumns();
			//�õ���һ�е�һ�еĵ�Ԫ��
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