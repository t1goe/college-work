import util.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class SoundManager {
    private Controller controller = Controller.getInstance();

    private HashMap<String, AudioInputStream> mySounds = new HashMap<>();

    private float masterVolume = 0;

    public SoundManager(float volume) {

        this.masterVolume = volume;

        //bump.aiff from https://freesound.org/people/timgormly/sounds/170141/
        //hurt.aiff from https://freesound.org/people/timgormly/sounds/170148/
        //jump.aiff from https://freesound.org/people/timgormly/sounds/170163/
        //bumper.aiff from https://freesound.org/people/timgormly/sounds/170140/

        String[] fileLocations = {
                "res/sounds/bump.aiff",
                "res/sounds/hurt.aiff",
                "res/sounds/jump.aiff",
                "res/sounds/bumper.aiff"
        };

        for (String s : fileLocations) {
            loadFile(s);
        }

    }

    public float getMasterVolume() {
        return masterVolume;
    }

    public void setMasterVolume(float masterVolume) {
        this.masterVolume = masterVolume;
    }

    private void loadFile(String s) {
        try {
            File sound = new File(s);
            mySounds.put(s, AudioSystem.getAudioInputStream(sound));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playFile(String s) {
        playFile(s, 0);
    }

    public void playFile(String s, float volume) {
        loadFile(s);
        System.out.println(s);
        SoundThread st = new SoundThread(mySounds.get(s), volume + masterVolume);
        st.start();
    }


}