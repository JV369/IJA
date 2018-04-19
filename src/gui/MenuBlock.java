package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuBlock extends ImageView{
    private Class AbstractBlock;
    public MenuBlock(Class AbstractBlock, Image image){
        super(image);
        this.AbstractBlock = AbstractBlock;
    }

    public Class getAbstractBlockClass(){
        return this.AbstractBlock;
    }
}
