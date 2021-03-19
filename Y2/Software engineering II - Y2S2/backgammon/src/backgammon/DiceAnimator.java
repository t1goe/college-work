package backgammon;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;

public class DiceAnimator{
	public static JLabel tempDice;

	public DiceAnimator() throws InterruptedException {
		// diceRollTest();
	}

	public static void rollDice(int i) throws InterruptedException {
		Dice tempDice = Board.diceArray[i];
		Random r = new Random();
		int rolledNum = r.nextInt((6 - 1) + 1) + 1;
		//TODO: Is there a reason that ((6 - 1) + 1) couldn't just be (6)?

		tempDice.swapFace(tempDice.getNumber(), rolledNum);
		tempDice.setCurrentNum(rolledNum);

	}

	public static void diceRollTest(int diceNum) throws InterruptedException {

		final int speed = 150;

		for (int i = 0; i < 10; i++) {
			TimeUnit.MILLISECONDS.sleep(speed);
			rollDice(diceNum);
		}

	}

}
