package model.border;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Optional;
import java.util.function.BiFunction;

import model.Ship;
import ch.judos.generic.data.geometry.Angle;
import ch.judos.generic.data.geometry.PointI;

/**
 * @since 17.05.2015
 * @author Julian Schelker
 */
public class ScreenBorder extends Border {

	public static final ScreenBorder leftBorder = new ScreenBorder(Angle.A_0, (size, pos) -> {
		return pos.getX() <= -size;
	});
	public static final ScreenBorder rightBorder = new ScreenBorder(Angle.A_180,
		(size, pos) -> {
			return pos.getX() >= 1919 + size;
		});
	public static final ScreenBorder topBorder = new ScreenBorder(Angle.A_90, (size, pos) -> {
		return pos.getY() <= -size;
	});
	public static final ScreenBorder bottomBorder = new ScreenBorder(Angle.A_270,
		(size, pos) -> {
			return pos.getY() >= 1079 + size;
		});

	private BiFunction<Integer, Point2D, Boolean> wouldCrossFct;

	private ScreenBorder(Angle normal, BiFunction<Integer, Point2D, Boolean> wouldCross) {
		super(normal);
		this.wouldCrossFct = wouldCross;
	}

	@Override
	public boolean shipIsAllowedToCross(Ship ship) {
		return ship.getCargo().isAllowedToBeExported();
	}

	@Override
	public boolean positionUpdateWouldCross(Point2D original, Point2D newPos,
		Optional<Ship> ship) {
		int border = 0;
		if (ship.isPresent()) {
			border = ship.get().getType().getSize();
			// ship with cargo will cross the border already when
			// they partly leave the screen
			if (!ship.get().getCargo().isEmpty())
				border = 0;

		}
		boolean before = this.wouldCrossFct.apply(border, original);
		return !before && this.wouldCrossFct.apply(border, newPos);
	}

	@Override
	public boolean shouldShipBeRemovedAfterCrossing() {
		return true;
	}

	@Override
	public PointI getClosestPointOnBorderTo(Point p) {
		if (this.normalAngle.equals(Angle.A_0))
			return new PointI(0, p.y);
		else if (this.normalAngle.equals(Angle.A_90))
			return new PointI(p.x, 0);
		else if (this.normalAngle.equals(Angle.A_180))
			return new PointI(1920, p.y);
		else if (this.normalAngle.equals(Angle.A_270))
			return new PointI(p.x, 1080);
		throw new RuntimeException("undefined closest point for ScreenBorder with normal: "
			+ this.normalAngle);
	}

}
