package application.models;

import domain.Inventory;
import domain.gameobjects.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.awt.*;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import domain.gameobjects.GameObject;
import domain.gameobjects.aliens.Alien;
import domain.gameobjects.aliens.CooperativeAlien;
import domain.gameobjects.aliens.ProtectingAlien;
import domain.gameobjects.aliens.RepairingAlien;
import domain.gameobjects.aliens.TimeWastingAlien;
import domain.gameobjects.aliens.alienbehaviour.CreateAsteroid;
import domain.gameobjects.aliens.alienbehaviour.CreateRowAndFreeze;
import domain.gameobjects.aliens.alienbehaviour.DeleteRow;
import domain.gameobjects.aliens.alienbehaviour.EscapeAfterFive;
import domain.gameobjects.aliens.alienbehaviour.NoAlienBehave;
import domain.gameobjects.aliens.hitbehaviour.Escape;
import domain.gameobjects.aliens.hitbehaviour.NoCooperative;
import domain.gameobjects.aliens.hitbehaviour.UpperPartHit;
import domain.gameobjects.asteroids.Asteroid;
import domain.gameobjects.asteroids.AsteroidFactory;
import domain.gameobjects.asteroids.ExplosiveAsteroid;
import domain.gameobjects.asteroids.FirmAsteroid;
import domain.gameobjects.asteroids.GiftAsteroid;
import domain.gameobjects.asteroids.SimpleAsteroid;

import domain.gameobjects.aliens.SuprisingAlien;

import domain.gameobjects.powerups.Chance;
import domain.gameobjects.powerups.CreateAlien;
import domain.gameobjects.powerups.Magnet;
import domain.gameobjects.powerups.Powerup;
import ui.UIGameObject;


public class GameModel {

	private static GameModel instance;
	
	private boolean ballPaddleCollided;
	
	private Ball ball;
	private Paddle paddle;
	BuildModel buildModel;
	//private ArrayList<GameObject> gameObjects = new ArrayList<>();
	private List<GameObject> gameObjects = Collections.synchronizedList(new ArrayList<GameObject>());
	private ArrayList<GameObject> asteroids;
	private int gameWidth;
	private int gameHeight;
	private int lives;
	private boolean isGameLost;
	private boolean isPause;
	private double delay;
	private Clip themeMusic;
	private HashMap<Integer, GameObject> gameObjectIdsToBeRemoved = new HashMap<>();
	private ArrayList<Alien> aliens = new ArrayList<>(); 
	private boolean canCreateCoAlien = true;
	private ArrayList<GameObject> frozenAsteroids = new ArrayList<>();
	private ArrayList<Ball> balls = new ArrayList<>();
	private boolean isRunFrozen;
	private double frozenTime = 0; 
	private Inventory inventory;
	private boolean originalBallFell = false;
	private int initialNumOfAsteroids;
	
	long start;
	long end;
	int score=0;
	//public GameModel(int screenWidth, int screenHeight, double delay,BuildModel buildModel) {


	
	private int screenWidth; //
	private int screenHeight; //
	
	public static void initializeGameModel(int screenWidth, int screenHeight, double delay,BuildModel buildModel) {
		instance = null;
		instance = new GameModel(screenWidth, screenHeight, delay, buildModel);
	}
	
	public static synchronized GameModel getInstance() {
		return instance;
	}
	
