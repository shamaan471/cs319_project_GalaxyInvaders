package Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        //gets name of clicked button
        String clickedButton = ((Component) e.getSource()).getName();

        //starts game after START GAME button clicked
        if (clickedButton == "Play")
        {
            GameController.getGameFrame().startGameSession();

            GameController.sessionListener.setTimer(true);

        }
        //exits after QUIT button clicked
        else if (clickedButton == "How To Play")
        {
            GameController.getGameFrame().howToPlaySession();
            GameController.sessionListener.setTimer(false);
        }
        //exits after QUIT button clicked
        else if (clickedButton == "Settings")
        {
            GameController.getGameFrame().settingsSession();
            GameController.sessionListener.setTimer(false);
        }
        //exits after QUIT button clicked
        else if (clickedButton == "Credits")
        {
            GameController.getGameFrame().creditsSession();
            GameController.sessionListener.setTimer(false);
        }
        else if (clickedButton == "Go To Menu")
        {
            GameController.getGameFrame().mainMenuSession();
            GameController.sessionListener.setTimer(false);
        }
        //exits after QUIT button clicked
        else if (clickedButton == "Quit")
        {
            System.exit(0);
        }

    }
}
