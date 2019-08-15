package framework.WorldElements;

import framework.Machines.Blow;
import framework.Settings.Gravity;

import java.awt.*;

public class CellarMap {
    public volatile Cellar[][] cellars;
    public Gravity gravity = new Gravity();

    public CellarMap(Integer x, Integer y) {
        this.cellars = new Cellar[x][y];
    }

    public boolean pointHitTheGround(Point point) {
        if (point.x<0||point.y<0){
            return false;
        }
        if (cellars[point.x][point.y].type.equals(TypeOfSoil.Ground))
            return true;
        return false;
    }

    public void update() {
        for (int i = 0; i < cellars.length; i++) {
            for (int j = 0; j < cellars[0].length; j++) {
                if (cellars[i][j].type.equals(TypeOfSoil.Ground)) {
                    if ((j + 1 < cellars[0].length) && (cellars[i][j + 1].type.equals(TypeOfSoil.Air)))
                        moveGround(i, j, gravity.gravity);
                }
            }
        }
    }

    void moveGround(int x, int y, int steps) {
        for (int i = 0; i < steps; i++) {
            if ((y + i + 1 < cellars[0].length) && (cellars[x][y + 1].type.equals(TypeOfSoil.Air))) {
                cellars[x][y + i].type = TypeOfSoil.Air;
                cellars[x][y + i + 1].type = TypeOfSoil.Ground;
            }
        }

    }

    public boolean isPointAir(Point point) {
        if (point.x < cellars.length && point.y < cellars[0].length) {
            return (cellars[point.x][point.y].type.equals(TypeOfSoil.Air));
        }
        return false;
    }

    public boolean isPointGround(Point point) {
        if (point.x < cellars.length && point.y < cellars[0].length) {
            return (cellars[point.x][point.y].type.equals(TypeOfSoil.Ground));
        }
        return false;
    }

    public boolean isPointRightOnTheGround(Point point) {
        if (point.x < cellars.length && point.y-1 < cellars[0].length) {
            return (cellars[point.x][point.y-1].type.equals(TypeOfSoil.Ground));
        }
        return false;
    }
}
