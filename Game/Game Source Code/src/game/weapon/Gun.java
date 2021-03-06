package game.weapon;

import game.entity.mob.Mob;
import game.gfx.Sprite;
import game.input.Mouse;
import game.level.Level;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Gun extends Weapon {

	public Gun() {
		sprite = Sprite.shot;
		
		// These properties are just random
		speed = 4;
		damage = 1;
		range = 200;
		shotsPerSec = 5;
		// 60 is ups (fireRate = number of updates that are between each shot)
		fireRate = 60 / shotsPerSec;
		
		try {
			image = ImageIO.read(Level.class.getResource("/weapons.png"));
			
			width = image.getWidth();
			height = image.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics g, int xOffset, int yOffset, Mob mob, int xScale, int yScale) {
		int x = mob.getX() + mob.getSprite().getWidth() / 2 - xOffset;
		int y = mob.getY() + mob.getSprite().getHeight() / 2 - yOffset;

		// replace 3 (the scale) with (jframe.width / width) to make it possible
		// to work after resizing the frame
		int dx = Mouse.getX() - x * xScale;
		int dy = Mouse.getY() - y * yScale;

		angle = Math.atan2(dy, dx);
		
		// For making the projectile start from the pipe's end
//		pipeXOffset = width * Math.cos(angle);
//		pipeYOffset = width * Math.sin(angle);

		if (-90 >= Math.toDegrees(angle) || Math.toDegrees(angle) >= 90) {
			at = AffineTransform.getScaleInstance(-1, 1);
			x *= -1;
			angle = getCorrectAngle(angle);
		} else at = AffineTransform.getScaleInstance(1, 1);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// replace 3 (the scale) with (jframe.width / width) to make it possible
		// to work after resizing the frame
		at.scale(xScale, yScale);
		at.translate(x, y);
		at.rotate(angle);
		
		g2d.drawImage(image, at, null);
		
		at.scale(1/xScale, 1/yScale);
		at.translate(-x, -y);
		at.rotate(-angle);
	}
	
	// Should be false
	public boolean isHoldable() {
		return true;
	}
}
