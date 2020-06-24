package view;

import controller.GameBoard;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class SpInvApplication extends Application {

    private GUI gui;


    //Application and GUI setup is to be put here
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TUM SpaceInvaders StartScreen");
        Button start = new Button("Start");
        Button score = new Button("Score");
        //start.setMaxHeight(100);
        //start.setMaxWidth(200);
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
                    /*if(e.getCode().equals(KeyCode.ESCAPE))
                        gui.stopGame();*/
                    if(e.getCode().equals(KeyCode.LEFT))
                        gui.getGameBoard().getGameObjects().steerCannon(-5);
                    if(e.getCode().equals(KeyCode.RIGHT))
                        gui.getGameBoard().getGameObjects().steerCannon(5);
                });
            }
        });

        primaryStage.setScene(startScene);
        primaryStage.show();
    }




    public static void launchApp(String[] args) {
        launch(args);
    }

}
