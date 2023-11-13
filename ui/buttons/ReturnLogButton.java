package ui.buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import ui.GameView;
import ui.panels.RegisterPanel;

public class ReturnLogButton extends JPanel {
	private JButton retLogButton;
	private GameView gameView;
	private RegisterPanel registerPanel = new RegisterPanel(gameView);
	
	public ReturnLogButton() {
		retLogButton = new JButton("Register Now");
		retLogButton.setFocusable(false);
		retLogButton.setPreferredSize(new Dimension(100, 100));
		retLogButton.setBackground(Color.white);
		retLogButton.setForeground(Color.blue);
		retLogButton.setFocusable(false);
		add(retLogButton);
		this.setBackground(new Color(31,61,90));
        this.setFont(new Font("Tahoma", Font.PLAIN, 13));

		
		//ButtonHandler handler = new ButtonHandler();
		//startButton.addActionListener(handler);
		
	}
	

	public JButton getReturnLogButton() {
		return retLogButton;
	}

	class ButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {

			gameView = GameView.getInstance();
			gameView.switchLoginPanel();
			
			
		}
	}

	

}
