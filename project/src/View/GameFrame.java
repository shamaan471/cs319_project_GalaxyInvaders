package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

public class GameFrame extends JFrame {

    private JPanel cardPanel;  //to hold all the sub panels
    private CardLayout cardLayout = new CardLayout();

    private static Dimension dimension;  //for the size of the main frame


    private MainMenuView mainMenuView;
    private GameSceneView gameSceneView;  //panel on which the gameplay will tak place
    private CreditsView creditsView;
    private SettingsView settingsView;
    private HowToPlayView howToPlayView;

    BufferedImage backgroundImage;

    public GameFrame(){

        this.setFocusable(true);

        //to define basic information about starting position of window on screen
        dimension = Toolkit.getDefaultToolkit().getScreenSize();

        this.setName("Galaxy Invader");
        this.setSize(1300,813);
        this.setResizable(false);

        //setting starting location of window on the screen
        this.setLocation(dimension.width/2 - this.getWidth()/2, dimension.height/2 - this.getHeight()/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);



        //initializing scene view panels

        mainMenuView = new MainMenuView();
        cardPanel.add(mainMenuView);

        gameSceneView = new GameSceneView();
        settingsView = new SettingsView();
        creditsView = new CreditsView();
        howToPlayView = new HowToPlayView();




        this.add(cardPanel);
        cardPanel.setVisible(true);


    }


    public void addActionListenerMainMenu(ActionListener actionListenerMainMenu){
        mainMenuView.addActionListenerMainMenu(actionListenerMainMenu);
        settingsView.addActionListenerMainMenu(actionListenerMainMenu);
        creditsView.addActionListenerMainMenu(actionListenerMainMenu);
        howToPlayView.addActionListenerMainMenu(actionListenerMainMenu);
    }


    //sets key listener to get key events from keyboard
    public void addKeyListenerGame (KeyListener keyListenerGame)
    {
        this.addKeyListener(keyListenerGame);
    }



    //setting what clicking on the buttons does

    public void startGameSession(){
        cardPanel.removeAll();
        cardPanel.add(gameSceneView);
        cardPanel.repaint();
        cardPanel.revalidate();
    }

    public void pauseGameSession(){
        cardPanel.removeAll();
        cardPanel.add(mainMenuView);
        cardPanel.repaint();
        cardPanel.revalidate();
    }

    public void endGameSession(){

    }

    public void mainMenuSession(){
        cardPanel.removeAll();
        cardPanel.add(mainMenuView);
        cardPanel.repaint();
        cardPanel.revalidate();
    }


    public void settingsSession(){
        cardPanel.removeAll();
        cardPanel.add(settingsView);
        cardPanel.repaint();
        cardPanel.revalidate();
    }
    public void creditsSession(){
        cardPanel.removeAll();
        cardPanel.add(creditsView);
        cardPanel.repaint();
        cardPanel.revalidate();
    }
    public void howToPlaySession(){
        cardPanel.removeAll();
        cardPanel.add(howToPlayView);
        cardPanel.repaint();
        cardPanel.revalidate();
    }







    //getters
    public MainMenuView getMainMenuView(){return mainMenuView;}
    public GameSceneView getGameSceneView(){return gameSceneView;}
    public SettingsView getSettingsView(){return settingsView;}
    public CreditsView getCreditsView(){return creditsView;}
    public HowToPlayView getHowToPlayView(){return howToPlayView;}

}
