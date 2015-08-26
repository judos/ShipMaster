package controller;

import java.util.Iterator;
import java.util.Optional;

import model.Map;
import model.Ship;
import model.border.Border;
import ch.judos.generic.data.geometry.PointF;

/**
 * @since 14.05.2015
 * @author Julian Schelker
 */
public class ShipController {

	private Map	map;

	public ShipController(Map map) {
		this.map = map;
	}

	public void update() {
		Iterator<Ship> it = this.map.getShips().iterator();
		while (it.hasNext()) {
			Ship ship = it.next();

			if (!ship.isStopped()) {
				PointF next = ship.getNextPosition();
				boolean canMove = true;
				if (!ship.isDocking()) {
					for (Border b : this.map.getBorders()) {
						if (b.positionUpdateWouldCross(ship.getPoint(), next, Optional.of(ship))) {
							if (b.shipIsAllowedToCross(ship)) {
								if (b.shouldShipBeRemovedAfterCrossing())
									it.remove();
							}
							else {
								canMove = false;
								b.turnShip(ship);
							}
						}
					}
				}
				if (canMove)
					ship.setPosition(next);
			}
		}
	}
}
