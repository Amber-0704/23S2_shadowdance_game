import bagel.*;

/**
 * Class for hold notes
 */
public class HoldNote extends Note {

    private static final int HEIGHT_OFFSET = 82;
    private final static int Y_START_AXIS = 24;
    private boolean holdStarted = false;

    /** The constructor of HoldNote
     * @param dir The Direction of holdNote
     * @param appearanceFrame The Frame that HoldNote Appear
     * @param x The xAxis of HolNote
     */
    public HoldNote(String dir, int appearanceFrame, double x) {
        super(appearanceFrame, new Image("res/HoldNote" + dir + ".png"),x, Y_START_AXIS );
    }

    /** The method use to make Hold start
     */
    public void startHold() {
        holdStarted = true;
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
        if (isActive() && !holdStarted) {
            int score = accuracy.evaluateNormalScore(getBottomHeight(), targetHeight, input.wasPressed(relevantKey));
            if (score == Accuracy.MISS_SCORE) {
                deactivate();
                return score;
            } else if (score != Accuracy.NOT_SCORED) {
                startHold();
                return score;
            }
        } else if (isActive() && holdStarted) {
            int score = accuracy.evaluateNormalScore(getTopHeight(), targetHeight, input.wasReleased(relevantKey));
            if (score != Accuracy.NOT_SCORED) {
                deactivate();
                return score;
            } else if (input.wasReleased(relevantKey)) {
                deactivate();
                accuracy.setAccuracy(Accuracy.MISS);
                return Accuracy.MISS_SCORE;
            }
        }

        return 0;
    }

    /** The method use to get the Height of bottom of HoldNote
     * @return The Height of bottom of HoldNote
     */
    private double getBottomHeight() {
        return getYAxis() + HEIGHT_OFFSET;
    }

    /** The method use to get the Height of top of HoldNote
     * @return The Height of top of HoldNote
     */
    private double getTopHeight() {
        return getYAxis() - HEIGHT_OFFSET;
    }
}
