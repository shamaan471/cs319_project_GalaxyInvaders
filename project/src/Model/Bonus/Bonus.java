package Model.Bonus;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.IOException;

public class Bonus extends Rectangle {


    private String bonusType;
    private Image image;
    private String bonusSound;
    private AudioInputStream audioInput;
    private Clip clip;

    public Bonus()
    {
        setSize(new Dimension(20, 20));
        playBonusSound();
    }

    public void playBonusSound()
    {

    }
    public void setSpawnPosition(int x , int y)
    {
        this.setLocation(x,y);
    }
    public void setPosition(int x , int y)
    {
        this.setLocation(x,y);
    }
    public void getPosition()
    {
        this.getLocation();
    }

    public void setImage(String path)
    {
        //initiation of a image variable
        image = null;
        try {
            //sets image by a given picture path
            image = ImageIO.read(getClass().getResourceAsStream(path));
        }
        catch (IOException e){
            e.printStackTrace();
        }

        //assigns image to an bullet object

    }


    //----------------------------------------Getters------------------------------
    public String getBonusType() {
        return bonusType;
    }
    public Image getImage() {
        return image;
    }
    public String getBonusSound() {
        return bonusSound;
    }
    public AudioInputStream getAudioInput() {
        return audioInput;
    }
    public Clip getClip() {
        return clip;
    }

    // ----------------------------------------Setter----------------------------------------

    public void setBonusType(String bonusType) {
        this.bonusType = bonusType;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setBonusSound(String bonusSound) {
        this.bonusSound = bonusSound;
    }

    public void setAudioInput(AudioInputStream audioInput) {
        this.audioInput = audioInput;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }
}
