package components;

import java.util.ArrayList;

/**
 * Třída pro simulující činnost "Sport" typu Human
 * @author Aleš Postulka (xpostu03)
 * @see AbstractBlock
 */
public class BlockSport extends AbstractBlock {
    /**
     * Konstuktor třídy BlockSport
     */
    public BlockSport(){
        super();
        this.inPort.add(new Port("Human", "in"));
        this.inPort.add(new Port("Time", "in"));
        this.outPort.add(new Port("Human", "out"));
    }

    /**
     * Provede operaci nad blokem
     * Vezme hodnoty weight a stamina typu Human na vstupu na základě času
     * vstupním portu zmenší hodnoty stamina a weight a předá aktualizovaný
     * typ Human na výstupní port<p>
     * Tento blok zmenšuje rychleji hodnotu weight než BlockWork
     * @see BlockWork
     */
    public void execute(){
        Port in1 = this.inPort.get(0);
        Port in2 = this.inPort.get(1);
        Port out = this.outPort.get(0);

        ArrayList<Double> values = new ArrayList<>();

        double weight = in1.getType().getValue("weight") - ((in2.getType().getValue("hours") + (in2.getType().getValue("minutes")/60))*0.625);
        double stamina = in1.getType().getValue("stamina") - ((in2.getType().getValue("hours") + (in2.getType().getValue("minutes")/60))*20);

        values.add(weight);
        values.add(stamina);
        out.update("Human", values);
    }
}
