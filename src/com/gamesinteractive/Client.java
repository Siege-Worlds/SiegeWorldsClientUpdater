package com.gamesinteractive;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Client {
	
	/**
	 * The main game loop.
	 */
	public static void gameLoop() {

		init();
		
		while (gameIsRunning) {

			Long startTime = System.nanoTime() / 1000000L;

			Graphics2D g = (Graphics2D) GFrame.strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, 1200, 750);

			update();

			draw(g);

			g.dispose();

			GFrame.strategy.show();

			try {
				//some anti-lag for the noobs using windows 97
				Long sleepTime = 20 - (System.nanoTime() / 1000000L - startTime);
				if (sleepTime > 0) {
					// 50 frames per second
					Thread.sleep(sleepTime);
					gameTicks++;
				}
			} catch (Exception e) {
			}
		}
	}

	public static void init() {
		SplashScreen.init();
		MainMenu.init();
	}
	
	/**
	 * Updating the game
	 */
	public static void update() {
		switch (gameStage) {
		case 0:
			//cacheDownload.update();
			SplashScreen.update();
			break;
		case 1:
			MainMenu.update();
			break;
		}
	}

	/**
	 * Drawing the game
	 */
	public static void draw(Graphics g) {
		switch (gameStage) {
		case 0:
			SplashScreen.draw(g);
			break;
		case 1:
			MainMenu.draw(g);
			break;
		}
		
		
	}

	/**
	 * Allows other classes to change the games stage
	 * 
	 * @param stage
	 *            the stage to set the game to
	 * 
	 *            0 = Download Cache 1 = Loading 2 = menu 3 = cutscene /
	 *            storyline 4 = game 5 = end
	 */
	public static void setStage(int stage) {
		gameStage = stage;
		switch (stage) {
		case 2:
		//	MenuScreen.init();
			break;
		
		}
	}
	
	
	/**
	 * 
	 * @return the game state
	 */
	public static int getStage() {
		return gameStage;
	}

	/**
	 * the ammount of game ticks itterated
	 */
	public static int gameTicks = 0;

	/**
	 * The stage of the game
	 */
	private static int gameStage = 0;

	/**
	 * the mouse location, avaliable for all classes
	 */
	public static Point mouseLocation;

	/**
	 * Game is running
	 */
	public static Boolean gameIsRunning = false;

}
