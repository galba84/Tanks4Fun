package framework.Machines;

import java.awt.*;

public class Cannon extends GameObject {
    public Cannon(int angle, Point mountingPoint) {
        this.angle = angle;
        this.mountingPoint = mountingPoint;
        this.edgePoint=mountingPoint;
    }

    public void update(){

    }

    public Point mountingPoint;
    public Point edgePoint;
    public int angle;
    public int type;
    public int id;
}
