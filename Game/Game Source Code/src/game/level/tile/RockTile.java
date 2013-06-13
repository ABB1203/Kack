package game.level.tile;

import game.gfx.Sprite;

public class RockTile extends Tile {

	public RockTile(Sprite sprite) {
		super(sprite);
	}
	
	public boolean isSolid() {
		return true;
	}
}
