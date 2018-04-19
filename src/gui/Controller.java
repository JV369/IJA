package gui;

import components.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sun.security.util.ManifestEntryVerifier;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private MenuBlock selectedBlock = null;
    private boolean selected;

    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    @FXML
    private ScrollPane blockMenuPane;

    @FXML
    private VBox blockMenu;

    @FXML
    private AnchorPane blockScene;



    @FXML
    private void handleBlockAction(ActionEvent event) {
        System.out.println("You clicked me!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

        MenuBlock blockEat = new MenuBlock(BlockEat.class, new Image("file:lib/BlockEat.png", 125, 93.75, false, true));
        MenuBlock blockSleep = new MenuBlock(BlockSleep.class, new Image("file:lib/BlockSleep.png", 125, 93.75, false, true));
        MenuBlock blockWork = new MenuBlock(BlockWork.class, new Image("file:lib/BlockWork.png", 125, 93.75, false, true));
        MenuBlock blockCook = new MenuBlock(BlockCook.class, new Image("file:lib/BlockCook.png", 125, 93.75, false, true));
        MenuBlock blockSport = new MenuBlock(BlockSport.class, new Image("file:lib/BlockSport.png", 125, 93.75, false, true));

        this.blockMenu.setSpacing(10);
        this.blockMenu.setPadding(new Insets(10,0,10,0));
        this.blockMenu.getChildren().add(blockEat);
        this.blockMenu.getChildren().add(blockSleep);
        this.blockMenu.getChildren().add(blockWork);
        this.blockMenu.getChildren().add(blockCook);
        this.blockMenu.getChildren().add(blockSport);

        blockCook.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleMenuClick(blockCook);
            }
        });

        blockEat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleMenuClick(blockEat);
            }
        });

        blockSleep.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleMenuClick(blockSleep);
            }
        });

        blockSport.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleMenuClick(blockSport);
            }
        });

        blockWork.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleMenuClick(blockWork);
            }
        });

        blockScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleSceneClick(event);
            }
        });

    }

    private void handleMenuClick(MenuBlock block){
        if(selected && block.getAbstractBlockClass().equals(selectedBlock.getAbstractBlockClass())) {
            selected = false;
            selectedBlock = null;
            block.setStyle("");
        }
        else {
            selected = true;
            if(selectedBlock != null)
                selectedBlock.setStyle("");
            selectedBlock = block;
            block.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(255,0,0,0.8), 15, 0, 0, 0)");
        }
    }

    private void handleSceneClick(MouseEvent event){
        if(selected){
            String url;
            switch (selectedBlock.getAbstractBlockClass().getSimpleName()){
                case "BlockCook":
                    url = "file:lib/BlockCook.png";
                    break;
                case "BlockSleep":
                    url = "file:lib/BlockSleep.png";
                    break;
                case "BlockEat":
                    url = "file:lib/BlockEat.png";
                    break;
                case "BlockWork":
                    url = "file:lib/BlockWork.png";
                    break;
                case "BlockSport":
                    url = "file:lib/BlockSport.png";
                    break;
                default:
                    url = "file:lib/BlockCook.png";
            }
            MenuBlock block = new MenuBlock(selectedBlock.getAbstractBlockClass(), new Image(url, 125, 93.75, false, true));
            double Xcoord, Ycoord;
            if((event.getX() - 125.0) <= 0){
                Xcoord = event.getX();
            }
            else {
                Xcoord = event.getX() - 125.0/2.0;
            }

            if((event.getY() - 93.75) <= 0){
                Ycoord = event.getY();
            }
            else {
                Ycoord = event.getY() - 93.75/2.0;
            }

            block.setX(Xcoord);
            block.setY(Ycoord);

            block.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    orgSceneX = event.getSceneX();
                    orgSceneY = event.getSceneY();
                    orgTranslateX = ((MenuBlock)(event.getSource())).getTranslateX();
                    orgTranslateY = ((MenuBlock)(event.getSource())).getTranslateY();
                }
            });

            block.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    double offsetX = event.getSceneX() - orgSceneX;
                    double offsetY = event.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    //this wont let you drag it on menu
                    if((newTranslateX + block.getX()) <= 0){
                        newTranslateX = -block.getX();
                    }
                    if((newTranslateY + block.getY()) <= 0){
                        newTranslateY = -block.getY();
                    }

                    ((MenuBlock)(event.getSource())).setTranslateX(newTranslateX);
                    ((MenuBlock)(event.getSource())).setTranslateY(newTranslateY);
                }
            });

            blockScene.getChildren().add(block);

        }

    }


}