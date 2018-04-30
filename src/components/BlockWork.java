package components;

import java.util.ArrayList;

/**
 * Třída pro vytvoření bloku simulující činnost "Work" typu Human
 * @author Aleš Postulka (xpostu03)
 * @see AbstractBlock
 */
public class BlockWork extends AbstractBlock {
    public BlockWork(){
        super();
        this.inPort.add(new Port("Human", "in"));
        this.inPort.add(new Port("Time", "in"));
        this.outPort.add(new Port("Human", "out"));
    }

    /**
     * Provede operaci nad blokem
     * Vezme hodnoty weight a stamina typu Human a na vstupu na základě času
     * vstupním portu zmenší hodnoty stamina a weight a aktualizuje
     * typ Human na výstupním portu
     * Tento blok má menší vliv na stamina a weight než BlockSport
     * @see BlockSport
     */
    public void execute(){
        Port in1 = this.inPort.get(0);
        Port in2 = this.inPort.get(1);
        Port out = this.outPort.get(0);

        ArrayList<Double> values = new ArrayList<>();

        double weight = in1.getType().getValue("weight") - ((in2.getType().getValue("hours") + (in2.getType().getValue("minutes")/60))*0.12);
        double stamina = in1.getType().getValue("stamina") - ((in2.getType().getValue("hours") + (in2.getType().getValue("minutes")/60))*7);

        weight = weight < 0? 0: weight;
        stamina = stamina < 0? 0: stamina;

        values.add(weight);
        values.add(stamina);

        out.update("Human", values);
    }
}
