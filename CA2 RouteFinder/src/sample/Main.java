package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;


/**
 * @authors Tommy Dalton 200088305 , Jack Fitzpatrick 20090266
 *
 *  Breadth First Search - Tommy
 *
 *  Djikstras - Jack
 *
 *  GUI and Image Conversion- The both of us.
 *  Everything else - The Both of us.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("UI.fxml"));
        primaryStage.setTitle("Route Finder");
        //primaryStage.getIcons().add(new Image(""));
        primaryStage.setScene(new Scene(root, 925, 1200));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
