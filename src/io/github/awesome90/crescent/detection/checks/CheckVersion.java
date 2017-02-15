package io.github.awesome90.crescent.detection.checks;

import java.util.ArrayList;

import io.github.awesome90.crescent.detection.CheckType;
import io.github.awesome90.crescent.detection.Detection;
import io.github.awesome90.crescent.info.Profile;

public abstract class CheckVersion implements CheckApproach {

	/**
	 * The category of check that
	 */
	protected final Check check;
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

	/**
	 * The total number of times that this particular CheckVersion has been
	 * called.
	 */
	protected long totalCalls;

	/**
	 * The total number of times that this CheckVersion has returned with belief
	 * that the player could be cheating.
	 */
	protected long improperCalls;

	public CheckVersion(Check check, String checkVersion) {
		this.check = check;
		this.profile = check.getProfile();
		this.type = check.getType();
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
	public void callback(boolean suspicious) {
		if (suspicious) {
			final Detection detection = new Detection(profile, type, checkVersion, checkCurrentCertainty());
			// Add the detection to the list for this CheckType.
			previous.add(detection);

			improperCalls++;
		}

		totalCalls++;
	}

	/**
	 * @return The overall certainty of a player cheating (taking into account
	 *         previous checks).
	 */
	public double calculateOverallCertainty() {
		return (((improperCalls / totalCalls) * 100.0) / type.getCheatConsider()) * 100.0;
	}

	public CheckType getCheckType() {
		return type;
	}

	public String getCheckVersion() {
		return checkVersion;
	}

}
