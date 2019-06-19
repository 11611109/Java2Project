package application;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.Grader;

public class ScoreBoxController{
//	@FXML
//    private TableView<Words> wordlist;
//    @FXML
//    private TableColumn<Words, String> wordColumn;
//    @FXML
//    private TableColumn<Words, String> emotionColumn;
//    @FXML
//    private TableColumn<Words, String> weightColumn;
//	
	@FXML ChoiceBox<Integer> weight1;
	@FXML ChoiceBox<Integer> weight2;
	@FXML ChoiceBox<Integer> weight3;
	@FXML ChoiceBox<Integer> weight4;
	@FXML ChoiceBox<Integer> weight5;
	
	@FXML ChoiceBox<String> emotion1;
	@FXML ChoiceBox<String> emotion2;
	@FXML ChoiceBox<String> emotion3;
	@FXML ChoiceBox<String> emotion4;
	@FXML ChoiceBox<String> emotion5;
//	
//	@FXML  TextField key1;
//	@FXML  TextField key2;
//	@FXML  TextField key3;
//	@FXML  TextField key4;
//	@FXML  TextField key5;
	@FXML  Label label1;
	@FXML  Label label2;
	@FXML  Label label3;
	@FXML  Label label4;
	@FXML  Label label5;
	@FXML  Label scoreLabel;
	
	@FXML Button Score;
	@FXML ProgressBar progress;
	@FXML Button barChart;
	@FXML Button pieChart;

	
	@FXML  TextField scoreofProduct;
	
	private Stage dialogStage;
    private Grader grader;
    private boolean scoreClicked = false;
				
    
    public void setGrader(Grader grader) {
		this.grader=grader;
		String[] KeyList=grader.getKeyset();
		System.out.println(KeyList+"list");
		System.out.println(label1+"label");
		label1.setText(KeyList[0]);
		System.out.println(label2);
		label2.setText(KeyList[1]);
		label3.setText(KeyList[2]);
		label4.setText(KeyList[3]);
		label5.setText(KeyList[4]);
	}
    
    public void setAll(Grader grader) throws IOException {
		this.grader=grader;
		String product=grader.get_name();
        String[] filterList=grader.getFilter();
        
        String data=product+filterList[0]+filterList[1]+filterList[2]+".txt";
		File dataFile=new File(data);
		BufferedReader srcFileBr = new BufferedReader(new InputStreamReader(new FileInputStream(dataFile),"GBK"));
		
		String currentLine;
		currentLine=srcFileBr.readLine();
		String[] keys=currentLine.split(" ");
		currentLine=srcFileBr.readLine();
		String[] weights=currentLine.split(" ");
		currentLine=srcFileBr.readLine();
		String[] emotions=currentLine.split(" ");
		currentLine=srcFileBr.readLine();
		String score=currentLine;
		
		label1.setText(keys[0]);
//		System.out.println(label2);
		label2.setText(keys[1]);
		label3.setText(keys[2]);
		label4.setText(keys[3]);
		label5.setText(keys[4]);
		
		scoreLabel.setText(score);
		progress.setProgress(Double.valueOf(score)/5);
		
		emotion1.setValue(emotions[0]);
		emotion2.setValue(emotions[1]);
		emotion3.setValue(emotions[2]);
		emotion4.setValue(emotions[3]);
		emotion5.setValue(emotions[4]);
		
		weight1.setValue(Integer.valueOf(weights[0]));
		weight2.setValue(Integer.valueOf(weights[1]));
		weight3.setValue(Integer.valueOf(weights[2]));
		weight4.setValue(Integer.valueOf(weights[3]));
		weight5.setValue(Integer.valueOf(weights[4]));
		
		srcFileBr.close();
	}
	@FXML
	private void initialize() {
	
		weight1.getItems().addAll(1,2,3,4,5);
		weight2.getItems().addAll(1,2,3,4,5);
		weight3.getItems().addAll(1,2,3,4,5);
		weight4.getItems().addAll(1,2,3,4,5);
		weight5.getItems().addAll(1,2,3,4,5);
		
		emotion1.getItems().addAll("positive","negative");
		emotion2.getItems().addAll("positive","negative");
		emotion3.getItems().addAll("positive","negative");
		emotion4.getItems().addAll("positive","negative");
		emotion5.getItems().addAll("positive","negative");
		
		weight1.setStyle("-fx-background-color: #c9fb89; -fx-text-fill: black;");
		weight2.setStyle("-fx-background-color: #c9fb89; -fx-text-fill: black;");
		weight3.setStyle("-fx-background-color: #c9fb89; -fx-text-fill: black;");
		weight4.setStyle("-fx-background-color: #c9fb89; -fx-text-fill: black;");
		weight5.setStyle("-fx-background-color: #c9fb89; -fx-text-fill: black;");
		
		emotion1.setStyle("-fx-background-color: #c9fb89; -fx-text-fill: black;");
		emotion2.setStyle("-fx-background-color: #c9fb89; -fx-text-fill: black;");
		emotion3.setStyle("-fx-background-color: #c9fb89; -fx-text-fill: black;");
		emotion4.setStyle("-fx-background-color: #c9fb89; -fx-text-fill: black;");
		emotion5.setStyle("-fx-background-color: #c9fb89; -fx-text-fill: black;");
		
	}
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	
	public Stage getDialogStage() {
        return this.dialogStage;
    }
	
