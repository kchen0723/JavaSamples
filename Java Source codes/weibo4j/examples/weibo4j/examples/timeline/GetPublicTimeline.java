/**
 *
 */
package weibo4j.examples.timeline;

import java.util.List;

import weibo4j.Status;
import weibo4j.Weibo;
import weibo4j.WeiboException;

/**
 * @author sina
 *
 */
public class GetPublicTimeline {

	/**
	 * ��ȡ���¸��µĹ���΢����Ϣ
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
    	try {
			//��ȡǰ20�����¸��µĹ���΢����Ϣ
    		Weibo weibo = new Weibo();
			weibo.setToken("cb84083390367bfa5437469c438a6502","3fe919007f7528adef06e79e8f031341");
			 List<Status> statuses =weibo.getPublicTimeline(100,0);
			for (Status status : statuses) {
	            System.out.println(status.getUser().getName() + ":" +
	                               status.getCreatedAt());
	            //System.out.println(status.toString());
	        }
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
