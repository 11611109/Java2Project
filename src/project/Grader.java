package project;

import java.util.List;
import project.TimeDifferUtil;
import java.io.*;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.lingjoin.nlpir.NLPIR;

public class Grader {
	private String productName;
	private ArrayList<Double> userLevel;
	private String[] keySet;
	private int[] frequencySet;
	private double finalScore;
	private int[] optionEmotion;
	private int[] optionWeight;
	private String filter1;
	private String filter2;
	private String filter3;

	public Grader() {
		this.productName="product";
	}
	public Grader(String name) {
		this.filter1="No limitation";
		this.filter2="No limitation";
		this.filter3="No limitation";
		this.productName=name;
	}
	public Grader(String name,String filter1,String filter2, String filter3) {
		this.productName=name;
		this.filter1=filter1;
		this.filter2=filter2;
		this.filter3=filter3;
	}
	public void set_name(String productName) {
			this.productName=productName;
	}
	public String get_name() {
		return this.productName;
	}
	
	public void setKeyset(String[] keys) {
		this.keySet=keys;
	}
	
	public String[] getKeyset() {
		return this.keySet;
	}
	
	public void setScore(double score){
		this.finalScore=score;
	}
	
	public double getScore() {
		return this.finalScore;
	}
	
	public void setOptionE(int[] option){
		this.optionEmotion=option;
	}
	
	public int[] getOptionE() {
		return this.optionEmotion;
	}
	public void setOptionW(int[] option){
		this.optionWeight=option;
	}
	
	public int[] getOptionW() {
		return this.optionWeight;
	}
	
	public ArrayList<Double> getuserlevel() {
		return userLevel;
	}
	public String[] getFilter() {
		String[] filters=new String[3];
		filters[0]=filter1;
		filters[1]=filter2;
		filters[2]=filter3;
		return filters;
	}
	
//	public static void main(String[] args) {
//		String[] name={"iphoneX"};
//		//"Huawei","iphoneX","XiaoMi","Sony","beats","logitech","Hp","Dell","Mac"
//		for(String a:name){
//			Grader grader=new Grader(a,"In half year","Longer than 5","With picorvideo");
//			try {
//				grader.getFeature();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//	}


	public void getFeature() throws Exception {
		filter();
		
		String srcFile = this.productName+"Filtered"+".txt";
		File src=new File(srcFile);
		File dst=new File(productName+"key"+".txt");
//		if(!dst.exists()){
			BufferedReader srcFileBr = new BufferedReader(new InputStreamReader(new FileInputStream(src),"GBK"));
			BufferedWriter destFileBw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dst),"GBK")); 
			
			String currentLine="";
			ArrayList<Double> userlevel=new ArrayList<>();
			ArrayList<String> comment=new ArrayList<>();
			int i=0;
			while((currentLine = srcFileBr.readLine()) != null){
				i++;
				if(i%4==1){
					switch (currentLine) {
					case "钻石会员":
						userlevel.add(1.4);
						break;
					case "金牌会员":
						userlevel.add(1.3);
						break;
					case "银牌会员":
						userlevel.add(1.2);
						break;
					case "铜牌会员":
						userlevel.add(1.1);
						break;
					case "注册会员":
						userlevel.add(1.1);
						break;
					case "PLUS会员":
						userlevel.add(1.4);
						break;
					default:
						userlevel.add(1.1);
						break;
					}
				}else if(i%4==3){
					comment.add(currentLine);
				}
			
			}
			this.userLevel=userlevel;
//			System.out.println("lines + " +i);
//			System.out.println("userlevel + "+userlevel);
//			System.out.println("usersize + "+userlevel.size());
			cleanComments(comment);
			fenciAndStop();
			String[] keySet=generateFeature();
			for(String a:keySet){
				destFileBw.write(a+" ");
			}
			destFileBw.write("\r\n");
			for(int x:frequencySet){
				destFileBw.write(x+" ");
			}
//			int[] option1={1,1,-1,1,-1};
//			int[] option2={5,4,5,3,2};
//			grade(keySet,option1,option2,userlevel);
			srcFileBr.close();
			destFileBw.close();