	private GameModel(int screenWidth, int screenHeight, double delay,BuildModel buildModel) {

		this.buildModel = buildModel;
		while(!buildModel.isFinish());
		this.asteroids = buildModel.getAsteroids();
		this.gameWidth = screenWidth - 16;
		this.gameHeight = screenHeight - 70; //93
		this.lives = 300;
		this.isGameLost = false;
		this.delay = delay;
		this.inventory = new Inventory();
		paddle = new Paddle(delay, (int) (screenWidth / 2), (int) screenHeight - 125);
		paddle.setWidth(screenWidth / 10);
		paddle.setX(paddle.getX() - paddle.getWidth()/2);
		ball = new Ball(delay,(int) (screenWidth / 2), (int) screenHeight - 125-20);
		balls.add(ball);
		gameObjects.add(paddle);
		gameObjects.add(ball);
		gameObjects.addAll(asteroids);
		initialNumOfAsteroids = asteroids.size();
		/*createAnAlien();
		createAnAlien();
		createAnAlien();
		createAnAlien();
		createASuprisingAlien();*/
		
		
		
		
		// Load Music Files
		try {
			AudioInputStream audioStream;
			audioStream = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("resources/" + "Alien Asteroid Crusher Theme Music.wav"));
			themeMusic = AudioSystem.getClip();
			themeMusic.open(audioStream);
			themeMusic.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException | IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) { 
			e1.printStackTrace();
		}
	}

	public void run() throws ArithmeticException{
		synchronized (gameObjects) {
			getStartTime();
			for(GameObject asteroid: asteroids) {
				if(!asteroid.isDestroyed() && asteroid.getStrength()<=0) {
					
						asteroid.setDestroyed(true);
						try {
						score=score+ 300/(int)((getCurrentTime()-getStartTime())/1000);
						}
						catch(ArithmeticException e) {
							score=0;
							System.out.println("exception catched");
						}
					
				}
			}
			//this.clearAsteroidArray();
			//this.clearGameObjectArray();
			removeGameObjectsToBeRemoved();
			
			for(GameObject item : gameObjects) {
				item.update();
			}
			checkPaddleWallCollision();
			checkBallWallCollision();
			checkBallPaddleCollision();
	
			checkAlienWallCollision();
			checkAlienBallCollision();
			
		
	
			
			if(isRunFrozen) {
				frozenTime++;
				if(frozenTime > 15000.0/delay) {
					for(GameObject asteroid: frozenAsteroids) {
						System.out.println("Unfrozen------------------------");
						//System.out.println(asteroid.isFrozen());
						asteroid.setFrozen(false);
						asteroid.setBecomeUnFrozen(true);
					}
					
					isRunFrozen = false;
					frozenAsteroids.clear();
					frozenTime = 0;
				}
			}
			
			for(int i = 0; i < gameObjects.size(); i++) {
				GameObject gameObject = gameObjects.get(i);
	 			if(gameObject instanceof Alien) {
					
	 				((Alien )gameObject).setAlienCounter(((Alien )gameObject).getAlienCounter() + (double) delay * 0.001);
	 				((Alien)gameObject).alienPerform(this);
	 				//System.out.println(((Alien )gameObject).getAlienCounter());
					
				}
				
				if(gameObject instanceof Asteroid) {
					checkAsteroidWallCollision(gameObject);
					if (gameObject.isMovingObject() && gameObject.getVx() == 0) {
						if (Math.random() < 0.5) {
							gameObject.setVx(paddle.getWidth() * delay / 4000);
						}
						else {
							gameObject.setVx(-paddle.getWidth() * delay / 4000);
						}
					}
					
					for(GameObject gameObject2 : gameObjects) {
						if(gameObject2 instanceof Asteroid) {
							checkAsteroidAsteroidCollision(gameObject, gameObject2);
					
						}
						if(gameObject2 instanceof Alien) {
							checkAsteroidAlienCollision(gameObject, gameObject2);
					
						}
						if(gameObject2 instanceof Laser) {
							//checkAsteroidLaserCollision(gameObject, gameObject2);
							if (gameObject2.getX() + gameObject2.getWidth() > gameObject.getX() &&
									gameObject2.getX() < gameObject.getX() + gameObject.getWidth() &&
									gameObject2.getY() < gameObject.getY() + gameObject.getHeight()) {
								((Asteroid) gameObject).hitByBall();
								addToWillBeRemoved(gameObject);
							}
						}
					}
					for (Ball ball : balls) {
						checkBallAsteroidCollision(ball,(Asteroid)gameObject);
					}
					if(gameObject instanceof ExplosiveAsteroid) {
						checkIfExploded((ExplosiveAsteroid)gameObject);
					}
					
				}
				
			}
			
			for (GameObject asteroid : asteroids) {
				if (asteroid instanceof GiftAsteroid) {
					if (asteroid.getStrength() <= 0) {
						((GiftAsteroid) asteroid).spawnPowerup();
					}
					if (((GiftAsteroid) asteroid).isGiftDropped()) {
						checkPowerUpPaddleCollision(((GiftAsteroid) asteroid).getPowerup());
					}
				}
			}
		}
		
	}
	
	
	
	public ArrayList<Alien> getAliens() {
		return aliens;
	}

	public void setAliens(ArrayList<Alien> aliens) {
		this.aliens = aliens;
	}

	public void addToWillBeRemoved(GameObject gameObject) {
		gameObjectIdsToBeRemoved.put(gameObject.getId(), gameObject);
	}
	
	private void removeGameObjectsToBeRemoved() {
		gameObjects.removeAll(gameObjectIdsToBeRemoved.values());
	}
	
	public ArrayList<GameObject> checkAsteroidsAlive() {
		ArrayList<GameObject> aliveAsteroids = new ArrayList<>();
		for(GameObject asteroid: this.getAsteroids()) {
			if(((Asteroid) asteroid).getStrength() >= 1) {
				aliveAsteroids.add(asteroid);
			}
		}
		
		return aliveAsteroids;
	}
	
	public void usePowerup(String powerup) {
		inventory.usePowerup(powerup);
	}

	public double getAsteroidRatio() {
		return ((double) checkAsteroidsAlive().size())/initialNumOfAsteroids;
	}
	public int getGameHeight() {
		return gameHeight;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}
	

	private boolean checkCollision(int x, int y, int w, int h) {
		
		Rectangle newbrick = new Rectangle(x, y, w, h);
		
		for (GameObject gameObject : asteroids) {
			Rectangle oldbrick = new Rectangle((int)gameObject.getX(), (int)gameObject.getY(),w, h);
		       if (oldbrick.intersects(newbrick)) {

		          return true;  
		        }
			
		}
		return false;
	}
		
	private boolean checkAlienCollision(int x, int y, int w, int h) {
		
		Rectangle newbrick = new Rectangle(x, y, w, h);
		
		for (GameObject gameObject : aliens) {
			Rectangle oldbrick = new Rectangle((int)gameObject.getX(), (int)gameObject.getY(),w, h);
		       if (oldbrick.intersects(newbrick)) {

		          return true;  
		        }
			
		}
		return false;
	}

	public int getGameWidth() {
		return gameWidth;
	}
	
	public SuprisingAlien createASuprisingAlien() {
		double ratio = getAsteroidRatio();
		Alien alien;
		//alien = new SuprisingAlien(new Escape(), new CreateRowAndFreeze());
		
		if(ratio > 0.7) {
			 alien = new SuprisingAlien(new NoCooperative(), new DeleteRow());
			 System.out.println("DeleteRow");
		}
		
		else if(ratio < 0.3) {
			alien = new SuprisingAlien(new Escape(), new CreateRowAndFreeze());
			System.out.println("RowFreeze");
		}
		
		else if(ratio > 0.4 && ratio < 0.5) {
			alien = new SuprisingAlien(new UpperPartHit(), new NoAlienBehave());
			System.out.println("UpperPartHit");
		}
		
		else if(ratio > 0.5 && ratio < 0.6) {
			alien = new SuprisingAlien(new Escape(), new CreateAsteroid());
			System.out.println("CreateAsteroid");
		}
		else  {
			alien = new SuprisingAlien(new Escape(), new EscapeAfterFive());
			System.out.println("Escape");
		}
		
		
		Random rand = new Random();
		int px = rand.nextInt(gameWidth - (int) alien.getWidth());
    	int py = rand.nextInt((gameHeight-300));
    
		while(checkCollision(px, py, (int) alien.getWidth(),(int) alien.getHeight()) || checkAlienCollision(px, py, (int) alien.getWidth(),(int) alien.getHeight())) {
				
			px = rand.nextInt(gameWidth - (int) alien.getWidth());
		    py = rand.nextInt((gameHeight-300));
				
		}
		
		alien.setX(px);
		alien.setY(py);
		alien.setX(px);
		alien.setY(py);
		this.gameObjects.add(alien);
		this.aliens.add(alien);
		return (SuprisingAlien)alien;
		
	}

	public void createAnAlien() {
		Random rand = new Random();
		Alien alien;
		//createASuprisingAlien();
		
		
		ArrayList<String> remainingAliens = new ArrayList<String>();
		if(canCreateCoAlien) {
			remainingAliens.add("Co");
		}
		remainingAliens.add("Pr");
		remainingAliens.add("Re");
		remainingAliens.add("Ti");
		remainingAliens.add("Su");
		for(int i = 0; i < aliens.size(); i++) {
			if(aliens.get(i) instanceof CooperativeAlien && !((Alien)aliens.get(i)).isHasEscaped()) {
				remainingAliens.remove("Co");
			} else if(aliens.get(i) instanceof RepairingAlien && !((Alien)aliens.get(i)).isHasEscaped()) {
				remainingAliens.remove("Re");
			}  else if(aliens.get(i) instanceof ProtectingAlien && !((Alien)aliens.get(i)).isHasEscaped()) {
				remainingAliens.remove("Pr");
			} else if(aliens.get(i) instanceof TimeWastingAlien && !((Alien)aliens.get(i)).isHasEscaped()) {
				remainingAliens.remove("Ti");
			} else if(aliens.get(i) instanceof SuprisingAlien && !((Alien)aliens.get(i)).isHasEscaped()) {
				remainingAliens.remove("Su");
			}
		}
		
		if(remainingAliens.size() < 1) {
			return;
		}
		
		int a = rand.nextInt(remainingAliens.size());
		
		if(remainingAliens.get(a).equals("Co")) {
			alien = new CooperativeAlien();
		} else if(remainingAliens.get(a).equals("Re")) {
			alien = new RepairingAlien();
		} else if(remainingAliens.get(a).equals("Pr")) {
			alien = new ProtectingAlien();
		} else if(remainingAliens.get(a).equals("Su")) {
			createASuprisingAlien();
			return;
		} else {
			alien = new TimeWastingAlien();
		}
		
		//alien = new RepairingAlien();
		
		if(alien instanceof ProtectingAlien) {
			alien.setX((gameWidth - (int) alien.getWidth())/2);
			alien.setY((gameHeight-160));
		} else {
			int px = rand.nextInt(gameWidth - (int) alien.getWidth());
	    	int py = rand.nextInt((gameHeight-300));
	    
			while(checkCollision(px, py, (int) alien.getWidth(),(int) alien.getHeight()) || checkAlienCollision(px, py, (int) alien.getWidth(),(int) alien.getHeight())) {
					
				px = rand.nextInt(gameWidth - (int) alien.getWidth());
			    py = rand.nextInt((gameHeight-300));
					
			}
			
			alien.setX(px);
			alien.setY(py);
		}
		this.gameObjects.add(alien);
		this.aliens.add(alien);
		
	}

	
	public void createASimpleAsteroid() {
		//AsteroidFactory factory = new AsteroidFactory();
		int simpleW = (int)(gameWidth*0.02);
		int simpleH = 20;	
		int[] dimensions = {simpleW,simpleH};
		Random rand = new Random();
		int px = rand.nextInt(gameWidth - dimensions[0]);
    	int py = rand.nextInt((gameHeight-300));
	
    
		while(checkCollision(px, py, dimensions[0], dimensions[1]) || checkAlienCollision(px, py, dimensions[0],dimensions[1])) {
				
			px = rand.nextInt(gameWidth - dimensions[0]);
		    py = rand.nextInt(gameHeight-300);
				
		}
		//Asteroid asteroid = factory.getAsteroid("simple", dimensions , px, py);
		//asteroid = (SimpleAsteroid) asteroid;
		SimpleAsteroid asteroid = new SimpleAsteroid(dimensions[0], dimensions[1],px,py);
		asteroid.setAlienCreated(true);
		this.gameObjects.add((GameObject) asteroid);
		this.asteroids.add((GameObject) asteroid);
		
		
	}

	public boolean createASimpleAsteroidAtLocation(int px, int py) {
		//AsteroidFactory factory = new AsteroidFactory();
		int simpleW = (int)(gameWidth*0.02);
		int simpleH = 20;	
		int[] dimensions = {simpleW,simpleH};
		
    
		if(!checkCollision(px, py, dimensions[0], dimensions[1]) && !checkAlienCollision(px, py, dimensions[0],dimensions[1])) {
			SimpleAsteroid asteroid = new SimpleAsteroid(dimensions[0], dimensions[1],px,py);
			asteroid.setAlienCreated(true);
			this.gameObjects.add((GameObject) asteroid);
			this.asteroids.add((GameObject) asteroid);
			asteroid.setFrozen(true);
			getFrozenAsteroids().add(asteroid);
			asteroid.setBecomeFrozen(true);
			return true;
		}
		//Asteroid asteroid = factory.getAsteroid("simple", dimensions , px, py);
		//asteroid = (SimpleAsteroid) asteroid;
		return false;
		
		
	}

	
	private void checkAlienBallCollision() {
		for(int i = 0; i < aliens.size(); i++) {
			for (Ball ball : balls) {
				if(nonMovingRectangularAsteroidBallCollision(ball,(GameObject) aliens.get(i))) {
					aliens.get(i).hitPerform(this);
				}
			}
		}
		
	}
	
	private void checkAlienWallCollision() {
		
		for(Alien alien: aliens) {
			if(alien instanceof ProtectingAlien || (alien instanceof SuprisingAlien && alien.getAlienBehaviour() instanceof UpperPartHit)) {
				
				if (alien.getX() < 0) {
					alien.setX(0);
					alien.setVx(-alien.getVx());
					
				}
				if (alien.getX() + alien.getWidth() > gameWidth) {
					alien.setX(gameWidth - alien.getWidth());
					alien.setVx(-alien.getVx());
					
				}
			
				
			}
		}
		
	}

	
	private void checkBallWallCollision() {
		ArrayList<Ball> ballsToBeRemoved = new ArrayList<>();
		for (Ball ball : balls) {
			if (ball.getX() < 0) ball.setVx(Math.abs(ball.getVx()));
			if (ball.getX() > gameWidth - 2*ball.getRadius()) ball.setVx(-Math.abs(ball.getVx()));
			if (ball.getY() < 0) ball.setVy(Math.abs(ball.getVy()));
			if (ball.getY() > gameHeight - 2*ball.getRadius()) {
				if (balls.size() == 1) {
					decreaseLives();
					ball.reinitialize(paddle);
				} 
				if (balls.size() > 1) {
					ballsToBeRemoved.add(ball);
					addToWillBeRemoved(ball);
					if (this.ball == ball) {
						originalBallFell = true;
					}
				}
				
			}
		}
		balls.removeAll(ballsToBeRemoved);
		if (originalBallFell) this.ball = balls.get(0);
	}

	public void checkBallPaddleCollision() {
		for (Ball ball : balls) {
			double newXp = paddle.getX() + paddle.getWidth()/2 * (1 - Math.cos(Math.toRadians(paddle.getAngle())));
			double newYp = paddle.getY() - paddle.getWidth()/2 * Math.sin(Math.toRadians(paddle.getAngle()));
			// Rotate axes
			double xpr = rotateX(newXp, newYp, paddle.getAngle()); // x of paddle in rotated axis
			double ypr = rotateY(newXp, newYp, paddle.getAngle()); // y of paddle in rotated axis
			double xbr = rotateX(ball.getX(), ball.getY(), paddle.getAngle()); // x of ball in rotated axis
			double ybr = rotateY(ball.getX(), ball.getY(), paddle.getAngle()); // y of ball in rotated axis
			double vxbr = rotateX(ball.getVx(), ball.getVy(), paddle.getAngle());
			double vybr = rotateY(ball.getVx(), ball.getVy(), paddle.getAngle());
			double vxpr = rotateX(paddle.getVx(), paddle.getVy(), paddle.getAngle());
			double vypr = rotateY(paddle.getVx(), paddle.getVy(), paddle.getAngle());
			
			// If hits top of the paddle
			if (ybr + ball.getRadius() * 2 > ypr 
					&& xbr + ball.getRadius() >= xpr
					&& xbr + ball.getRadius() <= xpr + paddle.getWidth()
					&& ybr < ypr + paddle.getHeight()) { 
				ballPaddleCollided=true;
				
				if(inventory.getMagnet().isInUse() && !Magnet.iswPressed()) {
					Magnet.setBallCatched(true);
					ball.setVx(0);
					ball.setVy(0);
					ball.setX(paddle.getX()+ paddle.getWidth()/2);
					ball.setY(paddle.getY()-ball.getRadius()*2+1);
				}
				else if(inventory.getMagnet().isInUse() && Magnet.iswPressed() && Magnet.isBallCatched()) {
					ball.reinitialize(paddle);
					Magnet.setwPressed(false);
					Magnet.setBallCatched(false);
				}
				else {

	
				vybr = - Math.abs(vybr);
				ybr = ypr - ball.getRadius() * 2 - Math.abs(vypr);
				
				// Rotate back
				double newXb = rotateX(xbr, ybr, -paddle.getAngle());
				double newYb = rotateY(xbr, ybr, -paddle.getAngle());
				double newVx = rotateX(vxbr, vybr, -paddle.getAngle());
				double newVy = rotateY(vxbr, vybr, -paddle.getAngle());
				ball.setX(newXb);
				ball.setY(newYb);
				ball.setVx(newVx);
				ball.setVy(newVy);
	
				paddle.playSound();
	
				}
				
	
			}
			// If hits right side of the paddle
			else if (xbr < xpr + paddle.getWidth() 
					&& xbr + ball.getRadius() > xpr + paddle.getWidth() 
					&& ybr + 2 * ball.getRadius() > ypr
					&& ybr < ypr + paddle.getHeight()) {
				ballPaddleCollided=true;
				
				if(inventory.getMagnet().isInUse() && !Magnet.iswPressed()) {
					ball.setVx(0);
					ball.setVy(0);
					ball.setX(paddle.getX()+ paddle.getWidth()/2);
					ball.setY(paddle.getY()-ball.getRadius()*2+1);
				}
				else if(inventory.getMagnet().isInUse() && Magnet.iswPressed()) {
					ball.reinitialize(paddle);
					Magnet.setwPressed(false);
				}
				else {
				
				vxbr = Math.abs(vxbr); 
				xbr = xpr + paddle.getWidth();
			
				// Rotate back
				double newXb = rotateX(xbr, ybr, -paddle.getAngle());
				double newYb = rotateY(xbr, ybr, -paddle.getAngle());
				double newVx = rotateX(vxbr, vybr, -paddle.getAngle());
				double newVy = rotateY(vxbr, vybr, -paddle.getAngle());
				ball.setX(newXb);
				ball.setY(newYb);
				ball.setVx(newVx);
				ball.setVy(newVy);
	
				paddle.playSound();
	
				
				}	
			}
			// If hits left side of the paddle
				else if (xbr + 2 * ball.getRadius() > xpr 
						&& xbr + ball.getRadius() < xpr
						&& ybr + 2 * ball.getRadius() > ypr
						&& ybr < ypr + paddle.getHeight()) {
					ballPaddleCollided=true;
					
					if(inventory.getMagnet().isInUse() && !Magnet.iswPressed()) {
						ball.setVx(0);
						ball.setVy(0);
						ball.setX(paddle.getX()+ paddle.getWidth()/2);
						ball.setY(paddle.getY()-ball.getRadius()*2+1);
					}
					else if(inventory.getMagnet().isInUse() && Magnet.iswPressed()) {
						ball.reinitialize(paddle);
						Magnet.setwPressed(false);
					}
					else {
					vxbr = - Math.abs(vxbr); 
					xbr = xpr - ball.getRadius() * 2;
	
					// Rotate back
					double newXb = rotateX(xbr, ybr, -paddle.getAngle());
					double newYb = rotateY(xbr, ybr, -paddle.getAngle());
					double newVx = rotateX(vxbr, vybr, -paddle.getAngle());
					double newVy = rotateY(vxbr, vybr, -paddle.getAngle());
					ball.setX(newXb);
					ball.setY(newYb);
					ball.setVx(newVx);
					ball.setVy(newVy);
	
					paddle.playSound();
					}	
				}
				else {
					ballPaddleCollided=false;
				}
		}
	}
	
	public void checkPowerUpPaddleCollision(Powerup powerup) {
		double newXp = paddle.getX() + paddle.getWidth()/2 * (1 - Math.cos(Math.toRadians(paddle.getAngle())));
		double newYp = paddle.getY() - paddle.getWidth()/2 * Math.sin(Math.toRadians(paddle.getAngle()));
		// Rotate axes
		double xpr = rotateX(newXp, newYp, paddle.getAngle()); // x of paddle in rotated axis
		double ypr = rotateY(newXp, newYp, paddle.getAngle()); // y of paddle in rotated axis
		double xbr = rotateX(powerup.getX(), powerup.getY(), paddle.getAngle()); // x of powerup in rotated axis
		double ybr = rotateY(powerup.getX(), powerup.getY(), paddle.getAngle()); // y of powerup in rotated axis
		double vxbr = rotateX(powerup.getVx(), powerup.getVy(), paddle.getAngle());
		double vybr = rotateY(powerup.getVx(), powerup.getVy(), paddle.getAngle());
		double vxpr = rotateX(paddle.getVx(), paddle.getVy(), paddle.getAngle());
		double vypr = rotateY(paddle.getVx(), paddle.getVy(), paddle.getAngle());
		
		// If hits top of the paddle
		if (ybr + powerup.getHeight() > ypr 
				&& xbr + powerup.getHeight() >= xpr
				&& xbr + powerup.getHeight() <= xpr + paddle.getWidth()
				&& ybr < ypr + paddle.getHeight()) { 
			powerup.setY(-100);
			powerup.setVy(0);
			
			inventory.addToInventory(powerup);
			addToWillBeRemoved(powerup);
			if(powerup instanceof CreateAlien) {
				powerup.usePowerUp();
			}
		}
		// If hits right side of the paddle
		else if (xbr < xpr + paddle.getWidth() 
				&& xbr + powerup.getHeight() > xpr + paddle.getWidth() 
				&& ybr + powerup.getHeight() > ypr
				&& ybr < ypr + paddle.getHeight()) {
		
			powerup.setY(-100);
			powerup.setVy(0);
			inventory.addToInventory(powerup);
			addToWillBeRemoved(powerup);
			if(powerup instanceof CreateAlien) {
				powerup.usePowerUp();
			}
			
		}
		// If hits left side of the paddle
			else if (xbr + powerup.getWidth() > xpr 
					&& xbr + powerup.getWidth() < xpr
					&& ybr + powerup.getWidth() > ypr
					&& ybr < ypr + paddle.getHeight()) {
		
				
				powerup.setY(-100);
				powerup.setVy(0);
				inventory.addToInventory(powerup);
				addToWillBeRemoved(powerup);
				if(powerup instanceof CreateAlien) {
					powerup.usePowerUp();
				}
				
				
			}
	}
		
	
	
	public HashMap<Integer, GameObject> getGameObjectIdsToBeRemoved() {
		return gameObjectIdsToBeRemoved;
	}

	public void setGameObjectIdsToBeRemoved(HashMap<Integer, GameObject> gameObjectIdsToBeRemoved) {
		this.gameObjectIdsToBeRemoved = gameObjectIdsToBeRemoved;
	}

	private void checkBallAsteroidCollision(Ball ball, Asteroid asteroid) {
		if(asteroid instanceof SimpleAsteroid || asteroid instanceof GiftAsteroid) {
			if(asteroid.isMoveable() == 0) {
				if(nonMovingRectangularAsteroidBallCollision(ball, (GameObject) asteroid)) {
					asteroid.hitByBall();
				}
			} else {
				
			}
		}
		else if(asteroid instanceof FirmAsteroid) {
			nonMovingCircleAsteroidBallCollision(ball, asteroid);
		}
		else if(asteroid instanceof ExplosiveAsteroid) {
			if(asteroid.isMoveable() == 0) {
				nonMovingCircleAsteroidBallCollision(ball, asteroid);
			} else {
				
			}
			checkIfExploded((ExplosiveAsteroid)asteroid);
		}
		else {
			nonMovingRectangularAsteroidBallCollision(ball, (GameObject) asteroid);
		}
		
	}
	
	public static boolean checkAsteroidAsteroidCollision(GameObject ast1, GameObject ast2) throws IllegalArgumentException {
		// @requires: ast1 != Null, ast2 != Null,ast1.getX() != Null, ast2.getX() != Null,ast1.getY() != Null, ast2.getY() != Null,ast1.getVx() != Null, ast2.getVx() != Null,
		// @requires: ast1.getWidth() != Null, ast2.getWidth() != Null,ast1.getHeight() != Null, ast2.getHeight() != Null,
		// @modifies: ast1.setX(),ast1.setVx(),ast2.setVx();
		// @effects: If ast2.getY()is smaller than 0 or ast1.getY()is smaller than 0 or ast1.getX()is smaller than 0 or ast2.getX()is smaller than 0 or ast.getWidth() or ast.getHeight() is smaller than 1, throws IllegalArgumentException 
		// if ast1.getVx() bigger than 0 which moves right and ast1.getX() + ast1.getWidth() bigger than ast2.getX() and ast2.getY() + ast2.getHeight() smaller than ast1.getY() and ast2.getY() smaller than ast1.getY() + ast1.getHeight() returns true.
		// else if ast1.getVx() smaller than 0 which moves left and ast2.getX() + ast2.getWidth() bigger than ast1.getX() and ast2.getX() smaller than ast1.getX() and ast2.getY() + ast2.getHeight() bigger than ast1.getY() and ast2.getY() smaller than ast1.getY() + ast1.getHeight() returns true.
		// if !ast1.isMovingObject() or ast1 == ast2 returns false
		
		
		//In given objects any of the properties despite Vx, Vy and ismoving gets smaller or equal to 0 give exception
		/*if(ast2.getY() <= 0 || ast2.getX() <= 0 || ast2.getHeight() <= 0 || ast2.getWidth() <= 0 || ast1.getY() <= 0 || ast1.getX() <= 0 || ast1.getHeight() <= 0 || ast1.getWidth() <= 0) {
			throw new IllegalArgumentException("Dimensions and locatins must be bigger than zero");
		}
		*/
		if (ast1.isMovingObject() && ast1 != ast2) {
			// If first object is moving right
			if (ast1.getX() + ast1.getWidth() > ast2.getX()  
					&& ast2.getY() + ast2.getHeight() > ast1.getY() 
					&& ast2.getY() < ast1.getY() + ast1.getHeight() 
					&& ast1.getX() < ast2.getX() 
					&& ast1.getVx() >= 0) {
				ast1.setX(ast2.getX() - ast1.getWidth());
				ast1.setVx(-ast1.getVx());
				ast2.setVx(-ast2.getVx());
				return true;
			}
			// If first object is moving left
			else if (ast2.getX() + ast2.getWidth() > ast1.getX() 
					&& ast2.getX() < ast1.getX()
					&& ast2.getY() + ast2.getHeight() > ast1.getY()
					&& ast2.getY() < ast1.getY() + ast1.getHeight()
					&& ast1.getVx() <= 0) {
				ast1.setX(ast2.getX() + ast2.getWidth());
				ast1.setVx(-ast1.getVx());
				ast2.setVx(-ast2.getVx());
				return true;
			}
		}
		return false;
	}
	
	private boolean checkAsteroidAlienCollision(GameObject ast1, GameObject al) {
		if (ast1.isMovingObject()) {
			// If first object is moving right
			if (ast1.getX() + ast1.getWidth() > al.getX()  
					&& al.getY() + al.getHeight() > ast1.getY() 
					&& al.getY() < ast1.getY() + ast1.getHeight() 
					&& ast1.getX() < al.getX() 
					&& ast1.getVx() > 0) {
				ast1.setX(al.getX() - ast1.getWidth());
				ast1.setVx(-ast1.getVx());
				return true;
			}
			// If first object is moving left
			else if (al.getX() + al.getWidth() > ast1.getX() 
					&& al.getX() < ast1.getX()
					&& al.getY() + al.getHeight() > ast1.getY()
					&& al.getY() < ast1.getY() + ast1.getHeight()
					&& ast1.getVx() < 0) {
				ast1.setX(al.getX() + al.getWidth());
				ast1.setVx(-ast1.getVx());
				return true;
			}
		}
		return false;
	}
	
	
	
	private boolean checkAsteroidWallCollision(GameObject asteroid) {
		if (asteroid.getX() < 0) {
			asteroid.setX(0);
			asteroid.setVx(-asteroid.getVx());
			return true;
		}
		if (asteroid.getX() + asteroid.getWidth() > gameWidth) {
			asteroid.setX(gameWidth - asteroid.getWidth());
			asteroid.setVx(-asteroid.getVx());
			return true;
		}
		return false;
	}
	
	private boolean nonMovingRectangularAsteroidBallCollision(Ball ball, GameObject asteroid) {
		// Left Collision
		double angle = Math.atan(asteroid.getHeight() / asteroid.getWidth());
		if (ball.getX() + 2 * ball.getRadius() > asteroid.getX() 
				&& ball.getX() + 2 * ball.getRadius() < asteroid.getX() + (ball.getY() + ball.getRadius() - asteroid.getY()) / Math.tan(angle)
				&& ball.getX() + 2 * ball.getRadius() < asteroid.getX() + (asteroid.getY() + asteroid.getHeight() - ball.getY() + ball.getRadius()) / Math.tan(angle)
				&& ball.getY() + 0 * ball.getRadius() >= asteroid.getY() 
				&& ball.getY() + 2 * ball.getRadius() < asteroid.getY() + asteroid.getHeight()) {
			ball.setX(asteroid.getX() - 2 * ball.getRadius());
			ball.setVx(-Math.abs(ball.getVx()));	

			//asteroid.setStrength(asteroid.getStrength()-1);
			((GameObject) asteroid).playSound();

			//asteroid.hitByBall();
			

			System.out.println("LEFT");
			return true;
		}
		// Right Collision
		else if (ball.getX() < asteroid.getX() + asteroid.getWidth()
				&& ball.getX() + 0 * ball.getRadius() >= asteroid.getX() + asteroid.getWidth() - (ball.getY() + ball.getRadius() - asteroid.getY()) / Math.tan(angle)
				&& ball.getX() + 0 * ball.getRadius() >= asteroid.getX() + asteroid.getWidth() - (asteroid.getY() + asteroid.getHeight() - ball.getY() + ball.getRadius()) / Math.tan(angle)
				&& ball.getY() + 0 * ball.getRadius() > asteroid.getY() 
				&& ball.getY() + 2 * ball.getRadius() < asteroid.getY() + asteroid.getHeight()) {
			ball.setX(asteroid.getX() + asteroid.getWidth());
			ball.setVx(Math.abs(ball.getVx()));

			//asteroid.setStrength(asteroid.getStrength()-1);
			((GameObject) asteroid).playSound();

			//asteroid.hitByBall();

			System.out.println("RIGHT");
			return true;
		}
		// Top Collision
		else if (ball.getY() + 2 * ball.getRadius() > asteroid.getY()
				&& ball.getY() + ball.getRadius() < asteroid.getY() + (ball.getX() - asteroid.getX()) * Math.tan(angle)
				&& ball.getY() + ball.getRadius() < asteroid.getY() + (asteroid.getX() + asteroid.getWidth() - ball.getX()) * Math.tan(angle)
				&& ball.getX() + 0 * ball.getRadius() > asteroid.getX()
				&& ball.getX() + 2 * ball.getRadius() < asteroid.getX() + asteroid.getWidth()) {
			ball.setY(asteroid.getY() - 2 * ball.getRadius());
			ball.setVy(-Math.abs(ball.getVy()));		

			//asteroid.setStrength(asteroid.getStrength()-1);
			((GameObject) asteroid).playSound();

			//asteroid.hitByBall();

			System.out.println("TOP");
			return true;
		}
		// Bottom Collision
		else if (ball.getY() < asteroid.getY() + asteroid.getHeight()
				&& ball.getY() + ball.getRadius() > asteroid.getY() + asteroid.getHeight() - (ball.getX() - asteroid.getX()) * Math.tan(angle)
				&& ball.getY() + ball.getRadius() > asteroid.getY() + asteroid.getHeight() - (asteroid.getX() + asteroid.getWidth() - ball.getX()) * Math.tan(angle)
				&& ball.getX() + 0 * ball.getRadius() > asteroid.getX()
				&& ball.getX() + 2 * ball.getRadius() < asteroid.getX() + asteroid.getWidth()) {
			ball.setY(asteroid.getY() + asteroid.getHeight());
			ball.setVy(Math.abs(ball.getVy()));

			//asteroid.setStrength(asteroid.getStrength()-1);
			((GameObject) asteroid).playSound();

			//asteroid.hitByBall();

			System.out.println("BOTTOM");
			return true;
		}
		// Top Left Corner Collision
		else if (ball.getX() + 2 * ball.getRadius() > asteroid.getX()
				&& ball.getX() + 2 * ball.getRadius() < asteroid.getX() + asteroid.getWidth() / 2
				&& ball.getY() + 2 * ball.getRadius() > asteroid.getY() 
				&& ball.getY() + 2 * ball.getRadius() < asteroid.getY() + asteroid.getHeight() / 2) {
			if (ball.getVx() > 0 && ball.getVy() > 0) {
				double vbxr = rotateX(ball.getVx(), ball.getVy(), Math.toDegrees(-Math.PI / 4));
				double vbyr = rotateY(ball.getVx(), ball.getVy(), Math.toDegrees(-Math.PI / 4));
				vbyr = -Math.abs(vbyr);
				ball.setVx(rotateX(vbxr, vbyr, Math.toDegrees(Math.PI / 4)));
				ball.setVy(rotateY(vbxr, vbyr, Math.toDegrees(Math.PI / 4)));

				//asteroid.setStrength(asteroid.getStrength()-1);
				((GameObject) asteroid).playSound();

				//asteroid.hitByBall();

				System.out.println("TOP LEFT CORNER");
				return true;
			}
			else if (ball.getVx() < 0 && ball.getVy() > 0) {
				ball.setY(asteroid.getY() - 2 * ball.getRadius());
				ball.setVy(-Math.abs(ball.getVy()));	

				//asteroid.setStrength(asteroid.getStrength()-1);
				((GameObject) asteroid).playSound();

				//asteroid.hitByBall();
				return true;

			}
			else if (ball.getVx() > 0 && ball.getVy() < 0) {
				ball.setX(asteroid.getX() - 2 * ball.getRadius());
				ball.setVx(-Math.abs(ball.getVx()));	

				//asteroid.setStrength(asteroid.getStrength()-1);
				((GameObject) asteroid).playSound();

				//asteroid.hitByBall();
				return true;

			}
		}
		// Top Right Corner Collision
		else if (ball.getX() < asteroid.getX() + asteroid.getWidth()
				&& ball.getX() > asteroid.getX() + asteroid.getWidth() / 2
				&& ball.getY() + 2 * ball.getRadius() > asteroid.getY()
				&& ball.getY() + 2 * ball.getRadius() < asteroid.getY() + asteroid.getHeight() / 2) {
			if (ball.getVx() < 0 && ball.getVy() > 0) {
				double vbxr = rotateX(ball.getVx(), ball.getVy(), Math.toDegrees(Math.PI / 4));
				double vbyr = rotateY(ball.getVx(), ball.getVy(), Math.toDegrees(Math.PI / 4));
				vbyr = -Math.abs(vbyr);
				ball.setVx(rotateX(vbxr, vbyr, Math.toDegrees(-Math.PI / 4)));
				ball.setVy(rotateY(vbxr, vbyr, Math.toDegrees(-Math.PI / 4)));

				//asteroid.setStrength(asteroid.getStrength()-1);
				((GameObject) asteroid).playSound();

				//asteroid.hitByBall();

				System.out.println("TOP RIGHT CORNER");
				return true;
			}
			else if (ball.getVx() < 0 && ball.getVy() < 0) {
				ball.setX(asteroid.getX() + asteroid.getWidth());
				ball.setVx(Math.abs(ball.getVx()));

				//asteroid.setStrength(asteroid.getStrength()-1);
				((GameObject) asteroid).playSound();

				//asteroid.hitByBall();
				return true;

			}
			else if (ball.getVx() > 0 && ball.getVy()  > 0) {
				ball.setY(asteroid.getY() - 2 * ball.getRadius());
				ball.setVy(-Math.abs(ball.getVy()));		

				//asteroid.setStrength(asteroid.getStrength()-1);
				((GameObject) asteroid).playSound();

				//asteroid.hitByBall();
				return true;

			}
		}
		// Bottom Left Corner Collision
		else if (ball.getX() + 2 * ball.getRadius() > asteroid.getX()
				&& ball.getX() + 2 * ball.getRadius() < asteroid.getX() + asteroid.getWidth()
				&& ball.getY() > asteroid.getY() + asteroid.getHeight() / 2
				&& ball.getY() < asteroid.getY() + asteroid.getHeight()) {
			if (ball.getVx() > 0 && ball.getVy() < 0) {
				double vbxr = rotateX(ball.getVx(), ball.getVy(), Math.toDegrees(Math.PI / 4));
				double vbyr = rotateY(ball.getVx(), ball.getVy(), Math.toDegrees(Math.PI / 4));
				vbyr = Math.abs(vbyr);
				ball.setVx(rotateX(vbxr, vbyr, Math.toDegrees(-Math.PI / 4)));
				ball.setVy(rotateY(vbxr, vbyr, Math.toDegrees(-Math.PI / 4)));

				//asteroid.setStrength(asteroid.getStrength()-1);
				((GameObject) asteroid).playSound();

				//asteroid.hitByBall();

				System.out.println("BOTTOM LEFT CORNER");
				return true;
			}
			else if (ball.getVx() > 0 && ball.getVy() > 0) {
				ball.setX(asteroid.getX() - 2 * ball.getRadius());
				ball.setVx(-Math.abs(ball.getVx()));	

				//asteroid.setStrength(asteroid.getStrength()-1);
				((GameObject) asteroid).playSound();

				//asteroid.hitByBall();
				return true;

			}
			else if (ball.getVx() < 0 && ball.getVy() < 0) {
				ball.setY(asteroid.getY() + asteroid.getHeight());
				ball.setVy(Math.abs(ball.getVy()));

				//asteroid.setStrength(asteroid.getStrength()-1);
				((GameObject) asteroid).playSound();

				//asteroid.hitByBall();
				return true;

			}
		}
		// Bottom Right Corner Collision
		else if (ball.getX() < asteroid.getX() + asteroid.getWidth()
				&& ball.getX() > asteroid.getX() + asteroid.getWidth() / 2
				&& ball.getY() < asteroid.getY() + asteroid.getHeight()
				&& ball.getY() > asteroid.getY() + asteroid.getHeight() / 2) {
			if (ball.getVx() < 0 && ball.getVy() < 0) {
				double vbxr = rotateX(ball.getVx(), ball.getVy(), Math.toDegrees(-Math.PI / 4));
				double vbyr = rotateY(ball.getVx(), ball.getVy(), Math.toDegrees(-Math.PI / 4));
				vbyr = Math.abs(vbyr);
				ball.setVx(rotateX(vbxr, vbyr, Math.toDegrees(Math.PI / 4)));
				ball.setVy(rotateY(vbxr, vbyr, Math.toDegrees(Math.PI / 4)));

				//asteroid.setStrength(asteroid.getStrength()-1);
				((GameObject) asteroid).playSound();

				//asteroid.hitByBall();

				System.out.println("BOTTOM RIGHT CORNER");
				return true;
			}
			else if (ball.getVx() > 0 && ball.getVy() < 0) {
				ball.setY(asteroid.getY() + asteroid.getHeight());
				ball.setVy(Math.abs(ball.getVy()));

				//asteroid.setStrength(asteroid.getStrength()-1);
				((GameObject) asteroid).playSound();

				//asteroid.hitByBall();

				System.out.println("BOTTOM");
				return true;
			}
			else if (ball.getVx() < 0 && ball.getVy() > 0) {
				ball.setX(asteroid.getX() + asteroid.getWidth());
				ball.setVx(Math.abs(ball.getVx()));

				//asteroid.setStrength(asteroid.getStrength()-1);
				((GameObject) asteroid).playSound();

				//asteroid.hitByBall();
				return true;

			}
		}

		return false;
		

	}
	
	public double getDelay() {
		return delay;


	}
	
	private void nonMovingCircleAsteroidBallCollision(Ball ball, Asteroid asteroid) {
		/*
		double ballCenterX = ball.getX() + ball.getRadius();
		double ballCenterY = ball.getY() + ball.getRadius();
		double asteroidCenterX = asteroid.getX() + asteroid.getDimensions()[0]/2;
		double asteroidCenterY = asteroid.getY() + asteroid.getDimensions()[0]/2;
		
		if(Math.sqrt(Math.pow(ballCenterX-asteroidCenterX, 2) + Math.pow(ballCenterX-asteroidCenterX, 2)) <= ball.getRadius()+asteroid.getDimensions()[0]/2) {
			ball.setVx(-ball.getVx());
			ball.setVy(-ball.getVy());
			asteroid.setStrength(asteroid.getStrength()-1);
		}
		*/
		double xb = ball.getX() + ball.getRadius();
		double yb = ball.getY() + ball.getRadius();
		double xa = asteroid.getX() + asteroid.getWidth() / 2;
		double ya = asteroid.getY() + asteroid.getWidth() / 2;
		
		double angle = Math.toDegrees(Math.atan((ya - yb) / (xa - xb)));
		//double angle = Math.toDegrees(Math.atan((xb - xa) / (yb - ya)));
		//System.out.println(Math.toDegrees(angle));
		//angle = Math.PI - angle;
		double dist = Math.sqrt(Math.pow((xb - xa), 2) + Math.pow((yb - ya), 2));
		if (dist < asteroid.getWidth() / 2 + ball.getRadius()) {
			// Rotate axes
			double xbr = rotateX(xb, yb, angle);
			double ybr = rotateY(xb, yb, angle);
			double vxbr = rotateX(ball.getVx(), ball.getVy(), angle);
			double vybr = rotateY(ball.getVx(), ball.getVy(), angle);
			
			xbr = rotateX(xa, ya, angle) + asteroid.getWidth() / 2;
			vxbr = - vxbr;
			
			// Rotate back
			//ball.setX(rotateX(xbr, ybr, -angle));
			//ball.setY(rotateY(xbr, ybr, -angle));
			ball.setVx(rotateX(vxbr, vybr, -angle));
			ball.setVy(rotateY(vxbr, vybr, -angle));
			asteroid.hitByBall();
		}

		
	}
	
	private void checkIfExploded(ExplosiveAsteroid asteroid) {
		if(asteroid.getStrength() <= 0) {
			double asteroidCenterX = asteroid.getX() + asteroid.getDimensions()[0]/2;
			double asteroidCenterY = asteroid.getY() + asteroid.getDimensions()[1]/2;
			for(GameObject gameObject: gameObjects) {
				if(!asteroid.equals(gameObject) && gameObject instanceof Asteroid) {
					Asteroid asteroid2 = (Asteroid)gameObject;
					if(intersects(asteroidCenterX,asteroidCenterY,150,gameObject.getX(),gameObject.getY(),gameObject.getWidth(),gameObject.getHeight())) {
						if(!((GameObject)asteroid2).isFrozen()) {
							asteroid2.setStrength(0);
						}
					}
				}
			}
		}
	}
	
	
	public static boolean intersects(double cx, double cy, double radius, double rx, double ry, double rwidth, double rheight) throws IllegalArgumentException
	{
		// @requires: radius > 0, rwidth > 0, rheight > 0 
		// @modifies:
		// @effects: If radius or rwidth or rheight is smaller than 1, throws IllegalArgumentException 
		// Else if absolute difference between cx and rx is bigger than summation of rwidth/2 and radius, returns false (no intersection). Otherwise, returns true.
		// Else if absolute difference between cy and ry is bigger than summation of rheight/2 and radius, returns false (no intersection). Otherwise, returns true. 
	    // Else if there is a corner collision returns true. Otherwise returns false. 
		
		if(radius <= 0 || rwidth <= 0 || rheight <= 0) {
			throw new IllegalArgumentException();
		}

		double circleDistancex = Math.abs(cx - rx);
	    double circleDistancey = Math.abs(cy - ry);

	    if (circleDistancex > (rwidth/2 + radius)) { return false; }
	    if (circleDistancey > (rheight/2 + radius)) { return false; }

	    if (circleDistancex <= (rwidth/2)) { return true; } 
	    if (circleDistancey <= (rheight/2)) { return true; }

	    double cornerDistance_sq = Math.pow((circleDistancex - rwidth/2),2) +
	                         Math.pow((circleDistancey - rheight/2),2);
	    
	    return (cornerDistance_sq <= Math.pow(radius,2));
	}
	
	public void clearAsteroidArray() {
		ArrayList<GameObject> deleteArray = new ArrayList<>();
		for (int i = 0; i < asteroids.size(); i++) {
			if(((Asteroid)asteroids.get(i)).getStrength() < 1) {
				deleteArray.add(asteroids.get(i));
			}
		}
		asteroids.removeAll(deleteArray);
	}
	
	public void clearGameObjectArray() {
		ArrayList<GameObject> deleteArray = new ArrayList<>();
		for (int i = 0; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof Asteroid && ((Asteroid)gameObjects.get(i)).getStrength() < 1) {
				deleteArray.add(gameObjects.get(i));
			}
		}
		gameObjects.removeAll(deleteArray);
	}
	
	/**
	 * 
	 * @param x -> x value in the current axis
	 * @param y -> y value in the current axis
	 * @param angle -> amount of angle to rotate the axis (in degrees, not radians)
	 * @return returns x value in the rotated axis
	 */
	public double rotateX(double x, double y, double angle) {
		return x * Math.cos(Math.toRadians(angle)) + y * Math.sin(Math.toRadians(angle)); 
	}
	/**
	 * 
	 * @param x -> x value in the current axis
	 * @param y -> y value in the current axis
	 * @param angle -> amount of angle to rotate the axis (in degrees, not radians)
	 * @return returns y value in the rotated axis
	 */
	public double rotateY(double x, double y, double angle) {
		return -x * Math.sin(Math.toRadians(angle)) + y * Math.cos(Math.toRadians(angle));
	}
	
	private void checkPaddleWallCollision() {
		if (paddle.getX() < 0) { // left wall collision
			if (inventory.getWrap().isInUse()) {
				paddle.setX(gameWidth - paddle.getWidth());
				
				System.out.println("LEFT WALL COlls");
			}
			else {
				paddle.setX(0);
			}
		}
		if (paddle.getX() + paddle.getWidth() > gameWidth) { // right wall collision
			if (inventory.getWrap().isInUse()) {
				paddle.setX(0);
			}
			else {
				paddle.setX(gameWidth - paddle.getWidth());
			}
		}
	}
	
	public void decreaseLives() {
		this.lives--;
		if (lives <= 0) {
			this.isGameLost = true;
		}
	}
	
	public void muteGameMusic() {
		themeMusic.stop();
	}
	
	public void playGameMusic() {
		themeMusic.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void setPaddleDirection(String desicion) {
		
		if(desicion.equals("left pressed") && (paddle.getX() >= paddle.getV()*delay/1000.0)) {
			paddle.setLeft(true);
			paddle.setRight(false);
			paddle.setDisplacement(0);
		}
		else if (desicion.equals("right pressed") && (paddle.getX()+paddle.getV()*delay/1000.0) <= gameWidth-paddle.getWidth() ){
			paddle.setRight(true);
			paddle.setLeft(false);
			paddle.setDisplacement(0);
		} 
		else if (desicion.equals("right released")) {
			paddle.setRight(false);
		} 
		else if (desicion.equals("left released")) {
			paddle.setLeft(false);
		}
		else {
			paddle.setLeft(false);
			paddle.setRight(false);
		}
	}
	
	public void setPaddleVelocitySetting(String str) {
		paddle.setVelocitySetting(str);
	}
	
	public void setPaddleRotationDirection(String desicion) {
		
		if(desicion.equals("clockwise")) {
			paddle.setClockwise(true);
			//paddle.setCounterclockwise(false);
		}
		else if(desicion.equals("counterclockwise")){
			paddle.setCounterclockwise(true);
			//paddle.setClockwise(false);
		}
		else if(desicion.equals("clockwise released")){
			paddle.setClockwise(false);
		}
		else if(desicion.equals("counterclockwise released")){
			paddle.setCounterclockwise(false);
		}

	}
	public double getPaddleWidth() {
		return paddle.getWidth();
	}
	
	public void setPaddleWidth(int width) {
		paddle.setWidth(width/10);
	}
	
	public void updateGameObjectsForGameSize(double width, double height) {
		double heightScale = (height - 100) / (double) gameHeight;
		double widthScale = width / (double) gameWidth;
		
		/*
		paddle.setWidth(width / 10);
		paddle.setX(widthScale * paddle.getX());
		paddle.setY(heightScale * paddle.getY()); 
		
		ball.setX(widthScale * ball.getX());
		ball.setY(heightScale * ball.getY());

		
		for (GameObject asteroid : asteroids) {
			asteroid.setX(heightScale * asteroid.getX());
			asteroid.setY(widthScale * asteroid.getY());

			if (((Asteroid) asteroid).getType().equals("Simple")) {
				asteroid.setWidth(paddle.getWidth() / 5);

			}
		}
		*/
		
		for (GameObject gameObject : gameObjects) {
			
			gameObject.setX(widthScale * gameObject.getX());
			gameObject.setY(heightScale * gameObject.getY());
			gameObject.setWidth(widthScale * gameObject.getWidth());
			gameObject.setHeight(widthScale * gameObject.getHeight());
			gameObject.setVx(widthScale * gameObject.getVx());
			gameObject.setVy(heightScale * gameObject.getVy());
			if (gameObject instanceof Ball) {
				((Ball) gameObject).setRadius(widthScale * ((Ball) gameObject).getRadius());
				
			}
			if (gameObject instanceof ExplosiveAsteroid) {
				gameObject.setWidth(gameObject.getHeight());
			}
		}
		
		gameWidth = (int) width;
		gameHeight = (int) height - 100;
	}
	
	public int[] getPaddleXPoints() {
		return paddle.getXPoints();
	}
	
	public int[] getPaddleYPoints() {
		return paddle.getYPoints();
	}

	public ArrayList<GameObject> getAsteroids() {
		return asteroids;
	}

	public void setAsteroids(ArrayList<GameObject> asteroids) {
		this.asteroids = asteroids;
	}

	
	public int getLives() {
		return lives;
	}



	public boolean isCanCreateCoAlien() {
		return canCreateCoAlien;
	}

	public void setCanCreateCoAlien(boolean canCreateCoAlien) {
		this.canCreateCoAlien = canCreateCoAlien;
	}

	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}
	public ArrayList<GameObject> getFrozenAsteroids() {
		return frozenAsteroids;
	}

	public boolean isRunFrozen() {
		return isRunFrozen;
	}

	public void setRunFrozen(boolean isAsteroidFrozen) {
		this.isRunFrozen = isAsteroidFrozen;
	}
	

	public boolean isPause() {
		return isPause;
	}


	public void setPause(boolean isPause) {
		this.isPause = isPause;
	}

	public boolean isGameLost() {
		return isGameLost;
	}

	public void setGameLost(boolean isGameLost) {
		this.isGameLost = isGameLost;
	}

	public void increaseLives() {
		// TODO Auto-generated method stub
		this.lives++;
	}

	public int getScreenHeight() {
		// TODO Auto-generated method stub
		return screenHeight;
	}

	public int getScreenWidth() {
		// TODO Auto-generated method stub
		return screenWidth;
	}
	
	public Paddle getPaddle() {
		return paddle;
	}
	
	public void setPaddle(Paddle paddle) {
		this.paddle=paddle;
	}


	public boolean isBallPaddleCollided() {
		return ballPaddleCollided;
	}

	public ArrayList<Ball> getBalls() {
		return balls;
	}

	public void setBalls(ArrayList<Ball> balls) {
		this.balls = balls;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
	public Inventory getInventory() {
	return inventory;
	}
	
	public long getCurrentTime() {
		return System.currentTimeMillis();
	}
	
	public long getStartTime() {
		if(start==0L) {
			start=System.currentTimeMillis();
		}
		return start;
		
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}


	public int getScore() {
		return score;
	}

	
	
}
