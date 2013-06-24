package game.entity.mob.AI;

import game.Game;
import game.entity.mob.Mob;
import game.entity.mob.Player;
import game.level.Level;

public class Stalker extends AI {

	// These names are extremely confusing... damageCount is the variable
	// counting down (until damage can be done again), damageCounter is the
	// value damageCount is reset to
	protected int damageCount, damageCounter;
	
	public Stalker(Game game) {
		super(game);
		sprite = sprite.shot;
		speed = 1;
		range = 200;
		health = 2;
		damageCounter = 60;
		randomTick = randomTick();
		
		// Makes the AIs move straight away
		angle = Math.toRadians(random.nextInt(360));
		xDir = Math.cos(angle) * speed;
		yDir = Math.sin(angle) * speed;
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
		
		// This just gets the player
		Player player = game.getPlayer();
		

		tickCount++;

		if (sees(player)) {
			seenPlayer = true;
		}

		if (tickCount >= randomTick || collided) {
			tickCount = 0;
			randomTick = randomTick();

			double dx = player.getX() - x;
			double dy = player.getY() - y;

			if (collided) {
				angle = Math.toRadians(random.nextInt(360));
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
}
