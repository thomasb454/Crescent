package io.github.awesome90.crescent.detection.checks.damage.killaura;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class KillauraC extends CheckVersion {

	public KillauraC(Check check) {
		super(check, "C", "Checks the angle between the player and the target.");
	}

	@Override
	public void call(Event event) {
		if (event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent edbe = (EntityDamageByEntityEvent) event;

			final float playerYaw = profile.getPlayer().getEyeLocation().getYaw();
			final float targetYaw = edbe.getEntity().getLocation().getYaw();
			final double difference = Math.abs(playerYaw - targetYaw);

			Bukkit.broadcastMessage(difference + "");
		}
	}

	@Override
	public double checkCurrentCertainty() {
		return 0;
	}

}
