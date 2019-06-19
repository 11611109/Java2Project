package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.ParseException;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;


import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import project.Grader;
import project.pashouji;


public class MainController {
	//implements Initializable
	
	private Main main;
	
	@FXML TreeView<String> treeView;
	@FXML ChoiceBox<String> timeChoice;
	@FXML ChoiceBox<String> starsChoice;
	@FXML ChoiceBox<String> numbersChoice;
	@FXML Button confirmbutton;
	
	@FXML ChoiceBox<String> score1;
	@FXML ChoiceBox<String> score2;
	@FXML ChoiceBox<String> score3;
	
	public MainController() {
		// TODO Auto-generated constructor stub
	}
	
	@FXML
    private void initialize() {
		TreeItem<String> root = new TreeItem<>("");
		
		
		TreeItem<String> cellphone = new TreeItem<>("CellPhone");
		TreeItem<String> nodeA = new TreeItem<>("iphoneX");
		TreeItem<String> nodeB = new TreeItem<>("Huawei");
		TreeItem<String> nodeC = new TreeItem<>("XiaoMi");
		cellphone.getChildren().add(nodeA);
		cellphone.getChildren().add(nodeB);
		cellphone.getChildren().add(nodeC);
		root.getChildren().add(cellphone);
		
		TreeItem<String> pc = new TreeItem<>("Computer");
		TreeItem<String> nodeD = new TreeItem<>("Dell");
		TreeItem<String> nodeE = new TreeItem<>("Mac");
		TreeItem<String> nodeF = new TreeItem<>("Hp");
		pc.getChildren().add(nodeD);
		pc.getChildren().add(nodeE);
		pc.getChildren().add(nodeF);
		root.getChildren().add(pc);
		
		
		TreeItem<String> earphone = new TreeItem<>("Earphone");
		TreeItem<String> nodeG = new TreeItem<>("Beats");
		TreeItem<String> nodeH = new TreeItem<>("Logitech");
		TreeItem<String> nodeI = new TreeItem<>("Sony");
		earphone.getChildren().add(nodeG);
		earphone.getChildren().add(nodeH);
		earphone.getChildren().add(nodeI);
		root.getChildren().add(earphone);
		
		treeView.setRoot(root);
		treeView.setShowRoot(false);
		
		timeChoice.getItems().addAll("In half year", "No limitation");
		starsChoice.getItems().addAll("Longer than 5","No limitation");
		numbersChoice.getItems().addAll("With picorvideo", "No limitation");


		timeChoice.setStyle("-fx-background-color: #c9fb89; -fx-text-fill: black;");
		starsChoice.setStyle("-fx-background-color: #c9fb89; -fx-text-fill: black;");
		numbersChoice.setStyle("-fx-background-color: #c9fb89; -fx-text-fill: black;");
        treeView.setStyle("-fx-background-color: #FFF0F5;");
     
    }

    public void setMain(Main main) {
        this.main = main;
        System.out.println(this+"set");
        System.out.println(main+" set");
        
    }
	
	@FXML
    private void handleConfirm() throws Exception {
		if(treeView.getSelectionModel().getSelectedItem()==null){
			Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Warn");
            alert.setContentText("Please choose one product in the left column");

            alert.showAndWait();
		}else {
			String product=treeView.getSelectionModel().getSelectedItem().getValue();
			
			String choice1=timeChoice.getValue();
			String choice2=starsChoice.getValue();
			String choice3=numbersChoice.getValue();
//			System.out.println(this+"before grader");
//			System.out.println(main+"before grader");
		
			if ((product!=null)&&(!product.equals("CellPhone"))&&(!product.equals("Computer"))&&(!product.equals("Earphone"))) {

				if(choice1!=null&&choice2!=null&&choice3!=null) {
					Grader newGrader=new Grader(product,choice1,choice2,choice3);
//					System.out.println(newGrader==null);
//					System.out.println(newGrader.get_name());
			//////////////		
					File file = new File("Product.txt");
					if(file.exists()) {
						file.delete();
					}
					BufferedWriter destFileBw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Product.txt"),"GBK"));
					destFileBw.write(product);
					destFileBw.close();
/////////////////////////////////////////
//					System.out.println(main+"after");
//					System.out.println(newGrader);
		            boolean scoreClicked = main.showGradeDialog(newGrader);
		            
		            if (scoreClicked) { 
		            }
					       
				}else {
					Alert alert = new Alert(AlertType.ERROR);
		            alert.setTitle("Error");
		            alert.setHeaderText("Warn");
		            alert.setContentText("Please finish all the choices");

		            alert.showAndWait();
				}

	        } else { // catches ANY exception
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Error");
	            alert.setHeaderText("Warn");
	            alert.setContentText("Please choose one product in the left column");

	            alert.showAndWait();
	        }
		}
		
		
		
		
//        Main.showGradeDialog();
    }
	
	
	
