package game.entity.mob;

import game.Game;
import game.entity.Entity;
import game.entity.projectile.GunProjectile;
import game.entity.projectile.Projectile;
import game.gfx.Screen;
import game.gfx.Sprite;
import game.level.tile.Tile;
import game.weapon.Weapon;

public class Mob extends Entity {

	protected Sprite sprite;
	protected Weapon weapon;
	protected int dir = 0;
	protected boolean moving = false;
	protected boolean walking = false;
	protected Game game;
	protected double angle;
	protected double fireRateCounter;

	public void move(int xDir, int yDir) {
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

	public void tick() {

	}
	
	protected void shoot(int x, int y, double angle, Weapon weapon) {
		Projectile p = new GunProjectile(x, y, angle, weapon);
		level.addProjectile(p);
	}

	public void render(Screen screen) {

	}
	
	public Weapon getWeapon() {
		return weapon;
	}
}
