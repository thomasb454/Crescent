package io.github.awesome90.crescent.detection.checks.movement.speed;

import io.github.awesome90.crescent.detection.CheckType;
import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.info.Profile;

public class Speed extends Check {

	public Speed(Profile profile) {
		super(profile, CheckType.SPEED);

		versions.add(new SpeedA(this));
	}

}
