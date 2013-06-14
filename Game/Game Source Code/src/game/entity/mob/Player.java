package game.entity.mob;

import game.entity.projectile.Projectile;
import game.gfx.Screen;
import game.gfx.Sprite;
import game.input.InputHandler;
import game.input.Mouse;
import game.weapon.Weapon;

public class Player extends Mob {

	private InputHandler input;
	private Sprite sprite = Sprite.leaves;

	public Player(InputHandler input, Weapon weapon) {
		this.input = input;
		this.weapon = weapon;
	}

	public void tick(int xOffset, int yOffset) {
		
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
		
		clear();
		updateShooting(xOffset, yOffset);
	}
	
	private void clear() {
		for(int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if(p.isRemoved()) level.getProjectiles().remove(i);
		}
	}
	
	private void updateShooting(int xOffset, int yOffset) {

		if(fireRateCounter < weapon.getFireRate()) fireRateCounter++;
		
		if(Mouse.getButton() == 1 && fireRateCounter/weapon.getFireRate() >= 1) {
			// As in the weapon class, the 3 is the scale
			double dx = Mouse.getX() - (x + sprite.getSize() / 2 - xOffset) * 3;
			double dy = Mouse.getY() - (y + sprite.getSize() / 2 - yOffset) * 3;
			
			angle = Math.atan2(dy, dx);
			shoot(x, y, angle, weapon);
			
			fireRateCounter -= weapon.getFireRate();
		}
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
