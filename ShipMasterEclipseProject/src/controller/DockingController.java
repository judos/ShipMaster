package controller;

import model.Dock;
import model.Map;
import model.Ship;

/**
 * @since 14.05.2015
 * @author Julian Schelker
 */
public class DockingController {

	private Map map;

	public DockingController(Map map) {
		this.map = map;
	}

	public void update() {
		for (Ship s : this.map.getShips()) {
			if (s.canDock()) {
				for (Dock d : this.map.getDocks()) {
					if (d.canAccept(s.getCargo()) && d.isShipInRange(s)) {
						s.startDocking(d);
						break;
					}
				}
			}
			else {
				boolean outside = true;
				for (Dock d : this.map.getDocks()) {
					if (d.isShipInRange(s)) {
						outside = false;
						break;
					}
				}
				if (outside)
					s.setCanDock(true);
			}
		}
	}

}
