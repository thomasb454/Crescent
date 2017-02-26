package io.github.awesome90.crescent.detection.checks.damage.killaura;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.comphenix.protocol.events.PacketContainer;

import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class KillauraA extends CheckVersion {

	private ArrayList<FakePlayer> currentChecks;

	public KillauraA(Check check) {
		super(check, "A", "Uses fake players to detect killaura.");
		this.currentChecks = new ArrayList<FakePlayer>();
	}

	public void addFakePlayer(FakePlayer player) {
		currentChecks.add(player);
	}

	public void removeFakePlayer(FakePlayer player) {
		currentChecks.remove(player);
	}

	@Override
	public void call(Event event) {
		if (event instanceof EntityDamageByEntityEvent) {
			// Spawn a fake player behind the player.
			spawnFakePlayer(profile.getPlayer().getLocation().toVector().multiply(-1).normalize()
					.toLocation(profile.getPlayer().getWorld()));
		}
	}

	public void spawnFakePlayer(Location location) {
		final FakePlayer fake = new FakePlayer(profile, 2);
		fake.spawn(location);

		currentChecks.add(fake);
	}

	@Override
	public void call(PacketContainer packet) {

	}

	@Override
	public double checkCurrentCertainty() {
		return 0;
	}

}
