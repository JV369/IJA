package gui;

import components.Port;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 * Controller pro grafické operace nad schématem
 * @author Jan Vávra (xvavra20)
 */
public class ControlerScheme{

    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;
    private GUIConnection selectedConnect;
    private GUIPort selectetGUIport1;
    private GUIBlock selectedGUIBlock;
    private Group selectedGroup1;
    private boolean connecting = false;

    private Scheme scheme;

    private ContextMenu contextMenuPortIn;
    private ContextMenu contextMenuPortOut;
    private ContextMenu contextMenuBlock;
    private ContextMenu contextMenuConnect;

    private AnchorPane blockScene;

    /**
     * Inicializuje controller
     * @param blockScene scéna
     */
    @FXML
    public void initialize(AnchorPane blockScene){
        scheme = new Scheme();
        this.blockScene = blockScene;
        //ContextMenu for deleting block as a whole
        contextMenuConnect = new ContextMenu();

        MenuItem itemDelConnect = new MenuItem("Delete");
        itemDelConnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GUIPort connectedPort = findRelated(selectedConnect.getConnect().getIn().getId());
                if(connectedPort != null) {
                    connectedPort.setChanged();
                }
                blockScene.getChildren().remove(selectedConnect);
                scheme.getConnections().remove(selectedConnect);
            }
        });

        contextMenuConnect.getItems().add(itemDelConnect);


        //contextMenu for Port (connection and set value)
        contextMenuPortIn = new ContextMenu();
        contextMenuPortOut = new ContextMenu();

        //change value on port
        MenuItem itemChangeVal = new MenuItem("Change value");
        itemChangeVal.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                selectetGUIport1.createDialog();
            }
        });

        //connection
        MenuItem itemConnectI = new MenuItem("Connect");
        itemConnectI.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!connecting) {
                    connecting = true;
                }
            }
        });
        MenuItem itemConnectO = new MenuItem("Connect");
        itemConnectO.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!connecting) {
                    connecting = true;
                }
            }
        });

        // Add MenuItem to ContextMenu
        contextMenuPortIn.getItems().addAll(itemChangeVal, itemConnectI);
        contextMenuPortOut.getItems().addAll(itemConnectO);

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
                        GUIPort connectedPort = findRelated(possibleConn.getConnect().getIn().getId());
                        if(connectedPort != null) {
                            connectedPort.setChanged();
                        }
                        possibleConn = scheme.getConnectionByPort(outPort);
                    }
                }
                scheme.getBlocks().remove(selectedGUIBlock);
            }
        });

        contextMenuBlock.getItems().add(itemDelBlock);
    }

    /**
     * Metoda vrací třídu pro operace nad schématem
     * @return třídu Scheme
     * @see Scheme
     */
    public Scheme getScheme() {
        return scheme;
    }

    /**
     * Vyčistí všechny bloky a spoje na scéně
     */
    public void clearScene(){
        scheme.clearScheme();
        blockScene.getChildren().clear();
    }

    /**
     * Metoda pro vytvoření bloku a vložení ho do scény
     * @param type název třídy bloku, který chceme vytvořit
     * @param fromMenu true pokud vytváříme blok z menu, false pokud vytváříme blok se souboru
     * @param x souřadnice, kde máme vytvořit blok na scéně
     * @param y souřadnice, kde máme vytvořit blok na scéně
     * @return vrací seskupení portů a bloku v Group
     * @see Group
     */
    public Group createBlock(String type, boolean fromMenu, double x, double y){
        String url;
        switch (type){
            case "BlockCook":
                url = "images/BlockCook.png";
                break;
            case "BlockSleep":
                url = "images/BlockSleep.png";
                break;
            case "BlockEat":
                url = "images/BlockEat.png";
                break;
            case "BlockWork":
                url = "images/BlockWork.png";
                break;
            case "BlockSport":
                url = "images/BlockSport.png";
                break;
            default:
                url = "images/BlockCook.png";
        }
        Image imageBlock = new Image(url,125, 93.75, false, true);
        Image imagePort = new Image("images/Port.png",25, 25, true, true);

        //group block inports and outports
        Group group = new Group();
        GUIBlock block = new GUIBlock(type,imageBlock);
        block.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                event.consume();
            }
        });
        block.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent contextMenuEvent) {
                contextMenuBlock.show(group, contextMenuEvent.getScreenX(),contextMenuEvent.getScreenY());
                selectedGroup1 = group;
                selectedGUIBlock = block;
                contextMenuPortIn.hide();
                contextMenuPortOut.hide();
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
            GUIPort port = new GUIPort(inPort,actOffset);
            group.getChildren().add(port);
            port.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                @Override
                public void handle(ContextMenuEvent contextMenuEvent) {
                    contextMenuPortIn.show(port, contextMenuEvent.getScreenX(),contextMenuEvent.getScreenY());
                    portContext(port,group);
                }
            });
            port.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(connecting){
                        makeConnection(selectedGroup1,group,selectetGUIport1,port);
                    }
                    event.consume();
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
            GUIPort port = new GUIPort(outPort,actOffset);
            group.getChildren().add(port);
            actOffset += offset*2;
            port.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                @Override
                public void handle(ContextMenuEvent contextMenuEvent) {
                    contextMenuPortOut.show(port, contextMenuEvent.getScreenX(),contextMenuEvent.getScreenY());
                    portContext(port,group);
                }
            });
            port.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(connecting){
                        makeConnection(selectedGroup1,group,selectetGUIport1,port);
                    }
                    event.consume();
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
                event.consume();
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
                event.consume();
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
        }
        block.getBlock().setCoordinates(group.getTranslateX(), group.getTranslateY());
        scheme.addBlock(block);
        blockScene.getChildren().add(group);
        return group;
    }


    /**
     * Metoda pro reakci na kliknutí na port
     * @param port kliknutý port
     * @param group seskupení, ve kterém se port nachází
     */
    private void portContext(GUIPort port, Group group){
        if(!connecting) {
            selectedGroup1 = group;
            selectetGUIport1 = port;
        }
        contextMenuBlock.hide();
    }

    /**
     * Zobrazí okno s chybovým hlášením
     * @param title nadpis chyby
     * @param messg text chyby
     */
    public void displayError(String title,String messg){
        Alert alert = new Alert(Alert.AlertType.ERROR,messg);
        alert.setTitle(title);
        alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));
        alert.showAndWait();
    }

    /**
     * Vytvoří spoj mezi porty
     * @param group1 seskupení, do kterého patří port1
     * @param group2 seskupení, do kterého patří port2
     * @param port1 port, pro který chceme vytvořit spojení
     * @param port2 port, pro který chceme vytvořit spojení
     */
    public void makeConnection(Group group1, Group group2, GUIPort port1, GUIPort port2){
        if(!port1.getPort().getType().getName().equals(port2.getPort().getType().getName())) {
            displayError("Incompatible types","Can't connect "+ port1.getPort().getType().getName() +
                    " with " + port2.getPort().getType().getName());
        }
        else if(port1.getPort().getId() == port2.getPort().getId()){
            displayError("Connection to itself","Can't connect port to itself");
        }
        else if(port1.getPort().getName().equals(port2.getPort().getName())){
            displayError("Incompatible ports","Can't connect "+ port1.getPort().getName()+
                    " and "+ port2.getPort().getName());
        }
        else if (scheme.connectionExists(port1.getPort(),port2.getPort())){
            displayError("Connection exists","Connection already exists");
        }
        else if(scheme.getConnectionByPort(port1.getPort()) != null){
            displayError("Connection exists","Can't make multiple connections from port");
        }
        else if(scheme.getConnectionByPort(port2.getPort()) != null){
            displayError("Connection exists","Can't make multiple connections from port");
        }
        else {
            GUIConnection line;
            if(port1.getPort().getName().equals("out")) {
                line = new GUIConnection(group1, group2, port1, port2);
                if(!port2.getChanged())
                    port2.setChanged();
            }
            else {
                line = new GUIConnection(group2, group1, port2, port1);
                if(!port1.getChanged())
                    port1.setChanged();
            }
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
        connecting = false;
    }

    /**
     * Najde port podle id
     * @param id id portu, který chceme najít
     * @return Pohled na port
     */
    private GUIPort findRelated(int id){
        for (int i = 0; i < blockScene.getChildren().size(); i++) {
            if(blockScene.getChildren().get(i).getClass().getSimpleName().equals("Group")) {
                Group group = (Group) blockScene.getChildren().get(i);
                for (int j = 1; j < group.getChildren().size(); j++) {
                    GUIPort port = (GUIPort) group.getChildren().get(j);
                    if (port.getPort().getId() == id && port.getPort().getName().equals("in")) {
                        return port;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Metoda detekuje, u jakých portů nebyly změněny
     * implicitní hodnoty a dá možnost tyto hodnoty změnit
     */
    public void detectUnsetPorts(){
        for(int i = 0 ; i < blockScene.getChildren().size() ; i++){
            if(blockScene.getChildren().get(i).getClass().getSimpleName().equals("Group")){
                Group group = (Group) blockScene.getChildren().get(i);
                for (int j = 1; j < group.getChildren().size() ;j++){
                    GUIPort port = (GUIPort) group.getChildren().get(j);
                    if(!port.getChanged() && port.getPort().getName().equals("in")){
                        port.setFill(Color.BLUE);
                        selectetGUIport1 = port;
                        port.createDialog();
                    }
                }
            }
        }
    }
}
