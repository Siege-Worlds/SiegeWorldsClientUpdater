package com.gamesinteractive;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.io.File;
import java.io.FileInputStream;


public class Constants {
	/**
	 * Name of the game
	 */
	public static final String APP_NAME = "Games Interactive";

	/**
	 * Version of the game
	 */
	public static final Double version = 1.0;
	
	/**
	 * Dimensions of the game screen
	 */
	public static final int SCREEN_WIDTH = 1200;
	
	public static final int SCREEN_HEIGHT = 750;
	
	public static final Dimension SCREEN_SIZE = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
	
	
	/**
	 * Some of the game stages
	 */
	public static final int SPLASH_SCREEN = 0;
	public static final int SELECT_GAME = 1;
	
	/**
	 * Cache directory
	 */
	public static String cacheDir = "Cache/";
		//System.getProperty("user.home") + "/Games_Interactive/";
	
	public static Font GAME_FONT;
	
	public static Font SCOREBOARD_FONT;
		
	/**
	 * load the main font "viking"
	 * @param size the size of the font
	 * @return a Font
	 */
	private static Font loadFont(int size) {
		try {
		return Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(cacheDir + "Font.ttf"))).deriveFont(Font.PLAIN, size);
		} catch (Exception e) {
			new Exception("Font file not found").printStackTrace();
		}
		return null;
	}
	
	/**
	 * this was a fix for the cachedownloader, cant instantly load fonts
	 * when the cache doesnt exist
	 */
	public static void load() {
		GAME_FONT = loadFont(16);
		SCOREBOARD_FONT = loadFont(22);
	}
}
