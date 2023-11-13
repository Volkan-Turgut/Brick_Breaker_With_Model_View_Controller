package domain.gameobjects.asteroids;

import domain.gameobjects.GameObject;

public class FirmAsteroid extends GameObject implements Asteroid  {
	
	String type;
	private int strength;
	int[] dimensions = {0,0};
	private int isMoving;
	private int radius;
	
	public FirmAsteroid(int lenght,int thicknes,double x,double y) {
		this.id = GameObject.generateId();
		this.type = "Firm";
		this.x = x;
		this.y = y;
		int rand = (int) ((Math.random() * (10 - 0)) + 0);
		this.strength = rand;
		this.radius = 10+this.strength; 
		this.dimensions[0] = radius;
		this.dimensions[1] = radius;
		this.width = dimensions[0];
		this.height = dimensions[1];
		this.isMoving = 1;
	}

	@Override
	public int getStrength() {
		// TODO Auto-generated method stub
		return this.strength;
	}

	@Override
	public int isMoveable() {
		// TODO Auto-generated method stub
		return this.isMoving;
	}

	@Override
	public int[] getDimensions() {
		// TODO Auto-generated method stub
		return this.dimensions;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return this.type;
	}

	@Override
	public void setStrength(int strength) {
		// TODO Auto-generated method stub
		this.strength=strength;
		
	}

	@Override
	public void setDimensions(int[] dimensions) {
		// TODO Auto-generated method stub
		this.dimensions[0]-=1;
		this.dimensions[1]-=1;
	}

	@Override
	public void update() {
		setRadius(radius);
		if(strength <= 0) {
			this.x = -50;
			this.y = -50;
		}
		this.dimensions[0] = radius;
		this.width = dimensions[0];
		this.height = dimensions[0];
		if(strength  <= 0) {
			x = -50;
			y = -50;
		}
		
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = 10+this.strength;

	}

	@Override
	public void setVx(double vx) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public void setIsMoving(int i) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void hitByBall() {
		// TODO Auto-generated method stub
		if(!this.isFrozen()) {
			this.setStrength(this.getStrength()-1);
		}
		
	}

	public String toString() {
		return "Firm Asteroid";
	}
	

	
	

	

}
