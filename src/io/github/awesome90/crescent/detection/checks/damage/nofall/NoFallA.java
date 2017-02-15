package io.github.awesome90.crescent.detection.checks.damage.nofall;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import io.github.awesome90.crescent.Crescent;
import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

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
		super(check, "A");
		this.totalDamage = totalDisplacedHealth = 0.0;
	}

	@Override
	public void call(Event event) {
		if (event instanceof EntityDamageEvent) {
			final EntityDamageEvent ede = (EntityDamageEvent) event;

			if (ede.getCause() == DamageCause.FALL) {
				final Player player = profile.getPlayer();
				final double original = player.getHealth();

				final double result = ede.getFinalDamage();

				final double calculatedHealth = player.getHealth() - result;

				Bukkit.getScheduler().runTaskLater(Crescent.getInstance(), new Runnable() {

					@Override
					public void run() {
						double sensibleHealth = calculatedHealth;
						if (sensibleHealth < 0) {
							sensibleHealth = 0;
						}

						final double current = player.getHealth();
						if (current < sensibleHealth) {
							final double difference = sensibleHealth - (original - current);

							totalDisplacedHealth += difference;

							callback(true, "Player took " + difference + " damage instead of expected " + sensibleHealth
									+ " damage.");

						}
					}
				}, 2L);

				totalDamage += result;
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

}
