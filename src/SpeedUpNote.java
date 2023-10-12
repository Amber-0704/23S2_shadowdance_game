import bagel.Image;
import bagel.Input;
import bagel.Keys;

public class SpeedUpNote extends Note{
    private final static int Y_START_AXIS = 100;
    public SpeedUpNote(String dir, int appearanceFrame, double x){
        super(appearanceFrame, new Image("res/noteSpeedUp.png"),x, Y_START_AXIS );

    }
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey,Note note) {
        int score = accuracy.evaluateSpecialScore(getYAxis(), targetHeight, input.wasPressed(relevantKey),
                Accuracy.SPEED_UP, note);
        if (score != Accuracy.NOT_SCORED) {
            //全部的note速度+1
            Note.speedChange = 1; // Note.speedChange += 1
            deactivate();
            return score;
        }
        return score;
    }
}
