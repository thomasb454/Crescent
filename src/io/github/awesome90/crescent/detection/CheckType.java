package io.github.awesome90.crescent.detection;

import io.github.awesome90.crescent.Crescent;

public enum CheckType {
	WATERWALK("WaterWalk", 10), NOFALL("NoFall", 10), ANTIKNOCKBACK("Antiknockback", 10), SNEAK("Sneak", 10), FLY("Fly",
			10), FASTBOW("Fastbow", 10), SPEED("Speed", 10), KILLAURA("Killaura", 10), FASTHEAL("FastHeal",
					10), HIGHJUMP("HighJump", 10), CRITICALS("Criticals", 10), TIMER("Timer", 10);

	/**
	 * The user-friendly name of the check.
	 */
	private String name;
	/**
	 * The suggested, default, value for cheatConsider;
	 */
	private int normalCheatConsider;
	/**
	 * The number of checks that need to be set off for the player to fully be
	 * considered cheating.
	 */
	private int cheatConsider;

	private CheckType(String name, int normalCheatConsider) {
		this.name = name;
		this.normalCheatConsider = normalCheatConsider;
		this.cheatConsider = getCheatConsiderPoints(name);
	}

	public String getName() {
		return name;
	}

	public int getNormalCheatConsider() {
		return normalCheatConsider;
	}

	public int getCheatConsider() {
		return cheatConsider;
	}

	/**
	 * @param name
	 *            The name of the check that you want to retrieve the
	 *            information from.
	 * @return The amount of points needed to fully consider a player cheating
	 *         for a particular CheckType.
	 */
	private static int getCheatConsiderPoints(String name) {
		return Crescent.getInstance().getConfig().getInt(name.toLowerCase() + ".cheatConsider");
	}
}
