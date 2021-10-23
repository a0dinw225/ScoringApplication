package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class UploadAnalysis_Controller {

    @FXML
    private TableView<GradesAnalysis> csvtable;

    @FXML
    private TextField high;

    @FXML
    private TextField lowest;

    @FXML
    private TextField average;

    @FXML
    private TextField s_score;

    @FXML
    private TextField a_score;

    @FXML
    private TextField b_score;

    @FXML
    private TextField c_score;

    @FXML
    private TextField d_score;

    @FXML
    private TableView<AnswerRate> answerrate;

    @FXML
    private TextField totalpeople;

    @FXML
    private Button turnback;

    @FXML
    private Button selectfile;

    @FXML
    private TextField directorypath;

    @FXML
    private Button filedelete;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private PieChart pieChart;

    private ObservableList<GradesAnalysis> observableArrayList = FXCollections.observableArrayList();//CSVファイル
    private ObservableList<AnswerRate> observableArrayList2 = FXCollections.observableArrayList();//正答率

    @FXML
    private void FileDeleteButton(ActionEvent event) {
    	if(observableArrayList.size() == 0) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("データがありません");
    		//alert.setContentText("データがありません");
    		alert.showAndWait();
    	}else {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setHeaderText("データを消去しますか?");
    		//alert.setContentText("データを削除しますか?");
    		Optional<ButtonType> result = alert.showAndWait();
    		if(result.get() == ButtonType.OK) {
    			ObservableList<TableColumn<GradesAnalysis, ? extends Object>>  columns = csvtable.getColumns();
    			columns.clear();
    			observableArrayList.clear();
    			observableArrayList2.clear();
            	directorypath.clear();
            	high.clear();
            	lowest.clear();
            	average.clear();
            	s_score.clear();
            	a_score.clear();
            	b_score.clear();
            	c_score.clear();
            	d_score.clear();
            	totalpeople.clear();
            	pieChart.getData().clear();
            	barChart.getData().clear();
    		}
    	}

    }

    @FXML
    private void SelectFileButton(ActionEvent event) {
    	ObservableList<TableColumn<GradesAnalysis, ? extends Object>> columns = csvtable.getColumns();
    	columns.clear();
    	observableArrayList.clear();
    	observableArrayList2.clear();
    	csvtable.setItems(observableArrayList);
    	Stage stage = new Stage();
    	FileChooser fileChooser = new FileChooser();
    	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)","*.csv");
   		fileChooser.getExtensionFilters().add(extFilter);
   		File tduscoringdata = new File("C:/TDUScoringdata");
   		if(tduscoringdata.exists()) {

   		}else {
   			Alert alert = new Alert(AlertType.ERROR);
   			alert.setHeaderText("保存されているCSVファイルが存在しません");
   			alert.setContentText("先に採点を行ってください!!");
   			alert.showAndWait();
   			return;
   		}
   		fileChooser.setInitialDirectory(new File("C:/TDUScoringdata"));
    	File file = fileChooser.showOpenDialog(stage);
    	try {
    		if(file != null) {
    			String path = file.getPath();
    			directorypath.setEditable(false);
           		directorypath.setText(path);
    		}else {
    			return;
    		}
    		observableArrayList.addAll(load(directorypath.getText()));
    		TableColumn<GradesAnalysis,?> column1 = new TableColumn<GradesAnalysis,String>("学籍番号");
       		TableColumn<GradesAnalysis,?> column2 = new TableColumn<GradesAnalysis,String>("点数");
       		TableColumn<GradesAnalysis,?> column3 = new TableColumn<GradesAnalysis,String>("ディレクトリパス");

        	column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        	column2.setCellValueFactory(new PropertyValueFactory<>("score"));
        	column3.setCellValueFactory(new PropertyValueFactory<>("pic"));
       		columns.addAll(column1,column2,column3);

        	int size = observableArrayList.get(0).getAdjusts().size();

        	for(int i = 0;i < size;i++) {
       			TableColumn<GradesAnalysis,String> column = new TableColumn<GradesAnalysis,String>("Q" + (i + 1));
       			columns.add(column);
       			final int k = i;
        		column.setCellValueFactory(c ->{
       				GradesAnalysis o = (GradesAnalysis)c.getValue();
       				return new SimpleStringProperty(o.getAdjusts().get(k).trim());
       			});
        		
       		}
        	MaxScore();
        	MinScore();
        	AverageScore();
        	Gpa();
        	ScoreRate();

    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    private void MaxScore() {
    	Integer maxScore = 0;
    	for(int i = 0;i < observableArrayList.size();i++) {
    		Integer j = Integer.parseInt( observableArrayList.get(i).getScore());
    		if( maxScore <= j ) {
    			maxScore = j;
    		}
    	}
    	high.setText(String.valueOf(maxScore) + "点");
    }

    private void MinScore() {
    	Integer minScore = 100;
    	for(int i = 0;i < observableArrayList.size();i++) {
    		Integer j = Integer.parseInt( observableArrayList.get(i).getScore());
    		if( minScore >= j ) {
    			minScore = j;
    		}
    	}
    	lowest.setText(String.valueOf(minScore) + "点");
    }

    private void AverageScore() {
    	Integer sum = 0;
    	for(int i = 0;i < observableArrayList.size();i++) {
    		sum += Integer.parseInt( observableArrayList.get(i).getScore());
    	}
    	Integer averageScore = sum / observableArrayList.size();
    	average.setText(String.valueOf(averageScore) + "点");
    }

    private void Gpa(){
    	Integer s = 0;
    	Integer a = 0;
    	Integer b = 0;
    	Integer c = 0;
    	Integer d = 0;
    	Integer j = 0;
    	for(int i = 0;i < observableArrayList.size();i++) {
    		j = Integer.parseInt( observableArrayList.get(i).getScore());
    		if(90 <= j && 100 >= j) {
    			s++;
    		}else if(80 <= j && j <= 89) {
    			a++;
    		}else if(70 <= j && j <= 79) {
    			b++;
    		}else if(60 <= j && j <= 69) {
    			c++;
    		}else if(0 <= j && j <= 59) {
    			d++;
    		}
    	}
    	s_score.setText(s + "人");
    	a_score.setText(a + "人");
    	b_score.setText(b + "人");
    	c_score.setText(c + "人");
    	d_score.setText(d + "人");
    	totalpeople.setText(observableArrayList.size() + "人");
    	javafx.scene.chart.PieChart.Data sp = new javafx.scene.chart.PieChart.Data("S( " +  100 * s / observableArrayList.size() + "% )", s);
    	javafx.scene.chart.PieChart.Data ap = new javafx.scene.chart.PieChart.Data("A( " +  100 * a / observableArrayList.size() + "% )", a);
    	javafx.scene.chart.PieChart.Data bp = new javafx.scene.chart.PieChart.Data("B( " +  100 * b / observableArrayList.size() + "% )", b);
    	javafx.scene.chart.PieChart.Data cp = new javafx.scene.chart.PieChart.Data("C( " +  100 * c / observableArrayList.size() + "% )", c);
    	javafx.scene.chart.PieChart.Data dp = new javafx.scene.chart.PieChart.Data("D( " +  100 * d / observableArrayList.size() + "% )", d);
    	pieChart.getData().clear();
		pieChart.getData().addAll(sp,ap,bp,cp,dp);
    }
    private void ScoreRate(){//正答率
    	observableArrayList2.clear();
    	answerrate.getColumns().clear();
		XYChart.Series<String, Number> series = new XYChart.Series<>(); 
    	ObservableList<TableColumn<AnswerRate, ? extends Object>> ratecolumns = answerrate.getColumns();
    	GradesAnalysis grade = observableArrayList.get(0);
    	int adjustssize = grade.getAdjusts().size();//0番目の問題数
    	List<String>[] list = new List[adjustssize];
    	System.out.println(adjustssize);
    	for(int a = 0;a < adjustssize;a++) {
    		list[a] = new ArrayList<String>();//問題の数だけlistインスタンス生成
    		System.out.println("list[" + (a + 1) + "]:");
    	}
    	String ratelink = "";
    	for(int i = 0;i < adjustssize;i++) {
    		for (int j = 0; j < observableArrayList.size(); j++) {
    			GradesAnalysis grades = observableArrayList.get(j);
    			if(grades.getAdjusts().get(i).equals("〇")) {
        			list[i].add("〇");
        			System.out.print("〇,");
        		}
    		}
    		System.out.print("Q" + (i + 1) + ":" + list[i].size() + ";");
    		String rate = String.valueOf((100 * list[i].size()) / observableArrayList.size());
    		ratelink += rate + "%,";
			System.out.println(list[i].size() + " / " + observableArrayList.size() + " = " + rate);


    		System.out.print("Q" + (i + 1) + ":" + list[i].size() + ";");
    		Integer a = (100 * list[i].size()) / observableArrayList.size();
			System.out.println(list[i].size() + " / " + observableArrayList.size() + " = " + a);
    	}
    	String[] split = ratelink.split(",",-1);
		List<String> asList = Arrays.asList(split);
		List<String> t = new ArrayList<String>();
		t.addAll(asList.subList(0, asList.size()-1));
		AnswerRate gradesrate = new AnswerRate(t);
		System.out.println("gradesrate:" + gradesrate.getRates());
		observableArrayList2.add(gradesrate);
		System.out.println("t = " + t);
    	System.out.println(observableArrayList2);
    	System.out.println("observableArrayList2の数:" + observableArrayList2.size());
    	System.out.println();

		int size = observableArrayList2.get(0).getRates().size();
		System.out.println("問題数:" + size);
		answerrate.setItems(observableArrayList2);
		for (int i = 0; i < t.size(); i++) {
			String rate = t.get(i).replace("%", "");
			Data<String, Number> e = new XYChart.Data<>("Q" + (i + 1), Integer.parseInt(rate));
			series.getData().add(e); 
		}

		barChart.getData().clear();
		barChart.getData().add(series);
		Platform.runLater(() -> {
			Stage stage = (Stage) barChart.getScene().getWindow();
			stage.setWidth(stage.getWidth() + 1);
		});
    	for(int i = 0;i < size;i++) {
    		TableColumn<AnswerRate,String> ratecolumn = new TableColumn<AnswerRate,String>("Q" + (i + 1));
   			ratecolumns.add(ratecolumn);
   			final int k = i;
   			System.out.println("開始");
   			System.out.println("実行中:" + gradesrate.getRates().get(k).trim());
    		ratecolumn.setCellValueFactory(c ->{
   				return new SimpleStringProperty(gradesrate.getRates().get(k).trim());
   			});
    		System.out.println("完了");
   		}

    }
    @FXML
    private void turnbackbutton(ActionEvent event) {
    	try {
    		FXMLLoader  loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
    		Parent parent = loader.load();
    		Stage stage = new Stage();
    		Scene scene = new Scene(parent);
    		stage.setScene(scene);
    		stage.setTitle("メインメニュー");
    		stage.show();
    		Scene scene2 = ((Node) event.getSource()).getScene();
	    	Window window = scene2.getWindow();
	    	window.hide();
    	}catch(IOException ex) {
    		ex.printStackTrace();
    	}
    }

    private ObservableList<GradesAnalysis> load(String file){
    	ObservableList<GradesAnalysis> observableArrayList = FXCollections.observableArrayList();
    	try {
    		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(file)));
    		String line;
    		while((line = bufferedReader.readLine()) != null) {
    			System.out.println(line);
    			String[] split = line.split(",",-1);
    			if("".contentEquals(split[0])) {
    				continue;
    			}
    			List<String> asList = Arrays.asList(split);
    			System.out.println("asList.size():" + asList.size());
    			List<String> t = new ArrayList<String>();
    			t.addAll(asList.subList(3, asList.size()-1));
    			GradesAnalysis grades = new GradesAnalysis(split[0],split[1],split[2],t);
    			observableArrayList.add(grades);
    		}
    		bufferedReader.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	System.out.println(observableArrayList);
    	return observableArrayList;
    }

}
