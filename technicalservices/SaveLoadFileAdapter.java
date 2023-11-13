package technicalservices;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import domain.gameobjects.GameObject;

public class SaveLoadFileAdapter implements SaveLoadAdapter{
	SaveLoadFileService fileService;
	
	public SaveLoadFileAdapter() {
		this.fileService = new SaveLoadFileService();
	}
	@Override
	public void loadGame(List<GameObject> gameObjects) {
		// TODO Auto-generated method stub
		fileService.load(gameObjects);
	}

	@Override
	public void saveGame(List<GameObject> gameObjects) {
		// TODO Auto-generated method stub
		fileService.save(gameObjects);
		
	}

}
