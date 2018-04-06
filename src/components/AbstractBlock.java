package components;

import interfaces.Block;

import java.util.ArrayList;

public abstract class AbstractBlock implements Block{
    private static int countID = 0;
    private int countPriority = 0;
    private ArrayList<InPort> inPort;
    private ArrayList<Port> outPort;
    private int id;
    private double x_coord;
    private double y_coord;

    public AbstractBlock(double x, double y){
        this.id = (countID++);
        this.x_coord = x;
        this.y_coord = y;
        this.inPort = new ArrayList<>();
        this.outPort = new ArrayList<>();
        InPort inPort1 = new InPort((++this.countPriority));
        InPort inPort2 = new InPort((++this.countPriority));
        Port outPort1 = new Port();
        this.inPort.add(inPort1);
        this.inPort.add(inPort2);
        this.outPort.add(outPort1);

    }

    public int getId() {
        return this.id;
    }

    public double[] getCoordinates(){
        double[] retVal = new double[2];
        retVal[0] = this.x_coord;
        retVal[1] = this.y_coord;
        return retVal;
    }

    public int getInPortID(int index){
        return this.inPort.get(index).getId();
    }

    public int getOutPortID(int index){
        return this.outPort.get(index).getId();
    }

    public void setCoordinates(double newX, double newY){
        this.x_coord = newX;
        this.y_coord = newY;
    }

    public void addInPort(){
        InPort newInPort = new InPort((++this.countPriority));
        this.inPort.add(newInPort);
    }

    public void addOutPort(){
        Port newOutPort = new Port();
        this.outPort.add(newOutPort);
    }

    public boolean deleteInPort(int index){
        if(inPort.size() < 3){
            return false;
        }
        this.inPort.remove(index);
        return true;
    }

    public boolean deleteOutPort(int index){
        if(inPort.size() < 2){
            return false;
        }
        this.outPort.remove(index);
        return true;
    }
}
