package components;

public class BlockEat extends AbstractBlock{
    public BlockEat(){
        super();
        this.inPort.add(new Port("Human"));
        this.inPort.add(new Port("Food"));
        this.outPort.add(new Port("Human"));
    }

    public void execute(){

    }
}
