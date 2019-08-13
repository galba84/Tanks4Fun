package framework.Machines;

import Server.User;

import java.awt.*;
import java.util.Random;

public class Tank extends Vehicle implements Moving {
    public Integer acceleration;
    public User owner;
    public Integer maxTankSpeed;
    public TankModel tankModel;

    public Tank(Point l, Point r, Cannon cannon, TankModel model) {
        this.left = l;
        this.right = r;
        this.cannon = cannon;
        this.buttetMagazine = 10;
        Random random = new Random();
        id = random.nextInt(1000) + 10;
        this.tankModel = model;
        acceleration = 0;

    }

    public Point left;
    public Point right;
    public Cannon cannon;
    public int buttetMagazine;


    public Bullet shoot() {
        if (buttetMagazine <= 0) return null;
        Bullet bullet = new Bullet(left, 25, -8);
        return bullet;
    }

    public Point[] getTrace() {
        return new Point[]{};
    }

    public void calculateMove() {
    }

    public void updateTankSpeed() {
        if (acceleration > 0)
            increaseSpeed();
        if (acceleration > 0)
            acceleration--;
        updatePosition();
    }

    @Override
    public void updatePosition() {
        this.left.x += speed;

    }

    private void increaseSpeed() {
        if (this.tankModel.maxSpeed > this.speed) {
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
        return maxTankSpeed;
    }

    public void setMaxTankSpeed(Integer maxTankSpeed) {
        this.maxTankSpeed = maxTankSpeed;
    }

    public TankModel getTankModel() {
        return tankModel;
    }

    public void setTankModel(TankModel tankModel) {
        this.tankModel = tankModel;
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

