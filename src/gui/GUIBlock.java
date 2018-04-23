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

    public GUIBlock(String type, Image image){
        super(image);

        switch (type){
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
