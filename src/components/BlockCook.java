package components;

import java.util.ArrayList;

public class BlockCook extends AbstractBlock {
    public BlockCook(){
        super();
        this.inPort.add(new Port("Food", "in"));
        this.inPort.add(new Port("Food", "in"));
        this.outPort.add(new Port("Food", "out"));
    }

    public void execute(){
        Port in1 = this.inPort.get(0);
        Port in2 = this.inPort.get(1);
        Port out = this.outPort.get(0);

        ArrayList<Double> values = new ArrayList<>();

        values.add(in1.getType().getValue("calories") + in2.getType().getValue("calories"));

        out.update("Food", values);
    }
}
