package src;

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;

public class DiceGUI extends Application{

	public static void main(String[] args) {
		System.out.println("Launching JavaFX application");
		// Start the JavaFX application by calling launch()
		launch(args);
	}
   
	//override the init method
	@Override
	public void init() {
		System.out.println("Inside the start method");
	}
	
	@Override
	public void start(Stage myStage) {
		// give the stage a title
		myStage.setTitle("JavaFX skeleton");
		
		//create a root node, in this case a flow layout
		//is used, but several alternatives exist
		FlowPane rootNode = new FlowPane();
		
		//create a scene
		Scene myScene = new Scene (rootNode, 300, 200);
		
		//set the stage on the scene
		myStage.setScene(myScene);
		
		//show the stage and its scene
		myStage.show();
	}
	
	
	@Override
	public void stop() {
		System.out.println("Inside the stop method.");
	}

}
