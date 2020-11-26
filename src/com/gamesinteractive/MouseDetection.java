package com.gamesinteractive;


import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseDetection implements MouseListener {
	
	public static Point firstClick;
	public static Point secondClick;
	
	private static Rectangle muteButton = new Rectangle(722,6,24,22);
	
	public void mouseClicked(MouseEvent click) {

	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent click) {
			GFrame.getInstance().initialClick = click.getPoint();
	}

	public void mouseReleased(MouseEvent click) {
		Console.print("Mouse pressed x: " + (click.getX() 
				//+ World.offset.x
				) + ", y: " + (click.getY() 
						//+ World.offset.y
						));
		if (Client.getStage() == 1) {
			MainMenu.mouseReleased(click);
		}
		updateClicks(click);
		
		
	}
	
	private void updateClicks(MouseEvent click) {
		if (firstClick == null) {
			firstClick = new Point(click.getX(), click.getY());
		} else if (secondClick == null) {
			secondClick = new Point(click.getX(), click.getY());
		} else {
			firstClick = secondClick;
			secondClick = new Point(click.getX(), click.getY());
		}
	}
}
