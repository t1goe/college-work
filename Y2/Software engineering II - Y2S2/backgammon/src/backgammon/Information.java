package backgammon;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import java.io.IOException;

import javax.imageio.ImageIO;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Information {
	private static JFrame mainFrame;

	private static BufferedImage image;

	public static JLabel bReturn;
	public static JLabel bReturnHover;

	static JLabel bRules;
	static BufferedImage img;

	int change = 0, Total = 0;

	public Information()
			throws InterruptedException, IOException, UnsupportedAudioFileException, LineUnavailableException {

		// Get background Image
		image = ImageIO.read(this.getClass().getResource("background3.png"));
		JLabel label = new JLabel(new ImageIcon(image));
		label.setPreferredSize(new Dimension(1100, 700));

		JPanel panel = new JPanel();
		// main window
		mainFrame = new JFrame("JPanel Example");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);

		image = ImageIO.read(this.getClass().getResource("return.png"));
		bReturn = new JLabel(new ImageIcon(image));

		image = ImageIO.read(this.getClass().getResource("returnHover.png"));
		bReturnHover = new JLabel(new ImageIcon(image));

		img = ImageIO.read(this.getClass().getResource("rules.png"));
		bRules = new JLabel(new ImageIcon(img));

		bReturn.setBounds(20, 630, 145, 50);
		bRules.setBounds(700, 520, 180, 180);

		bRules.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (change == 1) {
					bRules.setBounds(800, 30, 180, 180);
				} else if (change == 2) {
					bRules.setBounds(50, 30, 180, 180);
				} else if (change == 3) {
					bRules.setBounds(300, 500, 180, 180);
				} else {
					bRules.setBounds(700, 520, 180, 180);
					change = 0;
				}
				Total++;
			}

			public void mouseReleased(MouseEvent e) {
				change++;
			}
		});

		bReturn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				bReturn.setVisible(false);
				bReturnHover.setBounds(20, 630, 145, 50);
				bReturnHover.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				bReturnHover.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						mainFrame.setVisible(false);
						Menu.getFrame().setVisible(true);
					}

					public void mouseExited(MouseEvent e) {
						bReturn.setVisible(true);
					}
				});
			}
		});

		mainFrame.add(bReturn);
		mainFrame.add(bReturnHover);
		mainFrame.add(bRules);

		panel.add(label);
		mainFrame.add(panel);

		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	public static JFrame getFrame() {
		return mainFrame;
	}
}
