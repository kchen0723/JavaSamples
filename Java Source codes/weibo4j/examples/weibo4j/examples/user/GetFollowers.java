/**
 * 
 */
package weibo4j.examples.user;

import java.util.List;
import weibo4j.User;
import weibo4j.Weibo;
import weibo4j.WeiboException;

/**
 * @author sina
 *
 */
public class GetFollowers {

	/**
	 * 返回用户关注对象列表，并返回最新微博文章。
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		try {
			Weibo weibo = new Weibo();
			weibo.setToken("cb84083390367bfa5437469c438a6502","3fe919007f7528adef06e79e8f031341");
			int cursor = 20;
			//采用cursor参数处理翻页
			List<User> list = weibo.getFollowersStatuses("达巴赖马",cursor);
			for(User user : list) {
				System.out.println(user.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
