package components;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Třída pro přípravu dat ve schématu na uložitelné data
 * @author Jan Vávra (xvavra20)
 */
public class SerializableData implements Serializable{
    public String className;
    public String type;
    public double value1;
    public double value2;
    public int id;
    public ArrayList<Integer> connectedTo;

    /**
     * Přetransformuje blok na uložitelné hodnoty a uloží je do této třídy
     * @param block blok, který chceme uložit
     */
    public void convertBlock(AbstractBlock block){
        this.className = block.getClass().getSimpleName();
        this.type = "";
        this.value1 = block.getCoordinates()[0];
        this.value2 = block.getCoordinates()[1];
        this.id = block.getId();
    }

    /**
     * Přetransformuje port na uložitelné data a uloží je do této třídy
     * @param port port, který chceme uložit
     * @param connectedId pole id portů, ke kterým je připojený
     */
    public void convertPort(Port port,ArrayList<Integer> connectedId){
        this.connectedTo = new ArrayList<>();
        this.className = port.getName();
        this.type = port.getType().getName();
        switch (type){
            case "Human":
                this.value1 = port.getType().getValue("weight");
                this.value2 = port.getType().getValue("stamina");
                break;
            case "Time":
                this.value1 = port.getType().getValue("hours");
                this.value2 = port.getType().getValue("minutes");
                break;
            case "Food":
                this.value1 = port.getType().getValue("calories");
                this.value2 = 0;
                break;
        }
        this.id = port.getId();
        this.connectedTo.addAll(connectedId);
    }

}
