package Server;

import framework.Machines.Blow;
import framework.Machines.Bullet;
import framework.Machines.Tank;
import framework.WorldElements.CellarMap;
import framework.Settings.Gravity;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class Level implements Cloneable, Serializable {
    Integer id;
    public Map<Integer, Integer> borderLine;
    public CellarMap map;
    public Gravity gravity;
    public volatile ArrayBlockingQueue<Bullet> bullets = new ArrayBlockingQueue(999);
    public volatile ArrayBlockingQueue<Blow> blows = new ArrayBlockingQueue(999);
    public int xDimension;
    public int yDimension;
    public int PointsInOnePercentOfScreenX = 10;
    public int PointsInOnePercentOfScreenY = 8;
    public int secondsPerGamePeriod = 10;
    public Tank tank1;
    public Tank tank2;

    public Level() {
        setId();
    }

    public Integer getId() {
        return this.id;
    }

    private void setId() {
        Random rand = new Random(1000);
        this.id = rand.nextInt(1000) + 10;
    }

}
