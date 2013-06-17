package game.entity.mob;

import game.gfx.Screen;
import game.gfx.Sprite;
import game.input.InputHandler;
import game.input.Mouse;
import game.level.Level;
import game.weapon.Weapon;

public class Player extends Mob {

	private InputHandler input;

	public Player(InputHandler input, Weapon weapon) {
		this.input = input;
		this.weapon = weapon;
		sprite = Sprite.leaves;
		speed = 1;
	}

	public void tick(int xOffset, int yOffset) {
		super.tick();
		
		xDir = 0;
		yDir = 0;
		
		if (input.up) yDir = - speed;
		if (input.down) yDir = +speed;
		if (input.right) xDir=+speed;
		if (input.left) xDir=-speed;
		
		move(xDir, yDir);
		
		
		clear();
		updateShooting(xOffset, yOffset);
	}
	
	public void render(Screen screen) {
		super.render(screen);
	}
	
	private void updateShooting(int xOffset, int yOffset) {

		if(fireRateCounter < weapon.getFireRate()) fireRateCounter++;
		
		if(Mouse.getButton() == 1 && fireRateCounter/weapon.getFireRate() >= 1) {
			// As in the weapon class, the 3 is the scale
			double dx = Mouse.getX() - (x + sprite.getSize() / 2 - xOffset) * 3;
			double dy = Mouse.getY() - (y + sprite.getSize() / 2 - yOffset) * 3;
			
			angle = Math.atan2(dy, dx);
			shoot((int)x, (int)y, angle, weapon);
			
			fireRateCounter -= weapon.getFireRate();
		}
	}
}
