package Controller;
import Model.ModelManager;
import View.*;

public class GameController {
    static private GameFrame gameFrame;
    ModelManager gameModel = ModelManager.getInstance();

    static public MainMenuListener mainMenuListener;
    static public SessionListener sessionListener;

    public GameController(GameFrame gameFrame){
        setViewAndModel(gameFrame);
        setListeners();
    }


    public void setViewAndModel(GameFrame gameFrame)
    {
        this.gameFrame = gameFrame;

    }

    public void setListeners() {
        mainMenuListener = new MainMenuListener();
        GameController.gameFrame.addActionListenerMainMenu(mainMenuListener);

        sessionListener = new SessionListener();
        GameController.gameFrame.addKeyListenerGame(sessionListener);

    }

    static public GameFrame getGameFrame(){
        return gameFrame;
    }
    ModelManager getGameModel(){return gameModel;}
}
