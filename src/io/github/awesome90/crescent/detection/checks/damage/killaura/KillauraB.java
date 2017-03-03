package io.github.awesome90.crescent.detection.checks.damage.killaura;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
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

			if (edbe.getCause() != DamageCause.ENTITY_ATTACK) {
				return;
			}

			final Player player = profile.getPlayer();

			final Vector direction = player.getLocation().getDirection().normalize();
			final Vector target = edbe.getEntity().getLocation().toVector().normalize();

			double angle = Math.toDegrees(direction.angle(target));

			Bukkit.broadcastMessage("angle: " + angle);
		}
	}

	@Override
	public double checkCurrentCertainty() {
		return 0;
	}

}
