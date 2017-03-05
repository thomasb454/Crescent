package io.github.awesome90.crescent.behaviour;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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

	public final boolean isInWater() {
		Material in = getBlockPlayerIsIn().getType();
		return in == Material.WATER || in == Material.STATIONARY_WATER;
	}

	public final boolean isInWeb() {
		return getBlockPlayerIsIn().getType() == Material.WEB;
	}

	public final boolean isOnLadder() {
		return getBlockPlayerIsIn().getType() == Material.LADDER;
	}

	public final boolean isOnVine() {
		return getBlockPlayerIsIn().getType() == Material.VINE;
	}

	public final boolean isOnLiquidBlock() {
		return getBlockUnderPlayer().isLiquid();
	}

	public final boolean isOnGround() {
		return ((Entity) getPlayer()).isOnGround();
	}

	public final Block getBlockPlayerIsIn() {
		return getPlayer().getLocation().getBlock();
	}

	public final Block getBlockAbovePlayer() {
		return getBlockOnFace(BlockFace.UP);
	}

	public final Block getBlockUnderPlayer() {
		return getBlockOnFace(BlockFace.DOWN);
	}

	public final Block getBlockOnFace(BlockFace face) {
		return getPlayer().getLocation().getBlock().getRelative(face);
	}

	public final int getPotionEffectLevel(PotionEffectType type) {
		for (PotionEffect effect : getPlayer().getActivePotionEffects()) {
			if (effect.getType().equals(type)) {
				return effect.getAmplifier();
			}
		}

		return 0;
	}

	public final double getEPF(int level, double typeModifier) {
		return Math.floor((6 + level * level) * typeModifier / 3);
	}

	private final Player getPlayer() {
		return profile.getPlayer();
	}

}
