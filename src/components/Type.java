package components;

import java.util.ArrayList;

import static java.lang.Double.NaN;

public class Type {
    private ArrayList<String> type;
    private ArrayList<Double> value;

    public Type(){
        String [] types = new String[20];
        types[0] = "Natural";
        types[1] = "8bit";
        types[2] = "Real";
        for(String newType : types){
            type.add(newType);
            value.add(0.0);
        }
    }

    public double getTypeValue(String requestType){
        for(int i = 0; i < type.size();i++){
            if(type.get(i).equals(requestType)){
                return value.get(i);
            }
        }
        return NaN;
    }

    public boolean setTypeValue(String requestType, double requestValue){
        for(int i = 0; i < type.size();i++){
            if(type.get(i).equals(requestType)){
                value.set(i,requestValue);
                return true;
            }
        }
        return false;
    }
}
