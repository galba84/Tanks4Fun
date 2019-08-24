package framework.Machines;

import framework.WorldElements.CellarMap;

import java.awt.*;

public interface Moving {
    Point[] getTrace();

    Point getNextPosition(CellarMap map);

    Point getCurrentPosition();

    boolean isXDirectionPositive();

    boolean isYDirectionPositive();

}
