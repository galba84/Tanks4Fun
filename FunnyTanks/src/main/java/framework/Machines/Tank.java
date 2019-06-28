package framework.Machines;

import framework.primitives.Point;

public class Tank extends Vehicle {
    public Tank(Point l, Point r, Cannon cannon) {
        this.left = l;
        this.right = r;
        this.cannon = cannon;
    }

    public Point left;
    public Point right;
    public Cannon cannon;
    public int id;
    public Bullet bullet;

//    public void shoot() {
//        bullet = new Bullet();
//        bullet.velocityX = 100;
//        bullet.velocityY = 10;
//        bullet.endPoint = new Point(300L, 350L);
//    }

}

