package game.gfx;

import game.Game;

public class Sprite {

	private int width, height;
	private int x, y;
	public int[] pixels;
	private SpriteSheet sheet;
	private int tileSize = Game.getTileSize();

	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite flower = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite rock = new Sprite(16, 2, 0, SpriteSheet.tiles);
	public static Sprite hedge = new Sprite(16, 6, 0, SpriteSheet.tiles);
	public static Sprite shot = new Sprite(16, 7, 0, SpriteSheet.tiles);
	public static Sprite barrel = new Sprite(16, 8, 0, SpriteSheet.tiles);
	public static Sprite leaves = new Sprite(16, 9, 0, SpriteSheet.tiles);
	public static Sprite XP = new Sprite(5, 0, 7, SpriteSheet.tiles);
	public static Sprite gun = new Sprite(16, 3, 0, SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite(16, 0xff);
	
	public static Sprite healthBar = new Sprite(32, 16, 0, 10, SpriteSheet.tiles);
	
	public static Sprite[] health = {
		new Sprite(32, 16, 0, 15, SpriteSheet.tiles),
		new Sprite(32, 16, 0, 14, SpriteSheet.tiles),
		new Sprite(32, 16, 0, 13, SpriteSheet.tiles),
		new Sprite(32, 16, 0, 12, SpriteSheet.tiles),
		new Sprite(32, 16, 0, 11, SpriteSheet.tiles)
	};
			
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		this.x = x * tileSize;
		this.y = y * tileSize;
		width = size;
		height = size;
		this.sheet = sheet;
		pixels = new int[width * height];
		loadSprite();
	}
	
	public Sprite(int width, int height, int x, int y, SpriteSheet sheet) {
		this.x = x * tileSize;
		this.y = y * tileSize;
		this.height = height;
		this.width = width;
		this.sheet = sheet;
		pixels = new int[width * height];
		loadSprite();
	}

	public Sprite(int size, int color) {
		width = size;
		height = size;
		pixels = new int[size * size];
		setColor(color);
	}

	private void setColor(int color) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
		}
	}

	private void loadSprite() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sheet.pixels[(this.x + x) + (this.y + y) * sheet.getSize()];
			}
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
