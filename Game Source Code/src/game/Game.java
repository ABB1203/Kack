package game;

import game.gfx.Screen;
import game.input.InputHandler;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {

	private int width;
	private int height;
	private int scale;
	private static int minTileSize;
	private Dimension dimensions;

	private String levelPath;
	private boolean running = false;
	private Thread thread;
	private Frame frame;
	
	private Screen screen;
	private InputHandler input;

	private BufferedImage image;
	private int[] pixels;

	public Game(String levelPath, int width, int height, int scale, int minTileSize, Frame frame) {
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.minTileSize = minTileSize;
		this.frame = frame;

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

		dimensions = new Dimension(width * scale, height * scale);
		setPreferredSize(dimensions);
		setMaximumSize(dimensions);
		setMinimumSize(dimensions);
		
		screen = new Screen(width, height);
		input = new InputHandler(this);
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
			System.out.println("Could not join the game thread!");
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
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				frame.setTitle("UPS: " + ticks + "\t\t         FPS: " + frames);
				frames = 0;
				ticks = 0;
			}

			frames++;
			render();
		}

		stop();
	}

	public void tick() {
		input.tick();


		if(input.up) y--;
		if(input.down) y++;
		if(input.right) x++;
		if(input.left) x--;
	}
	
	int x = 0, y = 0;

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		screen.render(x, y);
		
		Graphics g = bs.getDrawGraphics();
		
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		

		g.setColor(Color.black);

		g.dispose();
		bs.show();

	}

	// ///////////////// GETTERS AND SETTERS////////////////////////////////////
	public int getScreenWidth() {
		return width * scale;
	}

	public int getScreenHeight() {
		return height * scale;
	}
	
	public static int getMinTileSize() {
		return minTileSize;
	}
}