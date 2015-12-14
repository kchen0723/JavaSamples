/**
 * 
 */
package weibo4j.examples.statuses;

import weibo4j.Status;
import weibo4j.Weibo;

/**
 * @author sina
 *
 */
public class Repost {

	/**
	 * ת��һ��΢����Ϣ
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
        try {
        	Weibo weibo = new Weibo();
			weibo.setToken(args[0],args[1]);
        	String sid = "15667252412";
        	Thread.sleep(1000);
        	//args[2]�����ת������Ϣ
        	Status status = weibo.repost(sid, "�Ȱ�������Ȱ���");
        	System.out.println(status.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
