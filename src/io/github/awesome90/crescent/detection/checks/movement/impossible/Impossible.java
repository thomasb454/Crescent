package io.github.awesome90.crescent.detection.checks.movement.impossible;

import io.github.awesome90.crescent.detection.CheckType;
import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.info.Profile;

public class Impossible extends Check {

	public Impossible(Profile profile) {
		super(profile, CheckType.SNEAK);

		versions.add(new ImpossibleA(this));
	}

}
