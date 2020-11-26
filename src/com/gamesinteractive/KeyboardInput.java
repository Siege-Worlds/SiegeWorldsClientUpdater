package com.gamesinteractive;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Handles all keypress events
 * 
 * @author Jake O'Connor
 * www.prosperitygames.co.uk
 */
public class KeyboardInput extends KeyAdapter {

	private static char[] Allowed = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ' };
	
	public static boolean isAllowed(char c) {
		for (int i = 0; i < Allowed.length; i++) {
			if (c == Allowed[i]) {
				return true;
			}
		}
		return false;
	}

	public static String removeLastChar(String s) {
		if (s == null || s.length() == 0) {
			return s;
		}
		return s.substring(0, s.length() - 1);
	}

	public void keyPressed(KeyEvent e) {
		Console.print("Key pressed: " + e.getKeyChar());
		/*if (Client.getStage() == Constants.MenuScreen) {
			MenuScreen.keyPressed(e);
		}
		if (Client.getStage() == Constants.GameScreen) {
			GameScreen.keyPressed(e);
		}*/
	}

	public void keyReleased(KeyEvent e) {
		/*if (Client.getStage() == Constants.GameScreen) {
			GameScreen.keyReleased(e);
		}*/
	}
}
