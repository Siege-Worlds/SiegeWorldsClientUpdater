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
		
		if (GFrame.getInstance().initialClick.y > 100) {
			return;
		}
		
		// get location of Window
        int thisX = GFrame.getInstance().container.getLocation().x;
        int thisY = GFrame.getInstance().container.getLocation().y;

        // Determine how much the mouse moved since the initial click
        int xMoved = mouse.getX() - GFrame.getInstance().initialClick.x;
        int yMoved = mouse.getY() - GFrame.getInstance().initialClick.y;

        // Move window to this position
        int X = thisX + xMoved;
        int Y = thisY + yMoved;
        GFrame.getInstance().container.setLocation(X, Y);
	}

	public void mouseMoved(MouseEvent mouse) {
		Client.mouseLocation = new Point(mouse.getX(), mouse.getY());
	}

}
