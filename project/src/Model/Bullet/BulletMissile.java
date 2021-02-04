package Model.Bullet;
import Model.Enemy.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class BulletMissile extends Bullet {
    String type;

    private double deltaX;
    private double deltaY;
    private Enemy target;

    public BulletMissile(boolean right){
        super(right);
        this.setSpeed(20);
        this.setDamage(20);
        type = "Missile";
    }



    //calculates the movement of the bullet
    @Override
    public void calculateMovement(){

        determineDeltas();
        //takes into consideration factor step for X and Y axis
        int stepX = (int)(this.getSpeed()* deltaX);
        int stepY = (int)(this.getSpeed()* deltaY);

        //updating missile image
        updateImageDirection(stepX);

        //calculate new position
        int xLoc = this.getLocation().x + stepX;
        int yLoc = this.getLocation().y + stepY;

        //sets new position
        this.setLocation(xLoc, yLoc);


    }


    public void determineDeltas(){
        if(target != null){
            double angle = Math.atan2(target.getCenterX() - this.getCenterX(), target.getCenterY() - this.getCenterY());
            deltaX = Math.sin(angle);
            deltaY = Math.cos(angle);
        }
        else {
            deltaX = 1;
            deltaY = 0;
        }
    }



    public void updateImageDirection(int x){
        if (x < 0 && getIsRight()){
            setImage("/missile_left.png");
            setIsRight(false);
        }
        else if( x > 0 && !getIsRight()){
            setImage("/missile_right.png");
            setIsRight(true);
        }
    }





    //------------------setters------------------
    public void setTarget(Enemy enemy){
        this.target = enemy;
    }


    public void setDeltas(double x, double y){
        this.deltaX = x;
        this.deltaY =y;
    }



    //-----------------getters------------------
    public Enemy getTarget(){return target;}

    public double getDeltaX(){return deltaX;}

    public double getDeltaY(){return deltaY;}
}
