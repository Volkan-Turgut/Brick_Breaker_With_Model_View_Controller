package ui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import domain.gameobjects.Ball;
import domain.gameobjects.GameObject;
import domain.gameobjects.Laser;
import domain.gameobjects.Paddle;
import domain.gameobjects.aliens.CooperativeAlien;
import domain.gameobjects.aliens.ProtectingAlien;
import domain.gameobjects.aliens.RepairingAlien;
import domain.gameobjects.aliens.SuprisingAlien;
import domain.gameobjects.aliens.TimeWastingAlien;
import domain.gameobjects.asteroids.ExplosiveAsteroid;
import domain.gameobjects.asteroids.FirmAsteroid;
import domain.gameobjects.asteroids.GiftAsteroid;
import domain.gameobjects.asteroids.SimpleAsteroid;
import domain.gameobjects.powerups.Chance;
import domain.gameobjects.powerups.CreateAlien;
import domain.gameobjects.powerups.DestructiveLaserGun;
import domain.gameobjects.powerups.GangOfBalls;
import domain.gameobjects.powerups.Magnet;
import domain.gameobjects.powerups.TallerPaddle;
import domain.gameobjects.powerups.Wrap;

public class UIGameObject {
	String objectType;
	String fileName;
	BufferedImage image;
	Image scaledImage;
	int id;
	int x;
	int y;
	int width;
	int height;
	double angle;
	
	

	
	public UIGameObject(GameObject gameObject) {
		if (gameObject instanceof Ball) {
			this.objectType = "ball";
			this.fileName = "ball2.png";
		}
		else if (gameObject instanceof Paddle) {
			this.objectType = "paddle";
			this.fileName = "paddle3.png";
		}
		else if (gameObject  instanceof SimpleAsteroid) {
			this.objectType = "simple";
			this.fileName = "simpleAsteroid2.png";
		}
		else if (gameObject   instanceof FirmAsteroid) {
			this.objectType = "firm";
			this.fileName = "firmAsteroid2.png";
		}
		else if (gameObject  instanceof ExplosiveAsteroid) {
			this.objectType = "explosive";
			this.fileName = "explosiveAsteroid2.png";
		}
		else if (gameObject  instanceof GiftAsteroid) {
			this.objectType = "gift";
			this.fileName = "giftAsteroid.png";
		}
		else if (gameObject instanceof Chance) {
			this.objectType = "chance";
			this.fileName = "chance.png";
		}
		else if (gameObject instanceof TallerPaddle) {
			this.objectType = "tallerPaddle";
			this.fileName = "tallerPaddle.png";
		}
		else if (gameObject instanceof Wrap) {
			this.objectType = "wrap";
			this.fileName = "wrap.png";
		}
		else if (gameObject instanceof GangOfBalls) {
			this.objectType = "gangOfBalls";
			this.fileName = "gangOfBalls.png";
		}
		else if (gameObject instanceof DestructiveLaserGun) {
			this.objectType = "destructiveLaserGun";
			this.fileName = "destructiveLaserGun.png";
		}
		else if (gameObject instanceof Laser) {
			this.objectType = "destructiveLaserGun";
			this.fileName = "laser.png";
		}
		else if (gameObject instanceof CooperativeAlien) {
			this.objectType="coalien";
			this.fileName = "coalien.png";
		}
		else if (gameObject instanceof RepairingAlien) {
			this.objectType="realien";
			this.fileName = "realien.png";
			
		}
		else if (gameObject instanceof TimeWastingAlien) {
			this.objectType="tialien";
			this.fileName = "tialien.png";
			
		}
		else if (gameObject instanceof ProtectingAlien) {
			this.objectType="pralien";
			this.fileName = "pralien.png";
			
		}
		else if (gameObject instanceof Magnet) {
			this.objectType="magnet";
			this.fileName = "magnet.png";
			
		}
		else if (gameObject instanceof CreateAlien) {
			this.objectType="vesikalik2";
			this.fileName = "surprise.png";
			
		}
		else if (gameObject instanceof SuprisingAlien) {
			this.objectType="spalien";
			this.fileName = "surprise.png";
			
		}

		
		

		
		this.id = gameObject.getId();
		this.x = (int) gameObject.getX();
		this.y = (int) gameObject.getY();
		this.width = (int) gameObject.getWidth();
		this.height = (int) gameObject.getHeight();
		
		
		try {
			image = ImageIO.read(getClass().getClassLoader().getResource("resources/" + fileName)); 
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Background image not found!", "Error", JOptionPane.WARNING_MESSAGE);;
		} catch (IllegalArgumentException iae) {
			JOptionPane.showMessageDialog(null, "Illegal argument! File name: " + fileName, "Error", JOptionPane.WARNING_MESSAGE);
		}
		
		scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		
	}
	

	
	public UIGameObject(String objectType, int x, int y , int width, int height, String fileName) {
		this.objectType = objectType;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.fileName = fileName;
		try {
			image = ImageIO.read(getClass().getClassLoader().getResource("resources/" + fileName)); 
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Background image not found!", "Error", JOptionPane.WARNING_MESSAGE);;
		} catch (IllegalArgumentException iae) {
			JOptionPane.showMessageDialog(null, "Illegal argument", "Error", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	public UIGameObject(String objectType, int id, int x, int y , int width, int height, String fileName) {
		this.objectType = objectType;
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.fileName = fileName;
		try {
			image = ImageIO.read(getClass().getClassLoader().getResource("resources/" + fileName)); 
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Background image not found!", "Error", JOptionPane.WARNING_MESSAGE);;
		} catch (IllegalArgumentException iae) {
			JOptionPane.showMessageDialog(null, "Illegal argument", "Error", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	public UIGameObject(String objectType, int x, int y, String fileName) {
		this.objectType = objectType;
		this.fileName = fileName;
		this.x = x;
		this.y = y;
		try {
			image = ImageIO.read(getClass().getClassLoader().getResource("resources/" + fileName)); 
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Background image not found!", "Error", JOptionPane.WARNING_MESSAGE);;
		} catch (IllegalArgumentException iae) {
			JOptionPane.showMessageDialog(null, "Illegal argument", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public UIGameObject(String objectType, int x, int y, int width, int height, double angle, String fileName) {
		this.objectType = objectType;
		this.fileName = fileName;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.angle = angle;
		try {
			image = ImageIO.read(getClass().getClassLoader().getResource("resources/" + fileName)); 
			scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Background image not found!", "Error", JOptionPane.WARNING_MESSAGE);;
		} catch (IllegalArgumentException iae) {
			JOptionPane.showMessageDialog(null, "Illegal argument", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public UIGameObject(String objectType, int id, int x, int y, int width, int height, double angle, String fileName) {
		this.objectType = objectType;
		this.fileName = fileName;
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.angle = angle;
		try {
			image = ImageIO.read(getClass().getClassLoader().getResource("resources/" + fileName)); 
			scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Background image not found!", "Error", JOptionPane.WARNING_MESSAGE);;
		} catch (IllegalArgumentException iae) {
			JOptionPane.showMessageDialog(null, "Illegal argument", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void changeImage(String fileName) {
		
		try {
			image = ImageIO.read(getClass().getClassLoader().getResource("resources/" + fileName)); 
			scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Background image not found!", "Error", JOptionPane.WARNING_MESSAGE);;
		} catch (IllegalArgumentException iae) {
			JOptionPane.showMessageDialog(null, "Illegal argument", "Error", JOptionPane.WARNING_MESSAGE);
		}
		
	}


	public void scaleImage(int width, int height) {
		scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}
	
	public void scaleImage() {
		scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}
	
	@Override
    public boolean equals(Object o) {
 
        // If the object is compared with itself then return true 
        if (o == this) {
            return true;
        }
 
        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof UIGameObject)) {
            return false;
        }
        
        UIGameObject uiGameObject = (UIGameObject) o;
        if (!this.objectType.equals(uiGameObject.getObjectType())) return false;
        if (!this.fileName.equals(uiGameObject.fileName)) return false;
       // if (!this.image.equals(uiGameObject.image)) return false;
       // if (!this.scaledImage.equals(uiGameObject.scaledImage)) return false;
        if (this.id != uiGameObject.id) return false;
        if (this.x != uiGameObject.x) return false;
        if (this.y != uiGameObject.y) return false;
        if (this.width != uiGameObject.width) return false;
        if (this.height != uiGameObject.height) return false;
        if (this.angle != uiGameObject.angle) return false;
        return true;
	}
	
	
	
	public String getObjectType() {
		return objectType;
	}

	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public Image getImage() {
		return image;
	}

	public double getAngle() {
		return angle;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Image getScaledImage() {
		return scaledImage;
	}

	public void setScaledImage(BufferedImage scaledImage) {
		this.scaledImage = scaledImage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	

}
