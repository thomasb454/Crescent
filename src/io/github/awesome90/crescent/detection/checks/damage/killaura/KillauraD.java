package io.github.awesome90.crescent.detection.checks.damage.killaura;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class KillauraD extends CheckVersion {

	public KillauraD(Check check) {
		super(check, "D",
				"Check the pitch angle between the player and the entity they damage compared to a calculated value.");
	}

	@Override
	public void call(Event event) {
		if (event instanceof EntityDamageByEntityEvent) {
			final EntityDamageByEntityEvent edbe = (EntityDamageByEntityEvent) event;

			final Player player = profile.getPlayer();

			if (!(edbe.getEntity() instanceof LivingEntity)) {
				return;
			}

			LivingEntity le = (LivingEntity) edbe.getEntity();

			final Location playerEye = player.getEyeLocation();
			final Location target = le.getLocation().clone().add(0, 1, 0);

			final double distance = round(getExpectedDistance(playerEye, target));
			final double expectedDistance = round(
					Math.abs(Math.abs(playerEye.getY() - target.getY()) / Math.sin(Math.abs(playerEye.getPitch()))));

			Bukkit.broadcastMessage("expected: " + expectedDistance + " actual: " + distance);
		}
	}

	/**
	 * @param from
	 *            The location from where we want to start measuring.
	 * @param to
	 *            The location we want to measure to.
	 * @return The distance.
	 */
	private double getExpectedDistance(Location from, Location to) {
		return Math.pow(from.getX() - to.getX(), 2.0) + Math.pow(from.getY() - to.getY(), 2.0)
				+ Math.pow(from.getZ() - to.getZ(), 2.0);
	}

	/**
	 * @param value
	 *            The value to round.
	 * @return A value rounded to the nearest 0.5.
	 */
	private double round(double value) {
		return Math.round(value) + 0.5;
	}

	@Override
	public double checkCurrentCertainty() {
		return 0;
	}

}
