package application;


import java.io.*;
import java.net.URL;
import java.util.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.*;
import javafx.stage.*;
import javafx.fxml.*;

public class UploadScoring_Controller implements Initializable{
	Stage stage;

    @FXML
    private ResourceBundle resources;

    @FXML
    Button scoring,trunback,modelanswer,studentanswer,b3,modeldelete,studentdelete;
    
    @FXML
    private TextField modelpath,studentpath;
    
    private ScoringResult_Controller scoringResult_Controller;

    @FXML
    TableView<Grades> modeltable,studenttable,resulttable;
    
    @FXML
    ComboBox<String> year;

    @FXML
    ComboBox<String> subject;
    
    @FXML
    private ComboBox<String> examination;

    static Object yearbox;
    static Object subjectbox;
    static Object examinationbox;
    
    @FXML
    private TableColumn<Grades, String> numbercolumn,answercolumn,pointcolumn;
    
    static ObservableList<Grades> observableArrayList1 = FXCollections.observableArrayList();//学生解答
	ObservableList<Grades> observableArrayList2 = FXCollections.observableArrayList();//模範解答
    
	//String scoreFile;
    
   	public void initialize(Stage stage) {
	}
   
    @FXML
    void goscoring(ActionEvent event) {//採点ボタン
    	if(observableArrayList1.size() == 0 || observableArrayList2.size() == 0) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("");
    		alert.setHeaderText("エラー");
    		alert.setContentText("データが不足しています");
    		alert.showAndWait();
    	}else {
    		try {
    			Stage stage = new Stage();
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("ScoringResult.fxml"));
		        Parent root = loader.load();
		        scoringResult_Controller = loader.getController();
    			Scene scene = new Scene(root);
    			stage.setTitle("採点結果");
    			stage.setScene(scene);
    			
    	    	int size = observableArrayList1.size();//学生
    	    	List<Grades> temp = new ArrayList<Grades>();
    	    	for(int i = 0;i < size;i++) {
    	    		int sum = 0;
    	    		Grades grade = observableArrayList1.get(i);
    	    		if(temp.contains(grade)) {
    	    			Alert alert = new Alert(AlertType.ERROR);
    	    			alert.setTitle("採点エラー");
    	    			alert.setHeaderText("番号　" + (i + 1) + "行目でエラー:" + grade.getId());
    	    			alert.show();
    	    			return;
    	    		}
    	    		temp.add(grade);
    	    		
    	    		List<String> answers = grade.getAnswers();//学生の解答
    	    		//System.out.println(answers.size());
    	    		for(int j = 0;j < answers.size();j++) {
    	    			String trueAnswer = observableArrayList2.get(j).getAnswer();//模範解答の正答
    	    			if(answers.get(j).equals(trueAnswer)) {
    	    				grade.getAdjusts().add("〇");
    	    				System.out.println(observableArrayList2.get(j).getScore());
    		    			sum += Integer.parseInt(observableArrayList2.get(j).getScore());
    	    			}else {
    	    				grade.getAdjusts().add("×");
    	    			}
    	    			
    	    		}
    	    		System.out.println(grade.getAdjusts());
    	    		grade.setSum(sum);
    	    		System.out.println(sum);
    	    	}
    	    	scoringResult_Controller.getResultTable().setItems(observableArrayList1);
    	    	ObservableList<TableColumn<Grades, ? extends Object>> columns = scoringResult_Controller.getResultTable().getColumns();
    	    	int size2 = scoringResult_Controller.getResultTable().getItems().get(0).getAdjusts().size();
    	    	for(int i = 0;i < size2;i++) {//各問の正解の表示
    	   			TableColumn<Grades,String> column = new TableColumn<Grades,String>("Q" + (i + 1));
    	   			columns.add(column);
    	   			final int k = i;
    	    		column.setCellValueFactory(c ->{
    	   				Grades o = (Grades)c.getValue();
    	   				return new SimpleStringProperty(o.getAdjusts().get(k).trim());
    	   			});
    	   		}
    	    	Alert alert = new Alert(AlertType.INFORMATION);
    	    	alert.setHeaderText("採点成功!");
    	    	alert.showAndWait();
    			stage.show();
    			Scene scene2 = ((Node) event.getSource()).getScene();
    	    	Window window = scene2.getWindow();
    	    	window.hide();
    			
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    @FXML
    void ontrunbackbutton(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
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
    private void init1() {//学生解答ボタン
    	ObservableList<TableColumn<Grades, ? extends Object>> columns = studenttable.getColumns();
    	observableArrayList1.clear();
    	columns.clear();
    	studenttable.setItems(observableArrayList1);
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("学生解答データ");
    	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)","*.csv");
   		fileChooser.getExtensionFilters().add(extFilter);
   		
   		if((year.getValue() == null) && (subject.getValue() == null) && subject.getValue() == null) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("学生解答データを読み込む前に開講年度、コース名、試験名を指定してください");
    		alert.showAndWait();
    		observableArrayList1.clear();
    		studentpath.clear();
    		return;
    	}else if((year.getValue() == null) && (subject.getValue() == null)){
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("学生解答データを読み込む前に開講年度とコース名を指定してください");
    		alert.showAndWait();
    		observableArrayList1.clear();
    		studentpath.clear();
    		return;
    	}else if((year.getValue() == null) && (examination.getValue() == null)){
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("学生解答データを読み込む前に開講年度と試験名を指定してください");
    		alert.showAndWait();
    		observableArrayList1.clear();
    		studentpath.clear();
    		return;
    	}else if((subject.getValue() == null) && (examination.getValue() == null)){
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("学生解答データを読み込む前にコース名と試験名を指定してください");
    		alert.showAndWait();
    		observableArrayList1.clear();
    		studentpath.clear();
    		return;
    	}else if(year.getValue() == null){
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("学生解答データを読み込む前に開講年度を指定してください");
    		alert.showAndWait();
    		observableArrayList1.clear();
    		studentpath.clear();
    		return;
    	}else if(subject.getValue() == null) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("学生解答データを読み込む前にコース名を指定してください");
    		alert.showAndWait();
    		observableArrayList1.clear();
    		studentpath.clear();
    		return;
    	}else if(examination.getValue() == null){
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("学生解答データを読み込む前に試験名を指定してください");
    		alert.showAndWait();
    		observableArrayList1.clear();
    		studentpath.clear();
    		return;
    	}
   		File file = fileChooser.showOpenDialog(stage);
   	  
