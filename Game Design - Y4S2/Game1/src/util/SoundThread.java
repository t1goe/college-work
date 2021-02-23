package util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundThread extends Thread {

    private AudioInputStream audioFile;

    public SoundThread(AudioInputStream file) {
        this.audioFile = file;
    }

    public AudioInputStream getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(AudioInputStream audioFile) {
        this.audioFile = audioFile;
    }

    public void run(){
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(this.audioFile);
            clip.start();

            do {
                Thread.sleep(15);
            } while (clip.isRunning());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
