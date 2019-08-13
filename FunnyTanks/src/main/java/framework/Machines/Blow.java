package framework.Machines;


import framework.primitives.Position;

import java.awt.*;

public class Blow extends GameObject{
    public Bullet bullet;
    public int stage;

    public Blow(Bullet bullet, Point hitPoint) {
        position=new Position();
        position.center=hitPoint;
        this.bullet = bullet;
        stage = 0;
    }
}
