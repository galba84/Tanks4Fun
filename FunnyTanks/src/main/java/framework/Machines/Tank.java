package framework.Machines;

import Server.User;
import framework.WorldElements.CellarMap;


import java.awt.*;
import java.util.Random;

public class Tank extends Vehicle implements Moving {
    public Integer acceleration;
    public User owner;
    boolean xDirectionPositive;
    boolean yDirectionPositive;
    public TankSettings tankSettings;

    public Tank(Point l, Point r, Cannon cannon, TankSettings tankSettings) {
        this.left = l;
        this.right = r;
        this.cannon = cannon;
        this.buttetMagazine = 10;
        this.tankSettings = tankSettings;
    }

    @Override
    public void getNextPosition(CellarMap map) {

    }

    @Override
    public boolean isXDirectionPositive() {
        return xDirectionPositive;
    }

    @Override
    public boolean isYDirectionPositive() {
        return yDirectionPositive;
    }

    public Point left;
    public Point right;
    public Cannon cannon;
    public int buttetMagazine;


    public Bullet shoot(Integer weapon) {
        if (buttetMagazine <= 0) return null;

        return getBullet(weapon);
    }

    private Bullet getBullet(Integer weapon) {
        int xv = (int) Balistics.getXSpeed((double) cannon.angle, (double) 50);
        int yv = (int) Balistics.getYSpeed((double) cannon.angle, (double) 30);

        if (weapon.equals(2))
            return new BulletNuke(this.cannon.edgePoint, xv, yv);
        return new Bullet(this.cannon.edgePoint, xv, yv);

    }

    @Override
    public Point getCurrentPosition() {
        return left;
    }

    public Point[] getTrace() {
        return new Point[]{};
    }

    public void calculateMove(CellarMap map) {
        updatePosition(map);

    }


    public void updateTankSpeed() {
        if (acceleration > 0)
            increaseSpeed();
        if (acceleration > 0)
            acceleration--;
    }


    public void updatePosition(CellarMap map) {
        if (speed > 0)
            speed--;
        this.left.x += speed;
        Point p = new Point(left.x, left.y);
        if (map.isPointAir(p)) {
            this.left.y += 6;
        }
        while (map.isPointGround(left)) {
            left.y--;
        }

        for (int i = 0; i < 6; i++) {
            if (isBarrierInFront(map)) {
                this.left.y -= 1;
            }
        }
        if (isBarrierInFront(map)) {
            stepBack();
        }

        double x_new = (this.left.x) + 30 * Math.cos(Math.toRadians(this.cannon.angle));
        double y_new = (this.left.y) + 30 * Math.sin(Math.toRadians(this.cannon.angle));

        this.cannon.mountingPoint = this.left;
        this.cannon.edgePoint = new Point((int) x_new, (int) y_new);

    }

    boolean isBarrierInFront(CellarMap map) {
        if (map.pointHitTheGround(this.left)) {
            Point tryPoind = new Point(this.left.x, this.left.y + this.tankSettings.wheelRadius);
            if (map.pointHitTheGround(tryPoind)) {
                return true;
            }
        }
        return false;
    }

    void stepBack() {
        speed = 0;
        left.x -= 6;
    }

    private void increaseSpeed() {
        if (this.tankSettings.maxSpeed > this.speed) {
            this.speed += 1;
        }
    }

    public Integer getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Integer acceleration) {
        this.acceleration = acceleration;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Integer getMaxTankSpeed() {
        return this.tankSettings.maxSpeed;
    }



    public Point getLeft() {
        return left;
    }

    public void setLeft(Point left) {
        this.left = left;
    }

    public Point getRight() {
        return right;
    }

    public void setRight(Point right) {
        this.right = right;
    }

    public Cannon getCannon() {
        return cannon;
    }

    public void setCannon(Cannon cannon) {
        this.cannon = cannon;
    }

    public int getButtetMagazine() {
        return buttetMagazine;
    }

    public void setButtetMagazine(int buttetMagazine) {
        this.buttetMagazine = buttetMagazine;
    }
}

