package game.entity.projectile;

import game.entity.Entity;
import game.gfx.Screen;
import game.gfx.Sprite;
import game.weapon.Weapon;

import java.util.Random;

public class Projectile extends Entity {

	protected final int xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double x, y;
	protected double distance;
	protected double xNew, yNew;
	protected double speed, fireRate, range, damage;
	protected final Random random = new Random();
	
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

		xNew = Math.cos(angle) * speed;
		yNew = Math.sin(angle) * speed;
	}
	
	public void render(Screen screen) {
		screen.renderProjectile((int) x, (int) y, this);
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	protected void move() {
		x += xNew;
		y += yNew;
		
		if(distance() >= range) remove();
	}
	
	protected double distance() {
		double distance;
		distance = Math.sqrt(Math.pow(xOrigin - x, 2) + Math.pow(yOrigin - y,  2));
		return distance;
	}

}
