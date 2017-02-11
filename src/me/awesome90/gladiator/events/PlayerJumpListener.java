package me.awesome90.gladiator.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerJumpListener implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		final Player player = event.getPlayer();

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
				Bukkit.getPluginManager().callEvent(new PlayerJumpEvent(player, movedUpDistance));
			}
		}
	}

}
