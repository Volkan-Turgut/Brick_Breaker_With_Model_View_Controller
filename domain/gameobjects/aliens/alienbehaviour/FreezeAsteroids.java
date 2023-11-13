package domain.gameobjects.aliens.alienbehaviour;

import java.util.ArrayList;
import java.util.Random;

import application.models.GameModel;
import domain.gameobjects.GameObject;
import domain.gameobjects.aliens.Alien;
import domain.gameobjects.aliens.hitbehaviour.Escape;
import domain.gameobjects.asteroids.Asteroid;

public class FreezeAsteroids implements AlienBehaviour {

	@Override
	public void behave(GameModel gameModel, Alien alien, double alienCounter) {
		// TODO Auto-generated method stub

		
		if(alienCounter > 5) {
			ArrayList<GameObject> selectedAsteroids = new ArrayList<>();
			ArrayList<GameObject> aliveAsteroids = new ArrayList<>();
			for(GameObject asteroid: gameModel.getAsteroids()) {
				if(((Asteroid) asteroid).getStrength() >= 1) {
					aliveAsteroids.add(asteroid);
				}
			}
			if(aliveAsteroids.size() > 8) {
				Random rand = new Random();
				ArrayList<Integer> numbers = new ArrayList<Integer>();
				for(int i = 0; i < aliveAsteroids.size(); i++) {
					numbers.add(i);
				}
				ArrayList<Integer> randIndex = new ArrayList<Integer>();
				for(int i = 0; i < 8; i++) {
					int a = rand.nextInt(numbers.size());
					randIndex.add(numbers.get(a));
					selectedAsteroids.add(aliveAsteroids.get(randIndex.get(i)));
					numbers.remove(a);
				}
				
			} else {
				for(int i = 0; i < aliveAsteroids.size(); i++) {
					selectedAsteroids.add(aliveAsteroids.get(i));
				}
			}
			
			for (int i = 0; i < selectedAsteroids.size(); i++) {
				selectedAsteroids.get(i).setFrozen(true);
				gameModel.getFrozenAsteroids().add(selectedAsteroids.get(i));
				//selectedAsteroids.get(i).setId(selectedAsteroids.get(i).generateId());
				
				selectedAsteroids.get(i).setBecomeFrozen(true);
				System.out.println("Frezzeddddddddddddddddddddddddd");
				
			}
			
			gameModel.setRunFrozen(true);
			alien.setHitBehaviour(new Escape());
			alien.hitPerform(gameModel);
			
		}
		
		
	}

}
