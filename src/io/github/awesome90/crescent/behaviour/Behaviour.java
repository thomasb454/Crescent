package io.github.awesome90.crescent.behaviour;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import io.github.awesome90.crescent.info.Profile;

public class Behaviour {

	private final Profile profile;

	private boolean jumping;

	/**
	 * @param profile
	 *            The profile of the player whose behaviour is being analysed.
	 */
	public Behaviour(Profile profile) {
		this.profile = profile;
		this.jumping = false;
	}

	public final TransportMethod getCurrentTransportMethod() {
		final Player player = getPlayer();

		if (player.isSprinting() && jumping) {
			return TransportMethod.SPRINT_JUMPING;
		} else if (player.isSprinting()) {
			return TransportMethod.SPRINTING;
		} else if (player.isSprinting() && player.isFlying()) {
			return TransportMethod.FLYING_SPRINTING;
		} else if (player.isFlying()) {
			return TransportMethod.FLYING;
		} else {
			return TransportMethod.WALKING;
		}

	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public final boolean isJumping() {
		return jumping;
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

	public final boolean isInWeb() {
		return getBlockPlayerIsIn().getType() == Material.WEB;
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

	public final double getEPF(int level, double typeModifier) {
		return Math.floor((6 + Math.pow(level, 2)) * typeModifier / 3);
	}

	private final Player getPlayer() {
		return profile.getPlayer();
	}

}
