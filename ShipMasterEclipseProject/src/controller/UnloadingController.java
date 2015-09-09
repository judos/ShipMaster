package controller;

import model.Map;
import model.Ship;

/**
 * @since 14.05.2015
 * @author Julian Schelker
 */
public class UnloadingController {

	private Map map;

	public UnloadingController(Map map) {
		this.map = map;
	}

	public void update() {
		for (Ship s : this.map.getShips()) {
			s.tryDockAction();
		}
	}

}
