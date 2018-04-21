package gui;

import components.Connection;
import javafx.scene.Group;
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
    }

    public Connection getConnect() {
        return connect;
    }
}
