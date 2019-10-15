package backgammon;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Main {

	public static void main(String args[]) throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException{
		//new GameSettings();
		new Menu();
		new Game();
		System.exit(0);
	}
}

