package game.entity.mob.AI;

import game.Game;
import game.entity.mob.Player;
import game.gfx.Sprite;
import game.level.Level;
import game.weapon.Gun;

public class Gunner extends AI {

	public Gunner(Game game) {
		this.game = game;
		level = game.getLevel();
		randomTick = randomTick();

		sprite = Sprite.hedge;
		speed = 1;

		maxHealth = 4;
		health = maxHealth;

		weapon = new Gun();

		// Just for making the AI spawn on a non-solid tile
		Level level = game.getLevel();
		while (true) {
			x = random.nextInt(level.getTileWidth());
			y = random.nextInt(level.getTileHeight());
			if (!level.getTile((int) x, (int) y).isSolid()) break;
		}
		x*=16;
		y*=16;
	}

	public void tick() {
		super.tick();

		Player player = game.getPlayer();

		tickCount++;

		if (tickCount >= randomTick) {
			tickCount = 0;
			randomTick = randomTick();

			double dx = player.getX() - x;
			double dy = player.getY() - y;

			// If it has been moving or 67 % if it has been still
			if ((xDir != 0 && yDir != 0)) {

				// 20 % chance if it has not shot during the last random tick
				if (sees(player) && random.nextInt(2) == 0 && !hasRecentlyShot) {
					angle = Math.atan2(dy, dx) + Math.toRadians(random.nextInt(90) - 45);
					shoot((int) x, (int) y, angle, weapon);
					hasRecentlyShot = true;
				} else hasRecentlyShot = false;

				xDir = 0;
				yDir = 0;

				// The AI will move
			} else {
				if (sees(player)) {
					angle = Math.atan2(dy, dx) + Math.toRadians(random.nextInt(45) - 22.5);
				} else {
					angle = Math.toRadians(random.nextInt(360));
				}
				xDir = Math.cos(angle) * speed;
				yDir = Math.sin(angle) * speed;
				hasRecentlyShot = false;
			}
		}

		move(xDir, yDir);

		tickShooting();

	}

}