	/**
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws InterruptedException 
     */
    @FXML
    private void handleUpdate() throws IOException, ParseException, InterruptedException {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Update information");
        if(pashouji.shishi() == true) {
        alert.setHeaderText("Update started!");
        alert.setContentText("Update may take a long time, please wait... \n If you are bored with waiting, you could use this Website: http://4399.com");
        pashouji.papapa();
        pashouji.refresh();
        }else {
        	 alert.setHeaderText("The comments data is already the lastest!");
		}
        alert.showAndWait();
    }
    @FXML
    private void handleshowStatistics() {
    	Main.showStatistics();
    }

    
    @FXML
    private void SeriesBuilder() {
    	Stage stage = new Stage();
    	CategoryAxis xAxis = new CategoryAxis();  
	      xAxis.setCategories(FXCollections.<String>observableArrayList(
	         Arrays.asList("Cell Phone", "Computer", "Earphone")));
	      xAxis.setLabel(" ");
	  
	      NumberAxis yAxis = new NumberAxis();
	      yAxis.setLabel("total score");
	     
	      //Creating the Bar chart
	      StackedBarChart<String, Number> barChart = new StackedBarChart<>(xAxis, yAxis); 
	      barChart.setTitle("Categories Comparison");
	      barChart.setCategoryGap(80);  
	      //Prepare XYChart.Series objects by setting data       
	      XYChart.Series<String, Number> series1 = new XYChart.Series<>();
	      series1.setName("产品1");
	      series1.getData().add(new XYChart.Data<>("Cell Phone", 4.9/3));
	      series1.getData().add(new XYChart.Data<>("Computer", 3.9/3));
	      series1.getData().add(new XYChart.Data<>("Earphone", 3.9/3));
	     
	        
	      XYChart.Series<String, Number> series2 = new XYChart.Series<>();
	      series2.setName("产品2");
	      series2.getData().add(new XYChart.Data<>("Cell Phone", 5.0/3));
	      series2.getData().add(new XYChart.Data<>("Computer", 4.5/3));
	      series2.getData().add(new XYChart.Data<>("Earphone", 3.7/3));
	    
	      XYChart.Series<String, Number> series3 = new XYChart.Series<>();
	      series3.setName("产品3");
	      series3.getData().add(new XYChart.Data<>("Cell Phone", 4.9/3));
	      series3.getData().add(new XYChart.Data<>("Computer", 4.9/3));
	      series3.getData().add(new XYChart.Data<>("Earphone", 3.2/3));
	      
	         
	      
//	      XYChart.Series<String, Number> series5 = new XYChart.Series<>();
//	      series5.setName("");
//	      series5.getData().add(new XYChart.Data<>("Cell Phone", 0));
//	      series5.getData().add(new XYChart.Data<>("Computer", 0));
//	      series5.getData().add(new XYChart.Data<>("Earphone", 0));
	      
	      
	      //Setting the data to bar chart       
	      barChart.getData().addAll(series1, series2, series3);
	        
	      //Creating a Group object 
	      Group root = new Group(barChart);
	        
	      //Creating a scene object
	      Scene scene = new Scene(root, 500, 400);

	      //Setting title to the Stage
	      stage.setTitle("Bar Chart");
	        
	      //Adding scene to the stage
	      stage.setScene(scene);
	        
	      //Displaying the contents of the stage
	      stage.show();
    }
    
    
    @FXML
    private void PhoneChart() {
    	Stage stage = new Stage();
    	CategoryAxis xAxis = new CategoryAxis();  
	      xAxis.setCategories(FXCollections.<String>observableArrayList(
	         Arrays.asList("IphoneX","Huawei","XiaoMi")));
	      xAxis.setLabel("category");
	       
	      NumberAxis yAxis = new NumberAxis(0,5,0.5);
	      yAxis.setLabel("score");

	      //Creating the Bar chart
	      BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis); 
	      barChart.setTitle("Cell Phone Comparison");
	      barChart.setCategoryGap(80);  
	      //Prepare XYChart.Series objects by setting data       
	      XYChart.Series<String, Number> series1 = new XYChart.Series<>();
	      series1.setName("phones");
	      series1.getData().add(new XYChart.Data<>("IphoneX", 4.9));
	      series1.getData().add(new XYChart.Data<>("Huawei", 5.0));
	      series1.getData().add(new XYChart.Data<>("XiaoMi", 4.9));
	     

	        
	      
