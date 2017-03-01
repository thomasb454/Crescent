package io.github.awesome90.crescent.detection.checks.damage.killaura;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class KillauraB extends CheckVersion {

	public KillauraB(Check check) {
		super(check, "B",
				"Check if the angle between the player's eye and an attacked entity's eye is greater than 90 degrees when the entity is attacked.");
	}

	@Override
	public void call(Event event) {
		if (event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent edbe = (EntityDamageByEntityEvent) event;

			final Location playerLoc = profile.getPlayer().getLocation();
			final Location damagedLoc = edbe.getEntity().getLocation();

			final double diffX = Math.abs(playerLoc.getX() - damagedLoc.getX()),
					diffZ = Math.abs(playerLoc.getZ() - damagedLoc.getZ());

			final double angle = Math.tan(diffX / diffZ);

			Bukkit.broadcastMessage("angle: " + angle);
		}
	}

	@Override
	public double checkCurrentCertainty() {
		return 0;
	}

}
