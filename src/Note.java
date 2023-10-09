import bagel.Image;
import bagel.Input;
import bagel.Keys;

public abstract class Note {
    private final Image image;
    private final int appearanceFrame;
    private final int speed = 2;
    public static int speedChange = 0;
    private int y ;
    private boolean active = false;
    private boolean completed = false;


    public Note( int appearanceFrame, Image image, int y) {
        this.image = image;
        this.appearanceFrame = appearanceFrame;
        this.y =  y;
    }

    //添加了一个新的getY method，用于在继承他的class里面使用
    public int getY() {
        return y;
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
            y += speed + speedChange; //在这里面加一个speedChange,方便后面的special_note控制速度
        }

        if (ShadowDance.getCurrFrame() >= appearanceFrame && !completed) {
            active = true;
        }
    }

    public void draw(int x) {
        if (active) {
            image.draw(x, y);
        }
    }
    //因为每个class的记分板都不一样，所以都需要重写，改成abstract就可以了
    public abstract int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKe);
}
