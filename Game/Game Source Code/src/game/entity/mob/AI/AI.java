package game.entity.mob.AI;

import game.entity.mob.Mob;
import game.entity.mob.Player;

public class AI extends Mob {

	protected int tickCount;
	protected int randomTick;
	protected int range;
	// Makes the AI move into a random direction if it somehow gets stuck or
	// trapped by the player
	protected boolean collided;
	protected boolean seenPlayer;
	protected double k, m;

	public AI() {
		this.level = level;
		sprite = sprite.voidSprite;
		speed = 0.5;
		range = 200;
		randomTick = randomTick();
	}

	public void tick(Mob player) {
		super.tick();

		tickCount++;

		if (tickCount > randomTick || collided) {
			tickCount = 0;
			randomTick = randomTick();

			double dx = player.getX() - x;
			double dy = player.getY() - y;

			if (range >= distance(player.getX(), player.getY()) && !collided) angle = Math.atan2(dy, dx) + Math.toRadians(random.nextInt(120) - 60);
			else {
				while (true) {
					angle = Math.toRadians(random.nextInt(360));
					if(!hasCollided(xDir, yDir)) break;
				}
				collided = false;
			}
		
			xDir = Math.cos(angle) * speed;
			yDir = Math.sin(angle) * speed;

		}

		move(xDir, yDir);
	}

	protected void move(double xDir, double yDir) {
		if (!hasCollided(xDir, yDir)) {
			x += xDir;
			y += yDir;
		} else collided = true;
	}

	protected double distance(int xPlayer, int yPlayer) {
		double distance;
		distance = Math.sqrt(Math.pow(xPlayer - x, 2) + Math.pow(yPlayer - y, 2));
		return distance;
	}

	protected int randomTick() {
		// This gives a random value to how many ticks the AI should wait. If
		// not updated, the number of ticks should be 60 each second
		return 60 + random.nextInt(90);
	}

	protected int randomAngle() {
		return random.nextInt(360);
	}

}
