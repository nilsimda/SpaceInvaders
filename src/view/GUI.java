package view;

import controller.GameBoard;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.Cannon;
import model.Spaceship;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class GUI extends Canvas implements Runnable {
    private static String SPACESHIP_IMAGE = "spaceship.png";
    private static String CANNON_IMAGE = "cannon.png";
    private static final Color backgroundColor = Color.BLACK;
    private static final int SLEEP_TIME = 1000 / 25; // this gives us 25fps
    private static final Dimension2D DEFAULT_SIZE = new Dimension2D(500, 300);
    // attribute inherited by the JavaFX Canvas class
    private GraphicsContext graphicsContext = this.getGraphicsContext2D();
    private Thread thread;
    private GameBoard gameBoard;
    private Dimension2D size;

    public GUI() {
        this.size = DEFAULT_SIZE;
        gameSetup();

    }

    public void gameSetup() {
        this.gameBoard = new GameBoard(size);
        this.widthProperty().set(this.size.getWidth());
        this.heightProperty().set(this.size.getHeight());
        this.size = new Dimension2D(getWidth(), getHeight());
        paint(this.graphicsContext);
    }

    public void startGame() {
        this.gameBoard.startGame();
        thread = new Thread(this);
        thread.start();
        paint(this.graphicsContext);
    }

    public void stopGame(){
        this.gameBoard.gameOver();
    }

    private Image getSpaceShipImage() {
        try {
            URL spaceshipImageUrl = this.getClass().getClassLoader().getResource(SPACESHIP_IMAGE);
            if (spaceshipImageUrl == null) {
                throw new RuntimeException("Please ensure that your resources folder contains the appropriate files for this exercise.");
            }
            InputStream inputStream = spaceshipImageUrl.openStream();
            return new Image(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Image getCannonImage() {
        try {
            URL spaceshipImageUrl = this.getClass().getClassLoader().getResource(CANNON_IMAGE);
            if (spaceshipImageUrl == null) {
                throw new RuntimeException("Please ensure that your resources folder contains the appropriate files for this exercise.");
            }
            InputStream inputStream = spaceshipImageUrl.openStream();
            return new Image(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void paint(GraphicsContext graphics) {
        graphics.setFill(backgroundColor);
        graphics.fillRect(0, 0, getWidth(), getHeight());


        synchronized (this.gameBoard.getGameObjects().getSpaceships()) {
            for (Spaceship spaceship : this.gameBoard.getGameObjects().getSpaceships()) {
                paintSpaceships(spaceship, graphics);
            }
        }
        paintCannon(this.gameBoard.getGameObjects().getCannon(), graphics);

    }

    private void paintSpaceships(Spaceship spaceship, GraphicsContext graphics) {
        Point2D spaceshipPosition = new Point2D(spaceship.getX(), spaceship.getY());
        Point2D canvasPosition = convertPosition(spaceshipPosition);

        graphics.drawImage(getSpaceShipImage(), canvasPosition.getX(), canvasPosition.getY(),
                Spaceship.spaceshipWidth, Spaceship.spaceshipHeight);
    }

    private void paintCannon(Cannon cannon, GraphicsContext graphics) {
        Point2D cannonPos = new Point2D(cannon.getPosition(), 30);
        Point2D canvasPosition = convertPosition(cannonPos);
        graphics.drawImage(getCannonImage(), canvasPosition.getX(), canvasPosition.getY(), Cannon.cannonWidth, Cannon.cannonHeight);
    }

    public Point2D convertPosition(Point2D toConvert) {
        return new Point2D(toConvert.getX(), getHeight() - toConvert.getY());
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }


    @Override
    public void run() {
        while (!this.gameBoard.isGAME_OVER()) {
            paint(this.graphicsContext);
            try {
                Thread.sleep(SLEEP_TIME); // milliseconds to sleep
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
