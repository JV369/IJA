package components;

public class BlockSleep extends AbstractBlock{
    public BlockSleep(){
        super();
        this.inPort.add(new Port("Human"));
        this.inPort.add(new Port("Time"));
        this.outPort.add(new Port("Human"));
    }

    public void execute(){
        Type in1 = inPort.get(0).getType();
        Type in2 = inPort.get(1).getType();
        Type tmp = outPort.get(0).getType();
        double resultStamina = in1.getValue("stamina") + (in2.getValue("hours") + in2.getValue("minutes")/60)*15;
        if(resultStamina > 100.0){
            resultStamina = 100.0;
        }
        tmp.update("stamina", resultStamina);
    }
}