//		}else {
//			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(dst),"GBK"));
//			String current=br.readLine();
//			String[] keyset=current.split(" ");
//			
//			this.keySet=keyset;
//			br.close();
//		}
		
	}
	
	public void filter()throws Exception{
		String srcFile = this.productName+".txt";
	
		String dstFile = this.productName+"Filtered"+".txt";
		File src=new File(srcFile);
		File dst=new File(dstFile);
		
		BufferedReader srcFileBr = new BufferedReader(new InputStreamReader(new FileInputStream(src),"GBK"));
		BufferedWriter destFileBw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dst),"GBK")); 
		int[] flag=new int[3];
		
		switch (filter1) {
		case "No limitation":
			flag[0]=0;
			break;
		case "In half year":
			flag[0]=1;
			break;
		default:
			break;
		}

		switch (filter2) {
		case "No limitation":
			flag[1]=0;
			break;
		case "Longer than 5":
			flag[1]=1;
			break;
		default:
			break;
		}
		
		switch (filter3) {
		case "No limitation":
			flag[2]=0;
			break;
		case "With picorvideo":
			flag[2]=1;
			break;
		default:
			break;
		}
		
		System.out.println(filter1+" time"+filter2+" length"+filter3+" pic");
		System.out.println(flag[0]+","+flag[1]+","+flag[2]);
		
		int i=0;
		
		Date date=new Date();//此时date为当前的时间 
		SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
		String  now=dateFormat.format(date);
		System.out.println(now);
		
		ArrayList<String[]> filterGroups=new ArrayList<>();
		String first;
		while((first=srcFileBr.readLine())!=null){
			String[] group=new String[4];
			int[] flagforone=new int[3];
			String userLevel=first;
			//System.out.println(first);
			String pic=srcFileBr.readLine();
			//System.out.println(pic);
			String content=srcFileBr.readLine();
			//System.out.println(content);
			String time=srcFileBr.readLine();
			//System.out.println(time);
			
			group[0]=userLevel;
			group[1]=pic;
			group[2]=content;
			group[3]=time;
			
			if(!filter2.equals("No limitation")){
				if (content.length()>5) {
					flagforone[1]=1;
				}else {
					flagforone[1]=0;
				}
			}else{flagforone[1]=0;}
			
			if(!filter3.equals("No limitation")){
				if (Integer.valueOf(pic)!=0) {
	//				System.out.println(content);
					flagforone[2]=1;
				}else {
					flagforone[2]=0;
				}
			}else{flagforone[2]=0;}
			long dtime = TimeDifferUtil.getDistanceDays(time,now);
			if(!filter1.equals("No limitation")){
				if (dtime<180) {
					flagforone[0]=1;
				}else {
					flagforone[0]=0;
				}
			}else{flagforone[0]=0;}
			
			int count=0;
			for (int j = 0; j < 3 ; j++) {
				if(flagforone[j]==flag[j]){
					count++;
//					System.out.println("count"+count);
				}
			}
			if (count==3) {
				filterGroups.add(group);
			}
			
		}
		
		for(String[] a:filterGroups){
//			System.out.println(a[0]);
//			System.out.println(a[1]);
//			System.out.println(a[2]);
//			System.out.println(a[3]);
			destFileBw.write(a[0]+"\r\n");
			destFileBw.write(a[1]+"\r\n");
			destFileBw.write(a[2]+"\r\n");
			destFileBw.write(a[3]+"\r\n");
		}
		srcFileBr.close();
		destFileBw.close();
		
	}
	public void cleanComments(ArrayList<String> temp) throws IOException{
		String destFile = this.productName+"clean"+".txt"; 
		BufferedWriter destFileBw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(destFile)),"GBK"));

		LevenshteinDistance ld=new LevenshteinDistance();

		for(int j=0;j<temp.size()-1;j++){
			for(int i=j+1;i<temp.size();i++){
				if(temp.get(i)!=null && temp.get(j)!=null){
					if(ld.getDistance(temp.get(i), temp.get(j))<5){
						//System.out.println(temp.get(i)+"123456789"+temp.get(j));
						temp.set(j, null);
					}
				}
			}
		}
		for(String b:temp){
			if(b!=null){
				destFileBw.write(b+"\r\n"); 
			}
		}
		destFileBw.close();  
	}
	public void fenciAndStop() throws IOException {
	
		String srcFile = this.productName+"clean"+".txt"; 
		String destFile = this.productName+"fenci"+".txt"; 

		BufferedReader srcFileBr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(srcFile)),"GBK")); 
		BufferedReader StopWordFileBr = new BufferedReader(new InputStreamReader(new FileInputStream(new File("stop_words.txt")))); 
		BufferedWriter destFileBw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(destFile)),"GBK")); 
		Set<String> stopWordSet = new HashSet<String>(); 
		String stopWord = null; 
		for(; (stopWord = StopWordFileBr.readLine()) != null;){ 
			stopWordSet.add(stopWord); 
		} 
		//System.out.println(stopWordSet);
		if (NLPIR.init("lib")== false) { 
			System.out.println("NLPIR failed to initialize!"); 
		} 
		String currentLine="";
		for(; (currentLine = srcFileBr.readLine()) != null;){ 
			String temp=NLPIR.paragraphProcess(currentLine, 1);
			String[] resultArray = temp.split(" "); 
			for(int i = 0; i<resultArray.length; i++){ 
				int loc=resultArray[i].lastIndexOf("/");
				if(loc!=-1){
					if(stopWordSet.contains(resultArray[i].substring(0, loc))){ 
					//System.out.println(resultArray[i]);
					resultArray[i] = null; 
					} 
				}else {
					//System.out.println("no fenci"+resultArray[i]);
					//这里怎么处理？？
				}
			} 
			StringBuffer finalStr = new StringBuffer(); 
			for(int i = 0; i<resultArray.length; i++){ 
				if(resultArray[i] != null){ 
					finalStr = finalStr.append(resultArray[i]).append(" "); 
				} 
			}
			destFileBw.write(finalStr.toString()+"\r\n"); 
		} 
		destFileBw.close(); 
		StopWordFileBr.close(); 
		srcFileBr.close(); 
	}
	
	public String[] generateFeature() throws IOException{
		String srcFile = this.productName+"fenci"+".txt";
		BufferedReader srcFileBr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(srcFile)),"GBK"));
		
		String currentLine="";
		Map<String, Integer> featureList=new HashMap<>();
		while((currentLine = srcFileBr.readLine()) != null){
			String[] wordArray = currentLine.split(" ");
			//System.out.println(Arrays.toString(wordArray));
			for(int i=0;i<wordArray.length;i++){
				int loc;
				if((loc=wordArray[i].lastIndexOf("/a"))!=-1){
					String temp=wordArray[i].substring(0, loc);
					if(featureList.keySet().contains(temp)){
						featureList.put(temp, featureList.get(temp)+1);
					}else {
						if (temp.length()>1) {
							featureList.put(temp,1);
						}
					}		
				}			    
			}
		}
		
		Comparator<Map.Entry<String, Integer>> featureComparator = Comparator.comparingInt(e -> e.getValue());
		ArrayList<Map.Entry<String,Integer>> list=new ArrayList<>();
		list.addAll(featureList.entrySet());
		Collections.sort(list, featureComparator);
