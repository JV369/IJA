package gui;

import components.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.util.Callback;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private MenuBlock selectedBlock = null;
    private Port selectedPort;
    private GUIConnection selectedConnect;
    private GUIPort selectetGUIport1;
    private GUIPort selectetGUIport2;
    private boolean connecting = false;
    private boolean selected;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;
    private ContextMenu contextMenuPort;
    private ContextMenu contextMenuBlock;
    private ContextMenu contextMenuConnect;
    private Group selectedGroup;
    private Group selectedGroup1;

    @FXML
    private ScrollPane blockMenuPane;

    @FXML
    private VBox blockMenu;

    @FXML
    private AnchorPane blockScene;

    @FXML
    private MenuItem menuClose;
    @FXML
    private MenuItem menuNew;

    @FXML
    private void handleBlockAction(ActionEvent event) {
        System.out.println("You clicked me!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

        //ContextMenu for deleting block as a whole
        contextMenuConnect = new ContextMenu();

        MenuItem itemDelConnect = new MenuItem("Delete");
        itemDelConnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                blockScene.getChildren().remove(selectedConnect);
            }
        });

        contextMenuConnect.getItems().add(itemDelConnect);

        //main menu actions
        menuClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        menuNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                blockScene.getChildren().clear();
            }
        });

        //contextMenu for Port (connection and set value)
        contextMenuPort = new ContextMenu();

        //change value on port
        MenuItem itemChangeVal = new MenuItem("Change value");
        itemChangeVal.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                createDialog();
            }
        });
        
        //connection
        MenuItem itemConnect = new MenuItem("Connect");
        itemConnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!connecting) {
                    connecting = true;
                } else {
                    if(!selectetGUIport1.getPort().getType().getName().equals(selectetGUIport2.getPort().getType().getName())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Incompatible types");
                        alert.setContentText("Can't connect "+ selectetGUIport1.getPort().getType().getName() +
                                " with " + selectetGUIport2.getPort().getType().getName());
                        alert.showAndWait();
                    }
                    else if(selectetGUIport1.getPort().getId() == selectetGUIport2.getPort().getId()){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Connection to itself");
                        alert.setContentText("Can't connect port to itself");
                        alert.showAndWait();
                    }
                    else if(selectetGUIport1.getPort().getName().equals(selectetGUIport2.getPort().getName())){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Incompatible ports");
                        alert.setContentText("Can't connect "+ selectetGUIport1.getPort().getName()+" and "+
                        selectetGUIport2.getPort().getName());
                        alert.showAndWait();
                    }
                    else {
                        GUIConnection line = new GUIConnection(selectedGroup, selectedGroup1, selectetGUIport1, selectetGUIport2);
                        line.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                            @Override
                            public void handle(ContextMenuEvent event) {
                                contextMenuConnect.show(line, event.getScreenX(), event.getScreenY());
                                selectedConnect = line;
                            }
                        });
                        blockScene.getChildren().add(line);
                    }
                    selectetGUIport1 = null;
                    selectetGUIport2 = null;
                    connecting = false;
                }
            }
        });

        // Add MenuItem to ContextMenu
        contextMenuPort.getItems().addAll(itemChangeVal, itemConnect);

        //ContextMenu for deleting block as a whole
        contextMenuBlock = new ContextMenu();

        MenuItem itemDelBlock = new MenuItem("Delete");
        itemDelBlock.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                blockScene.getChildren().remove(selectedGroup);
            }
        });

        contextMenuBlock.getItems().add(itemDelBlock);


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
        //remove select of block
        if(selected && block.getAbstractBlockClass().equals(selectedBlock.getAbstractBlockClass())) {
            selected = false;
            selectedBlock = null;
            block.setStyle("");
        }
        else {
            //remember clicked block
            selected = true;
            if(selectedBlock != null)
                selectedBlock.setStyle("");
            selectedBlock = block;
            block.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(255,0,0,0.8), 15, 0, 0, 0)");
        }
    }

    //create block on scene
    private void handleSceneClick(MouseEvent event){
        if(selected){
            //select right image for block
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
            Image imageBlock = new Image(url,125, 93.75, false, true);
            Image imagePort = new Image("file:lib/Port.png",25, 25, true, true);

            //group block inports and outports
            Group group = new Group();
            GUIBlock block = new GUIBlock(selectedBlock.getAbstractBlockClass(),event,imageBlock);
            block.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                @Override
                public void handle(ContextMenuEvent contextMenuEvent) {
                    contextMenuBlock.show(group, contextMenuEvent.getScreenX(),contextMenuEvent.getScreenY());
                    selectedGroup = group;
                    contextMenuPort.hide();
                }
            });
            group.getChildren().add(block);

            //create inports
            double offset = 93.75/(block.getBlock().getAllInPorts().size()*2);
            double actOffset;
            if (block.getBlock().getAllInPorts().size() == 1)
                actOffset = 0;
            else
                actOffset = -offset;
            for (Port inPort: block.getBlock().getAllInPorts()) {
                GUIPort port = new GUIPort(inPort,event,imagePort,actOffset);
                group.getChildren().add(port);
                port.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                    @Override
                    public void handle(ContextMenuEvent contextMenuEvent) {
                        portContext(contextMenuEvent,port,group);
                    }
                });
                actOffset += offset*2;
            }

            //create outports
            offset = 93.75/(block.getBlock().getAllOutPorts().size()*2);
            if (block.getBlock().getAllOutPorts().size() == 1)
                actOffset = 0;
            else
                actOffset = -offset;
            for (Port outPort: block.getBlock().getAllOutPorts()) {
                GUIPort port = new GUIPort(outPort,event,imagePort,actOffset);
                group.getChildren().add(port);
                actOffset += offset*2;
                port.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                    @Override
                    public void handle(ContextMenuEvent contextMenuEvent) {
                        portContext(contextMenuEvent,port,group);
                    }
                });
            }

            group.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    orgSceneX = event.getSceneX();
                    orgSceneY = event.getSceneY();
                    orgTranslateX = ((Group)(event.getSource())).getTranslateX();
                    orgTranslateY = ((Group)(event.getSource())).getTranslateY();
                }
            });
            group.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    double offsetX = event.getSceneX() - orgSceneX;
                    double offsetY = event.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;
                    //cant go on menu
                    if(newTranslateX <= 13){
                        newTranslateX = 13;
                    }
                    if(newTranslateY <= 0){
                        newTranslateY = 0;
                    }

                    ((Group)(event.getSource())).setTranslateX(newTranslateX);
                    ((Group)(event.getSource())).setTranslateY(newTranslateY);
                }
            });
            //you cant spawn on menu
            group.setTranslateX(event.getX() - 125.0/2.0);
            group.setTranslateY(event.getY() - 93.75/2.0);
            if((event.getX() - 125) <= 0){
                group.setTranslateX(13);
            }
            if((event.getY() - 93.75) <= 0){
                group.setTranslateY(0);
            }
            blockScene.getChildren().add(group);

        }

    }

    private Dialog createDialog(){
        Dialog dialog = new Dialog();
        dialog.setTitle("Change value: "+ selectedPort.getType().getName());
        dialog.setResizable(true);
        GridPane grid = new GridPane();
        TextField text1 = new TextField();
        TextField text2 = new TextField();
        Label label1;
        Label label2;

        switch (selectedPort.getType().getName()){
            case "Human":
                label1 = new Label("Weight: ");
                label2 = new Label("Stamina: ");
                text1.setText(Double.toString(selectedPort.getType().getValue("weight")));
                text2.setText(Double.toString(selectedPort.getType().getValue("stamina")));

                grid.add(label1, 1, 1);
                grid.add(text1, 2, 1);
                grid.add(label2, 1, 2);
                grid.add(text2, 2, 2);
                break;
            case "Time":
                label1 = new Label("Hours: ");
                label2 = new Label("Minutes: ");
                text1.setText(Double.toString(selectedPort.getType().getValue("hours")));
                text2.setText(Double.toString(selectedPort.getType().getValue("minutes")));

                grid.add(label1, 1, 1);
                grid.add(text1, 2, 1);
                grid.add(label2, 1, 2);
                grid.add(text2, 2, 2);
                break;
            case "Food":
                label1 = new Label("Calories: ");
                text1.setText(Double.toString(selectedPort.getType().getValue("calories")));

                grid.add(label1, 1, 1);
                grid.add(text1, 2, 1);
                break;

        }

        dialog.getDialogPane().setContent(grid);
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, double[]>() {
             @Override
             public double[] call(ButtonType b) {

                 if (b == buttonTypeOk) {
                      double []arr = {Double.parseDouble(text1.getText()),Double.parseDouble(text2.getText())};
                      return arr;
                 }

                return null;
            }
        });

        Optional<double[]> result = dialog.showAndWait();
        if (result.isPresent()){
             switch (selectedPort.getType().getName()){
                 case "Human":
                     selectedPort.getType().update("weight",result.get()[0]);
                     selectedPort.getType().update("stamina",result.get()[1]);
                 case "Time":
                     selectedPort.getType().update("hours",result.get()[0]);
                     selectedPort.getType().update("minutes",result.get()[1]);
                 case "Food":
                     selectedPort.getType().update("calories",result.get()[0]);
             }
        }
        return dialog;
    }

    private void portContext(ContextMenuEvent event,GUIPort port, Group group){
        contextMenuPort.show(port, event.getScreenX(),event.getScreenY());
        selectedPort = port.getPort();
        if(!connecting) {
            selectedGroup = group;
            selectetGUIport1 = port;
        }
        else {
            selectedGroup1 = group;
            selectetGUIport2 = port;
        }
        contextMenuBlock.hide();
    }
}