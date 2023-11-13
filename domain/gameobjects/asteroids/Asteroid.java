package domain.gameobjects.asteroids;

public  interface Asteroid {
	


	void setVx(double vx);


	int getStrength();
	int isMoveable();
	int[] getDimensions();
	String getType();
	void setStrength(int strength);
	void setDimensions(int[] dimensions);
	double getX();
	double getY();
	double getWidth();
	double getHeight();
	void update();


	void setIsMoving(int i);
	void setMovingObject(boolean b);
	double getVx();


	void hitByBall();
	
	


	
	

}

