package domain.gameobjects;

public class Ball extends GameObject {
	static double startX;
	static double startY;
	static double startVx = 300;
	static double startVy= -300;

	
	private double radius;
	
	private double updateTime;
	
	public Ball(double updateTime, double x, double y) {
		this.updateTime = updateTime;
		this.radius = 5;
		this.width = radius*2;
		this.height = radius*2;
		this.x = x;
		this.y = y;
		this.vx = startVx;
		this.vy = -startVy;
		this.id = GameObject.generateId();
		startX = x;
		startY = y;
		this.type = "Ball";
	}
	
	@Override
	public void update() {
		x += vx*updateTime/1000.0;
		y += vy*updateTime/1000.0;
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

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(double updateTime) {
		this.updateTime = updateTime;
	}

	public double getWidth() {
		return this.width;
	}

	public void setWidth(double width) {
		this.width = width;
	}
	
	@Override
	public String toString() {
		return "Ball";
	}

	public void reinitialize(Paddle paddle) {
		// TODO Auto-generated method stub
		x = paddle.getX() + paddle.getWidth() / 2;
		y = paddle.getY() - radius * 2;
		vx = startVx;
		vy = startVy;
	}
	
	public double getSpeed() {
		return Math.sqrt(Math.pow(vx, 2)+ Math.pow(vy, 2));
	}

	public double getAngle() {
		if(vx==0 && vy>=0) {
			return Math.PI/2;
		}
		else if(vx==0 && vy<=0) {
			return 3*Math.PI/2;
		}
		else {
		return Math.atan(vy/vx);
		}
	}
	
	
	
}
