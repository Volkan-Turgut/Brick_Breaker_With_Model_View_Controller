package ui.buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import ui.GameView;

public class HelpButton extends JPanel {
	private JButton helpButton;
	private GameView gameView;
	
	public HelpButton() {
		helpButton = new JButton("Help");
		helpButton.setFocusable(false);
		this.setFocusable(false);
		add(helpButton);
		this.setBackground(new Color(31,61,90));
		
		//ButtonHandler handler = new ButtonHandler();
		//startButton.addActionListener(handler);
		
	}
	

	public JButton getHelpButton() {
		return helpButton;
	}

	class ButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			gameView = GameView.getInstance();
			gameView.switchHelpPanel();
			
		}
	}
	

}
