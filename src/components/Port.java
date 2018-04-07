package components;

public class Port {
    private static int count = 0;
    private int id;
    private Type type;

    public Port(String type){
        this.id = (count++);
        this.type = new Type(type);
    }

    public int getId(){
        return this.id;
    }

    public Type getType(){
        return this.type;
    }

}
