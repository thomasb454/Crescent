package io.github.awesome90.crescent;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.awesome90.crescent.detection.CheckType;

public class Crescent extends JavaPlugin {

	private static Crescent instance;

	/**
	 * @return The instance of the plugin.
	 */
	public static Crescent getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;

		// Configuration.
		loadConfig();
	}

	/**
	 * Set the configuration defaults.
	 */
	private void loadConfig() {

		for (CheckType type : CheckType.values()) {
			this.getConfig().set(type.getName() + ".cheatConsider", type.getNormalCheatConsider());
		}

		// Fly check values.
		this.getConfig().set("fly.a.ascendFlyCertain", 1500);
		this.getConfig().set("fly.b.countInAir", 20);
		this.getConfig().set("fly.b.flyCertain", 1500);

		this.getConfig().set("waterwalk.a.walkTime", 1000);

		this.getConfig().set("fastbow.a.allowedMinimumDifference", 600);

		this.getConfig().set("speed.a.distanceCheck", 10);
		this.getConfig().set("speed.a.compareDifference", 200);

		// Antiknockback check A default values.
		this.getConfig().set("antiknockback.nonSprintNormal", "2.1-3.1");
		this.getConfig().set("antiknockback.sprintNormal", "4.9-6.8");
		this.getConfig().set("antiknockback.nonSprintKnockbackI", "4.9-6.8");
		this.getConfig().set("antiknockback.nonSprintKnockbackII", "5.0-11.0");
		this.getConfig().set("antiknockback.sprintKnockbackI", "5.0-11.0");
		this.getConfig().set("antiknockback.sprintKnockbackII", "9.5-15.0");

		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
	}

}
