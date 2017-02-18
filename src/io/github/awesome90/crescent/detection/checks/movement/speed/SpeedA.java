package io.github.awesome90.crescent.detection.checks.movement.speed;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.awesome90.crescent.Crescent;
import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class SpeedA extends CheckVersion {

	/**
	 * The total difference in the time between what the player has travelled
	 * for and what the player should have travelled for.
	 */
	private double totalChange;
	/**
	 * The total distance that the player should have travelled for.
	 */
	private double totalTime;

	/**
	 * The last recorded distance of the player (used to measure distance from
	 * current in order to calculate speed).
	 */
	private Location previous;
	/**
	 * The start time of the current measurement.
	 */
	private double start;

	public SpeedA(Check check) {
		super(check, "A", "Compares the movement speed of players compared to expected movement speeds.");

		this.totalChange = totalTime = 0.0;

		this.previous = null;
		this.start = -1;
	}

	@Override
	public void call(Event event) {
		if (event instanceof PlayerMoveEvent) {
			PlayerMoveEvent pme = (PlayerMoveEvent) event;

			/*
			 * Check if the player has actually moved (not just moved their
			 * head).
			 */
			if (pme.getTo().getBlockX() == pme.getFrom().getBlockX()
					&& pme.getTo().getBlockZ() == pme.getFrom().getBlockZ()) {
				return;
			}

			if (previous == null) {
				previous = new Location(pme.getFrom().getWorld(), pme.getFrom().getX(), pme.getFrom().getY(),
						pme.getFrom().getZ());
				start = System.currentTimeMillis();
				return;
			}

			final double distance = previous.distanceSquared(pme.getTo());

			final int distanceCheck = Crescent.getInstance().getConfig().getInt("speed.a.distanceCheck");

			if (distance >= (distanceCheck * distanceCheck)) {
				// Player has moved 5 blocks or over.

				final double speed = profile.getBehaviour().getCurrentTransportMethod().getSpeed();

				// Don't use square roots in the actual check!
				final double expected = (distance / (speed * speed)) * 1000.0;
				final double actual = Math.pow(System.currentTimeMillis() - start, 2.0);

				final double difference = Math.abs(expected - actual);

				if (difference >= Crescent.getInstance().getConfig().getInt("speed.a.compareDistance")) {
					callback(true);

					totalChange += difference;
					totalTime += expected;
				}

				previous = null;
			}
		}
	}

	@Override
	public void check() {

	}

	@Override
	public double checkCurrentCertainty() {
		return (totalChange / totalTime) * 100.0;
	}

}
