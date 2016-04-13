/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Shape;

/**
 *
 * @author Omar Elfarouk
 */
public class Shapes {

    private Shape shape;
    protected int x1;
    protected int x2;

    public Shapes(Shape shape) {
        this.shape = shape;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public Shapes() {
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Shape getShape() {
        return shape;
    }

}
