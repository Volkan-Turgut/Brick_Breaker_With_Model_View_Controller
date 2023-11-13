package domain.gameobjects;

import application.models.GameModel;

public class Laser extends GameObject {
	static int count = 0;
	public Laser() {
		width = 20;
		height = 20;
		vy = 4;
		y =  GameModel.getInstance().getPaddle().getY();
		if (count % 2 == 0) {
			x = GameModel.getInstance().getPaddle().getX();
		}
		else {
			x = GameModel.getInstance().getPaddle().getX() + GameModel.getInstance().getPaddle().getWidth();
		}
		count++;
		id = GameObject.generateId();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		y -= vy;
		/*if (y < 0) {
			GameModel.getInstance().addToWillBeRemoved(this);
		}*/
		
	}
	

}
