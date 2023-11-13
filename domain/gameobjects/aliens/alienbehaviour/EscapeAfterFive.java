package domain.gameobjects.aliens.alienbehaviour;

import application.models.GameModel;
import domain.gameobjects.aliens.Alien;
import domain.gameobjects.aliens.hitbehaviour.Escape;

public class EscapeAfterFive implements AlienBehaviour {

	@Override
	public void behave(GameModel gameModel, Alien alien, double alienCounter) {
		// TODO Auto-generated method stub
		if(alienCounter > 5) {
			alien.setHitBehaviour(new Escape());
			alien.hitPerform(gameModel);
		}
		

	}

}
