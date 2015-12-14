/**
 * 
 */
package weibo4j.examples.favorites;

import java.util.List;

import weibo4j.Status;
import weibo4j.Weibo;
import weibo4j.WeiboException;

/**
 * @author sina
 *
 */
public class DestroyFavorite {

	/**
	 * ɾ����ǰ�û��ղص�΢����Ϣ 
	 * @param args
	 * args[0]��args[1]Ϊͨ��GetToken.java��ȡ����accesstoken��accesstoken secret
	 * args[2]Ϊ�㽫Ҫɾ����statusId
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		try {
			Weibo weibo = new Weibo();
			weibo.setToken(args[0],args[1]);
			weibo.destroyFavorite(Long.parseLong(args[2]));
			List<Status> list = weibo.getFavorites();
			for(Status status : list) {
				System.out.println(status.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
