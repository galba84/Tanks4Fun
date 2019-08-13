package framework.Machines;

import Server.User;

import java.awt.*;
import java.util.Random;

public class Bullet extends GameObject implements Moving {
    public User owner;
    public Point startPoint;
    public Integer id;
    public int speed;
    public Point endPoint;
    public int type;
    public Integer velocityX;
    public Integer velocityY;
    public int count;
    private Point[] trace;

    public Bullet(Point point, Integer velocityX, Integer velocityY) {
        Random rand = new Random();
        id = rand.nextInt();
        this.startPoint = new Point(point.x + 20, point.y - 30);
        this.endPoint = new Point(point.x + 20, point.y - 30);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        count = 10;
    }

    public Integer getId() {
        return this.id;
    }

    private void updateSpeed() {
        count--;
        if (velocityX >= 1) {
            velocityX -= 1;
        }
//        if (velocityY > 1) {
        velocityY++;
//        }
    }

    @Override
    public Point[] getTrace() {
        return trace;
    }

    @Override
    public void calculateMove() {
        updateSpeed();
        setTrace();
        updatePosition();
    }

    private void setTrace() {
        trace = new Point[]{new Point(this.endPoint.x, this.endPoint.y), new Point(this.endPoint.x + velocityX, this.endPoint.y + velocityY)};
    }

    public void updatePosition() {
        this.endPoint.x += velocityX;
        this.endPoint.y += velocityY;
    }
}
