package game.level.tile;

import game.gfx.Sprite;

public class VoidTile extends Tile {

	public VoidTile(Sprite sprite) {
		super(sprite);
	}
	
	public boolean isSolid() {
		return true;
	}

}
