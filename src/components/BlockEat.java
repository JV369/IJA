package components;

public class BlockEat extends AbstractBlock{
    public BlockEat(){
        super();
        this.inPort.add(new Port("Human"));
        this.inPort.add(new Port("Food"));
        this.outPort.add(new Port("Human"));
    }

    public void execute(){
        Type in1 = this.inPort.get(0).getType();
        Type in2 = this.inPort.get(1).getType();
        double resultWeight = in2.getValue("calories")/9000 + in1.getValue("weight");
        double resultStamina = in2.getValue("calories")/72.36 + in1.getValue("stamina");
        if(resultStamina > 100.0){
            resultStamina = 100.0;
        }

        this.outPort.get(0).getType().update("stamina", resultStamina);
        this.outPort.get(0).getType().update("weight", resultWeight);
    }
}
