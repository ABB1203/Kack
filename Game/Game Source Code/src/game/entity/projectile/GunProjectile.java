package game.entity.projectile;

import game.level.Level;
import game.weapon.Weapon;

public class GunProjectile extends Projectile {

	public GunProjectile(int xOrigin, int yOrigin, double angle, Weapon weapon, Level level) {
		super(xOrigin, yOrigin, angle, weapon);
		this.level = level;
	}
	
	public void tick() {
		move();
	}
}
