package framework;

import framework.primitives.Line;

public class Geometry {

    public static double angleBetween2Lines(Line line1, Line line2) {
        double angle1 = Math.atan2(line1.startY - line1.endY,
                line1.startX - line1.endX);
        double angle2 = Math.atan2(line2.startY - line2.endY,
                line2.startX - line2.endX);
        return angle1 - angle2;
    }
}
