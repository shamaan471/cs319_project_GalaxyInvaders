package Model.Bonus;

public class BulletBonus extends Bonus {

    private String bulletType;

    private final int LASER_UPGRADE = 50;

    private final int MISSILE_UPGRADE = 10;

    private int missileUpgrade;

    public BulletBonus()
    {
        super();
        setImage("/bulletPowerUp.png");
        determineUpgrade();

    }


    /*
    Determines what type of upgrade to apply
     */
    public void determineUpgrade(){
        bulletType = "laser";
        if(((int)(Math.random() * ((1) + 1))) == 1)
            bulletType = "missile";
    }



    public void setBulletType(String bulletType) {
        this.bulletType = bulletType;
    }

//--------------getters------------------------------
    public String getBulletType() {
        return bulletType;
    }

    public int getLASER_UPGRADE(){return LASER_UPGRADE;}

    public int getMISSILE_UPGRADE(){return MISSILE_UPGRADE;}

}
