import bagel.*;
/**
 * Class for dealing with accuracy of pressing the notes
 */

public class Accuracy {
    // Score
    public static final int PERFECT_SCORE = 10;
    public static final int GOOD_SCORE = 5;
    public static final int BAD_SCORE = -1;
    public static final int MISS_SCORE = -5;
    public static final int NOT_SCORED = 0;
    public static final int SPECIAL_SCORE = 15;
    // pop score
    public static final String PERFECT = "PERFECT";
    public static final String GOOD = "GOOD";
    public static final String BAD = "BAD";
    public static final String MISS = "MISS";
    public static final String SPEED_UP ="Speed Up";
    public static final String DOUBLE_SCORE = "Double Score";
    public static final String SLOW_DOWN = "Slow Down";
    public static final String LANE_CLEAR = "Lane Clear";
    private static final Font ACCURACY_FONT = new Font(ShadowDance.FONT_FILE, 40);
    private static final int RENDER_FRAMES = 30;
    // Distance
    public static final int CLEAR_ALL = 1;
    private static final int PERFECT_RADIUS = 15;
    private static final int GOOD_RADIUS = 50;
    private static final int BAD_RADIUS = 100;
    private static final int MISS_RADIUS = 200;
    private static final int SPECIAL_RADIUS = 50;
    // Use to record
    private String currAccuracy = null;
    public static int scoreChange = 1;
    private int frameCount = 0;
    private int doubleFrameCount = 0;
    private static final int DOUBLE_FRAME = 480;

    /** This method use to record Accuracy,
     * and keep track of how many frames the score was pop
     * @param accuracy give Accuracy to method
     */
    public void setAccuracy(String accuracy) {
        currAccuracy = accuracy;
        frameCount = 0;
    }

    /** This method use to active Double Score
     */
    public void doubleActive(){
        doubleFrameCount = 0;
        scoreChange *= 2;
    }

    /** Check the distance between target and special note
     * And give a score
     * @param height Special Note's Y-axis
     * @param targetHeight The position of Target
     * @param triggered Check if it's touched
     * @param specialMessage Give the current Note a Message
     * @param note Give the current Note
     * @return Score the Note get
     */
    public int evaluateSpecialScore(double height, double targetHeight, boolean triggered, String specialMessage, Note note){
        double distance = Math.abs(height - targetHeight);
        // SpecialNote is touched
        if (triggered) {
            if(distance <= SPECIAL_RADIUS){
                setAccuracy(specialMessage);
                return SPECIAL_SCORE;
            }
        // The SpecialNote is touched, and beyond the screen
        } else if (height >= (Window.getHeight())) {
            note.deactivate();
        }
        return NOT_SCORED;
    }

    /** Check the distance between target and note or HoldNote
     * And give a score
     * @param height Note's Y-axis
     * @param targetHeight The position of Target
     * @param triggered Check if it's touched
     * @return  Score the Note get
     */
    public int evaluateNormalScore(double height, double targetHeight, boolean triggered) {
        double distance = Math.abs(height - targetHeight);
        if (triggered) {
            if (distance <= PERFECT_RADIUS) {
                setAccuracy(PERFECT);
                return PERFECT_SCORE * scoreChange;
            } else if (distance <= GOOD_RADIUS) {
                setAccuracy(GOOD);
                return GOOD_SCORE * scoreChange;
            } else if (distance <= BAD_RADIUS) {
                setAccuracy(BAD);
                return BAD_SCORE * scoreChange;
            } else if (distance <= MISS_RADIUS) {
                setAccuracy(MISS);
                return MISS_SCORE * scoreChange;
            }
        } else if (height >= (Window.getHeight())) {
            setAccuracy(MISS);
            return MISS_SCORE;
        }
        return NOT_SCORED;
    }

    /** This method is used to update score
     * And also update score
     */
    public void update() {
        // Record Double Score active time
        if(doubleFrameCount < DOUBLE_FRAME){
            doubleFrameCount ++;
            if(doubleFrameCount == DOUBLE_FRAME){
                scoreChange = 1;
            }
        }
        // Record pop score time
        frameCount++;
        if (currAccuracy != null && frameCount < RENDER_FRAMES) {
            ACCURACY_FONT.drawString(currAccuracy,
                    Window.getWidth() / 2 - ACCURACY_FONT.getWidth(currAccuracy) / 2,
                    Window.getHeight() / 2);
        }
    }
}
