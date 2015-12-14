package statisticsModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;


public class SimpleStatistics {
	public SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	//list重复值剔除
	public String[] DuplicateDataDelete (List<String> arrayList){
		HashSet<String> hashSet = new HashSet<String>(arrayList);
	    List<String> arrayListNext = new ArrayList<String>(hashSet);
	    String[] s2 = arrayListNext.toArray(new String[arrayListNext.size()]);
		return s2;
	}	
	
	//日期排序
	public String[] DateOrder(String[] dates){
		String temp=dates[0];
		String[] back=null;
		try {
			Date latest=format.parse(temp);
			Date earliest=format.parse(temp);
			for(int i=1;i<dates.length;i++){
				Date point=format.parse(dates[i]);
				if(point.before(earliest)){
					earliest=format.parse(dates[i]);
				}
				if(point.after(latest)){
					latest=format.parse(dates[i]);
				}
			}
			//System.out.println("latest="+latest);最早日期测试
			//System.out.println("earliest="+earliest);最晚日期测试
			int days=Integer.parseInt(((latest.getTime()-earliest.getTime())/(1000*3600*24))+"");
			//System.out.println("days="+days);间隔天数测试
			List<String> arrayListtemp = new ArrayList<String>();
			arrayListtemp.add(format.format(earliest));
			
			Calendar c = Calendar.getInstance(); 
						
			for(int j=1;j<days+1;j++){
				c.setTime(earliest);
				c.add(Calendar.DATE, j); 
				Date dt=c.getTime();
				arrayListtemp.add(format.format(dt));
				//System.out.println("d="+format.format(dt));日期测试
			}
			back = arrayListtemp.toArray(new String[arrayListtemp.size()]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return back;
	}	
	
	//将当前日期加减n天
	public Date DaysAdd(Date date,int days){
		Calendar c = Calendar.getInstance(); 
		c.setTime(date);
		c.add(Calendar.DATE, days); 
		Date dt=c.getTime();
		return dt;
	}
	
	//将当前日期加减n小时
	public Date TimeAdd(Date date,int hours){
		Calendar c = Calendar.getInstance(); 
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, hours); 
		Date dt=c.getTime();
		return dt;
	}
	
	//计算数组（有重复）中某值的个数
	public int ComputeNo(String s,List<String> arrayListNext){
		int no=0;
		String[] original= arrayListNext.toArray(new String[arrayListNext.size()]);
		for(int i=0;i<original.length;i++){
			if(s.equals(original[i])){
				no++;
			}
		}
		return no;
	}
	
	//按区间对数据统计
	public int[] FanNumberClassification(Long[] in,int[] point){
		int[] back=new int[11];
		int[] backreal=new int[11]; 
		int index=0;
		for(int j=0;j<in.length;j++){
			a:for(int i=0;i<point.length;i++){
				if((in[j]-point[i])<0){
					index=i;
					switch(index)
					{
						case 0:back[0]++;
						case 1:back[1]++;
						case 2:back[2]++;
						case 3:back[3]++;
						case 4:back[4]++;
						case 5:back[5]++;
						case 6:back[6]++;
						case 7:back[7]++;
						case 8:back[8]++;
						case 9:back[9]++;
					}
					break a;
				}
			}
		}
		backreal[0]=back[0];
		backreal[10]=in.length-back[9];
		for(int i=1;i<10;i++){
			backreal[i]=back[i]-back[i-1];
		}
		return backreal;
	}
	
	//返回0-23点中分时间微博数
	public void TimeClassification(String date,int[] back){
		String[] s1=date.split(" ");
		String[] s2=s1[1].split(":");
		int index=Integer.parseInt(s2[0]);
		switch(index)
		{
			case 0:back[0]++;
			case 1:back[1]++;
			case 2:back[2]++;
			case 3:back[3]++;
			case 4:back[4]++;
			case 5:back[5]++;
			case 6:back[6]++;
			case 7:back[7]++;
			case 8:back[8]++;
			case 9:back[9]++;
			case 10:back[10]++;
			case 11:back[11]++;
			case 12:back[12]++;
			case 13:back[13]++;
			case 14:back[14]++;
			case 15:back[15]++;
			case 16:back[16]++;
			case 17:back[17]++;
			case 18:back[18]++;
			case 19:back[19]++;
			case 20:back[20]++;
			case 21:back[21]++;
			case 22:back[22]++;
			case 23:back[23]++;
		}			
	}
	
	//返回xx天前的日期
	public Date DateConvert(String days){
		int daysno=Integer.parseInt(days);
		Calendar c = Calendar.getInstance(); 
		Date now=new Date();
		c.setTime(now);
		c.add(Calendar.DATE, -daysno); 
		Date dt=c.getTime();
		return dt;
	}
	
	//判断用户地区
	public Boolean LocationConvert(int input,String location){
		Object[][] address={{"天津",12},{"台湾",71},{"上海",31},
				{"湖南",43},{"广东",44},{"江苏",32},{"浙江",33},
				{"河北",13},{"吉林",22},{"山西",14},{"安徽",34},
				{"内蒙古",15},{"江西",36},{"辽宁",21},{"福建",35},
				{"黑龙江",23},{"山东",37},{"四川",51},{"新疆",65},
				{"广西",45},{"云南",53},{"甘肃",62},{"河南",41},
				{"陕西",61},{"澳门",82},{"贵州",52},{"西藏",54},
				{"湖北",42},{"重庆",50},{"海南",46},{"青海",63},
				{"宁夏",64},{"海外",400},{"北京",11},{"香港",81}};
		int check=0;
		if(location.equals("不限")){
			check++;
		}else{
			for(int i=0;i<35;i++){
				int no=Integer.parseInt(address[i][1]+"");
				if(address[i][0].equals(location)&&no==input){
					check++;
				}
			}
		}
		if(check>0){
			return true;
		}else{
			return false;	
		}
	}
	
	//返回区域代码
	public int LocationConvertInt(String location){
		Object[][] address={{"天津",12},{"台湾",71},{"上海",31},
				{"湖南",43},{"广东",44},{"江苏",32},{"浙江",33},
				{"河北",13},{"吉林",22},{"山西",14},{"安徽",34},
				{"内蒙古",15},{"江西",36},{"辽宁",21},{"福建",35},
				{"黑龙江",23},{"山东",37},{"四川",51},{"新疆",65},
				{"广西",45},{"云南",53},{"甘肃",62},{"河南",41},
				{"陕西",61},{"澳门",82},{"贵州",52},{"西藏",54},
				{"湖北",42},{"重庆",50},{"海南",46},{"青海",63},
				{"宁夏",64},{"海外",400},{"北京",11},{"香港",81}};
		int check=-1;
		for(int i=0;i<35;i++){
			if(location.equals(address[i][0])){
				check=Integer.parseInt(address[i][1]+"");
			}
		}
		return check;
	}
	
	//地区combobox初始化
	public void ComboboxInitial(JComboBox locationbox){
		String[] address={"不限","广东","上海","北京","天津","台湾","湖南",
				"江苏","浙江","河北","吉林","山西","安徽","内蒙古",
				"江西","辽宁","福建","黑龙江","山东","四川","新疆",
				"广西","云南","甘肃","河南","陕西","澳门","贵州","西藏",
				"湖北","重庆","海南","青海","宁夏","香港","海外"};
		for(int i=0;i<address.length;i++){
			locationbox.addItem(address[i]);
		}
	}
	
	//显示分辨率修正（chart）
	public int chartwidthset(int width){
		int set=0;
		if(width<1152){
			set=650;
		}else if(width>=1152&&width<1280){
			set=750;
		}else if(width>=1280&&width<1400){
			set=900;
		}else if(width>=1400&&width<1600){
			set=1000;
		}else if(width>=1600&&width<1680){
			set=1200;
		}else if(width>=1680&&width<1920){
			set=1300;
		}else{
			set=1500;
		}
		return set;
	} 
	
	//显示分辨率修正（table）
	public int tablewidthset(int width){
		int set=0;
		if(width<=1024){
			set=120;
		}else{
			set=150;
		}
		return set;
	} 
	
	////显示分辨率修正
	public int chartheightset(int height){
		int set=0;
		if(height<=768){
			set=500;
		}else if(height>768&&height<=864){
			set=600;
		}else if(height>864&&height<=960){
			set=700;
		}else if(height>960&&height<=1024){
			set=750;
		}else if(height>1024&&height<=1050){
			set=800;
		}else{
			set=900;
		}
		return set;
	} 
	
	//提取日期中月，日
	public String MonthDayExtract(String date){
		String[] temp=date.split("-");
		String back=temp[1]+"-"+temp[2];
		return back;
	}
	
	//判断是否为数字
	public boolean isNum(String msg){
		if(java.lang.Character.isDigit(msg.charAt(0))){
			return true;
		}
			return false;
	}
	
	//两表间复制
	public void CopyBetweenTable(String item,DefaultTableModel modelsource,int columnsource,DefaultTableModel modeldestiny){
		int rows=modelsource.getRowCount();
		int column=modelsource.getColumnCount();
		System.out.println("r="+rows+"   "+"c="+column);
		for(int i=0;i<rows;i++){
			if(item.equals(modelsource.getValueAt(i,columnsource))){
				List<Object> arrayListtemp = new ArrayList<Object>();
				for(int j=0;j<column;j++){
					System.out.println(modelsource.getValueAt(i,j));
					arrayListtemp.add(modelsource.getValueAt(i, j));
				}
				arrayListtemp.add(format.format(new Date()));
				Object[] temp=arrayListtemp.toArray(new Object[arrayListtemp.size()]);
				modeldestiny.addRow(temp);
			}
		}
	}
	
	//获取url中客户端数值
	public String SourceExtract(String source){
		String result="";
		if(source!=""){
			String[] s1 =source.split(">");
			String[] s2 = s1[1].split("<"); 
			result=s2[0];
		}		
		return result;
	}
	
	//博文中关键词判断
	public boolean KeyWordsSearch(List<String> keywords,List<String> keywordfs,String source){
		int j=keywords.size();
		int f=keywordfs.size();
		for(int i=0;i<keywords.size();i++){
			int intIndex = source.indexOf(keywords.get(i));
			if(intIndex<0){
				intIndex=intIndex*10000;
			}
			j=j+intIndex;
		}
		
		for(int i=0;i<keywordfs.size();i++){
			int intIndex = source.indexOf(keywordfs.get(i));
			if(intIndex<0){
				intIndex=intIndex*10000;
			}
			f=f+intIndex;
		}
		if(f>0||j>0){
			return true;
	     }else{
	       	return  false;
	     }
    }

	//写table数值列判断
	public boolean NoRowCheck(int row,int[] norow){
		int check=0;
		for(int i=0;i<norow.length;i++){
			if(row==norow[i]){
				check++;
	        }
		}
		if(check>0){
			return true;
		}else{
			return false;
		}
	}
	
	
	//判断某日期是否在2日期之间
	public boolean DateBetweenCheck(String check,String ely,String late){
		Date checkd = null;
		Date elyd = null;
		Date lated = null;
		
		try {
			checkd=format.parse(check);
			elyd=format.parse(ely);
			lated=format.parse(late);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar c = Calendar.getInstance(); 
		c.setTime(elyd);
		c.add(Calendar.DATE, -1); 
		Date elydt=c.getTime();
		c.setTime(lated);
		c.add(Calendar.DATE, 1); 
		Date latedt=c.getTime();
		
		if(checkd.after(elydt)&&checkd.before(latedt)){
			return true;
		}else{
			return false;
		}
	}
	
	//在集合中搜索敏感词
	public String WordsSearch(String keywords,List<String> SensitiveWords){
		int items=SensitiveWords.size();
		String result=null;
		for(int i=0;i<items;i++){
			if(keywords.indexOf(SensitiveWords.get(i))>=0){
				result=result+","+SensitiveWords.get(i);
			}
		}
		return result;
	} 
}
