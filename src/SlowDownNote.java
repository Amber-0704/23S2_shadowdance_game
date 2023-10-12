import bagel.Image;
import bagel.Input;
import bagel.Keys;

public class SlowDownNote extends Note{
    private final static int Y_START_AXIS = 100;
    public SlowDownNote(String dir, int appearanceFrame){
        super(appearanceFrame, new Image("res/noteSlowDown.png"), Y_START_AXIS );

    }
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey, Note note) {
        int score = accuracy.evaluateSpecialScore(getY(), targetHeight,
                input.wasPressed(relevantKey), Accuracy.SLOW_DOWN, note);
        if (score != Accuracy.NOT_SCORED) {
            // 全部的Note的速度-1
            Note.speedChange = -1; // Note.speedChange -= 1
            deactivate();
            return score;
        }
        return score;
    }
}
