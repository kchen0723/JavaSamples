package weibo4j.examples.ids;

import weibo4j.Weibo;
import weibo4j.WeiboException;


public class GetFriendsIDs {

	/**
	 * ��ȡ�û���ע����uid�б� 
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		try {
			Weibo weibo = new Weibo();
			weibo.setToken(args[0],args[1]);
			//�ýӿڷ�ҳ�������cursor����
			int cursor=5;
            //args[2]:��ע�û���id
			long[] ids = weibo.getFriendsIDSByUserId(args[2], cursor).getIDs();
			for(long id : ids) {
				System.out.println(id);
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
