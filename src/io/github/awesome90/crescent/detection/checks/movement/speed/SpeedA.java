package io.github.awesome90.crescent.detection.checks.movement.speed;

import org.bukkit.event.Event;
import org.bukkit.util.Vector;

import com.comphenix.protocol.events.PacketContainer;

import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class SpeedA extends CheckVersion {

	private long lastTime;
	private double lastX, lastY, lastZ;

	public SpeedA(Check check) {
		super(check, "A", "Checks if a player is moving too quickly.");
		this.lastTime = -1;
		this.lastX = lastY = lastZ = -1.0;
	}

	@Override
	public void call(Event event) {

	}

	@Override
	public void call(PacketContainer packet) {
		final double x = packet.getDoubles().read(0), y = packet.getDoubles().read(1), z = packet.getDoubles().read(2);

		final int checkTime = 5; // Check every five seconds.

		if (lastTime != -1) {
			if ((System.currentTimeMillis() - lastTime) / 1000 >= checkTime) {
				final double deltaX = Math.abs(x - lastX), deltaY = Math.abs(y - lastY), deltaZ = Math.abs(z - lastZ);

				final double movementSquared = (deltaX * deltaX) + (deltaY + deltaY) + (deltaZ * deltaZ);

				final Vector vector = profile.getPlayer().getVelocity();

				final double expectedMovementSquared = (vector.getX() * vector.getX()) + (vector.getY() * vector.getY())
						+ (vector.getZ() * vector.getZ());

				if ((movementSquared - expectedMovementSquared) / (checkTime * 20) > 100) {
					// The player is moving too quickly.
					callback(true);
				}
			}
		}

		this.lastX = x;
		this.lastY = y;
		this.lastZ = z;

		this.lastTime = System.currentTimeMillis();
	}

	@Override
	public double checkCurrentCertainty() {
		return 0.0;
	}

}
