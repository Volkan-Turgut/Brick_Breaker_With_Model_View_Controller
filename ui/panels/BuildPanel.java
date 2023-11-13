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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import application.controllers.GameController;
import domain.gameobjects.GameObject;
import ui.GameView;
import ui.UIGameObject;

public class BuildPanel extends JPanel {
	//private ArrayList<UIGameObject> gameObjects = new ArrayList<>();
	private List<UIGameObject> gameObjects = Collections.synchronizedList(new ArrayList<UIGameObject>());
	HashMap<Integer, UIGameObject> gameObjectsMap = new HashMap<>();
	private int[] px;
	private int[] py;
	private int panelWidth;
	private int panelHeight;
	private int mouseX;
	private int mouseY;
	private GameView gameView;
	private MouseListener mouseListener;
	
	public BuildPanel(GameView gameView) {
			panelWidth = 1200;  //1008
			this.gameView = gameView;
			MouseHandler handler = new MouseHandler();
			this.addMouseListener(handler);
			this.addMouseMotionListener(handler);
			this.setOpaque(false);
	}
	
	public void draw(Graphics g) {
		synchronized (gameObjects) {
		Graphics2D g2d = (Graphics2D) g;
		for (UIGameObject gameObject : gameObjects) {
			g.drawImage(gameObject.getScaledImage(), gameObject.getX(), gameObject.getY(),this);
			
			
			
			
			
		}
		}
		
	}
	
	public MouseListener getMouseListener() {
		return mouseListener;
	}
	
	public class MouseHandler implements MouseListener, MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			mouseX = e.getX();
			gameView.setMouseX(mouseX);
			mouseY = e.getY();
			gameView.setMouseY(mouseY);
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void addGameObject(UIGameObject gameObject) {
		gameObject.scaleImage(gameObject.getWidth(), gameObject.getHeight());
		this.gameObjects.add(gameObject);
		this.gameObjectsMap.put(gameObject.getId(), gameObject);
	}
	
	public HashMap<Integer, UIGameObject> getGameObjectsMap() {
		return gameObjectsMap;
	}

	public void clearGameObjects() {
		this.gameObjects.clear();
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

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}
	
	
	

}
