package game.gfx;

import game.Game;
import game.entity.mob.Mob;
import game.entity.projectile.Projectile;
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
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void renderTile(int xPos, int yPos, Tile tile) {
		xPos -= xOffset;
		yPos -= yOffset;

		for (int y = 0; y < tile.getHeight(); y++) {
			int yAbsolute = y + yPos;
			for (int x = 0; x < tile.getWidth(); x++) {
				int xAbsolute = x + xPos;
				if (xAbsolute < -tile.getWidth() || xAbsolute >= width || yAbsolute < 0 || yAbsolute >= height) break;
				if (xAbsolute < 0) xAbsolute = 0;
				pixels[xAbsolute + yAbsolute * width] = tile.sprite.pixels[x + y * tile.getWidth()];
			}
		}
	}

	public void renderMob(int xPos, int yPos, Mob mob) {
		xPos -= xOffset;
		yPos -= yOffset;
		for (int y = 0; y < mob.getSprite().getHeight(); y++) {
			int yAbsolute = y + yPos;
			for (int x = 0; x < mob.getSprite().getWidth(); x++) {
				int xAbsolute = x + xPos;
				if (xAbsolute < -mob.getSprite().getWidth() || xAbsolute >= width || yAbsolute < 0 || yAbsolute >= height) break;
				if (xAbsolute < 0) xAbsolute = 0;

				pixels[xAbsolute + yAbsolute * width] = mob.getSprite().pixels[x + y * mob.getSprite().getWidth()];

			}
		}
	}

	public void renderProjectile(int xPos, int yPos, Projectile p) {
		xPos -= xOffset;
		yPos -= yOffset;
		for (int y = 0; y < p.getSprite().getHeight(); y++) {
			int yAbsolute = y + yPos;
			for (int x = 0; x < p.getSprite().getWidth(); x++) {
				int xAbsolute = x + xPos;
				if (xAbsolute < -p.getSprite().getWidth() || xAbsolute >= width || yAbsolute < 0 || yAbsolute >= height) break;
				if (xAbsolute < 0) xAbsolute = 0;

				int color = p.getSprite().pixels[x + y * p.getSprite().getWidth()];
				if (color != 0xffEE00AC) pixels[xAbsolute + yAbsolute * width] = p.getSprite().pixels[x + y * p.getSprite().getWidth()];
			}
		}
	}

	public void renderHealthBar(int xPos, int yPos, Mob m) {

		xPos -= xOffset;
		yPos -= yOffset;
		for (int y = 0; y < m.getHealthBarSprite().getHeight(); y++) {
			int yAbsolute = y + yPos;
			for (int x = 0; x < m.getHealthBarSprite().getWidth(); x++) {
				int xAbsolute = x + xPos;

				if (xAbsolute < -m.getHealthBarSprite().getWidth() || xAbsolute >= width || yAbsolute < 0 || yAbsolute >= height) break;
				if (xAbsolute < 0) xAbsolute = 0;

				int color = 0;

				if (m.getHealthBarSprite().pixels[x + y * m.getHealthBarSprite().getWidth()] == 0xff00ffff) {
					int span = m.getHealthBarSprite().getWidth();

					if (m.getLastHealth() / m.getMaxHealth() >= (x * 1.0 / span * 1.0) || x <= 1) {

						if (m.getHealth() / m.getMaxHealth() >= (x * 1.0 / span * 1.0) || x <= 1) color = 0xff00;
						else color = 0xffff00;
					}

					else color = 0xff0000;

					// System.out.println(m.getHealth() + " : " +
					// m.getLastHealth() + " : " + m.getMaxHealth());

				} else {
					color = m.getHealthBarSprite().pixels[x + y * m.getHealthBarSprite().getWidth()];
				}
				// int color = m.getHealthBarSprite().pixels[x + y *
				// m.getHealthBarSprite().getWidth()];
				if (color != 0xffEE00AC) pixels[xAbsolute + yAbsolute * width] = color;
			}
		}
	}

	public void setOffset(int x, int y) {
		xOffset = x;
		yOffset = y;
	}

	public int getXOffset() {
		return xOffset;
	}

	public int getYOffset() {
		return yOffset;
	}

}
