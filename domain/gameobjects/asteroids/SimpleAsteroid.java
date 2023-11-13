package domain.gameobjects.asteroids;

import domain.gameobjects.GameObject;

public class SimpleAsteroid extends GameObject implements Asteroid  {

	private int[] dimensions = {0, 0};
	private int strength = 1;
	private int isMoving = 0;

	private boolean alienCreated = false;


	String soundFileName;




	public SimpleAsteroid(int length, int thickness, double x, double  y) {
		this.id = GameObject.generateId();
		this.x = x;
		this.y = y;
		type = "Simple";
		this.width = length;
		this.height = thickness;
		if (Math.random() <0.9) {
			movingObject = false;
		}
		this.soundFileName = "Meteorite.wav";
	}
	
	@Override
	public int getStrength() {
		// TODO Auto-generated method stub
		return strength;
	}

	@Override
	public int isMoveable() {
		// TODO Auto-generated method stub
		return isMoving;
	}

	@Override
	public int[] getDimensions() {
		// TODO Auto-generated method stub
		int[] dimensions = {(int)width,(int)height};
		return dimensions;
	}


	


	public void setIsMoving(int isMoving) {
		this.isMoving = isMoving;
	}


	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void setStrength(int strength) {
		this.strength = strength;
		//playSound();
	}

	@Override
	public void setDimensions(int[] dimensions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(strength <= 0) {
			this.x = -50;
			this.y = -50;
		}
		else {
			this.x += vx;
		}
		
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void changeDirection() {
		this.vx = -this.vx;
	}
	public double getVx() {
		return vx;
	}


	public void setVx(double vx) {
		this.vx = vx;
	}
	
	@Override
	public String toString() {
		return "Simple Asteroid";
	}


	
	public boolean isAlienCreated() {
		return alienCreated;
	}

	public void setAlienCreated(boolean alienCreated) {
		this.alienCreated = alienCreated;
	}
	@Override
	public void hitByBall() {
		// TODO Auto-generated method stub
		if(!this.isFrozen()) {
			this.setStrength(this.getStrength()-1);
		}
		
	}

	
	
	
}
