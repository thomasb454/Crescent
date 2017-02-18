package io.github.awesome90.crescent.detection.checks.damage.antiknockback;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import io.github.awesome90.crescent.Crescent;
import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class AntiknockbackA extends CheckVersion {

	/**
	 * The variables that keep count of whether a player's knockback has
	 * returned to be too small, big or normal.
	 */
	private long tooSmall, normal, tooBig;

	public AntiknockbackA(Check check) {
		super(check, "A",
				"Compares how the player's position compares to average position values after specific types of hits (for normal and with swords).");
		this.tooSmall = normal = tooBig = 1;
	}

	@Override
	public void call(Event event) {
		final Player player = profile.getPlayer();
		final Location original = player.getLocation();

		if (event instanceof EntityDamageEvent) {
			final EntityDamageEvent ede = (EntityDamageEvent) event;

			/*
			 * The item the damager entity has in their hand. I know this might
			 * not be a sword, but I've called it that anyway!
			 */
			ItemStack damagerSword = null;
			Boolean damagerSprinting = null;

			if (ede instanceof EntityDamageByEntityEvent) {
				// Sorry for the messy casting! :D
				EntityDamageByEntityEvent edbye = (EntityDamageByEntityEvent) ede;

				if (edbye.getDamager() instanceof LivingEntity) {
					LivingEntity entity = (LivingEntity) edbye.getDamager();
					damagerSword = entity.getEquipment().getItemInHand();

					if (entity instanceof Player) {
						damagerSprinting = player.isSprinting();
					}
				}
			}

			if (damagerSword == null) {
				damagerSword = new ItemStack(Material.WOOD_SWORD);
			}

			if (damagerSprinting == null) {
				damagerSprinting = false;
			}

			// So these can be used in the runnable.
			final boolean sprinting = damagerSprinting;
			final ItemStack sword = damagerSword;

			final DamageCause cause = ede.getCause();

			if (cause == DamageCause.ENTITY_ATTACK) {

				Bukkit.getScheduler().runTaskLater(Crescent.getInstance(), new Runnable() {

					@Override
					public void run() {
						// Check if the player's x, y and z coordinates have
						// changed (and by how much).
						final Location current = player.getLocation();

						final double diffX = Math.abs(current.getX() - original.getX()),
								diffZ = Math.abs(current.getZ() - original.getZ());

						checkWithRelevantNumbers(sprinting, sword, diffX + diffZ);
					}
				}, 20L);
			}
		} else if (event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent edbye = (EntityDamageByEntityEvent) event;

			if (edbye.getDamager() instanceof Arrow) {
				Arrow arrow = (Arrow) edbye.getDamager();

				int punch = arrow.getKnockbackStrength();

				Bukkit.getScheduler().runTaskLater(Crescent.getInstance(), new Runnable() {

					@Override
					public void run() {
						final Location current = player.getLocation();

						final double diffX = Math.abs(current.getX() - original.getX()),
								diffZ = Math.abs(current.getZ() - original.getZ());
					}
				}, 5L);
			}

		}
	}

	@Override
	public void check() {

	}

	private void checkWithRelevantNumbers(boolean damagerSprinting, ItemStack damagerSword, double totalXZ) {
		String key = "antiknockback.a." + (damagerSprinting ? "sprint" : "nonSprint");

		int knockbackLevel = getKnockbackLevel(damagerSword);

		if (knockbackLevel != 0) {
			key += "Knockback";
			if (knockbackLevel == 1) {
				key += "I";
			} else if (knockbackLevel == 2) {
				// The knockback level must be 2.
				key += "II";
			} else {
				return;
			}
		} else {
			key += "Normal";
		}

		double[] values = readValues(key);

		/*
		 * Check the total of the x and z values and compare them with the read
		 * values.
		 */

		Bukkit.broadcastMessage(totalXZ + "");
		if (totalXZ < values[0]) {
			// Player is taking a very little amount of knockback.
			tooSmall++;
		} else {
			if (totalXZ >= values[0] && totalXZ <= values[1]) {
				// Player is taking a normal amount of knockback.
				normal++;
			} else {
				// Player is taking a large amount of knockback.
				tooBig++;
			}

			// The player is not suspicious.
			callback(false);
			return;
		}

		Bukkit.broadcastMessage(checkCurrentCertainty() + "");
		if (checkCurrentCertainty() > 45 && super.totalCalls >= 10) {
			callback(true);
		}
	}

	private int getKnockbackLevel(ItemStack item) {
		if (item.containsEnchantment(Enchantment.KNOCKBACK)) {
			return item.getEnchantmentLevel(Enchantment.KNOCKBACK);
		} else {
			return 0;
		}
	}

	private double[] readValues(String valueString) {
		double[] values = new double[2];
		String[] valueStrings = Crescent.getInstance().getConfig().getString(valueString).split("-");

		for (int i = 0; i < valueStrings.length; i++) {
			Bukkit.broadcastMessage(valueStrings[i]);
			values[i] = Double.valueOf(valueStrings[i]);
		}

		return values;
	}

	@Override
	public double checkCurrentCertainty() {
		return (tooSmall / (normal + tooBig)) * 100.0;
	}

}
