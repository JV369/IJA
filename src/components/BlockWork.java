package components;

import java.util.ArrayList;

public class BlockWork extends AbstractBlock {
    public BlockWork(){
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


        double weight = in1.getType().getValue("weight") - ((in2.getType().getValue("hours") + (in2.getType().getValue("minutes")/60))*0.12);
        double stamina = in1.getType().getValue("stamina") - ((in2.getType().getValue("hours") + (in2.getType().getValue("minutes")/60))*7);

        values.add(weight);
        values.add(stamina);

        out.update("Human", values);
    }
}
