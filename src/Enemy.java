import bagel.Image;

import java.util.Random;

/** The class of Enemy
 */
public class Enemy extends Entity{
    // Position
    private final static int X_MAX_RANGE = 900;
    private final static int X_MIN_RANGE = 100;
    // Image
    private final static String ENEMY_IMAGE = "res/Enemy.png";
    // Speed
    private int speed = 1;

    /** Constructor use to initialize Enemy
     * @param xAxis Enemy's initial x position
     * @param yAxis Enemy's initial x position
     */
    public Enemy(int xAxis, int yAxis){
        super(new Image(ENEMY_IMAGE), xAxis, yAxis);
    }

    /** Method use to update enemy
     */
    public void update(){
        draw();
        setXAxis(getXAxis() + speed);
        // If enemy attack
        if(getXAxis() >= X_MAX_RANGE || getXAxis() <= X_MIN_RANGE){
            directionChange();
        }
    }

    /** Method use to change the direction of enemy
     */
    private void directionChange(){
        if(speed == 1){
            speed = -1;
        } else {
            speed = 1;
        }
    }

}
