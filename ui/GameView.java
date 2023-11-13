package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;

import ui.UIGameObject;
import ui.buttons.AsteroidButtons;
import ui.buttons.HelpButton;
import ui.buttons.LoadDatabaseButton;
import ui.buttons.LoginButton;
import ui.buttons.PauseButton;
import ui.buttons.RegisterButton;
import ui.buttons.ReturnLogButton;
import ui.buttons.SaveDatabaseButton;
import ui.buttons.StartButton;
import ui.panels.BuildPanel;
import ui.panels.LoginPanel;
import ui.panels.RegisterPanel;
import ui.panels.RunningPanel;

public class GameView extends JFrame {
	
	private boolean saveDB = false;
	private boolean loadDB = false;
	
	private SaveDatabaseButton saveDBButton;
	private LoadDatabaseButton loadDBButton;
	
	


	private final int DELAY = 17;
	private static int screenWidth = 1024;  //1024
	private static int screenHeight = 768;  //768
	private static GameView gameView;
	private int[] px;
	private int[] py;
	private JPanel contentPane = new JPanel(new BorderLayout());
	ArrayList<UIGameObject> gameObjects = new ArrayList<>();
	BuildPanel buildPanel;
	LoginPanel loginPanel;
	RegisterPanel registerPanel;
	RunningPanel runningPanel;
	StartButton startButton;
	JLabel background;
	private boolean fullScreen;
	
	private int mouseX;
	private int mouseY;



	HelpButton helpButton = new HelpButton();
	LoginButton loginButton = new LoginButton();
	RegisterButton registerButton = new RegisterButton();
	PauseButton pauseButton = new PauseButton();
	ReturnLogButton returnLogButton = new ReturnLogButton ();
	AsteroidButtons asteroidButtons;
	String currentPanel;
	
	
	
	public static GameView getInstance() {
		if (gameView == null) {
			gameView = new GameView();
		}
		return gameView;
	}
	
	private GameView() {
		this.setName("Alien Crusher");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		this.setFocusable(true);
		
		loginPanel = new LoginPanel(this);
		buildPanel = new BuildPanel(this);
		registerPanel = new RegisterPanel(this);
		
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("resources/startBackground.png"));
		image.setImage(image.getImage().getScaledInstance((int) (screenHeight/2160.0*4096.0), screenHeight, Image.SCALE_DEFAULT));
		background = new JLabel(image);
		background.setLayout(new BorderLayout());
		setContentPane(background);
		

		//buildPanel.setSize(1008, 675);
		
		//this.currentPanel = "buildPanel";
		this.currentPanel = "loginPanel";
		//buildPanel.setSize(SCREEN_WIDTH - 16, SCREEN_HEIGHT -  93);
		//buildPanel.setSize(2560, 1440);
		loginPanel.setSize(2560, 1440);
		contentPane.setOpaque(false);
		
		loginButton = new LoginButton();
		startButton = new StartButton();
		asteroidButtons = new AsteroidButtons();
		saveDBButton = new SaveDatabaseButton();
		loadDBButton = new LoadDatabaseButton();
		
