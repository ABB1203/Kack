package game.level.random;

public class Bound extends Room {

	public Bound(int w, int h, RandomLevel randomLevel) {
		super(w, h, randomLevel);
	}
	
	protected void generateDimensinos(int w, int h) {
		width = w;
		height = h;
		
		width += random.nextInt(w / 5);
		height += random.nextInt(w / 5);
		
		x = random.nextInt(level.getTileWidth() - width);
		y = random.nextInt(level.getTileHeight() - height);
	}

}
