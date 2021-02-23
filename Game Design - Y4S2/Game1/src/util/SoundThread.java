package util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundThread extends Thread {

    private AudioInputStream audioFile;
    private float volume;

    public SoundThread(AudioInputStream file, float volume) {
        this.audioFile = file;
        this.volume = volume;
    }

    public AudioInputStream getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(AudioInputStream audioFile) {
        this.audioFile = audioFile;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public void run(){
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(this.audioFile);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(this.volume);
            clip.start();

            do {
                Thread.sleep(15);
            } while (clip.isRunning());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
