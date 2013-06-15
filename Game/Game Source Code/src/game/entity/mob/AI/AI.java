package game.entity.mob.AI;

import game.entity.mob.Mob;

public class AI extends Mob {

	protected int tickCount;
	protected int randomTick = randomTick();
	protected int range;
	// Makes the AI move into a random direction if it somehow gets stuck or
	// trapped by the player
	protected boolean collided;

	public AI() {
		sprite = sprite.voidSprite;
		speed = 0.5;
		range = 100;
	}

	public void tick(int xPlayer, int yPlayer) {
		super.tick();

		tickCount++;

		if (tickCount > randomTick || collided) {
			tickCount = 0;
			randomTick = randomTick();

			double dx = xPlayer - x;
			double dy = yPlayer - y;

			if (range >= distance(xPlayer, yPlayer) && !collided) angle = Math.atan2(dy, dx) + Math.toRadians(random.nextInt(90) - 45);
			else {
				while (true) {
					angle = Math.toRadians(random.nextInt(360));
					xDir = Math.cos(angle) * speed;
					yDir = Math.sin(angle) * speed;
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
		System.out.println(!hasCollided(xDir, yDir));
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
