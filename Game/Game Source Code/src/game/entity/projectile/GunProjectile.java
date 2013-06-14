package game.entity.projectile;

import game.gfx.Sprite;
import game.weapon.Weapon;

public class GunProjectile extends Projectile {

	public GunProjectile(int xOrigin, int yOrigin, double angle, Weapon weapon) {
		super(xOrigin, yOrigin, angle, weapon);
	}
	
	public void tick() {
		move();
	}

}