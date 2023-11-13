package domain.gameobjects.aliens;

import domain.gameobjects.GameObject;
import domain.gameobjects.aliens.alienbehaviour.DeleteRow;
import domain.gameobjects.aliens.hitbehaviour.NoCooperative;

public class CooperativeAlien extends Alien{
	
	public CooperativeAlien() {
		this.alienBehaviour = new DeleteRow();
		this.hitBehaviour = new NoCooperative();
		this.width = this.WIDTH;
		this.type = "Cooperative";
		this.height = this.HEIGHT;
		this.id = GameObject.generateId();
		this.movingObject = false;
		this.vx = 0;
		this.vy = 0;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
