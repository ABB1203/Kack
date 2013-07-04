package game.level.random;

import java.awt.Rectangle;
import java.util.Random;

public class Room {

	protected int x, y, width, height, size;
	protected boolean[] pixels;
	protected boolean removed = false;
	protected final Random random = new Random();

	protected RandomLevel level;

	public Room(int w, int h, RandomLevel randomLevel) {
		level = randomLevel;

		generateDimensions(w, h);
	}
	
	public Room(int x, int y, int size, RandomLevel randomLevel) {
		level = randomLevel;
		this.x = x;
		this.y = y;
		width = size;
		height = size;
	}

	protected void generateDimensions(int w, int h) {
		width = (random.nextBoolean() ? w : h);
		height = ((width == w) ? h : w);

		if (w / 4 > 0) {
			width += random.nextInt(w / 4);
			height += random.nextInt(w / 4);
		} else {
			width += random.nextInt(1);
			height += random.nextInt(1);
		}

		x = random.nextInt(level.getTileWidth() - width);
		y = random.nextInt(level.getTileHeight() - height);
	}

	public Rectangle getRectangle() {
		Rectangle r = new Rectangle(x, y, width, height);
		return r;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void remove() {
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}
}