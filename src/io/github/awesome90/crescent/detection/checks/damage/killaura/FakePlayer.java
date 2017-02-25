package io.github.awesome90.crescent.detection.checks.damage.killaura;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayServerEntityDestroy;
import com.comphenix.packetwrapper.WrapperPlayerServerNamedEntitySpawn;
import com.comphenix.protocol.ProtocolManager;

import io.github.awesome90.crescent.Crescent;

public class FakePlayer {

	private final Random random;
	private final String name;
	private final UUID uuid;
	private final int id;
	private final ProtocolManager pm;

	/**
	 * The players that can see the fake player.
	 */
	private final Player[] players;

	public FakePlayer(Player... players) {
		this.random = new Random();
		final Player randomPlayer = (Player) Bukkit.getOnlinePlayers().toArray()[random
				.nextInt(Bukkit.getOnlinePlayers().size())];
		this.name = randomPlayer.getName();
		this.uuid = randomPlayer.getUniqueId();
		this.id = random.nextInt(Integer.MAX_VALUE);
		this.pm = Crescent.getInstance().getProtocolManager();
		this.players = players;
	}

	public void spawn(Location location) {
		WrapperPlayerServerNamedEntitySpawn spawn = new WrapperPlayerServerNamedEntitySpawn();
		spawn.setEntityID(id);
		spawn.setPosition(location.toVector());
		spawn.setPlayerUUID(uuid.toString());
		spawn.setPlayerName(name);

		for (Player player : players) {
			try {
				pm.sendServerPacket(player, spawn.getHandle());
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	public void despawn() {
		WrapperPlayServerEntityDestroy destroy = new WrapperPlayServerEntityDestroy();
		destroy.setEntities(new int[] { id });

		for (Player player : players) {
			try {
				pm.sendServerPacket(player, destroy.getHandle());
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

}
