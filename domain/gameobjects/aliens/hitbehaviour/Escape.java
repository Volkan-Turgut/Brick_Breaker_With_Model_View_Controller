package domain.gameobjects.aliens.hitbehaviour;

import application.models.GameModel;
import domain.gameobjects.aliens.Alien;
import domain.gameobjects.aliens.alienbehaviour.NoAlienBehave;

public class Escape implements HitBehaviour {

	@Override
	public void behave(GameModel gameModel, Alien alien) {
		// TODO Auto-generated method stub
		alien.setX(-50);
		alien.setY(-50);
		alien.setVx(0);
		alien.setVy(0);
		alien.setHitBehaviour(new NoHitBehave());
		alien.setAlienBehaviour(new NoAlienBehave());
		alien.setHasEscaped(true);
	}



	

}
