package src;

import java.util.Random;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DiceGUI extends Application{
	
	private String betString = "";
	private Boolean clearFlag = false;
	private TextField results;

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
		myStage.setTitle("Dice Game");
		
		//create a root node, in this case a Grid layout
		//is used, but several alternatives exist
		BorderPane rootNode = new BorderPane();
		
		HBox diceHBox = new HBox(20);
		diceHBox.setPrefHeight(60);
		diceHBox.setPrefWidth(400);
		diceHBox.setAlignment(Pos.CENTER);
		
		Image diceUno = new Image("/images/one.png", 200, 200, false, false);
		Label dice1 = new Label();
		dice1.setGraphic(new ImageView(diceUno));

		
		Image diceDos = new Image("/images/one.png", 200, 200, false, false);
		Label dice2 = new Label();
		dice2.setGraphic(new ImageView(diceDos));
		dice2.setPrefSize(50, 50);
		
		diceHBox.getChildren().add(dice1);
		diceHBox.getChildren().add(dice2);
		
		rootNode.setCenter(diceHBox);
		
		HBox hbox = new HBox(25);
		hbox.setPrefWidth(1000);
		hbox.setPadding(new Insets(15, 12, 15, 12));
		
		//add label
		Label yourBet = new Label("Your Bet:");
		yourBet.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		hbox.getChildren().add(yourBet);
		
		TextField bet = new TextField();
		bet.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));

		bet.setBorder(new Border(new BorderStroke(Color.rgb(80, 80, 160, 1.0), BorderStrokeStyle.SOLID, new CornerRadii(3.0), BorderStroke.MEDIUM)));
		bet.setPrefWidth(60);
		hbox.getChildren().add(bet);
		
		// add Buttons
		Button rollDice = new Button ("Roll Dice");
		rollDice.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		rollDice.setPadding(new Insets(5, 5, 5, 5));
		hbox.getChildren().add(rollDice);
		
		Button exit = new Button ("Exit");
		exit.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		exit.setPadding(new Insets(5, 5, 5, 5));
		hbox.getChildren().add(exit);
		
		//add TextField
		results = new TextField();
		results.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		results.setPrefWidth(500);
		results.setPrefHeight(40);

		results.setBorder(new Border(new BorderStroke(Color.rgb(80, 80, 160, 1.0), BorderStrokeStyle.SOLID, new CornerRadii(3.0), BorderStroke.MEDIUM)));
		
		hbox.getChildren().add(results);
		
		hbox.setAlignment(Pos.CENTER);
		
		rootNode.setBottom(hbox);
		
		//create a scene
		Scene myScene = new Scene (rootNode, 1100, 325);
		
		
		//set the stage on the scene
		myStage.setScene(myScene);
		
		//show the stage and its scene
		
		String blank = "-fx-background-color: #ffffff;";
		String red = "-fx-background-color: #ff0000;";
		
		exit.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {
				stop();
				System.exit(0);
			}
		});
		
		
		rollDice.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {

				if(!setBothDice(dice1, dice2)) {
					bet.setStyle(red);
				} else {
					clearFlag = true;
				}
			}
		});
		
		
		bet.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
	
			public void handle(KeyEvent e) {
				
				if(clearFlag == true) {
					betString = "";
					bet.setText(betString);
				}

				
				results.setText("");
				
				if(e.getCode().equals(KeyCode.DELETE) || e.getCode().equals(KeyCode.BACK_SPACE)) {
					bet.setStyle(blank);
					if(betString.length() >= 1) {
						betString = betString.substring(0, betString.length()-1);
					}
					bet.setText(betString);
				}
				
				else if(e.getCode().toString().equals("ENTER")) {
					if(!setBothDice(dice1, dice2)) {
						bet.setStyle(red);
					} else {
						clearFlag = true;
					}
				}
				
				else if(e.getCode().isDigitKey()) {
					if(clearFlag == true) {
						betString = "";
						bet.setText(betString);
						clearFlag = false;
					}
					bet.setStyle(blank);
					betString += e.getText();
				} 
				else {
					clearFlag = true;
					bet.setText("");
					bet.setStyle(red);
				}
				
			}
		});
		myStage.show();
			

	}
	
	private boolean setBothDice(Label dice1, Label dice2) {
		
		if(betString.length() == 0) {
			return true;
		}
		
		if(Integer.parseInt(betString) > 12 || Integer.parseInt(betString) < 2) {
			clearFlag = true;
			return false;
		}
		
		int dice1Val = setDice(dice1);
		int dice2Val = setDice(dice2);
		
		if(Integer.parseInt(betString) == (dice1Val + dice2Val)) {
			results.setText("You win.  You bet on " + betString + ", and the dice rolled " + betString + ".");
		} else {
			results.setText("The house wins. You bet on " + betString + ", but the dice rolled " + (dice1Val + dice2Val) + ".");
		}
		return true;
	}
	
	private int setDice(Label dice) {
		int roll = rollDice();
		Image diceImage = null; 
		if(roll == 0) {
			diceImage = new Image("/images/one.png", 200, 200, false, false);
		} else if (roll == 1) {
			diceImage = new Image("/images/two.png", 200, 200, false, false);
		} else if (roll == 2) {
			diceImage = new Image("/images/three.png", 200, 200, false, false);
		} else if (roll == 3) {
			diceImage = new Image("/images/four.png", 200, 200, false, false);
		} else if (roll == 4) {
			diceImage = new Image("/images/five.png", 200, 200, false, false);
		} else if (roll == 5) {
			diceImage = new Image("/images/six.png", 200, 200, false, false);
		}
		dice.setGraphic(new ImageView(diceImage));
		return roll+1;
	}
	
	private int rollDice() {
		Random r = new Random();
		return r.nextInt(6);
	}
	
	
	@Override
	public void stop() {
		System.out.println("Inside the stop method.");
	}

}
