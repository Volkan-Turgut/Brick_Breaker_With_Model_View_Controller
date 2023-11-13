package ui.buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import application.controllers.GameController;
import ui.GameView;

public class StartButton extends JPanel {
	private JButton startButton;
	private GameView gameView;
	
	public StartButton() {
		startButton = new JButton("Start");
		startButton.setFocusable(false);
		this.setFocusable(false);
		add(startButton);
		this.setBackground(new Color(31,61,90));
		
		//ButtonHandler handler = new ButtonHandler();
		//startButton.addActionListener(handler);
		
	}
	

	public JButton getStartButton() {
		return startButton;
	}

	class ButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("asd");
			gameView = GameView.getInstance();
			gameView.switchRunningPanel();
			
		}
	}
	

}
