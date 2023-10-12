import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Code for SWEN20003 Project 2, Semester 2, 2023
 * @author Muhan Chu
 */
public class  ShadowDance extends AbstractGame  {
    //Level
    private final static int LEVEL_ONE = 1;
    private final static int LEVEL_TWO = 2;
    private final static int LEVEL_THREE = 3;
    private int level= -1;
    private final static String CSV_FILE_ONE = "res/level1.csv";
    private final static String CSV_FILE_TWO = "res/level2.csv";
    private final static String CSV_FILE_THREE = "res/level3.csv";
    private Track track;
    private static final int CLEAR_SCORE_ONE = 150;
    private static final int CLEAR_SCORE_TWO= 450;
    private static final int CLEAR_SCORE_THREE= 300;
    private final static String TRACK_ONE = "res/track1.wav";
    private final static String TRACK_TWO = "res/track2.wav";
    private final static String TRACK_THREE = "res/track3.wav";

    //Window
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");

    // Message
    private final static int TITLE_X = 220;
    private final static int TITLE_Y = 250;
    private final static int INS_X_OFFSET = 100;
    private final static int INS_Y_OFFSET = 190;
    private final static int SCORE_LOCATION = 35;
    private final static int WIN_Y_AXIS = 300;
    private final static int SELECTION_Y_AXIS = 500;
    public final static String FONT_FILE = "res/FSO8BITR.TTF";
    private final Font TITLE_FONT = new Font(FONT_FILE, 64);
    private final Font INSTRUCTION_FONT = new Font(FONT_FILE, 24);
    private final Font SCORE_FONT = new Font(FONT_FILE, 30);
    private static final String INSTRUCTIONS = "SELECT LEVELS WITH\n     NUMBER KEYS\n\n         1  2  3";
    private int clearScore;
    private static final String CLEAR_MESSAGE = "CLEAR!";
    private static final String TRY_AGAIN_MESSAGE = "TRY AGAIN";
    private static final String SELECTION_MESSAGE = "PRESS SPACE TO RETURN TO LEVEL SELECTION";

    // Note Type
    private final static String NORMAL_NOTE = "Normal";
    private final static String HOLD_NOTE = "Hold";
    private final static String BOMB_NOTE = "Bomb";
    private final static String SPEED_UP_NOTE = "SpeedUp";
    private final static String SLOW_DOWN_NOTE = "SlowDown";
    private final static String DOUBLE_SCORE_NOTE= "DoubleScore";


    // Enemy
    private static final int ENEMY_MIN_X = 100;
    private static final int ENEMY_MAX_X = 900;
    private static final int ENEMY_MIN_Y = 100;
    private static final int ENEMY_MAX_Y = 500;
    private static final int ENEMY_CREATE_FRAME = 600;

    //Record Information
    private Accuracy accuracy;
    private ArrayList<Lane> lanes;
    private ArrayList<Enemy> enemies;
    private Guardian guardian;
    private final Random randomAppear = new Random();
    private int score;
    private static int currFrame;
    private boolean started = false;
    private boolean finished;
    private boolean paused;

    public ShadowDance(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /** This method is used to create Game called Shadow Dance
     */
    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();
        game.run();
    }

