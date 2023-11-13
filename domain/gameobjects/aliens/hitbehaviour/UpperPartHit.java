package domain.gameobjects.aliens.hitbehaviour;

import application.models.GameModel;
import domain.gameobjects.Ball;
import domain.gameobjects.aliens.Alien;

public class UpperPartHit implements HitBehaviour {

	
	@Override
	public void behave(GameModel gameModel, Alien alien) {
		// TODO Auto-generated method stub
		Ball ball = gameModel.getBall();
		if(ball.getY() + ball.getRadius() <= alien.getY()) {
			alien.setHitBehaviour(new Escape());
			alien.hitPerform(gameModel);
		}

	}

}
