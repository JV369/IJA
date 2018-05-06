
package components;

import interfaces.Block;

import java.util.ArrayList;

/**
 * Abstraktní třída pro jednotlivé bloky
 * @author Jan Vávra (xvavra20)
 * @see Block
 */
public abstract class AbstractBlock implements Block{
    private static int countID = 0;
    protected ArrayList<Port> inPort;
    protected ArrayList<Port> outPort;
    private int id;
    private double x_coord;
    private double y_coord;

    /**
     * Konstruktor třídy AbstractBlock
     */
    public AbstractBlock(){
        this.id = countID++;

        this.x_coord = 0.0;
        this.y_coord = 0.0;
        this.inPort = new ArrayList<>();
        this.outPort = new ArrayList<>();
    }

    /**
     * Metoda pro získání id bloku
     * @return id bloku
     */
    public int getId() {
        return this.id;
    }

    /**
     * Získá polohové hodnoty x,y
     * @return pole 2 hodnot: x,y
     */
    public double[] getCoordinates(){
        double[] retVal = new double[2];
        retVal[0] = this.x_coord;
        retVal[1] = this.y_coord;
        return retVal;
    }

    /**
     * Metoda pro přístup ke vstupnímu portu na určitém indexu
     * @param index pozice portu v poli vstupních portů
     * @return vstupní port
     * @see Port
     */
    public Port getInPort(int index){
        return this.inPort.get(index);
    }

    /**
     * Metoda pro přístup ke výstupnímu portu na určitém indexu
     * @param index pozice portu v poli výstupních portů
     * @return výstupní port
     * @see Port
     */
    public Port getOutPort(int index){
        return this.outPort.get(index);
    }

    /**
     * Vrátí celé pole vstupních portů
     * @return pole vstupních portů
     * @see ArrayList
     * @see Port
     */
    public ArrayList<Port> getAllInPorts() {
        return inPort;
    }

    /**
     * Vrátí celé pole výstupních portů
     * @return pole výstupních portů
     * @see ArrayList
     * @see Port
     */
    public ArrayList<Port> getAllOutPorts() {
        return outPort;
    }

    /**
     * uloží si souřadnice x,y výskytu na scéně
     * @param newX nová souřadnice x
     * @param newY nová souřadnice y
     */
    public void setCoordinates(double newX, double newY){
        this.x_coord = newX;
        this.y_coord = newY;
    }

}
