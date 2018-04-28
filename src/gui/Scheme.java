package gui;

import components.Port;
import components.SerializableData;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Scheme {
    private ArrayList<GUIBlock> blocks;
    private ArrayList<GUIConnection> connections;

    public Scheme(){
        this.blocks = new ArrayList<>();
        this.connections = new ArrayList<>();
    }

    public boolean addBlock(GUIBlock block){
        if(!this.blocks.contains(block)) {
            this.blocks.add(block);
            return true;
        }
        return false;
    }

    public boolean addConnection(GUIConnection connection){
        if(!this.connections.contains(connection)){
            this.connections.add(connection);
        }
        return false;
    }

    public ArrayList<GUIBlock> getBlocks() {
        return blocks;
    }

    public ArrayList<GUIConnection> getConnections() {
        return connections;
    }

    public GUIConnection getConnectionByPort(Port port){
        for (GUIConnection conn: this.connections) {
            if(port.getId() == conn.getConnect().getIn().getId())
                return conn;
            if(port.getId() == conn.getConnect().getOut().getId()){
                return conn;
            }
        }
        return null;
    }

    public boolean connectionExists(Port port1, Port port2){
        for (GUIConnection conn: this.connections) {
            if(conn.getConnect().getIn().getId() == port1.getId() && conn.getConnect().getOut().getId() == port2.getId()){
                return true;
            }
            if(conn.getConnect().getIn().getId() == port2.getId() && conn.getConnect().getOut().getId() == port1.getId()){
                return true;
            }
        }
        return false;
    }


    public void clearScheme(){
        this.blocks.clear();
        this.connections.clear();
    }

    public boolean saveFile(File file){
        if(file == null)
            return false;
        try {
            FileOutputStream stream = new FileOutputStream(file);
            ObjectOutputStream objStream = new ObjectOutputStream(stream);
            for (GUIBlock block:this.blocks) {
                SerializableData data = new SerializableData();
                data.convertBlock(block.getBlock());
                objStream.writeObject(data);
                for (Port inPort: block.getBlock().getAllInPorts()) {
                    SerializableData dataPort = new SerializableData();
                    dataPort.convertPort(inPort,searchConnectTo(inPort));
                    objStream.writeObject(dataPort);
                }
                for (Port outPort:block.getBlock().getAllOutPorts()) {
                    SerializableData dataPort = new SerializableData();
                    dataPort.convertPort(outPort,searchConnectTo(outPort));
                    objStream.writeObject(dataPort);
                }
            }
            SerializableData eof = new SerializableData();
            eof.className = "EOF";
            eof.type = "EOF";
            eof.value1 = -1.0;
            eof.value2 = -1.0;
            eof.id = -1;
            objStream.writeObject(eof);
            objStream.close();
            stream.close();
        }
        catch (IOException i){
            i.printStackTrace();
            return false;
        }

        return true;
    }

    private ArrayList<Integer> searchConnectTo(Port port){
        ArrayList<Integer> arr = new ArrayList<>();
        for (GUIConnection conn: this.connections) {
            if(conn.getConnect().getIn().getId() == port.getId()){
                arr.add(conn.getConnect().getOut().getId());
            }
            else if(conn.getConnect().getOut().getId() == port.getId()){
                arr.add(conn.getConnect().getIn().getId());
            }

        }
        return arr;
    }


    public  ArrayList<GUIBlock> findEndBlocks(){
        ArrayList<GUIBlock> endBlocks = new ArrayList<>();
        boolean connected;
        for (GUIBlock block:this.getBlocks()){
            block.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(255,255,255,0.8), 15, 0, 0, 0)");
            connected = false;
            for(Port port:block.getBlock().getAllOutPorts()){
                if(this.getConnectionByPort(port) != null){
                    connected = true;
                    break;
                }
            }
            if(!connected){
                endBlocks.add(block);
            }
            //block.getBlock().execute();
        }
        return endBlocks;
    }

    public void executeBlock(Stack<GUIBlock> blockStack) throws InterruptedException {
        /*GUIConnection connection;
        Port p;

        //prohledá všechny porty z bloku na vrcholu zásobníku
        for (Port port:blockStack.peek().getBlock().getAllInPorts()){
            connection = this.getConnectionByPort(port);
            if(connection != null) {
                //connected = true;
                p = connection.getConnect().getOut();

                boolean found = false;
                for(GUIBlock tmpBl:blocks){
                    for(Port tmpPort:tmpBl.getBlock().getAllOutPorts()){
                        if(tmpPort.equals(p)){
                            found = true;
                            break;
                        }
                    }
                    if(found){
                        blockStack.push(tmpBl);
                        this.executeBlocks(blockStack);
                        break;
                    }
                }
            }
        }
*/
        GUIBlock block = blockStack.peek();

        block.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,200,255,0.8), 15, 0, 0, 0)");
        block.getBlock().execute();
        /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Run");
        alert.setHeaderText("I am running");
        alert.setContentText(String.valueOf(block.getBlock().getId()));
        alert.showAndWait();*/

        //block.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 15, 0, 0, 0)");
        //Thread.sleep(2000);
        //return blockStack;
    }

    public Stack<GUIBlock> fillStack(GUIBlock block){
        Stack<GUIBlock> blockStack = new Stack<>();
        LinkedList<GUIBlock> blockQueue = new LinkedList<>();
        GUIConnection connection;
        Port p;

        blockQueue.addLast(block);
        while(blockQueue.size() != 0) {
            //prohledání všech vstupních portů bloku
            for (Port port : blockQueue.peekFirst().getBlock().getAllInPorts()) {
                connection = this.getConnectionByPort(port);
                //pokud je port součástí spojení, je vyhledán blok na druhé straně spojení
                if (connection != null) {
                    p = connection.getConnect().getOut();

                    //nalezeni bloku na druhé straně spojení
                    boolean found = false;
                    for (GUIBlock tmpBl : blocks) {
                        for (Port tmpPort : tmpBl.getBlock().getAllOutPorts()) {
                            if (tmpPort.equals(p)) {
                                found = true;
                                break;
                            }
                        }
                        if (found) {
                            blockQueue.addLast(tmpBl);
                            break;
                        }
                    }
                }
            }
            blockStack.push(blockQueue.pollFirst());
        }
        return blockStack;
    }

    public boolean detectCycle(){
        for (GUIConnection currConn: this.connections) {
            ArrayList<Integer> idList = new ArrayList<>();
            GUIConnection actConnect = currConn;
            Port outPort = null;
            do {
                if (idList.contains(actConnect.getConnect().getOut().getId()))
                    return true;
                idList.add(actConnect.getConnect().getOut().getId());

                for (GUIBlock block : blocks) {
                    if (block.getBlock().getAllInPorts().contains(actConnect.getConnect().getIn())) {
                        outPort = block.getBlock().getAllOutPorts().get(0);
                    }
                }
                actConnect = getConnectionByPort(outPort);
            } while (actConnect != null);
        }
        return false;
    }
}
