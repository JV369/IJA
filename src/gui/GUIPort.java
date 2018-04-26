package gui;

import components.Port;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.*;


public class GUIPort extends Rectangle{
    private Port port;
    private boolean changed;

    public GUIPort(Port port, double offset){
        super();
        this.port = port;
        this.setWidth(25);
        this.setHeight(25);
        this.setFill(Color.WHITE);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(2.5);

        if(port.getName().equals("out")){
            this.setLayoutX(125-12.5);
            this.setLayoutY(93.75/2.0 - 12.5 + offset);
        }
        else if(port.getName().equals("in")){
            this.setLayoutX(-12.5);
            this.setLayoutY(93.75/2.0 - 12.5 + offset);
            this.changed = false;
            this.setFill(Color.RED);
        }
        //this.setLayoutY(offset);
        GUIPort port1 = this;

        port1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tooltip tooltip = new Tooltip();
                String valueStr = "";
                switch (port.getType().getName()){
                    case "Human":
                        valueStr = "weight->" + Double.toString(port.getType().getValue("weight")) +
                                "\nstamina->" + Double.toString(port.getType().getValue("stamina"));
                        break;
                    case "Time":
                        valueStr = "hours->" + Double.toString(port.getType().getValue("hours")) +
                                "\nminutes->" + Double.toString(port.getType().getValue("minutes"));
                        break;
                    case "Food":
                        valueStr = "calories->" + Double.toString(port.getType().getValue("calories"));
                        break;
                }
                tooltip.setText(port.getType().getName()+"\n"+valueStr);
                Tooltip.install(port1, tooltip);
            }
        });

    }

    public Port getPort() {
        return port;
    }

    public void setChanged(){
        changed = !changed;
        if(this.changed)
            this.setFill(Color.GREEN);
        else
            this.setFill(Color.RED);
    }

    public boolean getChanged(){
        return this.changed;
    }
}
