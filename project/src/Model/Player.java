package Model;

import Model.Bonus.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player extends Rectangle{

    //player stats
    private int health;
    private int humanAboard;
    private boolean isShielded;
    private int score;
    private int playerSpeed;
    private boolean isRight = true;
    private int remainingMissiles;
    private int remainingLasers;
    private int enemyDestroyed;
    private String typeOfWeaponEquipped;
    private BufferedImage playerImage;
    private final int MAX_SHIELD_TIME = 700;
    private final int MAX_SPEED = 20;


    public Player(){
        health = 100;
        humanAboard = 0;
        score = 0;
        isShielded = false;
        remainingMissiles = 15;
        remainingLasers = 100;
        enemyDestroyed = 0;
        playerSpeed = 9;
        typeOfWeaponEquipped = "blaster";
        setImage("/player_right_stop.png");
        setSize(new Dimension(120, 41));
        setNewPosition(new Point(600, 300));
    }

    /*calculates movement of player object,
     * based on given list of pressed keys ( list holds max. 2keys pressed at same time)*/
    public void calculateMovement(int pressedKey)
    {
        //if list contains LEFT BUTTON
        if (pressedKey == 37 )
        {

            updateX(false);
            if(isRight) {
                isRight = false;
                if (isShielded) {
                    setImage("/player_left_shielded.png");
                }
                else{
                    setImage("/player_left.png");
                }
            }

        }
        //if list contains UP BUTTON
        else if (pressedKey == 38)
        {
            updateY(false);

        }
        //if list contains RIGHT button
        else if (pressedKey == 39)
        {
            updateX(true);
            if(!isRight) {
                isRight = true;
                if(isShielded){
                    setImage("/player_right_shielded.png");
                }
                else {
                    setImage("/player_right.png");
                }
            }

        }
        //if list contains DOWN button
        else if (pressedKey == 40)
        {
            updateY(true);

        }

    }

    //update X position of given player object
    public void updateX(boolean isIncreasing)
    {
        //increase/decrease X value only if player is inside border of scene
        if (isIncreasing == true && getLocation().x < 1150)
        {
            setLocation(getLocation().x+playerSpeed, getLocation().y);
            //setImage("player_right.png");
        }
        else if (isIncreasing == false && getLocation().x > 0)
        {
            setLocation(getLocation().x-playerSpeed, getLocation().y);
           // setImage("player_left.png");
        }
    }


    //update Y position of given player object
    public void updateY(boolean isIncreasing)
    {
        //increase/decrease Y value only if player is inside border of scene
        if (isIncreasing == true && getLocation().y  < 700)
        {
            setLocation(getLocation().x, getLocation().y+playerSpeed);
        }
        else if (isIncreasing == false && getLocation().y > 0)
        {
            setLocation(getLocation().x, getLocation().y-playerSpeed);
        }
    }


    /*
    applies the bonus upgrade to the player
     */
    public void setBonusUpgrade(Bonus bonus){
        //if the bonus upgrades bullet amount
        if(bonus instanceof BulletBonus){
            if(((BulletBonus) bonus).getBulletType() == "missile")
                remainingMissiles += ((BulletBonus) bonus).getMISSILE_UPGRADE();
            else
                remainingLasers += ((BulletBonus) bonus).getLASER_UPGRADE();
        }

        //if the bonus upgrades health
        if(bonus instanceof HealthBonus){
            health += ((HealthBonus) bonus).getHealthUpgrade();
        }

        //is shield upgrade
        if(bonus instanceof ShieldBonus){
            setIsShielded(true);
            if(isRight){
                setImage("/player_right_shielded.png");
            }
            else {
                setImage("/player_left_shielded.png");
            }
        }

        //if speed upgrade
        if(bonus instanceof SpeedBonus){
            if(playerSpeed < MAX_SPEED) {
                playerSpeed += ((SpeedBonus) bonus).getSpeedUpgrade();
            }
        }
    }

    //////////setters////////////////////////////

    //sets image for a given player object
    public void setImage(String path)
    {
        //initiation of image variable
        BufferedImage image = null;

        try {
            //sets image by using String path
            image = ImageIO.read(getClass().getResourceAsStream(path));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        //sets image to a player object
        playerImage = image;
    }

    //sets new, fixed location of player
    public void setNewPosition(Point point)
    {
        setLocation(point);
    }

    //sets player health
    public void setHealth(int health){this.health = health;}


    //sets player score
    public void setScore(int score){this.score = score;}

    //sets if player should be shielded
    public void setShielded(boolean shielded){this.isShielded = shielded;}

    public void setHumanAboard(int humanAboard) {
        this.humanAboard = humanAboard;
    }
    public void updateRemainingMissiles(){
        remainingMissiles--;
    }
    public void updateRemainingLasers(){
        remainingLasers--;
    }

    public void updateEnemiesDestroyed(){
        enemyDestroyed++;
    }

    public void setTypeOfWeaponEquiped(String weaponEquiped){this.typeOfWeaponEquipped = weaponEquiped;}

    public void setIsShielded(boolean shielded){
        this.isShielded = shielded;
        if(!shielded){
            if(isRight){
                setImage("/player_right.png");
            }
            else {
                setImage("/player_left.png");
            }
        }
    }

    public void resetEnem(int enemyDestroyed){this.enemyDestroyed = enemyDestroyed;}
    //--------------------------getters----------------------------------

    //gets center of an object in global coordinate system
    public Point getCenter()
    {
        return new Point(this.getLocation().x+this.getBounds().width/2, this.getLocation().y+this.getBounds().height/2);
    }


    //returns player image
    public BufferedImage getPlayerImage(){
        return playerImage;
    }

    //gets whether the player is facing right
    public boolean getIsRight(){return isRight;}

    //returns player health
    public int getHealth(){return this.health;}

    //returns score
    public int getScore(){return this.score;}

    //returns if shielded
    public boolean getIsShielded(){return isShielded;}

    public int getRemainingMissiles(){return remainingMissiles;}
    public int getRemainingLasers(){return remainingLasers;}
    public String getTypeOfWeaponEquiped(){return typeOfWeaponEquipped;}

    public int getEnemiesDestroyed(){return enemyDestroyed;}

    public int getHumanAboard() {
        return humanAboard;
    }

    public int getMAX_SHIELD_TIME(){return MAX_SHIELD_TIME;}
}

