/**
 *
 */
package weibo4j.examples.timeline;

import java.util.List;

import weibo4j.Count;
import weibo4j.Paging;
import weibo4j.Status;
import weibo4j.Weibo;

/**
 * @author sina
 *
 */
public class GetCounts {

	/**
	 * 批量统计微博的评论数，转发数
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
        try {
        	Weibo weibo = new Weibo();
			weibo.setToken("cb84083390367bfa5437469c438a6502","3fe919007f7528adef06e79e8f031341");
        	List<Status> statuses = weibo.getUserTimeline("达巴赖马",new Paging(1,100));
        	StringBuilder ids = new StringBuilder();
        	for(Status status : statuses) {
        		ids.append(status.getId()).append(',');
        	}
        	ids.deleteCharAt(ids.length() - 1);
        	List<Count> counts = weibo.getCounts(ids.toString());
    		for(Count count : counts) {
    			System.out.println(count.toString());
    		}
        } catch (Exception e) {
			e.printStackTrace();
		}
	}
}
