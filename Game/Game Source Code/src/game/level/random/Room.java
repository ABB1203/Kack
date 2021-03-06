package game.level.random;

import java.awt.Rectangle;
import java.util.Random;

public class Room {

	protected int x, y, width, height, size;
	protected boolean[] pixels;
	protected boolean removed = false;
	protected final Random random = new Random();
	protected boolean[] tiles;

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
		
		tiles = new boolean[width * height];
		for(int i = 0; i < tiles.length; i++) tiles[i] = true;

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
	
//	protected void roundCorners() {
//		int xIndex = 0, yIndex = 0;
//		int x = this.x;
//		int y = this.y;
//		
//		for(;true;x++) {
//			if(getRectangle().contains(x, y)) xIndex++;
//			else break;
//		}
//		System.out.println(xIndex + ":" + width);
//	}

	public boolean isDisplayable(int xPos, int yPos) {
//		int xIndex = 0;
//		int yIndex = 0;
//		for (int x = xPos; true; x++) {
//			if (getRectangle().contains(x, yPos) && !getRectangle().contains(x, yPos-1)) xIndex++;
//			else break;
//		}
//		
//		if(xIndex != 0)
//		System.out.println(xIndex);
//		
//		for (int y = yPos; true; y++) {
//			if (getRectangle().contains(xPos, y)) yIndex++;
//			else break;
//		}

		
//		if (xPos == x && yPos == y) return false;
//		if (xPos == x + width - 1 && yPos == y) return false;
//		if (xPos == x && yPos == y + height - 1) return false;
//		if (xPos == x + width - 1 && yPos == y + height - 1) return false;
		return true;
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
