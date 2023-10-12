import bagel.Image;
import bagel.Input;
import bagel.Keys;

public class DoubleScoreNote extends Note{
    private final static int Y_START_AXIS = 100;
    public DoubleScoreNote(String dir, int appearanceFrame){
        super(appearanceFrame, new Image("res/note2x.png"), Y_START_AXIS );

    }
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey,Note note) {
            int score = accuracy.evaluateSpecialScore(getY(), targetHeight,
                    input.wasPressed(relevantKey), Accuracy.DOUBLE_SCORE, note);
            if (score != Accuracy.NOT_SCORED) {
                accuracy.doubleActive();
                /**和slowDown一样的办法在Accuracy里面做手脚
                  满足条件就双倍
                  持续480帧（同时这个帧数在Accuracy里面也有记录，调整一下就ok）*/
                deactivate();
                return score;
            }
            return 0;
    }
}
