
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class NodeShape extends Shapes {

    public NodeShape(int X1) {
        x1 = X1;
    }

    @Override
    public Shape getShape() {
        return new Ellipse2D.Double(x1 - 12, 250 - 12, 24, 24);
    }
}
