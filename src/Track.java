import javax.sound.sampled.*;

/** The class use to control music
 */

public class Track extends Thread {
    private AudioInputStream stream;
    private Clip clip;

    /** The method use to read file
     * @param file The music need to play
     */
    public Track(String file) {
        try {
            stream = AudioSystem.getAudioInputStream(new java.io.File(file));
            clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class,stream.getFormat()));
            clip.open(stream);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /** The method use to stop music
     */
    public void pause() {
        try {
            clip.stop();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** The method use to play music
     */
    public void run(){
        try {
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}