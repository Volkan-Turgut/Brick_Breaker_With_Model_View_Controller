package application.controllers;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import ui.UIGameObject;
import ui.panels.RunningPanel;
import application.models.BuildModel;
import application.models.GameModel;
import domain.gameobjects.GameObject;
import domain.gameobjects.Paddle;
import domain.gameobjects.aliens.Alien;
import domain.gameobjects.aliens.CooperativeAlien;
import domain.gameobjects.aliens.ProtectingAlien;
import domain.gameobjects.aliens.RepairingAlien;
import domain.gameobjects.aliens.TimeWastingAlien;
import domain.gameobjects.asteroids.Asteroid;
import domain.gameobjects.asteroids.ExplosiveAsteroid;
import domain.gameobjects.asteroids.FirmAsteroid;
import domain.gameobjects.asteroids.GiftAsteroid;
import domain.gameobjects.asteroids.SimpleAsteroid;
import domain.gameobjects.powerups.Magnet;
import domain.Inventory;
import domain.gameobjects.Ball;
import ui.GameView;
import resources.*;
import technicalservices.SaveLoadAdapter;
import technicalservices.SaveLoadDatabaseAdapter;
import technicalservices.SaveLoadFileAdapter;

public class GameController {
	GameView gameView;
	static GameModel gameModel;
	static BuildModel buildModel;
	static SaveLoadAdapter saveloadAdapter;
	static boolean downKeyPressed = false;
	static Timer timer;
	Inventory inventory;
	
