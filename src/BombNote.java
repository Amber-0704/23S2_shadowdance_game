import bagel.Image;
import bagel.Input;
import bagel.Keys;

public class BombNote extends Note{
    private final static int Y_START_AXIS = 100;
    public BombNote(String dir, int appearanceFrame){
        super(appearanceFrame, new Image("res/noteBomb.png"), Y_START_AXIS);

    }
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey, Note note) {
        int score = accuracy.evaluateSpecialScore(getY(), targetHeight, input.wasPressed(relevantKey),
                Accuracy.LANE_CLEAR, note);
        if (score == Accuracy.SPECIAL_SCORE) {
            // bomb消失
            deactivate();
            return Accuracy.CLEAR_ALL;
        }
        return Accuracy.NOT_SCORED;
    }

}