	      barChart.getData().addAll(series1);
	        
	      //Creating a Group object 
	      Group root = new Group(barChart);
	        
	      //Creating a scene object
	      Scene scene = new Scene(root, 500, 400);
	      stage.setScene(scene);
	        
	      //Displaying the contents of the stage
	      stage.show();
    }
	
    
    
    @FXML
    private void CpChart() {
    	Stage stage = new Stage();
    	CategoryAxis xAxis = new CategoryAxis();  
	      xAxis.setCategories(FXCollections.<String>observableArrayList(
	         Arrays.asList("Dell","Mac","Hp")));
	      xAxis.setLabel("category");
	       
	      NumberAxis yAxis = new NumberAxis(0,5,0.5);
	      yAxis.setLabel("score");

	      //Creating the Bar chart
	      BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis); 
	      barChart.setTitle("Computer Comparison");
	      barChart.setCategoryGap(80);  
	      //Prepare XYChart.Series objects by setting data       
	      XYChart.Series<String, Number> series1 = new XYChart.Series<>();
	      series1.setName("Computers");
	      series1.getData().add(new XYChart.Data<>("Dell", 3.9));
	      series1.getData().add(new XYChart.Data<>("Mac", 4.5));
	      series1.getData().add(new XYChart.Data<>("Hp", 4.9));
	     

	        
	      
	      barChart.getData().addAll(series1);
	        
	      //Creating a Group object 
	      Group root = new Group(barChart);
	        
	      //Creating a scene object
	      Scene scene = new Scene(root, 500, 400);
	      stage.setScene(scene);
	        
	      //Displaying the contents of the stage
	      stage.show();
    }
    
    @FXML
    private void handleHeatMap() throws IOException {
		// TODO Auto-generated method stub
    	Main.showHeatMap();
	}
    
    @FXML
    private void EpChart() {
    	Stage stage = new Stage();
    	CategoryAxis xAxis = new CategoryAxis();  
	      xAxis.setCategories(FXCollections.<String>observableArrayList(
	         Arrays.asList("Beats","Logitech","Sony")));
	      xAxis.setLabel("category");
	       
	      NumberAxis yAxis = new NumberAxis(0,5,0.5);
	      yAxis.setLabel("score");

	      //Creating the Bar chart
	      BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis); 
	      barChart.setTitle("Earphone Comparison");
	      barChart.setCategoryGap(80);  
	      //Prepare XYChart.Series objects by setting data       
	      XYChart.Series<String, Number> series1 = new XYChart.Series<>();
	      series1.setName("Earphones");
	      series1.getData().add(new XYChart.Data<>("Beats", 3.9));
	      series1.getData().add(new XYChart.Data<>("Logitech", 3.7));
	      series1.getData().add(new XYChart.Data<>("Sony", 4.2));
	     

	        
	      
	      barChart.getData().addAll(series1);
	        
	      //Creating a Group object 
	      Group root = new Group(barChart);
	        
	      //Creating a scene object
	      Scene scene = new Scene(root, 500, 400);
	      stage.setScene(scene);
	        
	      //Displaying the contents of the stage
	      stage.show();
    }
	
}
