package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HowToPlayView extends JPanel{

    private JButton goToMenu_button;
    private Image goToMenu_img;
    private BufferedImage imageOfHowToPlay;  //background image


    public HowToPlayView()
    {
        setComponents();
        addComponent();
    }
    public void setComponents()
    {
        // go to menu button
        goToMenu_button = new JButton();
        goToMenu_button.setPreferredSize(new Dimension(199, 52));
        goToMenu_button.setName("Go To Menu");
        goToMenu_img = new ImageIcon(this.getClass().getResource("/goToMenu_button.png")).getImage();
        goToMenu_button.setIcon(new ImageIcon((goToMenu_img)));

        setLayout(null);
        goToMenu_button.setLayout(null);

        setImagesOfHowToPlay(imageOfHowToPlay, "/howToPlay.jpg");
    }

    public void addComponent()
    {
        add(goToMenu_button);
        goToMenu_button.setBounds(1050, 700, 199, 52);
    }

    public void setImagesOfHowToPlay(BufferedImage image, String path){
        try {
            //sets image to given picture path
            imageOfHowToPlay = ImageIO.read(getClass().getResourceAsStream("/howToPlay.jpg"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addActionListenerMainMenu(ActionListener listener){
        goToMenu_button.addActionListener(listener);
    }
    @Override public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(imageOfHowToPlay, getX(), getY(),
                getSize().width, getSize().height, null);

    }
}
