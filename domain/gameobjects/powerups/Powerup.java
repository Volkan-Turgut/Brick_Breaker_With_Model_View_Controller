package domain.gameobjects.powerups;

import application.models.GameModel;
import domain.gameobjects.GameObject;
import domain.gameobjects.asteroids.Asteroid;

public abstract class Powerup extends GameObject{
	protected GameObject giftAsteroid;
	protected boolean isCollectable;
	static int count = 0;
	
	
	//BufferedImage image?
	public abstract void usePowerUp();
	public abstract void timeoutPowerUp();
	public Powerup(GameObject giftAsteroid) {
		this.giftAsteroid = giftAsteroid;
		this.id = GameObject.generateId();
		this.isCollectable = false;
		width = 20;
		height = 20;
		count++;
		System.out.printf("Powerup Count: %d\n", count);
		setVx(0);
		//setVy(gameModel.getPaddleWidth()*gameModel.getDelay()/4000);
		setVy(2);
		setX(giftAsteroid.getX());
		setY(giftAsteroid.getY());
	}
	
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		y += vy;
	}
	
	public void spawnPowerup() {
		this.x = giftAsteroid.getX();
		this.y = giftAsteroid.getY();
		GameModel.getInstance().getGameObjects().add(this);
		
	}
	
	public boolean isCollectable() {
		return isCollectable;
	}
	public void setCollectable(boolean isCollectable) {
		this.isCollectable = isCollectable;
	}
	
	
}
