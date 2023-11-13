package domain.gameobjects.aliens.hitbehaviour;

import application.models.GameModel;
import domain.gameobjects.aliens.Alien;

public interface HitBehaviour {

	void behave(GameModel gameModel, Alien alien);


}
