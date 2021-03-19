package backgammon;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class InfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static JTextArea textArea;

	public InfoPanel() {

		// Creates the textArea to view what was typed
		textArea = new JTextArea(8, 77);
		textArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textArea);
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();

		// Change font and size
		Font f = new Font(Font.SANS_SERIF, 18, 16);
		textArea.setFont(f);

		// Default Text
		textArea.setText("Welcome to Backgammon!\nTo begin the game type play");

		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		add(scrollPane, BorderLayout.CENTER);
	}

	public void addText(String text) {
		textArea.setText(textArea.getText() + "\n" + text);
	}
}