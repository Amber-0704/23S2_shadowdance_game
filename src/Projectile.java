import bagel.DrawOptions;
import bagel.Drawing;
import bagel.Image;

public class Projectile { //弓箭， 最好把Projectile放到Guardian里面
    private int xAxis;
    private int yAxis;
    private final Image PROJECTILE_IMAGE = new Image("res/arrow.png");
    private double rotateAngle; //用来记录projectile的旋转角度
    private DrawOptions options = new DrawOptions().setRotation(rotateAngle); //Bagel库里面的用法
    public Projectile(Enemy enemy){ // 这个里面给定的可以是一个Enemy。也可以是一个Enemy的坐标
        //rotateAngle是根据Enemy对于Guardian的方向改变的
        //正右方向是0，朝下是pi/2，朝左是pi，朝上是-pi/2
        double xDifferent = xAxis - enemy.getXAxis();
        double yDifferent = yAxis - enemy.getYAxis();
        double rotateAngle = Math.atan2(xDifferent, yDifferent);
        options.setRotation(rotateAngle);
        // 画出来之后在guardian的update里面去看collision
    }
    public void draw(){
        PROJECTILE_IMAGE.draw(xAxis, yAxis, options);//用来画projectile的图片，options是图片的朝向
    }

}
