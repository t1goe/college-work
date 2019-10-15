package backgammon;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu {
	private static JFrame mainFrame;

	private static BufferedImage image;

	public static JLabel bPlay, bInfo, bQuit, bVolume, bVolumeMute;
	public static JLabel bPlayHover, bInfoHover, bQuitHover, bVolumeHover, bVolumeMuteHover;

	public Menu() throws InterruptedException, IOException, UnsupportedAudioFileException, LineUnavailableException {

		// Get background Image
		image = ImageIO.read(this.getClass().getResource("background1.png"));
		JLabel label = new JLabel(new ImageIcon(image));
		label.setPreferredSize(new Dimension(1100, 700));

		JPanel panel = new JPanel();
		// main window
		mainFrame = new JFrame("Backgammon");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		image = ImageIO.read(this.getClass().getResource("logo.png"));
		mainFrame.setIconImage(image);

		image = ImageIO.read(this.getClass().getResource("play.png"));
		bPlay = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("info.png"));
		bInfo = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("quit.png"));
		bQuit = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("volume.png"));
		bVolume = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("volumeOff.png"));
		bVolumeMute = new JLabel(new ImageIcon(image));

		image = ImageIO.read(this.getClass().getResource("playHover.png"));
		bPlayHover = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("infoHover.png"));
		bInfoHover = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("quitHover.png"));
		bQuitHover = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("volumeHover.png"));
		bVolumeHover = new JLabel(new ImageIcon(image));
		image = ImageIO.read(this.getClass().getResource("volumeOffHover.png"));
		bVolumeMuteHover = new JLabel(new ImageIcon(image));

		bPlay.setBounds(620, 550, 145, 50);
		bInfo.setBounds(780, 550, 145, 50);
		bQuit.setBounds(940, 550, 145, 50);

		/*
		
		// Play music
		String soundName = "music1.wav";
		AudioInputStream audioInputStream = AudioSystem
				.getAudioInputStream(Information.class.getResourceAsStream(soundName));
		Clip clip = AudioSystem.getClip();

		bVolumeMute.setBounds(20, 550, 70, 70);

		bVolumeMute.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				bVolumeMute.setVisible(false);
				bVolumeMuteHover.setBounds(20, 550, 70, 70);
				bVolumeMuteHover.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				bVolumeMuteHover.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {

						if (clip.isActive() == false) {
							try {
								clip.open(audioInputStream);
							} catch (LineUnavailableException | IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							clip.start();
							System.out.println("enters");
						} else {
							clip.stop();
						}
					}

					public void mouseExited(MouseEvent e) {
						bVolumeMute.setVisible(true);
					}
				});
			}
		});

*/

		bPlay.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				bPlay.setVisible(false);
				bPlayHover.setBounds(620, 550, 145, 50);
				bPlayHover.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				bPlayHover.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						mainFrame.setVisible(false);
						//GameSettings.getFrame().setVisible(true);
						GUI.getFrame().setVisible(true);
					}

					public void mouseExited(MouseEvent e) {
						bPlay.setVisible(true);
					}
				});
			}
		});

		bInfo.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				bInfo.setVisible(false);
				bInfoHover.setBounds(780, 550, 145, 50);
				bInfoHover.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				bInfoHover.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						mainFrame.setVisible(false);
						if (Information.getFrame() == null) {
							try {

								new Information();
							} catch (InterruptedException | IOException | UnsupportedAudioFileException
									| LineUnavailableException e1) {
								e1.printStackTrace();
							}
						} else {
							Information.getFrame().setVisible(true);
						}
					}

					public void mouseExited(MouseEvent e) {
						bInfo.setVisible(true);
					}
				});
			}
		});

		bQuit.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				bQuit.setVisible(false);
				bQuitHover.setBounds(940, 550, 145, 50);
				bQuitHover.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				bQuitHover.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						System.exit(0);
					}

					public void mouseExited(MouseEvent e) {
						bQuit.setVisible(true);
					}
				});
			}
		});

		mainFrame.add(bVolume);
		mainFrame.add(bVolumeMute);
		mainFrame.add(bPlay);
		mainFrame.add(bInfo);
		mainFrame.add(bQuit);
		mainFrame.add(bVolumeHover);
		mainFrame.add(bVolumeMuteHover);
		mainFrame.add(bPlayHover);
		mainFrame.add(bInfoHover);
		mainFrame.add(bQuitHover);

		panel.add(label);
		mainFrame.add(panel);

		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	public static JFrame getFrame() {
		return mainFrame;
	}
}
