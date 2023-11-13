package domain.gameobjects.aliens.alienbehaviour;

import java.awt.Rectangle;
import java.util.ArrayList;

import application.models.GameModel;
import domain.gameobjects.GameObject;
import domain.gameobjects.aliens.Alien;

public class CreateAsteroid implements AlienBehaviour {

	@Override
	public void behave(GameModel gameModel, Alien alien, double alienCounter) {
		// TODO Auto-generated method stub
		if(alienCounter > 5) {
			gameModel.createASimpleAsteroid();
			System.out.println("aaaaa");
			alien.setAlienCounter(0);
		}
		
	}



}
