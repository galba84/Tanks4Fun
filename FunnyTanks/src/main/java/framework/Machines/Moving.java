package framework.Machines;

import framework.WorldElements.CellarMap;

import java.awt.*;

public interface Moving {
    Point[] getTrace();

    void getNextPosition(CellarMap map);

    Point getCurrentPosition();

    boolean isXDirectionPositive();

    boolean isYDirectionPositive();

}
