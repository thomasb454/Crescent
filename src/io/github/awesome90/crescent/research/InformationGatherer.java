package io.github.awesome90.crescent.research;

import java.util.ArrayList;
import java.util.HashMap;

import io.github.awesome90.crescent.detection.CheckType;
import io.github.awesome90.crescent.detection.Detection;

public class InformationGatherer {

	private HashMap<CheckType, ArrayList<Detection>> previousChecks;

	public InformationGatherer() {
		this.previousChecks = new HashMap<CheckType, ArrayList<Detection>>();
	}

	// Check to see if a detection has similar data to a previously made
	// detection.
	/**
	 * @param detection
	 *            The detection that the player has triggered.
	 * @return A previous detection most like the one that has been triggered.
	 */
	public Detection getMostPerfectMatch(Detection detection) {
		final CheckType checkType = detection.getCheckType();

		ArrayList<Detection> detections = new ArrayList<Detection>();

		for (CheckType type : previousChecks.keySet()) {
			if (checkType == type) {

			}
		}
	}

}
