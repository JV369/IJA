package components;

import interfaces.Block;

import java.util.ArrayList;

public abstract class AbstractBlock implements Block{
    private static int countID = 0;
    private ArrayList<Port> inPort;
    private ArrayList<Port> outPort;
    private int id;
    private double x_coord;
    private double y_coord;

    public AbstractBlock(){
        this.id = (countID++);
        this.x_coord = 0.0;
        this.y_coord = 0.0;
        inPort = new ArrayList<>();
        outPort = new ArrayList<>();
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

}
