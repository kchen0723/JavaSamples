/**
 * 
 */
package weibo4j.examples.timeline;

import java.util.List;

import weibo4j.Comment;
import weibo4j.Status;
import weibo4j.Weibo;

/**
 * @author sina
 *
 */
public class GetComments {
	
	/**
	 * 返回指定微博的最新n条评论 
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		try {
			Weibo weibo = new Weibo();
			weibo.setToken("cb84083390367bfa5437469c438a6502","3fe919007f7528adef06e79e8f031341");
			//args[2]:用户id
			List<Status> list = weibo.getUserTimeline("1032412920");
			if(list.size() > 0) {
				//最新一条微博信息id
				String sid = list.get(5).getId()+"";
				//对该微博消息添加评论
				weibo.updateComment("评论测试", sid , null);
				System.out.println(""+sid);
				List<Comment> comments = weibo.getComments("3355807372829812");
				for(Comment comment : comments) {
					System.out.println(comment.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
