package Model.Bullet;

public class BulletBlaster extends Bullet {


    String type;

    public BulletBlaster(boolean right){
        super(right);
        this.setDamage(10);
        type = "Blaster";
    }

}
