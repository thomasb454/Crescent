package io.github.awesome90.crescent.detection.checks.movement.fly;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.awesome90.crescent.Crescent;
import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class FlyB extends CheckVersion {

	/**
	 * The time that the player begins to exhibit suspicious characteristics.
	 */
	private long startSuspicious;
	/**
	 * Count of times that a player has moved in the air while the y axis is not
	 * decreasing.
	 */
	private long count;

	public FlyB(Check check) {
		super(check, "B",
				"Check for movement of x and z axes whilst the y axis is not moving or moving up whilst the player is in the air.");
		this.startSuspicious = -1;
		this.count = 0;
	}

	@Override
	public void call(Event event) {
		if (event instanceof PlayerMoveEvent) {
			PlayerMoveEvent pme = (PlayerMoveEvent) event;

			if (profile.getBehaviour().isDescending()) {
				return;
			}

			final Location from = pme.getFrom();
			final Location to = pme.getTo();

			/*
			 * Player must be in the air and moving (at least one block) to
			 * trigger this.
			 */
			if (from.getBlockY() == to.getBlockY()
					&& (from.getBlockX() != to.getBlockX() || from.getBlockZ() != to.getBlockZ())
					&& profile.getBehaviour().getBlockUnderPlayer().getType() == Material.AIR) {
				count++;
				if (startSuspicious == -1) {
					startSuspicious = System.currentTimeMillis();
				}

				if (count > Crescent.getInstance().getConfig().getInt("fly.b.countInAir") && System.currentTimeMillis()
						- startSuspicious >= Crescent.getInstance().getConfig().getInt("fly.b.flyCertain")) {
					callback(true);
					startSuspicious = -1;
					return;
				}
			}

			callback(false);
		}
	}

	@Override
	public void check() {

	}

	@Override
	public double checkCurrentCertainty() {
		return (count / totalCalls) * 100.0;
	}

}
