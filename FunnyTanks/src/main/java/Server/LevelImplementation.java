package Server;

import framework.Calculations.CurvesCreator;
import framework.Machines.*;
import framework.WorldElements.Cellar;
import framework.WorldElements.CellarMap;
import framework.Settings.Gravity;
import framework.WorldElements.TypeOfSoil;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LevelImplementation implements LevelInterface {
   public volatile Level level;

    synchronized   void  runBackgroundServer() {
        for (; ; ) {
            recalculateStep();
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void tankShoot(Tank tank) {
        Bullet bullet = tank.shoot();
        if (bullet != null)
            level.bullets.put(bullet.getId(), bullet);
    }

    private void updateTankBullet() {
        for (Map.Entry<Integer, Bullet> entry : level.bullets.entrySet()) {
            entry.getValue().calculateMove();
        }
    }

    private void calculateIntersections() {
        for (Map.Entry<Integer, Bullet> entry : level.bullets.entrySet()) {
            for (Point p : entry.getValue().getTrace()
            ) {
                if (hitSomething(p)){
                    createBlow(entry.getValue(), p);
                }
            }
        }
    }

    private boolean hitSomething(Point point) {
        if ( level.map.pointHitTheGround(point)) return true;
        else return false;
    }

    private void createBlow(Bullet bullet ,Point hitPoint) {
        Blow blow = new Blow(bullet,hitPoint);
        level.blows.add(blow);
        level.bullets.remove(bullet);
    }

    public LevelImplementation() {

    }

    private void recalculateStep() {

        updateTank();
        updateTankBullet();
        updateBlow();
        calculateIntersections();
//        updateMap(user);
    }


    private void updateTank() {
        if
        (level.tank1.left.x > level.map.cellars.length) {
            level.tank1.left.x = level.tank1.left.x - level.map.cellars.length;
        }
    }

    private synchronized void updateBlow() {
            for (Blow blow:level.blows
                 ) {
            if (blow.stage > 5)
                level.blows.remove(blow);
            else blow.stage++;
        }
    }

    public User createUser() {
        User user = new User();
        return user;
    }

    public Level createLevel(Integer maxX, Integer maxY, int maxStep) {
        this.level = new Level();
        level.bullets = new HashMap<>();
        level.borderLine = createBorderlineFromCurves(getCurvs(maxX));
        CellarMap cellarMap = new CellarMap(maxX, maxY);
        Gravity gravity = new Gravity();
        for (int x = 0; x < maxX; x++) {
            int borderPointY = level.borderLine.get(x);
            for (int y = 0; y < maxY; y++) {
                if (y > borderPointY) {
                    cellarMap.cellars[x][y] = new Cellar(TypeOfSoil.Ground.getValue());
                } else {
                    cellarMap.cellars[x][y] = new Cellar(TypeOfSoil.Air.getValue());

                }
            }
        }
        level.map = cellarMap;
        level.gravity = gravity;
        level.tank1 = new Tank(new Point(200, 300), new Point(230, 330), new Cannon(), new TankModel(25));

        level.tank2 = new Tank(new Point(600, 300), new Point(630, 330), new Cannon(), new TankModel(25));

        startBacgroundServer();
        return level;
    }

    private Map<Integer, Integer> createBorderlineFromCurves(Point[] points) {
        Map<Integer, Integer> result = new HashMap<Integer, Integer>() {
        };
        for (Point p : points
        ) {
            result.put(p.x, p.y);
        }
        return result;
    }

    private Point[] getCurvs(int maxX) {
        Point[] res = new Point[]{};
        CurvesCreator cc = new CurvesCreator();
        Point p1 = new Point(0, 200);
        Point p2 = new Point(0, 200);
        int x1 = 0, x2 = 0;

        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < maxX; i += 100) {
            if (i > 0) {
                p1 = new Point(p2.x, p2.y);
                x2 = p2.x + 100;
                int y2 = rand.nextInt(100) + 200;
                int distanceY = getDistance(p2.y, y2);
                if (distanceY < 10) {
                    if (p2.y > y2) {
                        y2 -= 10;
                    } else {
                        y2 += 10;
                    }
                }
                p2 = new Point(x2, y2);
            } else {
                x1 = i;
                int y1 = rand.nextInt(100) + 200;
                p1 = new Point(x1, y1);
                x2 = i + 100;
                int y2 = rand.nextInt(100) + 200;
                int distanceY = getDistance(y2, y1);
                if (distanceY < 10) {
                    if (y2 > y1) {
                        y2 += 10;
                    } else {
                        y2 -= 10;
                    }
                }
                p2 = new Point(x2, y2);
            }
            Point[] points = cc.drawCurve(p1, p2);
            if (points[0] == null) {
                System.out.println("shit");
            }
            res = concatenate(res, points);
        }
        return res;
    }

    int getDistance(int x11, int x22) {
        if (x11 > x22) {
            return x11 - x22;
        }
        return x22 - x11;
    }

    void startBacgroundServer() {
        Runnable r = new Runnable() {
            public void run() {
                runBackgroundServer();
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public static Point[] concatenate(Point[] a, Point[] b) {
        Point[] result = new Point[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;

    }

    public void moveTank() {
        level.tank1.acceleration += 5;
        level.tank1.updateTankSpeed();

    }
}
