package game.level.tile;

import game.gfx.Screen;
import game.gfx.Sprite;

public class Tile {

	private int x, y;
	public Sprite sprite;
	private int tileSize;
	private static int shift;

	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile flower = new GrassTile(Sprite.flower);
	public static Tile rock = new RockTile(Sprite.rock);
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
		tileSize = sprite.getSize();
		shift = (int) (Math.log(tileSize) / Math.log(2));
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << shift, y << shift, this);
	}
	
	public int getSize() {
		return tileSize;
	}
	
	public boolean isSolid() {
		return false;
	}
}
