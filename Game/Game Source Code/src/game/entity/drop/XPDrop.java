package game.entity.drop;

import game.gfx.Sprite;

public class XPDrop extends Drop {
	
	private double amount;

	public XPDrop(int x, int y) {
		sprite = Sprite.XP;
		amount = 10;
		init(x, y);		
	}
	
	public double getAmount() {
		return amount;
	}

}