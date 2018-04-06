package interfaces;

public interface Block {
    double calculate();
    double[] getCoordinates();
    int getInPortID(int index);
    int getOutPortID(int index);
    //maybe?
    void setCoordinates(double newX, double newY);
    //end maybe?

}
