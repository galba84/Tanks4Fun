package framework.Machines;

import java.awt.*;

import static java.lang.Math.atan2;

public interface Balistics {

    static double getXSpeed(double angle, double velocity) {
        return Math.cos(Math.toRadians(angle)) * velocity;
    }

    static double getYSpeed(double angle, double velocity) {
        double vx = Math.cos(Math.toRadians(angle)) * velocity;
        double result = Math.sqrt(Math.pow(velocity, 2) - Math.pow(vx, 2));
        if (angle > 180 && angle < 360) {
            result=-result;
        }
        return result;
    }

    static double angleOf(Point p1, Point p2) {
        final double deltaY = (p1.y - p2.y);
        final double deltaX = (p2.x - p1.x);
        final double result = Math.toDegrees(atan2(deltaY, deltaX));
        double res = (result < 0) ? (360d + result) : result;
        double ang = atan2(p1.x, p1.y);
        double angle = 180;
        double velocity = 11;

        return res;
    }
}
