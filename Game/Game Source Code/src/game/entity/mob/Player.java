package game.entity.mob;

import game.gfx.Screen;
import game.gfx.Sprite;
import game.input.InputHandler;

public class Player extends Mob {

	private InputHandler input;
	private Sprite sprite = Sprite.leaves;

	public Player(InputHandler input) {
		this.input = input;
	}

	public void tick() {
		int xDir = 0;
		int yDir = 0;
		
		if (input.up) yDir--;
		if (input.down) yDir++;
		if (input.right) xDir++;
		if (input.left) xDir--;
		
		move(xDir, yDir);
		
		if (x < 0) x = 0;
		if (y < 0) y = 0;
		if (x > level.getWidth() - sprite.getSize()) x = level.getWidth() - sprite.getSize();
		if (y > level.getHeight() - sprite.getSize()) y = level.getHeight() - sprite.getSize();
	}

	public void render(Screen screen) {
		screen.renderPlayer(x, y, this);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Sprite getSprite() {
		return sprite;
	}
}
