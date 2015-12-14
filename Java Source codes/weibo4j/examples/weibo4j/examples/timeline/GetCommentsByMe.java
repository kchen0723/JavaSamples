/**
 * 
 */
package weibo4j.examples.timeline;

import java.util.List;

import weibo4j.Comment;
import weibo4j.Paging;
import weibo4j.Weibo;

/**
 * @author sina
 *
 */
public class GetCommentsByMe {

	/**
	 * 获取当前用户发出的评论
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
        try {
        	Weibo weibo = new Weibo();
			weibo.setToken("cb84083390367bfa5437469c438a6502","3fe919007f7528adef06e79e8f031341");
        	List<Comment> comments = weibo.getCommentsToMe(new Paging(1,200));
    		for(Comment comment : comments) {
    			System.out.println(comment.toString());
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
