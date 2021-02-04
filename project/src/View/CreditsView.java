package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CreditsView extends JPanel {

    private JButton goToMenu_button;
    private Image goToMenu_img;
    private BufferedImage imageOfCredits;  //background image

    public CreditsView()
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

        setImagesOfCredits(imageOfCredits, "/credits.jpg");
    }
    public void addComponent()
    {

        add(goToMenu_button);
        goToMenu_button.setBounds(1050, 700, 199, 52);
    }
    public void setImagesOfCredits(BufferedImage image, String path){
        try {
            //sets image to given picture path
            imageOfCredits = ImageIO.read(getClass().getResourceAsStream("/credits.jpg"));
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
        g.drawImage(imageOfCredits, getX(), getY(),
                getSize().width, getSize().height, null);

    }
}
