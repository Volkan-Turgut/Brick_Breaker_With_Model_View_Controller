package ui.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import application.controllers.GameController;
import domain.Inventory;
import ui.GameView;
import ui.UIGameObject;
import ui.buttons.PauseButton;

public class RunningPanel extends JPanel {
	ArrayList<UIGameObject> gameObjects = new ArrayList<>();
	HashMap<Integer, UIGameObject> gameObjectsMap = new HashMap<>();
	private int[] px;
	private int[] py;
	private static int panelWidth = GameView.getScreenHeight() - 93;
	private static int panelHeight = GameView.getScreenWidth() - 16;
	private JLabel livesLabel;

	private JLabel magnetLabel;
	private JLabel tallerPaddleLabel;
	private JLabel wrapLabel;
	//BufferedImage magnetBufferedImage;
	Image magnetImage;
	Image tallerPaddleImage;
	Image wrapImage;

	private JLabel inventoryLabel;
	private JLabel scoreLabel;

	//private ImageIcon background = ImageIO.read(getClass().getClassLoader().getResource("resources/background.gif")); 
	//private ImageIcon backgroundImage;
	
	public RunningPanel() {
			GameController.addKeyBindings(this);
			this.setOpaque(false);
			
			this.livesLabel = new JLabel("Lives: 3");
			//livesLabel.setBounds(GameView.getScreenWidth() - 150, 10, 100, 50);
			livesLabel.setBounds(10, 10, 100, 50);
			livesLabel.setForeground(Color.WHITE);
			
			
			magnetLabel = new JLabel("0");
			magnetLabel.setBounds(70, GameView.getScreenHeight() - 180, 50, 100);
			magnetLabel.setForeground(Color.WHITE);
			
			tallerPaddleLabel = new JLabel("0");
			tallerPaddleLabel.setBounds(70, GameView.getScreenHeight() - 160, 50, 100);
			tallerPaddleLabel.setForeground(Color.WHITE);
			
			wrapLabel = new JLabel("0");
			wrapLabel.setBounds(70, GameView.getScreenHeight() - 140, 50, 100);
			wrapLabel.setForeground(Color.WHITE);
			
			
			
			this.add(livesLabel);

			this.add(magnetLabel);
			this.add(tallerPaddleLabel);
			this.add(wrapLabel);

			
			this.scoreLabel=new JLabel("Score: 0");
			scoreLabel.setBounds(10, 30, 100, 50);
			scoreLabel.setForeground(Color.WHITE);
			this.add(scoreLabel);
			
			this.inventoryLabel= new JLabel("Magnet: 0 Taller Paddle:0 Wrap:0");
			inventoryLabel.setBounds(0, GameView.getScreenHeight()-110, 200, 50);
			inventoryLabel.setForeground(Color.WHITE);
			//this.add(inventoryLabel);
			

			this.setLayout(null);
			
			try {
				BufferedImage magnetBufferedImage = ImageIO.read(getClass().getClassLoader().getResource("resources/" + "magnet.png")); 
				magnetImage = magnetBufferedImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Background image not found!", "Error", JOptionPane.WARNING_MESSAGE);;
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(null, "Illegal argument! File name: " + "magnet.png", "Error", JOptionPane.WARNING_MESSAGE);
			}
			magnetImage = magnetImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			
			try {
				BufferedImage tallerPaddleBufferedImage = ImageIO.read(getClass().getClassLoader().getResource("resources/" + "tallerPaddle.png")); 
				tallerPaddleImage = tallerPaddleBufferedImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Background image not found!", "Error", JOptionPane.WARNING_MESSAGE);;
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(null, "Illegal argument! File name: " + "tallerPaddle.png", "Error", JOptionPane.WARNING_MESSAGE);
			}
			tallerPaddleImage = tallerPaddleImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			
			try {
				BufferedImage wrapBufferedImage = ImageIO.read(getClass().getClassLoader().getResource("resources/" + "wrap.png")); 
				wrapImage = wrapBufferedImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Background image not found!", "Error", JOptionPane.WARNING_MESSAGE);;
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(null, "Illegal argument! File name: " + "tallerPaddle.png", "Error", JOptionPane.WARNING_MESSAGE);
			}
			wrapImage = wrapImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			
			
			
			//backgroundImage = new ImageIcon("resources/background.gif");
			//backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("resources/background.gif"));
			//backgroundImage.setImage(backgroundImage.getImage().getScaledInstance(GameView.getScreenWidth(), panelHeight, Image.SCALE_DEFAULT));
	}
	
