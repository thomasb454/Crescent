package io.github.awesome90.crescent.detection.checks.damage.nofall;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.awesome90.crescent.behaviour.Behaviour;
import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;
import io.github.awesome90.crescent.info.Profile;

public class NoFallA extends CheckVersion {

	private double seconds;
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
		this.seconds = -1.0;
		this.totalDamage = totalDisplacedHealth = 0.0;
		this.totalDisplacedHealth = 0.0;
	}

	@Override
	public void call(Event event) {
		if (event instanceof PlayerMoveEvent) {
			final PlayerMoveEvent pme = (PlayerMoveEvent) event;

			final Player player = profile.getPlayer();

			if (player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {

				final float fallDistance = player.getFallDistance();

				final Behaviour behaviour = profile.getBehaviour();

				if (seconds == -1.0 && behaviour.isDescending()) {
					seconds = System.currentTimeMillis();
				}

				// Check if player has moved from air to ground.
				final Material from = pme.getFrom().getBlock().getRelative(BlockFace.DOWN).getType();
				final Material to = pme.getTo().getBlock().getRelative(BlockFace.DOWN).getType();

				if (from == Material.AIR && to.isSolid()) {
					if (!behaviour.isInWater() && !behaviour.isInWeb() && !player.isInsideVehicle()
							&& !player.isSleeping()) {
						final double expected = getExpectedDamage(profile, to, fallDistance,
								System.currentTimeMillis() - seconds);

						// Reset the timer.
						seconds = -1.0;
						Bukkit.broadcastMessage("expected: " + (player.getHealth() - expected));
						Bukkit.broadcastMessage("actual: " + player.getHealth());

						if (player.getHealth() < expected) {
							callback(true);
						}
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

	/**
	 * @param profile
	 *            The profile of the player.
	 * @param fallDistance
	 *            The distance the player has fallen.
	 * @return How much their armour EPF reduces their fall damage.
	 */
	private double getExpectedDamage(Profile profile, Material under, float fallDistance, double seconds) {
		final Player player = profile.getPlayer();

		int jump = 0, resistance = 0;

		for (PotionEffect effect : player.getActivePotionEffects()) {
			/*
			 * Formulae for this:
			 * http://www.minecraftforum.net/forums/minecraft-discussion/
			 * survival-mode/2577601-facts-fall-damage
			 */
			if (effect.getType() == PotionEffectType.JUMP) {
				jump = effect.getAmplifier();
			} else if (effect.getType() == PotionEffectType.DAMAGE_RESISTANCE) {
				resistance = effect.getAmplifier();
			}
		}

		int enchantLevel = 0;
		int typeModifier = 0;

		if (player.getInventory().getBoots() != null) {
			// EPF data from: http://minecraft.gamepedia.com/Armor#Enchantments
			for (Map.Entry<Enchantment, Integer> entry : player.getInventory().getBoots().getEnchantments()
					.entrySet()) {
				if (entry.getKey() == Enchantment.PROTECTION_ENVIRONMENTAL) {
					enchantLevel = entry.getValue();
					typeModifier = 1;
				} else if (entry.getKey() == Enchantment.PROTECTION_FALL) {
					enchantLevel = entry.getValue();
					typeModifier = 3;
				}
			}
		}

		/*
		 * Formula for fall damage from:
		 * http://www.minecraftforum.net/forums/minecraft-discussion/survival-
		 * mode/2577601-facts-fall-damage
		 */

		int c = 0;
		if (fallDistance >= 29) {
			c = 12;
		} else if (fallDistance >= 10) {
			c = 14;
		}

		double damage = 0.0;

		damage = (seconds - c + (profile.getBehaviour().isJumping() ? 1.5 : 0) - 3 - jump);

		if (resistance != 0) {
			damage *= (1 - resistance * 0.2);
		}

		if (enchantLevel != 0) {
			damage *= (100 - (6 + enchantLevel * enchantLevel) * typeModifier / 3 * 4);
		}

		return damage;
	}

}
