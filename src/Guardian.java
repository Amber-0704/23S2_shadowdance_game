import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;

import java.util.ArrayList;
import java.util.List;

/**The class of Guardian
 */
public class Guardian extends Entity{

    private final static int Y_AXIS = 600;
    private final static int X_AXIS = 800;
    private final static int COLLISION_DISTANCE = 64;
    private final static String GUARDIAN_IMAGE = "res/guardian.png";
    private final ArrayList<Projectile> projectiles = new ArrayList<>();

    /** The Constructor of Guardian
     */
    public Guardian() {
        super(new Image(GUARDIAN_IMAGE), X_AXIS, Y_AXIS);
    }


    /** The method use to update Guardian and Projectile
     * Also check the collision of projectile and enemy
     * @param input This is used to receive keystroke arguments
     * @param enemies Input the list of Enemy
     */
    public void update(Input input, ArrayList<Enemy> enemies) {
        draw();
        // Generating projectile
        if (input.wasPressed(Keys.LEFT_SHIFT)) {
            if (! enemies.isEmpty()) {
                Projectile projectile = new Projectile(closestEnemy(enemies));
                projectiles.add(projectile);
            }
        }
        // Updates projectile and loops to check for collisions between enemy and projectile
        List<Projectile> projectilesToRemove = new ArrayList<>();
        List<Enemy> enemiesToRemove = new ArrayList<>();
        for (Projectile subProjectile : projectiles) {
            subProjectile.update();
            for (Enemy subEnemy : enemies) {
                if (calculateDistance(subProjectile, subEnemy) < COLLISION_DISTANCE) {
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


    /** The method use to calculate the Distance between Guardian and Enemy
     * @param enemy Input the Enemy
     * @return The distance
     */
    public double calculateDistance(Enemy enemy){
        double distance = Math.sqrt(Math.pow((X_AXIS - enemy.getXAxis()),2) + Math.pow((Y_AXIS - enemy.getYAxis()),2));
        return distance;
    }

    /** The method use to calculate the distance between projectile and enemy
     * @param projectile Input the projectile
     * @param enemy Input the Enemy
     * @return The distance
     */
    public double calculateDistance(Projectile projectile, Enemy enemy){
        double distance = Math.sqrt(Math.pow((projectile.getX() - enemy.getXAxis()), 2) +
                Math.pow((projectile.getY() - enemy.getYAxis()), 2));
        return distance;
    }

    /** The method used to find the closest Enemy to the Guardian
     * @param enemies The list of enemy
     * @return The closest Enemy to Guardian
     */
    public Enemy closestEnemy(ArrayList<Enemy> enemies) {
        double distance = Double.MAX_VALUE;
        Enemy closestEnemy = enemies.get(0);
        // Loop every enemy
        for (Enemy subEnemy : enemies) {
            if (calculateDistance(subEnemy) < distance) {
                distance = calculateDistance(subEnemy);
                closestEnemy = subEnemy;
            }
        }
        return closestEnemy;
    }

}