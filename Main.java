package application;
	
import javafx.application.Application;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
			Scene scene = new Scene(loader.load());
			stage.setTitle("ƒƒCƒ“ƒƒjƒ…[");
			stage.setScene(scene);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
