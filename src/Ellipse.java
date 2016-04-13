/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Shape;
import java.awt.geom.Arc2D;

/**
 *
 * @author Omar Elfarouk
 */
public class Ellipse extends Shapes {

//    int x1;
//    int x2;

    public Ellipse(int x1, int x2) {
        this.x1 = x1;
        this.x2 = x2;
    }

    @Override
    public Shape getShape() {
        int left1 = (int) Math.min(x1, x2);
        int h = (int) (Math.abs(x2 - x1) / 3.5);

//        return new Arc2D.Double(left1, (250 - h / 2)-2, Math.abs(point2.getX() - point1.getX()), h-2, 0, 180, 0);
        return new Arc2D.Double(left1, (250 - h / 2) - 2, Math.abs(x2 - x1), h - 2, 0, 180, Arc2D.OPEN);
    }
}
