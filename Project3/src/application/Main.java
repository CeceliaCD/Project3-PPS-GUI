package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
The Main class is responsible for executing the program and to
display the graphical user interface. This class will show the interface
that was built in View.fxml.
@author Ceceliachollette-Dickson, Nidaansari
*/
public class Main extends Application {
	
	/**
	Obtains all the information from the FXML file and executes it so the graphical user
	interface can be shown.
	@param primaryStage creates the window for the program to run on.
	*/
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("View.fxml"));
			Scene scene = new Scene(root,700,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Payroll Processing");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	Runs the payroll processing program.
	@param args array of string arguments
	*/
	public static void main(String[] args) {
		launch(args);
	}
}
