package io.github.awesome90.crescent.detection.checks.movement.waterwalk;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

import com.comphenix.protocol.events.PacketContainer;

import io.github.awesome90.crescent.Crescent;
import io.github.awesome90.crescent.behaviour.Behaviour;
import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class WaterWalkA extends CheckVersion {

	/**
	 * The time that the player has been standing on water since.
	 */
	private long startTime;

	public WaterWalkA(Check check) {
		super(check, "A", "Checks whether the player is walking on water");

		this.startTime = -1;
	}

	@Override
	public void call(Event event) {
		if (event instanceof PlayerMoveEvent) {
			PlayerMoveEvent pme = (PlayerMoveEvent) event;

			final Behaviour behaviour = profile.getBehaviour();
			final GameMode mode = profile.getPlayer().getGameMode();

			if ((mode != GameMode.CREATIVE && mode != GameMode.SPECTATOR) && behaviour.isOnLiquidBlock()
					&& !behaviour.isInWater() && (!behaviour.isDescending() || !behaviour.isAscending())) {
				/*
				 * Do not execute this statement if the player is not descending
				 * (this could lead to false positives) and if the player is in
				 * water (and not standing on it).
				 */

				final Material from = getMaterialDown(pme.getFrom());
				final Material to = getMaterialDown(pme.getTo());

				if (isWater(from) && isWater(to)) {

					// The player is standing on water.
					if (startTime == -1) {
						startTime = System.currentTimeMillis();
					}

					if (System.currentTimeMillis() - startTime > Crescent.getInstance().getConfig()
							.getInt("waterwalk.a.walkTime")) {
						callback(true);
						startTime = -1;
						return;
					}

				} else {
					startTime = -1;
				}
			}

			callback(false);
		}
	}

	@Override
	public void call(PacketContainer packet) {

	}

	/**
	 * @return The percentage of a minute that the player has stood on water
	 *         for.
	 */
	@Override
	public double checkCurrentCertainty() {
		return (((System.currentTimeMillis() - startTime) / 1000.0) / 60.0) * 100.0;
	}

	private Material getMaterialDown(Location location) {
		return location.getBlock().getRelative(BlockFace.DOWN).getType();
	}

	private boolean isWater(Material material) {
		return material == Material.WATER || material == Material.STATIONARY_WATER;
	}
}
