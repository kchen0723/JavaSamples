/**
 * 
 */
package weibo4j.examples.timeline;

import java.util.List;

import weibo4j.Paging;
import weibo4j.Status;
import weibo4j.Weibo;
import weibo4j.WeiboException;

/**
 * @author sina
 *
 */
public class GetUserTimeline {

	/**
	 * ��ȡ�û�������΢����Ϣ�б� 
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		try {
			Weibo weibo = new Weibo();
			weibo.setToken("cb84083390367bfa5437469c438a6502","3fe919007f7528adef06e79e8f031341");
			Paging pag = new Paging();
			pag.setSinceId(3343021761165196l);
			pag.setCount(200);
			//��ȡ24Сʱ��ǰ20���û���΢����Ϣ;args[2]:�û�ID
			List<Status> statuses = weibo.getUserTimeline("֪��",pag);
			for (Status status : statuses) {
	            System.out.println(status.getUser().getName() + ":" +status.getId()+":"+
	                               status.getText() + status.getOriginal_pic());
	        }
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}