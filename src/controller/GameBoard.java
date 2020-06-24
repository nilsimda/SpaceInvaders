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
    public boolean isGAME_OVER(){
        return GAME_OVER;
    }

    private GameObjectFacade gameObjects;

    /**
     * Creates a new gameboard and game instance for a given GUI
     *
     * @param size the GUI creating the gameboard
     */
    public GameBoard(Dimension2D size) {
        GUI_WIDTH = size.getWidth();
        GUI_HEIGHT = size.getHeight();
        this.gameObjects = new StandardFacade(this);
        this.currentScore = 0;
    }

    /**
     * Starts the game
     */
    public void startGame() {
        GAME_OVER = false;
        startTime = Timestamp.valueOf(LocalDateTime.now());

        // Thread interrupts itself when the game is over
        SpaceshipThread spaceshipThread = new SpaceshipThread();
        spaceshipThread.start();

        String name = enterName();
        gameObjects.saveData(currentScore, startTime.toLocalDateTime(), name);
    }

    /**
     * Class that moves the spaceships, spaceships get moved once every second
     */
    class SpaceshipThread extends Thread {
        @Override
        public void run() {
            while (!GAME_OVER) {
                synchronized (gameObjects.getSpaceships()) {
                    moveSpaceships();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.interrupt();
        }
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
     *
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
