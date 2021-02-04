package Model.Enemy;

import java.awt.*;

public class EnemyShooter extends Enemy {
    private final int SHOOTER_HEIGHT = 50;
    private final int SHOOTER_WIDTH = 50;
    private final int SHOOTER_HUMAN_HEIGHT = 70;
    boolean isCarryingHuman;


    public EnemyShooter(boolean isCarryingHuman){
        this.isCarryingHuman = isCarryingHuman;
        this.setHealth(10);
        this.setSpeed(5);
        setEnemyShooterImage();
    }


    //sets the image of the enemy
    public void setEnemyShooterImage(){
        if(isCarryingHuman){
            setImage("/enemy_left_human.png");
            setShooterSize(SHOOTER_WIDTH, SHOOTER_HUMAN_HEIGHT);
        }
        else{
            setImage("/enemy_left.png");
            setShooterSize(SHOOTER_WIDTH, SHOOTER_HEIGHT);
        }
    }



    public void setShooterSize(int width, int height){
        setSize(new Dimension(width, height));
    }


    //////////getters///////////////
    public boolean getIsCarryingHuman(){
        return isCarryingHuman;
    }
}
