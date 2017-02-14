package io.github.awesome90.crescent.behaviour;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

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

	public final boolean isOnLiquidBlock() {
		return getBlockUnderPlayer().isLiquid();
	}

	public final Block getBlockUnderPlayer() {
		return profile.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
	}

}
