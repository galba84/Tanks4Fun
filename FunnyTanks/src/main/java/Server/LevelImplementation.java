package Server;

import framework.Calculations.CurvesCreator;
import framework.Machines.*;
import framework.WorldElements.Cellar;
import framework.WorldElements.CellarMap;
import framework.Settings.Gravity;
import framework.WorldElements.TypeOfSoil;
import java.awt.*;
import java.util.*;

public class LevelImplementation implements LevelInterface {
    public volatile Level level;

    synchronized void runBackgroundServer() {
        for (; ; ) {
            recalculateStep();
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void moveCannon(int angle) {
        level.tank1.cannon.angle = angle;
    }

    public void tankShoot(Tank tank, Integer weapon) {
        Bullet bullet = tank.shoot(weapon);
        if (bullet != null)
            level.bullets.add(bullet);
    }

    private void updateTankBullet() {
        for (Bullet bullet : level.bullets
        ) {
            bullet.calculateMove(level.map, level.gravity.gravity);
            Point currentPosition = bullet.getCurrentPosition();
            if (isOutOfRealm(currentPosition))
                level.bullets.remove(bullet);
        }
    }

    boolean isOutOfRealm(Point position) {
        int x = this.level.map.cellars.length;
        int y = this.level.map.cellars[0].length;
        if (position.x >= x || position.y >= y || position.x < 0 || position.y < 0) {
            return true;
        }
        return false;
    }

    private void calculateIntersections() {
        for (Bullet bullet : level.bullets
        ) {
            for (Point point : bullet.getTrace()
            ) {
                if (isOutOfRealm(point)) {
                    level.bullets.remove(bullet);
                    break;
                }
                if (hitSomething(point)) {
                    createBlow(bullet, point);
                }
            }
        }
    }

    private boolean hitSomething(Point point) {
        if (level.map.pointHitTheGround(point)) return true;
        else return false;
    }

    private void createBlow(Bullet bullet, Point hitPoint) {
        Blow blow = new Blow(bullet, hitPoint);
        level.blows.add(blow);
        if (bullet.isMultyLoad()) {
            Bullet[] bullets = bullet.getLoad();
            if (bullets.length > 0) {
                int angle = (int) Balistics.angleOf(bullet.startPoint, bullet.endPoint);
                for (Bullet b : bullets
                ) {
                    Bullet bb = new Bullet(hitPoint, (int) Balistics.getXSpeed(angle, 10), (int) Balistics.getYSpeed(angle, 30));
                    level.bullets.add(bb);
                }
            }
        }

        level.bullets.remove(bullet);

    }

    public LevelImplementation() {

    }

    private void recalculateStep() {
        updateTank();
        updateTankBullet();
        updateBlow();
        calculateIntersections();
        updateMap();
    }


    private void updateTank() {
        if (level.tank1.left.x > level.map.cellars.length) {
            level.tank1.left.x = level.tank1.left.x - level.map.cellars.length;
        }
        level.tank1.calculateMove(level.map);
        level.tank2.calculateMove(level.map);
    }

    private synchronized void updateBlow() {
        for (Blow blow : level.blows
        ) {
            destroyLand(blow);
            if (blow.stage > 5)
                level.blows.remove(blow);
            else {
                blow.stage++;
                blow.radius += 2;
            }

        }
    }

    void destroyLand(Blow blow) {
        LinkedList<Point> b = blow.getCircleAsListOfPoints();
        for (Point p : b
        ) {
            if (p.x < level.map.cellars.length && p.y < level.map.cellars[0].length && p.x > 0 && p.y > 0)
                level.map.cellars[p.x][p.y].type = TypeOfSoil.Air;
        }
    }

    public User createUser() {
        User user = new User();
        return user;
    }

    public Level createLevel(Integer maxX, Integer maxY, int maxStep) {
        level = new Level();
        level.xDimension = maxX;
        level.yDimension = maxY;
        level.borderLine = createBorderlineFromCurves(getCurvs(maxX));
        level.map = new CellarMap(maxX, maxY);
        level.gravity = new Gravity();
        fillCellarMap();

        level.tank1 = new Tank(new Point(150, 100), new Point(190, 100), new Cannon(340, new Point(150, 100)), TankSettings.getSettings(TankModels.T84));
        level.tank2 = new Tank(new Point(600, 100), new Point(630, 330), new Cannon(120, new Point(600, 100)), TankSettings.getSettings(TankModels.Type99));

        startBacgroundServer();
        return level;
    }

    private void fillCellarMap() {
        for (int x = 0; x < level.xDimension; x++) {
            for (int y = 0; y < level.yDimension; y++) {
                if (y > level.borderLine.get(x)) {
                    level.map.cellars[x][y] = new Cellar(TypeOfSoil.Ground.getValue());
                } else {
                    level.map.cellars[x][y] = new Cellar(TypeOfSoil.Air.getValue());
                }
            }
        }
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
        Thread t = new Thread() {
            @Override
            public void run() {
                runBackgroundServer();
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                }
            }
        };
        t.setDaemon(true);
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

    void updateMap() {
        level.map.update();
    }
}
