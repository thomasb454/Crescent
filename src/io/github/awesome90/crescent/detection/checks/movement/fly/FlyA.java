package io.github.awesome90.crescent.detection.checks.movement.fly;

import org.bukkit.GameMode;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.awesome90.crescent.Crescent;
import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class FlyA extends CheckVersion {

	private long startAscending;

	public FlyA(Check check) {
		super(check, "A", "Checks if the player has been ascending for a large amount of time.");
		this.startAscending = -1;
	}

	@Override
	public void call(Event event) {
		if (event instanceof PlayerMoveEvent) {

			final GameMode mode = profile.getPlayer().getGameMode();

			if ((mode != GameMode.CREATIVE && mode != GameMode.SPECTATOR)
					&& (profile.getBehaviour().isAscending() && profile.getBehaviour().isJumping())) {
				// If the player is ascending and is not jumping.

				if (startAscending == -1) {
					startAscending = System.currentTimeMillis();
				} else if (System.currentTimeMillis() - startAscending >= Crescent.getInstance().getConfig()
						.getInt("fly.a.ascendFlyCertain")) {
					callback(true);
					startAscending = -1;
				}

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
		return 0;
	}

}
