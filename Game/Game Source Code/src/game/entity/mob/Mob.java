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
	protected Game game;
	protected double angle;
	protected double fireRateCounter;
	protected double xDir, yDir;
	protected double speed;
	protected double health;
	protected boolean dead = false;

	protected List<Projectile> projectiles = new ArrayList<Projectile>();
	
	protected void tick() {
		if(dead) {
			remove();
			level.removeMob(this);
		}
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

	protected void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) level.getProjectiles().remove(i);
		}
	}

	public void damage(double damage) {
		health -= damage;
		if (health <= 0) {
			dead = true;
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
}
