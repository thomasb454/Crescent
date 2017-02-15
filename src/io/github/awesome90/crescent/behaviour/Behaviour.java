package io.github.awesome90.crescent.behaviour;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import io.github.awesome90.crescent.info.Profile;

public class Behaviour {

	private final Profile profile;

	/**
	 * @param profile
	 *            The profile of the player whose behaviour is being analysed.
	 */
	public Behaviour(Profile profile) {
		this.profile = profile;
	}

	public final boolean isAscending() {
		return getPlayer().getVelocity().getY() > 0;
	}

	public final boolean isDescending() {
		return getPlayer().getVelocity().getY() < 0;
	}

	public final boolean isInWater() {
		Material in = getBlockPlayerIsIn().getType();
		return in == Material.WATER || in == Material.STATIONARY_WATER;
	}

	public final boolean isOnLadder() {
		return false; // Change this!
	}

	public final boolean isOnLiquidBlock() {
		return getBlockUnderPlayer().isLiquid();
	}

	public final Block getBlockPlayerIsIn() {
		return getPlayer().getLocation().getBlock();
	}

	public final Block getBlockUnderPlayer() {
		return getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
	}

	private final Player getPlayer() {
		return profile.getPlayer();
	}

}
