/**
 * 
 */
package weibo4j.examples.statuses;

import java.util.List;

import weibo4j.Comment;
import weibo4j.Status;
import weibo4j.Weibo;

/**
 * @author sina
 *
 */
public class Reply {

	/**
	 * ��һ��΢��������Ϣ���лظ�
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
        try {
        	Weibo weibo = new Weibo();
			weibo.setToken(args[0],args[1]);
        	List<Status> list = weibo.getUserTimeline(args[2]);
        	if(list.size() > 0) {
        		//����һ��΢����Ϣid
        		String sid = list.get(0).getId()+"";
        		List<Comment> comments = weibo.getComments(sid);
        		if(comments.size() > 0) {
        			String cid = comments.get(0).getId()+"";//���۵�id
        			Comment cmt = weibo.reply(sid, cid, "1Q84");//args[3]���ظ�����
        			System.out.println(cmt.getReplyComment().toString());
        		}
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
