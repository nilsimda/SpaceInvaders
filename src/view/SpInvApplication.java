package view;

import controller.GameBoard;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class SpInvApplication extends Application {


    //Application and GUI setup is to be put here
    @Override
    public void start(Stage primaryStage) {

        Button start = new Button("Start");
        Button score = new Button("Score");
        start.setMaxHeight(100);
        start.setMaxWidth(200);
        HBox hBox = new HBox(start,score);
        Scene startScene = new Scene(hBox,600,300);
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });

        primaryStage.setScene(startScene);
        primaryStage.show();
    }




    public static void launchApp(String[] args) {
        launch(args);
    }

}
