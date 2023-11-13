package application.models;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import domain.gameobjects.GameObject;
import domain.gameobjects.asteroids.Asteroid;
import domain.gameobjects.asteroids.AsteroidFactory;

public class BuildModel {
	
	private int ScreenWidth;
	private int ScreenHeight;
	private HashMap<String,Integer> asteroidNumbers = new HashMap<String,Integer>();
	//private ArrayList<Asteroid> asteroids = new ArrayList<>();
	private ArrayList<GameObject> asteroids = new ArrayList<>();
	private AsteroidFactory asteroidFactory;
	
	private boolean finish = false;
	GameObject targetAsteroid;
	

	
	public BuildModel(int ScreenWidth, int ScreenHeight) {
		this.ScreenWidth = (ScreenWidth - (int)(ScreenWidth*0.02)) - 16;
		this.ScreenHeight = (ScreenHeight-200)-93;
		this.asteroidFactory = new AsteroidFactory();
		
	}
	
	
	
	
	

	
	public void createAsteroids() {
		asteroids.clear();
		Random rand = new Random();
		int simpleW = (int)(ScreenWidth*0.02);
		int simpleH = 20;		
		for (String name: asteroidNumbers.keySet()) {
		    int value = asteroidNumbers.get(name);
	
		    if (name.equals("simple")) {
			    for (int i=1; i<=value ; i++) {
			    	int px = rand.nextInt(ScreenWidth);
			    	int py = rand.nextInt(ScreenHeight);
			    	
			    	while(checkCollision(px, py, simpleW  , simpleH)) {
			    		px = rand.nextInt(ScreenWidth);
			    		py = rand.nextInt(ScreenHeight);
			    	}
			    	
			    	
					int[] dimensions = {simpleW,simpleH};
					Asteroid asteroid = asteroidFactory.getAsteroid("simple", dimensions ,px,py);
					asteroids.add((GameObject) asteroid);
		    	
			    }
		    	
		    } else if (name.equals("firm")) {
		    	
			    for (int i=1; i<=value ; i++) {
			    	int px = rand.nextInt(ScreenWidth);
			    	int py = rand.nextInt(ScreenHeight);
			    	
			    	while(checkCollision(px, py, simpleW  , simpleH)) {
			    		px = rand.nextInt(ScreenWidth);
			    		py = rand.nextInt(ScreenHeight);
			    	}
			    	
			    	
					int[] dimensions = {simpleW,simpleH};
					Asteroid asteroid = asteroidFactory.getAsteroid("firm", dimensions ,px,py);
					asteroids.add((GameObject) asteroid);
		    	
			    }
		    	
		    } else if (name.equals("explosive")) {
			    for (int i=1; i<= value ; i++) {
			    	int px = rand.nextInt(ScreenWidth);
			    	int py = rand.nextInt(ScreenHeight);
			    	
			    	while(checkCollision(px, py, simpleW  , simpleH)) {
			    		px = rand.nextInt(ScreenWidth);
			    		py = rand.nextInt(ScreenHeight);
			    	}
			    	
			    	
					int[] dimensions = {simpleW,simpleH};
					Asteroid asteroid = asteroidFactory.getAsteroid("explosive", dimensions ,px,py);
					asteroids.add((GameObject) asteroid);
		    	
			    }
		    	
		    } else if (name.equals("gift")) {
		    	
			    for (int i=1; i<=value ; i++) {
			    	int px = rand.nextInt(ScreenWidth);
			    	int py = rand.nextInt(ScreenHeight);
			    	
			    	while(checkCollision(px, py, simpleW  , simpleH)) {
			    		px = rand.nextInt(ScreenWidth);
			    		py = rand.nextInt(ScreenHeight);
			    	}
			    	
			    	
					int[] dimensions = {simpleW,simpleH};
					Asteroid asteroid = asteroidFactory.getAsteroid("gift", dimensions ,px,py);
					asteroids.add((GameObject) asteroid);
		    	
			    }
		    }
		    
		}
			
			
		    
		

		
	}
	
	public boolean checkCollision(int x, int y, int w, int h) {
		Rectangle newbrick = new Rectangle(x, y, w, h);
		
		for (GameObject gameObject : asteroids) {
			Rectangle oldbrick = new Rectangle((int)gameObject.getX(), (int)gameObject.getY(),w, h);
		       if (oldbrick.intersects(newbrick)) {

		          return true;  
		        }
			
		}
		return false;
	}
	
	public void run() {
		
		for(GameObject item : asteroids) {
			item.update();
		}
	}

	public boolean isAsteroid(int mouseX, int mouseY) {
		
		for(GameObject object: asteroids) {
			if( (object.getX() <= mouseX) && (mouseX <= object.getX() + object.getWidth()) && (object.getY() <= mouseY) && (mouseY <= object.getY() + object.getHeight())) {			
				
				targetAsteroid = object;
				//asteroids.remove(object);
				return true;
					
			}	
				
			}
		return false;
		
	}
	


	
	public void changeTargetLoc(int mouseX, int mouseY) {
		//targetAsteroid.setX(mouseX);
		//targetAsteroid.setY(mouseY);
		if(!checkTargetCollision(mouseX,mouseY)) {
			targetAsteroid.setX(mouseX);
			targetAsteroid.setY(mouseY);
		}

	}



	public HashMap<String, Integer> getAsteroidNumbers() {
		return asteroidNumbers;
	}

	public void setAsteroidNumbers(HashMap<String, Integer> asteroidNumbers) {
		this.asteroidNumbers = (HashMap<String, Integer>) asteroidNumbers.clone();
		createAsteroids();
	}
	
	public ArrayList<GameObject> getAsteroids() {
		return asteroids;
	}


	public synchronized boolean isFinish() {
		return finish;
	}


	public synchronized void setFinish(boolean finish) {
		this.finish = finish;
	}
	

	
	public GameObject getTargetAsteroid() {
		return targetAsteroid;
	}

	public void setTargetAsteroid(GameObject targetAsteroid) {
		this.targetAsteroid = targetAsteroid;
	}



	public boolean checkTargetCollision(int mouseX, int mouseY) {
		Rectangle newbrick = new Rectangle(mouseX,mouseY,(int)targetAsteroid.getWidth(),(int)targetAsteroid.getHeight());
		
		for (GameObject gameObject : asteroids) {
			Rectangle oldbrick = new Rectangle((int)gameObject.getX(), (int)gameObject.getY(),(int)gameObject.getWidth(),(int)gameObject.getHeight());
		       if (oldbrick.intersects(newbrick) && (gameObject!=targetAsteroid)) {
		    	  System.out.println("Selected place collide with other object reselect ro replace");
		          return true;  
		        }
			
		}
		return false;
	}

	

}