//		System.out.println(list);
		
		//将形容词合并，取前几作为候选项,选做
		
		
//		System.out.println(list.size()+"featurelist");
		int keyAvailable=0;
		if(list.size()>=10){
			keyAvailable=10;
		}else {
			keyAvailable=list.size();			
		}
		String [] keyList=new String[keyAvailable];
		int[] frequencyList=new int[keyAvailable];
		for(int l=0;l<keyList.length;l++){
			keyList[l]=list.get(list.size()-l-1).getKey();
			frequencyList[l]=list.get(list.size()-l-1).getValue();
		}
		
		this.keySet=keyList;
		this.frequencySet=frequencyList;
		srcFileBr.close();
		return keyList;
		
	}
	//评分
	public void grade(String[] keyList, int[] option1, int[] option2,ArrayList<Double> userLevel) throws IOException{
		//option1是正负向的选择，option2是权重的选择，totalScore是总分
		double[] totalScore=new double[5];

		String srcFile = this.productName+"fenci"+".txt";
		BufferedReader srcFileBr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(srcFile)),"GBK"));
		
		String currentLine="";
		int j=0;
		while((currentLine = srcFileBr.readLine()) != null){
			
			String[] wordArray = currentLine.split(" ");
			for(String a:wordArray){
				int loc;
				if((loc=a.lastIndexOf("/a"))!=-1){
					String temp=a.substring(0, loc);
						for(int i=0;i<5;i++){
						if(temp.equals(keyList[i])){
							totalScore[i]+=userLevel.get(j)/1.4;
						}
					}
				}
			}
			j++;
		}
		double finalScore=0;
		double[] scoreForKey=new double[5];
		for(int t=0;t<5;t++){
			scoreForKey[t]=(option1[t]*Math.min(1, totalScore[t]/j*10)+1)/2;
		}
		int totalweight=0;
		System.out.println(j+"totalcomment");
		for(int p=0;p<5;p++){
			totalweight+=option2[p];
			System.out.print(scoreForKey[p]+"   ");
			finalScore+=option2[p]*scoreForKey[p];
		}
		this.finalScore=finalScore/totalweight*5;
		srcFileBr.close();
	}
	
}
class LevenshteinDistance {
	public static int getDistance(String s1,String s2){
		int m= (s1==null)?0:s1.length();
		int n= (s2==null)?0:s2.length();
		if(m==0){
			return n;
		}
		if(n==0){
			return m;
		}
		//左上方
		int tl;
		//上方
		int[] t=new int[n];
		//左方
		int[] l=new int[m];
		//初始化
		for(int i=0;i<t.length;i++){
			t[i]=i+1;
		}
		for(int i=0;i<l.length;i++){
			l[i]=i+1;
		}
		int d=0;
		for(int i=0;i<l.length;i++){
			tl=i;
			char s1_c=s1.charAt(i);
			for(int j=0;j<t.length;j++){
				d=Math.min(Math.min(l[i], t[j])+1,tl+((s1_c==s2.charAt(j))?0:1));
				tl=t[j];
				l[i]=d;
				t[j]=d;
			}
		}
		return d;
	}
	public static float getSimilarity(String s1,String s2){
		if(s1==null||s2==null){
			if(s1==s2){
				return 1.0f;
			}
			return 0.0f;
		}
		float d=getDistance(s1,s2);
		return 1-(d/Math.max(s1.length(), s2.length()));

	}

}

