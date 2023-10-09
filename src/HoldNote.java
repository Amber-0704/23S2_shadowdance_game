import bagel.*;

/**
 * Class for hold notes
 */
public class HoldNote extends Note {

    private static final int HEIGHT_OFFSET = 82;
    private final static int Y_START_AXIS = 24;
    private boolean holdStarted = false;
    public HoldNote(String dir, int appearanceFrame) {
        super(appearanceFrame, new Image("res/HoldNote" + dir + ".png"), Y_START_AXIS );

    }


    public void startHold() {
        holdStarted = true;
    }


    /**
     * scored twice, once at the start of the hold and once at the end
     */
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
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

    /**
     * gets the location of the start of the note
     */
    private int getBottomHeight() {
        return getY() + HEIGHT_OFFSET;
    }

    /**
     * gets the location of the end of the note
     */
    private int getTopHeight() {
        return getY() - HEIGHT_OFFSET;
    }
}
