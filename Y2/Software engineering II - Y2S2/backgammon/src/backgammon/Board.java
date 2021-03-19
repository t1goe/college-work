package backgammon;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Board {

	private final static int DISKSPACING = 50;

	public static Pip[] pipArray = new Pip[24];
	public static JLabel[] pipNumberArray = new JLabel[4];
	public static Dice[] diceArray = new Dice[4];

	public static BarAndBear blackBar;
	public static BarAndBear whiteBar;
	public static BarAndBear whiteBearZone;
	public static BarAndBear blackBearZone;

	public static JLabel diskImg;
	public static JLabel img;
	public static JLabel boardImage;

	public static Score[] totalScoreArray = new Score[16];
	public static Score[] playerOneScoreArray = new Score[16];
	public static Score[] playerTwoScoreArray = new Score[16];

	public Board() throws IOException, InterruptedException {
		initialisePips();
		initialiseDiskToPips();
		initialiseBarAndBear();
		initialiseBoard();
		initialisePipNumbers();
		initialiseDiceArray();
		initialiseScores();
		GUI.initialiseFrame();
	}

	public void initialisePipNumbers() throws IOException {

		BufferedImage q1 = ImageIO.read(this.getClass().getResource("q1.png"));
		BufferedImage q2 = ImageIO.read(this.getClass().getResource("q2.png"));
		BufferedImage q3 = ImageIO.read(this.getClass().getResource("q3.png"));
		BufferedImage q4 = ImageIO.read(this.getClass().getResource("q4.png"));

		// Quadrant 1
		img = new JLabel(new ImageIcon(q1));
		img.setBounds(466, 446, 500, 500);
		pipNumberArray[0] = img;

		// Quadrant 2
		img = new JLabel(new ImageIcon(q2));
		img.setBounds(-9, 446, 500, 500);
		pipNumberArray[1] = img;

		// Quadrant 3
		img = new JLabel(new ImageIcon(q3));
		img.setBounds(-7, -172, 500, 500);
		pipNumberArray[2] = img;

		// Quadrant 4
		img = new JLabel(new ImageIcon(q4));
		img.setBounds(468, -172, 500, 500);
		pipNumberArray[3] = img;

		for (int i = 0; i < 4; i++) {
			GUI.getFrame().add(pipNumberArray[i]);
		}
	}

	public void initialisePips() {
		int i, j = 0;

		for (i = 0; i < 6; i++)
			pipArray[i] = new Pip(849 - (72 * i), 600, EnumValues.quadrant.FIRST);
		for (i = 6; i < 12; i++)
			pipArray[i] = new Pip(805 - (72 * i), 600, EnumValues.quadrant.SECOND);

		while (j < 12) {
			for (i = 12; i < 18; i++) {
				pipArray[i] = new Pip(14 + (72 * j), 70, EnumValues.quadrant.THIRD);
				j++;
			}
			for (i = 18; i < 24; i++) {
				pipArray[i] = new Pip(58 + (72 * j), 70, EnumValues.quadrant.FOURTH);
				j++;
			}
		}
	}

	public void initialiseDiskToPips() throws IOException {
		int i;

		for (i = 0; i < 2; i++) {
			pipArray[0].diskStack.push(new Disk(EnumValues.diskColors.WHITE));
			pipArray[23].diskStack.push(new Disk(EnumValues.diskColors.BLACK));
		}
		for (i = 0; i < 5; i++) {
			pipArray[5].diskStack.push(new Disk(EnumValues.diskColors.BLACK));
			pipArray[18].diskStack.push(new Disk(EnumValues.diskColors.WHITE));
			pipArray[12].diskStack.push(new Disk(EnumValues.diskColors.BLACK));
			pipArray[11].diskStack.push(new Disk(EnumValues.diskColors.WHITE));
		}
		for (i = 0; i < 3; i++) {
			pipArray[7].diskStack.push(new Disk(EnumValues.diskColors.BLACK));
			pipArray[16].diskStack.push(new Disk(EnumValues.diskColors.WHITE));
		}
	}

	public void initialiseBarAndBear() {
		blackBar = new BarAndBear(430, 464, 264);
		whiteBar = new BarAndBear(430, 500, 500);
		whiteBearZone = new BarAndBear(935, 130, 300);
		blackBearZone = new BarAndBear(935, 430, 600);
	}

	/**
	 * Method to initialize all disks onto the board
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void initialiseBoard() throws IOException, InterruptedException {
		for (int i = 0; i < 24; i++) {
			int j = 0;

			Stack<Disk> tempStack = new Stack<Disk>(); // temp stack for holding disks of a pip

			while (!pipArray[i].diskStack.isEmpty()) {
				tempStack.push(pipArray[i].diskStack.pop()); // pop all disks off diskstack and push onto temp
			}

			while (!tempStack.isEmpty()) {
				Disk tempDisk = tempStack.pop();
				diskImg = tempDisk.getImage();

				int xPos = pipArray[i].getBaseXPos();
				int yPos = pipArray[i].getBaseYPos();

				// place on board according to quadrant
				if (pipArray[i].getQuadrant() == EnumValues.quadrant.FIRST
						|| pipArray[i].getQuadrant() == EnumValues.quadrant.SECOND) {
					yPos -= j * DISKSPACING;
				} else {
					yPos += j * DISKSPACING;
				}

				diskImg.setBounds(xPos, yPos, 100, 100);
				GUI.getFrame().add(diskImg);

				pipArray[i].diskStack.push(tempDisk); // push disk back onto original stack
				j++;
			}
		}
	}

	public void initialiseDiceArray() throws IOException, InterruptedException {
		int xPos = 770;
		int yPos = 350;

		for (int i = 0; i < 2; i++) {
			Dice newDice = new Dice(xPos + (i * 70), yPos);
			diceArray[i] = newDice;
			newDice.initialiseDice(GUI.getFrame(), newDice.getDiceX(), newDice.getDiceY());
		}
	}

	/*
	 * Sets the board to cheat mode
	 */
	public static void cheat() throws IOException, InterruptedException {
		Stack<Disk> blackStack = new Stack<Disk>();
		Stack<Disk> whiteStack = new Stack<Disk>();
		int i;

		// Empty the board before setting up cheat board
		for (i = 0; i < 24; i++) {
			if (pipArray[i].getColor() == EnumValues.diskColors.WHITE) {
				while (!pipArray[i].diskStack.isEmpty()) {
					whiteStack.push(pipArray[i].diskStack.pop());
				}
			} else if (pipArray[i].getColor() == EnumValues.diskColors.BLACK) {
				while (!pipArray[i].diskStack.isEmpty()) {
					blackStack.push(pipArray[i].diskStack.pop());
				}
			}
		}

		// Empty any disks that are on the bar
		while (!blackBar.diskStack.isEmpty()) {
			blackStack.push(blackBar.diskStack.pop());
		}
		while (!whiteBar.diskStack.isEmpty()) {
			whiteStack.push(whiteBar.diskStack.pop());
		}

		// Empty any disks that are currently on the bear off section
		while (!blackBearZone.diskStack.isEmpty()) {
			blackStack.push(blackBearZone.diskStack.pop());
		}
		while (!whiteBearZone.diskStack.isEmpty()) {
			whiteStack.push(whiteBearZone.diskStack.pop());
		}
		

		// Assign disks to cheat locations
		for (i = 0; i < 1; i++) {
			pipArray[0].diskStack.push(blackStack.pop());
			pipArray[23].diskStack.push(whiteStack.pop());
		}
		for (i = 0; i < 14; i++) {
			blackBearZone.diskStack.push(blackStack.pop());
			whiteBearZone.diskStack.push(whiteStack.pop());
		}

		// Redraw the board with new cheat board
		for (i = 0; i < 24; i++) {
			int j = 0;

			Stack<Disk> tempStack = new Stack<Disk>(); // temp stack for holding disks of a pip

			while (!pipArray[i].diskStack.isEmpty()) {
				tempStack.push(pipArray[i].diskStack.pop()); // pop all disks off diskstack and push onto temp
			}
			while (!tempStack.isEmpty()) {
				Disk tempDisk = tempStack.pop();
				diskImg = tempDisk.getImage();

				int xPos = pipArray[i].getBaseXPos();
				int yPos = pipArray[i].getBaseYPos();

				// place on board according to quadrant
				if (pipArray[i].getQuadrant() == EnumValues.quadrant.FIRST
						|| pipArray[i].getQuadrant() == EnumValues.quadrant.SECOND) {
					yPos -= j * DISKSPACING;
				} else {
					yPos += j * DISKSPACING;
				}

				diskImg.setBounds(xPos, yPos, 100, 100);
				diskImg.repaint();

				pipArray[i].diskStack.push(tempDisk); // push disk back onto original stack
				j++;
			}
		}

		Stack<Disk> tempStack = new Stack<Disk>();
		int xPos;
		int yPos;

		while (!whiteBearZone.diskStack.isEmpty()) {
			tempStack.push(whiteBearZone.diskStack.pop());
		}
		while (!tempStack.isEmpty()) {
			Disk tempDisk = tempStack.pop();
			diskImg = tempDisk.getImage();

			// place on board according to quadrant
			xPos = whiteBearZone.getPosX();
			yPos = whiteBearZone.getBottomPosY();
			yPos -= whiteBearZone.size() * 50;

			diskImg.setBounds(xPos, yPos, 100, 100);
			diskImg.repaint();

			whiteBearZone.diskStack.push(tempDisk); // push disk back onto original stack
		}

		while (!blackBearZone.diskStack.isEmpty()) {
			tempStack.push(blackBearZone.diskStack.pop());
		}
		while (!tempStack.isEmpty()) {
			Disk tempDisk = tempStack.pop();
			diskImg = tempDisk.getImage();

			// place on board according to quadrant
			xPos = blackBearZone.getPosX();
			yPos = blackBearZone.getBottomPosY();
			yPos -= blackBearZone.size() * 50;

			diskImg.setBounds(xPos, yPos, 100, 100);
			diskImg.repaint();

			blackBearZone.diskStack.push(tempDisk); // push disk back onto original stack
		}

		while (!blackBar.diskStack.isEmpty()) {
			tempStack.push(blackBar.diskStack.pop());
		}
		while (!tempStack.isEmpty()) {
			Disk tempDisk = tempStack.pop();
			diskImg = tempDisk.getImage();

			// place on board according to quadrant
			xPos = blackBar.getPosX();
			yPos = blackBar.getBottomPosY();
			yPos -= tempStack.size() * DISKSPACING;

			diskImg.setBounds(xPos, yPos, 100, 100);
			diskImg.repaint();

			blackBar.diskStack.push(tempDisk);
		}

		while (!whiteBar.diskStack.isEmpty()) {
			tempStack.push(whiteBar.diskStack.pop());
		}
		while (!tempStack.isEmpty()) {
			Disk tempDisk = tempStack.pop();
			diskImg = tempDisk.getImage();

			// place on board according to quadrant
			xPos = whiteBar.getPosX();
			yPos = whiteBar.getBottomPosY();
			yPos -= tempStack.size() * DISKSPACING;

			diskImg.setBounds(xPos, yPos, 100, 100);
			diskImg.repaint();

			whiteBar.diskStack.push(tempDisk);
		}

		Move.redrawPip(Board.whiteBearZone.diskStack);
		Move.redrawPip(Board.blackBearZone.diskStack);
		Move.redrawPip(Board.blackBar.diskStack);
		Move.redrawPip(Board.whiteBar.diskStack);
	}

	/*
	 * Initializes scores and sets visibility to false
	 */
	public void initialiseScores() throws IOException{

		for (int i = 0; i < 16; i++) {
			totalScoreArray[i] = new Score(i);
			playerOneScoreArray[i] = new Score(i);
			playerTwoScoreArray[i] = new Score(i);
			
			totalScoreArray[i].getNumber().setBounds(55, -215, 500, 500);
			playerOneScoreArray[i].getNumber().setBounds(415, -215, 500, 500);
			playerTwoScoreArray[i].getNumber().setBounds(655, -215, 500, 500);
					
			totalScoreArray[i].getNumber().setVisible(false);
			playerOneScoreArray[i].getNumber().setVisible(false);
			playerTwoScoreArray[i].getNumber().setVisible(false);
		}
		
		for (int i = 0; i < 16; i++) {
			GUI.getFrame().add(totalScoreArray[i].getNumber());
			GUI.getFrame().add(playerOneScoreArray[i].getNumber());
			GUI.getFrame().add(playerTwoScoreArray[i].getNumber());
		}
	}
}