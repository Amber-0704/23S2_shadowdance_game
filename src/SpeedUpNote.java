import bagel.Image;
import bagel.Input;
import bagel.Keys;
/** The class of Slow Down Note
 */

public class SpeedUpNote extends Note{
    private final static int Y_START_AXIS = 100;
    private final static int SPEED_UP = 1;
    private final static  String SPEED_UP_IMAGE = "res/noteSpeedUp.png";

    /** The  constructor of Speed Up Note
     * @param appearanceFrame The number of frame that Speed Up Note Appear
     * @param x The X_Axis of Speed up Note
     */
    public SpeedUpNote(String dir, int appearanceFrame, double x){
        super(appearanceFrame, new Image(SPEED_UP_IMAGE),x, Y_START_AXIS );
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
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey,Note note) {
        int score = accuracy.evaluateSpecialScore(getYAxis(), targetHeight, input.wasPressed(relevantKey),
                Accuracy.SPEED_UP, note);
        if (score != Accuracy.NOT_SCORED) {
            Note.speedChange = SPEED_UP;
            deactivate();
            return score;
        }
        return score;
    }
}
