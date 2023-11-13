package domain.gameobjects.aliens;

import application.models.GameModel;
import domain.gameobjects.GameObject;
import domain.gameobjects.aliens.alienbehaviour.AlienBehaviour;
import domain.gameobjects.aliens.hitbehaviour.HitBehaviour;


public abstract class Alien extends GameObject{
	protected AlienBehaviour alienBehaviour;
	protected HitBehaviour hitBehaviour;
	protected final int HEIGHT = 30;
	protected final int WIDTH = 30;
	protected double alienCounter = 0.0;
	protected boolean hasEscaped = false;
	
	
	public void hitPerform(GameModel gameModel) {
		hitBehaviour.behave(gameModel, this);
	}
	
	public void alienPerform(GameModel gameModel) {
		alienBehaviour.behave(gameModel, this, alienCounter);
		
	}
	
	public void setAlienBehaviour(AlienBehaviour alienBehaviour) {
		this.alienBehaviour = alienBehaviour;
	}
	public void setHitBehaviour(HitBehaviour hitBehaviour) {
		this.hitBehaviour = hitBehaviour;
	}
	
	public double getAlienCounter() {
		return alienCounter;
	}

	public void setAlienCounter(double alienCounter) {
		this.alienCounter = alienCounter;
	}

	public AlienBehaviour getAlienBehaviour() {
		return alienBehaviour;
	}

	public HitBehaviour getHitBehaviour() {
		return hitBehaviour;
	}

	public boolean isHasEscaped() {
		return hasEscaped;
	}

	public void setHasEscaped(boolean hasEscaped) {
		this.hasEscaped = hasEscaped;
	}
	
	
}
