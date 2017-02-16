package io.github.awesome90.crescent.detection.checks.movement.impossible;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class ImpossibleA extends CheckVersion {

	public ImpossibleA(Check check) {
		super(check, "A", "Checks whether the player is completing impossible actions.");
	}

	@Override
	public void call(Event event) {
		final Player player = profile.getPlayer();

		if (event instanceof PlayerMoveEvent) {
			if ((player.isSprinting() || player.isBlocking() || player.isConversing()) && player.isSneaking()) {
				callback(true);
				return;
			}
			callback(false);
		} else if (event instanceof AsyncPlayerChatEvent) {
			if ((player.isBlocking() || player.isSprinting() || player.isSneaking())) {
				callback(true);
				return;
			}
			callback(false);
		}
	}

	@Override
	public void check() {

	}

	@Override
	public double checkCurrentCertainty() {
		return (improperCalls / totalCalls) * 100.0;
	}
}
