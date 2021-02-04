package Model.Bullet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bullet extends Rectangle {

    private int speed;
    private String type;
    private int damage;

    private boolean isRight;

    private boolean isInCollision;

    private BufferedImage image;


    public Bullet(boolean right){
        isRight = right;

    }

    public void calculateMovement(){

        int stepX = speed;

        /*
        if(!isRight) {
            stepX = -1 * stepX;
        }
        */
        int x = getLocation().x + stepX;
        int y = getLocation().y;
        setLocation(x, y);

    }

    //----------------------setters-------------------------------

    //sets image of a bullet to paint on scene
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


    //setting size
    public void setBulletSize(Dimension dimension){
        setSize(dimension);
    }


    //setting size
    public void setSpeed(int speed){
        this.speed = speed;
    }


    //setting damage
    public void setDamage(int damage){this.damage = damage;}

    public void setIsRight(boolean isRight){this.isRight = isRight;}


    //----------------------getters-----------------------------------


    public Point getCenter()
    {
        return new Point(this.getLocation().x+this.getBounds().width/2, this.getLocation().y+this.getBounds().height/2);
    }


    public BufferedImage getImage() {return image;}


    public int getSpeed() {return speed;}



    public boolean getIsInCollision(){return isInCollision;}

    //returns damage
    public int getDamage(){return this.damage;}

    public boolean getIsRight(){return this.isRight;}

}