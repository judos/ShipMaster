package model.border;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Optional;

import model.Ship;
import ch.judos.generic.data.geometry.Angle;
import ch.judos.generic.data.geometry.LineI;
import ch.judos.generic.data.geometry.PointF;
import ch.judos.generic.data.geometry.PointI;

/**
 * protects ship from moving onto land
 * 
 * @since 17.05.2015
 * @author Julian Schelker
 */
public class RestrictionBorder extends Border {

	private LineI	line;

	public RestrictionBorder(PointI start, PointI end) {
		super(start.getAAngleTo(end).sub(Angle.A_90));
		this.line = new LineI(start, end);
	}

	@Override
	public boolean positionUpdateWouldCross(Point2D before, Point2D after, Optional<Ship> ship) {
		double sideWayDistOutside = this.line.ptLineDistAlongOutside(after);
		if (Math.abs(sideWayDistOutside) > 3)
			return false;
		if (this.line.ptLineDistSigned(after) < 0 && this.line.ptLineDistSigned(before) >= 0)
			return true;
		return false;
	}

	@Override
	public boolean shipIsAllowedToCross(Ship ship) {
		return false;
	}

	public PointI getStart() {
		return this.line.getP1();
	}

	public PointI getEnd() {
		return this.line.getP2();
	}

	@Override
	public PointI getClosestPointOnBorderTo(Point point) {
		double distanceFromLine = this.line.ptLineDist(point);
		return new PointF(point).movePoint(this.normalAngle, distanceFromLine).i();
	}
}
