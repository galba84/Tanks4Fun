package framework.Calculations;


import java.awt.*;

public class CurvesCreator {

    float getDistance(float p1, float p2) {
        if (p1 > p2) {
            return p1 - p2;
        }
        return p2 - p1;
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

    //x,y should be positive number. X0 - top, left cornet, Yo top, left corner
    public Point[] drawCurve(Point p1, Point p2) {
        float distanceX = getDistance(p2.x, p1.x);
        float distanceY = getDistance(p2.y, p1.y);

        int lineDimension;
        int numberOfSegments;
        Point[] result;

        numberOfSegments = (int) distanceX;
        lineDimension = (int) distanceY;

        if (numberOfSegments < 2 || lineDimension < 2) {
            return new Point[]{p1, p2};
        }

        float[] segments = splitLineIntoEqualSegments(lineDimension / 2, numberOfSegments / 2);
        float[] transformedSegmentsFirst = transformSegments(segments, lineDimension / 2);
        float[] transformedSegmentsSecond = mirrorArray(transformedSegmentsFirst);
        float[] united;

        if (numberOfSegments % 2 != 0) {
            float[] temp = new float[]{transformedSegmentsFirst[transformedSegmentsFirst.length - 1]};
            transformedSegmentsFirst = concatArrays(transformedSegmentsFirst, temp);
        }
        united = concatArrays(transformedSegmentsFirst, transformedSegmentsSecond);
        result = new Point[united.length];

        float add = 0;
        for (int i = 0; i < united.length; i++) {
            add += (united[i]);
            if (p2.y > p1.y) {
                result[i] = new Point(p1.x + i, p1.y + (int) Math.round(add));
            } else {
                result[i] = new Point(p1.x + i, p1.y - (int) Math.round(add));
            }
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
        float step = (float) lineDimension / segments.length / segments.length;
        segments[0] = 0;
        for (int i = 1; i < segments.length; i++) {
            segments[i - 1] += step;
            segments[i] = segments[i - 1] + step;
        }
        return segments;
    }

    public static float[] concatArrays(float[] a, float[] b) {
        float[] result = new float[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}
