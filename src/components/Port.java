package components;

import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;

/**
 * Třída pro vytvoření portu pro block
 * @author Jan Vávra (xvavra20)
 * @author Aleš Postulka (xpostu03)
 */
public class Port extends Observable{
    private static int count = 0;
    private int id;
    private String name;
    private Type type;

    /**
     * Konstruktor třídy Port
     * @param type typ, který bude v Portu uložen
     * @param name specifikace jestli je vstupní nebo výstupní
     */
    public Port(String type, String name){
        this.id = (count++);
        this.type = new Type(type);
        this.name = name;
    }

    /**
     * Vrátí id portu
     * @return id portu
     */
    public int getId(){
        return this.id;
    }

    /**
     * Metoda pro přístup k typu portu
     * @return typ portu
     * @see Type
     */
    public Type getType(){
        return this.type;
    }

    /**
     * Vrátí specifikaci portu
     * @return in pokud je vstupní, out pokud je výstupní
     */
    public String getName(){
        return this.name;
    }

    /**
     * Nastaví id portu
     * @param id nové id, na které se má nastavit
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Metoda aktualizuje hodnotu typu a notifikuje observery, že se port změnil
     * @param typeName typ, podle kterého se nastaví hodnoty
     * @param values pole hodnot
     * @return true pokud se hodnoty aktualizovali úspěšně, jinak false
     */
    public boolean update(String typeName, ArrayList<Double> values){
        if(typeName.equals("Human") && this.type.getName().equals(typeName) && values.size() == 2){
            this.type.update("weight", values.get(0));
            this.type.update("stamina", values.get(1));
        }else if(typeName.equals("Time") && this.type.getName().equals(typeName) && values.size() == 2) {
            this.type.update("hours", values.get(0));
            this.type.update("minutes", values.get(1));
        }else if(typeName.equals("Food") && this.type.getName().equals(typeName) && values.size() == 1) {
            this.type.update("calories", values.get(0));
        }else{
            return false;
        }
        if(this.name.equals("out")){
            setChanged();
            notifyObservers();
        }
        return true;
    }

    /**
     * Zkopíruje hodnoty z portu na tento port
     * @param src zdrojový port
     */
    public void copyValues(Port src){
        Map inMap = this.getType().getAllValues();
        inMap.clear();
        inMap.putAll(src.getType().getAllValues());
    }

}
