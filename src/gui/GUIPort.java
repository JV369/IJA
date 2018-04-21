package gui;

import components.Port;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class GUIPort extends ImageView{
    private Port port;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    public GUIPort(Port port, javafx.scene.input.MouseEvent event, Image image, double offset){
        super(image);
        this.port = port;

        double Xcoord, Ycoord;
        if((event.getX() - 125.0) <= 0){
            Xcoord = event.getX();
        }
        else {
            Xcoord = event.getX() - 125.0/2.0;
        }

        if((event.getY() - 93.75) <= 0){
            Ycoord = event.getY();
        }
        else {
            Ycoord = event.getY() - 93.75/2.0;
        }

        if(port.getName().equals("out")){
            this.setX(Xcoord+125-12.5);
            this.setY(Ycoord+93.75/2.0 - 12.5);
        }
        else if(port.getName().equals("in")){
            this.setX(Xcoord-12.5);
            this.setY(Ycoord+93.75/2.0 - 12.5);
        }
        this.setLayoutY(offset);
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
}
