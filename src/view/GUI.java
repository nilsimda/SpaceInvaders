package view;

import controller.GameBoard;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Cannon;
import model.Spaceship;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class GUI extends Application {

    Dimension2D size = new Dimension2D(400, 500);

    //private GUI gui = new GUI();
    private GameBoard gameBoard;
    private Stage primaryStage;
    private static String PLAYER_NAME;
    private ImageView cImage;
    private Map<Spaceship, ImageView> spImages;
    private Pane root;


    public GUI(){
        gameBoard = new GameBoard(size);
    }

    public Scene createStartScreen() {
        Text shoot = new Text("Shoot: SpaceBar (no Bullet Image yet)");
        Text move = new Text("Move Cannon: Left and right arrow keys");
        Button start = new Button("Start");
        Button score = new Button("Score");
        VBox vBox = new VBox(shoot, move, start, score);
        vBox.setSpacing(10);
        return new Scene(vBox, 500, 300);
    }

    public void gameSetup(Stage primaryStage) {
        this.primaryStage = primaryStage;
        cImage = new ImageView(getCannonImage());
        root = new Pane();
        spImages = new HashMap<>();
        for (Spaceship s : gameBoard.getGameObjects().getSpaceships()) {
            ImageView sp = new ImageView(getSpaceShipImage());
            spImages.put(s, sp);
            root.getChildren().add(sp);
            sp.setFitHeight(20);
            sp.setFitWidth(30);
        }
    }

    //Application and GUI setup is to be put here
    @Override
    public void start(Stage primaryStage) {
        gameSetup(primaryStage);
        primaryStage.setTitle("TUM SpaceInvaders StartScreen");
        Text controls = new Text("Controls");
        Text shoot = new Text("Shoot: SpaceBar (no Bullet Image yet)");
        Text move = new Text("Move Cannon: Left and right arrow keys");
        controls.setUnderline(true);
        
        Button start = new Button("Start");
        Button score = new Button("Scores");
        VBox vBox = new VBox(controls,shoot, move, start, score);
        vBox.setSpacing(10);
        Scene startScene = new Scene(vBox, 500 ,300);
        primaryStage.setScene(startScene);
        primaryStage.show();
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                startGame(startScene);
            }
        });
    }

    public void startGame(Scene startScene) {
        //gui.getGameBoard().startGame();
        root.setPrefSize(size.getWidth(), size.getHeight());
        paint();

        root.getChildren().add(cImage);
        cImage.setFitHeight(20);
        cImage.setFitWidth(30);

        Scene gameScene = new Scene(root);

        root.setStyle("-fx-background-color: black;");

        Text score = new Text("Score: " + gameBoard.getCurrentScore());
        score.setFill(Color.BLUE);
        root.getChildren().add(score);
        score.setX(20);
        score.setY(20);

        primaryStage.setTitle("TUM SpaceInvaders GameScreen");
        primaryStage.setScene(gameScene);
        primaryStage.show();

        gameBoard.startGame();
        playGame(gameScene, score);
    }

    public void paint() {
        for (Map.Entry<Spaceship, ImageView> en : spImages.entrySet())
            paintSpaceship(en.getKey(), en.getValue());

        paintCannon(gameBoard.getGameObjects().getCannon());
    }

    public void paintCannon(Cannon cannon) {
        cImage.setTranslateX((cannon.getPosition()));
        cImage.setTranslateY(size.getHeight()-Cannon.cannonHeight);
    }

    public void paintSpaceship(Spaceship s, ImageView spIm) {
        spIm.setTranslateX(s.getX());
        spIm.setTranslateY(s.getY());
    }

    public void playGame(Scene scene, Text score) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            gameBoard.moveSpaceships();
            paint();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        Thread gameThread = new Thread(() -> { //using a Thread to run the main game

            while (!gameBoard.getGAME_OVER()) {
                handleUserInput(scene, score);
            }
            if (gameBoard.getGameObjects().getSpaceships().isEmpty()) { //all spaceships are destroyed, the player has won
                displayEndScreen("You win!");
            } else {
                displayEndScreen("You lose!"); // game is over but spaceships are still alive, the player has lost
            }
        });
        gameThread.setDaemon(true);
        gameThread.start();
    }

    public void handleUserInput(Scene scene, Text score) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ESCAPE)) {
                Platform.exit();
                System.exit(0);
            }
            if (e.getCode().equals(KeyCode.LEFT))
                gameBoard.getGameObjects().steerCannon(-5);
            if (e.getCode().equals(KeyCode.RIGHT))
                gameBoard.getGameObjects().steerCannon(5);
            if (e.getCode().equals(KeyCode.SPACE)) {
                gameBoard.fireCannon();
                score.setText("Score: " + gameBoard.getCurrentScore());
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        removeSPImage();
                    }
                });
            }
            paint();
        });
    }

    private void removeSPImage() {
        for (Map.Entry<Spaceship, ImageView> en : spImages.entrySet()) {
            if (!en.getKey().getAlive())
                root.getChildren().remove(en.getValue());
        }
    }

    public void displayEndScreen(String message) {
        int finalScore = gameBoard.getCurrentScore();
        gameBoard.gameOver();

        Text endText = new Text(message + " Your final score is " + finalScore);
        Label nameLabel = new Label("Enter your name to save your score:");

        TextField textField = new TextField();
        Button saveButton = new Button("Save");

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().addAll(endText, nameLabel, textField, saveButton);
        Scene endScene = new Scene(vBox, 500, 300);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                primaryStage.setTitle("TUM SpaceInvaders End Screen");
                primaryStage.setScene(endScene);
                primaryStage.show();
            }
        });
        saveButton.setOnAction(e ->
        {
            displaySaveScreen(textField);
        });
    }

    public void displaySaveScreen(TextField textField) {
        PLAYER_NAME = textField.getText();
        Text savedMessage = new Text("Thank you " + PLAYER_NAME + ". Your score was saved successfully!");
        savedMessage.setX(50);
        savedMessage.setY(50);
        Group group = new Group(savedMessage);
        Scene saveScene = new Scene(group, 500, 300);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                primaryStage.setTitle("TUM SpaceInvaders End Screen");
                primaryStage.setScene(saveScene);
                primaryStage.show();
            }
        });
    }

    private Image getCannonImage() {
        try {
            String CANNON_IMAGE = "cannon.png";
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


    private Image getSpaceShipImage() {
        try {
            String SPACESHIP_IMAGE = "spaceship.png";
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

    public static void launchApp(String[] args) {
        launch(args);
    }
}


