package com.gamesinteractive;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Misc {

	public static void setTransparency(Graphics g, float value) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, value));
	}
	
	public static void Rect(Graphics g, int x, int y, int w, int h, Color c) {
		//Misc.setTransparency(g, transparency);
		g.setColor(c);
		g.fillRect(x, y, w, h);
		//Misc.setTransparency(g, 1.0f);
	}
	
}
