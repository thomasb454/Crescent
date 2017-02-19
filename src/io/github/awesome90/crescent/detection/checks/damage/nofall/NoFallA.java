package io.github.awesome90.crescent.detection.checks.damage.nofall;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.awesome90.crescent.behaviour.Behaviour;
import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;
import io.github.awesome90.crescent.info.Profile;

public class NoFallA extends CheckVersion {

	/**
	 * The total damage that a player should have taken.
	 */
	private double totalDamage;
	/**
	 * The difference the damage that a player should have taken and the damage
	 * that they actually took.
	 */
	private double totalDisplacedHealth;

	public NoFallA(Check check) {
		super(check, "A", "Checks the damage that the player took compared to the damage that they should have taken.");
		this.totalDamage = totalDisplacedHealth = 0.0;
	}

	@Override
	public void call(Event event) {
		if (event instanceof PlayerMoveEvent) {
			final PlayerMoveEvent pme = (PlayerMoveEvent) event;

			final Player player = profile.getPlayer();
			final float fallDistance = player.getFallDistance();

			final Behaviour behaviour = profile.getBehaviour();

			// Check if player has moved from air to ground.
			if (pme.getFrom().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR
					&& pme.getTo().getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
				if (player.getGameMode() != GameMode.CREATIVE && !behaviour.isInWater() && !behaviour.isInWeb()
						&& !player.isInsideVehicle() && !player.isSleeping()) {
					final double expected = getExpectedDamage(profile, fallDistance);
					Bukkit.broadcastMessage("expected: " + expected);
					Bukkit.broadcastMessage("actual: " + player.getHealth());

					if (player.getHealth() < expected) {
						callback(true);
					}
				}
			}
		}
	}

	/*
	 * No use for check method here, things can only be checked in the above
	 * listener.
	 */
	@Override
	public void check() {

	}

	@Override
	public double checkCurrentCertainty() {
		return (totalDisplacedHealth / totalDamage) * 100.0;
	}

	private double getExpectedDamage(Profile profile, float fallDistance) {
		final int featherFalling = profile.getPlayer().getInventory().getBoots()
				.getEnchantmentLevel(Enchantment.PROTECTION_FALL);
		final double expectedDamage = (fallDistance * 0.5) - 1.5;
		final double epf = profile.getBehaviour().getEPF(featherFalling, 2.5);

		// Return how much the EPF reduces the damage by.
		return expectedDamage / epf;
	}

}
