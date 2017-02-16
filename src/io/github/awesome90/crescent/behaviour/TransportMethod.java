package io.github.awesome90.crescent.behaviour;

public enum TransportMethod {

	/*
	 * Source for data apart from sprint jumping:
	 * http://minecraft.gamepedia.com/Transportation
	 */

	WALKING(4.30), SPRINTING(5.60), SPRINT_JUMPING(
			6.99 /*
					 * Source:
					 * http://gaming.stackexchange.com/questions/174761/how-much
					 * -faster-is-jumping-while-sprinting
					 */), FLYING(10.8), FLYING_SPRINTING(21.6);

	/**
	 * Speed in metres per second of this particular mode of transport.
	 */
	private double speed;

	private TransportMethod(double maxSpeed) {
		this.speed = maxSpeed;
	}

	public double getSpeed() {
		return speed;
	}

}
