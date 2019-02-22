package application;

import Controller.CitiLoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loder=new FXMLLoader(getClass().getResource("../View/citizenlogin.fxml"));
		Parent root=loder.load();
		CitiLoginController citiLoginController=loder.getController();
		
		citiLoginController.primaryStage = primaryStage;
		Scene scene=new Scene(root);
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}
}
