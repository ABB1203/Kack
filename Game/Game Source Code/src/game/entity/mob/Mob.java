package game.entity.mob;

import game.Game;
import game.entity.Entity;
import game.entity.mob.AI.AI;
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
	protected boolean dead = false;
	
	// Number of ticks the health bar should be displayed
	protected int healthBarTick = 240;
	protected int healthBarTickCount = healthBarTick;

	protected List<Projectile> projectiles = new ArrayList<Projectile>();

	protected void tick() {
		if (dead) {
			remove();
			level.removeMob(this);
		}
		
		if(lastHealth > health) lastHealth -= 0.02;
		if(lastHealth < health) lastHealth = health;

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
		int yy = (int) (y + sprite.getHeight() / 2 - getHealthBarSprite().getHeight() / 2) - game.getTileSize();
		if (healthBarTickCount < healthBarTick) {
			screen.renderHealthBar(xx, yy, this);
		}
	}

	protected void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) level.getProjectiles().remove(i);
		}
	}

	public void damage(double damage) {
		healthBarTickCount = 0;
		
		health -= damage;
		if (health <= 0) {
			dead = true;
			// This just prints out when the player dies
			if (this instanceof Player) System.out.println("You died!\tMob - damage");
		}
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public Sprite getHealthBarSprite() {
//		if (health / maxHealth >= 0.875) return Sprite.health[4];
//		if (health / maxHealth >= 0.625) return Sprite.health[3];
//		if (health / maxHealth >= 0.375) return Sprite.health[2];
//		if (health / maxHealth >= 0.125) return Sprite.health[1];
//		if (health / maxHealth < 0) return Sprite.health[0];
//
//		return Sprite.health[0];
		
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
}
