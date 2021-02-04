package Model.Enemy;

import java.awt.*;

public class EnemySplitter extends Enemy {
    private final int SPLITTER_HEIGHT = 50;
    private final int SPLITTER_WIDTH = 50;

    public EnemySplitter() {
        this.setHealth(10);
        this.setSpeed(3);
        setKamikazeSize();
        setImage("/Splitter.png");
    }

    public void setKamikazeSize() { setSize(new Dimension(SPLITTER_WIDTH, SPLITTER_HEIGHT)); }
}
