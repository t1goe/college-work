package backgammon;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Score {

	public JLabel number;

	/*
	 * List of all numbers that the scores will consist of
	 */
	public Score(int score) throws IOException {
		if (score == 0)
			number = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource("num0.png"))));
		if (score == 1)
			number = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource("num1.png"))));
		if (score == 2)
			number = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource("num2.png"))));
		if (score == 3)
			number = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource("num3.png"))));
		if (score == 4)
			number = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource("num4.png"))));
		if (score == 5)
			number = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource("num5.png"))));
		if (score == 6)
			number = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource("num6.png"))));
		if (score == 7)
			number = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource("num7.png"))));
		if (score == 8)
			number = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource("num8.png"))));
		if (score == 9)
			number = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource("num9.png"))));
		if (score == 10)
			number = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource("num10.png"))));
		if (score == 11)
			number = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource("num11.png"))));
		if (score == 12)
			number = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource("num12.png"))));
		if (score == 13)
			number = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource("num13.png"))));
		if (score == 14)
			number = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource("num14.png"))));
		if (score == 15)
			number = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource("num15.png"))));
		if (score == 16)
			number = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource("num16.png"))));
	}

	public JLabel getNumber() {
		return this.number;
	}
}
