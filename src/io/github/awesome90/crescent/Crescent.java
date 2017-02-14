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
			this.getConfig().set("cheatConsider." + type.getName(), type.getNormalCheatConsider());
		}

		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
	}

}
