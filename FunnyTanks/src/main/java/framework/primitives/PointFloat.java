package framework.primitives;

public class PointFloat {
    public Integer x;
    public float fx;
    public float fy;

    public Integer y;

    public PointFloat(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public PointFloat(float x, float y) {
        this.fx = x;
        this.fy = y;
    }

}
