package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainMenu_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button scoring;

    @FXML
    private Button analysis;

    @FXML
    private Button view;

    @FXML
    void nextanalysis(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("UploadAnalysis.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setTitle("ï™êÕ");
			stage.setScene(scene);
			stage.show();
			Scene scene2 = ((Node) event.getSource()).getScene();
	    	Window window = scene2.getWindow();
	    	window.hide();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void nextview(ActionEvent event) {
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("ImageView.fxml"));
    		Scene scene = new Scene(root);
    		Stage stage = new Stage();
    		stage.setTitle("â{óó");
    		stage.setScene(scene);
    		stage.show();
    		Scene scene2 = ((Node)event.getSource()).getScene();
    		Window window = scene2.getWindow();
    		window.hide();
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }

   @FXML
    void nextscoring(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("UploadScoring.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setTitle("çÃì_");
			stage.setScene(scene);
			stage.show();
			Scene scene2 = ((Node) event.getSource()).getScene();
	    	Window window = scene2.getWindow();
	    	window.hide();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
        assert scoring != null : "fx:id=\"scoring\" was not injected: check your FXML file 'MainMenu.fxml'.";
        assert analysis != null : "fx:id=\"analysis\" was not injected: check your FXML file 'MainMenu.fxml'.";
        assert view != null : "fx:id=\"view\" was not injected: check your FXML file 'MainMenu.fxml'.";

    }
}
