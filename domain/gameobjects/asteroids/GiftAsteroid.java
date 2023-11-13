package domain.gameobjects.asteroids;

import application.models.GameModel;
import domain.gameobjects.GameObject;
import domain.gameobjects.powerups.Chance;
import domain.gameobjects.powerups.CreateAlien;
import domain.gameobjects.powerups.DestructiveLaserGun;
import domain.gameobjects.powerups.GangOfBalls;
import domain.gameobjects.powerups.Magnet;
import domain.gameobjects.powerups.Powerup;
import domain.gameobjects.powerups.TallerPaddle;
import domain.gameobjects.powerups.Wrap;

public class GiftAsteroid extends GameObject implements Asteroid  {
	
	private int[] dimensions = {0, 0};
	private int strength = 1;
	private int isMoving = 0;
	private Powerup powerup;
	private boolean giftDropped = false;
	
	
	
	public GiftAsteroid(int length, int thickness, double x, double  y) {
		type = "Gift";
		double rand = Math.random();
		if (rand < 1.0/10) {
			this.powerup = new Chance(this);
		}
		else if (rand < 2.0/10){
			this.powerup = new TallerPaddle(this);
		}
		else if (rand < 3.0/10){
			this.powerup = new GangOfBalls(this);
		}
		else if (rand < 4.0/10){
			this.powerup = new DestructiveLaserGun(this);
		}
		else if (rand < 5.0/10) {
			this.powerup = new Wrap(this);
		}
		else if (rand < 9.0/10) {
			this.powerup = new CreateAlien(this);
		}
		else {
			this.powerup= new Magnet(this);
		}
		
		this.id = GameObject.generateId();
		this.x = x;
		this.y = y;
		this.width = length;
		this.height = thickness;
		this.movingObject = false;
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

	public void spawnPowerup() {
		if (!giftDropped) {
			powerup.spawnPowerup();
			giftDropped = true;
		}
	}
	
	public Powerup getPowerup() {
		return powerup;
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

	@Override
	public void hitByBall() {
		// TODO Auto-generated method stub
		if(!this.isFrozen()) {
			this.setStrength(this.getStrength()-1);
		}
		
	}

	public boolean isGiftDropped() {
		// TODO Auto-generated method stub
		return giftDropped;
	}
	
	
}