	public boolean isScoreClicked() {
        return scoreClicked;
	}
	
	@FXML
    private void handleScore() throws IOException {
        scoreClicked = true;
        if(weight1.getValue()!=null&&weight2.getValue()!=null&&weight3.getValue()!=null&&weight4.getValue()!=null&&weight5.getValue()!=null&&
        		emotion1.getValue()!=null&&emotion2.getValue()!=null&&emotion3.getValue()!=null&&emotion4.getValue()!=null&&emotion5.getValue()!=null){
        	
                int[] option2=new int[5];
                int[] option1=new int[5];
                option2[0]=Integer.valueOf(weight1.getValue());
                option2[1]=Integer.valueOf(weight2.getValue());
                option2[2]=Integer.valueOf(weight3.getValue());
                option2[3]=Integer.valueOf(weight4.getValue());
                option2[4]=Integer.valueOf(weight5.getValue());
                
                option1[0]=(emotion1.getValue().equals("negative")?-1:1);
                option1[1]=(emotion2.getValue().equals("negative")?-1:1);
                option1[2]=(emotion3.getValue().equals("negative")?-1:1);
                option1[3]=(emotion4.getValue().equals("negative")?-1:1);
                option1[4]=(emotion5.getValue().equals("negative")?-1:1);
                System.out.println("emotion1-------------"+emotion1.getValue());
                System.out.println("emotion1"+option1[0]);
                
                grader.grade(grader.getKeyset(), option1, option2, grader.getuserlevel());
                System.out.println(grader.getScore());
                System.out.println(scoreofProduct);
                String score=String.format("%.1f", grader.getScore());
                //DecimalFormat df = new DecimalFormat("#.0"); 

                scoreLabel.setText(score);
                progress.setProgress(grader.getScore()/5);
//                dialogStage.showAndWait();
        	
        }else {
        	Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Warn");
            alert.setContentText("Please finish all the choices");

            alert.showAndWait();
		}

		
    }

	 public void setKey(Grader grader) {
        this.grader = grader;
        String[] KeyList=grader.getKeyset();
        System.out.println(KeyList[0]);
  
    }
	
	 
	 public static String[] keyReader(String name) throws IOException {
		 BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(name)));
		 String lineKey = in.readLine();
	     
		 String numbersline = in.readLine();
		 
		 String atAll = lineKey+" "+numbersline;
		 String[] array = atAll.split("\\s+");
		 return array;
		 
	 }
	
	 public static String Titlechooser() throws IOException {
		 BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream("Product.txt"),"GBK"));
