package game.entity.drop;

import game.entity.Entity;
import game.gfx.Screen;
import game.gfx.Sprite;

public class Drop extends Entity{

	protected Sprite sprite;
	protected int width, height;
	protected int ticksUntilRemoval, tickCounter;
	
	public void tick() {
		tickCounter++;
		if(tickCounter >= ticksUntilRemoval) remove();
	}

	protected void init(int x, int y) {
		ticksUntilRemoval = 360;
		
		width = sprite.getWidth();
		height = sprite.getHeight();
		this.x = x;
		this.y = y;
		
		// Makes the drop's center be where the dead mob's center was
		this.x -= width / 2;
		this.y -= height / 2;
	}

	public void render(Screen screen) {
		if(!isFlashing() || !isRemovable())
		screen.renderDrop((int)x, (int)y, this);
	}
	
	protected boolean isFlashing() {
		if(tickCounter * 1.0 / ticksUntilRemoval * 1.0 >= 0.6) {
			if(tickCounter % 45 >= 30) return true;
		}
		
		return false;
	}

	public Sprite getSprite() {
		return sprite;
	}
	
	protected boolean isRemovable() {
		return true;
	}
}
