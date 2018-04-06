package components;

import components.Type;

public class Port {
    private int id;
    private Type value;

    public Port(int newId){
        id = newId;
        value = new Type();
    }

    public int getId(){
        return id;
    }

    public double getValue(String type) {
        return value.getTypeValue(type);
    }

    public boolean setValue(String type, double newValue){
        return value.setTypeValue(type,newValue);
    }
}
