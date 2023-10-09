import bagel.*;

/**
 * Class for normal notes
 */
public class NormalNote extends Note {
    private final static int Y_START_AXIS = 100;

    public NormalNote(String dir, int appearanceFrame) {
        // super用于在abstractNote的construction中需要重写的东西
        super(appearanceFrame, new Image("res/Note" + dir + ".png"), Y_START_AXIS );
    }

    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            int score = accuracy.evaluateNormalScore(getY(), targetHeight, input.wasPressed(relevantKey));
            if (score != Accuracy.NOT_SCORED) {
                deactivate();
                return score;
            }

        }

        return 0;
    }

}
