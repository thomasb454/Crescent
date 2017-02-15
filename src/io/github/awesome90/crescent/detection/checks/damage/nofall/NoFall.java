package io.github.awesome90.crescent.detection.checks.damage.nofall;

import io.github.awesome90.crescent.detection.CheckType;
import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.info.Profile;

public class NoFall extends Check {

	public NoFall(Profile profile) {
		super(profile, CheckType.NOFALL);
	}

}
