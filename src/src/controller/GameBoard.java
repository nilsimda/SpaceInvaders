package src.src.controller;

import view.GUI;
import src.src.model.DataManager;
import src.src.model.GameObjectFacade;

import java.util.*;

public class GameBoard {
	
	//feel free to adjust these values if necessary
	public static final int GUI_WIDTH = 700;
	public static final int GUI_HEIGHT = 700;
	
	private boolean GAME_OVER;
	
	private GUI gui;
	private DataManager dataManager;
	private GameObjectFacade gameObjects;
	
	//TODO add constructor
	
	//return types of some methods will be changed
	public void startGame() {
		//TODO
	}
	public void moveSpaceships() {
		//TODO
	}
	public void enterName() {
		//TODO
	}
	public void gameOver() {
		this.GAME_OVER = true;
	}
	public void measureScore() {
		//TODO
	}
	public void passInput() {
		//TODO
	}
}
