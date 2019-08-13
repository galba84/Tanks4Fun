package framework.Machines;

import framework.primitives.Point;

public class Blow extends GameObject{
    public Bullet bullet;
    public int stage;

    public Blow(Bullet bullet) {
        this.bullet = bullet;
        stage = 0;
    }
}
