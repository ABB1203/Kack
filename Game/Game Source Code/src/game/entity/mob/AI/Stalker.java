package game.entity.mob.AI;

import game.entity.mob.Mob;
import game.entity.mob.Player;

public class Stalker extends AI {

	public Stalker() {
		sprite = sprite.shot;
		speed = 1;
		range = 200;
		randomTick = randomTick();
	}

	public void tick(Mob player) {
		super.tick();

		tickCount++;

		if (seesPlayer(player)) {
			seenPlayer = true;
		}

		if (tickCount > randomTick || collided) {
			tickCount = 0;
			randomTick = randomTick();

			double dx = player.getX() - x;
			double dy = player.getY() - y;

			if (collided) {
				angle = Math.toRadians(random.nextInt(360));

				seenPlayer = false;
				collided = false;
			} else {
				if (seenPlayer) {
					angle = Math.atan2(dy, dx) + Math.toRadians(random.nextInt(40) - 20);
				} else {
					angle += Math.toRadians(random.nextInt(40)- 20);
				}
			}

			xDir = Math.cos(angle) * speed;
			yDir = Math.sin(angle) * speed;

		}

		move(xDir, yDir);
	}

	private void getLineEquation(double x0, double x1, double y0, double y1) {
		k = (y0 - y1) / (x0 - x1);
		m = (y0 - x0 * k) / 16;
	}

	protected boolean seesPlayer(Mob player) {
		if (x != player.getX()) getLineEquation(x + sprite.getSize() / 2, player.getX() + player.getSprite().getSize() / 2, y + sprite.getSize() / 2, player.getY() + player.getSprite().getSize() / 2);
		
		double x0 = ((x < player.getX()) ? x : player.getX()) / 16;
		double x1 = (((x + sprite.getSize() > player.getX() + player.getSprite().getSize()) ? x + sprite.getSize() : player.getX() + player.getSprite().getSize()) - 1) / 16;
		double y0 = ((y < player.getY()) ? y : player.getY()) / 16;
		double y1 = (((y + sprite.getSize() > player.getY() + player.getSprite().getSize()) ? y + sprite.getSize() : player.getY() + player.getSprite().getSize()) - 1) / 16;

		for (double yy = y0; yy < y1; yy ++) {
			if ((int)x0 != (int)x1) {
				for (double xx = x0; xx <= x1; xx++) {
					
					if (level.getTile((int)xx, (int)yy).isSolid()) {
						if (yy <= xx * k + m && xx * k + m < yy + 1) return false;
						if (xx <= (yy - m) / k && (yy - m) / k < xx + 1) return false;
					}
				}
			} else if (level.getTile((int)x1, (int)yy).isSolid()) return false;
		}

		if (x != player.getX()) getLineEquation(x + sprite.getSize() / 2 + 1, player.getX() + player.getSprite().getSize() / 2, y + sprite.getSize() / 2, player.getY() + player.getSprite().getSize() / 2);
		
		// The value of the first parameter is changed (+1). By doing this, it
		// prevents the AI to spot the player when the situation is like this:
		// P = player
		// M = AI
		// X = wall
		// PX
		// XM
		// If this would not be done, the AI would be able to look through the
		// walls
		
		for (double yy = y0; yy < y1; yy ++) {
			if ((int)x0 != (int)x1) {
				for (double xx = x0; xx <= x1; xx++) {
					
					if (level.getTile((int)xx, (int)yy).isSolid()) {
						if (yy <= xx * k + m && xx * k + m < yy + 1) return false;
						if (xx <= (yy - m) / k && (yy - m) / k < xx + 1) return false;
					}
				}
			} else if (level.getTile((int)x1, (int)yy).isSolid()) return false;
		}
		
		return true;
	}
}
