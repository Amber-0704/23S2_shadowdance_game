import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Sample solution for SWEN20003 Project 1, Semester 2, 2023
 *
 * @author
 */

public class  ShadowDance extends AbstractGame  {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");
    private final Image GUARDIAN_IMAGE = new Image("res/guardian.png");
    private final static String CSV_FILE_ONE = "res/level1.csv";
    private final static String CSV_FILE_TWO = "res/test2.csv";
    private final static String CSV_FILE_THREE = "res/level3.csv";
    private Track track;
    public final static String FONT_FILE = "res/FSO8BITR.TTF";
    private final static int TITLE_X = 220;
    private final static int TITLE_Y = 250;
    private final static int INS_X_OFFSET = 100;
    private final static int INS_Y_OFFSET = 190;
    private final static int SCORE_LOCATION = 35;
    private final Font TITLE_FONT = new Font(FONT_FILE, 64);
    private final Font INSTRUCTION_FONT = new Font(FONT_FILE, 24);
    private final Font SCORE_FONT = new Font(FONT_FILE, 30);
    private static final String INSTRUCTIONS = "SELECT LEVELS WITH\nNUMBER KEYS\n\n  1  2  3"; // message的位置可以再改改
    private static final int CLEAR_SCORE_ONE = 150;
    private static final int CLEAR_SCORE_TWO= 450;
    private static final int CLEAR_SCORE_THREE= 300;
    private int clearScore;
    private static final String CLEAR_MESSAGE = "CLEAR!";
    private static final String TRY_AGAIN_MESSAGE = "TRY AGAIN\n PRESS SPACE TO RETURN TO LEVEL SELECTION";
    private static final int ENEMY_MIN_X = 100;
    private static final int ENEMY_MAX_X = 900;
    private static final int ENEMY_MIN_Y = 100;
    private static final int ENEMY_MAX_Y = 500;
    private static final int ENEMY_CREAT_FRAME = 600;
    private Accuracy accuracy;
    private ArrayList<Lane> lanes;
    private ArrayList<Enemy> enemies;
    private Guardian guardian;
    private final Random randomAppear = new Random(); // 这个class是用于帮助Enemies随机出现
    private int score;
    private static int currFrame;
    private boolean started = false;
    private boolean finished;
    private boolean paused;
    private int level= -1; // Use to record the level

