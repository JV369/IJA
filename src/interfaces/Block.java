package interfaces;

import components.Port;

public interface Block {
    boolean execute();
    int getId();
    double[] getCoordinates();
    Port getInPort(int index);
    Port getOutPort(int index);
    //maybe?
    void setCoordinates(double newX, double newY);
    //end maybe?

}
