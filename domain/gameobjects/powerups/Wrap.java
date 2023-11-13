package domain.gameobjects.powerups;

import application.models.GameModel;
import domain.gameobjects.GameObject;
import domain.gameobjects.Paddle;
import domain.gameobjects.asteroids.Asteroid;

public class Wrap extends Powerup{
	
	private boolean inUse;
	
	public Wrap(GameObject giftAsteroid) {
		super(giftAsteroid);
		this.isCollectable = true;
		inUse = false;
		width = 20;
		height = 20;
	}
	

	@Override
	public void usePowerUp() {
		// TODO Auto-generated method stub
		
		inUse = true;
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	timeoutPowerUp();
		            	inUse = false;
		            }
		        }, 
		        120 * 1000 
		);
		
		
	}

	

	@Override
	public void timeoutPowerUp() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isInUse() {
		return inUse;
	}

	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	
}
