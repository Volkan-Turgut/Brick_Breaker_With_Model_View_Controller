package ui.buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import ui.GameView;

public class RegisterButton extends JPanel {
	private JButton registerButton;
	private GameView gameView;
	
	public RegisterButton() {
		System.out.println("sadad");
		registerButton = new JButton("Login");
		registerButton.setFocusable(false);
		this.setFocusable(false);
		add(registerButton);
		this.setBackground(new Color(31,61,90));
		
		//ButtonHandler handler = new ButtonHandler();
		//startButton.addActionListener(handler);
		
	}
	

	public JButton getRegisterButton() {
		return registerButton;
	}

	class ButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			gameView = GameView.getInstance();
			gameView.switchRegisterPanel();
			
			
		}
	}

	/*public void addActionListener(ActionListener actionListener) {
		// TODO Auto-generated method stub
		@Override
		public void actionPerformed(ActionEvent event) {
			
			gameView = GameView.getInstance();
			gameView.switchBuildPanel();
			
			
		}
		
	}*/
	

}
