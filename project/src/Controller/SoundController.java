package Controller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class SoundController {

    public SoundController(){}

    public void playBGM(){
        URL url = this.getClass().getClassLoader().getResource("bgm.wav");
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
            clip.open(inputStream);
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-15.0f);
            clip.start();
            clip.loop(-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSound(String string, float volume)
    {
        URL url = this.getClass().getClassLoader().getResource(string + ".wav");
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
            clip.open(inputStream);
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(volume);
            if(string.equals("boss_laser"))
                control.setValue(volume);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}