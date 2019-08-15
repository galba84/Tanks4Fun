package framework.Machines;

import java.awt.*;

public class Cannon extends GameObject {
    public Cannon(int angle, Point mountingPoint) {
        this.angle = angle;
        this.mountingPoint = mountingPoint;
    }

    public Point mountingPoint;
    public Point edgePoint = new Point(0, 0);
    public int angle;
    public int type;
    public int id;
}
