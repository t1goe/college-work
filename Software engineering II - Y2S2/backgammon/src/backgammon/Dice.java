package backgammon;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Dice extends JPanel {

	private static final long serialVersionUID = 1L;
	public JLabel diceFaces[];
	private int currentNum;
	private int diceX;
	private int diceY;
	final int DICESIZE = 70;
	JLabel diceImg;

	public Dice(int diceX, int diceY) throws IOException {
		diceFaces = new JLabel[6];
		setCurrentNum(1);
		setDiceX(diceX);
		setDiceY(diceY);

		try {
			BufferedImage diceImage = ImageIO.read(this.getClass().getResource("dice1.png"));
			this.diceFaces[0] = new JLabel(new ImageIcon(diceImage));

			diceImage = ImageIO.read(this.getClass().getResource("dice2.png"));
			this.diceFaces[1] = new JLabel(new ImageIcon(diceImage));

			diceImage = ImageIO.read(this.getClass().getResource("dice3.png"));
			this.diceFaces[2] = new JLabel(new ImageIcon(diceImage));

			diceImage = ImageIO.read(this.getClass().getResource("dice4.png"));
			this.diceFaces[3] = new JLabel(new ImageIcon(diceImage));

			diceImage = ImageIO.read(this.getClass().getResource("dice5.png"));
			this.diceFaces[4] = new JLabel(new ImageIcon(diceImage));

			diceImage = ImageIO.read(this.getClass().getResource("dice6.png"));
			this.diceFaces[5] = new JLabel(new ImageIcon(diceImage));
		} catch (FileNotFoundException e) {
			System.out.println("The images were not found.");
		}
	}

	public JLabel getDiceFace(int faceNumber) {
		return this.diceFaces[faceNumber - 1];
	}

	public void setCurrentNum(int number) {
		this.currentNum = number;
	}

	public int getNumber() {
		return this.currentNum;
	}

	public int getDiceX() {
		return this.diceX;
	}

	public int getDiceY() {
		return this.diceY;
	}

	public void setDiceX(int diceX) {
		this.diceX = diceX;
	}

	public void setDiceY(int diceY) {
		this.diceY = diceY;
	}

	public void initialiseDice(JFrame jFrame, int x, int y) throws InterruptedException {
		for (int i = 1; i <= 6; i++) {
			jFrame.add(getDiceFace(i));
			getDiceFace(i).setVisible(false);
		}
	}

	public void swapFace(int prev, int next) {
		getDiceFace(prev).setBounds(getDiceX(), getDiceY(), DICESIZE, DICESIZE);
		getDiceFace(prev).setVisible(false);
		getDiceFace(prev).repaint();
		getDiceFace(next).setBounds(getDiceX(), getDiceY(), DICESIZE, DICESIZE);
		getDiceFace(next).setVisible(true);
		getDiceFace(next).repaint();
	}

	public int rollDice() {
		Random r = new Random();
		int rolledNum = r.nextInt((6 - 1) + 1) + 1;

		swapFace(this.getNumber(), rolledNum);
		this.setCurrentNum(rolledNum);

		return rolledNum;
	}
}