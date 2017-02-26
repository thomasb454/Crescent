package io.github.awesome90.crescent.detection.checks.movement.highjump;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.comphenix.protocol.events.PacketContainer;

import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;
import io.github.awesome90.crescent.events.PlayerJumpEvent;

public class HighJumpA extends CheckVersion {

	public HighJumpA(Check check) {
		super(check, "A", "Checks if the player jumps higher than the allowed jump height.");
	}

	@Override
	public void call(Event event) {
		if (event instanceof PlayerJumpEvent) {
			PlayerJumpEvent pje = (PlayerJumpEvent) event;

			final double distance = pje.getDistance();

			/*
			 * Before declaring the player as suspicious, check if they have a
			 * jump boost potion effect.
			 */

			final Player player = profile.getPlayer();

			int jumpLevel = 0;

			for (PotionEffect effect : player.getActivePotionEffects()) {
				if (effect.equals(PotionEffectType.JUMP)) {
					jumpLevel = effect.getAmplifier();
					break;
				}
			}

			double allowedExtra = 0.0;

			switch (jumpLevel) {
			case 1:
				/*
				 * https://www.reddit.com/r/minecraftsuggestions/comments/
				 * 3b0nfq/potion_of_jumping_not_strong_enough/
				 */
				allowedExtra += 0.25;
				break;
			case 2:
				/*
				 * Source:
				 * http://minecraft.gamepedia.com/Potion#Potion_of_Leaping
				 */
				allowedExtra += 0.75;
				break;
			}

			if (!isAllowed(distance, allowedExtra)) {
				callback(true);
			}
		}
	}

	/**
	 * @param distance
	 *            The distance that the player has jumped.
	 * @param extra
	 *            The allowed extra distance that a player is allowed to jump
	 *            (with jump boost etc.).
	 * @return Whether the player has jumped too high.
	 */
	private boolean isAllowed(double distance, double extra) {
		/*
		 * Normal jump height is about 1.25 blocks. Source:
		 * https://books.google.co.uk/books?id=tO3gBwAAQBAJ&printsec=
		 * frontcover#v=onepage&q&f=false
		 */

		return distance - extra > 1.25;
	}

	@Override
	public void call(PacketContainer packet) {

	}

	@Override
	public double checkCurrentCertainty() {
		return 0;
	}

}
