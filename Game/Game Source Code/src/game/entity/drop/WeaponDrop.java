package game.entity.drop;

import game.gfx.Sprite;

public class WeaponDrop extends Drop {
	
	public WeaponDrop(int x, int y) {
		sprite = Sprite.gun;
		init(x, y);
	}
	
}
