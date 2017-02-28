package io.github.awesome90.crescent.detection.checks.movement.timer;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class TimerA extends CheckVersion {

	private long lastTime;
	private long count;

	public TimerA(Check check) {
		super(check, "A", "Checks if the player is sending more movement packets than accepted.");

		this.lastTime = -1;
		this.count = 0;
	}

	@Override
	public void call(Event event) {
		if (event instanceof PlayerMoveEvent) {
			final PlayerMoveEvent pme = (PlayerMoveEvent) event;

			if (lastTime == -1) {
				lastTime = System.currentTimeMillis();
				return;
			}

			count++;

			if (System.currentTimeMillis() - lastTime >= 5000) {
				// Five seconds or over have passed.

				// Add one to the distance to prevent dividing by zero.
				final long difference = (System.currentTimeMillis() - lastTime) + 1;

				// The amount of movement packets being sent each second.
				double pps = (count / (difference * 1000.0)) / 5.0;

				// Bukkit.broadcastMessage(Math.ceil(pps) + "");

				count = 0;
			}
		}
	}

	@Override
	public double checkCurrentCertainty() {
		return 0;
	}

}
