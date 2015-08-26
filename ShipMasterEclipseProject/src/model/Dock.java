package model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.HashSet;

import model.border.Border;
import model.border.DockLeaveBorder;
import ch.judos.generic.data.geometry.Angle;
import ch.judos.generic.data.geometry.DirectedPoint;
import ch.judos.generic.data.geometry.PointI;
import controller.Game;

/**
 * @since 14.05.2015
 * @author Julian Schelker
 */
public class Dock {

	public static final int		RADIUS	= 70;

	private DirectedPoint		point;
	private int						unloadingTimer;

	private DockType				type;
	private HashSet<CargoType>	acceptedCargo;

	private DockLeaveBorder	leaveBorder;

	public Dock(DirectedPoint directedPoint) {
		this(directedPoint, DockType.NORMAL);
	}

	public Dock(DirectedPoint directedPoint, DockType type) {
		this.point = directedPoint;
		this.unloadingTimer = 0;
		this.type = type;
		this.acceptedCargo = CargoType.getAllCargoTypes();
		this.leaveBorder = DockLeaveBorder.createFromDock(this);
	}

	public HashSet<CargoType> getAcceptedCargo() {
		return this.acceptedCargo;
	}

	public void setAcceptedCargo(CargoType... cargoTypes) {
		this.acceptedCargo.clear();
		for (CargoType t : cargoTypes)
			this.acceptedCargo.add(t);
	}

	private boolean canUnload(CargoType t) {
		return this.acceptedCargo.contains(t);
	}

	public boolean canAccept(Cargo c) {
		for (CargoType t : c) {
			if (canUnload(t))
				return true;
		}
		return false;
	}

	public DirectedPoint getPoint() {
		return this.point;
	}

	/**
	 * @param cargo
	 * @return did unload something
	 */
	public boolean unload(Cargo cargo) {
		boolean didUnloadSomething = false;
		this.unloadingTimer++;
		if (this.unloadingTimer >= Game.FPS / this.type.getUnloadPerSecond()) {

			for (int i = 0; i < cargo.getSize(); i++) {
				if (canUnload(cargo.getTypeAt(i))) {
					cargo.setEmpty(i);
					didUnloadSomething = true;
					break;
				}
			}
			this.unloadingTimer = 0;
		}
		return didUnloadSomething;
	}

	public boolean isShipInRange(Ship s) {
		int shipSize = s.getType().getSize();
		PointI dockPosition = this.point.getPoint();
		return s.getPoint().distance(dockPosition) < shipSize + Dock.RADIUS;
	}
	
	public boolean isPointInRange(Point2D p) {
		return this.point.getPoint().distance(p) < Dock.RADIUS;
	}

	public Color getColor() {
		if (this.acceptedCargo.size() == 1) {
			CargoType ac = this.acceptedCargo.iterator().next();
			return ac.getColor();
		}
		return Color.white;
	}

	public Angle getDirection() {
		return this.point.getAAngle();
	}

	public Border getLeaveBorder() {
		return this.leaveBorder;
	}

}
