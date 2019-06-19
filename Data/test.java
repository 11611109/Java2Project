package Java2Proj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.commons.collections.iterators.SingletonListIterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class test {
public static void main(String[] args) throws IOException {
	BufferedReader br = new BufferedReader(new InputStreamReader(new
			FileInputStream("D:\\java-2018-12\\eclipse\\Java2Proj\\result.json")));
	File file = new File("D:\\java-2018-12\\eclipse\\Java2Proj\\pixie.txt");
	Writer w = new OutputStreamWriter(new FileOutputStream(file),"gbk");
	String str;
	while ((str = br.readLine()) != null) {
	 JSONObject obj =JSONObject.fromObject(str);
	 JSONArray pro =   obj.getJSONArray("comments");
	 for (int i = 0; i < pro.size(); i++) {
		 JSONObject a =  pro.getJSONObject(i);
		System.out.println(a.get("content"));
		w.write(a.get("content")+ "\r\n");
	}
	}
	w.flush();
	}

}