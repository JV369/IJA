package interfaces;

public interface Block {
    double calculate();
    int getId();
    double[] getCoordinates();
    int getInPortID(int index);
    int getOutPortID(int index);
    void addInPort();
    void addOutPort();
    boolean deleteInPort(int index);
    boolean deleteOutPort(int index);
    //maybe?
    void setCoordinates(double newX, double newY);
    //end maybe?

}
