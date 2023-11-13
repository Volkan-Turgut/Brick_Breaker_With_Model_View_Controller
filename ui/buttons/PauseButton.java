package ui.buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import ui.GameView;

public class PauseButton extends JPanel {
	private final JButton pauseButton;
	private final JButton fullScreenButton;
	private final JRadioButton musicButton;
	private final JButton saveDButton;
	private JButton saveFButton;
	private final JButton loadDButton;
	private JButton loadFButton;
	private boolean pause = true;
	private boolean fullscreen = false;
	private boolean saveDBClicked = false;
	private boolean saveFClicked = false;
	private boolean loadFClicked = false;




	public PauseButton() {
		pauseButton = new JButton("Play");
		pauseButton.setFocusable(false);
		add(pauseButton);
		
		fullScreenButton = new JButton("FullScreen");
		fullScreenButton.setFocusable(false);
		add(fullScreenButton);
		
		musicButton = new JRadioButton("Music");
		musicButton.setFocusable(false);
		musicButton.setSelected(true);
		
		
		add(musicButton);

		saveDButton = new JButton("SaveDB");
		saveDButton.setFocusable(false);
		saveDButton.setSelected(true);
		add(saveDButton);
		
		saveFButton = new JButton("SaveFile");
		saveFButton.setFocusable(false);
		saveFButton.setSelected(true);
		add(saveFButton);
		
		
		loadDButton = new JButton("LoadDB");
		loadDButton.setFocusable(false);
		loadDButton.setSelected(true);
		add(loadDButton);
		
		loadFButton = new JButton("LoadFile");
		loadFButton.setFocusable(false);
		loadFButton.setSelected(true);
		add(loadFButton);
		
		this.setFocusable(false);
		//this.setBackground(new Color(31,61,90));
		this.setOpaque(false);
	

		
		// Handlers
		ButtonHandler handler = new ButtonHandler();
		pauseButton.addActionListener(handler);
		
		FullScreenButtonHandler fullScreenHandler = new FullScreenButtonHandler();
		fullScreenButton.addActionListener(fullScreenHandler);
		
		SaveDBHandler saveDBhandler = new SaveDBHandler();
		saveDButton.addActionListener(saveDBhandler);
		
		SaveFileHandler saveFileHandler = new SaveFileHandler();
		saveFButton.addActionListener(saveFileHandler);
		
		LoadFileHandler loadFileHandler = new LoadFileHandler();
		loadFButton.addActionListener(loadFileHandler);
	}
		
		class ButtonHandler implements ActionListener{
			
			@Override
			public void actionPerformed(ActionEvent event) {

				if(pause) {
					pause = false;
					pauseButton.setText("Pause");
					
					
				}
				else {
					pause = true;
					pauseButton.setText("Play");
					
				}
				
			}
		}
		
		class FullScreenButtonHandler implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fullscreen = !fullscreen;
				if (fullscreen) {
					fullScreenButton.setText("Window mode");
				}
				else {
					fullScreenButton.setText("Fullscreen");
				}
			}
			
		}
		
		class SaveDBHandler implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				saveDBClicked = true;
			
			}
			
		}
		
		class SaveFileHandler implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				saveFClicked = true;
			
			}
			
		}
		
		class LoadFileHandler implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				loadFClicked = true;
			
			}
			
		}
		
		
		
		public JRadioButton getMusicButton() {
			return this.musicButton;
		}
		
		public boolean isPause() {
			return pause;
		}
		
		public void setPause(boolean pause) {
			this.pause = pause;
		}

		public boolean isFullScreen() {
			return fullscreen;
		}

		public JButton getPauseButton() {
			return pauseButton;
		}
		public void setTextButton(String text) {
			this.pauseButton.setText(text);
		}

		public JButton getFullScreenButton() {
			return fullScreenButton;
		}
		
		public boolean isSaveDBClicked() {
			return saveDBClicked;
		}

		public JButton getSaveDButton() {
			return saveDButton;
		}

		public JButton getLoadDButton() {
			// TODO Auto-generated method stub
			return loadDButton;
		}

		public JButton getSaveFButton() {
			return saveFButton;
		}

		public void setSaveFButton(JButton saveFButton) {
			this.saveFButton = saveFButton;
		}

		public boolean isSaveFClicked() {
			return saveFClicked;
		}

		public void setSaveFClicked(boolean saveFClicked) {
			this.saveFClicked = saveFClicked;
		}

		public JButton getLoadFButton() {
			return loadFButton;
		}

		public void setLoadFButton(JButton loadFButton) {
			this.loadFButton = loadFButton;
		}

		public boolean isLoadFClicked() {
			return loadFClicked;
		}

		public void setLoadFClicked(boolean loadFClicked) {
			this.loadFClicked = loadFClicked;
		}
		
		
		

	

}
