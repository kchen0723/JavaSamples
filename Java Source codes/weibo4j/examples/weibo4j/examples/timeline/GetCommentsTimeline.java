/**
 * 
 */
package weibo4j.examples.timeline;

import java.util.List;

import weibo4j.Comment;
import weibo4j.Weibo;

/**
 * @author sina
 *
 */
public class GetCommentsTimeline {

	/**
	 * ��ʱ��˳�򷵻�����n�����ͼ��յ�������
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
        try {
        	Weibo weibo = new Weibo();
			weibo.setToken(args[0],args[1]);
        	List<Comment> comments = weibo.getCommentsTimeline();
    		for(Comment comment : comments) {
    			System.out.println(comment.getStatus().getText());
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
