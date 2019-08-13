package framework.WorldElements;

import framework.Machines.Blow;
import framework.Settings.Gravity;

import java.awt.*;

public class CellarMap {
    public volatile Cellar[][] cellars;
    public Gravity gravity;

    public CellarMap(Integer x, Integer y) {
        this.cellars = new Cellar[x][y];
    }

    public boolean pointHitTheGround(Point point) {
        if (cellars[point.x][point.y].type.equals( TypeOfSoil.Ground))
            return true;
        return false;
    }
}
