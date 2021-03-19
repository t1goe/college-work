package backgammon;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Stack;

public class GUI extends JPanel {

	private static final long serialVersionUID = 1L;

	private static JFrame mainFrame;
	private static JPanel boardPanel;

	private static InfoPanel infoPanel;
	private static CommandPanel commandPanel;

	private static JLabel pic;
	private static BufferedImage image;

	static JTextArea textArea;

	public Disk disk;

	public GUI() throws IOException, InterruptedException {
		newFrame();
	}

	/**
	 * This method creates the default frame with the three panels: boardPanel,
	 * displayPanel and talkPanel
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void newFrame() throws IOException, InterruptedException {

		// Create a new frame
		mainFrame = new JFrame();
		mainFrame.setVisible(false);
		mainFrame.setTitle("Backgammon");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BorderLayout());

		boardPanel = new JPanel();
		infoPanel = new InfoPanel();
		commandPanel = new CommandPanel();

		boardPanel.setBackground(Color.BLACK);
		infoPanel.setBackground(Color.BLACK);
		commandPanel.setBackground(Color.BLACK);

		// Try to get the board image
		try {
			image = ImageIO.read(this.getClass().getResource("TESTBOARD.png"));
		} catch (FileNotFoundException e) {
			System.out.println("The image was not found.");
		}

		pic = new JLabel(new ImageIcon(image));
	}
	
	public static void redrawPip(Stack<Disk> inputStack) {
		if (!inputStack.isEmpty()) {
			Stack<Disk> tempStack = new Stack<Disk>();
			if (inputStack.size() >= 6) {// Compress pip
				while (!inputStack.isEmpty())
					tempStack.push(inputStack.pop());

				int xPos = tempStack.peek().getImage().getX();
				int baseYPos = tempStack.peek().getImage().getY();

				inputStack.push(tempStack.pop());

				int topYPos;
				if (baseYPos < tempStack.peek().getImage().getY()) {
					topYPos = baseYPos + 250;
				} else {
					topYPos = baseYPos - 250;
				}

				int increment = (topYPos - baseYPos) / (tempStack.size() + 1);
				baseYPos += increment;

				while (!tempStack.isEmpty()) {
					inputStack.push(tempStack.pop());
					inputStack.peek().getImage().setBounds(xPos, baseYPos, 100, 100);
					inputStack.peek().getImage().repaint();
					baseYPos += increment;
				}
			} else {// Draw pip standard method
				while (!inputStack.isEmpty())
					tempStack.push(inputStack.pop());

				int xPos = tempStack.peek().getImage().getX();
				int baseYPos = tempStack.peek().getImage().getY();

				inputStack.push(tempStack.pop());

				int increment = 50;

				if (!tempStack.isEmpty() && baseYPos > tempStack.peek().getImage().getY()) {
					increment *= -1;
				}

				baseYPos += increment;

				while (!tempStack.isEmpty()) {
					inputStack.push(tempStack.pop());
					inputStack.peek().getImage().setBounds(xPos, baseYPos, 100, 100);
					inputStack.peek().getImage().repaint();
					baseYPos += increment;
				}
			}
		} else {
			System.out.println("Attempting to redraw empty stack ~148");
		}
	}

	/**
	 * Adds all elements to the frame (includes: disks, disk movements and pips)
	 */
	public static void initialiseFrame() {

		boardPanel.add(pic);

		mainFrame.add(boardPanel, BorderLayout.NORTH);
		mainFrame.add(infoPanel, BorderLayout.CENTER);
		mainFrame.add(commandPanel, BorderLayout.SOUTH);

		mainFrame.pack();
	}

	public static String getCommand() {
		return commandPanel.getCommand();
	}

	public static void displayString(String string) {
		infoPanel.addText(string);
	}

	public static JFrame getFrame() {
		return mainFrame;
	}
}