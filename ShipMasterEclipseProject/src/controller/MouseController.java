package controller;

import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Optional;

import javax.swing.SwingUtilities;

import model.Dock;
import model.Map;
import model.Ship;
import model.border.Border;
import ch.judos.generic.data.geometry.PointI;

/**
 * @since 14.05.2015
 * @author Julian Schelker
 */
public class MouseController extends MouseAdapter {

	private Map map;
	private Ship selectedShip;

	private Component component;
	private boolean startDrawingWhenOutsideDock;
	private Dock selectedShipInDock;

	public MouseController(Map map, Component component) {
		this.map = map;
		this.selectedShip = null;
		this.component = component;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Ship canSelect = null;
		double closestDist = 50; // max offset to select ship
		for (Ship s : this.map.getShips()) {
			if (s.isSelectable()) {
				double dist = s.getDistanceFromHitboxTo(e.getPoint());
				if (dist < closestDist) {
					canSelect = s;
					closestDist = dist;
					if (dist == 0)
						break;
				}
			}
		}
		if (canSelect != null) {
			this.selectedShip = canSelect;
			this.selectedShip.setTargetDockToNull();
			this.startDrawingWhenOutsideDock = this.selectedShip.isStopped();
			this.selectedShipInDock = findDockInRangeOf(this.selectedShip.getPoint());
			this.selectedShip.setStopped(false);
			this.selectedShip.getPath().clear();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.selectedShip = null;
	}

	public Point getMousePosition() {
		Point mousePosition = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(mousePosition, this.component);
		return mousePosition;
	}

	public void update() {
		// deselect ship
		if (this.selectedShip == null)
			return;
		if (!this.selectedShip.isSelectable()) {
			this.selectedShip = null;
			return;
		}

		ArrayList<PointI> path = this.selectedShip.getPath();
		Point mousePosition = getMousePosition();
		PointI lastPoint;
		if (path.size() > 0)
			lastPoint = path.get(path.size() - 1);
		else {
			lastPoint = this.selectedShip.getPoint().i();
			if (mousePosition.distance(lastPoint) < 20)
				return;
		}
		Dock dockInRange = findDockInRangeOf(mousePosition);

		if (checkDocking(dockInRange, path))
			return;
		if (checkBorder(lastPoint, mousePosition, path))
			return;
		if (checkUndocking(lastPoint, mousePosition, dockInRange))
			return;
		if (lastPoint.distance(mousePosition) >= 10)
			path.add(new PointI(mousePosition));
	}

	private boolean checkUndocking(PointI lastPoint, Point mousePosition, Dock dockInRange) {
		if (dockInRange == null && this.startDrawingWhenOutsideDock) {
			if (this.selectedShipInDock.getLeaveBorder().positionUpdateWouldCross(lastPoint,
				mousePosition, Optional.empty())) {
				this.startDrawingWhenOutsideDock = false;
			}
			else {
				this.selectedShip = null;
				return true;
			}
		}
		if (this.startDrawingWhenOutsideDock)
			return true;
		return false;
	}

	private boolean checkBorder(PointI lastPoint, Point mousePosition, ArrayList<PointI> path) {
		for (Border b : this.map.getBorders()) {
			if (b.positionUpdateWouldCross(lastPoint, mousePosition, Optional.empty())) {
				PointI pointOnBorder = b.getClosestPointOnBorderTo(mousePosition);
				path.add(pointOnBorder);
				this.selectedShip = null;
				return true;
			}
		}
		return false;
	}

	private boolean checkDocking(Dock dockInRange, ArrayList<PointI> path) {
		if (dockInRange != null && !this.startDrawingWhenOutsideDock) {
			if (dockInRange.canAccept(this.selectedShip.getCargo())) {
				path.add(dockInRange.getPoint().getPoint());
				this.selectedShip = null;
				return true;
			}
			this.selectedShip = null;
			return true;
		}
		return false;
	}

	protected Dock findDockInRangeOf(Point2D position) {
		for (Dock d : this.map.getDocks()) {
			if (d.isPointInRange(position)) {
				return d;
			}
		}
		return null;
	}
}
