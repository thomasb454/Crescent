package me.awesome90.gladiator.detection;

import java.util.ArrayList;

public abstract class Check {

	protected final CheckType type;
	protected ArrayList<Detection> previous;

	public Check(CheckType type) {
		this.type = type;
		this.previous = new ArrayList<Detection>();
	}

}
