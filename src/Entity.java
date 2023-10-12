import bagel.Image;

abstract public class Entity {
    private Image image;
    private double xAxis;
    private double yAxis;

    public Entity(Image image, double xAxis, double yAxis) {
        this.image = image;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public void draw() {
        image.draw(xAxis,yAxis);
    }

    public double getXAxis() {
        return xAxis;
    }

    public double getYAxis() {
        return yAxis;
    }

    public void setXAxis(double xAxis) {
        this.xAxis = xAxis;
    }

    public void setYAxis(double yAxis) {
        this.yAxis = yAxis;
    }
}
