package components;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Double.NaN;

public class Type {
    private String name;
    private Map<String,Double> values;

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
        }
    }

    public String getName() {
        return name;
    }

    public boolean updateHuman(double weight, double stamina){
        if(this.name.equals("Human")){
            this.values.put("weight",weight);
            this.values.put("stamina",stamina);
            return true;
        }
        return false;
    }

    public boolean updateTime(double hours, double minutes){
        if(this.name.equals("Time")){
            this.values.put("hours",hours);
            this.values.put("minutes",minutes);
            return true;
        }
        return false;
    }

    public boolean updateFood(double calories){
        if(this.name.equals("Human")){
            this.values.put("calories",calories);
            return true;
        }
        return false;
    }
}
