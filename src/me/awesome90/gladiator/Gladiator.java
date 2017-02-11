package me.awesome90.gladiator;

import org.bukkit.plugin.java.JavaPlugin;

public class Gladiator extends JavaPlugin {

	private static Gladiator instance;

	// Return instance of the plugin.
	public static Gladiator getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;
	}

}
