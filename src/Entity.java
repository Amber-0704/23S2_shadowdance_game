import bagel.Image;
/**
 * The class of Entity
 */
abstract public class Entity {
    private Image image;
    private double xAxis;
    private double yAxis;

    /** The constructor of Entity
     * @param image Use to input Image
     * @param xAxis Use to input xAxis
     * @param yAxis Use to input yAxis
     */
    public Entity(Image image, double xAxis, double yAxis) {
        this.image = image;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    /** The method use to draw Image
     */
    public void draw() {
        image.draw(xAxis,yAxis);
    }

    /** The method use to get XAxis
     */
    public double getXAxis() {
        return xAxis;
    }

    /** The method use to get YAxis
     */
    public double getYAxis() {
        return yAxis;
    }

    /** The method use to set XAxis
     * @param xAxis Use to input xAxis
     */
    public void setXAxis(double xAxis) {
        this.xAxis = xAxis;
    }

    /** The method use to set Y Axis
     * @param yAxis Use to input yAxis
     */
    public void setYAxis(double yAxis) {
        this.yAxis = yAxis;
    }
}
