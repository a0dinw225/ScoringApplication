package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ImageView2_Controller implements Initializable{

	@FXML
	private BorderPane borderPane;

	@FXML
	private ImageView imageView;
	
	@FXML
	private Button bt1;

	@FXML
	private Button bt2;

	public void initStage(Stage stage) {
		stage.setWidth(1000);
		stage.setHeight(750);
		stage.setTitle(ImageView_Controller.idpath);
		imageView.fitWidthProperty().bind(borderPane.widthProperty());
		imageView.fitHeightProperty().bind(borderPane.heightProperty());
	}
	@FXML
    void ScaleDown(ActionEvent event) {
		imageView.setScaleX(1.0);
    	imageView.setScaleY(1.0);
    }

    @FXML
    void ScaleUp(ActionEvent event) {
    	imageView.setScaleX(1.5);
    	imageView.setScaleY(1.5);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO 自動生成されたメソッド・スタブ
		//path.setText(ImageView_Controller.idpath);
		//path.setFont(Font.font(24));
		Image image = new Image("file:" + ImageView_Controller.idpath);
		System.out.println("画像セットします");
		System.out.println("file:" + ImageView_Controller.idpath);
		imageView.setImage(image);
		System.out.println("画像セットしました");
	}
	@FXML
    void initialize() {
        assert borderPane != null : "fx:id=\"borderPane\" was not injected: check your FXML file 'ImageView2.fxml'.";
        assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'ImageView2.fxml'.";
        assert bt1 != null : "fx:id=\"bt1\" was not injected: check your FXML file 'ImageView2.fxml'.";
        assert bt2 != null : "fx:id=\"bt2\" was not injected: check your FXML file 'ImageView2.fxml'.";

    }

}
