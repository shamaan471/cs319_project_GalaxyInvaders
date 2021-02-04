package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.border.EmptyBorder;

public class MainMenuView extends JPanel {


    private JButton startButton;
    private JButton howToPlayButton;
    private JButton quitButton;
    private JButton settingsButton;
    private JButton creditsButton;

    private Image startImg;
    private Image howToPlayImg;
    private Image settingsImg;
    private Image creditsImg;
    private Image quitImg;


    private GridBagConstraints gbc;  //used to bring the components to the middle of the screen.

    private BufferedImage imageOfMenu;  //background image


    public MainMenuView(){

        //setBorder(new EmptyBorder(50, 50, 50, 50));
        setLayout(new GridBagLayout());

        setComponents();
        addComponents();
        setImagesOfMenu(imageOfMenu, "/Menu.jpg");

    }

    public void setComponents(){
        gbc = new GridBagConstraints();

        startButton = new JButton();
        startButton.setPreferredSize(new Dimension(199, 52));
        startButton.setName("Play");
        startImg = new ImageIcon(this.getClass().getResource("/play_button.png")).getImage();
        startButton.setIcon(new ImageIcon((startImg)));


        howToPlayButton= new JButton();
        howToPlayButton.setName("How To Play");
        howToPlayImg = new ImageIcon(this.getClass().getResource("/howToPlay_button.png")).getImage();
        howToPlayButton.setIcon(new ImageIcon((howToPlayImg)));

        settingsButton = new JButton();
        settingsButton.setName("Settings");
        settingsImg = new ImageIcon(this.getClass().getResource("/settings_button.png")).getImage();
        settingsButton.setIcon(new ImageIcon((settingsImg)));

        creditsButton = new JButton();
        creditsButton.setName("Credits");
        creditsImg = new ImageIcon(this.getClass().getResource("/credits_button.png")).getImage();
        creditsButton.setIcon(new ImageIcon((creditsImg)));

        quitButton = new JButton();
        quitButton.setName("Quit");
        quitImg = new ImageIcon(this.getClass().getResource("/quit_button.png")).getImage();
        quitButton.setIcon(new ImageIcon((quitImg)));


    }

    public void addComponents(){

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        GridLayout gridLayout = new GridLayout(5, 1, 0, 35);

        JPanel buttons = new JPanel();
        buttons.setLayout(gridLayout);
        buttons.setOpaque(false);

        buttons.add(startButton, gbc);
        buttons.add(howToPlayButton);
        buttons.add(settingsButton, gbc);
        buttons.add(creditsButton, gbc);
        buttons.add(quitButton, gbc);

        gbc.weighty = 3;
        add(buttons, gbc);
    }





    public void addActionListenerMainMenu(ActionListener listener){
        startButton.addActionListener(listener);
        howToPlayButton.addActionListener(listener);
        settingsButton.addActionListener(listener);
        creditsButton.addActionListener(listener);
        quitButton.addActionListener(listener);
    }


    public void setImagesOfMenu(BufferedImage image, String path){
        try {
            //sets image to given picture path
            imageOfMenu = ImageIO.read(getClass().getResourceAsStream("/Menu.jpg"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(imageOfMenu, getX(), getY(),
                getSize().width, getSize().height, null);

    }


}
