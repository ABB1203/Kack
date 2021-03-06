package game.weapon;

import game.Game;
import game.entity.mob.Mob;
import game.gfx.Sprite;
import game.input.Mouse;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Weapon {

	protected Sprite sprite;
	protected BufferedImage image;
	protected double speed, damage, range, fireRate, angle, shotsPerSec;
	protected final Random random = new Random();
	protected Mouse mouse;
	protected double width, height;
	protected Game game;
	protected AffineTransform at = new AffineTransform();
	//protected double pipeXOffset, pipeYOffset;
	
	
	public Weapon() {
		
	}

	public void render(Graphics g, int xOffset, int yOffset, Mob mob, int xScale, int yScale) {
		
	}
	
	protected double getCorrectAngle(double angle) {
		double diff = 0;
		if (Math.toDegrees(angle) >= 90) {
			diff = Math.toRadians(180) - angle;
		}
		if (-90 >= Math.toDegrees(angle)) {
			diff = Math.toRadians(-180) - angle;
		}
		return diff;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public double getDamage() {
		return damage;
	}
	
	public double getFireRate() {
		return fireRate;
	}
	
	public double getRange() {
		return range;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	// If the player can hold down the mouse button and keep shooting, this is true
	public boolean isHoldable() {
		return true;
	}
}
