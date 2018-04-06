package components;

import components.Port;

public class InPort extends Port {

    private int priority;
    public InPort(int prior){
        super();
        this.priority = prior;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int newPriority){
        this.priority = newPriority;
    }
}