   		if(file != null) {
    		String path = file.getPath();
   			studentpath.setEditable(false);
       		studentpath.setText(path);
   		}
   		observableArrayList1.addAll(load(studentpath.getText()));
	   	TableColumn<Grades,?> column1 = new TableColumn<Grades,String>("番号");
	  	TableColumn<Grades,?> column2 = new TableColumn<Grades,String>("学籍番号");
	    		
	   	column1.setCellValueFactory(new PropertyValueFactory<>("number"));
	   	column2.setCellValueFactory(new PropertyValueFactory<>("id"));
		columns.addAll(column1,column2);
	    		
	    int size = observableArrayList1.get(0).getAnswers().size();
	    for(int i = 0;i < size -3;i++) {
	   		TableColumn<Grades,String> column = new TableColumn<Grades,String>("Q" + (i + 1));
	   		columns.add(column);
	   		final int k = i;
	    	column.setCellValueFactory(c ->{
	   			Grades o = (Grades)c.getValue();
	   			return new SimpleStringProperty(o.getAnswers().get(k).trim());//javaのtrim()は文字列から前後の空白を除去する。
	   		});                                                               //例
	   	}                                                                     //String str = " あなた　わたし　かれ "
	    System.out.println("year:" + year.getValue());                        //System.out.println(str.trim()); 結果:「あなた　わたし　かれ」 
	    System.out.println("subject:" + subject.getValue());
    }                                                                             	           
    @FXML
    private void init2() {//模範解答ボタン
    	ObservableList<TableColumn<Grades, ? extends Object>>  columns = modeltable.getColumns();
  
    	modeltable.setItems(observableArrayList2);
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("模範解答データ");
   		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files(*.csv", "*.csv");
    	fileChooser.getExtensionFilters().add(extFilter);
    	File file = fileChooser.showOpenDialog(stage);
    	if(file != null) {
    		String path  = file.getPath();
    		modelpath.setText(path);
    	}else {
   			return;
    	}
    	observableArrayList2.addAll(Model_load(modelpath.getText()));
    	System.out.println(observableArrayList2);
    		
    	numbercolumn.setCellValueFactory(new PropertyValueFactory<Grades,String>("number"));
    	answercolumn.setCellValueFactory(new PropertyValueFactory<Grades,String>("answer"));
   		pointcolumn.setCellValueFactory(new PropertyValueFactory<Grades,String>("score"));
    		
   		modeltable.setItems(observableArrayList2);
    }
    @FXML
    private void Model_onClickDelete(ActionEvent event) {
    	if(observableArrayList2.size() == 0) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("模範解答データがありません");
    		alert.showAndWait();
    	}else {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setHeaderText("模範解答データを消去しますか?");
    		Optional<ButtonType> result = alert.showAndWait();
    		if(result.get() == ButtonType.OK) {
    			ObservableList<TableColumn<Grades, ? extends Object>>  columns = modeltable.getColumns();
    			observableArrayList2.clear();
    			modelpath.clear();
    	    	columns.clear();
    		}
    	}
    }

    @FXML
    private void Student_onClickDelete(ActionEvent event) {
    	if(observableArrayList1.size() == 0) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("学生解答データがありません");
    		alert.showAndWait();
    	}else {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setHeaderText("学生解答データを消去しますか?");
    		Optional<ButtonType> result = alert.showAndWait();
    		if(result.get() == ButtonType.OK) {
    			ObservableList<TableColumn<Grades, ? extends Object>>  columns = studenttable.getColumns();
    			observableArrayList1.clear();
    			studentpath.clear();
    	    	columns.clear();
    		}
    	}
    }
    
    private ObservableList<Grades> load(String file){
    	ObservableList<Grades> observableArrayList = FXCollections.observableArrayList();
    	try {
    		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(file)));
    		String line = bufferedReader.readLine();
    		while((line = bufferedReader.readLine()) != null) {
    			System.out.println(line);
    			String[] split = line.split(",",-1);
    			if("".contentEquals(split[0])) {
    				continue;
    			}
    			List<String> asList = Arrays.asList(split);
    			List<String> t = new ArrayList<String>();
    			t.addAll(asList.subList(2, asList.size()-4));
    			
    			String id = split[1].replace("'","");//"'"を削除する
    			id = convert(id);
    			Grades idgrade = new Grades();
    			idgrade.setId(id);//AJ変換した学籍番号をセット
    			Grades grades = new Grades(split[0],id,t);
    			grades.setOriginalPic(split[split.length - 3]);
    			String s = split[split.length - 3];
    			//String remove = s.substring(0,s.length()-23);//ファイルまでのディレクトリを取得
    			yearbox = year.getValue();
    			subjectbox = subject.getValue();
    			examinationbox = examination.getValue();
    			String pic =  "C:/TDUScoringdata/" + yearbox + "/" + subjectbox + "/" + examinationbox + "/"/*remove.substring(2)*/;
    			String idpic = pic + idgrade.getId() + ".jpg";//ファイル名を学籍番号に変換
    			grades.setPic(idpic);
    			observableArrayList.add(grades);
    		}
    		bufferedReader.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	System.out.println(observableArrayList);
    	return observableArrayList;
    }
    
    String convert(String s){//02からAJに変換
        String ss = "";
        String[] strArray = s.split("");
        strArray[2] = CourseCode.values()[Integer.parseInt(strArray[2])].toString();
        strArray[3] = CourseCode.values()[Integer.parseInt(strArray[3])].toString();
        for(int i = 0;i < strArray.length;i++){
            ss = ss + strArray[i];
        }
        return ss;
    }
    
    private ObservableList<Grades> Model_load(String file){
    	ObservableList<Grades> observableArrayList = FXCollections.observableArrayList();
    	try {
    		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(file)));
    		String line;
    		while((line = bufferedReader.readLine()) != null) {
    			System.out.println(line);
    			String[] split = line.split(",",-1);
    			if("".contentEquals(split[0])) {
    				continue;
    			}
    			Grades grades = new Grades(split[0],split[1],split[2]);
    			observableArrayList.add(grades);
    		}
    		bufferedReader.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	System.out.println(observableArrayList);
    	return observableArrayList;
    }

   @FXML
    void initialize() {
        assert modelanswer != null : "fx:id=\"modelanswer\" was not injected: check your FXML file 'UploadScoring.fxml'.";
        assert studentanswer != null : "fx:id=\"studentanswer\" was not injected: check your FXML file 'UploadScoring.fxml'.";
        assert scoring != null : "fx:id=\"scoring\" was not injected: check your FXML file 'UploadScoring.fxml'.";
        assert trunback != null : "fx:id=\"trunback\" was not injected: check your FXML file 'UploadScoring.fxml'.";

    }

   @Override
   public void initialize(URL location, ResourceBundle resources) {
	   // TODO 自動生成されたメソッド・スタブ
	   	year.getItems().add("2020");
	   	year.getItems().add("2021");
	   	year.getItems().add("2022");
   		year.getItems().add("2023");
   		year.getItems().add("2024");
   		year.getItems().add("2025");
   		year.getItems().add("");
   		
   		subject.getItems().add("データ形式と演習");
   		subject.getItems().add("オブジェクト指向設計");
   		subject.getItems().add("多言語プログラミング");
   		subject.getItems().add("");
   		
   		examination.getItems().add("中間考査");
   		examination.getItems().add("期末考査");
   		examination.getItems().add("");
   		
   		year.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        if (item.isEmpty()) {
                            setText("項目を追加");
                        } else {
                            setText(item);
                        }
                    }
                }
            };

            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
                if (cell.getItem().isEmpty() && ! cell.isEmpty()) {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setContentText("開講年度を入力");
                    dialog.showAndWait().ifPresent(text -> {
                        int index = year.getItems().size()-1;
                        year.getItems().add(index, text);
                        year.getSelectionModel().select(index);

                    });
                    evt.consume();
                }
            });

            return cell ;
        });
   		subject.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        if (item.isEmpty()) {
                            setText("項目を追加");
                        } else {
                            setText(item);
                        }
                    }
                }
            };

            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
                if (cell.getItem().isEmpty() && ! cell.isEmpty()) {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setContentText("コース名を入力");
                    dialog.showAndWait().ifPresent(text -> {
                        int index = subject.getItems().size()-1;
                        subject.getItems().add(index, text);
                        subject.getSelectionModel().select(index);
                    });
                    evt.consume();
                }
            });

            return cell ;
        });
    	examination.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        if (item.isEmpty()) {
                            setText("項目を追加");
                        } else {
                            setText(item);
                        }
                    }
                }
            };

            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
                if (cell.getItem().isEmpty() && ! cell.isEmpty()) {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setContentText("試験名を入力");
                    dialog.showAndWait().ifPresent(text -> {
                        int index = examination.getItems().size()-1;
                        examination.getItems().add(index, text);
                        examination.getSelectionModel().select(index);
                    });
                    evt.consume();
                }
            });

            return cell ;
        });
   }
}