package application.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.Timer;

import application.models.BuildModel;
import application.models.GameModel;
import domain.gameobjects.GameObject;
import domain.gameobjects.Paddle;
import domain.gameobjects.asteroids.Asteroid;
import domain.gameobjects.asteroids.ExplosiveAsteroid;
import domain.gameobjects.asteroids.FirmAsteroid;
import domain.gameobjects.asteroids.SimpleAsteroid;
import ui.GameView;
import ui.UIGameObject;

public class BuildController {
	GameView gameView;
	static BuildModel buildModel;
	HashMap<String, Integer> numbers;
	int mouseCounter = 0;
	boolean isSelected = false;
	double distX;
	double distY;
	
	public BuildController(BuildModel buildModel, GameView gameView) {
		this.gameView = gameView;
		this.buildModel = buildModel;
		gameView.setFocusable(true);
		gameView.requestFocus();
		gameView.requestFocusInWindow();
		//buildModel.setAsteroidNumbers(gameView.getAsteroidButtons().getAsteroidNumbers());
		numbers = gameView.getAsteroidButtons().getAsteroidNumbers();
		buildModel.setAsteroidNumbers(numbers);
		for (GameObject asteroidObject : buildModel.getAsteroids()) {
			gameView.addUIGameObjectToPanel(translateGameObjectForUI(asteroidObject));
			gameView.repaint();
		}
		gameView.getStartButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GameView gameView = GameView.getInstance();
				gameView.switchRunningPanel();
				buildModel.setFinish(true);
			}
			
		});
		gameView.addMouseListenerToPanel(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int mouseX = gameView.getMouseX();
				int mouseY = gameView.getMouseY();
				System.out.printf("%d\t%d\n", mouseX, e.getX());
				if((buildModel.isAsteroid(mouseX,mouseY)) && mouseCounter == 0 ) {
					mouseCounter = 1;
					System.out.println("Selected");
					System.out.println(mouseCounter);
					
					//isSelected = true;
					
				}
				if(!buildModel.isAsteroid(mouseX, mouseY) && mouseCounter == 1 ) {
					buildModel.changeTargetLoc(mouseX, mouseY);
					updateUIGameObject(buildModel.getTargetAsteroid());
					gameView.repaintView();
					mouseCounter = 0;
					System.out.println("Replaced");
					System.out.println(mouseCounter);
				}
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				boolean selected = (buildModel.isAsteroid(e.getX(),e.getY()));

				if(selected) {
					double objX = buildModel.getTargetAsteroid().getX();
					double objY = buildModel.getTargetAsteroid().getY();
					distX = e.getX() - objX;
					distY = e.getY() - objY;
				}
				
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
			
		});

		gameView.addMouseMotionListenerToPanel(new MouseMotionListener() {


			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

				int mouseX = e.getX();
				int mouseY = e.getY();
				boolean selected = (buildModel.isAsteroid(mouseX,mouseY));

				if(selected) {
					System.out.printf("%f\t%f\t%d\t%d\n", distX, buildModel.getTargetAsteroid().getX(), mouseX, (int) (mouseX - distX));
					System.out.printf("%f\t%f\t%d\t%d\n", distY, buildModel.getTargetAsteroid().getY(), mouseY, (int) (mouseY - distY));
					buildModel.changeTargetLoc((int) (mouseX - distX), (int) (mouseY - distY));
					updateUIGameObject(buildModel.getTargetAsteroid());
					gameView.repaintView();
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		JButton simpleButton = gameView.getAsteroidButtons().getButtons().get(0);
		JButton firmButton = gameView.getAsteroidButtons().getButtons().get(1);
		JButton explosiveButton = gameView.getAsteroidButtons().getButtons().get(2);
		JButton giftButton = gameView.getAsteroidButtons().getButtons().get(3);
		
		JTextArea simple = gameView.getAsteroidButtons().getTextAreas().get(0);
		JTextArea firm = gameView.getAsteroidButtons().getTextAreas().get(1);
		JTextArea explosive = gameView.getAsteroidButtons().getTextAreas().get(2);
		JTextArea gift = gameView.getAsteroidButtons().getTextAreas().get(3);
		
		HashMap<String,Integer> asteroidNumbers = gameView.getAsteroidButtons().getAsteroidNumbers();
		
		for (JButton button : gameView.getAsteroidButtons().getButtons()) {
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent event) {
					// TODO Auto-generated method stub
					JButton actionSource = (JButton) event.getSource();

				    if(actionSource.equals(simpleButton)){
				    	if(simple.isEnabled()) {
				    		simple.setEnabled(false);
				    		if(gameView.getAsteroidButtons().isCorrectInput(simple.getText())) {
				    			int numberofitem = Integer.parseInt(simple.getText());
				    			
				    			if (numberofitem >= 75) {
					    			asteroidNumbers.put("simple",numberofitem );
					    		}
				    			
				    			else{
					    			simple.setText("Not Valid");
					    		}
				    		}
				    		
				    		else {
				    			simple.setText("Not valid");
				    		}
				    		
				    		
				    		
				    		
				    	
				    	}
				    	
				    	else {
				    		simple.setEnabled(true);
				    		
				    	}
				    } 
				    
				    else if (actionSource.equals(firmButton)) {
				    	if(firm.isEnabled()) {
				    		firm.setEnabled(false);
				    		if(gameView.getAsteroidButtons().isCorrectInput(firm.getText())) {
				    			int numberofitem = Integer.parseInt(firm.getText());
				    			
				    			if (numberofitem >= 10) {
					    			asteroidNumbers.put("firm",numberofitem );
					    		}
				    			
				    			else{
					    			firm.setText("Not Valid");
					    		}
				    		}
				    		
				    		else {
				    			firm.setText("Not valid");
				    		}
				    		
				    	}
				    
				    	else {
				    		firm.setEnabled(true);
				    		
				    	}
				        
				    }
				    else if (actionSource.equals(explosiveButton)){
				    	if(explosive.isEnabled()) {
				    		explosive.setEnabled(false);
				    		if(gameView.getAsteroidButtons().isCorrectInput(explosive.getText())) {
				    			int numberofitem = Integer.parseInt(explosive.getText());
				    			
				    			if (numberofitem >= 5) {
					    			asteroidNumbers.put("explosive",numberofitem );
					    		}
				    			
				    			else{
					    			explosive.setText("Not Valid");
					    		}
				    		}
				    		
				    		else {
				    			explosive.setText("Not valid");
				    		}
				    	}
				    	
				    	
				    	else {
				    		explosive.setEnabled(true);
				    		
				    	}
				    }
				    
				    else if (actionSource.equals(giftButton)){
				    	if(gift.isEnabled()) {
				    		gift.setEnabled(false);
				    		if(gameView.getAsteroidButtons().isCorrectInput(gift.getText())) {
				    			int numberofitem = Integer.parseInt(gift.getText());
				    			
				    			if (numberofitem >= 10) {
					    			asteroidNumbers.put("gift",numberofitem );
					    		}
				    			
				    			else{
					    			gift.setText("Not Valid");
					    		}
				    		}
				    		
				    		else {
				    			gift.setText("Not valid");
				    		}
				    	}
				    	
				    	
				    	else {
				    		gift.setEnabled(true);
				    		
				    	}
				    }
				    	
					reinitializeGameObjects();
					gameView.repaint();
					System.out.println("Button pressed");
				}
			});
		}
		
		/*
		if (gameView.getCurrentPanel()=="buildPanel") {
			Timer timer = new Timer(gameView.getDelay(), new ActionListener() {
			
				public void actionPerformed(ActionEvent e) {
					int mouseX = gameView.getMouseX();
					int mouseY = gameView.getMouseY();
					if((buildModel.isAsteroid(mouseX,mouseY)) && mouseCounter == 0 ) {
						mouseCounter = 1;
						System.out.println("Selected");
						System.out.println(mouseCounter);
						//isSelected = true;
						
					}
					if(!buildModel.isAsteroid(mouseX, mouseY) && mouseCounter == 1 ) {
						buildModel.changeTargetLoc(mouseX, mouseY);
						mouseCounter = 0;
						System.out.println("Replaced");
						System.out.println(mouseCounter);
					}
					
						
					if ((gameView.getCurrentPanel()!="runningPanel")) {
						boolean gameObjectsCleared = false;
						
						if(buildModel.getAsteroidNumbers().get("simple") != gameView.getAsteroidButtons().getAsteroidNumbers().get("simple")) {
							gameObjectsCleared = true;
							gameView.clearGameObjects();
							buildModel.setAsteroidNumbers(gameView.getAsteroidButtons().getAsteroidNumbers());
						}
						if(buildModel.getAsteroidNumbers().get("firm") != gameView.getAsteroidButtons().getAsteroidNumbers().get("firm")) {
							if (!gameObjectsCleared) {
								gameView.clearGameObjects();
								gameObjectsCleared = true;
							}
							buildModel.setAsteroidNumbers(gameView.getAsteroidButtons().getAsteroidNumbers());
						}
						if(buildModel.getAsteroidNumbers().get("explosive") != gameView.getAsteroidButtons().getAsteroidNumbers().get("explosive")) {
							if (!gameObjectsCleared) {
								gameView.clearGameObjects();
								gameObjectsCleared = true;
							}
							buildModel.setAsteroidNumbers(gameView.getAsteroidButtons().getAsteroidNumbers());
						}

						if (gameObjectsCleared) {
							for (GameObject asteroidObject : buildModel.getAsteroids()) {
								gameView.addUIGameObjectToPanel(translateGameObjectForUI(asteroidObject));
							}
						}
						//buildModel.run();
						
						gameView.repaintView();
						
					}
					else {
						buildModel.setFinish(true);
					}
				}
			});
			
			//timer.start();
			this.gameView.setVisible(true);

		
		}
		*/
		gameView.setVisible(true);

		gameView.repaint();
		
	}
	
	private UIGameObject translateGameObjectForUI(GameObject asteroidObject ) {
		return new UIGameObject(asteroidObject);

	
	}
	
	private void updateUIGameObject(GameObject gameObject) {
		UIGameObject uiGameObject = gameView.getGameObjectsMap().get(gameObject.getId());
		uiGameObject.setX((int) gameObject.getX());
		uiGameObject.setY((int) gameObject.getY());
		if (uiGameObject.getObjectType().equals("paddle")) {
			uiGameObject.setAngle(((Paddle) gameObject).getAngle());
		}
		
		if (uiGameObject.getObjectType().equals("firm")) {
			uiGameObject.setWidth((int) gameObject.getWidth());
			uiGameObject.setHeight((int) gameObject.getHeight());
			uiGameObject.scaleImage();
		}
	}
	
	public void reinitializeGameObjects() {
		boolean gameObjectsCleared = false;
		if(buildModel.getAsteroidNumbers().get("simple") != gameView.getAsteroidButtons().getAsteroidNumbers().get("simple")) {
			gameObjectsCleared = true;
			gameView.clearGameObjects();
			buildModel.setAsteroidNumbers(gameView.getAsteroidButtons().getAsteroidNumbers());
		}
		if(buildModel.getAsteroidNumbers().get("firm") != gameView.getAsteroidButtons().getAsteroidNumbers().get("firm")) {
			if (!gameObjectsCleared) {
				gameView.clearGameObjects();
				gameObjectsCleared = true;
			}
			buildModel.setAsteroidNumbers(gameView.getAsteroidButtons().getAsteroidNumbers());
		}
		if(buildModel.getAsteroidNumbers().get("explosive") != gameView.getAsteroidButtons().getAsteroidNumbers().get("explosive")) {
			if (!gameObjectsCleared) {
				gameView.clearGameObjects();
				gameObjectsCleared = true;
			}
			buildModel.setAsteroidNumbers(gameView.getAsteroidButtons().getAsteroidNumbers());
		}
		if(buildModel.getAsteroidNumbers().get("gift") != gameView.getAsteroidButtons().getAsteroidNumbers().get("gift")) {
			if (!gameObjectsCleared) {
				gameView.clearGameObjects();
				gameObjectsCleared = true;
			}
			buildModel.setAsteroidNumbers(gameView.getAsteroidButtons().getAsteroidNumbers());
		}

		if (gameObjectsCleared) {
			for (GameObject asteroidObject : buildModel.getAsteroids()) {
				gameView.addUIGameObjectToPanel(translateGameObjectForUI(asteroidObject));
			}
		}
	}

}
