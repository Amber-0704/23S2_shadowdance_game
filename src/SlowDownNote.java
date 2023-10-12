import bagel.Image;
import bagel.Input;
import bagel.Keys;

/** The class of Slow Down Note
 */
public class SlowDownNote extends Note{
    private final static int Y_START_AXIS = 100;
    private final static  String SLOWDOWN_IMAGE= "res/noteSlowDown.png";

    /** The  constructor of SlowDown Note
     * @param appearanceFrame The number of frame that SlowDown Note Appear
     * @param x The X_Axis of SlowDown Note
     */
    public SlowDownNote(String dir, int appearanceFrame, double x){
        super(appearanceFrame, new Image(SLOWDOWN_IMAGE),x, Y_START_AXIS );
    }

    /** Method use to check score
     * And if clicked, it disappears
     * @param input This is used to receive keystroke arguments
     * @param accuracy Accuracy of Note
     * @param targetHeight Y Axis of target
     * @param relevantKey The key need to press
     * @param note The note need to check
     * @return the score of note in Accuracy
     */
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey, Note note) {
        int score = accuracy.evaluateSpecialScore(getYAxis(), targetHeight,
                input.wasPressed(relevantKey), Accuracy.SLOW_DOWN, note);
        if (score != Accuracy.NOT_SCORED) {
            // touched, then Change the Speed
            Note.speedChange = -1;
            deactivate();
            return score;
        }
        return score;
    }
}
