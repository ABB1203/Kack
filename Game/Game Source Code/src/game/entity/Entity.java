package game.entity;

import game.Game;
import game.gfx.Screen;
import game.level.Level;
import game.level.tile.Tile;

import java.util.Random;

public abstract class Entity {

	protected double x = 0, y = 350;
	protected boolean removed = false;
	protected final Random random = new Random();
	protected Game game;
	protected Level level = game.getLevel();
	
//	public void init(Level level) {
//		level = game.getLevel();
//	}

	public void tick(int x, int y) {}
	
	public void tick() {

	}

	public void render(Screen screen) {
		
	}

	public boolean hasCollided(double xDir, double yDir) {
		// test coordinates for the collision box
		// these variables should be specific for each mob since their collision box is different
		int xMin = 0;
		int xMax = 16;
		int yMin = 0;
		int yMax = 16;

		if (isSolidTile(xDir, yDir, xMin, yMin)) return true;
		if (isSolidTile(xDir, yDir, xMax, yMin)) return true;
		if (isSolidTile(xDir, yDir, xMin, yMax)) return true;
		if (isSolidTile(xDir, yDir, xMax, yMax)) return true;
		return false;
	}

	public boolean isSolidTile(double xDir, double yDir, int x, int y) {
		Tile newTile = level.getTile((int)(this.x + x + xDir) >> game.getTileShift(), (int)(this.y + y + yDir) >> game.getTileShift());
		if (newTile.isSolid()) return true;
		return false;
	}

	public void remove() {
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}
}
