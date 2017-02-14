package io.github.awesome90.crescent.detection;

import io.github.awesome90.crescent.info.Profile;

public class Detection {

	private final Profile profile;
	private final CheckType type;
	private final long time;

	/**
	 * @param profile
	 *            The player's profile instance.
	 * @param type
	 *            The type of cheat the player has used to set of the detection
	 */
	public Detection(Profile profile, CheckType type) {
		this.profile = profile;
		this.type = type;
		// The time that the detection was triggered.
		this.time = System.currentTimeMillis();

		// Add the detection to the profile's list.
		this.profile.addDetection(this);
	}

	public CheckType getCheckType() {
		return type;
	}

	/**
	 * @return The certainty calculated from this detection alone - ignores
	 *         other detections.
	 */
	public double getIsolatedCertainty() {
		// This should return the magnitude to which the info of this detection
		// is similar to the info of previous known cheat info.
	}

}
