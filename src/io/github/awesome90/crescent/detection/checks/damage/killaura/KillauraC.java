package io.github.awesome90.crescent.detection.checks.damage.killaura;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
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
			final EntityDamageByEntityEvent edbe = (EntityDamageByEntityEvent) event;

			if (!(edbe.getEntity() instanceof LivingEntity)) {
				return;
			}

			LivingEntity le = (LivingEntity) edbe.getEntity();

			final double playerYaw = profile.getPlayer().getEyeLocation().getYaw();
			final double targetYaw = le.getEyeLocation().getYaw();

			double expected;

			if (playerYaw < 0) {
				expected = playerYaw + 180;
			} else {
				expected = playerYaw - 180;
			}

			Bukkit.broadcastMessage(ChatColor.GREEN + "expected: " + expected + ", actual: " + targetYaw);
		}
	}

	@Override
	public double checkCurrentCertainty() {
		return 0;
	}

}
