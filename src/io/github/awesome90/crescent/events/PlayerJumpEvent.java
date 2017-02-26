package io.github.awesome90.crescent.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerJumpEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	private final Player player;
	private final double distance;

	/**
	 * @param player
	 *            The player that has jumped.
	 * @param distance
	 *            The distance that the player has jumped upwards.
	 */
	public PlayerJumpEvent(Player player, double distance) {
		this.player = player;
		this.distance = distance;
	}

	public Player getPlayer() {
		return player;
	}

	public double getDistance() {
		return distance;
	}

}
