package components;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Double.NaN;

/**
 * Třída pro vytvoření a správu typu uloženého v portu
 * @author Jan Vávra (xvavra20)
 * @author Aleš Postulka (xpostu03)
 */
public class Type {
    private String name;
    private Map<String,Double> values;

    /**
     * Konstruktor třídy Type
     * Na základě jména se vytvoří příslušný typ
     * Máme 3 typy:
     * Human - člověk, kde uchováváme váhu(weight) a energii(stamina)
     * Time - čas, kde uchováváme hodiny(hours) a minuty(minutes)
     * Food - jídlo, kde uchováváme kalorie(calories)
     * @param name název typu, který chceme vytvořit
     */
    public Type(String name){
        this.name = name;
        values = new HashMap<>();
        switch(name){
            case "Human":
                values.put("weight",80.0);
                values.put("stamina",100.0);
                break;
            case "Time":
                values.put("hours",0.0);
                values.put("minutes",30.0);
                break;
            case "Food":
                values.put("calories",1000.0);
                break;
            default:
                this.name = "Human";
                values.put("weight",80.0);
                values.put("stamina",100.0);
        } 
    }

    /**
     * Metoda pro zjištění jména typu třídy
     * @return název vytvořeného typu
     */
    public String getName() {
        return this.name;
    }

    /**
     * Vrátí hodnotu na základě názvu hodnoty
     * @param property jméno hodnoty namapované v values
     * @return hodnotu pokud jméno existuje, jinak NaN
     */
    public double getValue(String property){
        if(values.containsKey(property)){
            return values.get(property);
        }
        return NaN;
    }

    /**
     * Metoda pro přístup k mapě hodnot
     * @return Mapu hodnot
     * @see Map
     */
    public Map getAllValues(){
        return this.values;
    }

    /**
     * Nastaví property v values na value
     * @param property jméno hodnoty namapované v values
     * @param value hodnota, na kterou se má změnit
     * @return true pokud se hodnota aktualizovala, jinak false
     */
    public boolean update(String property, double value){
        if((this.name.equals("Human") || this.name.equals("Time") || this.name.equals("Food")) && this.values.containsKey(property)) {
            this.values.put(property, value);
            return true;
        }
        return false;
    }
}
