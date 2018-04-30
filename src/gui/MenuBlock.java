package gui;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Třída pro vytvoření položek, pro výběr bloku
 * @author Jan Vávra (xvavra20)
 * @author Aleš Postulka (xpostu03)
 */
public class MenuBlock extends ImageView{
    private Class abstractBlock;

    /**
     * Vytvoří blok podle jména třídy
     * @param abstractBlock název třídy, kterou bude blok reprezentovat
     * @param image obrázek pro reprezentaci třídy
     */
    public MenuBlock(Class abstractBlock, Image image){
        super(image);
        this.abstractBlock = abstractBlock;
        Tooltip tooltip = new Tooltip();
        switch (this.abstractBlock.getSimpleName()){
            case "BlockCook":
                tooltip.setText("Block Cook\n" +
                        "Adds 2 calories of 2 foods and returns it to the Outport\n" +
                        "Returns new food");
                break;
            case "BlockSleep":
                tooltip.setText("Block Sleep\n" +
                        "Recovers energy of Human based on Time\n" +
                        "Returns rested Human");
                break;
            case "BlockEat":
                tooltip.setText("Block Eat\n" +
                        "Adds weight and energy to human\n" +
                        "Returns fed Human");
                break;
            case "BlockWork":
                tooltip.setText("Block Work\n" +
                        "Removes some energy from Human based on Time\n" +
                        "Returns tired Human");
                break;
            case "BlockSport":
                tooltip.setText("Block Sport\n" +
                        "Human looses some weight at expence of energy based on Time\n" +
                        "Returns fit, but tired Human");
                break;
            default:
                tooltip.setText("Undefined block");
                break;
        }
        Tooltip.install(this, tooltip);
    }

    /**
     * Metoda pro přístup ke třídě kterou blok reprezentuje
     * @return třídu reprezentovanou tímto blokem
     */
    public Class getAbstractBlockClass(){
        return this.abstractBlock;
    }
}
