package technicalservices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.controllers.GameController;
import domain.gameobjects.GameObject;

public class SaveLoadDatabaseAdapter implements SaveLoadAdapter{
	SaveLoadDatabaseService dBservice;
	
	
	
	public SaveLoadDatabaseAdapter() {
		this.dBservice = new SaveLoadDatabaseService();
	}



	@Override
	public void saveGame(List<GameObject> gameObjects) {
		// TODO Auto-generated method stub
		
       dBservice.Save(gameObjects);
	}

	@Override
	public void loadGame(List<GameObject> gameObjects) {
		// TODO Auto-generated method stub
		dBservice.Load(gameObjects);
		
	}
	
	

}
