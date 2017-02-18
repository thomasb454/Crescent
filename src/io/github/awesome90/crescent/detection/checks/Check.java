package io.github.awesome90.crescent.detection.checks;

import java.util.ArrayList;

import org.bukkit.event.Event;

import io.github.awesome90.crescent.detection.CheckType;
import io.github.awesome90.crescent.info.Profile;

public abstract class Check {

	protected final Profile profile;
	protected final CheckType type;
	protected ArrayList<CheckVersion> versions;

	/**
	 * @param type
	 *            The type of cheat that this instance of Check attempts to
	 *            detect.
	 */
	public Check(Profile profile, CheckType type) {
		this.profile = profile;
		this.type = type;
		this.versions = new ArrayList<CheckVersion>();
	}

	public CheckVersion getCheckVersion(String version) {
		for (CheckVersion checkVersion : versions) {
			if (checkVersion.getCheckVersion().equalsIgnoreCase(version)) {
				return checkVersion;
			}
		}

		return null;
	}

	/**
	 * @param event
	 *            The event called that is passed to each CheckVersion to
	 *            analyse.
	 */
	public void call(Event event) {
		for (CheckVersion version : versions) {
			version.call(event);
		}
	}

	/**
	 * 
	 * @return The certainty of this check as a whole.
	 */
	public final double getCertainty() {
		double total = 0.0;
		for (CheckVersion version : versions) {
			total += version.calculateOverallCertainty();
		}

		return (total / versions.size()) * 100.0;
	}

	/**
	 * @return The profile.
	 */
	public final Profile getProfile() {
		return profile;
	}

	/**
	 * Add a specific version of a cheat to check for.
	 * 
	 * @param version
	 *            The CheckVersion you would like to add.
	 */
	public void addVersion(CheckVersion version) {
		versions.add(version);
	}

	public final CheckType getType() {
		return type;
	}

}
