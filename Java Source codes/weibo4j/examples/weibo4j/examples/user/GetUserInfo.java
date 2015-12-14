/**
 * 
 */
package weibo4j.examples.user;

import java.io.UnsupportedEncodingException;

import weibo4j.User;
import weibo4j.Weibo;
import weibo4j.WeiboException;

/**
 * @author sina
 *
 */
public class GetUserInfo {

	/**
	 * 根据用户ID获取用户资料（授权用户） 
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		try {
			String screen_name="李开复";
			Weibo weibo = new Weibo();
			weibo.setToken("cb84083390367bfa5437469c438a6502","3fe919007f7528adef06e79e8f031341");
			User user = weibo.showUser(screen_name);
			System.out.println(user.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
