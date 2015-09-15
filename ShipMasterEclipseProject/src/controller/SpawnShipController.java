package controller;

import model.Cargo;
import model.Map;
import model.Ship;
import model.ShipType;
import ch.judos.generic.data.geometry.DirectedPoint;
import ch.judos.generic.data.geometry.PointI;

/**
 * @since 17.05.2015
 * @author Julian Schelker
 */
public class SpawnShipController {

	private static final float spawnShipEveryXSec = 6.5f;
	private static final int dontSpawnNearerThanXPixelsToShip = 200;
	private static final boolean shouldSpawnShips = true;

	private Map map;

	private int timer;

	public SpawnShipController(Map map) {
		this.map = map;
		this.timer = 0;
	}

	public void update() {
		this.timer++;
		if (this.timer > Game.FPS * spawnShipEveryXSec && shouldSpawnShips) {
			this.timer = 0;

			ShipType type = ShipType.getRandom();
			DirectedPoint spawn;
			do {
				spawn = this.map.getSpawnLocation(type.getSize());
			} while (nearestDistanceToPoint(spawn.getPoint(), dontSpawnNearerThanXPixelsToShip) < dontSpawnNearerThanXPixelsToShip);
			Cargo cargo = this.map.generateCargo(type);
			Ship ship = new Ship(spawn, type, cargo);
			this.map.addShip(ship);
		}
	}

	/**
	 * @param point
	 *            the point from where to check the distance to the nearest ship
	 * @param lowerLimitInterested
	 *            if the value is below this limit, immediately returns
	 * @return distance
	 */
	public int nearestDistanceToPoint(PointI point, int lowerLimitInterested) {
		int distance = Integer.MAX_VALUE;
		for (Ship s : this.map.getShips()) {
			int currentDistance = (int) s.getPoint().distance(point);
			// improve performance with this lower limit test
			if (currentDistance < lowerLimitInterested)
				return currentDistance;
			if (currentDistance < distance)
				distance = currentDistance;
		}
		return distance;
	}

}
