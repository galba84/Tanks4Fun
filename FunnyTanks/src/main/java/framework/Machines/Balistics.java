package framework.Machines;

import java.awt.*;

import static java.lang.Math.atan2;
import static java.lang.Math.max;

public interface Balistics extends Cloneable {
    static double getXSpeed(double angle, double velocity) {
        return Math.cos(Math.toRadians(angle)) * velocity;
    }

    static double getYSpeed(double angle, double velocity) {
        double vx = Math.cos(Math.toRadians(angle)) * velocity;
        double result = Math.sqrt(Math.pow(velocity, 2) - Math.pow(vx, 2));
        if (angle > 180 && angle < 360) {
            result = -result;
        }
        return result;
    }

    static Point rotateLineClockWise(Point center, Point edge, int angle) {
        double xRot = (int) center.x + Math.cos(Math.toRadians(angle)) * (edge.x - center.x) - Math.sin(Math.toRadians(angle)) * (edge.y - center.y);
        double yRot = (int) center.y + Math.sin(Math.toRadians(angle)) * (edge.x - center.x) + Math.cos(Math.toRadians(angle)) * (edge.y - center.y);
        return new Point((int) xRot, (int) yRot);
    }

    static double angleOf(Point p1, Point p2) {
        final double deltaY = (p1.y - p2.y);
        final double deltaX = (p2.x - p1.x);
        final double result = Math.toDegrees(atan2(deltaY, deltaX));
        double res = (result < 0) ? (360d + result) : result;
        return res;
    }

    static Point getPointFromAngleAndVelocity(Point startPoint, int velocity, int angle) {
        double   x_new = (startPoint.x) + velocity * Math.cos(Math.toRadians(angle));
        double   y_new = (startPoint.y) + velocity * Math.sin(Math.toRadians(angle));
        return new Point((int) Math.round(x_new), (int) Math.round(y_new));
    }

    static double calculateEnergy(double mass, double velocity){
        return (double) (mass*Math.pow(velocity , mass));
    }
}
