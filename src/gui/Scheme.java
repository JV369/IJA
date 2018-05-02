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

/**
 * Třída pro práci s daty schématu
 * @author Jan Vávra (xvavra20)
 * @author Aleš Postulka (xpostu03)
 */
public class Scheme {
    private ArrayList<GUIBlock> blocks;
    private ArrayList<GUIConnection> connections;

    /**
     * Konstruktor třídy Scheme
     */
    public Scheme(){
        this.blocks = new ArrayList<>();
        this.connections = new ArrayList<>();
    }

    /**
     * Přidá blok do pole bloků
     * @param block blok pro přidání
     * @return false pokud blok už existuje v poli, true pokud jsme blok přidali
     */
    public boolean addBlock(GUIBlock block){
        if(!this.blocks.contains(block)) {
            this.blocks.add(block);
            return true;
        }
        return false;
    }

    /**
     * Přidá spoj do pole spojů
     * @param connection spoj pro přidání
     * @return false pokud spoj už existuje v poli, true pokud jsme spoj přidali
     */
    public boolean addConnection(GUIConnection connection){
        if(!this.connections.contains(connection)){
            this.connections.add(connection);
        }
        return false;
    }

    /**
     * Metoda pro přístup k poli bloků
     * @return pole bloků
     */
    public ArrayList<GUIBlock> getBlocks() {
        return blocks;
    }

    /**
     * Metoda pro přístup k poli spojů
     * @return pole spojů
     */
    public ArrayList<GUIConnection> getConnections() {
        return connections;
    }

    /**
     * Vrátí první spoj ve kterém se vyskytuje port
     * @param port port, podle kterého vyhledáváme spoj
     * @return spoj pokud existuje, jinak null
     */
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

    /**
     * Metoda pro zjištění jestli spoj mezi porty existuje
     * @param port1 port, podle kterrého vyhledáváme spoj
     * @param port2 port, podle kterrého vyhledáváme spoj
     * @return true pokud spoj mezi porty existuje, jinak false
     */
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

    /**
     * Vyčistí pole bloků a pole spojů
     */
    public void clearScheme(){
        this.blocks.clear();
        this.connections.clear();
    }

    /**
     * Metoda pro uložení aktuálního schémata
     * @param file soubor, do kterého budeme data ukládat
     * @return true pokud se podařilo data uložit, jinak false
     */
    public boolean saveFile(File file){
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

    /**
     * Metoda vyhledá všechny id portů, ke kterým je připojený parametr port
     * @param port port, pro který vyhledáváme všechny spoje
     * @return pole id portů, ke kterým je port připojený
     */
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

    /**
     * Najde koncové bloky (bloky, které na výstupním portu nemají spojení).
     * @return seznam koncových bloků
     */
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
        }
        return endBlocks;
    }

    /**
     * Nastaví barvu stínu bloku na modrou a vypočítá jej.
     * @param block vypočítávaný blok
     */
    public void executeBlock(GUIBlock block){
        block.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,200,255,0.8), 15, 0, 0, 0)");
        block.getBlock().execute();
    }

    /**
     * Vytvoří zásobník a naplní jej bloky ze schématu.
     * Na vrcholu zásobníku bude blok, který se má vypočítat jako první.
     * @param block Koncový blok (bude na dně zásobníku - počítá se poslední)
     * @return Zásobník naplněný bloky
     */
    public Stack<GUIBlock> fillStack(GUIBlock block){
        Stack<GUIBlock> blockStack = new Stack<>();
        LinkedList<GUIBlock> blockQueue = new LinkedList<>();
        GUIConnection connection;
        Port p;

        blockQueue.addLast(block);
        while(blockQueue.size() != 0) {
            //prohledání všech vstupních portů bloku
            for(int i = blockQueue.peekFirst().getBlock().getAllInPorts().size()-1; i >=0 ; i--){
                Port port = blockQueue.peekFirst().getBlock().getInPort(i);
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

    /**
     * Metoda detekuje, jestli mezi aktuálním spojením bloků neexistuje cyklus
     * @return true pokud cyklus existuje, jinak false
     */
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
