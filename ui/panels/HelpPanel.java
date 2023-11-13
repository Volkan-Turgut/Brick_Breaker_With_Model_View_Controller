package ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import application.controllers.GameController;
import domain.gameobjects.GameObject;
import ui.GameView;
import ui.UIGameObject;

public class HelpPanel extends JPanel {
	private ArrayList<UIGameObject> gameObjects = new ArrayList<>();
	HashMap<Integer, UIGameObject> gameObjectsMap = new HashMap<>();
	private int[] px;
	private int[] py;
	private int panelWidth;
	private int panelHeight;
	private int mouseX;
	private int mouseY;
	private GameView gameView;
	private MouseListener mouseListener;
	
	public HelpPanel(GameView gameView) {
			panelWidth = 1008;
			this.gameView = gameView;
			this.setOpaque(false);
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (UIGameObject gameObject : gameObjects) {
			g.drawImage(gameObject.getScaledImage(), gameObject.getX(), gameObject.getY(),this);
			

			
			
			
		}
		
		
	}
	

	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	

	
	
	

}
