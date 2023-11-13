package domain.gameobjects.aliens.hitbehaviour;

import application.models.GameModel;
import domain.gameobjects.aliens.Alien;

public class NoCooperative implements HitBehaviour {

	@Override
	public void behave(GameModel gameModel, Alien alien) {
		// TODO Auto-generated method stub
		gameModel.setCanCreateCoAlien(false);
		alien.setHitBehaviour(new Escape());
		alien.hitPerform(gameModel);

	}



}
