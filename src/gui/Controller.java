package gui;

import components.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Hlavní controller pro aplikaci
 * @author Jan Vávra
 */
public class Controller extends BorderPane {

    private MenuBlock selectedBlock = null;
    private boolean selected;

    private boolean doubleClick;

    private Stack<GUIBlock> blockStack;
    private ArrayList<GUIBlock> endBlocks;

    @FXML
    private VBox blockMenu;

    @FXML
    private MenuItem menuClose;
    @FXML
    private MenuItem menuNew;
    @FXML
    private MenuItem menuSave;
    @FXML
    private MenuItem menuOpen;
    @FXML
    private MenuItem menuRun;
    @FXML
    private MenuItem menuStepRun;
    @FXML
    private MenuItem menuNextStep;

    @FXML
    private AnchorPane GUIScheme;
    private ControlerScheme controlerScheme;

    /**
     * Inicializace základních prvků (menu, tlačítka v menu) a
     * přidání funkcionality jednotlivým prvkům
     */
    @FXML
    public void initialize(){
        controlerScheme = new ControlerScheme();
        controlerScheme.initialize(GUIScheme);

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
                controlerScheme.clearScene();
            }
        });

        menuSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save file");
                fileChooser.setInitialFileName("simulation.ser");
                File file = fileChooser.showSaveDialog(new Stage());
                controlerScheme.getScheme().saveFile(file);
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
                        controlerScheme.clearScene();
                        SerializableData input;
                        input = (SerializableData) objStream.readObject();
                        int index = 0;
                        ArrayList<Group> groups = new ArrayList<>();
                        Group actGroup = null;
                        while (!input.className.equals("EOF")){
                            switch (input.className){
                                case "BlockCook":
                                case "BlockSleep":
                                case "BlockEat":
                                case "BlockWork":
                                case "BlockSport":
                                    index = 0;
                                    actGroup = controlerScheme.createBlock(input.className,false,input.value1,input.value2);
                                    index++;
                                    break;
                                case "in":
                                case "out":
                                    GUIPort port = (GUIPort) actGroup.getChildren().get(index);
                                    port.getPort().setId(input.id);
                                    switch (input.type){
                                        case "Human":
                                            if(!(port.getPort().getType().getValue("weight") == input.value1)) {
                                                port.getPort().getType().update("weight", input.value1);
                                                port.setChanged();
                                            }
                                            if(!(port.getPort().getType().getValue("stamina") == input.value2)) {
                                                port.getPort().getType().update("stamina", input.value2);
                                                port.setChanged();
                                            }
                                            break;
                                        case "Time":
                                            if(!(port.getPort().getType().getValue("hours") == input.value1)) {
                                                port.getPort().getType().update("hours", input.value1);
                                                port.setChanged();
                                            }
                                            if(!(port.getPort().getType().getValue("minutes") == input.value2)) {
                                                port.getPort().getType().update("minutes", input.value2);
                                                port.setChanged();
                                            }
                                            break;
                                        case "Food":
                                            if(!(port.getPort().getType().getValue("calories") == input.value1)) {
                                                port.getPort().getType().update("calories", input.value1);
                                                port.setChanged();
                                            }
                                            break;
                                    }
                                    if(input.connectedTo.size() != 0){
                                        for (Group group:groups) {
                                            for (int j = 1; j < 4; j++) {
                                                for (int connected: input.connectedTo) {
                                                    GUIPort tempPort = (GUIPort)group.getChildren().get(j);
                                                    if(tempPort.getPort().getId() == connected && !controlerScheme.getScheme().connectionExists(port.getPort(),tempPort.getPort())) {
                                                        controlerScheme.makeConnection(actGroup,group,port,tempPort);
                                                    }
                                                }
                                            }
                                        }
                                        groups.add(actGroup);
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
                        controlerScheme.displayError("File error","Unable to open file " + i.toString());
                    }

                }
            }
        });

        menuRun.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                menuNextStep.setDisable(true);
                if(controlerScheme.getScheme().detectCycle()){
                    controlerScheme.displayError("Cycle detected!","There's a cycle in scheme. Delete connection to break the cycle.");
                    return;
                }
                endBlocks = controlerScheme.getScheme().findEndBlocks();
                //System.out.println(endBlocks);
                controlerScheme.detectUnsetPorts();
                for(GUIBlock block : endBlocks){
                    blockStack = controlerScheme.getScheme().fillStack(block);
                    while(!blockStack.empty()) {
                        try {
                            controlerScheme.getScheme().executeBlock(blockStack);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        blockStack.pop().setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,255,0,0.8), 15, 0, 0, 0)");
                    }
                }
            }

        });

        menuStepRun.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(controlerScheme.getScheme().detectCycle()){
                    controlerScheme.displayError("Cycle detected!","There's a cycle in scheme. Delete connection to break the cycle.");
                    return;
                }
                endBlocks = controlerScheme.getScheme().findEndBlocks();
                //System.out.println(endBlocks);
                controlerScheme.detectUnsetPorts();
                if(!endBlocks.isEmpty()) {
                    blockStack = controlerScheme.getScheme().fillStack(endBlocks.get(0));
                    endBlocks.remove(0);
                    if (!blockStack.empty()) {
                        try {
                            controlerScheme.getScheme().executeBlock(blockStack);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        menuNextStep.setDisable(false);
                    }
                }

            }
        });

        menuNextStep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                blockStack.pop().setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,255,0,0.8), 15, 0, 0, 0)");
                if(!blockStack.empty()) {
                    try {
                        controlerScheme.getScheme().executeBlock(blockStack);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    if(!endBlocks.isEmpty()) {
                        blockStack = controlerScheme.getScheme().fillStack(endBlocks.get(0));
                        endBlocks.remove(0);
                        if (!blockStack.empty()) {
                            try {
                                controlerScheme.getScheme().executeBlock(blockStack);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        menuNextStep.setDisable(true);
                    }

                }
            }
        });


        MenuBlock blockEat = new MenuBlock(BlockEat.class, new Image("images/BlockEat.png", 125, 93.75, false, true));
        MenuBlock blockSleep = new MenuBlock(BlockSleep.class, new Image("images/BlockSleep.png", 125, 93.75, false, true));
        MenuBlock blockWork = new MenuBlock(BlockWork.class, new Image("images/BlockWork.png", 125, 93.75, false, true));
        MenuBlock blockCook = new MenuBlock(BlockCook.class, new Image("images/BlockCook.png", 125, 93.75, false, true));
        MenuBlock blockSport = new MenuBlock(BlockSport.class, new Image("images/BlockSport.png", 125, 93.75, false, true));

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

        GUIScheme.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(selected){
                    if(!doubleClick){
                        selected = false;
                        selectedBlock.setStyle("");
                        doubleClick = false;
                    }
                    controlerScheme.createBlock(
                             selectedBlock.getAbstractBlockClass().getSimpleName(),true,event.getX(),event.getY()
                    );
                    if(!doubleClick)
                        selectedBlock = null;
                }
            }
        });


    }

    /**
     * Metoda reaguje na zmáčknutí bloku v levém menu
     * @param block blok, který byl zmáčknut
     */
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




}