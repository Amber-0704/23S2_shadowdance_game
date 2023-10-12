import bagel.Image;
import bagel.Input;
import bagel.Keys;

/** Class for BombNote
 */
public class BombNote extends Note{
    private final static int Y_START_AXIS = 100;

    /** The method use to initial BombNote
     * @param dir Which lane BombNote Appear
     * @param appearanceFrame Which Frame BombNote Appear
     * @param x The xAxis of Bomb Note
     */
    public BombNote(String dir, int appearanceFrame, double x){
        super(appearanceFrame, new Image("res/noteBomb.png"), x, Y_START_AXIS);

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
        int score = accuracy.evaluateSpecialScore(getYAxis(), targetHeight, input.wasPressed(relevantKey),
                Accuracy.LANE_CLEAR, note);
        if (score == Accuracy.SPECIAL_SCORE) {
            deactivate();
            return Accuracy.CLEAR_ALL;
        }
        return Accuracy.NOT_SCORED;
    }

}
