package backgammon;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameSettings {
	private static JFrame mainFrame;

	private static BufferedImage image;

	public static JLabel bReturn, bPlay, bOne, bThree, bFive, bEleven, bBlack, bWhite, bLightGrey, bDarkGrey,
			bReturnHover, bPlayHover, bOneHover, bThreeHover, bFiveHover, bElevenHover, bLightGreyP2, bDarkGreyP2,
			bBlackP2, bWhiteP2;

	public GameSettings() throws IOException {

		// Get background Image
		image = ImageIO.read(this.getClass().getResource("preGameBackground.png"));
		JLabel label = new JLabel(new ImageIcon(image));
		label.setPreferredSize(new Dimension(1100, 700));

		JPanel panel = new JPanel();
		// main window
		mainFrame = new JFrame("Backgammon");
		mainFrame.setVisible(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		image = ImageIO.read(this.getClass().getResource("logo.png"));
		mainFrame.setIconImage(image);
		mainFrame.setResizable(false);

		image = ImageIO.read(this.getClass().getResource("return.png"));
		bReturn = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("play.png"));
		bPlay = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("one.png"));
		bOne = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("three.png"));
		bThree = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("five.png"));
		bFive = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("eleven.png"));
		bEleven = new JLabel(new ImageIcon(image));

		image = ImageIO.read(this.getClass().getResource("returnHover.png"));
		bReturnHover = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("playHover.png"));
		bPlayHover = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("oneHover.png"));
		bOneHover = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("threeHover.png"));
		bThreeHover = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("fiveHover.png"));
		bFiveHover = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("elevenHover.png"));
		bElevenHover = new JLabel(new ImageIcon(image));

		image = ImageIO.read(this.getClass().getResource("black.png"));
		bBlack = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("white.png"));
		bWhite = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("black.png"));
		bBlackP2 = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("white.png"));
		bWhiteP2 = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("lightGrey.png"));
		bLightGrey = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("darkGrey.png"));
		bDarkGrey = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("lightGrey.png"));
		bLightGreyP2 = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("darkGrey.png"));
		bDarkGreyP2 = new JLabel(new ImageIcon(image));

		bReturn.setBounds(20, 630, 145, 50);
		bPlay.setBounds(500, 630, 145, 50);
		bOne.setBounds(100, 380, 70, 50);
		bThree.setBounds(200, 380, 70, 50);
		bFive.setBounds(300, 380, 70, 50);
		bEleven.setBounds(400, 380, 70, 50);

		bLightGrey.setBounds(110, 230, 60, 60);
		bDarkGrey.setBounds(200, 230, 60, 60);
		bLightGreyP2.setBounds(600, 230, 60, 60);
		bDarkGreyP2.setBounds(690, 230, 60, 60);

		bLightGrey.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				bLightGrey.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				bLightGrey.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						addTo(bLightGrey, bWhite, bDarkGrey, bBlack, 110, 230);
					}
				});

				bWhite.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						bWhite.setVisible(false);
						bLightGrey.setVisible(true);
					}
				});
			}
		});

		bDarkGrey.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				bDarkGrey.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				bDarkGrey.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						addTo(bDarkGrey, bBlack, bLightGrey, bWhite, 200, 230);
					}
				});

				bBlack.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						bBlack.setVisible(false);
						bDarkGrey.setVisible(true);
					}
				});
			}
		});

		bLightGreyP2.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				bLightGreyP2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				bLightGreyP2.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						addTo(bLightGreyP2, bWhiteP2, bDarkGreyP2, bBlackP2, 600, 230);
					}
				});

				bWhiteP2.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						bWhiteP2.setVisible(false);
						bLightGreyP2.setVisible(true);
					}
				});
			}
		});

		bDarkGreyP2.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				bDarkGreyP2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				bDarkGreyP2.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						addTo(bDarkGreyP2, bBlackP2, bLightGreyP2, bWhiteP2, 690, 230);
					}
				});

				bBlackP2.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						bBlackP2.setVisible(false);
						bDarkGreyP2.setVisible(true);
					}
				});
			}
		});

		// ***************************************************************************************************************
		// ***************************************************************************************************************

		bOne.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				bOne.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				bOne.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						addTo(bOne, bOneHover, bThree, bFive, bEleven, bThreeHover, bFiveHover, bElevenHover, 100, 380);
					}
				});

				bOneHover.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						bOne.setVisible(true);
					}
				});
			}
		});

		bThree.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				bThree.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				bThree.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						addTo(bThree, bThreeHover, bOne, bFive, bEleven, bOneHover, bFiveHover, bElevenHover, 200, 380);
					}
				});

				bThreeHover.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						bThree.setVisible(true);
					}
				});
			}
		});

		bFive.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				bFive.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				bFive.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						addTo(bFive, bFiveHover, bOne, bThree, bEleven, bOneHover, bThreeHover, bElevenHover, 300, 380);
					}
				});

				bFiveHover.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						bFive.setVisible(true);
					}
				});
			}
		});

		bEleven.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				bEleven.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				bEleven.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						addTo(bEleven, bElevenHover, bOne, bThree, bFive, bOneHover, bThreeHover, bFiveHover, 400, 380);
					}
				});

				bElevenHover.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						bEleven.setVisible(true);
					}
				});
			}
		});

		// ***************************************************************************************************************
		// ***************************************************************************************************************

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

		bPlay.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				bPlay.setVisible(false);
				bPlayHover.setBounds(500, 630, 145, 50);
				bPlayHover.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				bPlayHover.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						mainFrame.setVisible(false);
						GUI.getFrame().setVisible(true);
					}

					public void mouseExited(MouseEvent e) {
						bPlay.setVisible(true);
					}
				});
			}

		});

		mainFrame.add(bOne);
		mainFrame.add(bThree);
		mainFrame.add(bFive);
		mainFrame.add(bEleven);
		mainFrame.add(bPlay);
		mainFrame.add(bReturn);
		mainFrame.add(bPlayHover);
		mainFrame.add(bReturnHover);
		mainFrame.add(bOneHover);
		mainFrame.add(bThreeHover);
		mainFrame.add(bFiveHover);
		mainFrame.add(bElevenHover);
		mainFrame.add(bBlack);
		mainFrame.add(bWhite);
		mainFrame.add(bLightGrey);
		mainFrame.add(bDarkGrey);
		mainFrame.add(bLightGreyP2);
		mainFrame.add(bDarkGreyP2);
		mainFrame.add(bWhiteP2);
		mainFrame.add(bBlackP2);

		panel.add(label);
		mainFrame.add(panel);

		mainFrame.pack();
	}

	public static void addTo(JLabel button, JLabel buttonHover, JLabel one, JLabel two, JLabel three, JLabel oneHover,
			JLabel twoHover, JLabel threeHover, int x, int y) {
		button.setVisible(false);
		buttonHover.setBounds(x, y, 70, 50);
		buttonHover.setVisible(true);
		buttonHover.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		oneHover.setVisible(false);
		twoHover.setVisible(false);
		threeHover.setVisible(false);

		one.setVisible(true);
		two.setVisible(true);
		three.setVisible(true);
	}

	public static void addTo(JLabel button, JLabel buttonHover, JLabel one, JLabel oneHover, int x, int y) {
		button.setVisible(false);
		buttonHover.setBounds(x, y, 60, 60);
		buttonHover.setVisible(true);
		buttonHover.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		oneHover.setVisible(false);

		one.setVisible(true);
	}

	public static JFrame getFrame() {
		return mainFrame;
	}
}