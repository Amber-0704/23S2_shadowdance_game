import bagel.DrawOptions;
import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.ArrayList;

public class Projectile { //弓箭， 最好把Projectile放到Guardian里面
    private final static int Y_AXIS = 600;
    private final static int X_AXIS = 800;
    private final static int SPEED = 6;
    private int x = X_AXIS;
    private int y = Y_AXIS;
    private double rotateAngle;
    private final Image PROJECTILE_IMAGE = new Image("res/arrow.png");
    private DrawOptions options = new DrawOptions(); //Bagel库里面的用法


    public Projectile(Enemy enemy){
        // 这个里面给定的可以是一个Enemy。也可以是一个Enemy的坐标
        double xDifferent = enemy.getXAxis()-X_AXIS;
        double yDifferent = enemy.getYAxis()-Y_AXIS;
        rotateAngle = Math.atan2(yDifferent, xDifferent);
        // 画出来之后在guardian的update里面去看collision
        options.setRotation(rotateAngle);

    }
    public void update(){
        draw();
        x += SPEED * Math.cos(rotateAngle);
        y += SPEED * Math.sin(rotateAngle);
    }

    public void draw(){
        PROJECTILE_IMAGE.draw(x, y, options);//用来画projectile的图片，options是图片的朝向
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
