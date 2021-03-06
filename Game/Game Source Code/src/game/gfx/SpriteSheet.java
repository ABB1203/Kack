package game.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	private int size;
	public int[] pixels;

	// Since the sheets will not change, they will be static and thus accessible
	// from every class
	// ALL THE SPRITE SHEETS:
	public static SpriteSheet tiles = new SpriteSheet("/spritesheet.png", 256);

	public SpriteSheet(String path, int size) {
		this.path = path;
		this.size = size;
		pixels = new int[size * size];

		loadImage();
	}

	private void loadImage() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getSize() {
		return size;
	}

}
