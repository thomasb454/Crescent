package io.github.awesome90.crescent.detection.checks.damage.killaura;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayServerEntityDestroy;
import com.comphenix.packetwrapper.WrapperPlayServerNamedEntitySpawn;
import com.comphenix.protocol.ProtocolManager;

import io.github.awesome90.crescent.Crescent;
import io.github.awesome90.crescent.detection.CheckType;
import io.github.awesome90.crescent.info.Profile;

public class FakePlayer {

	private boolean alive;
	private final Random random;
	private final UUID uuid;
	private final int id;
	private final ProtocolManager pm;

	private final KillauraA killaura;

	private int life;

	/**
	 * The players that can see the fake player.
	 */
	private final Player[] players;

	/**
	 * @param profile
	 *            The profile of the player who is being checked by this fake
	 *            player.
	 * @param life
	 *            The time in seconds of the player before it is despawned.
	 * @param players
	 *            The players that the fake player should be visible to.
	 */
	public FakePlayer(Profile profile, int life, Player... players) {
		this.alive = false;
		this.life = life;
		this.random = new Random();
		final Player randomPlayer = (Player) Bukkit.getOnlinePlayers().toArray()[random
				.nextInt(Bukkit.getOnlinePlayers().size())];
		this.uuid = randomPlayer.getUniqueId();
		this.id = random.nextInt(Integer.MAX_VALUE);
		this.pm = Crescent.getInstance().getProtocolManager();
		this.players = players;

		this.killaura = (KillauraA) profile.getCheck(CheckType.KILLAURA).getCheckVersion("A");
	}

	public void spawn(Location location) {
		WrapperPlayServerNamedEntitySpawn spawn = new WrapperPlayServerNamedEntitySpawn();
		spawn.setEntityID(id);
		spawn.setPlayerUUID(uuid);

		spawn.setPosition(location.toVector());

		this.alive = true;

		for (Player player : players) {
			try {
				pm.sendServerPacket(player, spawn.getHandle());
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		Bukkit.getScheduler().runTaskLater(Crescent.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (alive) {
					despawn();
				}
			}
		}, 20 * life);
	}

	public void despawn() {
		WrapperPlayServerEntityDestroy destroy = new WrapperPlayServerEntityDestroy();
		destroy.setEntityIds(new int[] { id });

		this.alive = false;

		for (Player player : players) {
			try {
				pm.sendServerPacket(player, destroy.getHandle());
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		killaura.removeFakePlayer(this);
	}

	public void hit() {
		despawn();
		killaura.callback(true);
	}

	public void broadcastToWatchers(String message) {
		for (Player player : players) {
			player.sendMessage(message);
		}
	}

}
