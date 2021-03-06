//Thomas Igoe
//17372013

import util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.util.HashMap;


public class SoundManager {
    private HashMap<String, AudioInputStream> mySounds = new HashMap<>();

    private float masterVolume = 0;

    public SoundManager(float volume) {

        this.masterVolume = volume;

        //dash.aiff from https://freesound.org/people/timgormly/sounds/170141/
        //walk.aiff from https://freesound.org/people/timgormly/sounds/170148/
        //jump.aiff from https://freesound.org/people/timgormly/sounds/170163/
        //bumper.aiff from https://freesound.org/people/timgormly/sounds/170140/
        //flag.wav from https://freesound.org/people/LittleRobotSoundFactory/sounds/270342/
        //key_collect.wav from https://freesound.org/people/SomeGuy22/sounds/431329/
        //unlock.wav from https://freesound.org/people/Swedger/sounds/170636/
        //victory.wav from https://freesound.org/people/Mrthenoronha/sounds/518308/


        //This code is actually unnecessary, but its good to keep track of what sounds are available.
        String[] fileLocations = {
                "res/sounds/dash.aiff",
                "res/sounds/walk.aiff",
                "res/sounds/jump.aiff",
                "res/sounds/bumper.aiff",
                "res/sounds/flag.wav",
                "res/sounds/key_collect.wav",
                "res/sounds/unlock.wav"
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
        SoundThread st = new SoundThread(mySounds.get(s), volume + masterVolume);
        st.start();
    }


}