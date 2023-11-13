package domain.gameobjects.aliens.alienbehaviour;

import application.models.GameModel;
import domain.gameobjects.aliens.Alien;

public interface AlienBehaviour {

	void behave(GameModel gameModel, Alien alien, double alienCounter);

}
