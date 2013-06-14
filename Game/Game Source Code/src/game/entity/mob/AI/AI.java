package game.entity.mob.AI;

import game.entity.mob.Mob;

public class AI extends Mob {

	protected int tickCount;
	protected int randomTick = randomTick();

	public AI() {
		sprite = sprite.voidSprite;
		speed = 0.5;
	}

	public void tick(int x, int y) {
		super.tick();

		tickCount++;

		if (tickCount > randomTick) {
			tickCount = 0;
			randomTick = randomTick();
			
			double dx = x - this.x;
			double dy = y - this.y;
			
			angle = Math.atan2(dy, dx);
			
			xDir = Math.cos(angle) * speed;
			yDir = Math.sin(angle) * speed;
		}

		move(xDir, yDir);
	}

	protected int randomTick() {
		// This gives a random value to how many ticks the AI should wait. If
		// not updated, the number of ticks should be 60 each second
		return 100 + random.nextInt(90);
	}

	protected int randomAngle() {
		return random.nextInt(360);
	}

}
