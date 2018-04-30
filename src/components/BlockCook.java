package components;

import java.util.ArrayList;

/**
 * Třída pro simulující činnost "Cook" typu Human
 * @author Aleš Postulka (xpostu03)
 * @see AbstractBlock
 */
public class BlockCook extends AbstractBlock {
    /**
     * Konstruktor pro třídu BlockCook
     */
    public BlockCook(){
        super();
        this.inPort.add(new Port("Food", "in"));
        this.inPort.add(new Port("Food", "in"));
        this.outPort.add(new Port("Food", "out"));
    }

    /**
     * Provede operaci nad blokem
     * Sečte kalorie uložené ve vstupních portech a nastaví jejich součet va výstup
     */
    public void execute(){
        Port in1 = this.inPort.get(0);
        Port in2 = this.inPort.get(1);
        Port out = this.outPort.get(0);

        ArrayList<Double> values = new ArrayList<>();

        values.add(in1.getType().getValue("calories") + in2.getType().getValue("calories"));

        out.update("Food", values);
    }
}
