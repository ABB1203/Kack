package game.level;

import game.Game;
import game.gfx.Screen;
import game.level.tile.Tile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Level {

	private static Random random = new Random();

	protected int width, height;
	protected int[] tiles;

	protected Game game;

	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}

	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(Level.class.getResource(path));
			width = image.getWidth();
			height = image.getHeight();
			tiles = new int[width * height];
			tiles = image.getRGB(0, 0, width, height, null, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Could not get image from \"" + path + "\"!");
		}

	}

	protected void generateLevel() {

	}

	public void render(int xOffset, int yOffset, Screen screen) {
		if (xOffset < 0) xOffset = 0;
		if (xOffset > ((width << 4) - screen.width)) xOffset = ((width << 4) - screen.width);
		if (yOffset < 0) yOffset = 0;
		if (yOffset > ((height << 4) - screen.height)) yOffset = ((height << 4) - screen.height);

		screen.setOffset(xOffset, yOffset);

		// Every "4" can be replaced with "game.getMinTileShift()" to get the
		// shift value depending on the size of the tiles
		int x0 = xOffset >> 4; // from pixel position to tile position
		int x1 = (xOffset + screen.width + game.getMinTileSize()) >> 4;
		int y0 = yOffset >> 4;
		int y1 = (yOffset + screen.height + game.getMinTileSize()) >> 4;
		// These values define the rendering region of the screen

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}
	}

	protected Tile getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) return Tile.voidTile;
		if (tiles[x + y * width] == 0xff00ff00) return Tile.grass;
		if (tiles[x + y * width] == 0xffffff00) return Tile.flower;
		if (tiles[x + y * width] == 0xffcc6666) return Tile.rock;
		return Tile.voidTile;
	}
}
