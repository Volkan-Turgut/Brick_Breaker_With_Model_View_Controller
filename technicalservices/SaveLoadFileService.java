package technicalservices;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import application.models.GameModel;
import domain.gameobjects.Ball;
import domain.gameobjects.GameObject;
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

public class SaveLoadFileService {
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void save(List<GameObject> gameObjects){
		try {
			File file = new File("gameSave.txt");
			if (file.createNewFile()) {
		        System.out.println("File created: " + file.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
			FileWriter myWriter = new FileWriter("gameSave.txt");
			StringBuffer s = new StringBuffer(100);
			s.append(GameModel.getInstance().getLives());
			// Add this after implementing score
			/*
			s.append(",");
			s.append(GameModel.getInstance().getScore());
			*/
			s.append("\n");
			myWriter.write(s.toString());
			
			for (GameObject g: gameObjects) {
				s = new StringBuffer(100);
				s.append(g.getType().toString());
				s.append(",");
				s.append(((Integer)g.getId()).toString());
				s.append(",");
				s.append(((Integer)g.getcurrentId()).toString());
				s.append(",");
				s.append(((Double)g.getX()).toString());
				s.append(",");
				s.append(((Double)g.getY()).toString());
				s.append(",");
				s.append(((Double)g.getVx()).toString());
				s.append(",");
				s.append(((Double)g.getVy()).toString());
				s.append(",");
				s.append(((Double)g.getWidth()).toString());
				s.append(",");
				s.append(((Double)g.getHeight()).toString());
				s.append(",");
				s.append((g.isMovingObject()?"1":"0"));
				s.append(",");
				s.append((g.isFrozen()?"1":"0"));
				s.append(",");
				s.append((g.isBecomeFrozen()?"1":"0"));
				s.append(",");
				s.append((g.isBecomeUnFrozen()?"1":"0"));
				s.append("\n");
				myWriter.write(s.toString());
			}
			
			
			
			
		    myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	
	
	
	public void load(List<GameObject> gameObjects) {
		
		
		
		List<GameObject> gos = GameModel.getInstance().getGameObjects();
		  for (GameObject go : gos) {
			  GameModel.getInstance().addToWillBeRemoved(go);
		  }
		  gos.clear(); 
		  GameModel.getInstance().getBalls().clear();
		  GameModel.getInstance().getAsteroids().clear();
		  GameModel.getInstance().getAliens().clear();
		  
		  
		    try {
		        File myObj = new File("gameSave.txt");
		        Scanner myReader = new Scanner(myObj);
		        String data = myReader.nextLine();
		        int lives = Integer.parseInt(data);
		        GameModel.getInstance().setLives(lives);
		        
		        while (myReader.hasNextLine()) {
		          data = myReader.nextLine();
		          ArrayList<String> list = new ArrayList<>(Arrays.asList(data.split(",")));
		          String type = list.get(0);
		          int id = Integer.parseInt((String) list.get(1));
				  double x = Double.parseDouble((String) list.get(3));
				  double y = Double.parseDouble((String) list.get(4));
				  double vx = Double.parseDouble((String) list.get(5));
				  double vy = Double.parseDouble((String) list.get(6));
				  double width = Double.parseDouble((String) list.get(7));
				  double height = Double.parseDouble((String) list.get(8));
				  boolean movingobject = Boolean.parseBoolean((String) list.get(9));
				  boolean isFrozen = Boolean.parseBoolean((String) list.get(10));
				  boolean becomeFrozen = Boolean.parseBoolean((String) list.get(11));
				  boolean becomeUnFrozen = Boolean.parseBoolean((String) list.get(12));
				  
				  if (type.equals("Paddle")) {
					  Paddle paddle = new Paddle(GameModel.getInstance().getDelay(), x, y);
					  //paddle.setId(id);
					  paddle.setWidth(width);
					  paddle.setHeight(height);
					  gos.add(paddle);
					  GameModel.getInstance().setPaddle(paddle);
				  }
				  if (type.equals("Ball")) {
					  Ball ball = new Ball(GameModel.getInstance().getDelay(), x, y);
					  //ball.setId(id);
					  ball.setVx(vx);
					  ball.setVy(vy);
					  gos.add(ball);
					  GameModel.getInstance().setBall(ball);
					  GameModel.getInstance().getBalls().add(ball);
				  }
				  if (type.equals("Firm")) {
					  FirmAsteroid firmAsteroid = new FirmAsteroid((int) width, (int) height, x, y);
					  //firmAsteroid.setId(id);
					  firmAsteroid.setMovingObject(movingobject);
					  firmAsteroid.setWidth(width);
					  firmAsteroid.setHeight(height);
					  firmAsteroid.setRadius((int) width);

					  gos.add(firmAsteroid);
					  GameModel.getInstance().getAsteroids().add(firmAsteroid);
				  }
				  if (type.equals("Simple")) {
					  SimpleAsteroid simpleAsteroid = new SimpleAsteroid((int) width, (int) height, x, y);
					  //firmAsteroid.setId(id);
					  simpleAsteroid.setWidth(width);
					  simpleAsteroid.setHeight(height);
					  simpleAsteroid.setMovingObject(movingobject);
					  simpleAsteroid.setFrozen(isFrozen);
					  simpleAsteroid.setBecomeFrozen(becomeFrozen);
					  simpleAsteroid.setBecomeUnFrozen(becomeUnFrozen);
					  gos.add(simpleAsteroid);
					  GameModel.getInstance().getAsteroids().add(simpleAsteroid);
				  }
				  if (type.equals("Gift")) {
					  GiftAsteroid simpleAsteroid = new GiftAsteroid((int) width, (int) height, x, y);
					  //firmAsteroid.setId(id);
					  simpleAsteroid.setWidth(width);
					  simpleAsteroid.setHeight(height);
					  simpleAsteroid.setMovingObject(movingobject);
					  simpleAsteroid.setFrozen(isFrozen);
					  simpleAsteroid.setBecomeFrozen(becomeFrozen);
					  simpleAsteroid.setBecomeUnFrozen(becomeUnFrozen);
					  gos.add(simpleAsteroid);
					  GameModel.getInstance().getAsteroids().add(simpleAsteroid);
				  }
				  if (type.equals("Explosive")) {
					  ExplosiveAsteroid explosiveAsteroid = new ExplosiveAsteroid((int) width, (int) height, x, y);
					  //firmAsteroid.setId(id);
					  explosiveAsteroid.setWidth(width);
					  explosiveAsteroid.setHeight(height);
					  //explosiveAsteroid.setRadius((int) width);
					  explosiveAsteroid.setMovingObject(movingobject);
					  explosiveAsteroid.setFrozen(isFrozen);
					  explosiveAsteroid.setBecomeFrozen(becomeFrozen);
					  explosiveAsteroid.setBecomeUnFrozen(becomeUnFrozen);
					  gos.add(explosiveAsteroid);
					  GameModel.getInstance().getAsteroids().add(explosiveAsteroid);
				  }
				  if (type.equals("Cooperative")) {
					  CooperativeAlien cooperativeAlien = new CooperativeAlien();
					  //firmAsteroid.setId(id);
					  cooperativeAlien.setWidth(width);
					  cooperativeAlien.setHeight(height);
					  cooperativeAlien.setX(x);
					  cooperativeAlien.setY(y);
					  cooperativeAlien.setVx(vx);
					  cooperativeAlien.setVy(vy);
					  
					  //explosiveAsteroid.setRadius((int) width);
					  cooperativeAlien.setMovingObject(movingobject);
					  
					  gos.add(cooperativeAlien);
					  GameModel.getInstance().getAliens().add(cooperativeAlien);
					 // GameModel.getInstance().getAliens().add(explosiveAsteroid);
				  }
				  if (type.equals("Protecting")) {
					  ProtectingAlien protectingAlien = new ProtectingAlien();
					  //firmAsteroid.setId(id);
					  protectingAlien.setWidth(width);
					  protectingAlien.setHeight(height);
					  protectingAlien.setX(x);
					  protectingAlien.setY(y);
					  protectingAlien.setVx(vx);
					  protectingAlien.setVy(vy);
					  
					  //explosiveAsteroid.setRadius((int) width);
					  protectingAlien.setMovingObject(movingobject);
					  
					  gos.add(protectingAlien);
					  GameModel.getInstance().getAliens().add(protectingAlien);
					 // GameModel.getInstance().getAliens().add(explosiveAsteroid);
				  }
				  if (type.equals("Repairing")) {
					  RepairingAlien repairingAlien = new RepairingAlien();
					  //firmAsteroid.setId(id);
					  repairingAlien.setWidth(width);
					  repairingAlien.setHeight(height);
					  repairingAlien.setX(x);
					  repairingAlien.setY(y);
					  repairingAlien.setVx(vx);
					  repairingAlien.setVy(vy);
					  
					  //explosiveAsteroid.setRadius((int) width);
					  repairingAlien.setMovingObject(movingobject);
					  
					  gos.add(repairingAlien);
					  GameModel.getInstance().getAliens().add(repairingAlien);
					 // GameModel.getInstance().getAliens().add(explosiveAsteroid);
				  }
				  if (type.equals("Timewasting")) {
					  TimeWastingAlien timeWastingAlien = new TimeWastingAlien();
					  //firmAsteroid.setId(id);
					  timeWastingAlien.setWidth(width);
					  timeWastingAlien.setHeight(height);
					  timeWastingAlien.setX(x);
					  timeWastingAlien.setY(y);
					  timeWastingAlien.setVx(vx);
					  timeWastingAlien.setVy(vy);
					  
					  //explosiveAsteroid.setRadius((int) width);
					  timeWastingAlien.setMovingObject(movingobject);
					  
					  gos.add(timeWastingAlien);
					  GameModel.getInstance().getAliens().add(timeWastingAlien);
					 // GameModel.getInstance().getAliens().add(explosiveAsteroid);
				  }
				  
				  if (type.equals("Surprising")) {
					  
					  SuprisingAlien alien = GameModel.getInstance().createASuprisingAlien();
					  alien.setWidth(width);
					  alien.setHeight(height);
					  alien.setX(x);
					  alien.setY(y);
					  alien.setVx(vx);
					  alien.setVy(vy);
					  alien.setMovingObject(movingobject);
					  
				  }
		        }
		        myReader.close();
		      } catch (FileNotFoundException e) {
		        System.out.println("An error occurred.");
		        e.printStackTrace();
		      }
		    System.out.println("Loaded File");
	}
	

}
