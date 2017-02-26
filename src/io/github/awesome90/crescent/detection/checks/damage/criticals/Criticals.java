package io.github.awesome90.crescent.detection.checks.damage.criticals;

import io.github.awesome90.crescent.detection.CheckType;
import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.info.Profile;

public class Criticals extends Check {

	public Criticals(Profile profile) {
		super(profile, CheckType.CRITICALS);

		versions.add(new CriticalsA(this));
	}

}
