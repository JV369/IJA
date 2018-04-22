package gui;

import components.Connection;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class GUIConnection extends Line{
    private Connection connect;
    public GUIConnection(Group block1, Group block2, GUIPort port1, GUIPort port2){
        super();
        this.setStrokeWidth(5);
        this.startXProperty().bind(block1.translateXProperty().add(port1.getLayoutX()+12.5));
        this.startYProperty().bind(block1.translateYProperty().add(port1.getLayoutY()+12.5));
        this.endXProperty().bind(block2.translateXProperty().add(port2.getLayoutX()+12.5));
        this.endYProperty().bind(block2.translateYProperty().add(port2.getLayoutY()+12.5));

        connect = new Connection(port1.getPort(),port2.getPort());

        GUIConnection connect = this;

        connect.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tooltip tooltip = new Tooltip();
                String valueStr = "";
                switch (connect.getConnect().getOut().getType().getName()){
                    case "Human":
                        valueStr = "weight->" + Double.toString(connect.getConnect().getOut().getType().getValue("weight")) +
                                "\nstamina->" + Double.toString(connect.getConnect().getOut().getType().getValue("stamina"));
                        break;
                    case "Time":
                        valueStr = "hours->" + Double.toString(connect.getConnect().getOut().getType().getValue("hours")) +
                                "\nminutes->" + Double.toString(connect.getConnect().getOut().getType().getValue("minutes"));
                        break;
                    case "Food":
                        valueStr = "calories->" + Double.toString(connect.getConnect().getOut().getType().getValue("calories"));
                        break;
                }
                tooltip.setText(connect.getConnect().getOut().getType().getName()+"\n"+valueStr);
                Tooltip.install(connect, tooltip);
            }
        });
    }

    public Connection getConnect() {
        return connect;
    }
}
