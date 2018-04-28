package gui;

import components.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Třída pro vytvoření grafickou reprezentaci bloku ve schématu
 * @author Jan Vávra (xvavra20)
 */
public class GUIBlock extends ImageView{

    private AbstractBlock block;

    /**
     * Konstuktor třídy GUIBlock
     * @param type jméno třídy, kterou bude reprezentovat
     * @param image obrázek reprezencující třídy
     */
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

    /**
     * Metoda pro přístup na data bloku
     * @return blok
     * @see AbstractBlock
     */
    public AbstractBlock getBlock() {
        return block;
    }

}
