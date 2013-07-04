package game.level.tile;

import game.gfx.Screen;
import game.gfx.Sprite;

public class Tile {
	
	public Sprite sprite;
	private int width, height;
	private static int shiftX, shiftY;

	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile flower = new GrassTile(Sprite.flower);
	public static Tile rock = new RockTile(Sprite.rock);
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
		width = sprite.getWidth();
		height = sprite.getHeight();
		shiftX = (int) (Math.log(width) / Math.log(2));
		shiftY = (int) (Math.log(height) / Math.log(2));
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << shiftX, y << shiftY, this);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isSolid() {
		return false;
	}
}
