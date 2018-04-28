package components;

import java.util.Observable;
import java.util.Observer;

public class Connection implements Observer{
    private Port out;
    private Port in;

    public Connection(Port out, Port in){
        this.out = out;
        this.out.addObserver(this);
        this.in = in;
    }

    public Type getState(){
        return this.out.getType();
    }

    public void update(Observable obs, Object obj)
    {
        this.in.copyValues(this.out);
        /*if(this.in.getType().getName().equals("Human")) {
            System.out.println("new weight: " + this.in.getType().getValue("weight"));
            System.out.println("new stamina: " + this.in.getType().getValue("stamina"));
        }else if(this.in.getType().getName().equals("Food")){
            System.out.println("new calories: " + this.in.getType().getValue("calories"));
        }else if(this.in.getType().getName().equals("Time")) {
            System.out.println("new hours: " + this.in.getType().getValue("hours"));
            System.out.println("new minutes: " + this.in.getType().getValue("minutes"));
        }*/
    }

    public Port getOut() {
        return out;
    }

    public Port getIn() {
        return in;
    }
}
