package domain;

import java.util.HashMap;

import domain.gameobjects.asteroids.GiftAsteroid;
import domain.gameobjects.powerups.DestructiveLaserGun;
import domain.gameobjects.powerups.GangOfBalls;
import domain.gameobjects.powerups.Magnet;
import domain.gameobjects.powerups.Powerup;
import domain.gameobjects.powerups.TallerPaddle;
import domain.gameobjects.powerups.Wrap;

public class Inventory {
	Wrap wrap;
	Magnet magnet;
	TallerPaddle tallerPaddle;	
	HashMap<Powerup, Integer> inventoryCount = new HashMap<>();
	int[] inventoryCounts = {0, 0, 0};
	
	public Inventory() {
		wrap = new Wrap(new GiftAsteroid(0, 0, 0, 0));
		magnet = new Magnet(new GiftAsteroid(0, 0, 0, 0));
		tallerPaddle = new TallerPaddle(new GiftAsteroid(0, 0, 0, 0));
		inventoryCount.put(wrap, 0);
		inventoryCount.put(magnet, 0);
		inventoryCount.put(tallerPaddle, 0);
	}
	
	public void addToInventory(Powerup powerup) {
		System.out.println(powerup.getClass());
		
		if (!powerup.isCollectable()) {
			powerup.usePowerUp();
		}
		else {
			if (powerup instanceof Wrap) {
				inventoryCount.replace(wrap, inventoryCount.get(wrap) + 1) ;
				inventoryCounts[0]++;
			}
			else if (powerup instanceof Magnet) {
				inventoryCount.replace(magnet, inventoryCount.get(magnet) + 1) ;
				inventoryCounts[2]++;
			}
			else if (powerup instanceof TallerPaddle) {
				System.out.println("Taller paddle collected");
				inventoryCount.replace(tallerPaddle, inventoryCount.get(tallerPaddle) + 1) ;
				inventoryCounts[1]++;
			}
		}
		
	}
	
	public void usePowerup(String str) {
		Powerup powerup;
		if (str.equals("wrap")) {
			powerup = wrap;
			if (inventoryCounts[0] != 0 && !checkInUse()) inventoryCounts[0]--;
			
		}
		else if (str.equals("magnet")) {
			powerup = magnet;
			if (inventoryCounts[2] != 0 && !checkInUse()) inventoryCounts[2]--;
		}
		else if (str.equals("tallerPaddle")) {
			powerup = tallerPaddle;
			if (inventoryCounts[1] != 0 && !checkInUse()) inventoryCounts[1]--;
		}
		else {
			powerup = wrap;
		}
		if (!checkInUse()) {
			int count = inventoryCount.get(powerup);
			if (count > 0) {
				inventoryCount.replace(powerup, inventoryCount.get(powerup) - 1);
				
				powerup.usePowerUp();
			}
			else {
				System.out.println("You have zero taller paddels collected");
			}
		}
		
	}
	
	public boolean checkInUse() {
		if (wrap.isInUse()) {
			return true;
		}
		if (magnet.isInUse()) {
			return true;
		}
		if (tallerPaddle.isInUse()) {
			return true;
		}
		
		if(GangOfBalls.isInUse()) {
			return true;
		}
		return false;
	}

	public Wrap getWrap() {
		return wrap;
	}
	
	public Magnet getMagnet() {
		return magnet;
	}

	public HashMap<Powerup, Integer> getInventoryCount() {
		return inventoryCount;
	}

	public void setInventoryCount(HashMap<Powerup, Integer> inventoryCount) {
		this.inventoryCount = inventoryCount;
	}

	public int[] getInventoryCounts() {
		return inventoryCounts;
	}

	public void setInventoryCounts(int[] inventoryCounts) {
		this.inventoryCounts = inventoryCounts;
	}
	
	
	
	public int getNumberOfMagnet() {
		return inventoryCount.get(magnet);
	}
	
	public int getNumberOfWrap() {
		return inventoryCount.get(wrap);
	}
	
	public int getNumberOfTallerPaddle() {
		return inventoryCount.get(tallerPaddle);
	}
	
	
}
