package interfaces;

import components.Port;

/**
 * Rozhraní pro blok
 * @author Jan Vávra (xvavra20)
 * @author Aleš Postulka (xpostu03)
 */
public interface Block {
    /**
     * Provede akci nad blokem
     */
    void execute();

    /**
     * Vrátí id bloku
     * @return id
     */
    int getId();

    /**
     * Získá hodnoty x,y bloku ve schématu
     * @return 2prvkové pole [x,y]
     */
    double[] getCoordinates();

    /**
     * Vrátí port z pole vstupních portů na indexu "index"
     * @param index index portu v poli
     * @return vstupní port
     */
    Port getInPort(int index);
    /**
     * Vrátí port z pole výstupních portů na indexu "index"
     * @param index index portu v poli
     * @return výstupní port
     */
    Port getOutPort(int index);

    /**
     * Nastaví x,y souřadnici portu
     * Hodnoty se uchovávají pro případné uložení schématu
     * @param newX nová x souřadnice
     * @param newY nová y souřadnice
     */
    void setCoordinates(double newX, double newY);
}
