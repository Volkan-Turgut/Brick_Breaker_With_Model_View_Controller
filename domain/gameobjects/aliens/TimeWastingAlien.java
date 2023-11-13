package domain.gameobjects.aliens;

import domain.gameobjects.GameObject;
import domain.gameobjects.aliens.alienbehaviour.FreezeAsteroids;
import domain.gameobjects.aliens.alienbehaviour.NoAlienBehave;
import domain.gameobjects.aliens.hitbehaviour.Escape;
import domain.gameobjects.aliens.hitbehaviour.UpperPartHit;

public class TimeWastingAlien extends Alien{
	
	
	public TimeWastingAlien() {
		this.hitBehaviour = new Escape();
		this.alienBehaviour = new FreezeAsteroids();
		this.width = this.WIDTH;
		this.height = this.HEIGHT;
		this.id = GameObject.generateId();
		this.movingObject = false;
		this.type = "Timewasting";
		this.vx = 0;
		this.vy = 0;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
