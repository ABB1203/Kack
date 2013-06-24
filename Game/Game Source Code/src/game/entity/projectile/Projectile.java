package game.entity.projectile;

import game.entity.Entity;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.gfx.Screen;
import game.gfx.Sprite;
import game.level.tile.Tile;
import game.weapon.Weapon;

import java.awt.Rectangle;
import java.util.List;

public abstract class Projectile extends Entity {

	protected int xOrigin;
	protected int yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double xDir, yDir;
	protected double speed, fireRate, range, damage;
	protected boolean isBounceable;

	public Projectile(int xOrigin, int yOrigin, double angle, Weapon weapon) {
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
		this.angle = angle;
		this.x = xOrigin;
		this.y = yOrigin;

		speed = weapon.getSpeed();
		fireRate = weapon.getFireRate();
		range = weapon.getRange();
		damage = weapon.getDamage();
		sprite = weapon.getSprite();

		xDir = Math.cos(angle) * speed;
		yDir = Math.sin(angle) * speed;
	}

	public abstract void tick();
	
	public void render(Screen screen) {
		screen.renderProjectile((int) x, (int) y, this);
	}

	public Sprite getSprite() {
		return sprite;
	}
	
	public double getDamage() {
		return damage;
	}

	protected void move() {
		x += xDir;
		y += yDir;

		if (distance() >= range) remove();
		if (hasCollided((int) xDir, (int) yDir)) remove();
	}

	protected boolean isSolidTile(double xDir, double yDir, int x, int y) {
		Tile newTile = level.getTile((int) (this.x + x + xDir) >> game.getTileShift(), (int) (this.y + y + yDir) >> game.getTileShift());
		if (newTile.isSolid()) return true;
		return false;
	}

	protected double distance() {
		double distance;
		distance = Math.sqrt(Math.pow(xOrigin - x, 2) + Math.pow(yOrigin - y, 2));
		return distance;
	}


}
