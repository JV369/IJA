package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.scenicview.ScenicView;

public class Main extends Application {



    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("template.fxml"));
        Scene scene = new Scene(root);
        //ScenicView.show(scene);
        stage.setScene(scene);
        stage.setTitle("Human simulator");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
