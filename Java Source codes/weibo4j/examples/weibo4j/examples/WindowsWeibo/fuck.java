package weibo4j.examples.WindowsWeibo;

import java.io.UnsupportedEncodingException;

import internalFrame.weibomonitor.AutoFilter;

public class fuck {
	public static void main(String s[]) {
		AutoFilter af=new AutoFilter();
		af.AlertEmail("Íå×ÐÂëÍ·");
		try {
			af.OperationEmail() ;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
