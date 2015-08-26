package model;

import java.awt.Point;
import java.util.ArrayList;
import ch.judos.generic.data.geometry.Angle;
import ch.judos.generic.data.geometry.DirectedPoint;
import ch.judos.generic.data.geometry.PointF;
import ch.judos.generic.data.geometry.PointI;

/**
 * @since 14.05.2015
 * @author Julian Schelker
 */
public class Ship {

	public static final int		dangerRadius	= 10;

	private PointF					point;
	private Angle					direction;
	private ShipType				type;
	private ArrayList<PointI>	path;
	private boolean				isInDanger;
	private Cargo					cargo;

	private Dock					targetDock;

	private boolean				isStopped;

	private boolean				canDock;

	public Ship(DirectedPoint point, ShipType type, Cargo cargo) {
		this.point = point.getPointF();
		this.direction = point.getAAngle();
		this.type = type;
		this.cargo = cargo;
		this.path = new ArrayList<>();
		this.canDock = true;
	}

	/**
	 * @return the path
	 */
	public ArrayList<PointI> getPath() {
		return this.path;
	}

	public PointF getNextPosition() {
		if (this.isStopped)
			return this.point;
		if (this.path.size() > 0) {
			PointI nextTarget = this.path.get(0);
			this.direction = this.point.getAAngleTo(nextTarget);

			if (this.point.distance(nextTarget) < 5) {
				this.path.remove(0);
				if (this.path.size() == 0 && this.targetDock != null) {
					this.isStopped = true;
					this.direction = this.targetDock.getDirection().add(Angle.A_180);
				}
			}
		}
		return this.point.movePoint(this.direction.getRadian(), this.type.getSpeed());
	}

	public PointF getPoint() {
		return this.point;
	}

	public Angle getDirection() {
		return this.direction;
	}

	public ShipType getType() {
		return this.type;
	}

	public boolean containsPoint(Point somePoint) {
		return this.point.distance(somePoint) < this.type.getSize();
	}

	public double getDistanceFromHitboxTo(Point somePoint) {
		double dist = this.point.distance(somePoint) - this.type.getSize();
		if (dist < 0)
			return 0;
		return dist;
	}

	public void setInDanger(boolean isInDanger) {
		this.isInDanger = isInDanger;
	}

	public boolean isInDanger() {
		return this.isInDanger;
	}

	public Cargo getCargo() {
		return this.cargo;
	}

	public void startDocking(Dock d) {
		this.path.clear();
		this.path.add(d.getPoint().getPoint());
		this.targetDock = d;
		this.canDock = false;
	}

	public boolean isDocking() {
		return this.targetDock != null;
	}

	public boolean isSelectable() {
		return this.targetDock == null;
	}

	public void tryUnloading() {
		if (this.targetDock == null)
			return;
		if (this.path.size() > 0)
			return;
		boolean didUnload = this.targetDock.unload(this.cargo);
		if (didUnload) {
			if (!this.targetDock.canAccept(this.cargo)) {
				this.direction = this.targetDock.getDirection();
				this.targetDock = null;
			}
		}
	}

	public void setStopped(boolean isStopped) {
		this.isStopped = isStopped;
	}

	public boolean isStopped() {
		return this.isStopped;
	}

	public boolean canDock() {
		return this.canDock;
	}

	public void setCanDock(boolean canDock) {
		this.canDock = canDock;
	}

	public boolean hasCargo() {
		return !this.cargo.isEmpty();
	}

	public void setPosition(PointF point) {
		this.point = point;
	}

	/**
	 * manually override direction, clears the currently set path
	 * 
	 * @param a
	 */
	public void setDirection(Angle a) {
		this.direction = a;
		this.path.clear();
	}
}
