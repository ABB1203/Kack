package game.weapon;

import game.entity.mob.Player;
import game.gfx.Sprite;
import game.input.Mouse;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Weapon {

	protected Sprite sprite;
	protected BufferedImage image;
	protected double speed, damage, range, fireRate, angle;
	protected final Random random = new Random();
	protected Mouse mouse;
	
	
	public Weapon() {
		
	}

	public void render(Graphics g, int xOffset, int yOffset, Player player) {
		
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
}
