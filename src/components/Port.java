package components;

import components.Type;

public class Port {
    private static int count = 0;
    private int id;
    private Type value;

    public Port(){
        this.id = (count++);
        this.value = new Type("Real");
    }

    public int getId(){
        return this.id;
    }

    public Type getType(){
        return this.value;
    }

    public void setType(Type type){
        String actType = type.getActiveType();
        this.value.setActiveType(actType);
        this.value.setTypeValue(type.getTypeValue(actType));
    }
}
