package com.gamesinteractive;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import com.gamesinteractive.games.SiegeWorlds;
import com.gamesinteractive.games.TheBurningRealm;

public class MainMenu {

	public static void init() {
		swLogo = new Sprite("sw.png");
		tbrLogo = new Sprite("tbr.png");
		gameSelect = new Sprite("gameselect.png");
		siegeWorldsBackground = new Sprite("swbg.png");
		layout = new Sprite("layout.png");
		minimiseWindow = new Sprite("minimise.png");
		closeWindow = new Sprite("close.png");
		theBurningRealmBackground = new Sprite("tbrbg.png");
		siegeWorlds = new SiegeWorlds();
		theBurningRealm = new TheBurningRealm();
		siegeWorlds.init();
		theBurningRealm.init();
	}

	public static void update() {
		if (selectedGame == 0) {
			siegeWorlds.update();
		}
		if (selectedGame == 1) {
			theBurningRealm.update();
		}
	}

	public static void draw(Graphics g) {
		// draw background image
		if (selectedGame == 0) {
		siegeWorldsBackground.draw(g, 0, 0);
		} else if (selectedGame == 1) {
			theBurningRealmBackground.draw(g, 0, 0);
		}
		// draw the layout / overlay
		layout.draw(g, 0, 0);
		// top right close and minimise icons
		closeWindow.draw(g, 1176, 5, closeWindow.hover() ? Color.white : new Color(200, 200, 200));
		minimiseWindow.draw(g, 1153, 5, minimiseWindow.hover() ? Color.white : new Color(200, 200, 200));

		gameSelect.draw(g, 0, (52*selectedGame)+100);
		
		swLogo.draw(g, 12, 116);
		tbrLogo.draw(g, 15, 172);
		
		//game texts
		g.setFont(new Font("Arial", Font.PLAIN, 14));
		g.setColor(Color.white);
		g.drawString("Siege Worlds", 52, 140);
		
		g.setFont(new Font("Arial", Font.PLAIN, 14));
		g.setColor(Color.white);
		g.drawString("The Burning Realm", 52, 192);

		if (selectedGame == 0) {
			siegeWorlds.draw(g);
		}
		if (selectedGame == 1) {
			theBurningRealm.draw(g);
		}

	}

	public static void mouseReleased(MouseEvent click) {
		if (minimiseWindow.hover()) {
			GFrame.getInstance().container.setState(JFrame.ICONIFIED);
		}
		if (closeWindow.hover()) {
			System.exit(0);
		}
		if (selectedGame == 0) {
			siegeWorlds.mouseReleased(click);
		}
		if (selectedGame == 1) {
			theBurningRealm.mouseReleased(click);
		}

		if (new Rectangle(0, 100, 235, 52).contains(click.getPoint())) {
			selectedGame = 0;
		}
		if (new Rectangle(0, 152, 235, 52).contains(click.getPoint())) {
			selectedGame = 1;
		}
	}

	static Sprite siegeWorldsBackground;
	static Sprite theBurningRealmBackground;
	static Sprite layout;
	static Sprite minimiseWindow;
	static Sprite closeWindow;
	
	static Sprite swLogo;
	static Sprite tbrLogo;

	// shows which game is selected
	static Sprite gameSelect;

	static int selectedGame = 0;


	static SiegeWorlds siegeWorlds;
	static TheBurningRealm theBurningRealm;

}
