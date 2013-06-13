package game.entity;

import game.gfx.Screen;
import game.level.Level;

import java.util.Random;

public abstract class Entity {

	protected int x = 100, y;
	protected boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	
	public void init(Level level) {
		this.level = level;
	}

	public void tick() {

	}

	public void render(Screen screen) {

	}

	public void remove() {
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}
}
