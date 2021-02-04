package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SettingsView extends JPanel{

    private JButton goToMenu_button;
    private JToggleButton music_button;
    private JToggleButton fireSound_button;
    private Image goToMenu_img;
    private Image on_button_img;
    private Image off_button_img;
    private BufferedImage imageOfSettings;  //background image

    public SettingsView()
    {

        setComponents();
        addComponent();
    }


    public void setComponents()
    {
        on_button_img = new ImageIcon(this.getClass().getResource("/on_button.png")).getImage();
        off_button_img = new ImageIcon(this.getClass().getResource("/off_button.png")).getImage();

        goToMenu_button = new JButton();
        goToMenu_button.setPreferredSize(new Dimension(199, 52));
        goToMenu_button.setName("Go To Menu");
        goToMenu_img = new ImageIcon(this.getClass().getResource("/goToMenu_button.png")).getImage();
        goToMenu_button.setIcon(new ImageIcon((goToMenu_img)));

        setLayout(null);
        goToMenu_button.setLayout(null);

        music_button = new JToggleButton();
        music_button.setPreferredSize(new Dimension(185, 60));
        music_button.setName("Music Button");
        music_button.setIcon(new ImageIcon((on_button_img)));


        fireSound_button = new JToggleButton();
        fireSound_button.setPreferredSize(new Dimension(185, 60));
        fireSound_button.setName("Fire Sound Button");
        fireSound_button.setIcon(new ImageIcon((off_button_img)));

        setImagesOfSettings(imageOfSettings, "/settings.jpg");
    }
    public void addComponent()
    {

        goToMenu_button.setBounds(1050, 700, 199, 52);
        music_button.setBounds(600, 400, 80, 80);
        fireSound_button.setBounds(600, 500, 80, 80);
        add(music_button);
        add(fireSound_button);
        add(goToMenu_button);

    }
    public void setImagesOfSettings(BufferedImage image, String path){
        try {
            //sets image to given picture path
            imageOfSettings = ImageIO.read(getClass().getResourceAsStream("/settings.jpg"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void addActionListenerMainMenu(ActionListener listener){
        music_button.addActionListener(listener);
        goToMenu_button.addActionListener(listener);
        fireSound_button.addActionListener(listener);
    }
    @Override public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(imageOfSettings, getX(), getY(),
                getSize().width, getSize().height, null);

    }

}
