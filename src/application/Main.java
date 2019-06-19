package application;
	
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.tc33.jheatchart.HeatChart;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;

import javafx.stage.Modality;
import javafx.stage.Stage;
import project.Grader;

import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;



public class Main extends Application {
	private Stage primaryStage;
	private AnchorPane rootLayout;
//	public ObservableList<Words> wordlist = FXCollections.observableArrayList();
//	
//	public ObservableList<Words> getwordlist() {
//	    return wordlist;
//	}
	@Override
	public void start(Stage primaryStage) throws IOException {
		try {

			// Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class
                    .getResource("/application/Main.fxml"));
            rootLayout = (AnchorPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            MainController controller = loader.getController();
            controller.setMain(this);

            primaryStage.show();		          
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	private void closeProgram(Stage window) {
		Boolean answer = ConfirmBox.display("Title","Sure you want to exit?");
		if(answer)
			window.close();
	}
	
	public boolean showGradeDialog(Grader grader) throws Exception {
        try {
        	System.out.println("first step");
        	
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/application/Scorebox.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Grade the product!");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the person into the controller.
            ScoreBoxController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            
            String product=grader.get_name();
            String[] filterList=grader.getFilter();
            String data=product+filterList[0]+filterList[1]+filterList[2]+".txt";
			File dataFile=new File(data);
            
            
            if (!dataFile.exists()) {
            	grader.getFeature();
            	System.out.println("feature OK");
                controller.setGrader(grader);
			}else {
				controller.setAll(grader);
			}

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            
            
            return controller.isScoreClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	
	//corrlection analysis
		public static void showStatistics() {
			Map<String, Integer> phones = new HashMap<String, Integer>();
			Map<String, Integer> computers = new HashMap<String, Integer>();
			Map<String, Integer> hear = new HashMap<String, Integer>();
			ArrayList<String> com = new ArrayList<String>();
			data(phones, computers, hear, com);
	       Stage stage = new Stage();
	       stage.setTitle("Sales volume");
	       	CategoryAxis xAxis = new CategoryAxis();
	    	 NumberAxis yAxis = new NumberAxis();
	    	 xAxis.setLabel("Date");
	    	 final LineChart<String,Number> lineChart = 
	                 new LineChart<String,Number>(xAxis,yAxis);
	    	  lineChart.setTitle("Sales volume");
	    	  XYChart.Series<String,Number> series1 = new XYChart.Series<String,Number>();
	         series1.setName("phones");
	          XYChart.Series<String,Number> series2 = new XYChart.Series<String,Number>();
	          series2.setName("computers");
	          XYChart.Series<String,Number> series3 = new XYChart.Series<String,Number>();
	          series3.setName("earphone");
	          Collections.sort(com);
	          for (int i = 0; i < com.size(); i++) {
	       	   String key = com.get(i);
	       	   	series1.getData().add(new XYChart.Data(key,phones.get(key)));
				series2.getData().add(new XYChart.Data(key,computers.get(key)));
				 series3.getData().add(new XYChart.Data(key,hear.get(key)));
	          }
	          Scene scene  = new Scene(lineChart,800,600);       
	          lineChart.getData().addAll(series1, series2, series3);
	          stage.setScene(scene);
	          stage.show(); 
	   }

		public static void data(Map<String, Integer> phones, Map<String, Integer> computers, 
				Map<String, Integer> hear, ArrayList<String> com) {
			 ArrayList<String> iphoneX = new ArrayList<String>() ;
		   	 ArrayList<String> XiaoMi = new ArrayList<String>() ;
		   	 ArrayList<String> Huawei = new ArrayList<String>() ;
		   	 ArrayList<String> Dell = new ArrayList<String>() ;
		   	 ArrayList<String> Hp = new ArrayList<String>() ;
		   	  ArrayList<String> Mac = new ArrayList<String>() ;
		   	  ArrayList<String> beats = new ArrayList<String>() ;
		   	  ArrayList<String> logitech = new ArrayList<String>() ;
		   	  ArrayList<String> Sony = new ArrayList<String>() ;
		        try {
					tongji("iphoneX", iphoneX);
					tongji("XiaoMi", XiaoMi);
					tongji("Huawei", Huawei);
					tongji("Dell", Dell);
					tongji("Hp", Hp);
					tongji("Mac", Mac);
					tongji("beats", beats);
					tongji("logitech", logitech);
					tongji("Sony", Sony);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	zhonglei(phones, iphoneX, Huawei, XiaoMi);
		    	zhonglei(computers, Mac, Hp, Dell);	    	
		    	zhonglei(hear, Sony, beats, logitech);   	
		    	Set<String> keySet = phones.keySet();
		    	for(String key : keySet) {
		    		if (hear.containsKey(key)) {
						if (computers.containsKey(key)) {
							com.add(key);
						}
					}
		    	}
		    	
		}
	   	public static void tongji(String name, ArrayList<String> date) throws IOException {
	   	// TODO Auto-generated method stub
	      	 BufferedReader br = new BufferedReader(new InputStreamReader(new
	  			FileInputStream(name+".txt")));
	      	 	String str;  
	      	 	while ( (str=br.readLine()) != null) {		
		    		String reg = "2[0-9]{3}-((0([1-9]))|(1(0|1|2)))";
		    		Pattern pattern = Pattern.compile (reg);	    
		    		Matcher matcher = pattern.matcher (str);	    
		    		while (matcher.find ()){
		    			date.add(matcher.group());
		    }
	      }
		}
	   	
		public static void zhonglei(Map<String, Integer> count,ArrayList<String> one, ArrayList<String> two,ArrayList<String> three) {
			// TODO Auto-generated method stub
			for(int i = 0 ; i < one.size() ; i ++) {
	  			if(count.containsKey(one.get(i))==true) {
	  				count.get(one.get(i));
	  				count.put(one.get(i), count.get(one.get(i))+1);
	  			}else {
	  				count.put(one.get(i), 1);
	  			}
	  		}
			for(int i = 0 ; i < two.size() ; i ++) {
	  			if(count.containsKey(two.get(i))==true) {
	  				count.get(two.get(i));
	  				count.put(two.get(i), count.get(two.get(i))+1);
	  			}else {
	  				count.put(two.get(i), 1);
	  			}
	  		}
			for(int i = 0 ; i < three.size() ; i ++) {
	  			if(count.containsKey(three.get(i))==true) {
	  				count.get(three.get(i));
	  				count.put(three.get(i), count.get(three.get(i))+1);
	  			}else {
	  				count.put(three.get(i), 1);
	  			}
	  		}
		}

		private static double corr(int[] a, int[] b ) {
			// TODO Auto-generated method stub
			int suma = 0;
			int sumas = 0 ;
			int sumab = 0;
			int sumb = 0;
			int sumbs = 0 ;
			for (int i = 0; i < a.length; i++) {
				suma += a[i]; 
				sumas += a[i]*a[i];
				sumb += b[i];
				sumbs += b[i]*b[i];
				sumab += a[i]*b[i];
			}
			double ea = suma / a.length;
			double eas = sumas / a.length;
			double eb = sumb / a.length;
			double ebs = sumbs / a.length;
			double eab = sumab / a.length;
			
			return (eab-ea*eb)/Math.sqrt((eas-ea*ea)*(ebs-eb*eb));
		}
		
		public static void showHeatMap() throws IOException {
			Map<String, Integer> phones = new HashMap<String, Integer>();
			Map<String, Integer> computers = new HashMap<String, Integer>();
			Map<String, Integer> hear = new HashMap<String, Integer>();
			ArrayList<String> com = new ArrayList<String>();
			data(phones, computers, hear, com);
			int[] ph = new int[com.size()];
	     	for (int i = 0; i < com.size(); i++) {
	   		ph[i] = phones.get(com.get(i));
			}
	   	int[] co = new int[com.size()];
	     	for (int i = 0; i < com.size(); i++) {
	   		co[i] = computers.get(com.get(i));
			}
	   	int[] he = new int[com.size()];
	    	for (int i = 0; i < com.size(); i++) {
	 		he[i] = hear.get(com.get(i));
		}
	    	double corr1 = corr(ph, co);
	    	double corr2 = corr(co,he);
	    	double corr3 = corr(ph, he);
	    	 Stage stage = new Stage();
	    	 System.out.println(corr1+" "+corr2 + " "+corr3);
	    	 int cor1 = 1 ;
	    	 int cor2 = 2 ;
	    	 int cor3 = 3 ;
	    	 int temp = 0; 
	    	 
	    	 stage.setTitle("Heat Map");
	    	 double[][]  data = {    {4,2,1},
	    			 {2, 4,3},
  			 {1 ,3, 4}
	    	 };
		    
		    Font f1= new Font("Arial",50,95);
	        Font f2 = new Font("Arial", Font.PLAIN, 0);
		    // Create our heat chart using our data.
		    HeatChart chart = new HeatChart(data);
		    chart.setHighValueColour(Color.red);
		    chart.setLowValueColour(Color.yellow);
		    
		    // Customise the chart.
		    chart.setTitle("Sale Correlations");
		    chart.setTitleFont(f1);
		    chart.setAxisThickness(0);
		    chart.setYAxisValuesFrequency(10);
		    chart.setAxisValuesFont(f2);
		    chart.setCellHeight(500);
		    chart.setCellWidth(500);
		    chart.setChartMargin(100);
		    chart.setXAxisLabel("Cellphone   Computer   Earphone");
		    chart.setYAxisLabel("Earphone   Computer   Cellphone");
		    chart.setAxisLabelsFont(f1);
	    	 BorderPane root = new BorderPane();
	 	   // Output the chart to a file.
		    chart.saveToFile(new File("my-chart.png"));
		    

		    Image image = new Image("file:C:\\Users\\ASUS\\workspace\\java2PRO\\my-chart.png",500,500, false, false);
		    ImageView imageView = new ImageView();
			imageView.setImage(image);
			root.setCenter(imageView);
			

	 	   Scene scene  = new Scene(root,500,500);       
	 	
			stage.setScene(scene);
			stage.show();

		}
	public static void main(String[] args) {
		launch(args);
	}
	
}
