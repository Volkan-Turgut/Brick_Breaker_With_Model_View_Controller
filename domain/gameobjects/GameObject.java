package domain.gameobjects;

import javax.sound.sampled.Clip;

public abstract class GameObject {
	protected int id;
	private static int currentId = 0;
	protected String type;
	protected double x;
	protected double y;
	protected double vx;
	protected double vy;
	protected double width;
	protected double  height;
	protected boolean movingObject = true;
	private int strength;
	private boolean willBeRemoved;
	private boolean destroyed=false;

	protected Clip sound;
	protected String soundFileName;



	protected boolean isFrozen = false;
	protected boolean becomeFrozen = false;
	protected boolean becomeUnFrozen = false;
	




	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isBecomeUnFrozen() {
		return becomeUnFrozen;
	}

	public void setBecomeUnFrozen(boolean becomeUnFrozen) {
		this.becomeUnFrozen = becomeUnFrozen;
	}

	public boolean isBecomeFrozen() {
		return becomeFrozen;
	}

	public void setBecomeFrozen(boolean becomeFrozen) {
		this.becomeFrozen = becomeFrozen;
	}

	public abstract void update();
	
	public void setId(int id) {
		this.id = id;
	}

	public static int generateId() {
		currentId++;
		return currentId;
	}
	
	public int getId() {
		return id;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getVx() {
		return vx;
	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public double getVy() {
		return vy;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}

	public boolean isMovingObject() {
		return movingObject;
	}

	public void setMovingObject(boolean movingObject) {
		this.movingObject = movingObject;
	}

	public boolean isFrozen() {
		return isFrozen;
	}

	public void setFrozen(boolean isFrozen) {
		this.isFrozen = isFrozen;
	}


	public int getStrength() {
		// TODO Auto-generated method stub
		return this.strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}


	public int getcurrentId() {
		// TODO Auto-generated method stub
		return this.currentId;
	}

	public void playSound() {
		// TODO Auto-generated method stub
		
	}

	public boolean isWillBeRemoved() {
		return willBeRemoved;
	}

	public void setWillBeRemoved(boolean willBeRemoved) {
		this.willBeRemoved = willBeRemoved;
	}
	
	public void setDestroyed(boolean destroyed) {
		this.destroyed=destroyed;
	}
	public boolean isDestroyed() {
		return destroyed;
	}
	
	
	


	
	
	
	
	
}
