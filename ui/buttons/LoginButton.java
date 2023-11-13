package ui.buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import ui.GameView;

public class LoginButton extends JPanel {
	private JButton loginButton;
	private GameView gameView;
	
	public LoginButton() {
		System.out.println("sadad");
		loginButton = new JButton("Login");
		loginButton.setFocusable(false);
		this.setFocusable(false);
		add(loginButton);
		this.setBackground(new Color(31,61,90));
		
		//ButtonHandler handler = new ButtonHandler();
		//startButton.addActionListener(handler);
		
	}
	

	public JButton getLoginButton() {
		return loginButton;
	}

	class ButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("asd");
			gameView = GameView.getInstance();
			gameView.switchBuildPanel();
			
			
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
