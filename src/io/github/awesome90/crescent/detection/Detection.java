package io.github.awesome90.crescent.detection;

import io.github.awesome90.crescent.info.Profile;

public class Detection {

	private final Profile profile;
	private final CheckType type;
	private final String checkVersion;
	private final double certainty;
	private final long time;

	/**
	 * @param profile
	 *            The player's profile instance.
	 * @param type
	 *            The type of cheat the player has used to set of the detection
	 */
	public Detection(Profile profile, CheckType type, String checkVersion, double certainty) {
		this.profile = profile;
		this.type = type;
		// The time that the detection was triggered.
		this.time = System.currentTimeMillis();
		this.checkVersion = checkVersion;
		this.certainty = certainty;
	}

	public CheckType getCheckType() {
		return type;
	}

	public String getCheckVersion() {
		return checkVersion;
	}

	/**
	 * @return The certainty calculated from this detection alone - ignores
	 *         other detections.
	 */
	public double getCertainty() {
		// This should return the magnitude to which the info of this detection
		// is similar to the info of previous known cheat info.
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
