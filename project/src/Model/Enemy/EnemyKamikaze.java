package Model.Enemy;
import Model.Player;

import java.awt.*;

public class EnemyKamikaze extends Enemy {
    private final int KAMIKAZE_HEIGHT = 50;
    private final int KAMIKAZE_WIDTH = 50;

    private double deltaX;
    private double deltaY;
    private Player target;
    private boolean isRight;

    public EnemyKamikaze() {
        this.setHealth(10);
        this.setSpeed(15);
        setKamikazeSize();
        isRight = true;
        setImage("/kamikaze_right.png");
        //setSpawnLocation(-50);
        //JUST FOR TEST. NEED TO CHANGE IT LATER
    }

    @Override
    public void updateMovement()
    {
        determineDeltas();
        //takes into consideration factor step for X and Y axis
        int stepX = (int)(this.getSpeed()* deltaX);
        int stepY = (int)(this.getSpeed()* deltaY);

        //calculate new position
        int xLoc = this.getLocation().x + stepX;
        int yLoc = this.getLocation().y + stepY;

        //updating image direction
        updateImageDirection(stepX);

        //sets new position
        this.setLocation(xLoc, yLoc);
    }

    public void determineDeltas()
    {
        double angle = Math.atan2(target.getCenterX() - this.getCenterX(), target.getCenterY() - this.getCenterY());
        deltaX = Math.sin(angle);
        deltaY = Math.cos(angle);
    }

    private void updateImageDirection(int x){
        if (x < 0 && isRight){
            setImage("/kamikaze_left.png");
            isRight = false;
        }
        else if( x > 0 && !isRight){
            setImage("/kamikaze_right.png");
            isRight = true;
        }
    }

    public void setTarget(Player player){
        this.target = player;
    }

    public void setKamikazeSize() { setSize(new Dimension(KAMIKAZE_WIDTH, KAMIKAZE_HEIGHT)); }
}