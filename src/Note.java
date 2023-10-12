import bagel.Image;
import bagel.Input;
import bagel.Keys;

public abstract class Note extends Entity{
    private final int appearanceFrame;
    private final int SPEED = 2;
    public static int speedChange = 0;
    private boolean active = false;
    private boolean completed = false;


    public Note( int appearanceFrame, Image image, double x,double y) {
        super(image, x, y);
        this.appearanceFrame = appearanceFrame;
    }


    public boolean isActive() {
        return active;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void deactivate() {
        active = false;
        completed = true;
    }

    public void update() {
        if (active) {
            setYAxis(getYAxis() + SPEED + speedChange);
         //在这里面加一个speedChange,方便后面的special_note控制速度
        }

        if (ShadowDance.getCurrFrame() >= appearanceFrame && !completed) {
            active = true;
        }
    }

    public void draw() {
        if (active) {
            super.draw();
        }
    }

    //因为每个class的记分板都不一样，所以都需要重写，改成abstract就可以了
    public abstract int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKe, Note note);

    public static void setSpeedChange(int speedChange) {
        Note.speedChange = speedChange;
    }
}