//		 grader.get_name();
		 String line = in.readLine();
		return line;
	 }
	 
	 public static int[] integerTransformer(String[] array) {
			int[] times = new int[10];
			for(int i = 10; i < array.length; i++) {
				
				times[i-10] = Integer.parseInt(array[i]);
			}
			return times;
		}
		
		public static String[] keysGetter(String[] array) {
			String[] onlyKeys = new String[10];
			for(int i = 0; i < 10; i++) {
				onlyKeys[i] = array[i];
			}
			return onlyKeys;
		}
		
		public static int[] percentageComputer(int[] times) {
			double all = 0;
			for(int i = 0; i < 5; i++) {
				all += times[i];
			}
			int[] percents = new int[5];
			for(int i = 0; i < 5; i++) {
				percents[i] = (int) (times[i]*100/all);
			}
			percents[1] = 100 - percents[2]- percents[3]
							- percents[4]- percents[0];
			return percents;
		}
		
		
		public static Scene barChartBuilder(String appName, String[] words, int[] times) {
			CategoryAxis xAxis = new CategoryAxis();  
		      xAxis.setCategories(FXCollections.<String>observableArrayList(
		         Arrays.asList(words[0], words[1], words[2], words[3], words[4])));
		      xAxis.setLabel("Words");
		       
		      NumberAxis yAxis = new NumberAxis();
		      yAxis.setLabel("times");
	
		      //Creating the Bar chart
		      BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis); 
		      barChart.setTitle("Word Frequency");
		        
		      //Prepare XYChart.Series objects by setting data       
		      XYChart.Series<String, Number> series1 = new XYChart.Series<>();
		      series1.setName(appName);
		      series1.getData().add(new XYChart.Data<>(words[0], times[0]));
		      series1.getData().add(new XYChart.Data<>(words[1], times[1]));
		      series1.getData().add(new XYChart.Data<>(words[2], times[2]));
		      series1.getData().add(new XYChart.Data<>(words[3], times[3]));
		      series1.getData().add(new XYChart.Data<>(words[4], times[4]));

		        
		      
		      barChart.getData().addAll(series1);
		        
		      //Creating a Group object 
		      Group root = new Group(barChart);
		        
		      //Creating a scene object
		      Scene scene = new Scene(root, 500, 400);
		      return scene;
		}
		
		
		public static Scene pieChartBuilder(String appName, String[] words, int[] percents) {
			ObservableList<PieChart.Data> pieChartData = 
			         FXCollections.observableArrayList(
			            new PieChart.Data(words[0], percents[0]), 
			            new PieChart.Data(words[1], percents[1]), 
			            new PieChart.Data(words[2], percents[2]), 
			            new PieChart.Data(words[3], percents[3]),
			            new PieChart.Data(words[4], percents[4])
			         ); 
			      
			      //Creating a Pie chart 
			      PieChart pieChart = new PieChart(pieChartData); 
			      
			      
			      //Setting the title of the Pie chart 
			      pieChart.setTitle(appName); 
			      final Label caption = new Label("");
			        caption.setTextFill(Color.BLACK);
			        caption.setStyle("-fx-font: 24 arial;");

			        for (final PieChart.Data data : pieChart.getData()) {
			            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
			                    new EventHandler<MouseEvent>() {
			                        @Override public void handle(MouseEvent e) {
			                            caption.setTranslateX(e.getSceneX()-28);
			                            caption.setTranslateY(e.getSceneY()-7);
			                            caption.setText(String.valueOf(data.getPieValue()) 
			                                + "%");
			                        }
			                    });
			        }

			      //setting the direction to arrange the data 
			      pieChart.setClockwise(true); 
			       
			      //Setting the length of the label line 
			      pieChart.setLabelLineLength(50); 

			      //Setting the labels of the pie chart visible  
			      pieChart.setLabelsVisible(true); 
			     
 
			      //Setting the start angle of the pie chart  
			      pieChart.setStartAngle(180);     
			         
			      //Creating a Group object  
			      Group root = new Group(pieChart); 
			         
			      //Creating a scene object 
			      Scene scene = new Scene(root, 500, 400); 
			       ((Group) scene.getRoot()).getChildren().add(caption);
			      return scene;
		}

		public static Scene stagePie() throws IOException {
			String type = Titlechooser();
			String[] s1 = keyReader(type+"key"+".txt");
			for(String string : s1){
				//System.out.println(string);
			}
			int[] n1 = integerTransformer(s1);
			for(int times : n1){
				//System.out.println(times);
			}
	//		String[] s2 = keysGetter(s1);
			int[] n2 =percentageComputer(n1);
			Scene scene1 = pieChartBuilder(type, s1, n2);
			
			return scene1;
		}
		
		                                                                      
		public static Scene stageBar() throws IOException {
			String type = Titlechooser();
			String[] s1 = keyReader(type+"key"+".txt");
			int[] n1 = integerTransformer(s1);
			String[] s2 = keysGetter(s1);
		
			
			Scene scene2 = barChartBuilder(type, s2, n1);
			
			return scene2; 
		}
		@FXML
	    public void handleBarchart() throws IOException {
			Stage stage=new Stage();
			stage.setScene(stageBar());
			stage.show();
	    }
		
			
		@FXML
	    public void handlePiechart() throws IOException {
			Stage stage=new Stage();
			stage.setScene(stagePie());
			stage.show();
	    }
	 
		
	@FXML
		public void savingButton() throws IOException {
		if(weight1.getValue()!=null&&weight2.getValue()!=null&&weight3.getValue()!=null&&weight4.getValue()!=null&&weight5.getValue()!=null&&
        		emotion1.getValue()!=null&&emotion2.getValue()!=null&&emotion3.getValue()!=null&&emotion4.getValue()!=null&&emotion5.getValue()!=null){
        	

			String type = Titlechooser();
			String[] s1 = keyReader(type+"key"+".txt");
			System.out.println(grader);
			String[] filters = grader.getFilter();
			String Name = type+filters[0]+filters[1]+filters[2]+".txt";
			BufferedWriter Bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Name),"GBK"));
			Bw.write(s1[0]+" "+s1[1]+" "+s1[2]+" "+s1[3]+" "+s1[4]+"\r\n");
		
			String[] weight = new String[5];
			weight[0] = weight1.getValue()+"";
			weight[1] = weight2.getValue()+"";
			weight[2] = weight3.getValue()+"";
			weight[3] = weight4.getValue()+"";
			weight[4] = weight5.getValue()+"";
			System.out.println(weight);
			Bw.write(weight[0]+" "+weight[1]+" "+weight[2]+" "+weight[3]+" "+weight[4]+"\r\n");
		
			
			
			String[] emotion = new String[5];
			emotion[0] = emotion1.getValue()+"";
			emotion[1] = emotion2.getValue()+"";
			emotion[2] = emotion3.getValue()+"";
			emotion[3] = emotion4.getValue()+"";
			emotion[4] = emotion5.getValue()+"";
			System.out.println(emotion);
			
			Bw.write(emotion[0]+" "+emotion[1]+" "+emotion[2]+" "+emotion[3]+" "+emotion[4]+"\r\n");
			Bw.write(scoreLabel.getText());
			Bw.close();
        	
        }else {
        	Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Warn");
            alert.setContentText("Please finish all the choices");

            alert.showAndWait();
		}

		}
}
