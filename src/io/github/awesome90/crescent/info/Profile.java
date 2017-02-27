package io.github.awesome90.crescent.info;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import io.github.awesome90.crescent.behaviour.Behaviour;
import io.github.awesome90.crescent.detection.CheckType;
import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.damage.antiknockback.Antiknockback;
import io.github.awesome90.crescent.detection.checks.damage.criticals.Criticals;
import io.github.awesome90.crescent.detection.checks.damage.killaura.Killaura;
import io.github.awesome90.crescent.detection.checks.damage.nofall.NoFall;
import io.github.awesome90.crescent.detection.checks.health.fastheal.FastHeal;
import io.github.awesome90.crescent.detection.checks.interact.fastbow.Fastbow;
import io.github.awesome90.crescent.detection.checks.movement.fly.Fly;
import io.github.awesome90.crescent.detection.checks.movement.highjump.HighJump;
import io.github.awesome90.crescent.detection.checks.movement.impossible.Impossible;
import io.github.awesome90.crescent.detection.checks.movement.speed.Speed;
import io.github.awesome90.crescent.detection.checks.movement.timer.Timer;
import io.github.awesome90.crescent.detection.checks.movement.waterwalk.WaterWalk;
import net.minecraft.server.v1_8_R3.EntityPlayer;

public class Profile {

	private static ArrayList<Profile> profiles = new ArrayList<Profile>();

	public static Profile getProfile(UUID uuid) {
		for (Profile profile : profiles) {
			if (profile.getUUID() == uuid) {
				return profile;
			}
		}

		// The player does not have a profile, create a new one for them.

		Profile profile = new Profile(uuid);
		profiles.add(profile);

		return profile;
	}

	private final UUID uuid;
	private final Behaviour behaviour;
	private ArrayList<Check> checks;

	public Profile(UUID uuid) {
		this.uuid = uuid;
		this.behaviour = new Behaviour(this);
		// Create an ArrayList containing all the checks. This should only be
		// able to hold the maximum size of checks.
		this.checks = new ArrayList<Check>(CheckType.values().length);

		addChecks();
	}

	/**
	 * Add the cheat checks so that they can be utilised.
	 */
	private void addChecks() {
		checks.add(new Antiknockback(this));
		checks.add(new NoFall(this));
		checks.add(new Fastbow(this));
		checks.add(new Fly(this));
		checks.add(new Impossible(this));
		checks.add(new Speed(this));
		checks.add(new WaterWalk(this));
		checks.add(new Killaura(this));
		checks.add(new FastHeal(this));
		checks.add(new HighJump(this));
		checks.add(new Criticals(this));
		checks.add(new Timer(this));
	}

	public UUID getUUID() {
		return uuid;
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

	public final int getPing() {
		final CraftPlayer cp = (CraftPlayer) getPlayer();
		final EntityPlayer ep = (EntityPlayer) cp.getHandle();
		return ep.ping;
	}

}
