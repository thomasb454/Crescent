package io.github.awesome90.crescent;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.awesome90.crescent.detection.CheckType;
import io.github.awesome90.crescent.listeners.DetectionListener;

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

		registerListeners(new DetectionListener());
	}

	private void registerListeners(Listener... listeners) {
		final PluginManager pm = Bukkit.getPluginManager();
		for (Listener listener : listeners) {
			pm.registerEvents(listener, this);
		}
	}

	/**
	 * Set the configuration defaults.
	 */
	private void loadConfig() {

		for (CheckType type : CheckType.values()) {
			this.getConfig().set(type.getName().toLowerCase() + ".cheatConsider", type.getNormalCheatConsider());
		}

		// Fly check values.
		this.getConfig().set("fly.a.ascendFlyCertain", 1500);
		this.getConfig().set("fly.b.countInAir", 20);
		this.getConfig().set("fly.b.flyCertain", 1500);

		this.getConfig().set("waterwalk.a.walkTime", 1000);

		this.getConfig().set("fastbow.a.allowedMinimumDifference", 250);

		this.getConfig().set("speed.a.distanceCheck", 10);
		this.getConfig().set("speed.a.compareDifference", 200);

		// Antiknockback check A default values.
		this.getConfig().set("antiknockback.a.nonSprintNormal", "2.1-3.1");
		this.getConfig().set("antiknockback.a.sprintNormal", "4.9-6.8");
		this.getConfig().set("antiknockback.a.nonSprintKnockbackI", "4.9-6.8");
		this.getConfig().set("antiknockback.a.nonSprintKnockbackII", "5.0-11.0");
		this.getConfig().set("antiknockback.a.sprintKnockbackI", "5.0-11.0");
		this.getConfig().set("antiknockback.a.sprintKnockbackII", "9.5-15.0");

		this.getConfig().set("antiknockback.a.bow", 2);
		this.getConfig().set("antiknockback.a.bowPunchI", 2);
		this.getConfig().set("antiknockback.a.bowPunchII", 2);

		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
	}

}
