package game.weapon;

import game.entity.mob.Player;
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
		this.sprite = Sprite.gun;
		try {
			image = ImageIO.read(Level.class.getResource("/weapons.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics g, int xOffset, int yOffset, Player player) {
		AffineTransform trans = new AffineTransform();

		int x = player.getX() + player.getSprite().getSize() / 2 - xOffset;
		int y = player.getY() + player.getSprite().getSize() / 2 - yOffset;

		// replace 3 (the scale) with (jframe.width / width) to make it possible
		// to work after resizing the frame
		int xDelta = Mouse.getX() - x * 3;
		int yDelta = Mouse.getY() - y * 3;

		angle = Math.atan2(yDelta, xDelta);

		if (-90 >= Math.toDegrees(angle) || Math.toDegrees(angle) >= 90) {
			trans = AffineTransform.getScaleInstance(-1, 1);
			x *= -1;
			angle = getCorrectAngle(angle);
		}

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// replace 3 (the scale) with (jframe.width / width) to make it possible
		// to work after resizing the frame
		trans.scale(3, 3);
		trans.translate(x, y);
		trans.rotate(angle);
		g2d.drawImage(image, trans, null);
	}
}
