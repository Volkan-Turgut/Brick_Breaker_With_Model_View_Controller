package domain.gameobjects.powerups;

import application.models.GameModel;
import domain.gameobjects.GameObject;
import domain.gameobjects.asteroids.Asteroid;

public class Magnet extends Powerup{
	
	private boolean inUse;
	private double previousSpeed;
	private static boolean wPressed=false;
	private static boolean ballCatched=false;



	public Magnet(GameObject giftAsteroid) {
		super(giftAsteroid);
		this.isCollectable = true;
		inUse = false;
		// TODO Auto-generated constructor stub
		
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
		            	if(ballCatched) {
		            	GameModel.getInstance().getBall().reinitialize(GameModel.getInstance().getPaddle());
		            	ballCatched=false;
		            	}
		            }
		        }, 
		        5000 
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

	public static boolean iswPressed() {
		return wPressed;
	}

	public static void setwPressed(boolean wPressed) {
		Magnet.wPressed = wPressed;
	}
	

	public static void setBallCatched(boolean ballCatched) {
		Magnet.ballCatched = ballCatched;
	}

	public static boolean isBallCatched() {
		return ballCatched;
	}
}
