package technicalservices;

import java.util.ArrayList;
import java.util.List;

import domain.gameobjects.GameObject;

public interface SaveLoadAdapter {
	
	void saveGame(List<GameObject> gameObjects);
	void loadGame(List<GameObject> gameObjects);
	

}
