package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class pashouji {

	public static void refresh() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		daochu("iphoneX");
		daochu("XiaoMi");
		daochu("Huawei");
		daochu("Mac");
		daochu("Dell");
		daochu("Hp");
		daochu("logitech");
		daochu("beats");
		daochu("Sony");
	}
	public static int differentDays(Date date1,Date date2){
	    Calendar calendar1 = Calendar.getInstance();
	    calendar1.setTime(date1);
	    Calendar calendar2 = Calendar.getInstance();
	    calendar2.setTime(date2);

	    int day1 = calendar1.get(Calendar.DAY_OF_YEAR);
//	    System.out.println(day1);
	    int day2 = calendar2.get(Calendar.DAY_OF_YEAR);
//	    System.out.println(day2);
	    int year1 = calendar1.get(Calendar.YEAR);
	    int year2 = calendar2.get(Calendar.YEAR);

	    if (year1 != year2)  //不同年
	    {
	        int timeDistance = 0;
	        for (int i = year1 ; i < year2 ;i++){ //闰年
	            if (i%4==0 && i%100!=0||i%400==0){
	                timeDistance += 366;
	            }else { // 不是闰年
	                timeDistance += 365;
	            }
	        }
	        return  timeDistance + (day2-day1);
	    }else{// 同年
	        return day2-day1;
	    }
	}
	
	public static void daochu(String name) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(new
				FileInputStream(name+".json")));
		String str=br.readLine();
		JSONObject obj =JSONObject.fromObject(str);
		File file = new File(name+".txt");
		Writer w = new OutputStreamWriter(new FileOutputStream(file),"gbk");
		 JSONArray com =   obj.getJSONArray("comments");
		 jiexi(com, w);
		 String str0;
			while ( (str0=br.readLine()) != null) {	
				JSONObject obj0 =JSONObject.fromObject(str0);
				 JSONArray com1 =   obj0.getJSONArray("comments");
				jiexi(com1, w);
		}
}
	
	public static void jiexi( JSONArray com, Writer w) throws IOException {
		// TODO Auto-generated method stub
		 for (int i = 0; i < com.size(); i++) {
				 JSONObject com0 = (JSONObject) com.get(i);
				 String comment = ((String) com0.get("content")).replace("\n", "").replace("\r", "");
				 Object image = com0.get("images");
				 Object video = com0.get("videos");
			 	String time = ((String) com0.get("referenceTime")).substring(0,10);//只保存前面的日期部分
			 	String userlevel =  (String) com0.get("userLevelName");
			 	JSONObject after = (JSONObject) com0.get("afterUserComment");
			 	if(after != null) {
			 	JSONObject ha = (JSONObject) after.get("hAfterUserComment");
			 	w.write(userlevel+ "\r\n");
			 	if(image == null && video == null) {
		 			w.write(0+"\r\n");
			 	}else {
		 		w.write(1+"\r\n");
			 	}
			 	w.write(comment+((String) ha.get("content")).replace("\n", "").replace("\r", "")+ "\r\n");
			 	w.write(time+ "\r\n");
			 	System.out.println(userlevel);
			 	System.out.println(comment+ha.get("content"));
			 	System.out.println(time);
			 	}else {
			 	 	w.write(userlevel+ "\r\n");
			 	 	if(image == null && video == null) {
			 			w.write(0+"\r\n");
			 	}else {
			 		w.write(1+"\r\n");
				}
				 	w.write(comment+ "\r\n");
				 	w.write(time+ "\r\n");
			 		System.out.println(userlevel);
			 		System.out.println(comment);
			 		System.out.println(time);
			 	}
				w.flush();
	}
	}
	//pachong
	public static void paya(String name, String url) throws IOException, InterruptedException {
		File file = new File(name+".json");
		Writer write = new OutputStreamWriter(new FileOutputStream(file),"gbk");
//		String url = args[0];
		String url0 = url.substring(0,url.indexOf("page=")+5);
		String url1 = url.substring(url.indexOf("page=")+6);
		Document doc0 = Jsoup.connect(url)
				.timeout(100000)
				.header("Referer", "https://item.jd.com/100001359373.html")
				.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36")
				.get();
		String jsonString0 = doc0.text();
		String str0 = jsonString0.substring(jsonString0.indexOf("(")+1, jsonString0.lastIndexOf(")"));
		System.out.println(str0);	
		write.write(str0+ "\r\n");
		JSONObject obj =JSONObject.fromObject(str0);
		int total =  obj.getInt("maxPage");
		int p = 1;
		while (p < total) {
			Thread.currentThread().sleep(10000);
			Document doc = Jsoup.connect(url0+p+url1)
					.timeout(10000)
					.header("Referer", "https://item.jd.com/100001359373.html")
					.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36")
					.get();
			
		String jsonString = doc.text();
		String str = jsonString.substring(jsonString.indexOf("(")+1, jsonString.lastIndexOf(")"));
		System.out.println(str);
		write.write(str+ "\r\n");
		p++;
		}
        write.flush();
        write.close();
	}
	
	
	public static boolean shishi() throws IOException, ParseException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(new
				FileInputStream("time.json")));
			String date = br.readLine();
			br.close();
			 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		 	Date dNow = new Date();
		 	 Date t = ft.parse(date);
		   if (differentDays(t,dNow) > 30) {
			   File file = new File("time.json");
				Writer w = new OutputStreamWriter(new FileOutputStream(file),"gbk");		   		
			    String Ndate = ft.format(dNow); 
				w.write(date);
				w.flush();
				w.close();
			return true;
		   }else {
			return false;
		}   
	}
	public static void papapa() throws IOException, InterruptedException {
		
		String iphone = "https://sclub.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98vv3973&productId=5089235&score=0&sortType=5&page=0&pageSize=10&isShadowSku=0&rid=0&fold=1";
		String xiaomi = "https://sclub.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98vv5829&productId=100003434260&score=0&sortType=5&page=0&pageSize=10&isShadowSku=0&rid=0&fold=1";
		String huawei = "https://sclub.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98vv3362&productId=100004404928&score=0&sortType=5&page=0&pageSize=10&isShadowSku=0&rid=0&fold=1";
		paya("iphoneX",iphone);
		paya("XiaoMi", xiaomi);
		paya("Huawei", huawei);
		String mac = "https://sclub.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98vv170&productId=4331185&score=0&sortType=5&page=0&pageSize=10&isShadowSku=0&rid=0&fold=1";
		String dell = "https://sclub.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98vv587&productId=7555189&score=0&sortType=5&page=0&pageSize=10&isShadowSku=0&rid=0&fold=1";
		String hp = "https://sclub.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98vv439&productId=100003048768&score=0&sortType=5&page=0&pageSize=10&isShadowSku=0&rid=0&fold=1";
		paya("Mac",mac);
		paya("Dell", dell);
		paya("Hp", hp);
		String luoji = "https://sclub.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98vv393&productId=4219475&score=0&sortType=5&page=0&pageSize=10&isShadowSku=0&rid=0&fold=1";
		String beats = "https://sclub.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98vv120&productId=3133223&score=0&sortType=5&page=0&pageSize=10&isShadowSku=0&rid=0&fold=1";
		String sony = "https://sclub.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98vv155&productId=5180613&score=0&sortType=5&page=0&pageSize=10&isShadowSku=0&rid=0&fold=1   ";
		paya("logitech",luoji);
		paya("beats", beats);
		paya("Sony", sony);	
	}
}
