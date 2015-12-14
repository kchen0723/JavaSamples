package weibo4j.examples.trends;

import java.util.List;

import weibo4j.Paging;
import weibo4j.Status;
import weibo4j.Weibo;
import weibo4j.WeiboException;

public class getTrendStatus {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
    	Paging paging = new Paging();
    	paging.setCount(20);
	    paging.setPage(3);
	    String trends_name="Ä¦¸ù´óÍ¨";
		try{
			Weibo weibo = new Weibo();
			weibo.setToken("cb84083390367bfa5437469c438a6502","3fe919007f7528adef06e79e8f031341");
			for(int i=1;i<4;i++){
			List<Status> status = weibo.getTrendStatus(trends_name,new Paging(i,20));
			for(Status statuses:status){
				System.out.println(statuses.toString());
			}
			}
		}catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
