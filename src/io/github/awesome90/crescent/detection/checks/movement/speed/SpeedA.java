package io.github.awesome90.crescent.detection.checks.movement.speed;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class SpeedA extends CheckVersion {

	public SpeedA(Check check) {
		super(check, "A", "Checks if a player is moving too quickly.");
	}

	@Override
	public void call(Event event) {
		if (event instanceof PlayerMoveEvent) {
			final PlayerMoveEvent pme = (PlayerMoveEvent) event;

			final int speedLevel = profile.getBehaviour().getPotionEffectLevel(PotionEffectType.SPEED);

			// Ignore if the player's speed is higher than two.
			if (speedLevel > 2) {
				return;
			}

			/*
			 * Ignore y for this check. We only want to check speed on the x and
			 * z axes.
			 */
			final Vector from = pme.getFrom().toVector().clone().setY(0.0),
					to = pme.getTo().toVector().clone().setY(0.0);

			double speed = to.distanceSquared(from);

			if (speedLevel > 0) {
				// Take into account speed potions.
				speed -= (speed / 100.0) * (speedLevel * 20.0);
			}

			if (speed > 0.40) {
				callback(true);
			}
		}
	}

	@Override
	public double checkCurrentCertainty() {
		return 0.0;
	}

}
