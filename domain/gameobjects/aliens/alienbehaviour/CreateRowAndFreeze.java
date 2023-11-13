package domain.gameobjects.aliens.alienbehaviour;

import java.util.ArrayList;
import java.util.Random;

import application.models.GameModel;
import domain.gameobjects.aliens.Alien;
import domain.gameobjects.aliens.hitbehaviour.Escape;

public class CreateRowAndFreeze implements AlienBehaviour {

	@Override
	public void behave(GameModel gameModel, Alien alien, double alienCounter) {
		
		// TODO Auto-generated method stub
		if(alienCounter > 5) {
			ArrayList<Integer> intervals = new ArrayList<>();
			for(int i = 0; i < gameModel.getGameHeight() - 200; i += 20) {
				
				intervals.add(i);
	
			}
			
			Random rand = new Random();
			int index = rand.nextInt(intervals.size());
			for(int x = 0; x < gameModel.getGameWidth() - 10; x += (int)(gameModel.getGameWidth()*0.02 + 5)) {
				gameModel.createASimpleAsteroidAtLocation(x, intervals.get(index));
			}
			gameModel.setRunFrozen(true);
			
			alien.setHitBehaviour(new Escape());
			alien.setAlienBehaviour(new NoAlienBehave());
			alien.hitPerform(gameModel);
		}
	}

}
