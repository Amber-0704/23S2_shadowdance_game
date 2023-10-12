import bagel.Image;

import java.util.Random;

public class Enemy {
    private final static int X_MAX_RANGE = 900;
    private final static int X_MIN_RANGE = 100;
    private final Image ENEMY_IMAGE = new Image("res/Enemy.png");
    private final Random randomDirection = new Random();
    private int speed = 1; // Enemy移动的速度
    private int xAxis;
    private int yAxis;

    // constructor
    public Enemy(int xAxis, int yAxis){
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }
    // 一个update的function
    public void update(){
        draw();
        xAxis += speed;
        // Enemy 碰到边缘的时候需要换方向
        if(xAxis >= X_MAX_RANGE || xAxis <= X_MIN_RANGE){
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

    public void draw(){
        ENEMY_IMAGE.draw(xAxis, yAxis);
    }

    public int getXAxis() {
        return xAxis;
    }

    public int getYAxis() {
        return yAxis;
    }
}
