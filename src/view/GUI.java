package view;

import controller.GameBoard;

import javafx.application.Application;
import javafx.stage.Stage;

public class GUI extends Application {
	
	private GameBoard gameBoard;
	
	//Application and GUI setup is to be put here
	public void start(Stage primaryStage) {
		gameBoard = new GameBoard(this);
	}
	
	public void delegateInput() {
		//TODO
	}
	
	public static void launchApp(String[] args) {
		launch(args);
	}
}
