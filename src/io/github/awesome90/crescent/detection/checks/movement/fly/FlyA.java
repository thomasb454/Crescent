package io.github.awesome90.crescent.detection.checks.movement.fly;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class FlyA extends CheckVersion {

	public FlyA(Check check) {
		super(check, "A", "Checks if the player has been ascending for a large amount of time.");
	}

	@Override
	public void call(Event event) {
		if (event instanceof PlayerMoveEvent) {

			final Player player = profile.getPlayer();

			if (player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR
					&& !player.getAllowFlight()) {
				final PlayerMoveEvent pme = (PlayerMoveEvent) event;
				
				final double distanceSquared = pme.getTo().toVector().distanceSquared(pme.getFrom().toVector());

				/*
				 * If the player isn't on the ground (they could be walking),
				 * check if the block above the player is air and the player has
				 * not fallen.
				 * 
				 * If so, they are using fly cheats.
				 */
				if (!profile.getBehaviour().isOnGround()
						&& profile.getBehaviour().getBlockAbovePlayer().getType() == Material.AIR
						&& player.getFallDistance() == 0.0) {

					/*
					 * I roughly measured jumping as 0.15 as a vector squared
					 * between the to and from points.
					 * 
					 * Here, 0.25 is being used to avoid false positives.
					 */
					if (distanceSquared > 0.25) {
						callback(true);
						Bukkit.broadcastMessage("distance: " + distanceSquared);
					}
				}

			}
		}
	}

	@Override
	public double checkCurrentCertainty() {
		return 0;
	}

}
