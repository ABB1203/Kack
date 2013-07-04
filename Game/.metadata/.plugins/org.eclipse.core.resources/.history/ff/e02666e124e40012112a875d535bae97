package game.entity;

import game.Game;
import game.gfx.Screen;
import game.level.Level;
import game.level.tile.Tile;

import java.awt.Rectangle;
import java.util.Random;

import org.w3c.dom.css.Rect;

public abstract class Entity {

	protected boolean removed = false;
	protected final Random random = new Random();
	protected Game game;
	protected Level level;
	protected int xMin = 0;
	protected int xMax = 15;
	protected int yMin = 0;
	protected int yMax = 15;
	
	protected double x = random.nextInt(31) * 16, y = random.nextInt(31) * 16;


	public abstract void render(Screen screen);

	protected boolean hasCollided(double xDir, double yDir) {
		// test coordinates for the collision box
		// these variables should be specific for each mob since their collision box is different

		if (isSolidTile(xDir, yDir, xMin, yMin)) return true;
		if (isSolidTile(xDir, yDir, xMax, yMin)) return true;
		if (isSolidTile(xDir, yDir, xMin, yMax)) return true;
		if (isSolidTile(xDir, yDir, xMax, yMax)) return true;
		
		// These for loops check every outer pixel, not just the corner pins
		
//		for (int y = yMin; y <= yMax; y++) {
//			for (int x = xMin; x != xMax; x = xMax) {
//				if (isSolidTile(xDir, yDir, x, y)) return true;
//			}
//		}
//
//		for (int y = yMin; y != yMax; y = yMax) {
//			for (int x = xMin; x <= xMax; x++) {
//				if (isSolidTile(xDir, yDir, x, y)) return true;
//			}
//		}
		

		
		return false;
	}

	protected boolean isSolidTile(double xDir, double yDir, int x, int y) {
		Tile newTile = level.getTile((int) (this.x + x + xDir) >> game.getTileShift(), (int)(this.y + y + yDir) >> game.getTileShift());
		if (newTile.isSolid()) return true;
		return false;
	}

	public void remove() {
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}
	
	public int getXMin() {
		return (int) (x + xMin);
	}
	
	public int getXMax() {
		return (int) (x+xMax);
	}
	
	public int getYMin() {
		return (int) (y+yMin);
	}
	
	public int getYMax() {
		return (int) (y+yMax);
	}
	
	public Rectangle getCollisionBox() {
		return getCollisionBox(0, 0);
	}
		
	public Rectangle getCollisionBox(double xDir, double yDir) {
		return new Rectangle((int)(x + xMin + xDir), (int)(y + yMin + yDir), xMax - xMin, yMax- yMin);
	}
}
