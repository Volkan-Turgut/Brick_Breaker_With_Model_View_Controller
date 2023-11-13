package application.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

import javax.swing.Timer;

import application.models.BuildModel;
import application.models.GameModel;
import domain.gameobjects.GameObject;
import domain.gameobjects.Paddle;
import domain.gameobjects.asteroids.Asteroid;
import domain.gameobjects.asteroids.ExplosiveAsteroid;
import domain.gameobjects.asteroids.FirmAsteroid;
import domain.gameobjects.asteroids.SimpleAsteroid;
import ui.GameView;
import ui.UIGameObject;

public class LoginController {
	GameView gameView;
	
	public LoginController(GameView gameView) {
		this.gameView = gameView;
		gameView.setFocusable(true);
		gameView.requestFocus();
		gameView.requestFocusInWindow();

		gameView.getLoginButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GameView gameView = GameView.getInstance();
				System.out.println("hel");
				gameView.switchBuildPanel();
			}
			
		});
		
		gameView.getRegisterButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("hello");
				GameView gameView = GameView.getInstance();
				gameView.switchRegisterPanel();
			}
			
		});
		
		
		gameView.setVisible(true);
		
	}
	
}
