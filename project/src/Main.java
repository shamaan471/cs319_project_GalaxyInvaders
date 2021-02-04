import Controller.*;
import Model.ModelManager;
import View.*;

public class Main {

    public static GameFrame gameFrame = new GameFrame();
    //public static ModelManager modelManager = new ModelManager();
    //ModelManager modelManager = ModelManager.getInstance();

    public static void main(String[] args){

        GameController gameController = new GameController(gameFrame);
        gameFrame.setVisible(true);
    }
}
