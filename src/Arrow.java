
public class Arrow {

    private Shapes shape;
    private double gain;
    private boolean opposite;
    private boolean highLight;
    private String from;
    private String to;

    public Arrow(Shapes shape, double gain, String from, String to) {
        this.gain = gain;
        this.shape = shape;
        this.opposite = false;
        this.highLight = false;
        this.from = from;
        this.to = to;
    }

    public Shapes getShape() {
        return shape;
    }

    public void setShape(Shapes shape) {
        this.shape = shape;
    }

    public double getGain() {
        return gain;
    }

    public void setGain(int gain) {
        this.gain = gain;
    }

    public boolean isOpposite() {
        return opposite;
    }

    public void setOpposite(boolean opposite) {
        this.opposite = opposite;
    }

    public boolean isHighLight() {
        return highLight;
    }

    public void setHighLight(boolean highLight) {
        this.highLight = highLight;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

}
