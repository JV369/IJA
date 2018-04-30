package components;

import java.util.ArrayList;

/**
 * Třída pro simulující činnost "Sleep" typu Human
 * @author Aleš Postulka (xpostu03)
 * @see AbstractBlock
 */
public class BlockSleep extends AbstractBlock {
    /**
     * Konstruktor třídy BlockSleep
     */
    public BlockSleep(){
        super();
        this.inPort.add(new Port("Human", "in"));
        this.inPort.add(new Port("Time", "in"));
        this.outPort.add(new Port("Human", "out"));
    }

    /**
     * Provede výpočet nad blokem
     * Vezme hodnotu energie typu Human na vstupním portu a na základě
     * uvedeného času v druhém portu jí zvětší a předá aktualizovaný
     * typ Human na výstupní port
     */
    public void execute(){
        Port in1 = this.inPort.get(0);
        Port in2 = this.inPort.get(1);
        Port out = this.outPort.get(0);

        ArrayList<Double> values = new ArrayList<>();

        double resultStamina = in1.getType().getValue("stamina") + (in2.getType().getValue("hours") + in2.getType().getValue("minutes")/60)*15;
        if(resultStamina > 100.0){
            resultStamina = 100.0;
        }
        values.add(in1.getType().getValue("weight"));
        values.add(resultStamina);
        out.update("Human", values);
    }
}
