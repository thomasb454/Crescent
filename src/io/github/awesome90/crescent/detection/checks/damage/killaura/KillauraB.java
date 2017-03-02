package io.github.awesome90.crescent.detection.checks.damage.killaura;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

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

			final Player player = profile.getPlayer();
			final Vector compare = edbe.getEntity().getLocation().toVector().subtract(player.getLocation().toVector())
					.normalize();
			final Vector eyeCompare = player.getEyeLocation().toVector().subtract(player.getLocation().toVector())
					.normalize();

			final double angle = Math.toDegrees(compare.angle(eyeCompare));
		}
	}

	@Override
	public double checkCurrentCertainty() {
		return 0;
	}

}
