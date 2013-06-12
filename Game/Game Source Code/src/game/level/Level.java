package game.level;

import game.Game;
import game.gfx.Screen;
import game.level.tile.Tile;

import java.util.Random;

public class Level {
	
	private static Random random = new Random();

	protected int width = 64, height = 64;
	protected int[] tiles;
	
	protected Game game;
	
	public Level(String path) {
		loadLevel(path);
		generateLevel();
		
		tiles = new int[width * height];
		
		for(int i = 0; i < tiles.length; i++) {
			tiles[i] = random.nextInt(4);
		}
	}
	
	protected void loadLevel(String path) {
		
	}
	
	protected void generateLevel() {
		
	}
	
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		
		int x0 = xScroll >> 4;  // from pixel position to tile position
		int x1 = (xScroll + screen.width /* + game.getMinTileSize()*/) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height /* + game.getMinTileSie()*/) >> 4;
		// These values define the rendering region of the screen
		
		for(int y = y0; y < y1; y++) {
			for(int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}
	}
	
	protected Tile getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) return Tile.voidTile;
		if(tiles[x + y * width] == 1) return Tile.grass;
		return Tile.voidTile;
	}
	
	
}
