package gui;

import components.AbstractBlock;
import components.Connection;
import components.Port;
import components.SerializableData;
import gui.GUIBlock;

import java.io.*;
import java.util.ArrayList;

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
                    objStream.writeObject(data);
                }
                for (Port outPort:block.getBlock().getAllOutPorts()) {
                    SerializableData dataPort = new SerializableData();
                    dataPort.convertPort(outPort,searchConnectTo(outPort));
                    objStream.writeObject(data);
                }
                data.connectedTo = -1;
                data.className = "EOF";
                data.type = "EOF";
                data.value1 = -1.0;
                data.value2 = -1.0;
                data.id = -1;
                objStream.writeObject(data);
            }
            objStream.close();
            stream.close();
        }
        catch (IOException i){
            i.printStackTrace();
            return false;
        }

        return true;
    }

    private int searchConnectTo(Port port){
        for (GUIConnection conn: this.connections) {
            if(conn.getConnect().getIn().getId() == port.getId()){
                return conn.getConnect().getOut().getId();
            }
            else if(conn.getConnect().getOut().getId() == port.getId()){
                return conn.getConnect().getIn().getId();
            }

        }
        return -1;
    }
}
