package Model.Enemy;

public class EnemyBoss extends Enemy {
    private final int BOSS_HEIGHT = 150;
    private final int BOSS_WIDTH = 150;

    private boolean isGoingDown = false;

    public EnemyBoss(){
        setHealth(200);
        setSpeed(3);
        setImage("/boss_left.png");
        //setSize(new Dimension(BOSS_WIDTH, BOSS_HEIGHT));
    }


    @Override
    public void updateMovement(){
        if(this.getLocation().x > 1000){
            this.setLocation(this.getLocation().x - getSpeed(), this.getLocation().y);
        }
        else {
            setIsGoingDown();
            if (!isGoingDown) {
                this.setLocation(this.getLocation().x, this.getLocation().y - getSpeed());
            }
            else{
                this.setLocation(this.getLocation().x, this.getLocation().y + getSpeed());
            }
        }
    }


    private void setIsGoingDown(){
        if(this.getLocation().y > 700 && this.getLocation().x <= 1000){
            isGoingDown = false;
        }
        else if(this.getLocation().y < 50 && this.getLocation().x <= 1000){
            isGoingDown = true;
        }
    }



    public int getBOSS_HEIGHT(){return BOSS_HEIGHT;}

    public int getBOSS_WIDTH(){return BOSS_WIDTH;}
}