    public ShadowDance(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }


    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();
        game.run();
    }

    private void readCsv(String fileName) {
        accuracy = new Accuracy();
        lanes = new ArrayList<>();
        enemies = new ArrayList<>();
        guardian = new Guardian();
        score = 0;
        currFrame = 0;
        finished = false;
        paused = false;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String textRead;
            while ((textRead = br.readLine()) != null) {
                String[] splitText = textRead.split(",");
                if (splitText[0].equals("Lane")) {
                    String laneType = splitText[1];
                    int pos = Integer.parseInt(splitText[2]);
                    Lane lane = new Lane(laneType, pos);
                    lanes.add(lane);
                } else {
                    //记录note
                    String dir = splitText[0];
                    Lane lane = null;
                    for (Lane subLane:lanes){
                        if(subLane.getType().equals(dir)){
                            lane = subLane;
                            break;
                        }
                    }

                    // 在这个lane里面添加Normal和Hold
                    if (lane != null) {
                        switch (splitText[1]) {
                            case "Normal":
                                NormalNote normalNote = new NormalNote(dir, Integer.parseInt(splitText[2]));
                                lane.addNote(normalNote);
                                break;
                            case "Hold":
                                HoldNote holdNote = new HoldNote(dir, Integer.parseInt(splitText[2]));
                                lane.addNote(holdNote);
                                break;
                            case "Bomb":
                                BombNote bombNote = new BombNote(dir, Integer.parseInt(splitText[2]));
                                lane.addNote(bombNote);
                                break;
                            case "DoubleScore":
                                DoubleScoreNote doubleScoreNote = new DoubleScoreNote(dir, Integer.parseInt(splitText[2]));
                                lane.addNote(doubleScoreNote);
                                break;
                            case"SlowDown":
                                SlowDownNote slowDownNote = new SlowDownNote(dir, Integer.parseInt(splitText[2]));
                                lane.addNote(slowDownNote);
                                break;
                            case"SpeedUp":
                                SpeedUpNote speedUpNote = new SpeedUpNote(dir, Integer.parseInt(splitText[2]));
                                lane.addNote(speedUpNote);
                                break;

                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }


    protected void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        if (!started) {
            // starting screen
            TITLE_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
            INSTRUCTION_FONT.drawString(INSTRUCTIONS,
                    TITLE_X + INS_X_OFFSET, TITLE_Y + INS_Y_OFFSET);
            if (input.wasPressed(Keys.NUM_1)) {
                readCsv(CSV_FILE_ONE);
                level= 1;
                started = true;
                track= new Track("res/track1.wav");
                clearScore = CLEAR_SCORE_ONE;
                // track.start();
            }else if(input.wasPressed(Keys.NUM_2)) {
                readCsv(CSV_FILE_TWO);
                level= 2;
                started = true;
                track= new Track("res/track2.wav");
                clearScore = CLEAR_SCORE_TWO;
                // track.start();
            }else if(input.wasPressed(Keys.NUM_3)) {
                readCsv(CSV_FILE_THREE);
                level= 3;
                started = true;
                track= new Track("res/track3.wav");
                clearScore = CLEAR_SCORE_THREE;
                // track.start();
            }
        } else if (finished) {
            // end screen
            if (score >= clearScore) {
                TITLE_FONT.drawString(CLEAR_MESSAGE,
                        WINDOW_WIDTH/2 - TITLE_FONT.getWidth(CLEAR_MESSAGE)/2,
                        WINDOW_HEIGHT/2);
                if(input.wasPressed(Keys.SPACE)){
                    started = false;
                }
            } else {
                TITLE_FONT.drawString(TRY_AGAIN_MESSAGE,
                        WINDOW_WIDTH / 2 - TITLE_FONT.getWidth(TRY_AGAIN_MESSAGE) / 2,
                        WINDOW_HEIGHT / 2);
                if(input.wasPressed(Keys.SPACE)){
                    started = false;
                }
            }
        } else {
            // gameplay

            SCORE_FONT.drawString("Score " + score, SCORE_LOCATION, SCORE_LOCATION);

            if (paused) {
                if (input.wasPressed(Keys.TAB)) {
                    paused = false;
                    track.run();
                }
                for (Lane subLane: lanes){
                    subLane.draw();
                }
            } else {
                currFrame++;
                for(Lane subLane: lanes){
                    score += subLane.update(input, accuracy);
                }
                if (level == 3){
                    // 在这里把Guardian画出来
                    guardian.draw();

                    // 处理Enemy
                    if(currFrame % ENEMY_CREAT_FRAME == 1 && currFrame > ENEMY_CREAT_FRAME){
                        int enemyX = randomAppear.nextInt(ENEMY_MIN_X + 1 ) + (ENEMY_MAX_X - ENEMY_MIN_X);
                        int enemyY = randomAppear.nextInt(ENEMY_MIN_Y + 1 ) + (ENEMY_MAX_Y - ENEMY_MIN_Y);
                        Enemy enemy = new Enemy (enemyX, enemyY);
                        enemies.add(enemy);
                    }
                    //循环每一个Enemy，检查每一个lane和enemy，在lane里面加一个method，用于检查enemy与Lane里面的note的碰撞
                    for(Enemy subEnemy: enemies){
                        subEnemy.update();
                        for(Lane subLane:lanes){
                            subLane.checkCollision(subEnemy);
                        }
                    }
                    // 处理Guardian
                    guardian.update(input, enemies);
                }


                // 更新分数，且pop Score
                accuracy.update();
                finished = checkFinished();
                if (input.wasPressed(Keys.TAB)) {
                    paused = true;
                    track.pause();
                }
            }
        }

    }

    public static int getCurrFrame() {
        return currFrame;
    }

    private boolean checkFinished() {
        for(Lane subLane: lanes){
            if(!subLane.isFinished()){
                return false;
            }
        }
        return true;
    }
}
