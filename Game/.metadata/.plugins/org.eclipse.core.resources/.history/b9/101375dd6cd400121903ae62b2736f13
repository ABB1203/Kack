package game.gfx;

import game.Game;
import game.entity.mob.Player;
import game.level.tile.Tile;

import java.util.Random;

public class Screen {

	private final Random random = new Random();
	public int width, height;
	private int xOffset, yOffset;
	
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
	
	public void renderTile(int xPos, int yPos, Tile tile) {
		xPos -= xOffset;
		yPos -= yOffset;
		
		for(int y = 0; y < tile.getSize(); y++) {
			int yAbsolute = y + yPos;
			for(int x = 0; x < tile.getSize(); x++) {
				int xAbsolute = x + xPos;
				if(xAbsolute < -tile.getSize() || xAbsolute >= width || yAbsolute < 0 || yAbsolute >= height) break;
				if(xAbsolute < 0) xAbsolute = 0;
				pixels[xAbsolute + yAbsolute * width] = tile.sprite.pixels[x + y * tile.getSize()];
			}
		}
	}
	
	public void renderPlayer(int xPos, int yPos, Player player) {
		xPos -= xOffset;
		yPos -= yOffset;
		
		for(int y = 0; y < player.getSprite().getSize(); y++) {
			int yAbsolute = y + yPos;
			
			if(yAbsolute < 0) yAbsolute = 0;
			if(yAbsolute > height) yAbsolute = height;
			
			for(int x = 0; x < player.getSprite().getSize(); x++) {
				int xAbsolute = x + xPos;
				

				if(xAbsolute >= width || yAbsolute >= height) break;
				if(xAbsolute < 0) xAbsolute = 0;
				pixels[xAbsolute + yAbsolute * width] = player.getSprite().pixels[x + y * player.getSprite().getSize()];
				
			}
		}
	}
	
	public void setOffset(int x, int y) {
		xOffset = x;
		yOffset = y;
	}

}
