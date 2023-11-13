package domain.gameobjects.powerups;


import application.models.GameModel;
import domain.gameobjects.GameObject;
import domain.gameobjects.Paddle;
import domain.gameobjects.asteroids.Asteroid;

public class TallerPaddle extends Powerup{
	private boolean inUse;
	
	public TallerPaddle(GameObject giftAsteroid) {
		super(giftAsteroid);
		this.isCollectable = true;
		inUse = false;
		width = 20;
		height = 20;
	}

	@Override
	public void usePowerUp() {
		// TODO Auto-generated method stub.
		GameModel.getInstance().getPaddle().setWidth(GameModel.getInstance().getPaddle().getWidth()*2);
		inUse = true;
		
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	timeoutPowerUp();
		            	inUse = false;
		            }
		        }, 
		        30 * 1000  
		);
		
	}

	

	@Override
	public void timeoutPowerUp() {
		// TODO Auto-generated method stub
		GameModel.getInstance().getPaddle().setWidth(GameModel.getInstance().getPaddle().getWidth()/2);
	}

	public boolean isInUse() {
		return inUse;
	}

	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	
	

}
