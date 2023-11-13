package technicalservices;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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


public class SaveLoadDatabaseService {
	private int nbLive;
	

	
	public void Save(List<GameObject> gameObjects){
		
		
		
		// Creating a Mongo client 
	      MongoClient mongo = new MongoClient( "localhost" , 27017 ); 
	      // Creating Credentials 
	      MongoCredential credential; 
	      credential = MongoCredential.createCredential("sampleUser", "myDb", 
	         "password".toCharArray()); 
	      System.out.println("Connected to the database successfully");  
	      
	      // Accessing the database 
	      MongoDatabase database = mongo.getDatabase("myDb");  
	      
	      // Creating a collection 
	      System.out.println("Collections created successfully"); 
	      // Retrieving a collection
	      MongoCollection<Document> collection2 = database.getCollection("sampleCollection");
	      // Dropping a Collection 
	      collection2.drop(); 
	      System.out.println("Collection dropped successfully");
	      //Creating a collection 
	      database.createCollection("sampleCollection"); 
	      System.out.println("Collection created successfully");
		  	// Retrieving a collection
		  MongoCollection<Document> collection = database.getCollection("sampleCollection");
		  System.out.println("Collection sampleCollection selected successfully");
		  
          for (GameObject g: gameObjects) {
        	  Document document = new Document("type", g.getType().toString())
        			  
        			  
  					.append("id", ((Integer)g.getId()).toString())
  					.append("currentId", ((Integer)g.getcurrentId()).toString())
  					.append("x", ((Double)g.getX()).toString())
  					.append("y", ((Double)g.getY()).toString())
  					.append("vx", ((Double)g.getVx()).toString())
  					.append("vy", ((Double)g.getVy()).toString())
  					.append("width", ((Double)g.getWidth()).toString())
  					.append("height", ((Double)g.getHeight()).toString())
  					.append("movingobject", (g.isMovingObject()?"1":"0"))
  					.append("isFrozen", (g.isFrozen()?"1":"0"))
  					.append("becomeFrozen", (g.isBecomeFrozen()?"1":"0"))
  					.append("becomeUnFrozen", (g.isBecomeUnFrozen()?"1":"0"))
  					;
        	  
        	  		
  					
  					//Inserting document into the collection
  					collection.insertOne(document);
  					System.out.println("Row inserted successfully");  
          }
          
          Document document = new Document("type", "live")
        		  .append("count", 3); //fix
          collection.insertOne(document);
          System.out.println("Row inserted successfully");
          document = new Document("type", "score")
        		  .append("count", 3); //fix
          collection.insertOne(document);
          System.out.println("Row inserted successfully");
        		  
		  
		 
	   
		 /*try {
	            Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mechatronic_demo",
	                "root", "psw");
	            
	            PreparedStatement st0 = (PreparedStatement) connection
                        .prepareStatement("Drop table gameObject");
	            
	            PreparedStatement st1 = (PreparedStatement) connection
                        .prepareStatement("Create Table gameObject(\r\n"
                        		+ "             type varchar(100),\r\n"
                        		+ "             id integer,\r\n"
                        		+ "             currentId integer,\r\n"
                        		+ "             x double,\r\n"
                        		+ "             y double,\r\n"
                        		+ "             vx double,\r\n"
                        		+ "             vy double,\r\n"
                        		+ "             width double,\r\n"
                        		+ "             height double,\r\n"
                        		+ "             movingObject boolean,\r\n"
                        		+ "             isFrozen boolean,\r\n"
                        		+ "             becomeFrozen boolean,\r\n"
                        		+ "             becomeUnFrozen boolean\r\n"
                        		+ "    \r\n"
                        		+ "                );");
	            
	            int rsUser0 = st0.executeUpdate();
	            int rsUser1 = st1.executeUpdate();
	            
	            for (GameObject g: gameObjects) {
	            	PreparedStatement st2 = (PreparedStatement) connection
	                        .prepareStatement("INSERT INTO gameObject (type,id,currentId, x, y,vx ,vy,width,height,movingobject,isFrozen,becomeFrozen,becomeUnFrozen)\r\n"
	                        		+ "VALUES (?, ?, ?,?,?, ?, ?,?,?, ?, ?,?,?);");
	                	st2.setString(1, g.toString());
	                    st2.setString(2, ((Integer)g.getId()).toString());
	                    st2.setString(3, ((Integer)g.getcurrentId()).toString());
	                    st2.setString(4, ((Double)g.getX()).toString());
	                    st2.setString(5, ((Double)g.getY()).toString());
	                    st2.setString(6, ((Double)g.getVx()).toString());
	                    st2.setString(7, ((Double)g.getVy()).toString());
	                    st2.setString(8, ((Double)g.getWidth()).toString());
	                    st2.setString(9, ((Double)g.getHeight()).toString());
	                    st2.setString(10, (g.isMovingObject()?"1":"0"));
	                    st2.setString(11, (g.isFrozen()?"1":"0"));
	                    st2.setString(12, (g.isBecomeFrozen()?"1":"0"));
	                    st2.setString(13, (g.isBecomeUnFrozen()?"1":"0"));

	                    int i = st2.executeUpdate();
                        if (i > 0) {
                            System.out.println("ROW INSERTED");

                        } else {

                            System.out.println("ROW NOT INSERTED");
                        } 
	            	
	            	
	            	
	            }
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            

	            
	            //st.setString(1, IDField.getText());
	            //st.setString(2, userName);
	            /*ResultSet rs = st.executeQuery();
	            
	            PreparedStatement stID = (PreparedStatement) connection
	                    .prepareStatement("Select user_name, password from user where id=?");

	                stID.setString(1, IDField.getText());
	                ResultSet rsID = stID.executeQuery();
	                
	            PreparedStatement stUser = (PreparedStatement) connection
	                    .prepareStatement("Select user_name, password from user where user_name=?");

	                stUser.setString(1, userName);
	                ResultSet rsUser = stUser.executeQuery();
	            
	            if (rsID.next()) {
	            	infoBox("ID must be unique","Failed");
	            	System.out.println("ID must be unique");
	            }
	 
	            
	            
	            	
	            
	        } catch (SQLException sqlException) {
	            sqlException.printStackTrace();
	        }*/
	}

