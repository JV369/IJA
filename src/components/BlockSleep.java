package components;

import java.util.ArrayList;

/**
 * Třída pro simulující činnost "Sleep" typu Human
 * @author Aleš Postulka (xpostu03)
 */
public class BlockSleep extends AbstractBlock {
    public BlockSleep(){
        super();
        this.inPort.add(new Port("Human", "in"));
        this.inPort.add(new Port("Time", "in"));
        this.outPort.add(new Port("Human", "out"));
    }

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
