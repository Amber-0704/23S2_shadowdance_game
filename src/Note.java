import bagel.Image;
import bagel.Input;
import bagel.Keys;

/** The class of abstract Note
 */
public abstract class Note extends Entity{
    private final int appearanceFrame;
    private final int SPEED = 2;
    public static int speedChange = 0;
    private boolean active = false;
    private boolean completed = false;

    /** the constructor of Note
     * @param appearanceFrame The number of frame that Note Appear
     * @param image The image of note
     * @param x The x Axis of Note
     * @param y The x Axis of Note
     */
    public Note( int appearanceFrame, Image image, double x,double y) {
        super(image, x, y);
        this.appearanceFrame = appearanceFrame;
    }

    /** The method use to get Note is active or not
     * @return true/false
     */
    public boolean isActive() {
        return active;
    }

    /** The method use to get Note is complete or not
     * @return true/false
     */
    public boolean isCompleted() {
        return completed;
    }

    /** The method make Note Not active
     */
    public void deactivate() {
        active = false;
        completed = true;
    }

    /** The method use to update Note
     */
    public void update() {
        if (active) {
            setYAxis(getYAxis() + SPEED + speedChange);
        }
        if (ShadowDance.getCurrFrame() >= appearanceFrame && !completed) {
            active = true;
        }
    }

    /** The method use to draw Note
     */
    public void draw() {
        if (active) {
            super.draw();
        }
    }

    /** Method use to check score
     * And if clicked, it disappears
     * @param input This is used to receive keystroke arguments
     * @param accuracy Accuracy of Normal Note
     * @param targetHeight Y Axis of target
     * @param relevantKey The key need to press
     * @param note The NormalNote need to check
     */
    public abstract int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey, Note note);

    /** The method use to change the Speed Change
     * @param speedChange the speed need to change
     */
    public static void setSpeedChange(int speedChange) {
        Note.speedChange = speedChange;
    }
}
