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
			final Location target = le.getLocation();

			final double distance = getExpectedDistance(playerEye, target);
			final double expectedDistance = Math
					.abs(Math.abs(playerEye.getY() - target.getY()) / Math.sin(Math.abs(playerEye.getPitch())));

			Bukkit.broadcastMessage("expected: " + expectedDistance + " actual: " + distance);
		}
	}

	private double getExpectedDistance(Location from, Location to) {
		return Math.sqrt(Math.pow(from.getX() - to.getX(), 2.0) + Math.pow(from.getY() - to.getY(), 2.0)
				+ Math.pow(from.getZ() - to.getZ(), 2.0));
	}

	@Override
	public double checkCurrentCertainty() {
		return 0;
	}

}
