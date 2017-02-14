package io.github.awesome90.crescent.detection.checks.movement;

import org.bukkit.Material;

import io.github.awesome90.crescent.behaviour.Behaviour;
import io.github.awesome90.crescent.detection.CheckType;
import io.github.awesome90.crescent.detection.checks.CheckVersion;
import io.github.awesome90.crescent.info.Profile;

public class WaterwalkA extends CheckVersion {

	/**
	 * The time that the player has been standing on water since.
	 */
	private long startTime;

	public WaterwalkA(Profile profile) {
		super(profile, CheckType.WATERWALK, "A");

		this.startTime = -1;
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
