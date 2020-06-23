package controller;

import model.GameObjectFacade;
import model.StandardFacade;
import view.GUI;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class GameBoard {

    //feel free to adjust these values if necessary
    public static final int GUI_WIDTH = 700;
    public static final int GUI_HEIGHT = 700;

    private boolean GAME_OVER;
    private int currentScore;
    private Timestamp startTime;

    private GUI gui;
    private GameObjectFacade gameObjects;
    
    /**
     * Creates a new gameboard and game instance for a given GUI
     * @param gui the GUI creating the gameboard
     */
    public GameBoard(GUI gui) {
        this.gui = gui;
        this.gameObjects = new StandardFacade(this);
        this.currentScore = 0;
    }
    
    /**
     * Starts the game
     */
    public void startGame() {
        startTime = Timestamp.valueOf(LocalDateTime.now());
        //TODO create a thread for moving the spaceships
        
        //TODO terminate the spaceship thread
        String name = enterName();
        gameObjects.saveData(currentScore, startTime.toLocalDateTime(), name);
    }
    
    public void moveSpaceships() {
        gameObjects.moveSpaceships();
    }

    public String enterName() {
        //TODO call a method in GUI that gets the name
    	return null;
    }

    public void gameOver() {
        this.GAME_OVER = true;
        addToScore((int) (Timestamp.valueOf(LocalDateTime.now()).getTime() - startTime.getTime()));
    }

    public void addToScore(int scoreToAdd) {
        if (scoreToAdd < 0) throw new IllegalArgumentException("Can't add negative score!");
        currentScore += scoreToAdd;
    }
    
    /**
     * Moves the cannon by the given delta value
     * @param delta The distance to move the cannon. Negative values result in movements to the left and vice versa
     */
    public void moveCannon(int delta) {
    	gameObjects.steerCannon(delta);
    }
    
    /**
     * Fires the cannon
     */
    public void fireCannon() {
    	gameObjects.fireCannon();
    }
}
