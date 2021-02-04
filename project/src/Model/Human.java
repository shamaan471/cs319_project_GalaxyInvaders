package Model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Human extends Rectangle {

    private int fallingSpeed;
    private BufferedImage humanImage;

    public Human(){
        fallingSpeed = 5;
        setSize(new Dimension(10,20));
        setImage("/human.png");
    }


    //Sets a random point as the human objects location
    public void setSpawnLocation(int xPos, int yPos)
    {
        this.setLocation(xPos, yPos);
    }

    //set new position of human object
    public void setNewPosition(int x, int y)
    {
        setLocation(x, y);
    }


    //updates human movement
    public void updateMovement()
    {
        if (this.getPosition().y < 800)   ////////////////////////////////!!!!!!!!!!!!!!!////////////////////
        {
            this.setNewPosition(this.getPosition().x,
                    this.getPosition().y + fallingSpeed);
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
        humanImage = image;
    }


    //-----------------getters--------------------------------

    public Point getPosition()
    {
        return getLocation();
    }

    //Returns the position of the human object as a point
    public Point getCenter()
    {
        return (new Point(this.getLocation().x + this.getBounds().width / 2, this.getLocation().y + this.getBounds().height / 2));
    }

    public BufferedImage getHumanImage(){return humanImage;}

}
