package me.awesome90.gladiator.research;

import java.util.ArrayList;
import java.util.HashMap;

import me.awesome90.gladiator.detection.CheckType;
import me.awesome90.gladiator.detection.Detection;

public class InformationGatherer {

	private HashMap<CheckType, ArrayList<Detection>> previousChecks;

	public InformationGatherer() {
		this.previousChecks = new HashMap<CheckType, ArrayList<Detection>>();
	}

	// Check to see if a detection has similar data to a previously made
	// detection.
	public Detection getMostPerfectMatch(Detection detection) {
		final CheckType checkType = detection.getCheckType();

		ArrayList<Detection> detections = new ArrayList<Detection>();

		for (CheckType type : previousChecks.keySet()) {
			if (checkType == type) {
				
			}
		}
	}

}
