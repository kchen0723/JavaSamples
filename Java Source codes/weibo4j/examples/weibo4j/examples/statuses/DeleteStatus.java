/**
 * 
 */
package weibo4j.examples.statuses;

import java.util.List;

import weibo4j.Status;
import weibo4j.Weibo;

/**
 * @author sina
 *
 */
public class DeleteStatus {

	/**
	 * ɾ��һ��΢����Ϣ
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
        try {
        	Weibo weibo = new Weibo();
			weibo.setToken(args[0],args[1]);
        	//�ȷ���һƪ΢��
        	Status status = weibo.updateStatus("���Բ���");
        	System.out.println(status.getId() + " : "+ status.getText()+"  "+status.getCreatedAt());
        	//ɾ���շ����΢��
        	status = weibo.destroyStatus(status.getId());
        	List<Status> list = weibo.getUserTimeline(args[2]);//args[2]:�û�id
        	for(Status st : list) {//������ǰ΢����Ϣ
        		System.out.println(st.getId() + " : "+ st.getText()+"  "+st.getCreatedAt());
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
