import bagel.*;

/**
 * Class for normal notes
 */
public class NormalNote extends Note {
    private final static int Y_START_AXIS = 100;

    /** The constructor of NormalNote
     * @param dir The direction of NormalNote
     * @param appearanceFrame The number of frame that Normal Note Appear
     * @param x The xAxis of Normal Note
     */
    public NormalNote(String dir, int appearanceFrame, double x) {
        super(appearanceFrame, new Image("res/Note" + dir + ".png"), x, Y_START_AXIS );
    }

    /** Method use to check score
     * And if clicked, it disappears
     * @param input This is used to receive keystroke arguments
     * @param accuracy Accuracy of Normal Note
     * @param targetHeight Y Axis of target
     * @param relevantKey The key need to press
     * @param note The NormalNote need to check
     * @return the score of note in Accuracy
     */
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey, Note note) {
        if (isActive()) {
            int score = accuracy.evaluateNormalScore(getYAxis(), targetHeight, input.wasPressed(relevantKey));
            if (score != Accuracy.NOT_SCORED) {
                deactivate();
                return score;
            }
        }
        return 0;
    }

}
