package game.entity.mob.AI;

import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.entity.projectile.Projectile;

public abstract class AI extends Mob {

	protected int tickCount;
	protected int randomTick;
	protected int range;
	protected boolean seenPlayer;
	protected double k, m;
	protected boolean hasRecentlyShot = false;

	// Added just for testing
	protected double damage = 1;

	public void tick() {
		super.tick();
	}

	protected boolean hasCollided(double xDir, double yDir) {
		if (super.hasCollided(xDir, yDir)) {
			seenPlayer = false;
			return true;
		}

		// Checks if it will collide with other AIs
		for (Mob ai : level.getAIs()) {
			if (!ai.equals(this)) {
				if (getCollisionBox(xDir, yDir).intersects(ai.getCollisionBox())) {
					return true;
				}
			}
		}

		return false;
	}

	protected double distance(int xPlayer, int yPlayer) {
		double distance;
		distance = Math.sqrt(Math.pow(xPlayer - x, 2) + Math.pow(yPlayer - y, 2));
		return distance;
	}

	protected int randomTick() {
		// This gives a random value to how many ticks the AI should wait. If
		// not updated, the number of ticks should be 60 each second
		return 30 + random.nextInt(30);
	}

	private void getLineEquation(double x0, double x1, double y0, double y1) {
		k = (y0 - y1) / (x0 - x1);
		m = (y0 - x0 * k) / 16;
	}
	
	protected void tickShooting() {
		cleanProjectiles();
		
		Player player = game.getPlayer();
		
		for (Projectile p : projectiles) {
			if (p.getCollisionBox().intersects(player.getCollisionBox())) {
				p.remove();
				player.damage(p.getDamage());
			}
		}
	}

	protected boolean sees(Mob player) {

		double x0 = ((x < player.getX()) ? x : player.getX()) / 16;
		double x1 = (((x + sprite.getWidth() > player.getX() + player.getSprite().getWidth()) ? x + sprite.getWidth() : player.getX() + player.getSprite().getWidth()) - 1) / 16;
		double y0 = ((y < player.getY()) ? y : player.getY()) / 16;
		double y1 = (((y + sprite.getHeight() > player.getY() + player.getSprite().getHeight()) ? y + sprite.getHeight() : player.getY() + player.getSprite().getHeight()) - 1) / 16;

		if (x != player.getX()) getLineEquation(x + sprite.getWidth(), player.getX() + player.getSprite().getWidth(), y + sprite.getHeight(), player.getY() + player.getSprite().getHeight());

		for (double yy = y0; yy < y1; yy++) {
			if ((int) x0 != (int) x1) {
				for (double xx = x0; xx <= x1; xx++) {

					if (level.getTile((int) xx, (int) yy).isSolid()) {
						if (yy <= xx * k + m && xx * k + m < yy + 1) return false;
						if (xx <= (yy - m) / k && (yy - m) / k < xx + 1) return false;
					}
				}
			} else if (level.getTile((int) x1, (int) yy).isSolid()) return false;
		}

		if (x != player.getX()) getLineEquation(x + sprite.getWidth(), player.getX() + player.getSprite().getWidth(), y, player.getY());

		for (double yy = y0; yy < y1; yy++) {
			if ((int) x0 != (int) x1) {
				for (double xx = x0; xx <= x1; xx++) {

					if (level.getTile((int) xx, (int) yy).isSolid()) {
						if (yy <= xx * k + m && xx * k + m < yy + 1) return false;
						if (xx <= (yy - m) / k && (yy - m) / k < xx + 1) return false;
					}
				}
			} else if (level.getTile((int) x1, (int) yy).isSolid()) return false;
		}
		
		if (x != player.getX()) getLineEquation(x + sprite.getWidth(), player.getX() + player.getSprite().getWidth(), y + sprite.getHeight(), player.getY() + sprite.getHeight());

		for (double yy = y0; yy < y1; yy++) {
			if ((int) x0 != (int) x1) {
				for (double xx = x0; xx <= x1; xx++) {

					if (level.getTile((int) xx, (int) yy).isSolid()) {
						if (yy <= xx * k + m && xx * k + m < yy + 1) return false;
						if (xx <= (yy - m) / k && (yy - m) / k < xx + 1) return false;
					}
				}
			} else if (level.getTile((int) x1, (int) yy).isSolid()) return false;
		}
		
		if (x != player.getX()) getLineEquation(x, player.getX(), y + sprite.getHeight(), player.getY() + sprite.getHeight());

		for (double yy = y0; yy < y1; yy++) {
			if ((int) x0 != (int) x1) {
				for (double xx = x0; xx <= x1; xx++) {

					if (level.getTile((int) xx, (int) yy).isSolid()) {
						if (yy <= xx * k + m && xx * k + m < yy + 1) return false;
						if (xx <= (yy - m) / k && (yy - m) / k < xx + 1) return false;
					}
				}
			} else if (level.getTile((int) x1, (int) yy).isSolid()) return false;
		}

		// This may be redundant
		if (x != player.getX()) getLineEquation(x + sprite.getWidth() / 2 + 1, player.getX() + player.getSprite().getWidth() / 2, y + sprite.getHeight() / 2, player.getY() + player.getSprite().getHeight() / 2);

		// The value of the first parameter is changed (+1). By doing this, it
		// prevents the AI to spot the player when the situation is like this:
		// P = player
		// M = AI
		// X = wall
		// PX
		// XM
		// If this would not be done, the AI would be able to look through the
		// walls

		for (double yy = y0; yy < y1; yy++) {
			if ((int) x0 != (int) x1) {
				for (double xx = x0; xx <= x1; xx++) {

					if (level.getTile((int) xx, (int) yy).isSolid()) {
						if (yy <= xx * k + m && xx * k + m < yy + 1) return false;
						if (xx <= (yy - m) / k && (yy - m) / k < xx + 1) return false;
					}
				}
			} else if (level.getTile((int) x1, (int) yy).isSolid()) return false;
		}

		return true;
	}

}
