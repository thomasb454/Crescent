package me.awesome90.gladiator.behaviour;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import me.awesome90.gladiator.info.Profile;

public class Behaviour {

	private final Profile profile;

	public Behaviour(Profile profile) {
		this.profile = profile;
	}

	public boolean isOnLiquidBlock() {
		return getBlockUnderPlayer().isLiquid();
	}

	public Block getBlockUnderPlayer() {
		return profile.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
	}

}