	public void Load(List<GameObject> gameObjects) {	
		// TODO Auto-generated method stub
		
		///filllll
		System.out.println("Load button activated");
		
		// Creating a Mongo client 
		MongoClient mongo = new MongoClient( "localhost" , 27017 ); 
		// Creating Credentials 
		MongoCredential credential; 
		credential = MongoCredential.createCredential("sampleUser", "myDb", 
         "password".toCharArray()); 
		System.out.println("Connected to the database successfully");  
      
		// Accessing the database 
		MongoDatabase database = mongo.getDatabase("myDb");
		
		// Retrieving a collection
	      MongoCollection<Document> collection = database.getCollection("sampleCollection");
		  System.out.println("Collection sampleCollection selected successfully");
		
		  // Clear game objects array
		  List<GameObject> gos = GameModel.getInstance().getGameObjects();
		  for (GameObject go : gos) {
			  GameModel.getInstance().addToWillBeRemoved(go);
		  }
		  gos.clear(); 
		  GameModel.getInstance().getBalls().clear();
		  GameModel.getInstance().getAsteroids().clear();
		  GameModel.getInstance().getAliens().clear();
		  
		  
		
		 // DBCursor cursor = collection.find(query);
		  FindIterable<Document> iterDoc = collection.find();

		  int i = 1;
		  // Getting the iterator
		  Iterator it = iterDoc.iterator();
			
		  while (it.hasNext()) {
			  Document doc = (Document) it.next();
			  //int id = Integer.parseInt((String) doc.get("id"));
			  double x = Double.parseDouble((String) doc.get("x"));
			  double y = Double.parseDouble((String) doc.get("y"));
			  double vx = Double.parseDouble((String) doc.get("vx"));
			  double vy = Double.parseDouble((String) doc.get("vy"));
			  double width = Double.parseDouble((String) doc.get("width"));
			  double height = Double.parseDouble((String) doc.get("height"));
			  boolean movingobject = Boolean.parseBoolean((String) doc.get("movingobject"));
			  boolean isFrozen = Boolean.parseBoolean((String) doc.get("isFrozen"));
			  boolean becomeFrozen = Boolean.parseBoolean((String) doc.get("becomeFrozen"));
			  boolean becomeUnFrozen = Boolean.parseBoolean((String) doc.get("becomeUnFrozen"));
			  
 
			  if (doc.get("type").equals("Paddle")) {
				  Paddle paddle = new Paddle(GameModel.getInstance().getDelay(), x, y);
				  //paddle.setId(id);
				  paddle.setWidth(width);
				  paddle.setHeight(height);
				  gos.add(paddle);
				  GameModel.getInstance().setPaddle(paddle);
			  }
			  if (doc.get("type").equals("Ball")) {
				  Ball ball = new Ball(GameModel.getInstance().getDelay(), x, y);
				  //ball.setId(id);
				  ball.setVx(vx);
				  ball.setVy(vy);
				  gos.add(ball);
				  GameModel.getInstance().setBall(ball);
				  GameModel.getInstance().getBalls().add(ball);
			  }
			  if (doc.get("type").equals("Firm")) {
				  FirmAsteroid firmAsteroid = new FirmAsteroid((int) width, (int) height, x, y);
				  //firmAsteroid.setId(id);
				  firmAsteroid.setMovingObject(movingobject);
				  firmAsteroid.setWidth(width);
				  firmAsteroid.setHeight(height);
				  firmAsteroid.setRadius((int) width);

				  gos.add(firmAsteroid);
				  GameModel.getInstance().getAsteroids().add(firmAsteroid);
			  }
			  if (doc.get("type").equals("Simple")) {
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
			  if (doc.get("type").equals("Gift")) {
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
			  if (doc.get("type").equals("Explosive")) {
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
			  if (doc.get("type").equals("Cooperative")) {
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
			  if (doc.get("type").equals("Protecting")) {
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
			  if (doc.get("type").equals("Repairing")) {
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
			  if (doc.get("type").equals("Timewasting")) {
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
			  if (doc.get("type").equals("Surprising")) {
				  
				  SuprisingAlien alien = GameModel.getInstance().createASuprisingAlien();
				  alien.setWidth(width);
				  alien.setHeight(height);
				  alien.setX(x);
				  alien.setY(y);
				  alien.setVx(vx);
				  alien.setVy(vy);
				  alien.setMovingObject(movingobject);
				  
			  }
			  
			  
			  
			  //System.out.println(((DBCursor) it).curr().get("type"));
			  //System.out.println(it.next());
			  i++;	
		  }
		
	}
	
	public void setLive(int live) {
		// TODO Auto-generated method stub
		
		///filllll
		this.nbLive = live;
	}
	
	
	

}
