package game.entity.mob.AI;

import game.Game;
import game.entity.mob.Mob;

public abstract class AI extends Mob {

	// These names are extremely confusing... damageCount is the variable counting down (until damage can be done again), damageCounter is the value damageCount is reset to
	protected int tickCount, damageCount, damageCounter;
	protected int randomTick;
	protected int range;
	// Makes the AI move into a random direction if it somehow gets stuck or
	// trapped by the player
	protected boolean collided;
	protected boolean seenPlayer;
	protected double k, m;

	// Added just for testing
	protected double damage = 1;

	public AI(Game game) {
		this.game = game;
		randomTick = randomTick();
	}

	public void tick() {
		super.tick();
		
		damageCount--;
		
		// Checks collision with players
		for (Mob m : level.getPlayers()) {
			if (getCollisionBox(xDir, yDir).intersects(m.getCollisionBox())) {
				if (damageCount <= 0) {
					damageCount = damageCounter;
					m.damage(damage);
				}
			}
		}

	}

	protected void move(double xDir, double yDir) {
		if (!hasCollided(xDir, yDir)) {
			x += xDir;
			y += yDir;
		} else collided = true;
	}

	protected boolean hasCollided(double xDir, double yDir) {
		if (super.hasCollided(xDir, yDir)) {
			seenPlayer = false;
			return true;
		}

		// Checks if it will collide with other AIs
		for (AI ai : level.getAIs()) {
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
		if (random.nextBoolean() && random.nextBoolean()) remove();
		return 60 + random.nextInt(90);
	}

	public boolean isCollidableWithPlayer() {
		return true;
	}
}
