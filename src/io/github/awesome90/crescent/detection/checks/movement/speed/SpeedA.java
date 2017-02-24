package io.github.awesome90.crescent.detection.checks.movement.speed;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import com.comphenix.protocol.events.PacketContainer;

import io.github.awesome90.crescent.behaviour.Behaviour;
import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class SpeedA extends CheckVersion {

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

	}

	@Override
	public void call(PacketContainer packet) {
		final double x = packet.getDoubles().read(0), y = packet.getDoubles().read(1), z = packet.getDoubles().read(2);

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

	private void check(double x, double y, double z) {
		final double timeDifference = (System.currentTimeMillis() - lastTime) / 1000;
		if (timeDifference >= checkTime) {
			final double deltaX = Math.abs(x - lastX), deltaY = Math.abs(y - lastY), deltaZ = Math.abs(z - lastZ);

			final double movementSquared = (deltaX * deltaX) + (deltaY + deltaY) + (deltaZ * deltaZ);

			final Behaviour behaviour = profile.getBehaviour();
			final double velX = behaviour.getVelX(), velY = behaviour.getVelY(), velZ = behaviour.getVelZ();

			final double expectedMovementSquared = (velX * velX) + (velY * velY) + (velZ * velZ);

			final double difference = (movementSquared - expectedMovementSquared) * (checkTime * 20);
			Bukkit.broadcastMessage(difference + "");
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
