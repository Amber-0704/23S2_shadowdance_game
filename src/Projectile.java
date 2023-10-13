import bagel.DrawOptions;
import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.ArrayList;

/** The Class of Projectile
 */
public class Projectile {
    private final static int Y_AXIS = 600;
    private final static int X_AXIS = 800;
    private final static int SPEED = 6;
    private int x = X_AXIS;
    private int y = Y_AXIS;
    private double rotateAngle;
    private final Image PROJECTILE_IMAGE = new Image("res/arrow.png");
    private DrawOptions options = new DrawOptions();

    /** The constructor of Projectile
     * @param enemy The enemy which is closest to Guardian
     */
    public Projectile(Enemy enemy){
        double xDifferent = enemy.getXAxis()-X_AXIS;
        double yDifferent = enemy.getYAxis()-Y_AXIS;
        rotateAngle = Math.atan2(yDifferent, xDifferent);
        options.setRotation(rotateAngle);

    }

    /** The method use to update Projectile
     */
    public void update(){
        draw();
        x += SPEED * Math.cos(rotateAngle);
        y += SPEED * Math.sin(rotateAngle);
    }

    /** The method use to draw Projectile
     */
    public void draw() {
        PROJECTILE_IMAGE.draw(x, y, options);
    }

    /** The method use to get X
     * @return XAxis
     */
    public int getX() {
        return x;
    }

    /** The method use to get Y
     * @return YAxis
     */
    public int getY() {
        return y;
    }
}
