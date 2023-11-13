package ui.buttons;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import application.controllers.GameController;
import ui.GameView;

public class SaveDatabaseButton extends JPanel {
	private JButton saveDatabeButton;
	private GameView gameView;
	
	public SaveDatabaseButton() {
		saveDatabeButton = new JButton("Save");
		saveDatabeButton.setFocusable(false);
		this.setFocusable(false);
		add(saveDatabeButton);
		this.setBackground(new Color(31,61,90));
		
		//ButtonHandler handler = new ButtonHandler();
		//startButton.addActionListener(handler);
		
	}
	

	public JButton getSaveDatabeseButton() {
		return saveDatabeButton;
		
	}

	class ButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			gameView = GameView.getInstance();
			gameView.databaseSelected();
			
		}
	}
	

}
