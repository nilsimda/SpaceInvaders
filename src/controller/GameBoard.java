package controller;

import javafx.geometry.Dimension2D;
import model.GameObjectFacade;
import model.StandardFacade;
import view.GUI;


import java.sql.Timestamp;
import java.time.LocalDateTime;

public class GameBoard {

    //feel free to adjust these values if necessary
    public static double GUI_WIDTH;
    public static double GUI_HEIGHT;

    private boolean GAME_OVER;
    private int currentScore;
    private Timestamp startTime;

    private GUI gui;

    public GameObjectFacade getGameObjects() {
        return gameObjects;
    }

    private GameObjectFacade gameObjects;
    
    /**
     * Creates a new gameboard and game instance for a given GUI
     * @param size the GUI creating the gameboard
     */
    /*public GameBoard(GUI gui) {
        this.gui = gui;
        this.gameObjects = new StandardFacade(this);
        this.currentScore = 0;
    }*/

    public GameBoard(Dimension2D size){
        GUI_WIDTH = size.getWidth();
        GUI_HEIGHT = size.getHeight();
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
