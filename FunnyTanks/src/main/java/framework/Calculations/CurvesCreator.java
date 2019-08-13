package framework.Calculations;


import java.awt.*;

public class CurvesCreator {

    float getDistance(float x1, float x2) {
        if (x1 > x2) {
            return x1 - x2;
        }
        return x2 - x1;
    }

    private Point getMidPoint(Point p1, Point p2) {
        float x;
        float y;
        if (p2.x > p1.x) {
            x = Math.abs(Math.round(getDistance(p2.x, p1.x)) / 2);
            x += p1.x;
        } else {
            x = Math.abs(Math.round(getDistance(p2.x, p1.x)) / 2);
            x += p2.x;
        }
        if (p2.y > p1.y) {
            y = Math.abs(Math.round(getDistance(p2.y, p1.y)) / 2);
            y += p1.y;
        } else {
            y = Math.abs(Math.round(getDistance(p2.y, p1.y)) / 2);
            y += p2.y;
        }

        Point result = new Point((int) Math.round(x), (int) Math.round(y));
        return result;
    }

    private float[] mirrorArray(float[] array) {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length / 2; i++) {
            result[i] = array[array.length - i - 1];
            result[array.length - 1 - i] = array[i];
        }
        return result;
    }

    public Point[] drawCurve(Point p1, Point p2) {
        Point p3 = getMidPoint(p1, p2);

        float distanceX = getDistance(p2.x, p1.x);
        float distanceY = getDistance(p2.y, p1.y);

        int lineDimension;
        int numberOfSegments;
        Point[] result;

        numberOfSegments = (int) distanceX;
        lineDimension = (int) distanceY;
        result = new Point[Math.abs(numberOfSegments)];


        float[] segments = splitLineIntoEqualSegments(Math.round(lineDimension / 2), Math.round(numberOfSegments / 2));
        float[] transformedSegmentsFirst = transformSegments(segments, Math.round(lineDimension / 2));
        float[] transformedSegmentsSecond = mirrorArray(transformedSegmentsFirst);


        float add = 0;
        for (int i = 0; i < numberOfSegments / 2; i++) {
            add += (transformedSegmentsFirst[i]);
            for (int ii = 0; ii < transformedSegmentsFirst[i]; ii++) {
                if (p2.y > p1.y) {
                    result[i] = new Point(p1.x + i, p1.y + (int) Math.round(add));
                } else {
                    result[i] = new Point(p1.x + i, p1.y - (int) Math.round(add));
                }
            }
        }
        add = 0;
        for (int i2 = 0; i2 < numberOfSegments / 2; i2++) {
            add += (transformedSegmentsSecond[i2]);
            for (int ii = 0; ii < transformedSegmentsSecond[i2]; ii++) {
                if (p2.y > p1.y) {
                    result[i2 + numberOfSegments / 2] = new Point(p3.x + i2, p3.y + (int) Math.round(add));
                } else {
                    result[i2 + numberOfSegments / 2] = new Point(p3.x + i2, p3.y - (int) Math.round(add));
                }
            }
        }
        // TODO: 12/08/2019
        if (numberOfSegments % 2 > 0) {
            result[(numberOfSegments / 2) + 1] = new Point(1, 1);
        }
        return result;
    }

    float[] splitLineIntoEqualSegments(float line, float numberOfSegments) {
        float[] segments = new float[(int) numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            segments[i] = line / numberOfSegments;
        }
        return segments;
    }


    float[] transformSegments(float[] segments, int lineDimension) {
        Float step = (float) lineDimension / segments.length / segments.length;
        segments[0] = 0;
        for (int i = 1; i < segments.length; i++) {
            segments[i - 1] += step;
            segments[i] = segments[i - 1] + step;
        }
        return segments;
    }
}
