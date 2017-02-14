package io.github.awesome90.crescent.info;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import io.github.awesome90.crescent.behaviour.Behaviour;
import io.github.awesome90.crescent.detection.CheckType;
import io.github.awesome90.crescent.detection.checks.Check;

public class Profile {

	private final UUID uuid;
	private final Behaviour behaviour;
	private ArrayList<Check> checks;

	public Profile(UUID uuid) {
		this.uuid = uuid;
		this.behaviour = new Behaviour(this);
		// Create an ArrayList containing all the checks. This should only be
		// able to hold the maximum size of checks.
		this.checks = new ArrayList<Check>(CheckType.values().length);
	}

	public Behaviour getBehaviour() {
		return behaviour;
	}

	/**
	 * @param type
	 *            The CheckType that one would like to get the Check object of.
	 * @return The Check object associated with this particular CheckType. If
	 *         this cannot be found, then null will be returned.
	 */
	public Check getCheck(CheckType type) {
		for (Check check : checks) {
			if (check.getType() == type) {
				return check;
			}
		}

		return null;
	}

	public final boolean isOnline() {
		return getPlayer() != null;
	}

	public final Player getPlayer() {
		return Bukkit.getPlayer(uuid);
	}

}
