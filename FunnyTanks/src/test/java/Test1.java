
import framework.Calculations.CurvesCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Test1 {

    @Test
    @DisplayName("First test 11")
    void justAnExample() {
        CurvesCreator curvesCreator = new CurvesCreator();
        Point[] pa = curvesCreator.drawCurve(new Point(1, 2), new Point(10, 22));
        assertEquals(9, pa.length);
    }
}