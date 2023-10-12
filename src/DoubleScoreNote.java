import bagel.Image;
import bagel.Input;
import bagel.Keys;

/** Class for Double Score Note
 */
public class DoubleScoreNote extends Note{
    private final static int Y_START_AXIS = 100;
    private final static String DOUBLE_IMAGE = "res/note2x.png";

    /** The method use to initial Double Score Note
     * @param dir Which lane Double Score Note Appear
     * @param appearanceFrame Which Frame Double Score Note Appear
     */
    public DoubleScoreNote(String dir, int appearanceFrame, double x){
        super(appearanceFrame, new Image(DOUBLE_IMAGE),x, Y_START_AXIS );
    }

    /** Method use to check score
     * And if clicked, it disappears，and make score be double
     * @param input This is used to receive keystroke arguments
     * @param accuracy Accuracy of Note
     * @param targetHeight Y Axis of target
     * @param relevantKey The key need to press
     * @param note The note need to check
     * @return the score of note in Accuracy
     */
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey,Note note) {
            int score = accuracy.evaluateSpecialScore(getYAxis(), targetHeight,
                    input.wasPressed(relevantKey), Accuracy.DOUBLE_SCORE, note);
            if (score != Accuracy.NOT_SCORED) {
                accuracy.doubleActive();
                deactivate();
                return score;
            }
            return 0;
    }
}
