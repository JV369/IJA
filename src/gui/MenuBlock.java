package gui;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuBlock extends ImageView{
    private Class AbstractBlock;
    public MenuBlock(Class AbstractBlock, Image image){
        super(image);
        this.AbstractBlock = AbstractBlock;

        Tooltip tooltip = new Tooltip();

        tooltip.setText("Some shit");
        Tooltip.install(this, tooltip);
    }

    public Class getAbstractBlockClass(){
        return this.AbstractBlock;
    }
}
