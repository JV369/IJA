package components;

import components.Port;

public class InPort extends Port {

    private int priority;
    public InPort(int id,int prior){
        super(id);
        priority = prior;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int newPriority){
        priority = newPriority;
    }
}
