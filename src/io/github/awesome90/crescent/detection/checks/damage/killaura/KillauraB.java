package io.github.awesome90.crescent.detection.checks.damage.killaura;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.comphenix.protocol.events.PacketContainer;

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

			double angle = profile.getPlayer().getLocation().toVector()
					.angle(edbe.getEntity().getLocation().toVector());

			if (angle > 90) {
				callback(true);
			}
		}
	}

	@Override
	public void call(PacketContainer packet) {

	}

	@Override
	public double checkCurrentCertainty() {
		return 0;
	}

}
