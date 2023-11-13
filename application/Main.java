package application;

import application.controllers.BuildController;
import application.controllers.GameController;
import application.controllers.LoginController;
import application.controllers.RegisterController;
import application.models.BuildModel;
import application.models.GameModel;
import ui.GameView;

public class Main {

	public static void main(String[] args) {

		GameView gameView = GameView.getInstance();
		
		LoginController loginController = new LoginController(gameView);
		RegisterController registerController = new RegisterController(gameView);
		
		/*
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		try {
			while (!gameView.getCurrentPanel().equals("buildPanel")) {
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BuildModel buildModel = new BuildModel(gameView.getScreenWidth(),gameView.getScreenHeight());
		BuildController buildController = new BuildController(buildModel, gameView);
		
		System.out.println("Hello");
		
		/*
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		try {
			while (!buildModel.isFinish()) {
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Hello 2");
		//GameModel gameModel = new GameModel(gameView.getScreenWidth(),gameView.getScreenHeight(),gameView.getDelay(), buildModel);
		
		GameModel.initializeGameModel(gameView.getScreenWidth(),gameView.getScreenHeight(),gameView.getDelay(), buildModel);
		GameModel gameModel = GameModel.getInstance();

		GameController gameController = new GameController(gameModel, gameView);

	}

}