	public void draw(Graphics g) {
		//g.drawImage(backgroundImage.getImage(), 0, 0, this);
		g.drawImage(magnetImage, 20, GameView.getScreenHeight() - 100, this);
		g.drawImage(tallerPaddleImage, 20, GameView.getScreenHeight() - 120, this);
		g.drawImage(wrapImage, 20, GameView.getScreenHeight() - 140, this);
		for (UIGameObject gameObject : gameObjectsMap.values()) {
			if (gameObject.getObjectType() == "paddle") {
				AffineTransform at = AffineTransform.getTranslateInstance(gameObject.getX(), gameObject.getY());
				at.rotate(Math.toRadians(gameObject.getAngle()), gameObject.getWidth()/2, gameObject.getHeight()/2);
				Image tmp = gameObject.getImage().getScaledInstance(gameObject.getWidth(), gameObject.getHeight(), Image.SCALE_SMOOTH);
				Graphics2D g2d = (Graphics2D) g;
				g2d.drawImage(tmp, at, null);
				//g.drawImage(gameObject.getImage().getScaledInstance(panelWidth/10, 20, Image.SCALE_SMOOTH), gameObject.getX(), gameObject.getY(), this);
			}
			else {
				
				//g.setColor(Color.GREEN);
			    //g.fillRect((int)gameObject.getX(), (int)gameObject.getY(), gameObject.getWidth(), gameObject.getHeight());
				//g.setColor(Color.RED);
				//Rectangle r = new Rectangle((int)gameObject.getX(), (int)gameObject.getY(),30,15);
				//g.fillOval((int)gameObject.getX(), (int)gameObject.getY(), gameObject.getWidth(), gameObject.getWidth());
				//g.drawImage(gameObject.getScaledImage(), gameObject.getX(), gameObject.getY(),this);
				g.drawImage(gameObject.getScaledImage(), gameObject.getX(), gameObject.getY(),this);
			}
			
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public static void updatePanelSize() {
		panelWidth = GameView.getScreenHeight() - 93;
		panelHeight = GameView.getScreenWidth() - 16;
		
	}
	
	public void addGameObject(UIGameObject gameObject) {
		gameObject.scaleImage(gameObject.getWidth(), gameObject.getHeight());
		this.gameObjects.add(gameObject);
		this.gameObjectsMap.put(gameObject.getId(), gameObject);
	}

	public void clearGameObjects() {
		this.gameObjects.clear();
	}
	
	public JLabel getLivesLabel() {
		return this.livesLabel;
	}
	
	public JLabel getInventoryLabel() {
		return this.inventoryLabel;
	}
	public int[] getPx() {
		return px;
	}

	public void setPx(int[] px) {
		this.px = px;
	}

	public int[] getPy() {
		return py;
	}

	public void setPy(int[] py) {
		this.py = py;
	}

	public static int getPanelWidth() {
		return panelWidth;
	}

	public HashMap<Integer, UIGameObject> getGameObjectsMap() {
		return gameObjectsMap;
	}


	public JLabel getMagnetLabel() {
		return magnetLabel;
	}

	public void setMagnetLabel(JLabel magnetLabel) {
		this.magnetLabel = magnetLabel;
	}

	public JLabel getTallerPaddleLabel() {
		return tallerPaddleLabel;
	}

	public void setTallerPaddleLabel(JLabel tallerPaddleLabel) {
		this.tallerPaddleLabel = tallerPaddleLabel;
	}

	public JLabel getWrapLabel() {
		return wrapLabel;
	}

	public void setWrapLabel(JLabel wrapLabel) {
		this.wrapLabel = wrapLabel;
	}
	
	

	public JLabel getScoreLabel() {
		return this.scoreLabel;
	}

	
	
}
