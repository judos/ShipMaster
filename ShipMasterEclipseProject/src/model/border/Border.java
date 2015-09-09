package model.border;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Optional;

import model.Ship;
import ch.judos.generic.data.geometry.Angle;
import ch.judos.generic.data.geometry.PointI;

/**
 * @since 17.05.2015
 * @author Julian Schelker
 */
/**
 * @since 18.05.2015
 * @author Julian Schelker
 */
public abstract class Border {

	protected Angle normalAngle;

	public Border(Angle normal) {
		this.normalAngle = normal;
	}

	public boolean shouldShipBeRemovedAfterCrossing() {
		return false;
	}

	/**
	 * check whether the ship with this cargo load is allowed to cross the
	 * border
	 * 
	 * @param ship
	 * @return
	 */
	public abstract boolean shipIsAllowedToCross(Ship ship);

	public abstract boolean positionUpdateWouldCross(Point2D original, Point2D newPosition,
		Optional<Ship> ship);

	public void turnShip(Ship ship) {
		Angle target = ship.getDirection().bounceOnNormal(this.normalAngle);
		ship.setDirection(target);
	}

	public abstract PointI getClosestPointOnBorderTo(Point point);
}
