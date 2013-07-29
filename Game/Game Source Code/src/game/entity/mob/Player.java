package game.entity.mob;

import game.Game;
import game.entity.drop.AmmoDrop;
import game.entity.drop.Drop;
import game.entity.drop.WeaponDrop;
import game.entity.drop.XPDrop;
import game.entity.projectile.Projectile;
import game.gfx.Screen;
import game.gfx.Sprite;
import game.input.InputHandler;
import game.input.Mouse;
import game.level.Level;
import game.weapon.Weapon;

public class Player extends Mob {

	private InputHandler input;
	private double XP = 0;
	private boolean holdingMouse = false;

	public Player(InputHandler input, Weapon weapon, Game game) {
		this.game = game;
		level = game.getLevel();
		this.input = input;
		this.weapon = weapon;
		maxHealth = 10;
		health = maxHealth;
		ammo = 20;
		// Just for the mega fancy yellow bar
		lastHealth = health;

		// Just for making the Player spawn on a non-solid tile
		Level level = game.getLevel();
		while (true) {
			x = random.nextInt(level.getTileWidth());
			y = random.nextInt(level.getTileHeight());
			if (!level.getTile((int) x, (int) y).isSolid()) break;
		}
		x *= 16;
		y *= 16;

		sprite = Sprite.leaves;
		speed = 2;
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

		tickShooting();
		tickDrops();
		

		if(Mouse.getButton() == 1) {
			if(!weapon.isHoldable())
			holdingMouse = true;
		} else holdingMouse = false;

	}

	public void render(Screen screen) {
		super.render(screen);
	}

	private void tickShooting() {
		cleanProjectiles();

		if (fireRateCounter < weapon.getFireRate()) fireRateCounter++;

		if (Mouse.getButton() == 1 && fireRateCounter / weapon.getFireRate() >= 1 && !holdingMouse && ammo > 0) {
			int xOffset = game.getScreen().getXOffset();
			int yOffset = game.getScreen().getYOffset();

			// As in the weapon class, the 3 is the scale
			double dx = Mouse.getX() - (x + sprite.getWidth() / 2 - xOffset) * 3;
			double dy = Mouse.getY() - (y + sprite.getHeight() / 2 - yOffset) * 3;

			angle = Math.atan2(dy, dx);
			shoot((int) x, (int) y, angle, weapon);

			ammo--;
			fireRateCounter = 0;
			
			System.out.println(ammo + " shots left!\tPlayer - tickShooting()");
		}

		// Checking for hits on all the AIs
		for (Mob ai : level.getAIs()) {

			for (Projectile p : projectiles) {
				if (p.getCollisionBox().intersects(ai.getCollisionBox()) && !p.isRemoved()) {
					p.remove();
					ai.damage(p.getDamage());
				}
			}
		}
	}

	private void tickDrops() {
		boolean updated = true;
		while (updated) {
			updated = false;
			for (Drop d : level.getDrops()) {
				if (d.getCollisionBox().intersects(getCollisionBox()) && !d.isRemoved()) {
					pickUpDrop(d);
					d.remove();
					updated = true;
					break;
				}
			}
		}
	}

	private void pickUpDrop(Drop d) {
		if(d instanceof XPDrop) {
			XP += ((XPDrop) d).getAmount();
		}
		if(d instanceof WeaponDrop) {
			System.out.println("wepun");
		}
		if(d instanceof AmmoDrop) {
			ammo+=((AmmoDrop) d).getAmount();
		}
	}
}
