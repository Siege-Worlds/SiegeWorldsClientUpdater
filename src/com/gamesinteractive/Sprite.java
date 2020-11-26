package com.gamesinteractive;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


/**
 * The sprite class
 * 
 * @author Jake O'Connor
 * www.prosperitygames.co.uk
 */
public class Sprite {
	
	private int x, y;
		
	/**
	 * Dimensions of the sprite
	 */
	public int width, height;
	
	/**
	 * the "Image" of the sprite
	 */
	public Image image;
	
	/**
	 * Rotation.. because we have badass features in our sprite class
	 */
	private int rotation = 0;
	
	
	/**
	 * Want to load a sprite with a set width and height? no problem..
	 * use this method
	 * @param width the width of the sprite
	 * @param height the height of the sprite
	 * @param image the path for the image file
	 */
	public Sprite(int width, int height, String image) {
		try {
		this.width = width;
		this.height = height;
		//ZipFile zipFile = new ZipFile(Constants.cacheDir + "Cache.dat");
		//ZipEntry zipEntry = zipFile.getEntry(image);
		//InputStream entryStream = zipFile.getInputStream(zipEntry);
	    //this.image = ImageIO.read(entryStream);
		this.image = new ImageIcon(Constants.cacheDir + image).getImage();
		} catch (Exception e) {

			new Exception("Error loading Sprite").printStackTrace();
		}
	}
	
	/**
	 * Loading a sprite and taking the dimensions from the image
	 * @param image the path to the image file
	 */
	public Sprite(String image) {
		try {
		//ZipFile zipFile = new ZipFile(Constants.cacheDir + "Cache.dat");
		//ZipEntry zipEntry = zipFile.getEntry(image);
		//InputStream entryStream = zipFile.getInputStream(zipEntry);
	    //this.image = ImageIO.read(entryStream);
		this.image = new ImageIcon(Constants.cacheDir + image).getImage();
		width = this.image.getWidth(null);
		height = this.image.getHeight(null);
		} catch (Exception e) {
			//WE DONT HANDLE EXCEPTIONS
			// BEACAUSE WE ARE COOL!!!
			new Exception("Error loading Sprite").printStackTrace();
			// lol jk..
		}
	}
	
	/**
	 * Rotates an image.
	 *  (adds rotation instead of setting)
	 * @param degrees the ammount of rotation to add
	 */
	public void addRotation(int degrees){
		if (rotation + degrees > 360) {
			rotation = (360 - rotation) + degrees;
		} else {
			rotation += degrees;
		}
	}
	
	/**
	 * Sets the rotation for a sprite
	 * @param degrees
	 */
	public void setRotation(int degrees) {
		rotation = degrees;	
	}
	
	/**
	 * Draws an image that is rotated, use addRotation(degrees) first to image
	 * @param g Graphics g object
	 * @param x position to be drawn at
	 * @param y position to be drawn at
	 */
	public void drawRotatedImage(Graphics g, int x, int y) {
		AffineTransform reset = new AffineTransform();
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.toRadians(rotation), x + (width/2), y + (height / 2));
		Graphics2D g2 = (Graphics2D) g;
		g2.setTransform(transform);
		g2.drawImage(image, x, y, null);
		g2.setTransform(reset);
	}
	
	/**
	 * Drawing sprite to the screen
	 * @param g graphics object
	 * @param x position to be drawn at
	 * @param y position to be drawn at
	 */
	public void draw(Graphics g, int x, int y) {
		this.x = x;
		this.y = y;
		g.drawImage(image, x, y, width, height, null);
	}
	
	public void draw(Graphics g, int x, int y, Color c) {
		this.x = x;
		this.y = y;
		g.drawImage(image, x, y, width, height, c, null);
	}
	
	/**
	 * Drawing sprites to a size
	 * @param g graphics object
	 * @param x position to be drawn at
	 * @param y position to be drawn at
	 * @param w the width
	 * @param h the height
	 */
	public void draw(Graphics g, int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		g.drawImage(image, x, y, w, h, null);
	}
	
	public void drawClipped(Graphics g, int x, int y, int w, int h) {
		g.clipRect(x,y,w,h);
		g.drawImage(image, x, y, width, height, null);
		g.setClip(0, 0, 1200, 750);
	}
	
	public boolean hover() {
		if (Client.mouseLocation == null)
			return false;
		return new Rectangle(x,y,width,height).contains(new Point(Client.mouseLocation.x, Client.mouseLocation.y));
	}
}