	public GameController(GameModel gameModel, GameView gameView) {
		this.gameView = gameView;
		this.gameModel = gameModel;
		this.inventory=GameModel.getInstance().getInventory();
		//gameModel.setPaddleWidth(RunningPanel.getPanelWidth());
		gameView.setFocusable(true);
		gameView.requestFocus();
		gameView.requestFocusInWindow();
		
		for (GameObject gameObject : gameModel.getGameObjects()) {
			gameView.addUIGameObjectToPanel(translateGameObjectForUI(gameObject));
		}
		gameView.repaintView();
		
		timer = new Timer(gameView.getDelay(), new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!gameView.isPause()) {
					synchronized (gameModel.getGameObjects()) {
						for (GameObject gameObject : gameModel.getGameObjects()) {
							updateUIGameObject(gameObject);
						}
					}
					gameModel.run();
					gameView.getLivesLabel().setText("Lives: " + gameModel.getLives());
					gameView.getInventoryLabel().setText("Magnet: "+ inventory.getNumberOfMagnet()+ " Taller Paddle: "+ inventory.getNumberOfTallerPaddle()+ " Wrap: "+ inventory.getNumberOfWrap());
					gameView.getScoreLabel().setText("Score: "+ GameModel.getInstance().getScore());
					if (gameModel.isGameLost()) {
						gameView.setPause(true);
						gameView.addGameLostText();
					}
					
					gameView.getInventoryLabels().get(2).setText(gameModel.getInventory().getInventoryCounts()[0] + "");
					gameView.getInventoryLabels().get(1).setText(gameModel.getInventory().getInventoryCounts()[1] + "");
					gameView.getInventoryLabels().get(0).setText(gameModel.getInventory().getInventoryCounts()[2] + "");
					removeObjectsFromUI();
					gameView.repaintView();
				}
			}
		});
		timer.start();

		this.gameView.setVisible(true);
		
		gameView.getRunningPanelFSButton().addActionListener(e -> {
			if (!gameView.isFullScreen()) {
				gameView.dispose();
				gameView.setUndecorated(true);
				Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
				gameModel.updateGameObjectsForGameSize(size.getWidth(), size.getHeight());
				gameView.clearGameObjects();
				for (GameObject gameObject : gameModel.getGameObjects()) {
					gameView.addUIGameObjectToPanel(translateGameObjectForUI(gameObject));
				}
				gameView.setScreenHeight((int) size.getHeight());
				gameView.setScreenWidth((int) size.getWidth());
				gameView.resizeBackground();
				gameView.setSize(size);
				gameView.setLocation(0, 0);
				gameView.setVisible(true);
			}
			else {
				gameView.dispose();
				gameView.setUndecorated(false);
				gameView.setSize(new Dimension(1024, 768));
				gameModel.updateGameObjectsForGameSize(1024 - 16 , 768 - 93);
				gameView.setScreenHeight(1024);
				gameView.setScreenWidth(768);
				gameView.resizeBackground();
				gameView.clearGameObjects();
				for (GameObject gameObject : gameModel.getGameObjects()) {
					UIGameObject uiGameObject = translateGameObjectForUI(gameObject);
					uiGameObject.scaleImage();
					gameView.addUIGameObjectToPanel(uiGameObject);
					
				}
				gameView.setVisible(true);
				
			}
		});
		
		gameView.getMusicButton().addActionListener(e -> {
			if (gameView.getMusicButton().isSelected() ) {
				gameModel.playGameMusic();
			}
			else {
				gameModel.muteGameMusic();
			}
		});
		
		gameView.getSaveDBButton().addActionListener(e -> {
			saveloadAdapter = new SaveLoadDatabaseAdapter();
			saveloadAdapter.saveGame(gameModel.getGameObjects());
		});
		
		gameView.getLoadDBButton().addActionListener(e -> {
			
			saveloadAdapter = new SaveLoadDatabaseAdapter();
			saveloadAdapter.loadGame(gameModel.getGameObjects());
			
			
		});
		
		gameView.getSaveFileButton().addActionListener(e -> {			
			saveloadAdapter = new SaveLoadFileAdapter();
			saveloadAdapter.saveGame(gameModel.getGameObjects());
		});
		
		gameView.getLoadFileButton().addActionListener(e -> {			
			saveloadAdapter = new SaveLoadFileAdapter();
			saveloadAdapter.loadGame(gameModel.getGameObjects());
		});
		
		
	}
	
	private void removeObjectsFromUI() {
		for (Integer key : gameModel.getGameObjectIdsToBeRemoved().keySet()) {
			gameView.getGameObjectsMap().remove(key);
		}
	}
	
	private void updateUIGameObject(GameObject gameObject) {
		UIGameObject uiGameObject = gameView.getGameObjectsMap().get(gameObject.getId());
		if(uiGameObject == null) {
			gameView.addUIGameObjectToPanel(translateGameObjectForUI(gameObject));
			return;
		}
		
	
		
		uiGameObject.setX((int) gameObject.getX());
		uiGameObject.setY((int) gameObject.getY());
		if (uiGameObject.getObjectType().equals("paddle")) {
			uiGameObject.setAngle(((Paddle) gameObject).getAngle());
			uiGameObject.setWidth((int) gameObject.getWidth());
			uiGameObject.setHeight((int) gameObject.getHeight());
			uiGameObject.scaleImage();
		}
		
		if (uiGameObject.getObjectType().equals("firm")) {
			uiGameObject.setWidth((int) gameObject.getWidth());
			uiGameObject.setHeight((int) gameObject.getHeight());
			uiGameObject.scaleImage();
		}
		
		if(gameObject.isBecomeFrozen()) {
			if(gameObject instanceof SimpleAsteroid || gameObject instanceof GiftAsteroid) {
				uiGameObject.changeImage("frozen.png");
			} else {
				uiGameObject.changeImage("frozenCirle.png");
			}
			
			gameObject.setBecomeFrozen(false);
		}
		
		if(gameObject.isBecomeUnFrozen()) {
			if(gameObject instanceof SimpleAsteroid) {
				uiGameObject.changeImage("simpleAsteroid2.png");
			} else if(gameObject instanceof ExplosiveAsteroid) {
				uiGameObject.changeImage("explosiveAsteroid2.png");
			} else if(gameObject instanceof FirmAsteroid) {
				uiGameObject.changeImage("firmAsteroid2.png");
			} else if(gameObject instanceof GiftAsteroid) {
				uiGameObject.changeImage("giftAsteroid.png");
			}
			
			gameObject.setBecomeUnFrozen(false);
		}
		
		
	}

	private UIGameObject translateGameObjectForUI(GameObject gameObject) {
		return new UIGameObject(gameObject);

	}
	
	public static void startTimer() {
		timer.start();
	}
	
	public static void addKeyBindings(JComponent jc) {
		
		// Right Arrow  Key
	    jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right pressed");
	    jc.getActionMap().put("right pressed", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	            gameModel.setPaddleDirection("right pressed");
	        }
	    });
	    
	    jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "right released");
	    jc.getActionMap().put("right released", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	            gameModel.setPaddleDirection("right released");
	        }
	    });
	    
	    // Left Arrow Key
	    jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left pressed");
	    jc.getActionMap().put("left pressed", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	            gameModel.setPaddleDirection("left pressed");
	        }
	    });
	    
	    jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "left released");
	    jc.getActionMap().put("left released", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	            gameModel.setPaddleDirection("left released");
	        }
	    });
	    
	    // Down Arrow Key
	    jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "down pressed");
	    jc.getActionMap().put("down pressed", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	if (!downKeyPressed) {
	        		gameModel.setPaddleVelocitySetting("fast");
	        	}
	        	downKeyPressed = true;
	        }
	    });
	    
	    
	    jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "down released");
	    jc.getActionMap().put("down released", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	downKeyPressed = false;
	        	gameModel.setPaddleVelocitySetting("normal");
	        }
	    });
	    
	    // A Key
	    jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "A pressed");
	    jc.getActionMap().put("A pressed", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	gameModel.setPaddleRotationDirection("counterclockwise");
	        }
	    });
	    
	    jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "A released");
	    jc.getActionMap().put("A released", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	gameModel.setPaddleRotationDirection("counterclockwise released");
	        }
	    });
	    
	    
	    // D Key
	    jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "D pressed");
	    jc.getActionMap().put("D pressed", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	gameModel.setPaddleRotationDirection("clockwise");
	        }
	    });

	    jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "D released");
	    jc.getActionMap().put("D released", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	gameModel.setPaddleRotationDirection("clockwise released");
	        }
	    });
	    
	    jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0, false), "T pressed");
	    jc.getActionMap().put("T pressed", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	System.out.println("T Pressed");
	        	gameModel.usePowerup("tallerPaddle");
	        	
	        }
	    });
	    
	    jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_V, 0, false), "V pressed");
	    jc.getActionMap().put("V pressed", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	System.out.println("V Pressed");
	        	gameModel.usePowerup("wrap");
	        	
	        }
	    });
	    
	    jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0, false), "M pressed");
	    jc.getActionMap().put("M pressed", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	System.out.println("M Pressed");
	        	gameModel.usePowerup("magnet");
	        	
	        }
	    });
	    
	    jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "W pressed");
	    jc.getActionMap().put("W pressed", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	System.out.println("W Pressed");
	        	Magnet.setwPressed(true);
	        	
	        }
	    });
	    
	}
	

		
	
	
	

}
