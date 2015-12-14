/**
 * 
 */
package weibo4j.examples.ids;

import weibo4j.Weibo;
import weibo4j.WeiboException;

/**
 * @author sina
 *
 */
public class GetFollowersIDs {

	/**
	 * ��ȡ�û���˿����uid�б� 
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		try {
			Weibo weibo = new Weibo();
			weibo.setToken("cb84083390367bfa5437469c438a6502","3fe919007f7528adef06e79e8f031341");
			//cursor����ҳ
			int cursor=5;
			//args[2]:��ע�û���id
			long[] ids = weibo.getFollowersIDSByUserId("2217648053", cursor).getIDs();
			for(long id : ids) {
				System.out.println(id);
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
