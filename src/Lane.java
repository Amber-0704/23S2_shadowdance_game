import bagel.*;

import java.util.ArrayList;

/**
 * Class for the lanes which notes fall down
 */
public class Lane extends Entity{
    private static final int HEIGHT = 384;
    private static final int TARGET_HEIGHT = 657;
    private static final int COLLISION_DISTANCE = 104;
    private static final String LEFT = "Left";
    private static final String RIGHT = "Right";
    private static final String UP = "Up";
    private static final String DOWN = "Down";
    private static final String SPECIAL = "Special";
    private final String type;
    private Keys relevantKey;
    private final ArrayList<Note> notes = new ArrayList<>();

    /** The constructor of Lane
     * @param dir Input the direction of lane
     * @param location The location of lane
     */
    public Lane(String dir, int location) {
        super(new Image("res/lane" + dir + ".png"), location, HEIGHT);
        this.type = dir;
        switch (dir) {
            case LEFT:
                relevantKey = Keys.LEFT;
                break;
            case RIGHT:
                relevantKey = Keys.RIGHT;
                break;
            case UP:
                relevantKey = Keys.UP;
                break;
            case DOWN:
                relevantKey = Keys.DOWN;
                break;
            case SPECIAL:
                relevantKey = Keys.SPACE;
                break;
        }
    }

    /** The method use to getType
     * @return Type
     */
    public String getType() {
        return type;
    }

    /** The method use to update Lane and note
     * @param input This is used to receive keystroke arguments
     * @param accuracy The Accuracy of note
     * @return Score
     */
    public int update(Input input, Accuracy accuracy) {
        draw();
        // Update note
        for(Note subNote: notes){
            subNote.update();
        }
        if(!notes.isEmpty()){
            int score = notes.get(0).checkScore(input, accuracy, TARGET_HEIGHT,relevantKey ,notes.get(0));
            if (notes.get(0).isCompleted()){
                notes.remove(notes.get(0));
            }
            // Handle the situation where the Note touches a Bomb Note
            if (score == Accuracy.CLEAR_ALL){
                for(Note subNote: notes){
                    if(subNote.isActive()){
                        subNote.deactivate();
                    }
                }
            }else{
                return score;
            }
        }
        return Accuracy.NOT_SCORED;
    }

    /** The method use to add note into List of Note
     * @param note The note need add to List of Note
     */
    public void addNote(Note note) {
        notes.add(note);
    }

    /** The method use to check that every note is finish
     * @return true/flase
     */
    public boolean isFinished() {
        return notes.isEmpty();
    }

    /** The method used to draw the lane and the notes
     */
    public void draw() {
        super.draw();
        for(Note subNote: notes){
            subNote.draw();
        }
    }

    /** The method use to check collision of note and enemy
     * @param enemy Input the enemy
     */
    public void checkCollision(Enemy enemy){
        for(Note subNote: notes){
            if(subNote.isActive()) {
                double xDiff = subNote.getXAxis() - enemy.getXAxis();
                double yDiff = subNote.getYAxis() - enemy.getYAxis();
                double distanceEnemyNote = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
                if(distanceEnemyNote <= COLLISION_DISTANCE && subNote instanceof NormalNote){
                    subNote.deactivate();
                }
            }
        }

    }

}
