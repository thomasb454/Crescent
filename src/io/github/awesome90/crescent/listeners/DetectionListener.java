package io.github.awesome90.crescent.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import io.github.awesome90.crescent.Crescent;
import io.github.awesome90.crescent.behaviour.Behaviour;
import io.github.awesome90.crescent.detection.CheckType;
import io.github.awesome90.crescent.detection.checks.CheckVersion;
import io.github.awesome90.crescent.info.Profile;

public class DetectionListener implements Listener {

	public void addPacketListeners() {
		final Crescent crescent = Crescent.getInstance();

		crescent.getProtocolManager().addPacketListener(
				new PacketAdapter(crescent, ListenerPriority.NORMAL, PacketType.Play.Client.POSITION) {
					@Override
					public void onPacketReceiving(PacketEvent event) {
						if (event.getPacketType() == PacketType.Play.Client.POSITION) {
							getCheckVersion(event.getPlayer(), CheckType.SPEED, "A").call(event.getPacket());
						}
					}
				});

		crescent.getProtocolManager().addPacketListener(
				new PacketAdapter(crescent, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_VELOCITY) {
					@Override
					public void onPacketSending(PacketEvent event) {
						final Behaviour behaviour = Profile.getProfile(event.getPlayer().getUniqueId()).getBehaviour();
						final PacketContainer packet = event.getPacket();
						behaviour.setVelocity(packet.getIntegers().read(0), packet.getIntegers().read(1),
								packet.getIntegers().read(2));
					}
				});

		crescent.getProtocolManager().addPacketListener(
				new PacketAdapter(crescent, ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY) {
					@Override
					public void onPacketReceiving(PacketEvent event) {
						if (event.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
							final PacketContainer packet = event.getPacket();

							if (packet.getIntegers().read(0) == 1) {
								// 1 = attack.

							}
						}
					}
				});
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		final Player player = event.getPlayer();

		getCheckVersion(player, CheckType.FLY, "A").call(event);
		getCheckVersion(player, CheckType.FLY, "B").call(event);

		getCheckVersion(player, CheckType.WATERWALK, "A").call(event);

		getCheckVersion(player, CheckType.NOFALL, "A").call(event);

		getCheckVersion(player, CheckType.SNEAK, "A").call(event);
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			getCheckVersion((Player) event.getEntity(), CheckType.KILLAURA, "A").call(event);
		}

		if (event.getDamager() instanceof Player) {
			getCheckVersion((Player) event.getDamager(), CheckType.KILLAURA, "A").call(event);
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}

		final Player player = (Player) event.getEntity();

		getCheckVersion(player, CheckType.ANTIKNOCKBACK, "A").call(event);
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
	public void onEntityRegainHealth(EntityRegainHealthEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}

		final Player player = (Player) event.getEntity();

		getCheckVersion(player, CheckType.FASTHEAL, "A").call(event);
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
