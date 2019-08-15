package framework.Machines;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.Random;

public class BulletNuke extends Bullet {
    @Override
    public int getCalibre() {
        return 33;
    }

    @Override
    public Color getColour() {
        return Color.BEIGE;
    }

    BulletNuke(Point point, Integer velocityX, Integer velocityY) {
        super(point, velocityX, velocityY);
        Random rand = new Random();
        id = rand.nextInt();
        this.startPoint = new Point(point.x + 20, point.y - 30);
        this.endPoint = new Point(point.x + 20, point.y - 30);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        count = 10;
        setLoad(new Bullet[]{new Bullet(point, velocityX, velocityY), new Bullet(point, velocityX, velocityY), new Bullet(point, velocityX, velocityY)});

    }
}
