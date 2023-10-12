import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;

import java.util.ArrayList;
import java.util.List;

public class Guardian extends Entity{
    //Guardian的位子固定
    private final static int Y_AXIS = 600;
    private final static int X_AXIS = 800;
    private final static Image GUARDIAN_IMAGE = new Image("res/guardian.png");
    private final ArrayList<Projectile> projectiles = new ArrayList<>();

    public Guardian() {
        super(GUARDIAN_IMAGE, X_AXIS, Y_AXIS);
    }

    public void update(Input input, ArrayList<Enemy> enemies) { // 带一个Enemy是因为要根据input看是否产生projectile，然后让projectile根据enemy发射
        draw();
        if (input.wasPressed(Keys.LEFT_SHIFT)) {
            if (! enemies.isEmpty()) {
                Projectile projectile = new Projectile(closestEnemy(enemies));//在这里出现了一个问题就是，这里需要加入的不是第一个出现的Enemy，而是最先出现的enemy
                projectiles.add(projectile);
            }
        }

        List<Projectile> projectilesToRemove = new ArrayList<>();
        List<Enemy> enemiesToRemove = new ArrayList<>();
        for (Projectile subProjectile : projectiles) {
            subProjectile.update();
            for (Enemy subEnemy : enemies) {
                if (calculateDistance(subProjectile, subEnemy) < 64) {
                    projectilesToRemove.add(subProjectile);
                    enemiesToRemove.add(subEnemy);
                }
            }
            if (subProjectile.getY() >= Window.getHeight()) {
                projectilesToRemove.add(subProjectile);
            }
        }
        projectiles.removeAll(projectilesToRemove);
        enemies.removeAll(enemiesToRemove);
    }

    public double calculateDistance(Enemy enemy){
        double distance = Math.sqrt(Math.pow((X_AXIS - enemy.getXAxis()),2) + Math.pow((Y_AXIS - enemy.getYAxis()),2));
        return distance;
    }

    public double calculateDistance(Projectile projectile, Enemy enemy){
            double distance = Math.sqrt(Math.pow((projectile.getX() - enemy.getXAxis()), 2) +
                    Math.pow((projectile.getY() - enemy.getYAxis()), 2));
            return distance;
        }

    public Enemy closestEnemy(ArrayList<Enemy> enemies) {
        double distance = Double.MAX_VALUE;
        Enemy closestEnemy = enemies.get(0);
        for (Enemy subEnemy : enemies) {
            if (calculateDistance(subEnemy) < distance) {
                distance = calculateDistance(subEnemy);
                closestEnemy = subEnemy;
            }
        }
        return closestEnemy;
    }

}