package io.github.awesome90.crescent;

import org.bukkit.plugin.java.JavaPlugin;

public class Gladiator extends JavaPlugin {

	private static Gladiator instance;

	/**
	 * @return The instance of the plugin.
	 */
	public static Gladiator getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;
	}

}
