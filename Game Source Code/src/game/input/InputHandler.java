package game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.Game;

public class InputHandler implements KeyListener{

	private boolean[] keys = new boolean[200];
	public boolean up, down, left, right, escape;
	
	public void tick() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		escape = keys[KeyEvent.VK_ESCAPE];
	}
	
	public InputHandler(Game game) {
		game.addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
	}

}
