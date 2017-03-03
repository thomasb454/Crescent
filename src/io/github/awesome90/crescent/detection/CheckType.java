package io.github.awesome90.crescent.detection;

import org.bukkit.configuration.file.FileConfiguration;

import io.github.awesome90.crescent.Crescent;

public enum CheckType {
	WATERWALK("WaterWalk", 10), NOFALL("NoFall", 10), ANTIKNOCKBACK("Antiknockback", 10), SNEAK("Sneak", 10), FLY("Fly",
			10), FASTBOW("Fastbow", 10), SPEED("Speed", 10), KILLAURA("Killaura", 10), FASTHEAL("FastHeal",
					10), HIGHJUMP("HighJump", 10), CRITICALS("Criticals", 10), TIMER("Timer", 10);

	/**
	 * The user-friendly name of the check.
	 */
	private final String name;
	/**
	 * The suggested, default, value for cheatConsider;
	 */
	private final int normalCheatConsider;
	/**
	 * The number of checks that need to be set off for the player to fully be
	 * considered cheating.
	 */
	private final int cheatConsider;

	/**
	 * Whether the player should be prevented from performing an action (e.g.
	 * teleported back).
	 */
	private final boolean prevent;

	private CheckType(String name, int normalCheatConsider) {
		this.name = name;
		this.normalCheatConsider = normalCheatConsider;

		final FileConfiguration fc = Crescent.getInstance().getConfig();

		this.cheatConsider = fc.getInt(name + ".cheatConsider");
		this.prevent = fc.getBoolean(name + ".prevent");
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

	public boolean shouldPrevent() {
		return prevent;
	}
}
