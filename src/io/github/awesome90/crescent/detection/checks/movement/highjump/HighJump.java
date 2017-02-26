package io.github.awesome90.crescent.detection.checks.movement.highjump;

import io.github.awesome90.crescent.detection.CheckType;
import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.info.Profile;

public class HighJump extends Check {

	public HighJump(Profile profile) {
		super(profile, CheckType.HIGHJUMP);

		versions.add(new HighJumpA(this));
	}

}
