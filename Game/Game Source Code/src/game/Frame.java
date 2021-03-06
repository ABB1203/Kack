package game;

import javax.swing.JFrame;

public class Frame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 300;
	public static final int HEIGHT = WIDTH * 10 / 16;
	public static final int SCALE = 3;
	
	public static final String NAME = "Game Name";
	
	private Game game;

	public Frame() {
		game = new Game("this is the level path", WIDTH, HEIGHT, SCALE, 16 /*tile size*/, this);
		
		setSize(WIDTH * SCALE, HEIGHT * SCALE);
		setResizable(true);
		setTitle(NAME);
		add(game);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		game.start();
	}
	
	public static void main (String[] args) {
		new Frame();
	}
}
