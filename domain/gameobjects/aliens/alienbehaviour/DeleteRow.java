package domain.gameobjects.aliens.alienbehaviour;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import application.models.GameModel;
import domain.gameobjects.GameObject;
import domain.gameobjects.aliens.Alien;
import domain.gameobjects.aliens.hitbehaviour.Escape;
import domain.gameobjects.asteroids.Asteroid;

public class DeleteRow implements AlienBehaviour {

	@Override
	public void behave(GameModel gameModel, Alien alien, double alienCounter) {
		// TODO Auto-generated method stub
		if(alienCounter > 10) {
			ArrayList<Integer> intervals = new ArrayList<>();
			for(int i = 0; i < gameModel.getGameHeight(); i += 80) {
				
				intervals.add(i);
	
			}
			
			Random rand = new Random();
			boolean flag = false;
			do {
			int index = rand.nextInt(intervals.size());
			Rectangle newbrick = new Rectangle(0, intervals.get(index), gameModel.getGameWidth(), 80);
			for (GameObject gameObject : gameModel.getAsteroids()) {
				Rectangle oldbrick = new Rectangle((int)gameObject.getX(), (int)gameObject.getY(),(int) gameObject.getWidth(), (int) gameObject.getHeight());
			       if (oldbrick.intersects(newbrick)) {
			    	   flag = true;
			    	   ((Asteroid) gameObject).setStrength(((Asteroid)gameObject).getStrength() - 1);
			    	   System.out.println("Row Deleted");
			           gameObject.setDestroyed(true);
			        }
				
			}
			
			}while(!flag);
			
			alien.setHitBehaviour(new Escape());
			alien.hitPerform(gameModel);
			
		}
	}
	

}
