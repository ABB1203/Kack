package game.gfx;

public class Sprite {

	private int size;
	private int x, y;
	public int[] pixels;
	private SpriteSheet sheet;
	
	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite(16, 0xffff);
	
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		this.x = x * size;
		this.y = y * size;
		this.size = size;
		this.sheet = sheet;
		pixels = new int[size * size];
		loadSprite();
	}

	public Sprite(int size, int color) {
		this.size = size;
		pixels = new int[size * size];
		setColor(color);
	}

	private void setColor(int color) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
		}
	}

	private void loadSprite() {
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				pixels[x + y * size] = sheet.pixels[(this.x + x) + (this.y + y) * sheet.getSize()];
			}
		}
	}
	
	public int getSize() {
		return size;
	}
}
