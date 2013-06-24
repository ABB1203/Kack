package game.level;

import game.Game;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.entity.mob.AI.AI;
import game.entity.projectile.Projectile;
import game.gfx.Screen;
import game.level.tile.Tile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class Level {

	private static Random random = new Random();

	protected int width, height;
	protected int[] tiles;

	protected Game game;

	private List<AI> AIs = new ArrayList<AI>();
	private List<Mob> players = new ArrayList<Mob>();
	private List<Mob> mobs = new ArrayList<Mob>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();

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
		}

	}

	protected void generateLevel() {

	}

	public void tick(int xOffset, int yOffset) {
		for (int i = 0; i < players.size(); i++) {
			((Player) players.get(i)).tick(xOffset, yOffset);
		}

		for (int i = 0; i < AIs.size(); i++) {
			AIs.get(i).tick(players.get(0));
		}

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).tick();
		}
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
		int x1 = (xOffset + screen.width + game.getTileSize()) >> 4;
		int y0 = yOffset >> 4;
		int y1 = (yOffset + screen.height + game.getTileSize()) >> 4;
		// These values define the rendering region of the screen

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}

		for (int i = 0; i < players.size(); i++) {
			players.get(i).render(screen);
		}

		for (int i = 0; i < AIs.size(); i++) {
			AIs.get(i).render(screen);
		}

	}

	public void addAI(AI ai) {
		AIs.add(ai);
		mobs.add(ai);
	}

	public void addPlayer(Mob m) {
		players.add(m);
		mobs.add(m);
	}

	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}
	
	public void removeMob(Mob m) {
		mobs.remove(m);
		if(m instanceof AI) AIs.remove(m);
		if(m instanceof Player) players.remove(m);
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) return Tile.voidTile;
		if (tiles[x + y * width] == 0xff00ff00) return Tile.grass;
		if (tiles[x + y * width] == 0xffffff00) return Tile.flower;
		if (tiles[x + y * width] == 0xffcc6666) return Tile.rock;
		return Tile.voidTile;
	}

	public int getWidth() {
		// From tile width to pixel width
		return width << 4;
	}

	public int getHeight() {
		// From tile width to pixel width
		return height << 4;
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public List<AI> getAIs() {
		return AIs;
	}

	public List<Mob> getPlayers() {
		return players;
	}
}
