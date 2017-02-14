package io.github.awesome90.crescent.detection.checks;

import java.util.ArrayList;

import io.github.awesome90.crescent.detection.CheckType;
import io.github.awesome90.crescent.detection.Detection;
import io.github.awesome90.crescent.info.Profile;

public abstract class CheckVersion implements CheckApproach {

	/**
	 * The profile of the player.
	 */
	protected final Profile profile;
	/**
	 * The CheckType of this check version (the overall bracket of what this
	 * check covers).
	 */
	protected final CheckType type;
	/**
	 * The version (the name given to) this specific type of check.
	 */
	protected final String checkVersion;

	/**
	 * Previous suspicious activity triggered by the player for this particular
	 * check.
	 */
	protected ArrayList<Detection> previous;

	public CheckVersion(Profile profile, CheckType type, String checkVersion) {
		this.profile = profile;
		this.type = type;
		this.checkVersion = checkVersion;
		this.previous = new ArrayList<Detection>();
	}

	/**
	 * This will be called when a CheckVersion finishes executing the check
	 * method. The callback method (this method) will handle this.
	 * 
	 * @param suspicious
	 *            Whether the player showed suspicious behaviour or not.
	 * @param certainty
	 *            The calculated percentage change of the player cheating.
	 * @param info
	 *            Any additional information to be sent to users.
	 */
	public void callback(boolean suspicious, double certainty, String info) {
		if (suspicious) {
			Detection detection = new Detection(profile, type, checkVersion, certainty);
			// Add the detection to the list for this CheckType.
			detection.add();
		}
	}

	public CheckType getCheckType() {
		return type;
	}

	public String getCheckVersion() {
		return checkVersion;
	}

}
