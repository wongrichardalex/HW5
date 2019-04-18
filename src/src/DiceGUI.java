package src;

/*
 * Author: Richard Wong
 * 
 * Program: DiceGUI
 * 
 * Program Behavior: This program represents the model, view, and controller for
 * a dice rolling game.  The game is played by having the user guess a number from
 * 2 - 12 inclusive, and "rolling" two dice in hopes that the guess will be equal
 * to the sum of the dice after rolling. 
 */
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
	
	//some global variables to help us during execution
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
		
		
		//Create the HBox for the dices and set the height, width, and alignment
		HBox diceHBox = new HBox(20);
		diceHBox.setPrefHeight(60);
		diceHBox.setPrefWidth(400);
		diceHBox.setAlignment(Pos.CENTER);
		
		//Create the Dices
		Image diceUno = new Image("/images/one.png", 200, 200, false, false);
		Label dice1 = new Label();
		dice1.setGraphic(new ImageView(diceUno));

		
		Image diceDos = new Image("/images/one.png", 200, 200, false, false);
		Label dice2 = new Label();
		dice2.setGraphic(new ImageView(diceDos));
		dice2.setPrefSize(50, 50);
		
		//add them to the Hbox
		diceHBox.getChildren().add(dice1);
		diceHBox.getChildren().add(dice2);
		
		//Add the dice HBox to the correct position on the GridPane
		rootNode.setCenter(diceHBox);
		
		//Create the HBox for the row of bottoms and text fields at the bottom
		HBox hbox = new HBox(25);
		hbox.setPrefWidth(1000);
		hbox.setPadding(new Insets(15, 12, 15, 12));
		
		//add label for Your Bet:, alter the font and everything
		Label yourBet = new Label("Your Bet:");
		yourBet.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		hbox.getChildren().add(yourBet);
		
		//add TextField for user to enter bet
		TextField bet = new TextField();
		bet.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));

		bet.setBorder(new Border(new BorderStroke(Color.rgb(80, 80, 160, 1.0), BorderStrokeStyle.SOLID, new CornerRadii(3.0), BorderStroke.MEDIUM)));
		bet.setPrefWidth(60);
		hbox.getChildren().add(bet);
		
		// add Buttons
		
		//roll Dice button
		Button rollDice = new Button ("Roll Dice");
		rollDice.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		rollDice.setPadding(new Insets(5, 5, 5, 5));
		hbox.getChildren().add(rollDice);
		
		//exit button
		Button exit = new Button ("Exit");
		exit.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		exit.setPadding(new Insets(5, 5, 5, 5));
		hbox.getChildren().add(exit);
		
		//add TextField for results
		results = new TextField();
		results.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		results.setPrefWidth(500);
		results.setPrefHeight(40);
		results.setEditable(false); //we don't want the user to be able to type anything into the results textfield

		results.setBorder(new Border(new BorderStroke(Color.rgb(80, 80, 160, 1.0), BorderStrokeStyle.SOLID, new CornerRadii(3.0), BorderStroke.MEDIUM)));
		
		hbox.getChildren().add(results);
		
		hbox.setAlignment(Pos.CENTER);
		
		//add the Hbox for the bottom row to our layout
		rootNode.setBottom(hbox);
		
		//create a scene
		Scene myScene = new Scene (rootNode, 1100, 325);
		
		
		//set the stage on the scene
		myStage.setScene(myScene);
		
		//show the stage and its scene
		
		//these will be the colors for the bet textfield
		String blank = "-fx-background-color: #ffffff;";
		String red = "-fx-background-color: #ff0000;";
		
		//here is our exit button action handler
		exit.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {
				//we simply stop the program and exit
				stop();
				System.exit(0);
			}
		});
		
		//heree is our handler for the dice button to roll them
		rollDice.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) {

				if(!setBothDice(dice1, dice2)) { //if there is invalid input in the text box
					bet.setStyle(red); //set our background to red
				} else {
					clearFlag = true; //this means input is valid; we need to clear the textbox if the user types
					 				 //something in again
				}
			}
		});
		
		//here is our handler for the bet TextField, which will trigger whenever a button on the keyboard is pressed
		bet.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
	
			public void handle(KeyEvent e) {
				
				if(clearFlag == true) { //if the user entered valid input before, we need to clear the textfield
					betString = "";
					bet.setText(betString);
					clearFlag = false;
				}

				
				results.setText(""); //if the user enters anything into the bet textfield, we need to reset the results textfield
				
				if(e.getCode().equals(KeyCode.DELETE) || e.getCode().equals(KeyCode.BACK_SPACE)) { //if the user is deleting characters
					bet.setStyle(blank);
					if(betString.length() >= 1) { //just remove a character from the right of the string in the textfield
						betString = betString.substring(0, betString.length()-1);
					}
					bet.setText(betString); 
				}
				
				else if(e.getCode().toString().equals("ENTER")) { //enter will trigger the roll dice button
					if(!setBothDice(dice1, dice2)) { //this event handling mirrors the roll dice button
						bet.setStyle(red);
					} else {
						clearFlag = true; //set this to be true so that the next time the user presses a key, the textField will clear
					}
				}
				
				else if(e.getCode().isDigitKey()) { //for valid numeric input
					bet.setStyle(blank); //we need to ensure that the background turns white again
					betString += e.getText(); //we add the valid input to our betString
				} 
				else { //for invalid input across the keyboard
					clearFlag = true; //we need to ensure that we know that we clear the textField the next time input is entered
					bet.setText(""); //clear the textField
					bet.setStyle(red);
				}
				
			}
		});
		myStage.show();
			

	}
	
	/*
	 * This method represents rolling two dice and getting two random rolls. 
	 * 
	 * By taking in two labels with Dice graphics on them, this method will
	 * set these graphics to randomly chosen dice graphics.
	 */
	private boolean setBothDice(Label dice1, Label dice2) {
		
		if(betString.length() == 0) { //if the user did not enter any input, do nothing
			return true;
		}
		
		if(Integer.parseInt(betString) > 12 || Integer.parseInt(betString) < 2) { //if the user has invalid numeric input
			clearFlag = true; //ensure that the field will be cleared
			return false; //return false to show that we did not set both dice
		}
		
		//call the setDice method which randomly returns a number 1 - 6 while setting the label graphic to the 
		//corresponding dice image, representing a dice roll
		int dice1Val = setDice(dice1); 
		int dice2Val = setDice(dice2);
		
		//this method changes our results textField based on if the user bets correctly or not
		if(Integer.parseInt(betString) == (dice1Val + dice2Val)) {
			results.setText("You win.  You bet on " + betString + ", and the dice rolled " + betString + ".");
		} else {
			results.setText("The house wins. You bet on " + betString + ", but the dice rolled " + (dice1Val + dice2Val) + ".");
		}
		return true;
	}
	
	/*
	 * This chooses a random number from 1 - 6 and sets the Label parameter graphic to the corresponding Dice image located in the
	 * src/images folder, and returns that randomly chosen number
	 */
	private int setDice(Label dice) {
		int roll = rollDice();
		Image diceImage = null; 
		
		String image = "";
		
		//A series of statements to assign the image to the right number
		if(roll == 0) {
			image = "/images/one.png";
		} else if (roll == 1) {
			image = "/images/two.png";
		} else if (roll == 2) {
			image = "/images/three.png";
		} else if (roll == 3) {
			image = "/images/four.png";
		} else if (roll == 4) {
			image = "/images/five.png";
		} else if (roll == 5) {
			image = "/images/six.png";
		}
		
		diceImage = new Image(image, 200, 200, false, false);
		dice.setGraphic(new ImageView(diceImage)); //set our graphic for the label
		return roll+1; //return the number picked to the caller for use in determining if the user bet correctly
	}
	
	/*
	 * returns a random number from 0-5 inclusive
	 */
	private int rollDice() {
		Random r = new Random();
		return r.nextInt(6);
	}
	
	
	@Override
	public void stop() {
		System.out.println("Inside the stop method.");
	}

}
