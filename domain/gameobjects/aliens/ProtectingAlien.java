package domain.gameobjects.aliens;


import java.awt.Rectangle;
import java.util.ArrayList;

import domain.gameobjects.GameObject;
import domain.gameobjects.aliens.alienbehaviour.NoAlienBehave;
import domain.gameobjects.aliens.hitbehaviour.Escape;
import domain.gameobjects.aliens.hitbehaviour.UpperPartHit;
import domain.gameobjects.asteroids.Asteroid;


public class ProtectingAlien extends Alien{
	
	public ProtectingAlien() {
		this.hitBehaviour = new UpperPartHit();
		this.alienBehaviour = new NoAlienBehave();
		this.width = this.WIDTH;
		this.height = this.HEIGHT;
		this.type = "Protecting";
		this.id = GameObject.generateId();
		this.movingObject = true;
		this.vx = 3;
		this.vy = 0;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		x = x + vx;
		
	}
	
	

}
