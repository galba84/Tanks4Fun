package framework.WorldElements;

import framework.Machines.Blow;
import framework.primitives.Point;

public class CellarMap {
    public Cellar[][] cellars;
    public Blow blow;

    public CellarMap(Long x, Long y) {
        this.cellars = new Cellar[x.intValue()][y.intValue()];
    }

    public boolean pointHitTheGround(Point point) {
        if (cellars[point.x.intValue()][point.y.intValue()].type == TypeOfSoil.Ground)
            return true;
        return false;
    }
}
