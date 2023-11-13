package domain.gameobjects.aliens;

import application.models.GameModel;
import domain.gameobjects.GameObject;
import domain.gameobjects.aliens.alienbehaviour.AlienBehaviour;
import domain.gameobjects.aliens.alienbehaviour.DeleteRow;
import domain.gameobjects.aliens.hitbehaviour.HitBehaviour;
import domain.gameobjects.aliens.hitbehaviour.NoCooperative;
import domain.gameobjects.aliens.hitbehaviour.UpperPartHit;

public class SuprisingAlien extends Alien {
	
	
	
	public SuprisingAlien(HitBehaviour hitBehaviour, AlienBehaviour alienBehaviour) {
		this.alienBehaviour = alienBehaviour;
		this.hitBehaviour = hitBehaviour;
		this.width = this.WIDTH;
		this.height = this.HEIGHT;
		this.id = GameObject.generateId();
		this.type = "Surprising";
		this.movingObject = false;
		
		if(hitBehaviour instanceof UpperPartHit) {
			this.vx = 3;
			setX((GameModel.getInstance().getGameWidth() - (int) getWidth())/2);
			setY((GameModel.getInstance().getGameHeight()-192));
		}
		else {
			this.vx = 0;
		}
	
		this.vy = 0;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		x += vx;
		

	}
	
	@Override
	public void setHitBehaviour(HitBehaviour hitBehaviour) {
		this.hitBehaviour = hitBehaviour;
		if(hitBehaviour instanceof UpperPartHit) {
			this.vx = 3;
		}
		else {
			this.vx = 0;
		}
	}

}
