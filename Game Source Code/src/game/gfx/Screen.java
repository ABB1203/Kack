package game.gfx;

import game.Game;

import java.util.Random;

public class Screen {

	private final Random random = new Random();
	private int width, height;
	
	private Game game;

	public int[] pixels;
	public int[] tiles = new int[8 * 8];

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];

		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = random.nextInt(0xffffff);
		}
	}
	
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void render(int xOffset, int yOffset) {
		
		for (int y = 0; y < height; y++) {
			int yy = y + yOffset;
			for (int x = 0; x < width; x++) {
				int xx = x + xOffset;
				int tileIndex = ((xx >> 4) & 7) + ((yy >> 4) & 7) * 8;
				pixels[x + y * width] = tiles[tileIndex];
			}
		}
	}

}