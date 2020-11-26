package com.gamesinteractive;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class SplashScreen {
	
	public static final int DELAY_TIME = 200;
	public static long startTime;
	
	public static void init() {
		background = new Sprite(1200,750,"logo.png");
		startTime = System.currentTimeMillis();
	}
	
	public static void update() {
		if (System.currentTimeMillis() - startTime > DELAY_TIME) {
			Client.setStage(1);
		}
	}
	
	
	public static void draw(Graphics g) {
		background.draw(g, 0, 0, 1200, 750);
	}

	
	static Sprite background;
}
