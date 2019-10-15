package backgammon;

import java.awt.event.ActionEvent;
import java.util.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class CommandPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final JTextField commandField;
	private final LinkedList<String> commandBuffer;

	CommandPanel() {
		commandField = new JTextField(92);
		commandBuffer = new LinkedList<>();
		class AddActionListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				synchronized (commandBuffer) {
					commandBuffer.add(commandField.getText());
					commandField.setText("");
					commandBuffer.notify();
				}
			}
		}
		ActionListener listener = new AddActionListener();
		commandField.addActionListener(listener);
		commandField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		add(commandField, BorderLayout.CENTER);
	}

	public String getCommand() {
		String command;
		synchronized (commandBuffer) {
			while (commandBuffer.isEmpty()) {
				try {
					commandBuffer.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			command = commandBuffer.pop();
		}
		return command;
	}
}