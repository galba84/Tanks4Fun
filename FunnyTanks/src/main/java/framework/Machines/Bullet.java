package framework.Machines;

import Server.User;
import framework.WorldElements.CellarMap;
import javafx.scene.paint.Color;

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
    private Point[] trace = new Point[]{};
    private Bullet[] load = new Bullet[]{};

    public Bullet[] getLoad() {
        return load;
    }

    public void setLoad(Bullet[] load) {
        this.load = load;
    }

    public Bullet(Point point, Integer velocityX, Integer velocityY) {
        Random rand = new Random();
        id = rand.nextInt();
        this.startPoint = point;
        this.endPoint = point;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        count = 10;
    }

    @Override
    public Point getNextPosition(CellarMap map) {
return new Point(1,1);
    }

    @Override
    public boolean isXDirectionPositive() {
        return false;
    }

    @Override
    public boolean isYDirectionPositive() {
        return false;
    }

    public boolean isMultyLoad() {
        return (load.length > 0);
    }

    public int getCalibre() {
        return 20;
    }

    public Color getColour() {
        return Color.RED;
    }

    public Integer getId() {
        return this.id;
    }

    private void updateSpeed(int gravity) {
        count--;
        if (velocityX > 0) {
            if (velocityX > 2) {
                velocityX -= 1;
            }
        } else {
            if (velocityX < -2)
                velocityX += 1;
        }
        velocityY += gravity;
    }

    @Override
    public Point getCurrentPosition() {
        return endPoint;
    }


    @Override
    public Point[] getTrace() {
        return trace;
    }

    public void calculateMove(CellarMap map, int gravity) {
        updateSpeed(gravity);
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
