package components;

import components.Type;

public class Port {
    private static int count = 0;
    private int id;
    private Type value;

    public Port(){
        this.id = (count++);
        this.value = new Type();
    }

    public int getId(){
        return this.id;
    }

    public double getValue(String type) {
        return this.value.getTypeValue(type);
    }

    public boolean setValue(double newValue){
        return this.value.setTypeValue(newValue);
    }
}
