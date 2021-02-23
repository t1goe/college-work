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

    public SoundManager() {

        //bump.aiff from https://freesound.org/people/timgormly/sounds/170141/

        String[] fileLocations = {
                "res/sounds/bump.aiff"
        };

        for(String s : fileLocations){
            loadFile(s);
        }

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
        loadFile(s);
        System.out.println(s);
        SoundThread st = new SoundThread(mySounds.get(s));
        st.start();
    }


}