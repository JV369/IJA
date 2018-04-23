package components;

import java.io.Serializable;
import java.util.ArrayList;

public class SerializableData implements Serializable{
    public String className;
    public String type;
    public double value1;
    public double value2;
    public int id;
    public ArrayList<Integer> connectedTo;

    public void convertBlock(AbstractBlock block){
        this.className = block.getClass().getSimpleName();
        this.type = "";
        this.value1 = block.getCoordinates()[0];
        this.value2 = block.getCoordinates()[1];
        this.id = block.getId();
    }

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
