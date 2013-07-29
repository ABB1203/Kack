package game.entity.drop;

import game.gfx.Sprite;

public class AmmoDrop extends Drop {

	int amount = 15;
	
	public AmmoDrop(int x, int y) {
		sprite = Sprite.voidSprite;
		init(x, y);
	}
	
	public int getAmount() {
		return amount;
	}
}
