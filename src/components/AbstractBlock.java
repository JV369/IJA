
package components;

import components.Port;
import interfaces.Block;

import java.util.ArrayList;

/**
 * Abstraktní třída pro jednotlivé bloky
 * @author Jan Vávra (xvavra20)
 */
public abstract class AbstractBlock implements Block{
    private static int countID = 0;
    protected ArrayList<Port> inPort;
    protected ArrayList<Port> outPort;
    private int id;
    private double x_coord;
    private double y_coord;

    public AbstractBlock(){
        this.id = countID++;

        this.x_coord = 0.0;
        this.y_coord = 0.0;
        this.inPort = new ArrayList<>();
        this.outPort = new ArrayList<>();
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

    public Port getInPort(int index){
        return this.inPort.get(index);
    }

    public Port getOutPort(int index){
        return this.outPort.get(index);
    }

    public void setCoordinates(double newX, double newY){
        this.x_coord = newX;
        this.y_coord = newY;
    }

}
