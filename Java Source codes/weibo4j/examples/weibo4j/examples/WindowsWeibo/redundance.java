package weibo4j.examples.WindowsWeibo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class redundance {
	 public static void main(String s[]) {
		 List<String> arrayList1 = new ArrayList<String>();

		    arrayList1.add("2212729754");
		    arrayList1.add("2212729754");
		    arrayList1.add("2212729754");
		    arrayList1.add("2292729754");
		    arrayList1.add("2212724454");
		    
		    String[] s1 = arrayList1.toArray(new String[arrayList1.size()]); 

		    HashSet<String> hashSet = new HashSet<String>(arrayList1);

		    List<String> arrayList2 = new ArrayList<String>(hashSet);

		    String[] s2 = arrayList2.toArray(new String[arrayList2.size()]); 
		 
		    for(int i=0;i<s2.length;i++){
		    	int ss=stat(s2[i],s1);
		    	System.out.println(s2[i]+"  "+ss);
		    }
		    arrayList1.clear();
		    arrayList2.clear();
	 }
	 
	 public static int stat(String s,String[] str){
		int num=0;
		for(int i=0;i<str.length;i++){
			if(s==str[i]){
				num++;
			}
		} 
		 return num;
	 }
	 
}
