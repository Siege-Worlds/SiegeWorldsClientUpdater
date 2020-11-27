package com.gamesinteractive;


import java.awt.Canvas;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class GFrame extends Canvas {
	
	public Point initialClick;
	public JFrame container;

	/**
	 * Construct our game and set it running.
	 */
	public GFrame() {
		container = new JFrame(Constants.APP_NAME);
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(Constants.SCREEN_SIZE);
		panel.setSize(Constants.SCREEN_SIZE);
		panel.setLayout(null);
		setBounds(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		panel.add(this);
		setIgnoreRepaint(true);
		container.setUndecorated(true);
		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		container.setLocationRelativeTo(null);
		
		//MotionPanel mp = new MotionPanel(container);
		
		/**
		 * Event for closing window if requested
		 */
		container.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		/**
		 * request the focus so key events come to us
		 */
		// requestFocus();

		/**
		 * create the buffering strategy which will allow AWT to manage our
		 * accelerated graphics
		 */
		createBufferStrategy(2);
		strategy = getBufferStrategy();

		/**
		 * Adding mouse detection for login interface
		 */
		addMouseListener(new MouseDetection());

		addMouseMotionListener(new MouseMotionListener());

		/**
		 * Keyboard detection
		 */
		addKeyListener(new KeyboardInput());

	}

	/**
	 * Entry point to the program
	 */
	public static void main(String args[]) {
		//new Console(true);
		frame = new GFrame();
		Client.gameIsRunning = true;
		/**
		 * Start running the game loop
		 */
		Client.gameLoop();
	}
	
	public static GFrame getInstance() {
		return frame;
	}
	
	public static GFrame frame;

	/**
	 * The stragey that allows us to use accelerate page flipping
	 */
	public static BufferStrategy strategy;

	/**
	 * The generated serialVersionUID for extending the Canvas class
	 */
	private static final long serialVersionUID = -5414740185856158617L;

}
