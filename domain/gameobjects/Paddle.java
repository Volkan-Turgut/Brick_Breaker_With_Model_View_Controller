package domain.gameobjects;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class Paddle extends GameObject {
	
	private double angle;
	private boolean right;
	private boolean left;
	private boolean clockwise;
	private boolean counterClockwise;
	private double  angularVelocity;
	private double dtime;
	private double maxVelocity;
	private double velocity;
	private double displacement = 0;
	private double pressedAngularVelocity;
	private double releasedAngularVelocity;
	String soundFileName;
	
	public Paddle(double dtime,  double x, double y) {
		this.x = x;
		this.y = y;
		this.width = 150;
		this.height = 20;
		this.velocity = 0;
		this.angle = 0;
		this.dtime = dtime/1000;
		this.angularVelocity=0;
		this.maxVelocity = width * dtime / 1000;
		this.pressedAngularVelocity = 20 * dtime / 1000;
		this.releasedAngularVelocity = 45 * dtime / 1000;
		this.id = GameObject.generateId();
		this.soundFileName = "Paddle.wav";
		this.type = "Paddle";
	}
	
	
	
	
	public void rotateClockwise() {

		angle -= angularVelocity ;
		
	}
	
	public void rotateCounterClockwise() {

		angle += angularVelocity ;
		
	}
	
	public int[] getYPoints() {
		double radians=Math.toRadians(90-this.getAngle());
		int py[]= {
		(int)(this.getY()+this.getHeight()/2+((this.getWidth()*Math.sin(radians)-(this.getHeight()*Math.cos(radians)))/2)),
		(int)(this.getY()+this.getHeight()/2+((this.getWidth()*Math.sin(radians)+(this.getHeight()*Math.cos(radians)))/2)),
		(int)(this.getY()+this.getHeight()/2+((-this.getWidth()*Math.sin(radians)+(this.getHeight()*Math.cos(radians)))/2)),
		(int)(this.getY()+this.getHeight()/2+((-this.getWidth()*Math.sin(radians)-(this.getHeight()*Math.cos(radians)))/2))
		};
		return py;
	}
	
	public int[] getXPoints() {
		double radians=Math.toRadians(90-this.getAngle());
		int px[]= {
				(int) (this.getX()+this.getWidth()/2+((-this.getWidth()*Math.cos(radians)-(this.getHeight()*Math.sin(radians)))/2)),
				(int) (this.getX()+this.getWidth()/2+((-this.getWidth()*Math.cos(radians)+(this.getHeight()*Math.sin(radians)))/2)),
				(int) (this.getX()+this.getWidth()/2+((+this.getWidth()*Math.cos(radians)+(this.getHeight()*Math.sin(radians)))/2)),
				(int) (this.getX()+this.getWidth()/2+((+this.getWidth()*Math.cos(radians)-(this.getHeight()*Math.sin(radians)))/2))
				};
		return px;
	}
	

	
	@Override
	public void update() {
		// Linear Movement
		if (right) {
			velocity = maxVelocity;
		}
		
		if (left) {
			velocity = -maxVelocity;
		}

		if (!right && !left && Math.abs(velocity) > 0) {
			if (Math.abs(displacement) > width / 2) {
				velocity = 0;
				displacement = 0;
			}
			displacement += velocity;
		}
		
		x += velocity;
		
		// Angular Movement
		if (clockwise) {
			if (angle < 45) {
				angle += pressedAngularVelocity;
			}
			else {
				angle = 45;
			}
		}
		
		if (counterClockwise) {
			if (angle > -45) {
				angle -= pressedAngularVelocity;
			}
			else {
				angle = -45;
			}
		}
		
		if (!counterClockwise && !clockwise && angle != 0) {
			if (angle > 0) { // if it had rotated clockwise
				double newAngle = angle - releasedAngularVelocity;
				if (newAngle * angle < 0) {
					angle = 0;
				}
				else {
					angle = newAngle;
				}
			}
			
			if (angle < 0) { // if it had rotated counterclockwise
				double newAngle = angle + releasedAngularVelocity;
				if (newAngle * angle < 0) {
					angle = 0;
				}
				else {
					angle = newAngle;
				}
				
			}
			
			
		}
		
		
	}
	
	public void setVelocitySetting(String str) {
		switch (str) {
		case "fast":
			maxVelocity = 4 * width * dtime;
			break;
		case "normal":
			maxVelocity = width * dtime;
			break;
		}
	}


	public double getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public double getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public double getAngle() {
		return angle;
	}


	public void setAngle(double angle) {
		this.angle = angle;
	}


	public boolean isRight() {
		return right;
	}


	public void setRight(boolean right) {
		this.right = right;
	}


	public boolean isLeft() {
		return left;
	}


	public void setLeft(boolean left) {
		this.left = left;
	}


	public boolean isClockwise() {
		return clockwise;
	}


	public void setClockwise(boolean clockwise) {
		this.clockwise = clockwise;
	}


	public boolean isCounterclockwise() {
		return counterClockwise;
	}


	public void setCounterclockwise(boolean counterclockwise) {
		this.counterClockwise = counterclockwise;
	}


	public double getAnglechange() {
		return  angularVelocity;
	}


	public void setAnglechange(double anglechange) {
		this. angularVelocity = anglechange;
	}


	public double getDtime() {
		return dtime;
	}


	public void setDtime(double dtime) {
		this.dtime = dtime;
	}


	public double getV() {
		return velocity;
	}


	public void setV(double v) {
		this.velocity = v;
	}


	public double getDisplacement() {
		return displacement;
	}


	public void setDisplacement(double displacement) {
		this.displacement = displacement;
	}
	
	@Override
	public String toString() {
		return "Paddle";
	}
	
	




	
	
	
	

}
