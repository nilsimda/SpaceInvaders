package view;

import controller.GameBoard;

import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Cannon;
import model.Spaceship;

import java.util.HashMap;


public class GUI extends Canvas implements Runnable {
    private static String SPACESHIP_IMAGE = "spaceship.jpg";
    private static String CANNON_IMAGE = "cannon.jpg";
    private static final Color backgroundColor = Color.WHITE;
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
        /*this.gameBoard.resetCars();
        this.gameBoard.getGameObjects().getSpaceships().forEach((car -> this.carImages.put(car, getImage(car.getIconLocation()))));
        this.carImages.put(this.gameBoard.getPlayerCar(), this.getImage(this.gameBoard.getPlayerCar().getIconLocation()));
        paint(this.graphicsContext);
        */
    }

    private void paint(GraphicsContext graphics) {

        graphics.setFill(backgroundColor);
        graphics.fillRect(0, 0, getWidth(), getHeight());

        // render player car

        for (Spaceship spaceship : this.gameBoard.getGameObjects().getSpaceships()) {
            paintSpaceships(spaceship, graphics);
        }

        paintCannon(this.gameBoard.getGameObjects().getCannon(), graphics);

    }

    private void paintSpaceships(Spaceship spaceship, GraphicsContext graphics) {
        Point2D spaceshipPosition = new Point2D(spaceship.getX(), spaceship.getY());
        Point2D canvasPosition = convertPosition(spaceshipPosition);

        graphics.drawImage(null, canvasPosition.getX(), canvasPosition.getY(),
                Spaceship.spaceshipWidth, Spaceship.spaceshipHeight);
    }

    private void paintCannon(Cannon cannon, GraphicsContext graphics) {
        Point2D cannonPos = new Point2D(cannon.getPosition(), 0);
        Point2D canvasPosition = convertPosition(cannonPos);
        graphics.drawImage(null, canvasPosition.getX(), canvasPosition.getY(), Cannon.cannonWidth, Cannon.cannonHeight);
    }

    public Point2D convertPosition(Point2D toConvert) {
        return new Point2D(toConvert.getX(), getHeight() - toConvert.getY());
    }


    @Override
    public void run() {

    }


}
