package application;

import java.util.Optional;
import java.util.ResourceBundle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ScoringResult_Controller implements Initializable{

    @FXML
    private Button save,turnback;

    @FXML
    TableView<Grades> resulttable;

    @FXML
    private TextField directorychange;
    
    @FXML
    TableColumn<Grades,String> idcolumn,scorecolumn;
    
    ObservableList<Grades> data;
    
    String pic;
    
    String examinationpic;
    
    @FXML
    void ontrunbackbutton(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage .setScene(scene);
			stage.setTitle("メインメニュー");
			stage.show();
			Scene scene2 = ((Node) event.getSource()).getScene();
	    	Window window = scene2.getWindow();
	    	window.hide();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    @FXML
    void sava_onClick(ActionEvent event) {
    	CopyFile();
    }
    private void CopyFile() {//マークシート画像ファイルを指定した保存先にコピー
    	try {
    		String tduscoringdata = "C:/TDUScoringdata";
    		File tduscoringdatafile = new File(tduscoringdata);
    		if(tduscoringdatafile.exists()) {
    			System.out.println("TDUScoringdataは存在します");
    		}else {
    			File newdir = new File(tduscoringdata);
        		newdir.mkdir();
        		System.out.println("TDUScoringdataを作成");
    		}
    		String yearpic = tduscoringdata + "/" + UploadScoring_Controller.yearbox;
    		File yearfile = new File(yearpic);
    		if(yearfile.exists()) {
    			System.out.println("開講年度は存在します");
    		}else {
    			File newdir = new File(yearpic);
        		newdir.mkdir();
        		System.out.println("開講年度を作成");
    		}
    		String subjectpic = yearpic + "/" + UploadScoring_Controller.subjectbox;
    		File subjectfile = new File(subjectpic);
    		if(subjectfile.exists()) {
    			System.out.println("コースは存在します");
    		}else {
    			File newdir = new File(subjectpic);
        		newdir.mkdir();
        		System.out.println("コースを作成");
    		}
        	examinationpic = subjectpic + "/" + UploadScoring_Controller.examinationbox;
        	File examinationfile = new File(examinationpic);
    		if(examinationfile.exists()) {
    			System.out.println("試験名は存在します");
    			Alert alert = new Alert(AlertType.CONFIRMATION);
        		alert.setHeaderText("「" + examinationpic + "」 が既に存在します。ファイルの有無を確認してください");
        		alert.setContentText("上書きしますか?");
        		Optional<ButtonType> result = alert.showAndWait();
        		if(result.get() != ButtonType.OK) {
        			Alert alert2 = new Alert(AlertType.INFORMATION);
            		alert2.setHeaderText("最初からやり直してください");
            		alert2.showAndWait();
            		Stage stage = new Stage();
            		FXMLLoader loader = new FXMLLoader();
        			loader = new FXMLLoader(getClass().getResource("UploadScoring.fxml"));
        			Scene scene = new Scene(loader.load());
        			stage.setScene(scene);
        			stage.setTitle("採点");
        			stage.show();
        			Stage stage2 = (Stage)save.getScene().getWindow();
        			stage2.close();
        			return;
        		}
    		}else {
    			File newdir = new File(examinationpic);
        		newdir.mkdir();
        		System.out.println("試験名を作成");
    		}
    		//File_Check();
    		for(int i = 0;i < UploadScoring_Controller.observableArrayList1.size();i++) {
    			FileInputStream source = new FileInputStream(UploadScoring_Controller.observableArrayList1.get(i).getOriginalPic());
    			FileOutputStream target = new FileOutputStream(UploadScoring_Controller.observableArrayList1.get(i).getPic());
    			byte buf[] = new byte[256];
    			int len;
    			while((len = source.read(buf)) != -1) {
    				target.write(buf,0,len);
    			}
    			target.flush();
    			target.close();
    			source.close();
     		}
    		System.out.println("コピー完了しました");
    		System.out.println("マークシート画像ファイルコピー完了!");
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setHeaderText("マークシート画像ファイルコピー完了!");
    		alert.setContentText("マークシート画像は 　「" + examinationpic + "」　にコピーされました。");
    		alert.showAndWait();
    		Alert alert2 = new Alert(AlertType.INFORMATION);
    		alert2.setHeaderText("次に採点結果の表をCSVで保存してください");
    		alert2.showAndWait();
    		Csv_Save();
    	}catch(IOException ex) {
    		ex.printStackTrace();
    		pic = UploadScoring_Controller.observableArrayList1.get(0).getOriginalPic();
    		if(pic != null) {
    			Alert alert = new Alert(AlertType.ERROR);
        		alert.setHeaderText(UploadScoring_Controller.observableArrayList1.get(0).getOriginalPic() + "のパスが見つからないため保存先パスにコピーできません");
        		alert.showAndWait();
        		return;
    		}else {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setHeaderText("CSV保存・マークシート画像ファイルコピーに失敗しました");
    			alert.showAndWait();
    			return;
    		}
    	}
    }
    private void Csv_Save(){//CSVファイルを保存
    	Stage stage = new Stage();
    	FileChooser fileChooser = new FileChooser();
    	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)","*.csv");
   		fileChooser.getExtensionFilters().add(extFilter);
   		fileChooser.setInitialDirectory(new File(examinationpic + "/"));
   		File file = fileChooser.showSaveDialog(stage);
    	try {
    		FileWriter w = new FileWriter(file.getPath(),false);
    		ObservableList<Grades> grade = resulttable.getItems();
    		String write = "";
    		for(int i = 0;i < grade.size();i++) {
    			write += grade.get(i).writeList();
    		}
    		w.write(write);
    		w.close();
    		Alert alert4 = new Alert(AlertType.INFORMATION);
    		alert4.setHeaderText("CSV保存完了!");
    		alert4.setContentText("「" + file.getPath() + "」　に保存されました");
    		alert4.showAndWait();
    		Parent root = FXMLLoader.load(getClass().getResource("UploadAnalysis.fxml"));
			Scene scene = new Scene(root);
			stage .setScene(scene);
			stage.setTitle("分析");
			stage.show();
			Stage stage2 = (Stage)save.getScene().getWindow();
			stage2.close();
    	}catch(IOException ex) {
    		ex.printStackTrace();
    	}
    }
    private void File_Check() {//画像ファイルが既に存在するのか確認
    	try {
    		for(int i = 0;i < UploadScoring_Controller.observableArrayList1.size();i++) {
        		String id = UploadScoring_Controller.observableArrayList1.get(i).getPic();
    			File file = new File(id);
    			if(file.exists()) {
    				System.out.println(id.substring(id.length()-11) + "が存在します");
    				Alert alert = new Alert(AlertType.CONFIRMATION);
            		alert.setHeaderText(id.substring(id.length()-11) + "　が既に存在しますが上書きしますか?");
            		Optional<ButtonType> result = alert.showAndWait();
            		if(result.get() != ButtonType.OK) {
            			Alert alert2 = new Alert(AlertType.INFORMATION);
                		alert2.setHeaderText("最初からやり直してください");
                		alert2.showAndWait();
                		Stage stage = new Stage();
                		FXMLLoader loader = new FXMLLoader();
            			loader = new FXMLLoader(getClass().getResource("UploadScoring.fxml"));
            			Scene scene = new Scene(loader.load());
            			stage.setScene(scene);
            			stage.setTitle("採点");
            			stage.show();
            			Stage stage2 = (Stage)save.getScene().getWindow();
            			stage2.close();
            			return;
            		}
    			}
    		}
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    public TableView<Grades> getResultTable(){
    	return resulttable;
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO 自動生成されたメソッド・スタブ
		resulttable.getColumns().clear();
		TableColumn<Grades,String> id = new TableColumn<Grades,String>("学籍番号");
    	TableColumn<Grades,String> score = new TableColumn<Grades,String>("点数");
    	TableColumn<Grades,String> directory = new TableColumn<Grades,String>("ディレクトリ");
    	id.setCellValueFactory(c ->{
    		Grades o = (Grades)c.getValue();
    		return new SimpleStringProperty(o.getId());
    	});
    	
    	score.setCellValueFactory(c ->{
    		Grades o = (Grades)c.getValue();
    		return new SimpleStringProperty(String.valueOf(o.getSum()));
    	});
    	directory.setCellValueFactory(c ->{
    		Grades o = (Grades)c.getValue();
    		return new SimpleStringProperty(o.getPic());
    	});
    	resulttable.getColumns().addAll(id,score,directory);
	}
}

