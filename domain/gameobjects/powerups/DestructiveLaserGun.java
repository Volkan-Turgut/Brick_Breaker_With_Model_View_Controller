package domain.gameobjects.powerups;

import java.util.Timer;
import java.util.TimerTask;

import application.models.GameModel;
import domain.gameobjects.GameObject;
import domain.gameobjects.Laser;
import domain.gameobjects.asteroids.Asteroid;

public class DestructiveLaserGun extends Powerup{

	public DestructiveLaserGun(GameObject giftAsteroid) {
		super(giftAsteroid);
		this.isCollectable = false;
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public synchronized void usePowerUp() {
		// TODO Auto-generated method stub
		
		Timer timer= new Timer();
		timer.schedule(new TimerTask() {
		        	int times=0;
		            @Override
		            public void run() {
		            	if(times==5) {
		            		timer.cancel();
		            		return;
		            	}
		            	try {
			            	GameModel.getInstance().getGameObjects().add(new Laser());
			        		GameModel.getInstance().getGameObjects().add(new Laser());
		            	}
		            	catch (Exception e) {
		            		e.printStackTrace();
		            	}
		            	times++;
		            }
		        },0,1000 
		);
		
		
		
		
		
	
	}



	@Override
	public void timeoutPowerUp() {
		// TODO Auto-generated method stub
		
	}

}
					
