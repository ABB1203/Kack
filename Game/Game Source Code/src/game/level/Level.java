package game.level;

import game.Game;
import game.entity.drop.Drop;
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

import javax.imageio.ImageIO;

public class Level {

	protected int width, height;
	protected int[] tiles;

	protected Game game;

	private List<AI> AIs = new ArrayList<AI>();
	private List<Player> players = new ArrayList<Player>();
	private List<Mob> mobs = new ArrayList<Mob>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Drop> drops = new ArrayList<Drop>();

	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}

	public Level(int difficulty) {

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

	public void tick() {
		for (Mob p : players) {
			p.tick();
		}

		for (Mob ai : AIs) {
			if (players.size() != 0) ai.tick();
		}
		
		for (Drop d : drops) {
			d.tick();
		}

		for (Projectile p : projectiles) {
			p.tick();
		}
		cleanLists();
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
		int x1 = (xOffset + screen.width + Game.getTileSize()) >> 4;
		int y0 = yOffset >> 4;
		int y1 = (yOffset + screen.height + Game.getTileSize()) >> 4;
		// These values define the rendering region of the screen

		
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}

		for (Drop d : drops) {
			d.render(screen);
		}
		
		for (Projectile p : projectiles) {
			p.render(screen);
		}

		for (AI ai : AIs) {
			ai.render(screen);
		}

		for (Player p : players) {
			p.render(screen);
		}

		for (Mob m : mobs) {
			m.renderHealthBar(screen);
		}

	}

	public void addAI(AI ai) {
		AIs.add(ai);
		mobs.add(ai);
	}

	public void addPlayer(Player p) {
		players.add(p);
		mobs.add(p);
	}

	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}
	
	public void addDrop(Drop d) {
		drops.add(d);
	}

	public void removeMob(Mob m) {
		mobs.remove(m);
		if (m instanceof AI) AIs.remove(m);
		if (m instanceof Player) players.remove(m);
	}
	
	public void removeDrop(Drop d) {
		drops.remove(d);
	}
	
	public void removeProjectile(Projectile p) {
		projectiles.remove(p);
	}

	protected void cleanLists() {
		cleanPlayers();
		cleanAIs();
		cleanDrops();
		cleanProjectiles();
	}

	protected void cleanPlayers() {
		boolean updated = true;
		while (updated) {
			updated = false;
			for (Player p : players) {
				if (p.isRemoved()) {
					removeMob(p);
					updated = true;
					break;
				}
			}
		}
	}
	
	protected void cleanAIs() {
		boolean updated = true;
		while (updated) {
			updated = false;
			for (AI ai : AIs) {
				if (ai.isRemoved()) {
					removeMob(ai);
					updated = true;
					break;
				}
			}
		}
	}
	
	protected void cleanDrops() {
		boolean updated = true;
		while (updated) {
			updated = false;
			for (Drop d : drops) {
				if (d.isRemoved()) {
					removeDrop(d);
					updated = true;
					break;
				}
			}
		}
	}

	private void cleanProjectiles() {
		boolean updated = true;
		while (updated) {
			updated = false;
			for (Projectile p : projectiles) {
				if (p.isRemoved()) {
					removeProjectile(p);
					updated = true;
					break;
				}
			}
		}
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

	public int getTileWidth() {
		return width;
	}

	public int getTileHeight() {
		return height;
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public List<AI> getAIs() {
		return AIs;
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	public List<Drop> getDrops() {
		return drops;
	}
}
