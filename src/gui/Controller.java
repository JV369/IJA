package gui;

import components.BlockEat;
import components.BlockSleep;
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
        MenuBlock blockEat = new MenuBlock(BlockEat.class, new Image("file:lib/BlockEat.png", 125, 93.75, false, true));
        MenuBlock blockSleep = new MenuBlock(BlockSleep.class, new Image("file:lib/BlockSleep.png", 125, 93.75, false, true));
        MenuBlock blockWork = new MenuBlock(BlockEat.class, new Image("file:lib/BlockWork.png", 125, 93.75, false, true));
        MenuBlock blockCook = new MenuBlock(BlockSleep.class, new Image("file:lib/BlockCook.png", 125, 93.75, false, true));
        MenuBlock blockSport = new MenuBlock(BlockSleep.class, new Image("file:lib/BlockSport.png", 125, 93.75, false, true));
        this.blockMenu.getChildren().add(blockEat);
        this.blockMenu.getChildren().add(blockSleep);
        this.blockMenu.getChildren().add(blockWork);
        this.blockMenu.getChildren().add(blockCook);
        this.blockMenu.getChildren().add(blockSport);
    }

}