package domain.gameobjects.powerups;

import application.models.GameModel;
import domain.gameobjects.GameObject;
import domain.gameobjects.asteroids.Asteroid;

public class Chance extends Powerup{

	public Chance(GameObject giftAsteroid) {
		super(giftAsteroid);
		this.isCollectable = false;
		
		width = 20;
		height = 20;
	}

	@Override
	public void usePowerUp() {
		// TODO Auto-generated method stub
		GameModel.getInstance().increaseLives();
	}


	@Override
	public void timeoutPowerUp() {
		// TODO Auto-generated method stub
		//nothing here
	}

}
