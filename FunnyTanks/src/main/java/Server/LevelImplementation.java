package Server;

import framework.EventBus.Action;
import framework.Machines.*;
import framework.WorldElements.Cellar;
import framework.WorldElements.CellarMap;
import framework.Settings.Gravity;
import framework.primitives.Point;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LevelImplementation implements LevelInterface {
    public Level level;

    void runBackgroundServer(User user) {
        for (;;) {
            recalculateStep(user);
            try {
                Thread.sleep(500);
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
            entry.getValue().updateSpeed();
            entry.getValue().updatePosition();
            if (hitTheGround(entry.getValue().endPoint)){}

        }
    }

    private boolean hitTheGround(Point point){

        return true;
    }

    private void createBlow(Bullet bullet) {
        Blow blow = new Blow(bullet);
    }

    public LevelImplementation() {

    }


    public Tank getTankByUser(User user) {
        return level.tank1;
    }


    private void recalculateStep(User user) {
//        updateLevel(user);
        updateTank();
        updateTankBullet();
        updateBlow();
//        updateMap(user);
    }


    private void updateTank() {
        if
        (level.tank1.left.x > level.map.cellars.length) {
            level.tank1.left.x = level.tank1.left.x - level.map.cellars.length;
        }
    }

    private void updateBlow() {
        for (Map.Entry<Integer, Blow> entry : level.blows.entrySet()) {

            if (entry.getValue().stage > 5)
                level.blows.remove(entry.getKey());
            else entry.getValue().stage++;
        }
    }

    public User createUser() {
        User user = new User();
        return user;
    }

    public Level createLevel(Long maxX, Long maxY, int maxStep, User user) {
        this.level = new Level(user);
        level.bullets = new HashMap<>();
        level.blows = new HashMap<>();

        CellarMap cellarMap = new CellarMap(maxX, maxY);
        Gravity gravity = new Gravity();
        Map<Long, Long> borderLine = new HashMap<Long, Long>();
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
                    borderLine.put(x + 1L, new Long(y));
                }
                if (y > randX) {
                    cellarMap.cellars[x][y] = new Cellar(1L);
                } else {
                    cellarMap.cellars[x][y] = new Cellar(0L);
                }

            }
        }
        level.borderLine = borderLine;
        level.map = cellarMap;
        level.gravity = gravity;
        level.tank1 = new Tank(new Point(200L, 300L), new Point(230L, 330L), new Cannon(), new TankModel(25), user);

        level.tank2 = new Tank(new Point(600L, 300L), new Point(630L, 330L), new Cannon(), new TankModel(25), user);

        startBacgroundServer(user);
        return level;
    }

    void startBacgroundServer(User user) {
        Runnable r = new Runnable() {
            public void run() {
                runBackgroundServer(user);
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

//    public GameObject getGameObjectById(Integer objectId) {
//        for (Map.Entry<Integer, GameObject> entry : level.gameObjects.entrySet()) {
//            if (entry.getKey() == objectId)
//                return entry.getValue();
//        }
//        return null;
//    }

//    public void performClientAction(User user, Action action) {
//        GameObject object = getObjectByUserId(level, user);
//        GameObject updatedObject = action.execute(object);
//        level.gameObjects.put(updatedObject.id, updatedObject);
//    }

//    private GameObject getObjectByUserId(Level level, User user) {
//        for (Map.Entry<Integer, GameObject> entry : level.gameObjects.entrySet()) {
//            if (entry.getValue().ownerId == user.id)
//                return entry.getValue();
//        }
//        return null;
//    }

    public void moveTank(User user) {
        level.tank1.acceleration += 5;
        level.tank1.updateTankSpeed();

    }
}
