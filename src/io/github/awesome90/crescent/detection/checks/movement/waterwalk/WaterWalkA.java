package io.github.awesome90.crescent.detection.checks.movement.waterwalk;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.awesome90.crescent.behaviour.Behaviour;
import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class WaterWalkA extends CheckVersion {

	/**
	 * The time that the player has been standing on water since.
	 */
	private long startTime;

	public WaterWalkA(Check check) {
		super(check, "A");

		this.startTime = -1;
	}

	@Override
	public void call(Event event) {
		if (event instanceof PlayerMoveEvent) {
			check();
		}
	}

	@Override
	public void check() {
		final Behaviour behaviour = profile.getBehaviour();

		if (!behaviour.isInWater()) {

			/*
			 * Do not execute this statement if the player is not descending
			 * (this could lead to false positives) and if the player is in
			 * water (and not standing on it).
			 */

			if (!behaviour.isDescending() && behaviour.isOnLiquidBlock()) {
				// The player is standing on either water or lava.
				final Material under = behaviour.getBlockUnderPlayer().getType();

				if (under == Material.WATER || under == Material.STATIONARY_WATER) {
					// The player is standing on water.
					startTime = System.currentTimeMillis();
					callback(true, "Standing on water.");
					return;
				}
			}
		}

		if (startTime != -1) {
			// Since the player is no longer standing on water, we must reset
			// this value.
			startTime = -1;
		}

	}

	/**
	 * @return The percentage of a minute that the player has stood on water
	 *         for.
	 */
	@Override
	public double checkCurrentCertainty() {
		return (((System.currentTimeMillis() - startTime) / 1000.0) / 60.0) * 100.0;
	}
}
