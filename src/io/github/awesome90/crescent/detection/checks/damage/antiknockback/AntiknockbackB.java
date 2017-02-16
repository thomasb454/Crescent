package io.github.awesome90.crescent.detection.checks.damage.antiknockback;

import org.bukkit.entity.Arrow;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class AntiknockbackB extends CheckVersion {

	public AntiknockbackB(Check check) {
		super(check, "B", "Checks the knockback a player takes from being shot with a bow.");
	}

	@Override
	public void call(Event event) {
		if (event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent edbye = (EntityDamageByEntityEvent) event;

			if (edbye.getDamager() instanceof Arrow) {

			}

		}
	}

	@Override
	public void check() {

	}

	@Override
	public double checkCurrentCertainty() {
		return 0;
	}

}
