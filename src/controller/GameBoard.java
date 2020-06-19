package controller;

import model.DataManager;
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
    private DataManager dataManager;
    private GameObjectFacade gameObjects;
    
    /**
     * creates a new gameboard and game instance for a given GUI
     * @param gui the GUI creating the gameboard
     */
    public GameBoard(GUI gui) {
        this.gui = gui;
        this.dataManager = new DataManager();
        this.gameObjects = new StandardFacade(this);
        this.currentScore = 0;
        // TODO Make game start immediatly here?
    }

    //return types of some methods will be changed
    public void startGame() {
        startTime = Timestamp.valueOf(LocalDateTime.now());
    }

    public void moveSpaceships() {
        gameObjects.moveSpaceships();
    }

    public void enterName() {
        //TODO
    }

    public void gameOver() {
        this.GAME_OVER = true;
        //TODO find a better way to include playtime in score
        addToScore((int) (Timestamp.valueOf(LocalDateTime.now()).getTime() - startTime.getTime()));
    }

    public void measureScore() {
        /*TODO How should Score be measured? I propose that every shot down spaceship gives a certain amount of points,
         * additionally, maybe time survived should also be factored in here, to give scores a more "fancy" look
         * Thats a good idea, lets implement it first in that way
         */
    }

    public void addToScore(int scoreToAdd) {
        if (scoreToAdd < 0) throw new IllegalArgumentException("Can't add negative score!");
        currentScore += scoreToAdd;
    }

    public void passInput() {
        //TODO What does this do?
    }
}
