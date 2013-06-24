package game.entity.mob;

import game.entity.mob.AI.AI;
import game.entity.projectile.Projectile;
import game.gfx.Screen;
import game.gfx.Sprite;
import game.input.InputHandler;
import game.input.Mouse;
import game.level.Level;
import game.weapon.Weapon;

import java.awt.Rectangle;
import java.util.List;

public class Player extends Mob {

	private InputHandler input;

	public Player(InputHandler input, Weapon weapon, Level level) {
		this.level = level;
		this.input = input;
		this.weapon = weapon;
		sprite = Sprite.leaves;
		speed = 1;
	}

	public void tick(int xOffset, int yOffset) {
		super.tick();
		xDir = 0;
		yDir = 0;

		if (input.up) yDir = -speed;
		if (input.down) yDir = +speed;
		if (input.right) xDir = +speed;
		if (input.left) xDir = -speed;

		move(xDir, yDir);

		clear();
		tickShooting(xOffset, yOffset);

	}

	public void render(Screen screen) {
		super.render(screen);
	}

	private void tickShooting(int xOffset, int yOffset) {

		if (fireRateCounter < weapon.getFireRate()) fireRateCounter++;

		if (Mouse.getButton() == 1 && fireRateCounter / weapon.getFireRate() >= 1) {
			// As in the weapon class, the 3 is the scale
			double dx = Mouse.getX() - (x + sprite.getSize() / 2 - xOffset) * 3;
			double dy = Mouse.getY() - (y + sprite.getSize() / 2 - yOffset) * 3;

			angle = Math.atan2(dy, dx);
			shoot((int) x, (int) y, angle, weapon);

			fireRateCounter -= weapon.getFireRate();
		}
		
		// Checking for hits on all the AIs
		for (AI ai : level.getAIs()) {

			hitChecking: for (Projectile p : projectiles) {
				if (p.isRemoved()) {
					projectiles.remove(p);
					break;
				}
				if (p.getCollisionBox().intersects(ai.getCollisionBox())) {
					p.remove();
					ai.damage(p.getDamage());
					break hitChecking;
				}
			}

		}
	}

	public void tick() {

	}
}
