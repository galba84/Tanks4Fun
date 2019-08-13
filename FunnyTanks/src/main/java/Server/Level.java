package Server;

import framework.Machines.Blow;
import framework.Machines.Bullet;
import framework.Machines.Tank;
import framework.WorldElements.CellarMap;
import framework.Settings.Gravity;

import java.io.Serializable;
import java.util.Map;
import java.util.Random;

public class Level implements Cloneable, Serializable {
    Integer id;
    public Map<Long, Long> borderLine;
    public CellarMap map;
    public Gravity gravity;
    public Map<Integer, Bullet> bullets;
    public Map<Integer, Blow> blows;
    public Tank tank1;
    public Tank tank2;

    public Level(User user) {
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
