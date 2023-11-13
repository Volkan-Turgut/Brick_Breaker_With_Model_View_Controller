package domain.gameobjects.asteroids;

public class AsteroidFactory {
	private Asteroid asteroid;
	
	public Asteroid getAsteroid(String type, int[] dimensions, double positionX, double positionY) {
		
		if((type == "Simple") || (type == "simple")) {
			asteroid = new SimpleAsteroid(dimensions[0], dimensions[1],positionX,positionY);
			
		}
		else if (type == "Firm" || (type == "firm")) {
			asteroid = new FirmAsteroid(dimensions[0], dimensions[1],positionX,positionY);
		}
		/*else if (type == "Explosive" || (type == "explosive")){
			asteroid = new ExplosiveAsteroid(dimensions[0], dimensions[1],positionX,positionY);
		}*/
		
		else if (type == "Gift" || (type == "gift")){
			asteroid = new GiftAsteroid(dimensions[0], dimensions[1],positionX,positionY);
		}
		else {
			asteroid = new ExplosiveAsteroid(dimensions[0], dimensions[1],positionX,positionY);
		}
		
		return asteroid;
	}
	

}