    /** This method is used to read CSV file
     * @param fileName This used to tell mehtod which file to read
     */
    private void readCsv(String fileName) {
        // Use to record Information
        accuracy = new Accuracy();
        lanes = new ArrayList<>();
        enemies = new ArrayList<>();
        guardian = new Guardian();
        score = 0;
        currFrame = 0;
        finished = false;
        paused = false;
        // Read file
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String textRead;
            while ((textRead = br.readLine()) != null) {
                String[] splitText = textRead.split(",");
                // Record Lane information
                if (splitText[0].equals("Lane")) {
                    String laneType = splitText[1];
                    int pos = Integer.parseInt(splitText[2]);
                    Lane lane = new Lane(laneType, pos);
                    lanes.add(lane);
                } else {  // Record Note information
                    String dir = splitText[0];
                    Lane lane = null;
                    for (Lane subLane:lanes){
                        if(subLane.getType().equals(dir)){
                            lane = subLane;
                            break;
                        }
                    }
                    // Add NormalNote and Hold to this lane
                    if (lane != null) {

                        double x = lane.getXAxis();
                        switch (splitText[1]) {
                            case NORMAL_NOTE:
                                NormalNote normalNote = new NormalNote(dir, Integer.parseInt(splitText[2]), x);
                                lane.addNote(normalNote);
                                break;
                            case HOLD_NOTE:
                                HoldNote holdNote = new HoldNote(dir, Integer.parseInt(splitText[2]), x);
                                lane.addNote(holdNote);
                                break;
                            case BOMB_NOTE:
                                BombNote bombNote = new BombNote(dir, Integer.parseInt(splitText[2]), x);
                                lane.addNote(bombNote);
                                break;
                            case DOUBLE_SCORE_NOTE:
                                DoubleScoreNote doubleScoreNote = new DoubleScoreNote(dir, Integer.parseInt(splitText[2]), x);
                                lane.addNote(doubleScoreNote);
                                break;
                            case SLOW_DOWN_NOTE:
                                SlowDownNote slowDownNote = new SlowDownNote(dir, Integer.parseInt(splitText[2]), x);
                                lane.addNote(slowDownNote);
                                break;
                            case SPEED_UP_NOTE:
                                SpeedUpNote speedUpNote = new SpeedUpNote(dir, Integer.parseInt(splitText[2]), x);
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

    /** This method is used to update Game
     * @param input This is used to receive keystroke arguments
     */
    protected void update(Input input) {
        // Close the game
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
        // The game page
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        // Game play
        // starting screen
        if (!started) {
            TITLE_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
            INSTRUCTION_FONT.drawString(INSTRUCTIONS,
                    TITLE_X + INS_X_OFFSET, TITLE_Y + INS_Y_OFFSET);
            // Read information from different level
            chooseGameLevel(input);
        // Game over, print Game over page
        } else if (finished) {
            if (score >= clearScore) {
                printEndPage(CLEAR_MESSAGE,input);
            } else {
                printEndPage(TRY_AGAIN_MESSAGE,input);
            }
        // While the game is in progress
        } else {
            SCORE_FONT.drawString("Score " + score, SCORE_LOCATION, SCORE_LOCATION);
            // Game pause
            if (paused) {
                if (input.wasPressed(Keys.TAB)) {
                    paused = false;
                    track.run();
                }
                for (Lane subLane: lanes){
                    subLane.draw();
                }
            // Update game
            } else {
                currFrame++;
                for(Lane subLane: lanes){
                    score += subLane.update(input, accuracy);
                }
                // Level3 Game Note
                if (level == LEVEL_THREE){
                    // Draw Guardian
                    guardian.draw();
                    // Create Enemy
                    createEnemy(currFrame);
                    // Update enemy, and check if the monster and note collide
                    for(Enemy subEnemy: enemies){
                        subEnemy.update();
                        for(Lane subLane:lanes){
                            subLane.checkCollision(subEnemy);
                        }
                    }
                    // Update Guardian
                    guardian.update(input, enemies);
                }
               // Update Score and pop score
                accuracy.update();
                finished = checkFinished();
                if (input.wasPressed(Keys.TAB)) {
                    paused = true;
                    track.pause();
                }
            }
        }
    }

    /** Gets the number of frames that are currently running
     * @return number of frame
     */
    public static int getCurrFrame() {
        return currFrame;
    }

    /** Method use to check Game is finish or not
     * @return return true means game finish
     * @return return false means game not over yet
     */
    private boolean checkFinished() {
        for(Lane subLane: lanes){
            if(!subLane.isFinished()){
                return false;
            }
        }
        return true;
    }

    /** Method use to create Enemy
     * @param currFrame The number of frames up to now
     */
    private void createEnemy(int currFrame){
        if(ShadowDance.currFrame % ENEMY_CREATE_FRAME == 1 && ShadowDance.currFrame > ENEMY_CREATE_FRAME){
            int enemyX = randomAppear.nextInt(ENEMY_MAX_X - ENEMY_MIN_X + 1) + ENEMY_MIN_X;
            int enemyY = randomAppear.nextInt(ENEMY_MAX_Y - ENEMY_MIN_Y + 1) + ENEMY_MIN_Y;
            Enemy enemy = new Enemy (enemyX, enemyY);
            enemies.add(enemy);
        }
    }

    /** Method use to Choose the level we want to play
     * @param input This is used to receive keystroke arguments
     */
    private void chooseGameLevel(Input input){
        if (input.wasPressed(Keys.NUM_1)) {
            readCsv(CSV_FILE_ONE);
            Note.setSpeedChange(0);
            level= LEVEL_ONE;
            started = true;
            track= new Track(TRACK_ONE);
            clearScore = CLEAR_SCORE_ONE;
            track.start();
            // Read level two information
        }else if(input.wasPressed(Keys.NUM_2)) {
            readCsv(CSV_FILE_TWO);
            Note.setSpeedChange(0);
            level= LEVEL_TWO;
            started = true;
            track= new Track(TRACK_TWO);
            clearScore = CLEAR_SCORE_TWO;
            track.start();
            // Read level three information
        }else if(input.wasPressed(Keys.NUM_3)) {
            readCsv(CSV_FILE_THREE);
            Note.setSpeedChange(0);
            level= LEVEL_THREE;
            started = true;
            track= new Track(TRACK_THREE);
            clearScore = CLEAR_SCORE_THREE;
            track.start();
        }
    }

    /** The method use to print end page
     * @param message the message need to print
     * @param input  This is used to receive keystroke arguments
     */
    private void printEndPage(String message,Input input){
        TITLE_FONT.drawString(message,
                WINDOW_WIDTH/2 - TITLE_FONT.getWidth(message)/2,
                WIN_Y_AXIS);
        INSTRUCTION_FONT.drawString(SELECTION_MESSAGE,
                WINDOW_WIDTH/2 - INSTRUCTION_FONT.getWidth(SELECTION_MESSAGE)/2,
                SELECTION_Y_AXIS);
        if(input.wasPressed(Keys.SPACE)) {
            started = false;
        }
    }



}
