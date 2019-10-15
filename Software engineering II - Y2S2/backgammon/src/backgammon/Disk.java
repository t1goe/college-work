package backgammon;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Disk {

	private EnumValues.diskColors diskColor;

	private JLabel diskImg;

	public Disk(EnumValues.diskColors diskColor) throws IOException {
		this.diskColor = diskColor;

		if (diskColor == EnumValues.diskColors.BLACK) {
			BufferedImage diskImage = ImageIO.read(this.getClass().getResource("blackdisk.png"));
			this.diskImg = new JLabel(new ImageIcon(diskImage)); 
		} else {
			BufferedImage diskImage = ImageIO.read(this.getClass().getResource("whitedisk.png"));
			this.diskImg = new JLabel(new ImageIcon(diskImage)); 	
		}
	}

	public Disk() {
		this.diskColor = EnumValues.diskColors.NONE;
	}

	public JLabel getImage() {
		return this.diskImg;
	}

	public EnumValues.diskColors getDiskColor() {
		return this.diskColor;
	}
}