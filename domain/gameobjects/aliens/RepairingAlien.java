package domain.gameobjects.aliens;

import domain.gameobjects.GameObject;
import domain.gameobjects.aliens.alienbehaviour.CreateAsteroid;
import domain.gameobjects.aliens.alienbehaviour.NoAlienBehave;
import domain.gameobjects.aliens.hitbehaviour.Escape;
import domain.gameobjects.aliens.hitbehaviour.UpperPartHit;

public class RepairingAlien extends Alien{

	
	public RepairingAlien() {
		this.hitBehaviour = new Escape();
		this.alienBehaviour = new CreateAsteroid();
		this.width = this.WIDTH;
		this.height = this.HEIGHT;
		this.id = GameObject.generateId();
		this.type = "Repairing";
		this.movingObject = false;
		this.vx = 0;
		this.vy = 0;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
