package io.github.awesome90.crescent.detection.checks.damage.antiknockback;

import io.github.awesome90.crescent.detection.CheckType;
import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.info.Profile;

public class Antiknockback extends Check {

	public Antiknockback(Profile profile) {
		super(profile, CheckType.ANTIKNOCKBACK);

		versions.add(new AntiknockbackA(this));
	}

}
