package gui;

import components.Port;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Třída pro vytvoření poheldu na port
 * @author Jan Vávra
 */
public class GUIPort extends Rectangle{
    private Port port;
    private boolean changed;

    /**
     * Konstruktor třídy GUIPort
     * @param port port, pro který vytváříme pohled
     * @param offset posun po y ose vzhledem k bloku
     */
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

    /**
     * Metoda pro získání dat portu
     * @return this.port
     */
    public Port getPort() {
        return this.port;
    }

    /**
     * Nastaví příznak changed, což značí jestli byly hodnoty typu uvnitř portu modifikovány
     */
    public void setChanged(){
        changed = !changed;
        if(this.changed)
            this.setFill(Color.GREEN);
        else
            this.setFill(Color.RED);
    }

    /**
     * Vrátí příznak changed
     * @return this.changed
     */
    public boolean getChanged(){
        return this.changed;
    }

    /**
     * Vytvoří dialogové okno pro zněnění hodnot na portu
     */
    public void createDialog(){
        Dialog dialog = new Dialog();
        dialog.setTitle("Change value: "+ this.port.getType().getName());
        dialog.setResizable(true);
        GridPane grid = new GridPane();
        TextField text1 = new TextField();
        TextField text2 = new TextField();
        Label label1;
        Label label2;

        switch (this.port.getType().getName()){
            case "Human":
                label1 = new Label("Weight: ");
                label2 = new Label("Stamina: ");
                text1.setText(Double.toString(this.port.getType().getValue("weight")));
                text2.setText(Double.toString(this.port.getType().getValue("stamina")));

                grid.add(label1, 1, 1);
                grid.add(text1, 2, 1);
                grid.add(label2, 1, 2);
                grid.add(text2, 2, 2);
                break;
            case "Time":
                label1 = new Label("Hours: ");
                label2 = new Label("Minutes: ");
                text1.setText(Double.toString(this.port.getType().getValue("hours")));
                text2.setText(Double.toString(this.port.getType().getValue("minutes")));

                grid.add(label1, 1, 1);
                grid.add(text1, 2, 1);
                grid.add(label2, 1, 2);
                grid.add(text2, 2, 2);
                break;
            case "Food":
                label1 = new Label("Calories: ");
                text1.setText(Double.toString(this.port.getType().getValue("calories")));

                grid.add(label1, 1, 1);
                grid.add(text1, 2, 1);
                text2.setDisable(true);
                break;

        }

        dialog.getDialogPane().setContent(grid);
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, ArrayList<Double>>() {
            @Override
            public ArrayList<Double> call(ButtonType b) {

                if (b == buttonTypeOk) {
                    ArrayList<Double> arr = new ArrayList<>();
                    arr.add(Double.parseDouble(text1.getText()));
                    if(!text2.isDisabled())
                        arr.add(Double.parseDouble(text2.getText()));
                    return arr;
                }

                return null;
            }
        });

        Optional<ArrayList<Double>> result = dialog.showAndWait();
        if (result.isPresent()){
            this.port.update(this.port.getType().getName(),result.get());
            if(this.port.getName().equals("in") && !this.getChanged()){
                this.setChanged();
            }
        }
    }
}
