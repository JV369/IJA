package gui;

import components.BlockEat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private ScrollPane blockMenuPane;

    @FXML
    private VBox blockMenu;


    @FXML
    private void handleBlockAction(ActionEvent event) {
        System.out.println("You clicked me!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        MenuBlock blockEat = new MenuBlock(BlockEat.class, new Image("file:lib/Block.png", 50, 50, true, true));
        this.blockMenu.getChildren().add(blockEat);
    }

}