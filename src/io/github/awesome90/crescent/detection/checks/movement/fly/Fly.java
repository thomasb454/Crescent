package io.github.awesome90.crescent.detection.checks.movement.fly;

import io.github.awesome90.crescent.detection.CheckType;
import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.info.Profile;

public class Fly extends Check {

	public Fly(Profile profile) {
		super(profile, CheckType.FLY);

		versions.add(new FlyA(this));
		versions.add(new FlyB(this));
	}

}
