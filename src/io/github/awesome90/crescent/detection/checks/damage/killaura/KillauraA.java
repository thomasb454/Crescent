package io.github.awesome90.crescent.detection.checks.damage.killaura;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.comphenix.protocol.events.PacketContainer;

import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class KillauraA extends CheckVersion {

	public KillauraA(Check check, String checkVersion, String description) {
		super(check, checkVersion, description);
	}

	@Override
	public void call(Event event) {
		if (event instanceof EntityDamageByEntityEvent) {
			// Spawn a fake player behind the player.
			spawnFakePlayer(profile.getPlayer().getLocation().toVector().multiply(-1).normalize()
					.toLocation(profile.getPlayer().getWorld()));
		}
	}

	private void spawnFakePlayer(Location location) {
		final FakePlayer fake = new FakePlayer();
		fake.spawn(location, profile.getPlayer());
	}

	@Override
	public void call(PacketContainer packet) {

	}

	@Override
	public double checkCurrentCertainty() {
		return 0;
	}

}
