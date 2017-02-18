package io.github.awesome90.crescent.detection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.info.Profile;

public class Detection {

	private final Profile profile;
	private final Check check;
	private final String checkVersion;
	private final double certainty;
	private final long time;

	/**
	 * @param profile
	 *            The player's profile instance.
	 * @param type
	 *            The type of cheat the player has used to set of the detection
	 */
	public Detection(Profile profile, Check check, String checkVersion, double certainty) {
		this.profile = profile;
		this.check = check;
		// The time that the detection was triggered.
		this.time = System.currentTimeMillis();
		this.checkVersion = checkVersion;
		this.certainty = certainty;
	}

	public void alert() {
		Bukkit.broadcastMessage(
				ChatColor.RED + profile.getPlayer().getName() + " failed the " + check.getType().getName().toLowerCase()
						+ checkVersion + " check. (" + check.getCertainty() + "% ping: " + profile.getPing() + ")");
	}

	public Check getCheck() {
		return check;
	}

	public String getCheckVersion() {
		return checkVersion;
	}

	/**
	 * @return The certainty calculated from this detection alone - ignores
	 *         other detections.
	 */
	public double getCertainty() {
		return certainty;
	}

	/**
	 * @return The time that the detection happened in Unix time * 1000 (time
	 *         since January 1st 1970)
	 */
	public long getTime() {
		return time;
	}

}
