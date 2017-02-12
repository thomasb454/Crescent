package me.awesome90.gladiator.detection;

import java.util.ArrayList;

public abstract class Check {

	protected final CheckType type;
	protected ArrayList<Detection> previous;

	/**
	 * @param type
	 *            The type of cheat that this instance of Check attempts to
	 *            detect.
	 */
	public Check(CheckType type) {
		this.type = type;
		this.previous = new ArrayList<Detection>();
	}

}
