package framework.Machines;

import framework.primitives.Point;

public class Bullet {
    public Point startPoint;
    public int id;
    public int speed;
    public Point endPoint;
    public int type;
    public Long velocityX;
    public Long velocityY;
    public int count;

    public Bullet(Point point, Long velocityX, Long velocityY) {
        this.startPoint = new Point(point.x+20,point.y-30);
        this.endPoint = new Point(point.x+20,point.y-30);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        count = 10;
    }


    public void updateSpeed() {
        count--;
        if (velocityX >= 1) {
//            velocityX -= 1;
        }
//        if (velocityY > 1) {
            velocityY ++;

//        }
    }

    public void updatePosition() {
        this.endPoint.x += velocityX;
        this.endPoint.y += velocityY;
    }
}
