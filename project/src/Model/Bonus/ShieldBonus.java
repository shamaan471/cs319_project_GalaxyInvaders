package Model.Bonus;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ShieldBonus extends Bonus{

    private boolean shieldUpgrade;

    public ShieldBonus()
    {
        super();
        setImage("/shieldPowerUp.png");
    }

    public boolean isShieldUpgrade() {
        return shieldUpgrade;
    }

    public void setShieldUpgrade(boolean shieldUpgrade) {
        this.shieldUpgrade = shieldUpgrade;
    }
}
