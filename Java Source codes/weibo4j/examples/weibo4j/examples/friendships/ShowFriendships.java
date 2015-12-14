/**
 * 
 */
package weibo4j.examples.friendships;

import weibo4j.Weibo;
import weibo4j.org.json.JSONObject;

/**
 * @author sina
 *
 */
public class ShowFriendships {

	/**
	 * ���������û���ϵ����ϸ��� 
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		try {
			//�Լ���ù�ע����Ĺ�ϵ
			Weibo weibo = new Weibo();
			weibo.setToken("cb84083390367bfa5437469c438a6502","3fe919007f7528adef06e79e8f031341");
			JSONObject object = weibo.showFriendships("1134038393");//args[2]:��ע�û���id
			JSONObject source = object.getJSONObject("source");
			JSONObject target = object.getJSONObject("target");
			System.out.println(source.getString("screen_name")+"��"+target.getString("screen_name")+"��Ϊ��ע");
			//�����û���ϵ����ϸ��� 
			object =  weibo.showFriendships("1134038393","2244737757");
			source = object.getJSONObject("source");
			target = object.getJSONObject("target");
			System.out.println(source.getString("screen_name")+"��"+target.getString("screen_name")+"��Ϊ��ע");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