		//loginPanel.add(returnLogButton);
		background.add(loginPanel, BorderLayout.CENTER);
		//background.add(buildPanel, BorderLayout.CENTER);
		//background.add(loginButton, BorderLayout.NORTH);
		//background.add(startButton, BorderLayout.SOUTH);
		//background.add(helpButton, BorderLayout.SOUTH);
		//helpButton.setPreferredSize(new Dimension(320,100));
		helpButton.setLocation(700,500);
		//background.add(startButton, BorderLayout.SOUTH);
		//background.add(contentPane,BorderLayout.NORTH);
		//contentPane.add(asteroidButtons,BorderLayout.EAST);
		//asteroidButtons.setPreferredSize(new Dimension(320,100));
		//asteroidButtons.setLocation(700,500);
		//setUndecorated(true);
		this.pack();
		this.setSize(screenWidth, screenHeight);	
	}
	
	public void switchRunningPanel() {
		this.remove(this.buildPanel);
		this.remove(this.startButton);
		this.remove(this.contentPane);
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("resources/backgroundImage.png"));
		image.setImage(image.getImage().getScaledInstance((int) (screenHeight/2160.0*4096.0), screenHeight, Image.SCALE_DEFAULT));
		background = new JLabel(image);
		background.setLayout(new BorderLayout());
		setContentPane(background);
		
		this.runningPanel = new RunningPanel();
		
		this.currentPanel = "runningPanel";
		
		//this.add(this.runningPanel, BorderLayout.CENTER);
		background.add(this.runningPanel, BorderLayout.CENTER);
		runningPanel.setSize(1008, 675);
		//this.add(this.pauseButton, BorderLayout.SOUTH);
		background.add(this.pauseButton, BorderLayout.SOUTH);
		this.runningPanel.setFocusable(true);
		this.runningPanel.requestFocus();
		this.pack();
		this.setSize(screenWidth, screenHeight);
	}
	
	public void switchBuildPanel() {
		System.out.println("asd");
		this.remove(this.loginPanel);
		this.remove(this.loginButton);
		this.remove(this.contentPane);
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("resources/backgroundImage.png"));
		image.setImage(image.getImage().getScaledInstance((int) (screenHeight/2160.0*4096.0), screenHeight, Image.SCALE_DEFAULT));
		background = new JLabel(image);
		background.setLayout(new BorderLayout());
		setContentPane(background);
		
		
		
		this.buildPanel = new BuildPanel(this);
		
		this.currentPanel = "buildPanel";
		
		//this.add(this.runningPanel, BorderLayout.CENTER);
		background.add(this.buildPanel, BorderLayout.CENTER);
		buildPanel.setSize(1008, 675);
		//this.add(this.pauseButton, BorderLayout.SOUTH);
		background.add(startButton, BorderLayout.SOUTH);
		background.add(contentPane,BorderLayout.NORTH);
		contentPane.add(asteroidButtons,BorderLayout.EAST);
		this.buildPanel.setFocusable(true);
		this.buildPanel.requestFocus();
		this.pack();
		this.setSize(screenWidth, screenHeight);
		buildPanel.repaint();
		//setUndecorated(true);
	}
	
	public void switchRegisterPanel() {
		// TODO Auto-generated method stub
		this.remove(this.loginPanel);
		this.remove(this.loginButton);
		this.remove(this.contentPane);
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("resources/startBackground.png"));
		image.setImage(image.getImage().getScaledInstance((int) (screenHeight/2160.0*4096.0), screenHeight, Image.SCALE_DEFAULT));
		background = new JLabel(image);
		background.setLayout(new BorderLayout());
		setContentPane(background);
		contentPane.setOpaque(false);
		this.registerPanel = new RegisterPanel(this);
		
		currentPanel = "registerPanel";
		
		
		background.add(this.registerPanel, BorderLayout.CENTER);
		registerPanel.setSize(1008, 675);
		//this.add(this.pauseButton, BorderLayout.SOUTH);
		//background.add(startButton, BorderLayout.SOUTH);
		background.add(contentPane,BorderLayout.NORTH);
		this.registerPanel.setFocusable(true);
		this.registerPanel.requestFocus();
		this.pack();
		this.setSize(screenWidth, screenHeight);
		
	}
	
	public void switchLoginPanel() {
		// TODO Auto-generated method stub
		this.remove(this.registerPanel);
		this.remove(this.registerButton);
		this.remove(this.contentPane);
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("resources/startBackground.png"));
		image.setImage(image.getImage().getScaledInstance((int) (screenHeight/2160.0*4096.0), screenHeight, Image.SCALE_DEFAULT));
		background = new JLabel(image);
		background.setLayout(new BorderLayout());
		setContentPane(background);
		
		this.loginPanel = new LoginPanel(this);
		
		currentPanel = "loginPanel";
		
		
		background.add(this.loginPanel, BorderLayout.CENTER);
		registerPanel.setSize(1008, 675);
		//this.add(this.pauseButton, BorderLayout.SOUTH);
		background.add(contentPane,BorderLayout.NORTH);
		this.loginPanel.setFocusable(true);
		this.loginPanel.requestFocus();
		this.pack();
		this.setSize(screenWidth, screenHeight);
		
	}

	public void switchHelpPanel() {
		// TODO Auto-generated method stub
		
	}
	
	
	

	
	public void addKeyListenerToPanel(KeyListener kl) {
		this.runningPanel.addKeyListener(kl);
	}
	
	public void addUIGameObjectToPanel(UIGameObject gameObject) {
		if (currentPanel.equals("buildPanel")) {
			buildPanel.addGameObject(gameObject);
		}
		else if (currentPanel.equals("runningPanel")) {
			runningPanel.addGameObject(gameObject);
		}
	}
	
	public void addMouseListenerToPanel(MouseListener mouseListener) {
		if (currentPanel.equals("buildPanel")) {
			buildPanel.addMouseListener(mouseListener);
		}
		else if (currentPanel.equals("runningPanel")) {
			runningPanel.addMouseListener(mouseListener);
		}
	}
	
	public void addMouseMotionListenerToPanel(MouseMotionListener mouseListener) {
		if (currentPanel.equals("buildPanel")) {
			buildPanel.addMouseMotionListener(mouseListener);
		}
		else if (currentPanel.equals("runningPanel")) {
			runningPanel.addMouseMotionListener(mouseListener);
		}
	}
	
	public void resizeBackground() {
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("resources/backgroundImage.png"));
		image.setImage(image.getImage().getScaledInstance((int) (screenHeight/2160.0*4096.0), screenHeight, Image.SCALE_DEFAULT));
		background.setIcon(image);
		
	}

	

	public HelpButton getHelpButton() {
		return helpButton;
	}

	public void setHelpButton(HelpButton helpButton) {
		this.helpButton = helpButton;
	}

	public JButton getLoginButton() {
		return loginButton.getLoginButton();
	}

	public void setLoginButton(LoginButton loginButton) {
		this.loginButton = loginButton;
	}
	
	public JButton getRegisterButton() {
		// TODO Auto-generated method stub
		return loginPanel.getBtnRegButton();
		//return registerButton.getRegisterButton();
	}
	public JLabel getLivesLabel() {
		return runningPanel.getLivesLabel();

	}
	public JLabel getScoreLabel() {
		return runningPanel.getScoreLabel();
	}
	
	public JLabel getInventoryLabel() {
		return runningPanel.getInventoryLabel();
	}
	
	public ArrayList<JLabel> getInventoryLabels() {
		ArrayList<JLabel> l = new ArrayList<>(); 
		l.add(runningPanel.getWrapLabel());
		l.add(runningPanel.getTallerPaddleLabel());
		l.add(runningPanel.getMagnetLabel());
		return l;
		
	}
	
	public JButton getStartButton() {
		return startButton.getStartButton();
	}
	
	public JButton getRetLoginButton() {
		// TODO Auto-generated method stub
		System.out.println("asdxzczc2");
		//return returnLogButton.getReturnLogButton();
		return registerPanel.getBtnReturnButton();
	}

	public static void setScreenWidth(int screenWidth) {
		GameView.screenWidth = screenWidth;
	}

	public static void setScreenHeight(int screenHeight) {
		GameView.screenHeight = screenHeight;
	}

	public HashMap<Integer, UIGameObject> getGameObjectsMap() {
		if (currentPanel.equals("buildPanel")) {
			return buildPanel.getGameObjectsMap();
		}
		else if (currentPanel.equals("runningPanel")) {
			return runningPanel.getGameObjectsMap();
		}
		return null;
	}
	
	
	public int getDelay() {
		return this.DELAY;
	}
	
	public JButton getRunningPanelFSButton() {
		return pauseButton.getFullScreenButton();
	}
	
	public JRadioButton getMusicButton() {
		return pauseButton.getMusicButton();
	}
	
	
	
	
	public JButton getSaveDBButton() {
		return pauseButton.getSaveDButton();
	}

	public JButton getLoadDBButton() {
		return pauseButton.getLoadDButton();
	}
	
	public JButton getSaveFileButton() {
		return pauseButton.getSaveFButton();
	}
	
	public JButton getLoadFileButton() {
		return pauseButton.getLoadFButton();
	}
	
	public void adjustDimension() {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		screenHeight = (int) size.getHeight();
		screenWidth = (int) size.getWidth();
		runningPanel.updatePanelSize();
	}
	

	public void clearGameObjects() {
		if (currentPanel.equals("buildPanel")) {
			buildPanel.clearGameObjects();
		}
		else if (currentPanel.equals("runningPanel")) {
			runningPanel.clearGameObjects();
		}
		
	}
	
	public void addGameLostText() {
		JLabel lostLabel = new JLabel("You Lost!");
		lostLabel.setBounds(screenWidth/2 - 50, screenHeight/2 - 20, 300, 60);
	//lostLabel.setBounds(10, 10, 100, 50);
		lostLabel.setForeground(Color.WHITE);
		runningPanel.add(lostLabel);
		
	}

	public boolean isFullScreen() {
		return pauseButton.isFullScreen();
	}
	public boolean isPause() {
		return pauseButton.isPause();
	}
	
	public void setPause(boolean pause) {
		pauseButton.setPause(pause);
	}

	public void repaintView() {
		
		if (currentPanel.equals("buildPanel")) {
			System.out.println("atrue");
			buildPanel.repaint();
		}
		else if (currentPanel.equals("runningPanel")) {
			runningPanel.repaint();
		}
		else {
			buildPanel.repaint();
		}
	}
	
	public static int getScreenWidth() {
		return screenWidth;
	}

	public static int getScreenHeight() {
		return screenHeight;
	}

	public int[] getPx() {
		return px;
	}

	public void setPx(int[] px) {
		this.px = px;
		this.runningPanel.setPx(px);
	}

	public int[] getPy() {
		return py;
	}

	public void setPy(int[] py) {
		this.py = py;
		this.runningPanel.setPy(py);
	}


	public AsteroidButtons getAsteroidButtons() {
		return asteroidButtons;
	}

	public String getCurrentPanel() {
		return currentPanel;
	}

	public int getMouseX() {
		return mouseX;
	}

	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}

	public void databaseSelected() {
		// TODO Auto-generated method stub
		this.saveDB = true;
	}
	
	public boolean isSaveDB() {
		return saveDB;
	}
	
	public boolean isLoadDB() {
		return loadDB;
	}











	


	
}
