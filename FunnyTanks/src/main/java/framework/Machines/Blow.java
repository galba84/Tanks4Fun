package framework.Machines;


import framework.primitives.Position;

import java.awt.*;
import java.util.LinkedList;

public class Blow extends GameObject {
    public Bullet bullet;
    public int stage;
    public int radius = 5;
    public Point position;

    public Blow(Bullet bullet, Point hitPoint) {
        position = new Point();
        position = hitPoint;
        this.bullet = bullet;
        stage = 0;
    }

    public void moveCircle(int a, int b) {
        position.x += a;
        position.y += b;
        return;
    }

    public void zoomCircle(int k) {
        radius += k;
    }

    boolean isInCircle(int _x, int _y) {

        int px = _x - position.x;
        int py = _y - position.y;
        if (Math.pow((px), 2) + (Math.pow((py), 2)) <= Math.pow(radius, 2)) {
            return true;
        } else {
            return false;
        }
    }

    public LinkedList getCircleAsListOfPoints() {
        LinkedList points = new LinkedList<Point>();
        while (radius % 2 > 0) {
            radius++;
        }
        for (int i = position.x - radius; i < position.x + radius; i++) {
            for (int j = position.y - radius; j < position.y + radius; j++) {
                if (isInCircle(i, j)) {
                    points.add(new Point(i, j));
                }
            }
        }
        return points;
    }
}
