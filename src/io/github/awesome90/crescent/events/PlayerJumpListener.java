package io.github.awesome90.crescent.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.awesome90.crescent.info.Profile;

public class PlayerJumpListener implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		final Player player = event.getPlayer();
		final Profile profile = Profile.getProfile(player.getUniqueId());

		final Location from = event.getFrom(), to = event.getTo();

		if (to.getY() > from.getY()) {
			final double movedUpDistance = ((to.getY() - from.getY()) * 1000) / 1000.0;

			/*
			 * Thanks to sothatsit for his resource for this:
			 * https://www.spigotmc.org/threads/accurate-playerjumpevent-do-
			 * actions-when-players-jump.138946/
			 */
			if ((movedUpDistance < 0.116 || movedUpDistance > 0.118)
					&& (movedUpDistance > 0.37 || movedUpDistance < 0.35)) {

				// Set player as jumping in their Behaviour instance.
				profile.getBehaviour().setJumping(true);

				Bukkit.getPluginManager().callEvent(new PlayerJumpEvent(player, movedUpDistance));

				return;
			}
		}

		if (((Entity) player).isOnGround()) {
			profile.getBehaviour().setJumping(false);
		}
	}

}
