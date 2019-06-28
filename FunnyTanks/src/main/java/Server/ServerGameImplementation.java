package Server;

import framework.Machines.Blow;
import framework.Machines.Bullet;
import framework.Machines.Cannon;
import framework.Machines.Tank;
import framework.WorldElements.Cellar;
import framework.WorldElements.CellarMap;
import framework.primitives.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ServerGameImplementation implements ServerGame {
    private Map<Long, Long> borderLine;
    CellarMap cellarMap;
    Tank tank;

    void runBackgroundServer() {
        for (int i = 1; i > 0; ) {
            recalculateStep();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void tankShoot() {
        if (tank.bullet == null) {
            tank.bullet = new Bullet(tank.left, 20L, -8L);
        }
    }

    private void updateTankBullet() {
        if (tank.bullet != null) {
            tank.bullet.updateSpeed();
            tank.bullet.updatePosition();
            if (cellarMap.pointHitTheGround(tank.bullet.endPoint)) {
                createBlow(tank.bullet);
                tank.bullet = null;
            }
        }
    }

    private void createBlow(Bullet bullet) {
        cellarMap.blow = new Blow(bullet);
    }

    public ServerGameImplementation() {
        Runnable r = new Runnable() {
            public void run() {
                runBackgroundServer();
            }
        };

        Thread t = new Thread(r);
        t.start();
    }

    public CellarMap getCellarMap() {
        if (cellarMap == null)
            createRandomMap(800L, new Long(800), 9);
        return cellarMap;
    }

    public Map<Long, Long> getBorderLine() {
        if (cellarMap == null)
            createRandomMap(800L, new Long(800), 9);
        return borderLine;
    }

    public Tank getTank() {
        if (tank == null) {
            tank = new Tank(new Point(10L, 12L), new Point(20L, 22L), new Cannon());
        }
        return tank;
    }

    public void moveTank(int speed) {
        if (tank == null) {
            tank = new Tank(new Point(10L, 12L), new Point(20L, 22L), new Cannon());
        }
        tank.speed += speed;
        if (tank.speed > 0)
            tank.accelerate = true;
        else tank.accelerate = false;
    }

    private void recalculateStep() {
        if (tank != null && tank.accelerate == true && tank.speed > 0) {
            tank.left.x += tank.speed;
            tank.speed--;
            if (tank.left.x > cellarMap.cellars.length) {
                tank.left.x = tank.left.x - cellarMap.cellars.length;
            }
        }
        if (tank != null && tank.bullet != null)
            updateTankBullet();
        if (cellarMap!=null&&cellarMap.blow != null)
            updateBlow();
    }

    private void updateBlow() {
        if (cellarMap.blow.stage > 5)
            cellarMap.blow = null;
        else cellarMap.blow.stage++;
    }

    public void createRandomMap(Long maxX, Long maxY, int maxStep) {
        this.cellarMap = new CellarMap(maxX, maxY);
        this.borderLine = new HashMap<Long, Long>();
        Random generator = new Random();
        int step = 0;
        int randX = randX = generator.nextInt(9) + 200;
        Long[][] map1 = new Long[maxX.intValue()][maxY.intValue()];
        for (int x = 0; x < maxX; x++) {
            step++;
            if (step > maxStep) {
                randX = generator.nextInt(9) + 200;
                step = 0;
            }
            for (int y = 0; y < maxY; y++) {
                if (y == randX) {
                    this.borderLine.put(x + 1L, new Long(y));
                }
                if (y > randX) {
                    cellarMap.cellars[x][y] = new Cellar(1L);
                } else {
                    cellarMap.cellars[x][y] = new Cellar(0L);
                }

            }
        }
    }
}
