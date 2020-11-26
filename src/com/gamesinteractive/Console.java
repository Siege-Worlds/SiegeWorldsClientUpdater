package com.gamesinteractive;


import java.util.Scanner;

/**
 * The developer console which allows commands to be used
 * and information to be printed
 * 
 * @author Jake O'Connor
 * www.prosperitygames.co.uk
 */
public class Console implements Runnable {
	
	private static boolean debugging;
	
	public Console(boolean debugging) {
		this.debugging = debugging;
		new Thread(this).start();
	}
	
	public void run() {
		listen();
	}
	
	/**
	 * Waits till a command is used
	 */
	public void listen() {
		print("Listening for commands");
		
		Scanner read = new Scanner(System.in);
		
		String command = read.nextLine();
		
		processCommand(command);
	}
	
	
	/**
	 * Keeps the loop until the exit command is called
	 */
	public void processCommand(String Command) {
		if (!Command.toLowerCase().equals("exit")) {
			Commands(Command);
			listen();
			return;
		}
		print("Shutting down...");
		System.exit(0);
	}
	
	
	/**
	 * Commands handled here
	 */
	public void Commands(String Command) {
		/**
		 * Changes the gamestate
		 */
		if (Command.toLowerCase().startsWith("setstage")) {
			int args = Integer.parseInt(Command.substring(9));
			Client.setStage(args);
		}
		/**
		 * creates a rectangle from last two points
		 */
		if (Command.toLowerCase().equals("rectangle")) {
			print("x: " + MouseDetection.firstClick.x);
			print("y: " + MouseDetection.firstClick.y);
			print("width: " + (MouseDetection.secondClick.x - MouseDetection.firstClick.x));
			print("height: " + (MouseDetection.secondClick.y - MouseDetection.firstClick.y));
		}
		
		
		
	}
	
	/**
	 * Prints a line of text to the console
	 */
	public static void print(String line) {
		if (debugging)
		System.out.println(line);
	}
	
}
