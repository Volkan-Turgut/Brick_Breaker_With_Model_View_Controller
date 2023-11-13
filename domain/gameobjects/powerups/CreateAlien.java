package domain.gameobjects.powerups;

import java.util.Objects;
import java.util.Random;

import application.models.GameModel;
import domain.gameobjects.GameObject;

public class CreateAlien extends Powerup {

	public CreateAlien(GameObject giftAsteroid) {
		super(giftAsteroid);
		this.isCollectable = true;
		width = 20;
		height = 20;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void usePowerUp() {
		GameModel.getInstance().createAnAlien();
		
	}

	@Override
	public void timeoutPowerUp() {
		// TODO Auto-generated method stub
		
	}
	

	public static boolean isInUse() {
		if(Objects.isNull(GameModel.getInstance().getAliens())) {
			return false;
		}
		else if(GameModel.getInstance().getAliens().size()>1) {
			return true;
		}
		else {
			return false;
		}
	}
}
