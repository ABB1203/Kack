package game;

import game.entity.mob.Player;
import game.entity.mob.AI.Gunner;
import game.entity.mob.AI.Stalker;
import game.gfx.Screen;
import game.input.InputHandler;
import game.input.Mouse;
import game.level.Level;
import game.level.random.RandomLevel;
import game.weapon.Gun;
import game.weapon.Weapon;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

@SuppressWarnings({ "unused", "serial" })
public class Game extends Canvas implements Runnable {

	private static int width;
	private static int height;
	private static int scale;
	private static int minTileSize;
	private Dimension dimensions;

	private boolean running = false;
	private Thread thread;
	private Frame frame;

	private Screen screen;
	private InputHandler input;
	private Level level;
	private Player player;
	private Weapon weapon;
	private Mouse mouse;

	private BufferedImage image;
	private int[] pixels;

	public Game(String levelPath, int width, int height, int scale, int tileSize, Frame frame) {
		Game.width = width;
		Game.height = height;
		Game.scale = scale;
		Game.minTileSize = tileSize;
		this.frame = frame;

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

		dimensions = new Dimension(width * scale, height * scale);
		setPreferredSize(dimensions);
		setMaximumSize(dimensions);
		setMinimumSize(dimensions);

		screen = new Screen(width, height);
		input = new InputHandler(this);
		mouse = new Mouse(this);
		//level = new Level("/basicLevel.png");
		level = new RandomLevel(1);
		weapon = new Gun();
		player = new Player(input, weapon, this);
		level.addPlayer(player);

		for (int i = 0; i < 10; i++) {
			level.addAI(new Gunner(this));
		}

		for (int i = 0; i < 10; i++) {
			level.addAI(new Stalker(this));
		}
	}

	public synchronized void start() {
		running = true;

		thread = new Thread(this, "Canvas");
		thread.start();
	}

	public synchronized void stop() {
		running = false;

		try {
			thread.join();
		} catch (InterruptedException e) {
			System.out.println("Could not close the game thread!");
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1_000_000_000D / 60D;
		double delta = 0;

		long timer = System.currentTimeMillis();

		int frames = 0;
		int ticks = 0;

		requestFocus();

		while (running) {
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				System.out.println("Could not sleep!");
				e.printStackTrace();
			}

			delta += (System.nanoTime() - lastTime) / nsPerTick;
			lastTime = System.nanoTime();

			while (delta >= 1) {
				ticks++;
				tick();
				render();
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				frame.setTitle("UPS: " + ticks + "\t\t         FPS: " + frames);
				frames = 0;
				ticks = 0;
			}

			frames++;
		}

		stop();
	}

	public void tick() {
		input.tick();
		level.tick();

		if (level.getAIs().size() == 0) {
			System.out.println("You have won!");
			stop();
		}
	}

	int x = 0, y = 0;

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();

		// This centers the player on the screen
		int xOffset = player.getX() - (screen.width - player.getSprite().getWidth()) / 2;
		int yOffset = player.getY() - (screen.height - player.getSprite().getHeight()) / 2;

		level.render(xOffset, yOffset, screen);

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		// Just for testing with the random level class
		if (!input.escape) {
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

			weapon.render(g, screen.getXOffset(), screen.getYOffset(), player, getXScale(), getYScale());
		} else {
			// All the code where one can see the level (just for testing)
			double scale = getWidth() / ((RandomLevel) level).getTileWidth();
			((RandomLevel) level).draw(g, scale, player);
		}

		g.dispose();
		bs.show();
	}

	// ///////////////// GETTERS AND SETTERS////////////////////////////////////
	public static int getScreenWidth() {
		return width * scale;
	}

	public static int getScreenHeight() {
		return height * scale;
	}

	public static int getTileSize() {
		return minTileSize;
	}

	public static int getTileShift() {
		return (int) (Math.log(getTileSize()) / Math.log(2));
	}

	public int getXScale() {
		return (int) (frame.getWidth() / width);
	}

	public int getYScale() {
		return (int) (frame.getHeight() / height);
	}

	public Screen getScreen() {
		return screen;
	}

	public Player getPlayer() {
		return player;
	}

	public Level getLevel() {
		return level;
	}
}
