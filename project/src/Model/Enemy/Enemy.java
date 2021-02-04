package Model.Enemy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Enemy extends Rectangle
{
    //Enemy properties
    private int health;
    private int speed;
    private boolean isDestroyed = false;
    private BufferedImage enemyImage;
    private boolean isCollided = false;

    public Enemy() {

    }







    //Sets a random point as the enemy objects location
    public void setSpawnLocation(int xPos)
    {
        Random random = new Random();
        this.setLocation(xPos, random.nextInt(700));
    }



    //set new position of enemy object
    public void setNewPosition(int x, int y)
    {
        setLocation(x, y);
    }



    public void setEnemySize(Dimension dimension){
        this.setSize(dimension);
    }




    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void updateMovement()
    {
        if (this.getPosition().x > -50)   ////////////////////////////////!!!!!!!!!!!!!!!////////////////////
        {
            this.setNewPosition(this.getPosition().x - this.speed,
                    this.getPosition().y);
        }
    }

    //Sets the image of the enemy object
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
        //sets image to an enemy object
        enemyImage = image;
    }


    //sets health
    public void setHealth(int health){
        this.health = health;
    }

    //sets the speed
    public void setSpeed(int speed){
        this.speed = speed;
    }



    public void setCollided(boolean collided){this.isCollided = collided;}

    public void setDestroyed(boolean destroyed){this.isDestroyed = destroyed;}



    //-----------------getters--------------------------------

    //gets current position of an enemy in global coordinate system
    public Point getPosition()
    {
        return getLocation();
    }

    //Returns the position of the enemy object as a point
    public Point getCenter()
    {
        return (new Point(this.getLocation().x + this.getBounds().width / 2, this.getLocation().y + this.getBounds().height / 2));
    }


    //gets speed of given enemy object
    public int getSpeed()
    {
        return speed;
    }


    //get health of the enemy
    public int getHealth(){return this.health;}

    //returns image
    public BufferedImage getImage() { return enemyImage; }

}
