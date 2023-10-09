import bagel.Image;
import bagel.Input;
import bagel.Window;

import java.util.ArrayList;

public class Guardian {
    private final static int Y_AXIS = 600;
    private final static int X_AXIS = 800;
    private final Image GUARDIAN_IMAGE = new Image("res/guardian.png");
    private final ArrayList<Projectile> projectiles = new ArrayList<>();

    private void update(Input input, ArrayList<Enemy> enemies){ // 带一个Enemy是因为要根据input
        draw();
        //画完了之后要检查input，如果input是left shift -> add 一个Projectile(enemies.get(0))，会朝着直线（enemy的位置）飞行，朝着目标飞，直到超出屏幕
        //就是按了一个LeftShift就会射箭
        /**if(input){

        }*/
        //用于处理Collision
        for(Projectile subprojectile: projectiles){
            for(Enemy subEnemy: enemies){
                //如果enemy和projectile collision了
                //{enemies.remove(subEnemy)} (不可以直接这样子写这一条，因为有可能同时触到两个enemy，导致两个Enemy一起消失)
            }
        }
    }
    public void draw(){
        //Image draw Guardian
        GUARDIAN_IMAGE.draw(X_AXIS, Y_AXIS);

        for(Projectile subProjectile: projectiles){
            subProjectile.draw();

        }
    }
}
