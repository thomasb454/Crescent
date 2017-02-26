package io.github.awesome90.crescent.detection.checks;

import org.bukkit.event.Event;

import com.comphenix.protocol.events.PacketContainer;

public interface CheckApproach {
	/**
	 * Called to check for cheating (through a Bukkit listener).
	 * 
	 * @param event
	 *            The event the check will need to analyse.
	 */
	void call(Event event);

	/**
	 * Called to check for cheating (through packets).
	 * 
	 * @param packet
	 *            The packet that the check will need to analyse.
	 */
	void call(PacketContainer packet);

	/**
	 * Each CheckVersion handles this method the way that they need to.
	 * 
	 * @return The certainty at the current moment (of a particular check
	 *         cycle).
	 */
	double checkCurrentCertainty();
}
