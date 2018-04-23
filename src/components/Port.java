package components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * Třída pro vytvoření portu pro block
 * @author Jan Vávra (xvavra20)
 * @author Aleš Postulka (xpostu03)
 */
public class Port extends Observable{
    private static int count = 0;
    private int id;
    private String name;
    private Type type;

    public Port(String type, String name){
        this.id = (count++);
        this.type = new Type(type);
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public Type getType(){
        return this.type;
    }

    public String getName(){
        return this.name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean update(String typeName, ArrayList<Double> values){
        if(typeName.equals("Human") && this.type.getName().equals(typeName) && values.size() == 2){
            this.type.update("weight", values.get(0));
            this.type.update("stamina", values.get(1));
        }else if(typeName.equals("Time") && this.type.getName().equals(typeName) && values.size() == 2) {
            this.type.update("hours", values.get(0));
            this.type.update("minutes", values.get(1));
        }else if(typeName.equals("Food") && this.type.getName().equals(typeName) && values.size() == 1) {
            this.type.update("calories", values.get(0));
        }else{
            return false;
        }
        if(this.name.equals("out")){
            setChanged();
            notifyObservers();
        }
        return true;
    }

    public void copyValues(Port src){
        Map inMap = this.getType().getAllValues();
        inMap.clear();
        inMap.putAll(src.getType().getAllValues());
    }

}
