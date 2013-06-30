package game.entity.mob;

import game.Game;
import game.entity.mob.AI.AI;
import game.entity.projectile.Projectile;
import game.gfx.Screen;
import game.gfx.Sprite;
import game.input.InputHandler;
import game.input.Mouse;
import game.level.Level;
import game.weapon.Weapon;

public class Player extends Mob {

	private InputHandler input;

	public Player(InputHandler input, Weapon weapon, Game game) {
		this.game = game;
		level = game.getLevel();
		this.input = input;
		this.weapon = weapon;
		maxHealth = 10;
		health = maxHealth;
		//Just for testing
		lastHealth = health;
		
		sprite = Sprite.leaves;
		speed = 1;
	}

	public void tick() {
		super.tick();
		xDir = 0;
		yDir = 0;

		if (input.up) yDir = -speed;
		if (input.down) yDir = +speed;
		if (input.right) xDir = +speed;
		if (input.left) xDir = -speed;

		move(xDir, yDir);

		clear();
		tickShooting();

	}

	public void render(Screen screen) {
		super.render(screen);
	}

	private void tickShooting() {

		if (fireRateCounter < weapon.getFireRate()) fireRateCounter++;

		if (Mouse.getButton() == 1 && fireRateCounter / weapon.getFireRate() >= 1) {
			int xOffset = game.getScreen().getXOffset();
			int yOffset = game.getScreen().getYOffset();
			
			// As in the weapon class, the 3 is the scale
			double dx = Mouse.getX() - (x + sprite.getWidth() / 2 - xOffset) * 3;
			double dy = Mouse.getY() - (y + sprite.getHeight() / 2 - yOffset) * 3;

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
}
