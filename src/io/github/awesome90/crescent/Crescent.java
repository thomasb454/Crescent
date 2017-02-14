package io.github.awesome90.crescent;

import org.bukkit.plugin.java.JavaPlugin;

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
	}

}
