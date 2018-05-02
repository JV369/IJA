package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import org.scenicview.ScenicView;

/**
 * Třída Main
 * @author Jan Vávra (xvavra20)
 * @author Aleš Postulka (xpostu03)
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("template.fxml"));
        Scene scene = new Scene(root);
        //ScenicView.show(scene);
        stage.setScene(scene);
        stage.setTitle("Untitled - Human Simulator");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
