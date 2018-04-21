package gui;

import components.*;

import interfaces.Block;
import javafx.event.Event;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class GUIBlock extends ImageView{

    private AbstractBlock block;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    public GUIBlock(Class type, MouseEvent event, Image image){
        super(image);
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

        this.setX(Xcoord);
        this.setY(Ycoord);

        switch (type.getSimpleName()){
            case "BlockCook":
                block = new BlockCook();
                break;
            case "BlockSleep":
                block = new BlockSleep();
                break;
            case "BlockEat":
                block = new BlockEat();
                break;
            case "BlockWork":
               block = new BlockWork();
                break;
            case "BlockSport":
                block = new BlockSport();
                break;
            default:
                break;
        }


    }

    public AbstractBlock getBlock() {
        return block;
    }

}
