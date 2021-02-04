package Model.Bonus;

public class HealthBonus extends Bonus{
    public int getHealthUpgrade() {
        return healthUpgrade;
    }

    public void setHealthUpgrade(int healthUpgrade) {
        this.healthUpgrade = healthUpgrade;
    }

    private int healthUpgrade;

    public HealthBonus()
    {
        super();
        setImage("/healthPowerUp.png");
        healthUpgrade = 100;
    }



    //------getters-----------------
    public int getHealthUpgradeAmount(){return healthUpgrade;}


}
