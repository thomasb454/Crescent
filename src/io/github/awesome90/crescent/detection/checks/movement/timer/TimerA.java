package io.github.awesome90.crescent.detection.checks.movement.timer;

import org.bukkit.event.Event;
import org.bukkit.util.Vector;

import com.comphenix.protocol.events.PacketContainer;

import io.github.awesome90.crescent.detection.checks.Check;
import io.github.awesome90.crescent.detection.checks.CheckVersion;

public class TimerA extends CheckVersion {

	private long lastTime;
	private double lastX, lastY, lastZ;

	public TimerA(Check check) {
		super(check, "A", "Checks if a player is sending more packets to the server than allowed.");
		this.lastTime = -1;
		this.lastX = lastY = lastZ = -1.0;
	}

	@Override
	public void call(Event event) {

	}

	@Override
	public void call(PacketContainer packet) {
		
	}

	@Override
	public double checkCurrentCertainty() {
		return 0;
	}

}
