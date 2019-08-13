package framework.primitives;

public class PointFloat {
    public Long x;
    public float fx;
    public float fy;

    public Long y;

    public PointFloat(Long x, Long y) {
        this.x = x;
        this.y = y;
    }

    public PointFloat(float x, float y) {
        this.fx = x;
        this.fy = y;
    }

}
