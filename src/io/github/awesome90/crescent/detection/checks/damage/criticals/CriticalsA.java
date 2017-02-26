package io.github.awesome90.crescent.detection.checks.damage.criticals;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

import com.comphenix.protocol.events.PacketContainer;

import io.github.awesome90.crescent.behaviour.Behaviour;
import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class CriticalsA extends CheckVersion {

	public CriticalsA(Check check) {
		super(check, "A", "Checks if a player is distributing critical hits without the needed conditions to do so.");
	}

	@Override
	public void call(Event event) {
		if (event instanceof EntityDamageByEntityEvent) {
			final Player player = profile.getPlayer();

			if (canDealCritical() && profile.getBehaviour().getBlockUnderPlayer().getType().isSolid()
					&& (player.getLocation().getY() % 1.0 == 0.0 || player.getLocation().getY() % 0.5 == 0.0)) {
				// This is not a valid critical hit.
				callback(true);
			}
		}
	}

	/**
	 * @return Whether the player can deal a critical hit or not.
	 */
	private boolean canDealCritical() {
		final Player player = profile.getPlayer();
		final Behaviour behaviour = profile.getBehaviour();
		return behaviour.isDescending() && behaviour.isOnGround() && !behaviour.isOnLadder() && !behaviour.isOnVine()
				&& !behaviour.isInWater() && !player.hasPotionEffect(PotionEffectType.BLINDNESS)
				&& !player.isInsideVehicle() && !player.isSprinting();
	}

	@Override
	public void call(PacketContainer packet) {

	}

	@Override
	public double checkCurrentCertainty() {
		return 0;
	}

}
