/**
 * 
 */
package weibo4j.examples.statuses;

import java.util.List;

import weibo4j.Status;
import weibo4j.Weibo;

/**
 * @author haidong
 *
 */
public class GetRepostTimeline {

	/**
	 * 返回一条原创微博消息的最新n条转发微博消息。本接口无法对非原创微博进行查询。 
     * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
        try {
        	Weibo weibo = new Weibo();
        	weibo.setToken("cb84083390367bfa5437469c438a6502","3fe919007f7528adef06e79e8f031341");
        	List <Status> list =  weibo.getreposttimeline("3356803656279894");
        	for(Status status:list){
        	System.out.println(status.toString());
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}