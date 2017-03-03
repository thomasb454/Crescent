package io.github.awesome90.crescent;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

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

	private ProtocolManager protocolManager;

	@Override
	public void onEnable() {
		instance = this;

		protocolManager = ProtocolLibrary.getProtocolManager();

		// Configuration.
		loadConfig();

		registerListeners();
	}

	private void registerListeners() {
		final DetectionListener detections = new DetectionListener();

		final PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(detections, this);
	}

	/**
	 * Set the configuration defaults.
	 */
	private void loadConfig() {

		for (CheckType type : CheckType.values()) {
			final String name = type.getName().toLowerCase();
			this.getConfig().set(name + ".cheatConsider", type.getNormalCheatConsider());
			this.getConfig().set(name + ".prevent", true);
		}

		// Fly check values.
		this.getConfig().set("fly.a.ascendFlyCertain", 1500);
		this.getConfig().set("fly.b.countInAir", 20);
		this.getConfig().set("fly.b.flyCertain", 1500);

		this.getConfig().set("waterwalk.a.walkTime", 1000);

		this.getConfig().set("fastbow.a.allowedMinimumDifference", 250);

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

	public ProtocolManager getProtocolManager() {
		return protocolManager;
	}

}
