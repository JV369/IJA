package interfaces;

public interface Block {
    double calculate();
    double[] getCoordinates();
    int getInPortID();
    int getOutPortID();
    //maybe?
    void setInPortID();
    void setOutPortID();
    void setCoordinates(double newX, double newY);
    //end maybe?

}
