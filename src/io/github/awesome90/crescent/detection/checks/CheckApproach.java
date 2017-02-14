package io.github.awesome90.crescent.detection.checks;

public interface CheckApproach {

	/**
	 * This method executes the check which evaluates whether a player is
	 * cheating for a particular CheckVersion.
	 */
	void check();

	/**
	 * Each CheckVersion handles this method the way that they need to.
	 * 
	 * @return The certainty at the current moment (of a particular check
	 *         cycle).
	 */
	double checkCurrentCertainty();

}
