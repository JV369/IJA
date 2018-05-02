package components;

import java.util.Observable;
import java.util.Observer;

/**
 * Třída pro vytvoření spojení mezi bloky
 * @author Aleš Postulka (xpostu03)
 */
public class Connection implements Observer{
    private Port out;
    private Port in;

    /**
     * Konstruktor třídy Connection
     * @param out výstupní port spojení
     * @param in vstupní port spojení
     */
    public Connection(Port out, Port in){
        this.out = out;
        this.out.addObserver(this);
        this.in = in;
    }

    /**
     * Při změně hodnot na výstupním portu spojení aktualizuje hodnoty na vstupním portu spojení
     * @param obs observable objekt
     * @param obj objekt
     */
    public void update(Observable obs, Object obj)
    {
        this.in.copyValues(this.out);
    }

    /**
     * Získání výstupního portu spojení
     * @return výstupní port spojení
     */
    public Port getOut() {
        return out;
    }

    /**
     * Získání vstupního portu spojení
     * @return vstupní port spojení
     */
    public Port getIn() {
        return in;
    }
}
