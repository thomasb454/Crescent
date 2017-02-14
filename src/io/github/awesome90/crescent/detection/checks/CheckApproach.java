package io.github.awesome90.crescent.detection.checks;

import org.bukkit.event.Event;

public interface CheckApproach {

	/**
	 * Called when a specific event is triggered. Generally, this should then
	 * pass on duties (with the required data) to the check method.
	 * 
	 * @param event
	 *            The event that has been called.
	 */
	void call(Event event);

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
