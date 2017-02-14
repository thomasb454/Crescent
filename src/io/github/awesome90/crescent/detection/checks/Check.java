package io.github.awesome90.crescent.detection.checks;

import java.util.ArrayList;

import io.github.awesome90.crescent.detection.CheckType;

public abstract class Check {

	protected final CheckType type;
	protected ArrayList<CheckVersion> versions;

	/**
	 * @param type
	 *            The type of cheat that this instance of Check attempts to
	 *            detect.
	 */
	public Check(CheckType type) {
		this.type = type;
		this.versions = new ArrayList<CheckVersion>();
	}

	public final double getCertainty() {
		double total = 0.0;
		for (CheckVersion version : versions) {
			total += version.calculateOverallCertainty();
		}

		return (total / versions.size()) * 100.0;
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
