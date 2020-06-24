package view;

import controller.GameBoard;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class SpInvApplication extends Application {

    private GUI gui;


    //Application and GUI setup is to be put here
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TUM SpaceInvaders StartScreen");
        Button start = new Button("Start");
        Button score = new Button("Score");
        HBox hBox = new HBox(start,score);
        Scene startScene = new Scene(hBox,500,300);
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gui = new GUI();
                GridPane gridLayout = new GridPane();
                gridLayout.setPrefSize(500, 300);
                gridLayout.setVgap(5);
                gridLayout.setPadding(new Insets(5, 5, 5, 5));

                // add gui to the gridLayout
                // second parameter is column index, second parameter is row index of grid
                gridLayout.add(gui, 0, 0);

                // scene and stages
                Scene scene = new Scene(gridLayout);
                primaryStage.setTitle("TUM SpaceInvaders GameScreen");
                primaryStage.setScene(scene);
                primaryStage.show();
                gui.startGame();
                scene.setOnKeyPressed(e ->{
                    if(e.getCode().equals(KeyCode.ESCAPE)) {
                        gui.stopGame();
                        primaryStage.setScene(startScene);
                        primaryStage.show();
                    }
                    if(e.getCode().equals(KeyCode.LEFT))
                        gui.getGameBoard().getGameObjects().steerCannon(-5);
                    if(e.getCode().equals(KeyCode.RIGHT))
                        gui.getGameBoard().getGameObjects().steerCannon(5);
                    if(e.getCode().equals(KeyCode.SPACE))
                        gui.getGameBoard().getGameObjects().fireCannon();
                });
            }
        });
        primaryStage.setScene(startScene);
        primaryStage.show();
        while(true) {
            if (gui.getGameBoard().getGameObjects().getSpaceships().isEmpty()) {
                gui.stopGame();
                Text winText = new Text("YOU WIN");
                Group group = new Group(winText);
                Scene endScene = new Scene(group, 500, 300);
                primaryStage.setScene(endScene);
                primaryStage.show();
                break;
            }
        }
    }




    public static void launchApp(String[] args) {
        launch(args);
    }

}
