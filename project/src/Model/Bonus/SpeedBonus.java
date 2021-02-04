package Model.Bonus;

public class SpeedBonus extends Bonus{

    private final int SPEED_UPGRADE = 5;

    public SpeedBonus(){
        super();
        setImage("/speedPowerUp.png");
    }

    public int getSpeedUpgrade() {
        return SPEED_UPGRADE;
    }

}
