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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private MenuBlock selectedBlock = null;
    private Port selectedPort;
    private GUIConnection selectedConnect;
    private GUIPort selectetGUIport1;
    private GUIPort selectetGUIport2;
    private GUIBlock selectedGUIBlock;
    private boolean connecting = false;
    private boolean selected;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;
    private boolean doubleClick;
    private ContextMenu contextMenuPort;
    private ContextMenu contextMenuBlock;
    private ContextMenu contextMenuConnect;
    private Group selectedGroup1;
    private Group selectedGroup2;
    private Scheme scheme;

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
    private MenuItem menuSave;
    @FXML
    private MenuItem menuOpen;


    @Override
    public void initialize(URL url, ResourceBundle rb){
        //class for storing all block and connections
        scheme = new Scheme();
        //ContextMenu for deleting block as a whole
        contextMenuConnect = new ContextMenu();

        MenuItem itemDelConnect = new MenuItem("Delete");
        itemDelConnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                blockScene.getChildren().remove(selectedConnect);
                scheme.getConnections().remove(selectedConnect);
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
                scheme.clearScheme();
            }
        });

        menuSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save file");
                fileChooser.setInitialFileName("simulation.ser");
                File file = fileChooser.showSaveDialog(new Stage());
                scheme.saveFile(file);
            }
        });

        menuOpen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open file");
                File file = fileChooser.showOpenDialog(new Stage());
                if(file != null){
                    try {
                        FileInputStream stream = new FileInputStream(file);
                        ObjectInputStream objStream = new ObjectInputStream(stream);
                        scheme.clearScheme();
                        blockScene.getChildren().clear();
                        SerializableData input;
                        input = (SerializableData) objStream.readObject();
                        int index = 0;
                        ArrayList<Group> groups = new ArrayList<>();
                        while (!input.className.equals("EOF")){
                            switch (input.className){
                                case "BlockCook":
                                case "BlockSleep":
                                case "BlockEat":
                                case "BlockWork":
                                case "BlockSport":
                                    index = 0;
                                    createBlock(input.className,false,input.value1,input.value2);
                                    index++;
                                    break;
                                case "in":
                                case "out":
                                    GUIPort port = (GUIPort) selectedGroup1.getChildren().get(index);
                                    port.getPort().setId(input.id);
                                    switch (input.type){
                                        case "Human":
                                            port.getPort().getType().update("weight",input.value1);
                                            port.getPort().getType().update("stamina",input.value2);
                                            break;
                                        case "Time":
                                            port.getPort().getType().update("hours",input.value1);
                                            port.getPort().getType().update("minutes",input.value2);
                                            break;
                                        case "Food":
                                            port.getPort().getType().update("weight",input.value1);
                                            break;
                                    }
                                    if(input.connectedTo != -1){
                                        for (Group group:groups) {
                                            for (int j = 1; j < 4; j++) {
                                                GUIPort tempPort = (GUIPort)group.getChildren().get(j);
                                                if(tempPort.getPort().getId() == input.connectedTo && !scheme.connectionExists(port.getPort(),tempPort.getPort())){
                                                    selectedGroup2 = group;
                                                    selectetGUIport1 = port;
                                                    selectetGUIport2 = tempPort;
                                                    makeConnection();
                                                    break;
                                                }
                                            }
                                        }
                                        groups.add(selectedGroup1);
                                    }
                                    index++;
                                    break;
                                default:
                                    break;
                            }
                            input = (SerializableData) objStream.readObject();
                        }

                    }
                    catch (IOException|ClassNotFoundException i){
                        displayError("File error","Unable to open file " + i.toString());
                    }

                }
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
                    makeConnection();
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
                blockScene.getChildren().remove(selectedGroup1);
                for (Port inPort:selectedGUIBlock.getBlock().getAllInPorts()) {
                    GUIConnection possibleConn = scheme.getConnectionByPort(inPort);
                    while (possibleConn != null) {
                        blockScene.getChildren().remove(possibleConn);
                        scheme.getConnections().remove(possibleConn);
                        possibleConn = scheme.getConnectionByPort(inPort);
                    }
                }
                for (Port outPort: selectedGUIBlock.getBlock().getAllOutPorts()) {
                    GUIConnection possibleConn = scheme.getConnectionByPort(outPort);
                    while (possibleConn != null) {
                        blockScene.getChildren().remove(possibleConn);
                        scheme.getConnections().remove(possibleConn);
                        possibleConn = scheme.getConnectionByPort(outPort);
                    }
                }
                scheme.getBlocks().remove(selectedGUIBlock);
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
            if(!doubleClick){
                block.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,255,0,0.8), 15, 0, 0, 0)");
                doubleClick = true;
            }
            else {
                selected = false;
                selectedBlock = null;
                block.setStyle("");
                doubleClick = false;
            }
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
            if(!doubleClick){
                selected = false;
                selectedBlock.setStyle("");
                doubleClick = false;
            }
            createBlock(selectedBlock.getAbstractBlockClass().getSimpleName(),true,event.getX(),event.getY());
            if(!doubleClick)
                selectedBlock = null;
        }

    }
    private void createBlock(String type,boolean fromMenu,double x, double y){
        String url;
        switch (type){
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
        GUIBlock block = new GUIBlock(type,imageBlock);
        block.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent contextMenuEvent) {
                contextMenuBlock.show(group, contextMenuEvent.getScreenX(),contextMenuEvent.getScreenY());
                selectedGroup1 = group;
                selectedGUIBlock = block;
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
            GUIPort port = new GUIPort(inPort,imagePort,actOffset);
            group.getChildren().add(port);
            port.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                @Override
                public void handle(ContextMenuEvent contextMenuEvent) {
                    portContext(contextMenuEvent,port,group);
                }
            });
            port.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(connecting){
                        selectedGroup2 = group;
                        selectetGUIport2 = port;
                        makeConnection();
                    }
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
            GUIPort port = new GUIPort(outPort,imagePort,actOffset);
            group.getChildren().add(port);
            actOffset += offset*2;
            port.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                @Override
                public void handle(ContextMenuEvent contextMenuEvent) {
                    portContext(contextMenuEvent,port,group);
                }
            });
            port.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(connecting){
                        selectedGroup2 = group;
                        selectetGUIport2 = port;
                        makeConnection();
                    }
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
                block.getBlock().setCoordinates(newTranslateX,newTranslateY);
            }
        });
        //you cant spawn on menu
        if(fromMenu) {
            group.setTranslateX(x - 125.0 / 2.0);
            group.setTranslateY(y - 93.75 / 2.0);
            if ((x - 125) <= 0) {
                group.setTranslateX(13);
            }
            if ((y - 93.75) <= 0) {
                group.setTranslateY(0);
            }
        }
        else {
            group.setTranslateX(x);
            group.setTranslateY(y);
            selectedGroup1 = group;
        }
        block.getBlock().setCoordinates(group.getTranslateX(), group.getTranslateY());
        scheme.addBlock(block);
        blockScene.getChildren().add(group);
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

        dialog.setResultConverter(new Callback<ButtonType, ArrayList<Double>>() {
             @Override
             public ArrayList<Double> call(ButtonType b) {

                 if (b == buttonTypeOk) {
                      ArrayList<Double> arr = new ArrayList<>();
                      arr.add(Double.parseDouble(text1.getText()));
                      arr.add(Double.parseDouble(text2.getText()));
                      return arr;
                 }

                return null;
            }
        });

        Optional<ArrayList<Double>> result = dialog.showAndWait();
        if (result.isPresent()){
             selectedPort.update(selectedPort.getType().getName(),result.get());
        }
        return dialog;
    }

    private void portContext(ContextMenuEvent event,GUIPort port, Group group){
        contextMenuPort.show(port, event.getScreenX(),event.getScreenY());
        selectedPort = port.getPort();
        if(!connecting) {
            selectedGroup1 = group;
            selectetGUIport1 = port;
        }
        else {
            selectedGroup2 = group;
            selectetGUIport2 = port;
        }
        contextMenuBlock.hide();
    }

    private void displayError(String title,String messg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(messg);
        alert.showAndWait();
    }

    private void makeConnection(){
        if(!selectetGUIport1.getPort().getType().getName().equals(selectetGUIport2.getPort().getType().getName())) {
            displayError("Incompatible types","Can't connect "+ selectetGUIport1.getPort().getType().getName() +
                    " with " + selectetGUIport2.getPort().getType().getName());
        }
        else if(selectetGUIport1.getPort().getId() == selectetGUIport2.getPort().getId()){
            displayError("Connection to itself","Can't connect port to itself");
        }
        else if(selectetGUIport1.getPort().getName().equals(selectetGUIport2.getPort().getName())){
            displayError("Incompatible ports","Can't connect "+ selectetGUIport1.getPort().getName()+
                    " and "+ selectetGUIport2.getPort().getName());
        }
        else if (scheme.connectionExists(selectetGUIport1.getPort(),selectetGUIport2.getPort())){
            displayError("Connection exists","Connection already exists");
        }
        else {
            GUIConnection line;
            if(selectetGUIport1.getPort().getName().equals("out"))
                line = new GUIConnection(selectedGroup1, selectedGroup2, selectetGUIport1, selectetGUIport2);
            else
                line = new GUIConnection(selectedGroup2,selectedGroup1, selectetGUIport2, selectetGUIport1);
            scheme.addConnection(line);
            line.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                @Override
                public void handle(ContextMenuEvent event) {
                    contextMenuConnect.show(line, event.getScreenX(), event.getScreenY());
                    selectedConnect = line;
                }
            });
            blockScene.getChildren().add(line);
        }
        selectedGroup2 = null;
        selectetGUIport1 = null;
        selectetGUIport2 = null;
        connecting = false;
    }
}