import bagel.*;

import java.util.ArrayList;

/**
 * Class for the lanes which notes fall down
 */
public class Lane {
    private static final int HEIGHT = 384;
    private static final int TARGET_HEIGHT = 657;
    private static final int COLLISION_DISTANCE = 104;
    private final String type;
    private final Image image;
    private Keys relevantKey;
    private final int location;
    private final ArrayList<Note> notes = new ArrayList<>();

    // 这里就是shadowDance里面存入lane的方向和位置
    public Lane(String dir, int location) {
        this.type = dir;
        this.location = location;
        image = new Image("res/lane" + dir + ".png");
        //在这里目前只存在4个正常的lane，后续会需要加入specialLane
        switch (dir) {
            case "Left":
                relevantKey = Keys.LEFT;
                break;
            case "Right":
                relevantKey = Keys.RIGHT;
                break;
            case "Up":
                relevantKey = Keys.UP;
                break;
            case "Down":
                relevantKey = Keys.DOWN;
                break;
            case "Special":
                relevantKey = Keys.SPACE;
                break;
        }
    }
    // 用于得到lane的type
    public String getType() {
        return type;
    }

    public int update(Input input, Accuracy accuracy) {
        draw();
        for(Note subNote: notes){
            subNote.update();
        }
        if(!notes.isEmpty()){
            int score = notes.get(0).checkScore(input, accuracy, TARGET_HEIGHT,relevantKey ,notes.get(0));
            if (notes.get(0).isCompleted()){
                notes.remove(notes.get(0));
            }
            // 处理bomb的情况
            if (score == Accuracy.CLEAR_ALL){ // 代表被hit中了
                /**消除abstractNote中active的note，做一个while loop把所有的active的note remove掉*/
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

    // 在这里HoldNote和NormalNote都属于Note，所以他们俩个共用一个method就可以了
    public void addNote(Note n) {
        notes.add(n);
    }

    public boolean isFinished() {
        return notes.isEmpty();
    }

    /**
     * draws the lane and the notes
     */
    public void draw() {
        image.draw(location, HEIGHT);
        for(Note subNote: notes){
            subNote.draw(location);
        }
    }
    /**加一个method 用于checkCollision，用于检查Enemy是否于Note产生碰撞 //loop每个Note，检查是否碰撞
     */
    public void checkCollision(Enemy enemy){
        for(Note subNote: notes){
            int xDiff = subNote.getX() - enemy.getXAxis();
            int yDiff = subNote.getY() - enemy.getYAxis();
            double distanceEnemyNote = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
            if(distanceEnemyNote < COLLISION_DISTANCE&& (subNote instanceof HoldNote || subNote instanceof NormalNote)){
                subNote.deactivate();
            }
        }

    }

}
