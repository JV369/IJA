package components;

import components.Type;

public class Port {
    private int id;
    private Type value;

    public Port(int newId){
        this.id = newId;
        this.value = new Type();
    }

    public int getId(){
        return this.id;
    }

    public double getValue(String type) {
        return this.value.getTypeValue(type);
    }

    public boolean setValue(String type, double newValue){
        return this.value.setTypeValue(type,newValue);
    }
}
