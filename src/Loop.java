
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Loop extends Shapes {


    public Loop(int X1, int X2) {
        x1 = X1;
        x2 = X2;
    }

    @Override
    public Shape getShape() {

        return new Ellipse2D.Double(x1-23, 250, 50, 50);
    }
}
