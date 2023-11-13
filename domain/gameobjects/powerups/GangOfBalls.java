package domain.gameobjects.powerups;
import java.util.Objects;

import java.util.ArrayList;

import application.models.GameModel;
import domain.gameobjects.Ball;
import domain.gameobjects.GameObject;
import domain.gameobjects.asteroids.Asteroid;

public class GangOfBalls extends Powerup{
private Ball originalBall;
private ArrayList<Ball> otherBalls;


	public GangOfBalls(GameObject giftAsteroid) {
		super(giftAsteroid);
		this.isCollectable = false;
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void usePowerUp() {
		// TODO Auto-generated method stub
		this.originalBall=GameModel.getInstance().getBall();
		otherBalls=new ArrayList<>();
		for(int i=0; i<9; i++) {
			otherBalls.add(new Ball(GameModel.getInstance().getDelay(), (int) GameModel.getInstance().getBall().getX(), (int) GameModel.getInstance().getBall().getY()));
		}
		for(int i=0; i<otherBalls.size(); i++) {
			otherBalls.get(i).setVx(originalBall.getSpeed()*Math.cos(originalBall.getAngle()+Math.toRadians(36*(i+1))));
			otherBalls.get(i).setVy(originalBall.getSpeed()*Math.sin(originalBall.getAngle()+Math.toRadians(36*(i+1))));
			GameModel.getInstance().getGameObjects().add(otherBalls.get(i));
			GameModel.getInstance().getBalls().add(otherBalls.get(i));
		}
		
	}
	
	public static boolean isInUse() {
		
		if(Objects.isNull(GameModel.getInstance().getBalls())) {
			return false;
		}
		else if(GameModel.getInstance().getBalls().size()>1) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void timeoutPowerUp() {
		// TODO Auto-generated method stub
		}
	
	
}
