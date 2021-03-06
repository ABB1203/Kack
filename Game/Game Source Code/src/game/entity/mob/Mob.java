package game.entity.mob;

import game.Game;
import game.entity.Entity;
import game.entity.drop.AmmoDrop;
import game.entity.drop.WeaponDrop;
import game.entity.drop.XPDrop;
import game.entity.projectile.GunProjectile;
import game.entity.projectile.Projectile;
import game.gfx.Screen;
import game.gfx.Sprite;
import game.weapon.Weapon;

import java.util.ArrayList;
import java.util.List;

public abstract class Mob extends Entity {

	protected Sprite sprite;
	protected Weapon weapon;
	protected int dir = 0;
	protected boolean moving = false;
	protected boolean walking = false;
	protected double angle;
	protected double fireRateCounter;
	protected double xDir, yDir;
	protected double speed;
	protected double health, lastHealth, maxHealth;
	protected int ammo;
	// For AIs
	// The variable was added here so that the move method did not need to be
	// overridden in the AI class
	protected boolean collided;

	// Number of ticks the health bar should be displayed
	protected int healthBarTick = 240;
	protected int healthBarTickCount = healthBarTick;

	protected List<Projectile> projectiles = new ArrayList<Projectile>();

	public void tick() {
		if (lastHealth > health) lastHealth -= 0.02;
		if (lastHealth < health) lastHealth = health;

		if (health <= 0 && !isRemoved()) {
			remove();
			drop();
			// This just prints out when the player dies
			if (this instanceof Player) System.out.println("You died!\tMob - damage");
		}

		healthBarTickCount++;
	}

	protected void move(double xDir, double yDir) {
		if (xDir != 0 && yDir != 0) {
			move(xDir, 0);
			move(0, yDir);
			return;
		}
		// Change to 8 directions
		if (yDir < 0) dir = 0;
		if (xDir > 0) dir = 1;
		if (yDir > 0) dir = 2;
		if (xDir < 0) dir = 3;

		if (!hasCollided(xDir, yDir)) {
			x += xDir;
			y += yDir;
		} else collided = true;
	}

	protected void drop() {
		if (random.nextBoolean()) {
			if(random.nextBoolean())
			level.addDrop(new XPDrop((int) x + sprite.getWidth() / 2, (int) y + sprite.getHeight() / 2));
			else 
				level.addDrop(new WeaponDrop((int) x + sprite.getWidth() / 2, (int) y + sprite.getHeight() / 2));
		} else {
			level.addDrop(new AmmoDrop((int) x + sprite.getWidth() / 2, (int) y + sprite.getHeight() / 2));
		}
	}

	protected void shoot(int x, int y, double angle, Weapon weapon) {
		Projectile p = new GunProjectile(x, y, angle, weapon, level);
		level.addProjectile(p);
		projectiles.add(p);
	}

	public void render(Screen screen) {
		screen.renderMob((int) x, (int) y, this);
	}

	public void renderHealthBar(Screen screen) {
		int xx = (int) (x + sprite.getWidth() / 2 - getHealthBarSprite().getWidth() / 2);
		int yy = (int) (y + sprite.getHeight() / 2 - getHealthBarSprite().getHeight() / 2) - Game.getTileSize();
		if (healthBarTickCount < healthBarTick) {
			screen.renderHealthBar(xx, yy, this);
		}
	}

	public void damage(double damage) {
		healthBarTickCount = 0;

		health -= damage;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public Sprite getHealthBarSprite() {
		return Sprite.healthBar;
	}

	public double getHealth() {
		return health;
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public double getLastHealth() {
		return lastHealth;
	}

	protected void cleanProjectiles() {
		// Cleans the array list from removed projectiles
		List<Projectile> temp = new ArrayList<Projectile>();
		temp.addAll(projectiles);
		projectiles.clear();
		for (Projectile p : temp) {
			if (!p.isRemoved()) projectiles.add(p);
		}
	}
}
