package io.github.awesome90.crescent.detection.checks;

import java.util.ArrayList;

import io.github.awesome90.crescent.detection.CheckType;
import io.github.awesome90.crescent.detection.Detection;

public abstract class Check {

	protected final CheckType type;
	protected ArrayList<Detection> previous;

	/**
	 * @param type
	 *            The type of cheat that this instance of Check attempts to
	 *            detect.
	 */
	public Check(CheckType type) {
		this.type = type;
		this.previous = new ArrayList<Detection>();
	}

	/**
	 * @param detection
	 *            The Detection object that should be added to the player's
	 *            detection list for this specific check.
	 */
	public void addDetection(Detection detection) {
		previous.add(detection);
	}

	public CheckType getType() {
		return type;
	}

}
