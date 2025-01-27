package components;

import java.util.ArrayList;

/**
 * Třída pro vytvoření bloku simulující činnost "Eat" typu Human
 * @author Aleš Postulka (xpostu03)
 * @see AbstractBlock
 */
public class BlockEat extends AbstractBlock{

    /**
     * Konstruktor třídy BlockEat
     */
    public BlockEat(){
        super();
        this.inPort.add(new Port("Human", "in"));
        this.inPort.add(new Port("Food", "in"));
        this.outPort.add(new Port("Human", "out"));
    }

    /**
     * Provede výpočet nad blokem
     * Vezme hodnoty weight a stamina typu Human na vstupu a na základě hodnoty calories
     * v typu Food na vstupním portu vypočítá nové hodnoty stamina a weight a aktualizuje
     * typu Human na výstupním portu
     */
    public void execute(){
        Port in1 = this.inPort.get(0);
        Port in2 = this.inPort.get(1);
        Port out = this.outPort.get(0);

        ArrayList<Double> values = new ArrayList<>();

        double resultWeight = in2.getType().getValue("calories")/3000 + in1.getType().getValue("weight");
        double resultStamina = in2.getType().getValue("calories")/72.36 + in1.getType().getValue("stamina");
        if(resultStamina > 100.0){
            resultStamina = 100.0;
        }

        values.add(resultWeight);
        values.add(resultStamina);

        out.update("Human", values);
    }
}
