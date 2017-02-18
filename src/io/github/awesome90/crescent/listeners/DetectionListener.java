package io.github.awesome90.crescent.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.awesome90.crescent.detection.CheckType;
import io.github.awesome90.crescent.detection.checks.CheckVersion;
import io.github.awesome90.crescent.info.Profile;

public class DetectionListener implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		final Player player = event.getPlayer();

		getCheckVersion(player, CheckType.SPEED, "A").call(event);

		getCheckVersion(player, CheckType.FLY, "A").call(event);
		getCheckVersion(player, CheckType.FLY, "B").call(event);

		getCheckVersion(player, CheckType.WATERWALK, "A").call(event);

		getCheckVersion(player, CheckType.SNEAK, "A").call(event);
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}

		final Player player = (Player) event.getEntity();

		getCheckVersion(player, CheckType.NOFALL, "A").call(event);
		getCheckVersion(player, CheckType.ANTIKNOCKBACK, "A").call(event);
		getCheckVersion(player, CheckType.ANTIKNOCKBACK, "B").call(event);
	}

	@EventHandler
	public void onPlayerInteract(EntityShootBowEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}

		final Player player = (Player) event.getEntity();

		getCheckVersion(player, CheckType.FASTBOW, "A").call(event);
	}

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();

		getCheckVersion(player, CheckType.SNEAK, "A").call(event);
	}

	private Profile getProfile(Player player) {
		return Profile.getProfile(player.getUniqueId());
	}

	private CheckVersion getCheckVersion(Player player, CheckType type, String version) {
		return getProfile(player).getCheck(type).getCheckVersion(version);
	}

}
