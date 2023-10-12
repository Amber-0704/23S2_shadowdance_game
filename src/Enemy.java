import bagel.Image;

import java.util.Random;

/** The class of Enemy
 */
public class Enemy extends Entity{
    // Position
    private final static int X_MAX_RANGE = 900;
    private final static int X_MIN_RANGE = 100;

    // Image
    private final static Image ENEMY_IMAGE = new Image("res/Enemy.png");
    // Speed
    private int speed = 1;

    /** Constructor use to initialize Enemy
     * @param xAxis Enemy's initial x position
     * @param yAxis Enemy's initial x position
     */
    public Enemy(int xAxis, int yAxis){
        super(ENEMY_IMAGE, xAxis, yAxis);
    }

    /** Method use to update enemy
     */
    public void update(){
        draw();
        setXAxis(getXAxis() + speed);
        // Enemy 碰到边缘的时候需要换方向
        if(getXAxis() >= X_MAX_RANGE || getXAxis() <= X_MIN_RANGE){
            directionChange();
        }
    }

    private void directionChange(){  //当x的超出范围时，改变速度，相当于就是改变方向
        if(speed == 1){
            speed = -1;
        } else {
            speed = 1;
        }
    }

}
