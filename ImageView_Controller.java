package application;

import java.io.File;

import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ImageView_Controller implements Initializable{

    @FXML
    private Button turnback;

    @FXML
    private TextField studentid;

    @FXML
    private Button seach;
    
    @FXML
    private ComboBox<String> year;

    @FXML
    private ComboBox<String> subject;
    
    @FXML
    private ComboBox<String> examination;
    
    @FXML
    private TableView<PathTable> pathtable;
    
    @FXML
    private TableColumn<PathTable, String> pathcolumn;
    
    ObservableList<PathTable> observableArrayList = FXCollections.observableArrayList();
	
    static String idpath = null;
    
    @FXML
    void SeachButton(ActionEvent event) {
    	Object yearbox = year.getValue();
        Object subjectbox = subject.getValue();
        Object examinationbox = examination.getValue();
    	String tduscoringdata = "C:/TDUScoringdata";
    	File tduscoringdatafile = new File(tduscoringdata);
		if(tduscoringdatafile.exists()) {
			System.out.println("TDUScoringdataは存在します");
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("C:/TDUScoringdataが存在しません。");
			alert.setContentText("採点を行いマークシート画像を保存してください。");
			alert.showAndWait();
			return;
		}
		String yearpath = "C:/TDUScoringdata/" + yearbox;
		File yearfile = new File(yearpath);
		if(yearfile.exists()) {
			System.out.println("開講年度は存在します");
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("「" + yearpath + "」　のディレクトリは存在しません。");
			alert.setContentText("採点を行いマークシート画像を保存してください。");
			alert.showAndWait();
			return;
		}
		String subjectpath = yearpath + "/" + subjectbox;
		File subjectfile = new File(subjectpath);
		if(subjectfile.exists()) {
			System.out.println("コースは存在します");
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("「" + subjectpath + "」　のディレクトリは存在しません。");
			alert.setContentText("採点を行いマークシート画像を保存してください。");
			alert.showAndWait();
			return;
		}
		String examinationpath = subjectpath +  "/" + examinationbox;
		File examinationfile = new File(examinationpath);
		if(examinationfile.exists()) {
			
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("「" + examinationpath + "」　のディレクトリは存在しません。");
			alert.setContentText("採点を行いマークシート画像を保存してください。");
			alert.showAndWait();
			return;
		}
		if(studentid.getText().length() != 0) {
			idpath = examinationpath + "/" + studentid.getText() + ".jpg";
			File idfile = new File(idpath);
			System.out.println(idfile + "を参照します");
			if(idfile != null) {
				try {
					imageRoad();
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("「" + idpath + "」　が存在しません。");
				alert.setContentText("採点を行いマークシート画像を保存してください。");
				alert.showAndWait();
				return;
			}
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("学籍番号が入力されていません");
			alert.showAndWait();
			return;
		}
    }

    public void imageRoad() throws Exception{
    	Stage stage = new Stage();
    	FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ImageView2.fxml"));
		Parent root = loader.load();
		ImageView2_Controller controller = loader.getController();
		controller.initStage(stage);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    @FXML
    void turnbackbutton(ActionEvent event) {
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
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
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
