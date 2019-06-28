package framework.primitives;

public class Point {
    public Long x;
    public Long y;

    public Point(Long x, Long y) {
        this.x = x;
        this.y = y;
    }




    @Override
    public boolean equals(Object obj) {
        Point p = (Point) obj;
        return (p.x == this.x && p.y == this.y);
    }
}
