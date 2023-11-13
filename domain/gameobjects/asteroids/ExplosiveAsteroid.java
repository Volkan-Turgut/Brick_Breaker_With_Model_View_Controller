package domain.gameobjects.asteroids;

import domain.gameobjects.GameObject;

public class ExplosiveAsteroid extends GameObject implements Asteroid {
	
	private int[] dimensions = {10,10};
	private int strength;
	private int isMoving; 
	
	public ExplosiveAsteroid(int radius,int thickness,double x,double y) {
		this.id = GameObject.generateId();
		this.x=x;
		this.y=y;
		this.type = "Explosive";
		this.dimensions[0] = 10;
		this.width = dimensions[0];
		this.height = dimensions[0];
		this.strength = 1;
		this.isMoving = 0;
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
	public void setStrength(int strength) {
		// TODO Auto-generated method stub
		this.strength = strength;
	}



	@Override
	public void setDimensions(int[] dimensions) {
		// TODO Auto-generated method stub
		this.dimensions = dimensions;
	}



	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(strength <= 0) {
			this.x = -50;
			this.y = -50;
		}
		
	}



	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
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
		return "ExplosiveAsteroid";
	}
	
}

