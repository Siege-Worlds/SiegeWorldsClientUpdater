package com.gamesinteractive;


import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * A class that gives the position of the mouse relitive to the
 * JFrame
 * 
 * @author Jake O'Connor
 * www.prosperitygames.co.uk
 */
public class MouseMotionListener implements java.awt.event.MouseMotionListener {

	public void mouseDragged(MouseEvent mouse) {
	}

	public void mouseMoved(MouseEvent mouse) {
		Client.mouseLocation = new Point(mouse.getX(), mouse.getY());
	}

}
