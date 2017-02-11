package me.awesome90.gladiator.detection;

import me.awesome90.gladiator.info.Profile;

public class Detection {

	private final Profile profile;
	private final CheckType type;
	private final long time;

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

	// Gets the certainty from this Detection alone - ignores all other recent
	// detections.
	public double getIsolatedCertainty() {

	}

}
