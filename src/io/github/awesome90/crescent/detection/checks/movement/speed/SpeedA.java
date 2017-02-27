package io.github.awesome90.crescent.detection.checks.movement.speed;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class SpeedA extends CheckVersion {

	/*
	* This check is broken. I need to recode it.
	*/
	
	private static final int checkTime = 5;

	private double lastTime;
	private double lastX, lastY, lastZ;

	public SpeedA(Check check) {
		super(check, "A", "Checks if a player is moving too quickly.");
		this.lastTime = -1;
		this.lastX = lastY = lastZ = -1.0;
	}

	@Override
	public void call(Event event) {
		if (event instanceof PlayerMoveEvent) {
			PlayerMoveEvent pme = (PlayerMoveEvent) event;
			final double x = pme.getTo().getX(), y = pme.getTo().getY(), z = pme.getTo().getZ();

			if (x == lastX && y == lastY && z == lastZ) {
				// The player has not moved so return.
				return;
			}

			if (lastTime == -1.0) {
				lastTime = System.currentTimeMillis();
				lastX = x;
				lastY = y;
				lastZ = z;
			} else {
				check(x, y, z);
			}
		}
	}

	private void check(double x, double y, double z) {
		final double timeDifference = (System.currentTimeMillis() - lastTime) / 1000.0;
		if (timeDifference >= checkTime) {
			final double deltaX = Math.abs(x - lastX), deltaY = Math.abs(y - lastY), deltaZ = Math.abs(z - lastZ);

			final double movementSquared = (deltaX * deltaX) + (deltaY + deltaY) + (deltaZ * deltaZ);

			final Vector velocity = profile.getPlayer().getVelocity();
			final double velX = velocity.getX(), velY = velocity.getY(), velZ = velocity.getZ();

			final double expectedMovementSquared = (velX * velX) + (velY * velY) + (velZ * velZ);

			final double difference = (movementSquared - expectedMovementSquared) * (checkTime * 20);
			if (difference > 100.0) {
				// The player is moving too quickly.
				lastTime = -1.0; // Reset the timer.
				callback(true);
			}
		}
	}

	@Override
	public double checkCurrentCertainty() {
		return 0.0;
	}

}
