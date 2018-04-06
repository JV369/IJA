package components;

import java.util.ArrayList;

import static java.lang.Double.NaN;

public class Type {
    private ArrayList<String> type;
    private ArrayList<Double> value;

    public Type(){
        type = new ArrayList<String>();
        value = new ArrayList<Double>();
        String [] types = new String[20];
        types[0] = "Natural";
        types[1] = "8bit";
        types[2] = "Real";
        this.type = new ArrayList<>();
        this.value = new ArrayList<>();
        for(String newType : types){
            this.type.add(newType);
            this.value.add(0.0);
        }
    }

    public double getTypeValue(String requestType){
        for(int i = 0; i < this.type.size();i++){
            if(this.type.get(i).equals(requestType)){
                return this.value.get(i);
            }
        }
        return NaN;
    }

    public boolean setTypeValue(double requestValue){
        for(int i = 0; i < this.type.size();i++){
            switch (this.type.get(i)){
                case "Natural":
                    if(requestValue < 0){
                        this.value.set(i,NaN);
                        break;
                    }
                    this.value.set(i,Math.floor(requestValue));
                    break;
                case "8bit":
                    if(requestValue < 0){
                        this.value.set(i,NaN);
                        break;
                    }
                    this.value.set(i,Math.floor(requestValue)%256);
                    break;
                case "Real":
                    this.value.set(i,requestValue);
                    break;
            }


        }
        return false;
    }
}
