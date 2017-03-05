package io.github.awesome90.crescent.detection.checks.damage.killaura;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class KillauraA extends CheckVersion {

	/**
	 * The amount of hits that the player has made.
	 */
	private long hits;
	/**
	 * The time that the hits started being counted from.
	 */
	private long lastRecord;

	public KillauraA(Check check) {
		super(check, "A", "This checks the frequency of attacks that a player is making.");
		this.hits = lastRecord = 0;
	}

	@Override
	public void call(Event event) {
		if (event instanceof EntityDamageByEntityEvent) {
			final EntityDamageByEntityEvent edbe = (EntityDamageByEntityEvent) event;

			if (lastRecord == 0 || System.currentTimeMillis() - lastRecord >= 5000) {
				reset();
			}

			hits++;

			final double hitsPerSecond = getHitsPerSecond();

			Bukkit.broadcastMessage(ChatColor.AQUA + "" + hitsPerSecond);
		}
	}

	@Override
	public double checkCurrentCertainty() {
		return 0;
	}

	/**
	 * Reset the hits count and set the lastRecord variable to the current time.
	 */
	private void reset() {
		this.hits = 0;
		this.lastRecord = System.currentTimeMillis();
	}

	/**
	 * @return The amount of hits that a player makes per second.
	 */
	private double getHitsPerSecond() {
		return hits / ((System.currentTimeMillis() - lastRecord) * 1000.0);
	}

}
